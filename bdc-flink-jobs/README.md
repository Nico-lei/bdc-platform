# bdc-flink-jobs · Flink 作业

| 项 | 值 |
| --- | --- |
| 作用 | CDC 增量、实时 ETL（Kafka → DWD）、实时数据质量 |
| 提交方式 | Flink 1.18 Application Mode |
| 触发方 | DolphinScheduler / `bdc-scheduler-adapter` |
| 落地阶段 | **P3.4 / P3.8 / P4.x** |

## 作业清单（规划）

| 作业 | 来源 | 目标 | 阶段 |
| --- | --- | --- | --- |
| `cdc-mysql-to-ods` | MySQL Binlog | Kafka `ods.*` → PG `bdc_dw.ods_*` | P3.1 |
| `cdc-pg-to-ods` | PG WAL | Kafka `ods.*` → PG `bdc_dw.ods_*` | P3.1 |
| `realtime-dwd-student` | Kafka `ods.*` | PG `bdc_dw.dwd_student_*` | P3.5 |
| `realtime-dwd-teaching` | Kafka `ods.*` | PG `bdc_dw.dwd_teaching_*` | P3.5 |
| `realtime-dq-engine` | Kafka `ods.*` | Kafka `govern.quality` | P3.3 |

## 引用文档

- [`docs/03-功能模块详设/01-数据采集模块.md`](../docs/03-功能模块详设/01-数据采集模块.md)
- [`docs/07-任务编排/01-任务编排规范.md`](../docs/07-任务编排/01-任务编排规范.md)
- [`docs/10-项目管理/02-开发任务计划.md`](../docs/10-项目管理/02-开发任务计划.md) § P3.4 / P3.8
