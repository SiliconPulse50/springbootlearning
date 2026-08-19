后端项目学习📖  
##目前进度  
JWT+拦截器的学习  
未来进度  
一些改进  
登录查数据库：在 UserService 加 findByUsername，改 LoginController 用真实用户（连 id 也真实）——照着第 4 节的提示写。
支持 Bearer 前缀：改 LoginInterceptor，if (token.startsWith("Bearer ")) token = token.substring(7);，前端就能规范地传 Authorization: Bearer xxx 了。
密码加密：加 spring-security-crypto 依赖，注册时 BCrypt 加密存库，登录时 matches() 比较。
按角色授权：登录时往 token 里塞 role（如 claim("role","admin")），拦截器里 parseToken 后取出来判断"这个接口 admin 才能调"。
后续学习  

未完待续
