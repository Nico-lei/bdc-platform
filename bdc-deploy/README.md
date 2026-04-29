# bdc-deploy · 部署与运维资产

> 承载 **Docker Compose 编排、初始化 SQL、部署脚本、Nacos 配置、监控大盘** 等部署期产物。
> 与设计文档 [`docs/09-部署运维/01-离线部署方案.md`](../docs/09-部署运维/01-离线部署方案.md) 严格对齐。

## 目录结构

```
bdc-deploy/
├── dev/         # 开发环境（P0.5 落地：单机 PG/CK/Kafka/Redis/MinIO/ES/Nacos/SkyWalking/Prometheus/Grafana/Loki/DS/Flink + Mock CAS/LDAP）
├── test/        # 测试环境（P3 阶段引入，规模略缩）
├── prod/        # 生产环境（与 docs/09-部署运维 / docs/01-总体架构/04 一致）
├── compose/     # 公共 compose 片段（按组件复用）
├── nacos/       # Nacos 命名空间初始化配置（dev/test/prod）
├── sql/         # 5 个物理库初始化脚本：bdc_app / bdc_flowable / bdc_audit / bdc_dw / bdc_dw_olap
└── scripts/     # 自动化脚本：00-check-env / 10-os / 20-container / 30-harbor / 40-images / 60-infra / 70-platform / 80-post-check / rollback
```

## 5 个物理库（与 `docs/02-数据架构/02-数据库分库与表结构规范.md` 一致）

| 库 | 类型 | 用途 |
| --- | --- | --- |
| `bdc_app` | PostgreSQL | 18 个业务服务的 Schema 隔离库 |
| `bdc_flowable` | PostgreSQL | 审批引擎专用（Flowable 官方表 + 业务映射） |
| `bdc_audit` | PostgreSQL | 审计只追加库（独立 + Hash 链） |
| `bdc_dw` | PostgreSQL | 数仓 ODS / DWD / DIM 物理库 |
| `bdc_dw_olap` | ClickHouse | 数仓 DWS / ADS 物理库 |

## 落地阶段

- **P0.5**：dev 环境 compose（缩水版，组件齐全）
- **P3.4**：test 环境 + DolphinScheduler / Flink 集群
- **P7\~P8**：prod 环境正式接入（参见 `docs/09-部署运维/01-离线部署方案.md`）

## 引用文档

- [`docs/01-总体架构/04-部署架构与网络设计.md`](../docs/01-总体架构/04-部署架构与网络设计.md)
- [`docs/09-部署运维/01-离线部署方案.md`](../docs/09-部署运维/01-离线部署方案.md)
- [`docs/09-部署运维/02-监控告警设计.md`](../docs/09-部署运维/02-监控告警设计.md)
- [`docs/09-部署运维/03-备份恢复方案.md`](../docs/09-部署运维/03-备份恢复方案.md)
- [`docs/10-项目管理/02-开发任务计划.md`](../docs/10-项目管理/02-开发任务计划.md) § P0.5 / P3 / P7
