/*
 * bdc-platform · Monorepo 根 settings
 *
 * 包含：
 *   - bdc-bom（Java Platform，统一依赖版本，由 P0.3.4 落地）
 *   - bdc-common 下 9 个二方库
 *   - bdc-gateway 网关
 *   - bdc-services 下 19 个业务服务
 *   - bdc-flink-jobs / bdc-datax-plugins
 *
 * 前端 bdc-frontend 由 pnpm workspace 单独管理，不进入 Gradle 构建。
 */

rootProject.name = "bdc-platform"

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        // P0.3.1 之后切换到内网 Nexus
        // maven("https://nexus.bdc.local/repository/gradle-plugins/")
    }
}

dependencyResolutionManagement {
    @Suppress("UnstableApiUsage")
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    @Suppress("UnstableApiUsage")
    repositories {
        mavenCentral()
        // P0.3.1 之后切换到内网 Nexus
        // maven("https://nexus.bdc.local/repository/maven-public/")
    }
}

// ====== bdc-bom ======
include(":bdc-bom")
project(":bdc-bom").projectDir = file("bdc-bom")

// ====== bdc-common（9 个二方库） ======
listOf(
    "core", "web", "security", "audit", "feign", "event", "db", "cache", "crypto"
).forEach { name ->
    include(":bdc-common:$name")
    project(":bdc-common:$name").projectDir = file("bdc-common/$name")
}

// ====== bdc-gateway ======
include(":bdc-gateway")
project(":bdc-gateway").projectDir = file("bdc-gateway")

// ====== bdc-services（19 个业务微服务） ======
listOf(
    // 平台服务（gateway 见上方）
    "auth", "uac",
    // 四大中心
    "metadata", "mdm", "indicator", "tag",
    // 八大模块
    "collect", "warehouse", "govern", "push", "service-api",
    "market", "approval", "visual",
    // 安全 / 审计 / 通知 / 调度 / 文件 支撑
    "security", "audit", "notify", "scheduler-adapter", "file"
).forEach { name ->
    include(":bdc-services:$name")
    project(":bdc-services:$name").projectDir = file("bdc-services/$name")
}

// ====== Flink 作业 / DataX 插件 ======
include(":bdc-flink-jobs")
project(":bdc-flink-jobs").projectDir = file("bdc-flink-jobs")

include(":bdc-datax-plugins")
project(":bdc-datax-plugins").projectDir = file("bdc-datax-plugins")
