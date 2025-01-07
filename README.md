# OJ在线判题平台

## 项目介绍

用于在线评测编程题目代码的系统，能够根据用户提交的代码、出题人预先设置的题目输入和输出用例，进行编译代码、运行代码、判断代码运行结果是否正确。

## 实现核心

1. 权限校验

2. 代码沙箱（安全沙箱）

3. 判题规则

4. 任务调度

## 技术选型

### 后端

- Java Spring Boot 开发框架
- 存储层：MySQL 数据库 + Redis 缓存 + 阿里云 OSS 对象存储
- MyBatis-Plus 及 MyBatis X 自动生成

### 前端

- Vue 3
- Vue-CLI 脚手架
- Axios 请求库
- Arco Design 组件库
- 前端工程化：ESLint + Prettier + TypeScript
- 富文本编辑器
- ⭐️ OpenAPI 前端代码生成