# bdc-visual

| 项 | 值 |
| --- | --- |
| 服务名 | `bdc-visual` |
| 端口 | `8114` |
| 主数据库 | bdc_app.visual |
| Kafka Topic | `—` |
| 包根 | `com.bdc.visual` |
| 启动类 | `com.bdc.visual.VisualApplication` |
| 落地阶段 | **P4.8**（当前 P0.1.2 仅骨架） |

## 职责

大屏/仪表板：基于 GoView 的设计器适配、数据集、组件

## 目录结构

```
bdc-services\visual/
├── api/                # OpenAPI 3 规范（service-visual.yaml）
├── sql/                # Flyway 脚本：V1.0.0__init.sql ...
├── docker/             # Dockerfile / 启动脚本
├── src/main/java/      # 源码：com.bdc.visual.*
├── src/main/resources/ # application.yml / bootstrap.yml / mapper xml
├── src/test/java/      # 单测 + 集成测试
└── build.gradle.kts
```

## 依赖与上下游

- 通用依赖：`bdc-bom` + `bdc-common-{core,web,security,audit,feign,event,db,cache,crypto}`（按需）
- 上下游详见 [`docs/01-总体架构/03-微服务拆分与服务清单.md`](../../docs/01-总体架构/03-微服务拆分与服务清单.md) § 3
- 详细职责见 [`docs/03-功能模块详设/`](../../docs/03-功能模块详设/) 与 [`docs/05-接口设计/02-接口清单.md`](../../docs/05-接口设计/02-接口清单.md)

## 验收（P0.1.2）

- [x] 目录骨架与设计文档一致
- [ ] `./gradlew :bdc-services:visual:tasks` 能列出任务（待 P0.3.4）
- [ ] `./gradlew :bdc-services:visual:bootRun` 启动后 `/actuator/health` 200（待 P4.8）