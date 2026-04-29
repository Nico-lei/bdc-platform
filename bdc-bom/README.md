# bdc-bom · BOM 版本统一

| 项 | 值 |
| --- | --- |
| 类型 | Java Platform（Maven BOM） |
| 坐标 | `com.bdc:bdc-bom:1.0.0-SNAPSHOT` |
| 落地阶段 | **P0.3.4**（当前 P0.1.2 仅骨架） |

## 用途

供所有 `bdc-*` 子模块在 `dependencies` 块中以 `platform()` / `enforcedPlatform()` 引入：

```kotlin
dependencies {
    implementation(platform(project(":bdc-bom")))
    implementation("org.springframework.boot:spring-boot-starter-web")  // 不写版本号
}
```

## 涵盖范围（待 P0.3.4 冻结）

- Spring Boot 3.2.x / Spring Cloud 2023.x / Spring Cloud Alibaba 2023.x
- MyBatis-Plus、Druid、Flyway、Liquibase
- PostgreSQL JDBC、ClickHouse JDBC、达梦/金仓 JDBC
- Kafka Client、Flink、Flink CDC
- Redis（Lettuce / Jedis）、Caffeine
- Elasticsearch RestClient
- Bouncy Castle、国密 SM2/SM3/SM4
- SkyWalking Agent、Sentinel、Seata
- Junit 5、AssertJ、Mockito、Testcontainers、WireMock、ArchUnit

## 引用文档

- [`docs/01-总体架构/02-技术选型.md`](../docs/01-总体架构/02-技术选型.md)
- [`docs/10-项目管理/02-开发任务计划.md` § P0.3.4](../docs/10-项目管理/02-开发任务计划.md)
