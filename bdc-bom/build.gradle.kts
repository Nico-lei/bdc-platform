/*
 * bdc-bom · BOM 版本统一
 *
 * P0.1.2 占位：仅保留 java-platform 形态，正式 BOM 内容由 P0.3.4 一次性冻结
 * （Spring Boot 3.2.x、Spring Cloud Alibaba、MyBatis-Plus、ClickHouse JDBC、
 *  Flink、Flink CDC、国密包、SkyWalking Agent 等版本）。
 */

plugins {
    `java-platform`
    `maven-publish`
}

description = "bdc-platform BOM：统一所有子模块依赖版本"

javaPlatform {
    allowDependencies()
}

dependencies {
    // 占位：示例项（后续 P0.3.4 落地完整清单）
    constraints {
        // api("org.springframework.boot:spring-boot-dependencies:${project.findProperty("springBootVersion")}")
        // api("com.alibaba.cloud:spring-cloud-alibaba-dependencies:${project.findProperty("springCloudAlibabaVersion")}")
    }
}

publishing {
    publications {
        create<MavenPublication>("bom") {
            from(components["javaPlatform"])
            artifactId = "bdc-bom"
        }
    }
}
