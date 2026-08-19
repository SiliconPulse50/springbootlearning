package com.example.demo;

// 统一响应结果类（泛型，支持任何类型的数据）
public class Result<T> {
    private Integer code;   // 状态码
    private String msg;     // 提示信息
    private T data;         // 数据（泛型，可以是 User、List<User> 等）

    // 1. 无参构造（给框架用的）
    public Result() {}

    // 2. 有参构造（方便自己 new）
    public Result(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    // 3. 静态方法：成功（带数据）
    public static <T> Result<T> success(T data) {
        return new Result<>(200, "success", data);
    }

    // 4. 静态方法：成功（不带数据，比如删除接口）
    public static <T> Result<T> success() {
        return new Result<>(200, "success", null);
    }

    // 5. 静态方法：失败（带错误信息）
    public static <T> Result<T> error(String msg) {
        return new Result<>(500, msg, null);
    }

    // 6. Getter 和 Setter（一定要有，否则 Jackson 无法转成 JSON）
    public Integer getCode() { return code; }
    public void setCode(Integer code) { this.code = code; }
    public String getMsg() { return msg; }
    public void setMsg(String msg) { this.msg = msg; }
    public T getData() { return data; }
    public void setData(T data) { this.data = data; }
}