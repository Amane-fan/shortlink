# shortlink

一个用来练手和整理工程经验的短链接项目。

它不是单纯做一个“把长链接变短”的小工具，而是尽量把短链接系统里常见的几个问题都放进来了，比如分表、缓存、布隆过滤器、延迟任务、访问统计、网关路由和服务拆分。

## 这个项目有什么意义

短链接本身是个很小的业务，但很适合拿来做完整后端系统练习，因为它同时包含：

- 写操作：创建、修改、删除、分组管理
- 读操作：短链接跳转、高频查询
- 统计操作：PV、UV、UIP、地区、设备、浏览器等维度统计
- 中间件使用：MySQL、Redis、Nacos
- 架构拆分：网关、后台服务、核心业务服务、聚合模式

如果只是做 CRUD，这类项目没有太多意思；但把跳转、缓存、分片、统计链路放进来以后，它就比较接近一个真实可运行的小型系统了。

## 可以用来干什么

- 当成一个 Java 后端综合练手项目
- 用来熟悉 Spring Boot 3、Spring Cloud、MyBatis-Plus、ShardingSphere
- 用来练习 Redis 在业务里的几种常见用法
- 用来理解短链接系统的基本数据模型和访问链路
- 用来做简历里的个人项目原型，再按自己的方向继续扩展

如果继续往下做，这个项目也很适合继续加：

- 自定义短码
- 过期策略
- 黑白名单
- 更细的风控
- Docker Compose 部署
- 接口测试和压测脚本

## 这个项目目前做了什么

- 支持短链接创建、修改、分页查询、回收站
- 支持短链接跳转
- 支持基础访问统计和部分维度分析
- 使用 ShardingSphere 做分表
- 使用 Redis 做缓存、布隆过滤器、延迟队列和 Stream
- 保留了网关和服务注册发现，既可以拆分跑，也可以聚合跑

## 项目优势

- 业务简单，但覆盖的工程点比较多，适合系统性练习
- 不是纯单体，也不是过度设计，复杂度相对可控
- 数据库脚本完整，能直接初始化本地环境
- 默认提供聚合模式，本地跑通成本不高
- 模块边界比较清晰，后续想重构或者扩展都比较方便

## 模块说明

- `gateway`：统一入口，负责路由和部分鉴权逻辑
- `admin`：后台管理接口，处理用户、分组、回收站、控制台相关操作
- `project`：短链接核心业务，负责创建、跳转、统计等能力
- `aggregation`：把 `admin` 和 `project` 聚合到一个服务里，适合本地调试
- `console-vue`：前端控制台

## 技术栈

- Java 17
- Spring Boot 3
- Spring Cloud / OpenFeign / Gateway
- Nacos
- MyBatis-Plus
- ShardingSphere-JDBC
- MySQL 8
- Redis
- Vue 3 + Vite + Element Plus

## 运行依赖

本地需要准备：

- MySQL 8
- Redis
- Nacos
- JDK 17
- Maven
- Node.js 18+（如果需要启动前端）

默认本地配置：

- MySQL：`127.0.0.1:3306`
- 数据库：`link`
- 用户名：`root`
- 密码：`135790`
- Redis：`127.0.0.1:6379`
- Redis 密码：`135790`
- Nacos：`127.0.0.1:8848`

## 数据库初始化

建表和初始化数据脚本：

- `resources/database/link.sql`
- `resources/database/link-data.sql`

执行方式：

```sql
CREATE DATABASE link DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
```

```bash
mysql -h127.0.0.1 -P3306 -uroot -p135790 link < resources/database/link.sql
mysql -h127.0.0.1 -P3306 -uroot -p135790 link < resources/database/link-data.sql
```

## 怎么跑

本地推荐直接跑聚合模式，也就是：

- 启动 MySQL、Redis、Nacos
- 启动 `aggregation`
- 启动 `gateway`
- 需要页面的话再启动 `console-vue`

这样最省事，因为网关默认就是走聚合模式。

## 默认端口

- `gateway`: `8000`
- `project`: `8001`
- `admin`: `8002`
- `aggregation`: `8003`

## 适合什么人看

- 想找一个比普通管理系统更有意思的练手项目的人
- 想补一下中间件和微服务基础能力的人
- 想拿一个可以继续二次开发的短链接项目当底子的个人开发者

## 当前状态

这个仓库目前更偏向“可运行、可扩展、适合继续改”的个人项目，还不是完全生产化版本。

README 也尽量只保留真正有用的信息：

- 这个项目为什么值得做
- 它适合拿来练什么
- 它现在已经具备哪些能力
- 本地最少要准备什么
