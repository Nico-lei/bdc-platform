/*
 * bdc-warehouse · 仓库元数据、分层管理、非结构化资产
 *
 * P0.1.2 占位：声明 application 形态，启动类与依赖将在 P3.2 阶段落地。
 */

plugins {
    `java`
    application
}

description = "bdc-warehouse : 仓库元数据、分层管理、非结构化资产"

application {
    mainClass.set("com.bdc.warehouse.WarehouseApplication")
}

dependencies {
    // P0.3.4 之后启用：
    // implementation(platform(project(":bdc-bom")))
    // implementation(project(":bdc-common:core"))
    // implementation(project(":bdc-common:web"))
    // implementation(project(":bdc-common:security"))
    // implementation(project(":bdc-common:audit"))
    // implementation("org.springframework.boot:spring-boot-starter-web")
    // implementation("org.springframework.boot:spring-boot-starter-actuator")

    testImplementation("org.junit.jupiter:junit-jupiter:5.10.2")
}

tasks.named<JavaExec>("run") {
    systemProperty("server.port", "8108")
}