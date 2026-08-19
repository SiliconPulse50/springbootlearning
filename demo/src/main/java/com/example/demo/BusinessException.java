package com.example.demo;

// 1. 继承 RuntimeException（运行时异常），这样事务会自动回滚，且不需要在方法上显式 throws
public class BusinessException extends RuntimeException {

    // 2. 定义业务状态码（可以传 code，也可以只传 msg，我们简化版先用 msg）
    private Integer code;

    // 3. 构造方法1：只传错误信息（默认 code 设置为 500）
    public BusinessException(String message) {
        super(message);
        this.code = 500;
    }

    // 4. 构造方法2：传错误信息 + 自定义状态码（比如 404 表示未找到）
    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    // 5. Getter 方便异常处理器获取 code
    public Integer getCode() {
        return code;
    }
}
