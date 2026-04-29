# bdc-datax-plugins · 定制 DataX 插件

| 项 | 值 |
| --- | --- |
| 类型 | DataX Reader / Writer 插件包 |
| 部署 | 编译产物释放到 `${DATAX_HOME}/plugin/{reader,writer}/<plugin-name>/` |
| 落地阶段 | **P3.1**（与 `bdc-collect` 同步） |

## 插件清单（规划）

| 插件 | 类型 | 用途 |
| --- | --- | --- |
| `dameng-reader` / `dameng-writer` | Reader / Writer | 达梦数据库增强（CDC 时间戳、SCD2、列加密） |
| `kingbase-reader` / `kingbase-writer` | Reader / Writer | 金仓数据库增强 |
| `gm-encrypt-writer` | Writer 装饰器 | 落库前对 L3/L4 字段做 SM4 加密 |
| `minio-writer` | Writer | 直写 MinIO 桶（按分区写 Parquet/ORC） |

## 引用文档

- [`docs/03-功能模块详设/01-数据采集模块.md`](../docs/03-功能模块详设/01-数据采集模块.md)
- [`docs/06-安全与合规/03-脱敏与加密.md`](../docs/06-安全与合规/03-脱敏与加密.md)
- [`docs/10-项目管理/02-开发任务计划.md`](../docs/10-项目管理/02-开发任务计划.md) § P3.1.3
