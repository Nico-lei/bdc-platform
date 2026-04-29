# Commit / PR 规范

> 全员强制遵守。CI 会校验 **PR 标题** 与每条 **commit 标题** 是否符合 Conventional Commits。
>
> 上位规范：[`docs/08-开发规范/02-Git与CI-CD规范.md`](../docs/08-开发规范/02-Git与CI-CD规范.md)。

## 1. 标题格式

```
<type>(<scope>): <subject>  [JIRA-XXX]
```

| 字段 | 规则 | 示例 |
| --- | --- | --- |
| `type` | 必填，小写，见下方枚举 | `feat` |
| `scope` | 必填，**短名**（不含 `bdc-` 前缀） | `collect`、`uac`、`common-security`、`frontend-admin` |
| `subject` | ≤ 50 字，祈使句 / 现在时，不加句号 | `新增达梦增量采集连接器` |
| `[JIRA-XXX]` | 一期暂可写 GitHub Issue 号 `#123` | `[BDC-101]` 或 `(#123)` |

### type 枚举

| type | 含义 | 出现在 CHANGELOG | 是否触发 minor 版本 |
| --- | --- | --- | --- |
| `feat` | 新功能 | ✅ | ✅ |
| `fix` | 缺陷修复 | ✅ | patch |
| `perf` | 性能优化 | ✅ | patch |
| `refactor` | 重构（不改外部行为） | ✅（合并段） |  |
| `docs` | 文档变更 |  |  |
| `style` | 格式化（空格/分号），不改语义 |  |  |
| `test` | 测试相关 |  |  |
| `build` | 构建脚本 / 依赖 |  |  |
| `ci` | CI/CD 流水线 |  |  |
| `chore` | 杂项（不影响代码与文档） |  |  |
| `revert` | 回滚 | ✅ |  |

### Body / Footer

```
<type>(<scope>): <subject>  [JIRA-XXX]
<空行>
<body：为什么这样做、关键决策、影响面，可多段>
<空行>
BREAKING CHANGE: <破坏性变更说明>
Closes: BDC-101, BDC-102
```

## 2. 良好 / 不良示例

```text
✅ feat(collect): 新增达梦增量采集连接器  [BDC-101]
✅ fix(uac): 修复 LDAP 全量同步死循环  [BDC-203]
✅ docs(security): 修正国密 TLS 协议版本为 TLCP/TLS 1.2
✅ refactor(common-db): 提取 search_path 注入逻辑到独立 Interceptor
✅ ci: GitHub Actions 自托管 Runner 配置初版

❌ update              （没有 type / scope）
❌ feat: aaa           （subject 无意义）
❌ feat(Collect): 新增连接器 （scope 必须小写、用短名）
❌ Fix(uac): xxxxxx    （type 必须小写）
❌ feat(collect): 新增达梦增量采集连接器,优化采集性能,修复SCD2 bug   （多目标，应拆分多个 commit）
```

## 3. 分支命名

```
feat/<issue>-<短描述>      例：feat/BDC-101-dameng-collect
fix/<issue>-<短描述>       例：fix/BDC-203-uac-ldap-sync
hotfix/<version>-<短描述>  例：hotfix/v1.0.1-cve-patch
release/<x.y.z>            例：release/1.0.0
chore/<短描述>             例：chore/upgrade-spring-boot-3.2.6
docs/<短描述>              例：docs/governance-design
```

## 4. PR 规则

- 每个 PR 聚焦**单一目标**，超 500 行代码（除生成代码外）需拆分；
- PR 标题与 commit 标题同格式；
- 至少 **1 名 reviewer**（高危模块 ≥ 2，参见 [`CODEOWNERS`](./CODEOWNERS)）；
- 必检项：CI 全绿（lint / test / sonar / 安全扫描 / 镜像扫描）；
- 合并方式：默认 **Squash and merge**，保持 main 历史线性整洁。

## 5. 高危模块（强制 ≥ 2 reviewer）

> 这些路径下的变更，CODEOWNERS 已就位 1 人，团队建议在 PR 中再 @ 1 人评审：

- `/bdc-services/security/`、`/bdc-services/audit/`
- `/bdc-common/security/`、`/bdc-common/crypto/`、`/bdc-common/audit/`
- `/bdc-deploy/sql/`、`/bdc-deploy/prod/`、`/bdc-deploy/scripts/`
- `/docs/06-安全与合规/`、`/docs/01-总体架构/`
- 任何 Flyway DDL 变更（`**/sql/V*.sql`）

## 6. 自动化（由 CI 实施）

| 校验项 | 工具 | 触发 |
| --- | --- | --- |
| PR 标题格式 | `amannn/action-semantic-pull-request` | 每次 PR 打开 / 编辑标题 |
| Commit 标题格式 | `wagoid/commitlint-github-action` | PR 打开 / 推送 commit |
| 密钥泄露扫描 | `gitleaks` | 每次 PR / push |
| 文件大小（>10MB 拦截） | 自定义 step | 每次 PR |

## 7. 本地辅助（可选，推荐）

```bash
# 推荐安装 commitlint + husky 做本地预校验（待 P0.6 接入）
pnpm dlx commitlint --from=HEAD~1
```

> 当前 P0.1.3 仅落地 CI 端校验；本地 husky 钩子由 P0.6 测试质量基线阶段统一接入。
