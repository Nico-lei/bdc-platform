# dev 环境部署清单

> P0.5 阶段落地。当前 P0.1.2 仅占位，下面列出**预期文件清单**与组件目标。

## 预期文件

```
bdc-deploy/dev/
├── docker-compose.dev.yml      # 一键拉起全部 dev 中间件
├── .env.dev                    # 端口、密码、数据卷路径
├── README-dev.md               # 本文件
├── pg/                         # PostgreSQL 15 单机 + 5 个物理库初始化
│   ├── init/
│   │   ├── 00-create-dbs.sql
│   │   ├── 01-app-schemas.sql  # 18 个 schema
│   │   ├── 02-flowable.sql
│   │   ├── 03-audit.sql
│   │   └── 04-dw.sql
├── ch/                         # ClickHouse 24 单机 + bdc_dw_olap 初始化
├── kafka/                      # KRaft 单节点
├── redis/                      # Redis 7
├── minio/                      # MinIO 单节点（4 桶预创建）
├── es/                         # Elasticsearch 8 单节点
├── nacos/                      # Nacos 单机 + dev namespace 导入
├── skywalking/                 # SkyWalking OAP + UI
├── observability/              # Prometheus + Grafana + Loki + Promtail
├── ds/                         # DolphinScheduler 单机
├── flink/                      # Flink Standalone Session
└── mock/                       # Mock CAS / Mock LDAP（开发期不依赖学校真实环境）
```

## 验收（P0.5 完成后）

```bash
cd bdc-deploy/dev
docker compose -f docker-compose.dev.yml up -d
docker compose -f docker-compose.dev.yml ps        # 全部 healthy
```

## 引用

- [`docs/10-项目管理/02-开发任务计划.md`](../../docs/10-项目管理/02-开发任务计划.md) § P0.5
- [`docs/09-部署运维/01-离线部署方案.md`](../../docs/09-部署运维/01-离线部署方案.md)
