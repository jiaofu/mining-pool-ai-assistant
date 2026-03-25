package com.binance.pool.config;

import com.binance.pool.interceptor.ApiInterceptorImpl;
import com.binance.pool.interceptor.UserIdInterceptorImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Slf4j
@Configuration
public class SpringMvcConfig implements WebMvcConfigurer {
    @Bean
    public ApiInterceptorImpl loginInterceptor() {
        return new ApiInterceptorImpl();
    }
    @Bean
    public UserIdInterceptorImpl userIdInterceptor() {
        return new UserIdInterceptorImpl();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册拦截器
        InterceptorRegistration ir = registry.addInterceptor(loginInterceptor());

        // 配置拦截的路径
        ir.addPathPatterns("/**");
        // 配置不拦截的路径
        ir.excludePathPatterns("/**.html");
        ir.excludePathPatterns("/**.*");
        ir.excludePathPatterns("/api/ai/**");
        ir.excludePathPatterns("/static/**");

        // 还可以在这里注册其它的拦截器
        registry.addInterceptor(userIdInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/**.html")
                .excludePathPatterns("/**.*")
                .excludePathPatterns("/api/ai/**")
                .excludePathPatterns("/static/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 静态资源映射 - AI 对话界面
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
    }
}
