# bdc-metadata · 多阶段 Dockerfile（P0.1.2 占位，正式版由 P0.3 / P2.* 落地）
# 基础镜像：bdc/base-jdk17:1.0（在 P0.3.3 推送到内网 Harbor）

# ---- build stage ----
FROM gradle:8.7-jdk17 AS build
WORKDIR /workspace
COPY . .
RUN gradle :bdc-services:metadata:bootJar --no-daemon || echo "P0.1.2 占位：尚未生效"

# ---- runtime stage ----
FROM bdc/base-jdk17:1.0
LABEL bdc.service="bdc-metadata"
LABEL bdc.port="8103"

WORKDIR /app
# COPY --from=build /workspace/bdc-services\metadata/build/libs/*.jar /app/app.jar
EXPOSE 8103
HEALTHCHECK --interval=30s --timeout=5s --retries=3 \
  CMD curl -fs http://localhost:8103/actuator/health || exit 1
# ENTRYPOINT ["java","-jar","/app/app.jar"]
ENTRYPOINT ["sh","-c","echo 'P0.1.2 占位镜像，启动类待 P2.4 阶段落地' && sleep infinity"]