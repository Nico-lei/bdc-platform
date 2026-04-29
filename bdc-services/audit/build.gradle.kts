/*
 * bdc-audit · 操作日志、审计、登录、下载轨迹
 *
 * P0.1.2 占位：声明 application 形态，启动类与依赖将在 P5.2 阶段落地。
 */

plugins {
    `java`
    application
}

description = "bdc-audit : 操作日志、审计、登录、下载轨迹"

application {
    mainClass.set("com.bdc.audit.AuditApplication")
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
    systemProperty("server.port", "8116")
}