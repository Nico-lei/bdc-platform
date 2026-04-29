<!--
PR 标题格式（强校验，CI 会拦截）：
  <type>(<scope>): <subject>  [JIRA-1234]
  例：  feat(collect): 新增达梦增量采集连接器  [BDC-101]
        fix(uac): 修复 LDAP 全量同步死循环  [BDC-203]

  type   : feat | fix | docs | style | refactor | perf | test | build | ci | chore | revert
  scope  : 服务/模块短名（如 collect、uac、common-security、frontend-admin）
  subject: 50 字以内，祈使句，不加句号

详细规范见：.github/COMMIT_CONVENTION.md
              docs/08-开发规范/02-Git与CI-CD规范.md
-->

## 1. 变更摘要

> 一句话说清"做了什么 + 为什么"。  
> 关联文档（如有）：[`docs/...`](../docs/)

-

## 2. 关联

- 关联 Issue / Jira：close #___ / BDC-____
- 关联设计文档：`docs/...`
- 关联 WBS 阶段：P_._._（参见 `docs/10-项目管理/02-开发任务计划.md`）

## 3. 变更类型

- [ ] feat（新功能）
- [ ] fix（缺陷修复）
- [ ] refactor（重构，不影响外部行为）
- [ ] perf（性能优化）
- [ ] docs（文档）
- [ ] test（测试）
- [ ] build / ci / chore（构建、CI、杂项）
- [ ] BREAKING CHANGE（破坏性变更，需在 footer 显式标注）

## 4. 影响面

- [ ] 数据库变更（Flyway 脚本已加入）
- [ ] 接口变更（OpenAPI 文件已同步）
- [ ] 配置变更（Nacos 命名空间已说明）
- [ ] 安全/分级敏感字段（已与 `docs/06-安全与合规` 对齐）
- [ ] 监控/告警/SLO（已在 `docs/09-部署运维/02-监控告警设计.md` 同步）

## 5. 测试

- [ ] 已补充单元测试（核心模块行覆盖率 ≥ 70%）
- [ ] 已补充/通过集成测试（核心主流程 100%）
- [ ] 已本地启动验证：

```text
# 粘贴本地验证命令与关键输出
```

## 6. 风险与回滚

> 如有不可逆变更（删表 / 改字段类型 / 大表索引 等）必须填。

- 风险：
- 回滚预案：

## 7. 合并清单（自检）

- [ ] PR 标题符合 `<type>(<scope>): <subject> [JIRA-XXX]`
- [ ] 至少 1 名 reviewer（高危模块 ≥ 2 名，参见 `.github/CODEOWNERS`）
- [ ] CI 全绿（lint / test / sonar / 安全扫描 / 镜像扫描）
- [ ] 无密钥/凭证泄露（`.env`、`*.pem`、`*.jks` 等不进库）
- [ ] 已更新 `CHANGELOG`（如发版相关）
- [ ] 已更新对应文档（`docs/03-功能模块详设/`、`docs/04-数据库详细设计/`、`docs/05-接口设计/`）

---

> Reviewer 工作建议（可勾选，不强制）：
>
> - 是否符合分层与命名规范？(`docs/08-开发规范/01-编码规范.md`)
> - 是否引入跨域直连或绕过 `bdc-common-*`？
> - 数据敏感字段是否使用 `@Encrypt` / 脱敏注解？
> - 异常路径是否走 `BdcException + ErrorCode`？
