package com.example.demo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

// 1. 注解：告诉 Spring，这是一个"业务层"组件，启动时帮我创建它的实例
@Service
public class UserService {

    // 2. 把后厨工具（JdbcTemplate）要过来
   /* @Autowired
    private JdbcTemplate jdbcTemplate;
    // 3. 定义一个业务方法：专门负责"查询所有用户"
    // 这个方法里只写和业务相关的逻辑（在这里就是执行 SQL）
    public List<Map<String, Object>> findAllUsers() {
        String sql = "SELECT id, username FROM user";
        // 这里依然用 JdbcTemplate 去查，但它属于"数据访问"层面
        return jdbcTemplate.queryForList(sql);
    }*/
    public User findByUsername(String username){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        return userMapper.selectOne(wrapper);
    }
    public User addUser(User user) {
        // 1. 校验用户名是否已存在（去重逻辑）
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", user.getUsername());
        Long count = userMapper.selectCount(wrapper);
        if (count > 0) {
            throw new BusinessException(400, "用户名已存在");
        }

        // 2. 执行插入
        int rows = userMapper.insert(user);
        if (rows <= 0) {
            throw new BusinessException(500, "新增用户失败");
        }

        // 3. 返回插入后的用户对象（包含自动生成的 ID）
        return user;
    }
    // 1. 注入 Mapper，替代 JdbcTemplate
    @Autowired
    private UserMapper userMapper;
    // 2. 查询所有用户（这里就是“无 SQL”编程）
    public List<User> findAllUsers() {
        // 这行代码等价于：SELECT * FROM user
        // 并且 MyBatis-Plus 会自动把结果集封装成 List<User>
        return userMapper.selectList(null);
        //selectList(null) 中的 null 表示“无查询条件”，即查全部。
    }
//    public User getUserById(Long id){
//        return userMapper.selectById(id);
//    }
public User getUserById(Long id) {
    User user = userMapper.selectById(id);
    if (user == null) {
        // 抛出自定义业务异常，告诉前端：404（资源未找到）+ 友好提示
        throw new BusinessException(404, "用户不存在，请检查ID是否正确");
    }
    return user;
}
    // 新增：根据关键词模糊查询
    public List<User> searchUsers(String keyword) {
        // 1. 创建条件构造器（这就是 MyBatis-Plus 的神器）
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        // 2. 设置条件：username 字段 模糊匹配 (LIKE) keyword
        // 如果 keyword 为 null 或空，这里就不加条件，直接查全部
        if (keyword != null && !keyword.isEmpty()) {
            queryWrapper.like("username", keyword);
            //SELECT id, username, phone FROM user WHERE username LIKE '%小%'
        }

        // 3. 执行查询，Wrapper 会自动把条件拼进 SQL
        return userMapper.selectList(queryWrapper);
    }
    public User adduser(User user) {
        System.out.println("🔥 Controller 收到了请求：" + user.getUsername());
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", user.getUsername());
    //User exisitingUser=userMapper.selectOne(wrapper);
        Long count=userMapper.selectCount(wrapper);
    if(count>0){
        throw new BusinessException(400,"用户名已存在");
    }
       else{
        boolean result=userMapper.insert(user)>0;
        if(!result){
            throw new BusinessException(500,"新增用户失败");

        }else{

            return user;
        }
    }

    }
    public String updateUser (User user){
      User a=userMapper.selectById(user.getId());
      if(a==null){
          throw new BusinessException(404,"用户不存在");
      }else{
          int b=userMapper.updateById(user);
          if(b<=0)throw new BusinessException(500,"更新失败");
          return "更新成功";
      }
    }
    public void deleteUser(Long id){
        User c=userMapper.selectById(id);
        if(c==null)throw new BusinessException(404,"用户不存在");
       else{
           if(userMapper.deleteById(id)<=0){
               throw new BusinessException(500,"删除失败");
           }else{
               System.out.println("删除成功");
           }

        }

    }
    public Page<User> getUsersByPage (int current,int size){
        Page<User>page=new Page<>(current,size);
        userMapper.selectPage(page,null);
        return page;
    }

}
