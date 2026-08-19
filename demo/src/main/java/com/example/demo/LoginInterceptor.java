package com.example.demo;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1. 从请求头中获取 token（前端必须把 token 放在 Header 的 Authorization 里）
        String token = request.getHeader("Authorization");

        // 2. 如果没有 token 或者 token 无效
        if (token == null || !JwtUtil.validateToken(token)) {
            // 返回 401 状态码（未授权）
            response.setStatus(401);
            // 抛出自定义异常，由全局异常处理器统一返回 JSON
            throw new BusinessException(401, "请先登录");
        }

        // 3. 如果 token 有效，放行（让请求继续往下走，进入 Controller）
        return true;
    }
}