package com.example.demo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Api(tags = "登录认证")
@RestController
public class LoginController {

    @ApiOperation("用户登录")
    @PostMapping("/login")
    public Result<Map<String, String>> login(@RequestBody LoginRequest loginRequest) {
        // 1. 模拟校验用户名密码（实际应该查数据库）
        if ("admin".equals(loginRequest.getUsername()) && "123456".equals(loginRequest.getPassword())) {
            // 2. 生成令牌
            String token = JwtUtil.generateToken(1L, "admin");

            // 3. 封装返回给前端
            Map<String, String> data = new HashMap<>();
            data.put("token", token);
            return Result.success(data);
        } else {
            // 4. 账号密码错误，抛业务异常
            throw new BusinessException(401, "用户名或密码错误");
        }
    }
}