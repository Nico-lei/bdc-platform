# bdc-platform · 高校数据中台 Monorepo

> 本仓库为 **高校数据中台系统**（bdc-platform）的 **单仓 Monorepo**，承载所有微服务、二方库、Flink 作业、DataX 插件、前端工程、部署脚本与设计文档。
>
> 设计依据：[`docs/`](./docs/) 全套设计文档。
> 当前阶段：**P0 开发环境与基础设施搭建**（参见 [`docs/10-项目管理/02-开发任务计划.md`](./docs/10-项目管理/02-开发任务计划.md)）。

## 1. 仓库目录结构

```
bdc-platform/
├── bdc-bom/                     # BOM 版本统一（Java Platform）
├── bdc-common/                  # 二方库（9 个）
│   ├── core/  web/  security/  audit/  feign/  event/  db/  cache/  crypto/
├── bdc-gateway/                 # 网关（Spring Cloud Gateway）
├── bdc-services/                # 19 个业务微服务
│   ├── auth/  uac/  metadata/  mdm/  indicator/  tag/
│   ├── collect/  warehouse/  govern/  push/  service-api/
│   ├── market/  approval/  visual/  security/  audit/  notify/
│   └── scheduler-adapter/  file/
├── bdc-flink-jobs/              # Flink 作业（CDC、实时 ETL、实时 DQ）
├── bdc-datax-plugins/           # 定制 DataX Reader/Writer（达梦/金仓增强、国密写）
├── bdc-frontend/                # Vue 前端 Monorepo（pnpm workspace）
│   └── portal/  admin/  screen/  market/  designer/
├── bdc-deploy/                  # Docker Compose / 初始化 SQL / 部署脚本
└── docs/                        # 项目内设计文档（即设计文档中的 bdc-docs）
```

> **目录与设计的对应**：设计文档 `01-总体架构/03-微服务拆分与服务清单.md#6` 中描述的 `bdc-docs/`，在本仓中即根目录下的 `docs/`。两者等价，所有文档交叉引用均以 `docs/` 为准。

## 2. 技术主栈（仅列与代码骨架强相关项）

| 类别 | 选型 | 版本 |
| --- | --- | --- |
| 语言 | Java / TypeScript | JDK 17 / TS 5 |
| 后端框架 | Spring Boot + Spring Cloud Alibaba | 3.2.x / 2023.x |
| 构建 | Gradle Composite | 8.7 |
| 前端框架 | Vue 3 + Element Plus + Vite | 3.4 / 2 / 5 |
| 包管理 | pnpm | 9.x |
| 容器 | Docker + Compose v2 | 24 / v2 |

完整冻结清单见 [`docs/01-总体架构/02-技术选型.md`](./docs/01-总体架构/02-技术选型.md)。

## 3. 快速开始

> 本仓库当前为 **P0.1.2 阶段产出的目录骨架**，构建脚本、CI、Docker 镜像将在后续 P0.* 任务中陆续填充：
>
> - P0.2 本地开发机标准化
> - P0.3 Nexus / Harbor / base 镜像 / `bdc-bom`
> - P0.4 GitHub Actions CI / SonarQube / Trivy
> - P0.5 dev 期中间件（PG/CK/Kafka/Redis/MinIO/ES/Nacos/SkyWalking/DS/Flink + Mock CAS/LDAP）

```bash
# 后端（待 P0.3.4 BOM 与 Wrapper 就绪后）
./gradlew tasks                  # 列出所有任务
./gradlew :bdc-services:auth:bootRun

# 前端（待 P0.2 安装 pnpm 后）
cd bdc-frontend
pnpm install
pnpm --filter portal dev
```

## 4. 文档导航

请直接进入 [`docs/README.md`](./docs/README.md) 获取完整文档导航。
开发任务计划见 [`docs/10-项目管理/02-开发任务计划.md`](./docs/10-项目管理/02-开发任务计划.md)。

## 5. 贡献规范

| 项 | 文档 |
| --- | --- |
| 编码规范 | [`docs/08-开发规范/01-编码规范.md`](./docs/08-开发规范/01-编码规范.md) |
| Git / CI-CD 规范 | [`docs/08-开发规范/02-Git与CI-CD规范.md`](./docs/08-开发规范/02-Git与CI-CD规范.md) |
| 接口规范 | [`docs/05-接口设计/01-接口设计规范.md`](./docs/05-接口设计/01-接口设计规范.md) |

## 6. 许可

详见 [`LICENSE`](./LICENSE)。
