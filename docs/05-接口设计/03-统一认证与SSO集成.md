# 03 统一认证与 SSO 集成

## 1. 目标

- 对接学校现有 **CAS**（首选）与 **LDAP**（账号库）；
- 中台自身**不**自建账号体系；
- 满足等保三级的身份鉴别、双因素（管理员）、访问控制要求。

## 2. 认证方式

| 用户类型 | 登录方式 | MFA |
| --- | --- | --- |
| 师生（业务用户） | CAS SSO | 可选 |
| 系统/安全/审计管理员 | CAS + 强制 MFA（TOTP） | 强制 |
| 外部应用 | AK/SK + 签名 | — |
| 服务间 | mTLS + Feign Token | — |

## 3. CAS 接入

### 3.1 假定

- 学校 CAS Server: `https://cas.edu.cn/cas`
- 协议：CAS 3.0（支持 SAML 协议者更佳）
- 可获取用户属性：`uid`（工号/学号）, `cn`（姓名）, `mail`, `mobile`（视 CAS 管理员开放）, `affiliation`（员工/学生）

### 3.2 登录流程

```
用户访问 https://portal.bdc.edu.cn/
  └─▶ bdc-auth 发现未登录
       └─302 重定向 https://cas.edu.cn/cas/login?service=https://api.bdc.edu.cn/api/auth/v1/cas/callback
            └─用户在 CAS 登录成功
                 └─302 带 ticket 回到 /cas/callback?ticket=ST-xxx
                      └─bdc-auth 向 CAS Server validate：
                         https://cas.edu.cn/cas/serviceValidate?service=...&ticket=ST-xxx
                           └─返回用户 uid + 属性
                                └─bdc-auth 本地查 bdc_uac.t_user（必须已同步），未找到 → 按配置：
                                    a) 自动创建（默认关闭）
                                    b) 提示联系管理员
                                └─签发 JWT（HS256 或 SM2 签名），写 HttpOnly Cookie
                                └─重定向回门户
```

### 3.3 单点注销（SLO）

- `GET /api/auth/v1/logout` → 清除本地 Token → 重定向 `https://cas.edu.cn/cas/logout?service=https://portal.bdc.edu.cn/logged-out`；
- CAS Back-Channel Logout：`POST /api/auth/v1/cas/back-channel-logout`（可选）。

## 4. LDAP 集成

### 4.1 用途

- 用户 / 组织数据**同步**（不作为主动认证，兜底使用）；
- CAS 不可达时，LDAP 账密直登（仅管理员可配置开启）。

### 4.2 参数（示例）

```
url: ldap://ldap.edu.cn:389
baseDN: ou=people,dc=edu,dc=cn
userFilter: (uid={0})
attrs: uid, cn, mail, mobile, employeeType, departmentNumber
```

### 4.3 同步策略

- DS 定时任务：每日凌晨全量拉取 LDAP → 比对 `bdc_uac.t_user`；
- 事件驱动：CAS 登录回调若 bdc_uac 缺失用户 + 配置允许 → 从 LDAP 按需拉取；
- 敏感字段（手机、邮箱）：入库即 SM4 加密 + SM3 Hash。

## 5. Token 设计

### 5.1 JWT 规范

- 头部：`{"alg":"SM2","typ":"JWT","kid":"auth-2026"}`（或 `HS256` 兜底）
- Payload 关键字段：
  - `sub`（userId）
  - `lgName`（登录名）
  - `orgId`
  - `roles`
  - `authTime`、`exp`、`iat`、`jti`
  - `mfa`（true/false）
  - `authMethod`（cas/ldap）
- 有效期：Access 30 min，Refresh 12 h；高敏操作 Step-up MFA 后 15 min 内可做；
- 签名：SM2 非对称签名，公钥下发各服务；或 HS256 共享密钥（内网低风险）。

### 5.2 Token 吊销

- 黑名单表（Redis，失效时间 = exp）；
- Refresh Token 每次使用轮换；
- 管理员账户吊销立即生效（广播事件）。

## 6. 管理员 MFA

- 基于 TOTP（RFC 6238），内置 Authenticator App（Google/Microsoft）扫码绑定；
- 首次登录后 30 天内必须绑定；
- 特殊操作（KMS 轮换、审计导出、上架 L4 资源）二次 MFA；
- Step-up：session 中最近 15 min 未 MFA 则重新校验。

## 7. 应用鉴权（AK/SK）

- 接入方：由数据服务模块注册应用；
- Secret 下发：仅展示一次，建议用"接入方私钥 + 中台公钥"的非对称方式交换；
- 请求签名：见 `01-接口设计规范.md#5.2`；
- 密钥轮换：季度强制提醒，支持平滑轮换（新旧 Secret 各 7 天并存）；
- IP 白名单：应用 + 可选 API 粒度。

## 8. 服务间认证

- Docker 内网 mTLS（Gateway SSL Bundle 或服务证书）；
- Feign 拦截器透传 JWT + 服务身份 Header `X-Service-Id`；
- 服务白名单：Gateway 到后端强制；
- 审计：内部调用也写审计（抽样）。

## 9. 安全细节

- 密码策略：即使走 LDAP，也对本地兜底账号强制：长度 12、三类以上、不复用、90 天期；
- 错误次数：5 次锁 30 min（UID）+ 10 次锁 1 h（IP）；
- 登录日志：成功/失败，IP、UA、时间、地点（按校内 IP 库）；
- Session 固定：登录成功后重签 Session / 轮换 Token；
- CSRF：纯 Token 无需额外；Cookie 模式 `SameSite=Strict`；
- Clock Skew：允许 ±300 秒；
- Referrer Policy：`strict-origin-when-cross-origin`。

## 10. 配置与开关

Nacos 配置（示例）：

```yaml
bdc:
  auth:
    cas:
      server-url: https://cas.edu.cn/cas
      service-url: https://api.bdc.edu.cn/api/auth/v1/cas/callback
      protocol: CAS3
    ldap:
      url: ldap://ldap.edu.cn:389
      base-dn: ou=people,dc=edu,dc=cn
      user-filter: (uid={0})
      bind-dn: cn=readonly,dc=edu,dc=cn
      bind-pwd-cipher: "SM4:xxxx"
    token:
      access-ttl: 30m
      refresh-ttl: 12h
      algorithm: SM2
      kid: auth-2026
    mfa:
      enforce-admin: true
      step-up-critical: true
```

## 11. 运维要点

- **灰度上线**：CAS 回调地址先在预发环境验证；
- **容灾**：CAS 不可达时自动降级到 LDAP 兜底（管理员审批启用）；
- **时钟同步**：NTP 对齐，偏差 > 60s 告警；
- **证书**：JWT 签名私钥存 HSM / 文件加密；定期轮换；
- **审计**：所有认证事件同步到审计中心。

## 12. 接入检查清单

- [ ] 已获取 CAS 管理员联系人、CAS Server URL、Service 配置
- [ ] 已获取 LDAP BaseDN、只读账号
- [ ] 已配置 CAS Service 白名单（回调 URL 备案）
- [ ] 已协调学校统一身份认证属性返回（姓名、工号、邮箱）
- [ ] 内网 DNS 已解析 `cas.edu.cn`
- [ ] 校准时钟（NTP）
- [ ] 账号同步策略确认
- [ ] 管理员 MFA 装置发放（可选硬 Token）
