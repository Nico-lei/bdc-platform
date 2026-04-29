/*
 * bdc-common-web · MVC 配置、全局异常、跨域、TraceId、TimeZone
 *
 * P0.1.2 占位：仅声明 java-library 形态；实际依赖与代码由 P2.1 落地。
 */

plugins {
    `java-library`
    `maven-publish`
}

description = "bdc-common-web : MVC 配置、全局异常、跨域、TraceId、TimeZone"

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
            artifactId = "bdc-common-web"
        }
    }
}