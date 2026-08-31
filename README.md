后端项目学习📖  
## 目前进度  
JWT+拦截器的学习  
# ✅ 目前已经熟悉的技术栈与能力清单

## 1. 核心基础与架构（脱离了“新手村”）
- **三层架构**：Controller（控制层） ↔ Service（业务层） ↔ Mapper（数据层），做到了职责分离。
- **Maven 项目管理**：能自己添加依赖、解决 jar 包冲突（比如切换 Knife4j 版本、引入 JJWT）。
- **Spring Boot 核心**：理解 `@RestController`、`@Autowired` 依赖注入、`application.properties` 配置，不再惧怕启动报错。

## 2. 数据库操作
- **MyBatis-Plus**：熟练使用 `BaseMapper`，无需手写 SQL 即可完成增删改查。
- **复杂查询**：能用 `QueryWrapper` 构造条件（等值查询 `.eq`、模糊查询 `.like`、条件判断）。
- **分页能力**：配置了分页插件，能熟练返回 `Page` 对象给前端。

## 3. 接口设计与规范
- **RESTful API**：区分 GET、POST、PUT、DELETE 语义。
- **统一响应**：封装了 `Result<T>` 泛型类，所有接口返回固定格式的 JSON（`code`/`msg`/`data`）。
- **参数接收**：熟练使用 `@PathVariable`（路径参数）、`@RequestParam`（查询参数）、`@RequestBody`（JSON 参数）。

## 4. 安全与防御
- **全局异常处理**：使用 `@RestControllerAdvice` 统一拦截异常，把丑陋的堆栈信息转化成优雅的 JSON。
- **自定义业务异常**：能区分“业务错误（如 404）”和“系统错误（如 500）”。
- **参数校验**：使用 `@Valid` + `@NotNull` 拦截非法数据，保护了数据库完整性。
- **登录认证（JWT）**：理解了 Token 生成、解析原理，并成功跑通了登录接口。
- **拦截器**：能自己写 `Interceptor` 拦住未登录的请求。
- **密码加密（BCrypt）**：这是今天刚通关的！理解了“加盐哈希”，数据库里不再是明文密码。
