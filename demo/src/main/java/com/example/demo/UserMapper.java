package com.example.demo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

// 1. @Mapper 注解：告诉 Spring，这是一个数据库映射器，启动时生成代理对象
@Mapper
public interface UserMapper extends BaseMapper<User> {
    // 注意！这里什么都不用写！
    // 因为继承了 BaseMapper，它已经内置了：
    // insert(), deleteById(), updateById(), selectList(), selectById() 等方法。
}