package com.example.demo;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册拦截器
        registry.addInterceptor(new LoginInterceptor())
                // 拦截所有请求（/** 表示所有路径）
                .addPathPatterns("/**")
                // 放行登录接口和 Knife4j 文档（否则文档页面都访问不了）
                .excludePathPatterns("/login", "/doc.html", "/webjars/**", "/v2/api-docs", "/swagger-resources/**","/migrate-password",
                        "/**/*.html",            // 所有 HTML 页面
                        "/**/*.js",              // 所有 JS 文件
                        "/**/*.css"              // 所有 CSS 文件

                );
    }
}