# 部署脚本（占位）

> 正式脚本由 P0.5（dev）/ P7（试运行）/ P8（上线）阶段落地，命名与 [`docs/09-部署运维/01-离线部署方案.md`](../../docs/09-部署运维/01-离线部署方案.md) § 2 一致：

```
00-check-env.sh         硬件 / 网络 / 内核参数检查
10-install-os-deps.sh   离线安装 OS 基础包
20-install-container.sh Docker 24 + containerd
30-install-harbor.sh    Harbor 私仓初始化
40-import-images.sh     批量导入镜像 tar
60-install-infra.sh     PG / CK / Kafka / MinIO / Redis / Elasticsearch
70-install-platform.sh  20 个微服务
80-post-check.sh        健康检查与最小业务流程验证
nacos-import.py         Nacos 配置批量导入
rollback/               回滚脚本
```
