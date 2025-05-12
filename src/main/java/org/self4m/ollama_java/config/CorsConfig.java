package org.self4m.ollama_java.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer{
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")// /** 表示在后端允许匹配客户端发过来的任意请求
                .allowedHeaders("*")//请求带任意头都可以
                .allowedMethods("GET", "POST", "PUT", "DELETE")//任意请求方式都可以 get/post/put...
                .allowedOriginPatterns("*")//任意域都可以(任意请求地址或端口号)
                .allowCredentials(true)//请求可以携带会话相关信息(cookie/session)
                .maxAge(3600);//同一请求一小时内不再检测 直接放行
    }
}
