package com.example.demo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Api(tags = "用户管理")//knife4j文档分组
@RestController//告诉spring我是处理http请求的Bean,这个类里的所有方法返回的数据都直接写成JSON
public class TestController {
//注入service
    private static final Logger logger=LoggerFactory.getLogger(TestController.class);

    @Autowired
    private UserService userService;//具体业务逻辑交给userservice
    // 2. 在方法里使用 logger.info() 替代 System.out.println()




//controller 只负责接待客人
//    @ApiOperation(value = "查询全部用户", notes = "返回所有用户列表")
//    @GetMapping("/users")
//    public Result<List<User>> getUsers() {
//
//        return Result.success(userService.findAllUsers());
//    }
// ✅ 保留这一个（带 @ApiOperation 的），把日志搬进来
@ApiOperation(value = "查询全部用户", notes = "返回所有用户列表")
@ApiImplicitParam(name = "Authorization", value = "登录接口返回的 token（不要带 Bearer 前缀）", required = false, dataType = "string", paramType = "header")
@GetMapping("/users")
public Result<List<User>> getUsers() {
    logger.info("========== 接收到查询全部用户请求 ==========");
    List<User> list = userService.findAllUsers();
    logger.info("查询到 {} 条用户数据", list.size());
    return Result.success(list);
}

    // ✅ 保留这一个（带 @ApiOperation 的），把日志搬进来
    @ApiOperation(value = "新增用户", notes = "传入用户名和手机号，返回添加成功信息")
    @ApiImplicitParam(name = "Authorization", value = "登录接口返回的 token（不要带 Bearer 前缀）", required = false, dataType = "string", paramType = "header")
    @PostMapping("/users")
    public Result<String> addUser(@Valid @RequestBody User user) {
        logger.info("========== 接收到新增用户请求：{} ==========", user.getUsername());
        userService.addUser(user);
        logger.info("用户 {} 新增成功", user.getUsername());
        return Result.success("添加成功");
    }
    @ApiOperation(value = "根据ID查询用户", notes = "路径传参，返回单个用户对象")
    @ApiImplicitParam(name = "Authorization", value = "登录接口返回的 token（不要带 Bearer 前缀）", required = false, dataType = "string", paramType = "header")
    @GetMapping("/user/{id}")
    public Result<User> getUserById(
            @ApiParam(value = "用户ID", required = true, example = "1")
            @PathVariable Long id) {//从URL路径里拿{id}的值
        return Result.success(userService.getUserById(id));
    }

    @ApiOperation(value = "分页查询用户", notes = "根据页码和每页条数获取用户列表")
    @ApiImplicitParam(name = "Authorization", value = "登录接口返回的 token（不要带 Bearer 前缀）", required = false, dataType = "string", paramType = "header")
    @GetMapping("/users/page")
    public Result<Page<User>> getUserPage(
            @ApiParam(value = "当前页码", defaultValue = "1")
            @RequestParam(defaultValue = "1") Integer current,
            @ApiParam(value = "每页条数", defaultValue = "5")
            @RequestParam(defaultValue = "5") Integer size) {
        Page<User> page = userService.getUsersByPage(current, size);
        return Result.success(page);
    }
//
//    @ApiOperation(value = "新增用户", notes = "传入用户名和手机号，返回添加成功信息")
//    @PostMapping("/users")
//    public Result<String> addUser(@Valid @RequestBody User user) {
//        //requestbody把前端发来的JSON转成Java对象
//        userService.addUser(user);
//        return Result.success("添加成功");
//    }

    @ApiOperation(value = "修改用户", notes = "根据ID修改用户名或手机号")
    @ApiImplicitParam(name = "Authorization", value = "登录接口返回的 token（不要带 Bearer 前缀）", required = false, dataType = "string", paramType = "header")
    @PutMapping("/user/{id}")
    public Result<String> updateUser(
            @ApiParam(value = "用户ID", required = true, example = "1")
            @PathVariable Long id,
            @Valid @RequestBody User user) {
        user.setId(id);//PUT前端通常只传username新名字，不会传id,因为id在URL里
        userService.updateUser(user);
        return Result.success("修改成功");
    }

    @ApiOperation(value = "删除用户", notes = "根据ID删除用户")
    @ApiImplicitParam(name = "Authorization", value = "登录接口返回的 token（不要带 Bearer 前缀）", required = false, dataType = "string", paramType = "header")
    @DeleteMapping("/user/{id}")
    public Result<String> deleteUser(
            @ApiParam(value = "用户ID", required = true, example = "1")
            @PathVariable Long id) {
        userService.deleteUser(id);
        return Result.success("删除成功");
    }
}