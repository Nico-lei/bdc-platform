# bdc-services · 19 个业务微服务

> 本目录承载除网关外的 **19 个业务微服务**（与 `bdc-gateway` 合计 20 个）。
> 完整服务清单与职责见 [`../docs/01-总体架构/03-微服务拆分与服务清单.md`](../docs/01-总体架构/03-微服务拆分与服务清单.md)。

## 服务索引

| 分类 | 服务 |
| --- | --- |
| 平台服务 | [`auth`](./auth/) ·[`uac`](./uac/) |
| 四大中心 | [`metadata`](./metadata/) · [`mdm`](./mdm/) · [`indicator`](./indicator/) · [`tag`](./tag/) |
| 八大模块 | [`collect`](./collect/) · [`warehouse`](./warehouse/) · [`govern`](./govern/) · [`push`](./push/) · [`service-api`](./service-api/) · [`market`](./market/) · [`approval`](./approval/) · [`visual`](./visual/) |
| 安全/审计/通知 | [`security`](./security/) · [`audit`](./audit/) · [`notify`](./notify/) |
| 调度/文件 | [`scheduler-adapter`](./scheduler-adapter/) · [`file`](./file/) |

## 服务交付清单（每个微服务）

参见 [`../docs/01-总体架构/03-微服务拆分与服务清单.md`](../docs/01-总体架构/03-微服务拆分与服务清单.md) § 7：

1. Java 源码 + Gradle 构建脚本
2. Dockerfile（多阶段；基于 `bdc/base-jdk17`）
3. Docker Compose 部署文件 + `.env.prod` / `.env.test`
4. 初始化 SQL（Flyway）
5. OpenAPI 3 规范文件
6. 单元测试覆盖 ≥ 60%；核心模块整体 ≥ 70%（与 `08-开发规范` 对齐）
7. README
8. 部署清单（CPU/内存/磁盘/副本数）