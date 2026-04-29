/*
 * bdc-file · 文件上传下载 + MinIO 代理 + 水印嵌入
 *
 * P0.1.2 占位：声明 application 形态，启动类与依赖将在 P4.10 阶段落地。
 */

plugins {
    `java`
    application
}

description = "bdc-file : 文件上传下载 + MinIO 代理 + 水印嵌入"

application {
    mainClass.set("com.bdc.file.FileApplication")
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
    systemProperty("server.port", "8119")
}