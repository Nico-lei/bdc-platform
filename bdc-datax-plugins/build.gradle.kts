/*
 * bdc-datax-plugins · 定制 DataX Reader/Writer
 *
 * 主要扩展：
 *   - 达梦/金仓增强 Reader/Writer
 *   - 国密加密 Writer（落库前对 L3+ 字段做 SM4 加密）
 *   - HDFS / MinIO 优化 Writer
 *
 * P0.1.2 占位：实际代码由 P3.1 落地。
 */

plugins {
    `java-library`
}

description = "bdc-datax-plugins : 定制 DataX Reader/Writer（达梦/金仓增强、国密写）"

dependencies {
    // P0.3.4 之后启用：
    // compileOnly("com.alibaba.datax:datax-common:0.0.1-SNAPSHOT")
    // compileOnly("com.alibaba.datax:datax-core:0.0.1-SNAPSHOT")
    // implementation(project(":bdc-common:crypto"))

    testImplementation("org.junit.jupiter:junit-jupiter:6.0.3")
}
