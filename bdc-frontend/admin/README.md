# admin

| 项 | 值 |
| --- | --- |
| 工程 | `bdc-frontend/admin` |
| 用途 | 平台管理控制台（uac/采集/仓库/治理/集市/安全/审计） |
| dev 端口 | `5174` |
| 落地阶段 | **P2.5 ~ P5**（当前 P0.1.2 仅骨架） |

## 启动

```bash
cd bdc-frontend
pnpm install
pnpm dev:admin
# 或
pnpm --filter admin dev
```

## 引用文档

- [`docs/05-接口设计/`](../../docs/05-接口设计/)
- [`docs/08-开发规范/01-编码规范.md`](../../docs/08-开发规范/01-编码规范.md)
- [`docs/10-项目管理/02-开发任务计划.md`](../../docs/10-项目管理/02-开发任务计划.md)