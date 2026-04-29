/*
 * bdc-common-crypto · 国密 SM2/SM3/SM4 工具、@Encrypt 注解、KMS Client
 *
 * P0.1.2 占位：仅声明 java-library 形态；实际依赖与代码由 P2.1 落地。
 */

plugins {
    `java-library`
    `maven-publish`
}

description = "bdc-common-crypto : 国密 SM2/SM3/SM4 工具、@Encrypt 注解、KMS Client"

dependencies {
    // P0.3.4 之后启用：
    // api(platform(project(":bdc-bom")))
    // implementation("org.springframework.boot:spring-boot-starter")

    testImplementation("org.junit.jupiter:junit-jupiter:5.10.2")
}

publishing {
    publications {
        create<MavenPublication>("library") {
            from(components["java"])
            artifactId = "bdc-common-crypto"
        }
    }
}