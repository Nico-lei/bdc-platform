# 初始化 SQL · 5 个物理库 + 各 Schema

> 与 `docs/02-数据架构/02-数据库分库与表结构规范.md`、`docs/04-数据库详细设计/01-核心表结构清单.md`、
> `docs/09-部署运维/01-离线部署方案.md#4.6` 严格保持一致。
>
> P0.1.2 仅占位，正式 SQL 由 P1.2.2（DDL 详设）+ 各模块 Flyway 脚本 共同落地。

## 预期目录

```
bdc-deploy/sql/
├── 00-create-dbs.sql                创建 5 个物理库 + 各服务专用账号 + 权限
├── 01-app/                          bdc_app（PG）下 18 个业务 Schema 初始化
│   ├── auth/V1.0.0__init.sql
│   ├── uac/V1.0.0__init.sql
│   ├── metadata/V1.0.0__init.sql
│   ├── ...
├── 01-flowable/                     bdc_flowable（PG）：Flowable 官方建表 + 业务映射表
│   └── V1.0.0__init.sql
├── 01-audit/                        bdc_audit（PG）：只追加表 + 哈希链触发器
│   └── V1.0.0__init.sql
├── 02-dw/                           bdc_dw（PG）：ODS / DWD / DIM Schema 与基础表
│   ├── V1.0.0__schemas.sql
│   ├── ods/...
│   ├── dwd/...
│   └── dim/...
├── 03-ck/                           bdc_dw_olap（ClickHouse）：DWS / ADS 表 + 物化视图
│   └── init.sql
└── 99-seed/                         种子数据（字典、国标、行政区划、字体）
```

## 5 个物理库

| 库名 | DBMS | 用途 |
| --- | --- | --- |
| `bdc_app` | PostgreSQL 15 | 18 业务服务 Schema |
| `bdc_flowable` | PostgreSQL 15 | 审批 |
| `bdc_audit` | PostgreSQL 15 | 审计 |
| `bdc_dw` | PostgreSQL 15 | 数仓 ODS / DWD / DIM |
| `bdc_dw_olap` | ClickHouse 24 | 数仓 DWS / ADS |

> 各微服务的 Schema 命名直接复用服务短名（去 `bdc-` 前缀）：
> 例如 `bdc-uac` → `bdc_app.uac`、`bdc-service-api` → `bdc_app.dataservice`。
