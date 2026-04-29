# bdc-common · 平台二方库

> 平台共享基础库，所有微服务必须依赖。9 个子模块按职责拆分，**无业务逻辑、不引入业务表**。
> 落地阶段：**P2.1**（W6\~W7）。当前 P0.1.2 仅骨架。

## 子模块清单

| 子模块 | 包名 | 职责 | 关键产出 |
| --- | --- | --- | --- |
| `core` | `com.bdc.common.core` | 通用异常、返回体 `R<T>`、分页 `PageR<T>`、状态枚举、雪花 ID | `BdcException`、`ErrorCode`、`R`、`PageR`、`SnowflakeId` |
| `web` | `com.bdc.common.web` | MVC 配置、全局异常、跨域、TraceId、TimeZone | `GlobalExceptionHandler`、`TraceFilter` |
| `security` | `com.bdc.common.security` | 权限切面 `@RequiresPermission`、`@DataPermission`、`UserContext` | 权限注解 + 切面 |
| `audit` | `com.bdc.common.audit` | `@Audit` 注解 + 异步 Kafka 发送 | `AuditAspect`、`AuditEventProducer` |
| `feign` | `com.bdc.common.feign` | OpenFeign 配置、Token 透传、国密签名拦截器、Sentinel 集成 | `FeignAutoConfiguration` |
| `event` | `com.bdc.common.event` | Kafka 事件结构 `BdcEvent<T>`、生产/消费封装 | `BdcEvent`、`EventBus` |
| `db` | `com.bdc.common.db` | 多数据源、动态 `search_path`、MyBatis-Plus 配置、慢日志、Flyway | `DynamicDataSource`、`SearchPathInterceptor` |
| `cache` | `com.bdc.common.cache` | Redis 封装、缓存注解扩展（支持加密缓存）、限流计数器 | `BdcRedisTemplate`、`@RateLimit` |
| `crypto` | `com.bdc.common.crypto` | 国密 SM2/SM3/SM4 工具类、`@Encrypt` 注解、KMS Client（HSM 真包 + 软兜底） | `SmUtils`、`KmsClient`、`EncryptAspect` |

## 设计与验收

- [`docs/01-总体架构/03-微服务拆分与服务清单.md`](../docs/01-总体架构/03-微服务拆分与服务清单.md) § 5
- [`docs/10-项目管理/02-开发任务计划.md`](../docs/10-项目管理/02-开发任务计划.md) § P2.1
- 单测覆盖率门槛：**≥ 80%**（基础库标准更高）
