# bdc-audit

| 项 | 值 |
| --- | --- |
| 服务名 | `bdc-audit` |
| 端口 | `8116` |
| 主数据库 | bdc_audit（独立库 + Elasticsearch） |
| Kafka Topic | `audit.*（消费）` |
| 包根 | `com.bdc.audit` |
| 启动类 | `com.bdc.audit.AuditApplication` |
| 落地阶段 | **P5.2**（当前 P0.1.2 仅骨架） |

## 职责

操作日志、审计、登录、下载轨迹

## 目录结构

```
bdc-services\audit/
├── api/                # OpenAPI 3 规范（service-audit.yaml）
├── sql/                # Flyway 脚本：V1.0.0__init.sql ...
├── docker/             # Dockerfile / 启动脚本
├── src/main/java/      # 源码：com.bdc.audit.*
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
- [ ] `./gradlew :bdc-services:audit:tasks` 能列出任务（待 P0.3.4）
- [ ] `./gradlew :bdc-services:audit:bootRun` 启动后 `/actuator/health` 200（待 P5.2）