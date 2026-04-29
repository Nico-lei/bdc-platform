# 02 Git 与 CI/CD 规范

## 1. 代码仓库管理

- 单 Monorepo（`bdc-platform`） + 独立子仓（`bdc-flink-jobs`、`bdc-deploy`）；
- 托管：GitHub；
- 分支保护：`main`、`dev` 禁止直推，必须 PR；
- 必检：CI 通过 + 至少 1 人 +1（高危模块 2 人）+ Sonar 门禁。

## 2. 分支策略（GitFlow 简化）

```
main (tag: v1.0.0, v1.1.0...)   ← 每次上线
   ↑
release/1.x.x                    ← 发版分支，灰度验证
   ↑
dev                              ← 日常主线，所有 feat/fix 汇合
   ↑
feat/<issue>-<短描述>             ← 功能分支
fix/<issue>-<短描述>              ← 缺陷分支
hotfix/<version>-<短描述>         ← 紧急修复，从 main 拉，合回 main+dev
```

## 3. 提交信息

Conventional Commits：
```
<type>(<scope>): <subject>

[body]

[footer: BREAKING CHANGE / close #issue]
```
`type`：`feat|fix|docs|style|refactor|perf|test|build|ci|chore|revert`
`scope`：服务名或模块名（如 `collect`, `uac`, `common-security`）。

## 4. Jira / Issue 对接

- 每个 PR 标题包含 `[JIRA-1234]`；
- 合并后自动更新 Jira 状态（GitHub Webhook）；
- 版本发布自动生成 CHANGELOG（基于 Conventional Commits）。

## 5. CI 流水线（GitHub Actions）

```
stage 1: 代码拉取
stage 2: 静态检查（ESLint/Checkstyle/Spotless）
stage 3: 编译 & 单元测试
stage 4: SonarQube 扫描（质量门禁）
stage 5: 依赖漏洞扫描（OWASP Dependency-Check / Trivy fs）
stage 6: 许可证扫描（禁用 GPL/AGPL）
stage 7: 构建产物（Jar / Vue dist）
stage 8: Docker 镜像构建 + Trivy 镜像扫描
stage 9: 推送 Harbor 私仓（打 tag: {branch}-{shortCommit}）
stage 10: 自动部署到 DEV 环境（Docker Compose pull + up -d）
stage 11: 自动化 API 测试（Newman / RestAssured）
stage 12: 通知结果（钉钉/邮件）
```

## 6. CD 流水线

- **DEV** 环境：每次 PR 合入 `dev` 自动部署；
- **TEST** 环境：手动触发（或按 Tag）；
- **UAT** 环境：按 `release/x.y.z` 触发，灰度；
- **PROD** 环境：手动审批 + 双人操作 + 变更单号；
  - 蓝绿或分批切流部署；
  - 回滚脚本随部署一同生成。

## 7. 镜像规范

- 基镜像：`harbor.bdc.edu.cn/base/jdk17:1.0`、`base/node-nginx:1.0`；
- 多阶段构建；
- 用户：`nonroot`（uid 1000）；
- 最小化：只装运行时依赖；
- 安全：`USER nonroot`，禁 `--privileged`；
- 标签：`maintainer=bdc-team`, `license=Apache-2.0`, `version=1.0.0`；
- Trivy 扫描：Critical 漏洞 0 容忍。

## 8. 配置管理

- 所有配置放 Nacos；
- 敏感配置加密存储（`ENC(...)`，启动时 KMS 解密）；
- 禁止 `.env` 提交仓库；
- 环境区分命名空间：`dev/test/uat/prod`。

## 9. 流水线失败处置

- 编译失败：作者必须 2h 内修复；
- 测试失败：阻断合并；
- Sonar 门禁失败：需修复或架构组豁免；
- 安全扫描发现高危：必须升级或替换依赖，除非有豁免说明。

## 10. 发布流程

1. 从 `dev` 拉 `release/x.y.z`；
2. UAT 测试 + 回归；
3. 打 Tag `v{x.y.z}`；
4. 合回 `main` 和 `dev`；
5. 生成 Release Notes（CHANGELOG）；
6. 灰度部署：先 10% 流量 → 30% → 100%；
7. 监控 1h 无异常 → 全量；
8. 异常：一键回滚（保留上一版本镜像）。

## 11. 数据库变更流程（Flyway）

- SQL 脚本 PR → DBA 审核（校验性能、索引、兼容性）；
- Dev/Test 自动执行；
- Prod 由 DBA 在变更窗口手工执行 + Flyway 锁；
- 重大变更（DDL 大表、索引重建）必须：
  - 变更单；
  - 灰度先在预发复核；
  - 低峰期执行；
  - 回滚方案。

## 12. 容器化与资源声明

Docker Compose 默认值示例：
```yaml
services:
  bdc-auth:
    image: harbor.bdc.edu.cn/bdc/bdc-auth:${IMAGE_TAG}
    restart: always
    env_file:
      - .env.prod
    deploy:
      resources:
        limits:
          cpus: "2"
          memory: 4G
    healthcheck:
      test: ["CMD", "curl", "-f", "http://localhost:8101/actuator/health"]
      interval: 30s
      timeout: 5s
      retries: 3
```

## 13. 安全门禁汇总

| 门禁 | 阻断条件 |
| --- | --- |
| 代码规范 | Checkstyle 高危 |
| 单元测试 | 失败或覆盖率降幅 > 2% |
| Sonar | Bug Blocker/Critical > 0；Code Smell Major > 20 |
| 依赖漏洞 | 高危 CVE > 0（可豁免，需书面） |
| 许可证 | 未经项目确认的 GPL/AGPL/SSPL 出现 |
| 镜像扫描 | 高危 CVE > 0 |
| 秘钥扫描 | 代码出现疑似密钥（truffleHog） |
| 提交钩子 | Commit 无 Jira 号 |

## 14. 文档与代码同步

- 接口变更必须同步更新 `api/*.yaml`；
- 数据库变更必须同步更新 `04-数据库详细设计/01-核心表结构清单.md`；
- 模块重大设计变化更新对应 `03-功能模块详设/*.md`；
- 文档变更走 PR，专人 review。
