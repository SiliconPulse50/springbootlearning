package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// 1. 注解：告诉 Spring，这是一个“全局拦截器”，专门处理 Controller 层抛出的异常
@RestControllerAdvice
public class GlobalExceptionHandler {
    // 1. 【优先】处理业务异常（用户操作失误、数据不存在等）

    private static final Logger logger=LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @ExceptionHandler(BusinessException.class)
    public Result<Void> handleBusinessException(BusinessException e) {
        // 这里不再打印堆栈（业务异常是预期的，不需要打印满屏红色吓自己）
        // 如果需要，可以只打印简单信息：System.out.println("业务异常：" + e.getMessage());

        // 如果异常里带了自定义 code，就用它；否则默认为 500
//        Integer code = e.getCode() != null ? e.getCode() : 500;
       // return new Result<>(code, e.getMessage(), null);
        // 等价于 Result.error(e.getMessage())，但这里我们保留code灵活性
        return new Result<>(e.getCode(), e.getMessage(), null);
    }
    // 2. 【新增】处理参数校验失败异常
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<Void> handleValidationException(MethodArgumentNotValidException e) {
        // 从异常中提取第一条错误信息（比如"用户名不能为空"）
        String message = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        //e（异常） → 2. BindingResult（校验结果包） → 3. List<Error>（错误列表） → 4. 第一个错误 → 5. 它的 message（提示语）。
        return new Result<>(400, message, null);
    }
    // 2. 注解：指定要拦截哪种异常。这里写了 Exception.class，表示拦截所有异常
    @ExceptionHandler(Exception.class)
    public Result<Void> handleException(Exception e) {
        // 3. 打印堆栈信息到控制台（方便我们开发时看具体哪行报错）
        //e.printStackTrace();
       logger.error("系统发生未捕获异常:",e);
        // 4. 关键！把异常信息塞进 Result 的 msg 字段，返回给前端
        // 注意：生产环境通常不会把具体错误信息直接给前端（怕泄露细节），
        // 但初学时我们先用“系统异常：xxx”方便调试
        return Result.error("服务器网络繁忙，请稍后再试");
    }
}