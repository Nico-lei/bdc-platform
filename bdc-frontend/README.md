# bdc-frontend · 前端 Monorepo

> 基于 **pnpm workspace + Vue 3.4 + TypeScript 5 + Vite 5 + Element Plus 2** 的多应用单仓。
> 落地阶段：**P2.5**（W6\~W9）。当前 P0.1.2 仅骨架。

## 应用清单

| 应用 | 子域名（生产） | 用途 | 落地阶段 |
| --- | --- | --- | --- |
| [`portal`](./portal/) | `portal.bdc.edu.cn` | 统一门户首页 + 师生入口 | P2.5 |
| [`admin`](./admin/) | `admin.bdc.edu.cn` | 平台管理控制台（uac / 采集 / 仓库 / 治理 / 集市 / 安全 / 审计） | P2.5 \~ P5 |
| [`screen`](./screen/) | `screen.bdc.edu.cn` | 数据大屏运行态 | P4.8 |
| [`market`](./market/) | `market.bdc.edu.cn` | 数据集市门户（用户视角） | P4.7 |
| [`designer`](./designer/) | `designer.bdc.edu.cn` | 大屏 / ETL / 表单 设计器 | P3.3 / P4.8 |

> 后续可在 `shared/` 目录下沉公共组件（`shared/components`、`shared/utils`、`shared/api-client`）。

## 快速开始（待 P0.2 完成 Node + pnpm 安装后）

```bash
cd bdc-frontend
pnpm install
pnpm dev:portal      # 启动门户
pnpm dev:admin       # 启动管理控制台
pnpm build           # 全量构建
```

## 引用文档

- [`docs/03-功能模块详设/08-数据可视化模块.md`](../docs/03-功能模块详设/08-数据可视化模块.md)
- [`docs/05-接口设计/01-接口设计规范.md`](../docs/05-接口设计/01-接口设计规范.md)
- [`docs/08-开发规范/01-编码规范.md`](../docs/08-开发规范/01-编码规范.md)
- [`docs/10-项目管理/02-开发任务计划.md`](../docs/10-项目管理/02-开发任务计划.md) § P2.5 / P4.7 / P4.8
