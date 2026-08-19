package com.example.demo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("登录请求参数")
public class LoginRequest {
    @ApiModelProperty(value = "用户名", required = true, example = "admin")
    private String username;

    @ApiModelProperty(value = "密码", required = true, example = "123456")
    private String password;

    // 必须要有 Getter 和 Setter（IDEA快捷键 Alt+Insert 生成）
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}