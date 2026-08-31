package com.example.demo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "登录认证")
@RestController
public class LoginController {
@Autowired
private UserService userService;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
@ApiOperation("用户登录")
@PostMapping("/login")

    public Result<Map<String, String>> login(@RequestBody LoginRequest loginRequest) {
        User user=userService.findByUsername(loginRequest.getUsername());
        // 1. 模拟校验用户名密码（实际应该查数据库）
        if (user != null && passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            // 2. 生成令牌
            String token = JwtUtil.generateToken(user.getId(), user.getUsername());

            // 3. 封装返回给前端
            Map<String, String> data = new HashMap<>();
            data.put("token", token);
            return Result.success(data);
        } else {
            // 4. 账号密码错误，抛业务异常
            throw new BusinessException(401, "用户名或密码错误");
        }
    }
    /*
    🤔 为什么需要这个接口？不能直接在数据库里写 SQL 加密吗？
因为 BCrypt 加密需要计算随机盐（Salt），这个算法是 Java 代码实现的，靠纯 SQL 语句（比如 UPDATE user SET password = MD5('123456')）做不出来。所以我们必须写一个一次性的 Java 接口，让它跑一遍代码，把全表数据都刷新一遍。

⚠️ 重要提醒（安全须知）
这是一个“一次性工具”，跑完就要立即禁用（注释掉 @GetMapping 或直接删掉方法）。否则：

如果坏人发现了这个接口（比如他扫到了 /migrate-password），他可以反复调用，虽然不会把密码改回明文，但会浪费服务器性能（因为 BCrypt 加密很吃 CPU）。

更严重的是，如果代码逻辑被篡改，可能会有风险。
    //里临时添加一个接口（用完可以删掉）：
    @GetMapping("/migrate-password")
    public Result<String> migratePassword() {
        // 1. 查出所有用户
        List<User> users = userService.findAllUsers();
        int count = 0;
        for (User user : users) {
            String rawPassword = user.getPassword();
            // 2. 如果密码不是 BCrypt 格式（不以 $2a 开头），说明还是明文
            if (rawPassword != null && !rawPassword.startsWith("$2a")) {
                String encoded = passwordEncoder.encode(rawPassword);
                user.setPassword(encoded);
                userService.updateUser(user);  // 更新数据库
                count++;
            }
        }
        return Result.success("迁移完成，共处理 " + count + " 条记录");
    }*/
}