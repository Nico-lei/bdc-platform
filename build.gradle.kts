/*
 * bdc-platform · 根 build 脚本
 *
 * 当前阶段：P0.1.2 仅落地骨架，所有"约定插件"（如 spring-boot-conventions）将在
 * P0.2 / P0.3.4 / P0.6 阶段通过 buildSrc 与 bdc-bom 落地。
 */

plugins {
    base
}

group = "com.bdc"
version = providers.gradleProperty("bdc.version").getOrElse("1.0.0-SNAPSHOT")

allprojects {
    group = rootProject.group
    version = rootProject.version
}

subprojects {
    // 占位：通用配置（编译参数、JaCoCo、Sonar、Spotless 等）将在
    // P0.6 测试与质量基线阶段统一注入。
    plugins.withId("java") {
        extensions.findByType<JavaPluginExtension>()?.apply {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
            withJavadocJar()
            withSourcesJar()
        }

        tasks.withType<JavaCompile>().configureEach {
            options.encoding = "UTF-8"
            options.compilerArgs.addAll(listOf("-parameters", "-Xlint:all"))
        }

        tasks.withType<Test>().configureEach {
            useJUnitPlatform()
            systemProperty("file.encoding", "UTF-8")
        }
    }
}

// 顶层便捷任务：列出全部子模块
tasks.register("listModules") {
    group = "help"
    description = "列出所有 Gradle 子模块（bdc-* 全景）"
    doLast {
        println("=== bdc-platform 子模块 ===")
        rootProject.subprojects
            .map { it.path }
            .sorted()
            .forEach { println("  $it") }
        println("总计：${rootProject.subprojects.size}")
    }
}
