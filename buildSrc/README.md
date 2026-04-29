# buildSrc · Gradle 约定插件

> 本目录将在 **P0.3.4 / P0.6** 阶段集中落地以下约定插件，供子模块以一行 `plugins {}` 引用：
>
> - `bdc.java-conventions`：JDK 17、UTF-8、JaCoCo、Spotless、Sonar 接入
> - `bdc.spring-boot-conventions`：Spring Boot 3.2 启动约定 + Actuator + SkyWalking Agent
> - `bdc-common-deps`：BOM 引入、二方库默认依赖
> - `bdc.docker-conventions`：多阶段 Docker 构建（基于 `bdc/base-jdk17`）
> - `bdc.test-conventions`：Testcontainers 模板、JUnit 5、AssertJ、Mockito、覆盖率门槛
>
> 当前 P0.1.2 仅占位，避免 `settings.gradle.kts` 里 plugin 引用失败。
