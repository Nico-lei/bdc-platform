/*
 * bdc-flink-jobs · Flink 作业（CDC、实时 ETL、实时 DQ）
 *
 * P0.1.2 占位：实际依赖（flink-streaming-java、flink-cdc-pipeline、flink-connector-kafka 等）
 * 在 P3.4 / P3.8 阶段落地，提交方式为 Application Mode。
 */

plugins {
    `java`
    application
}

description = "bdc-flink-jobs : Flink 作业（CDC、实时 ETL、实时 DQ）"

application {
    mainClass.set("com.bdc.flink.jobs.FlinkJobLauncher")
}

dependencies {
    // P0.3.4 之后启用：
    // implementation(platform(project(":bdc-bom")))
    // implementation("org.apache.flink:flink-streaming-java:${project.findProperty("flinkVersion")}")
    // implementation("org.apache.flink:flink-clients:${project.findProperty("flinkVersion")}")
    // implementation("com.ververica:flink-cdc-pipeline-connector-mysql:${project.findProperty("flinkCdcVersion")}")
    // implementation("com.ververica:flink-cdc-pipeline-connector-postgres:${project.findProperty("flinkCdcVersion")}")
    // implementation("org.apache.flink:flink-connector-kafka:${project.findProperty("flinkVersion")}")

    testImplementation("org.junit.jupiter:junit-jupiter:5.10.2")
}
