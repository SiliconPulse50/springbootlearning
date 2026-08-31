package com.example.demo;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

// 1. 注解：指定对应数据库中的 user 表
@TableName("user")
public class User {
    // 2. 注解：标识这是主键，且是自增类型
    @TableId(type = IdType.AUTO)

    private Long id;

@NotNull(message="用户名不能为空")
@Size(min=2,max=10,message="用户名长度必须在2-10之间")
private String username;
@NotNull(message="手机号不能为空")
@Pattern(regexp="^1[3-9]\\d{9}$", message = "手机号格式不正确")
//意思是“1开头，第二位是3-9，后面跟9个数字”，总共11位。
private String phone;
private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // 无参构造（必须要有）
    public User() {}

    // 有参构造（方便测试）


    public User(Long id, String username, String phone, String password) {
        this.id = id;
        this.username = username;
        this.phone = phone;
        this.password = password;
    }

    // 右键 -> Generate -> Getter and Setter -> 全选生成
    // 或者直接复制下面这半段（如果不会生成，先手动补上）
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
}