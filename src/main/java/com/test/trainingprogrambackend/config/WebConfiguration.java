package com.test.trainingprogrambackend.config;

import com.test.trainingprogrambackend.interceptor.TokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {
    @Value("${upload.path}")
    private String uploadPath;
    /*
      解决跨域请求
      @Param registry
     */
    @Autowired
    private TokenInterceptor tokenInterceptor;
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedHeaders("*")
                .allowedMethods("*")
                .allowedOriginPatterns("*")
                .allowCredentials(true)
                .exposedHeaders("Authorization")
                .maxAge(36000);

    }

    /*
     配置拦截器，每次请求都会执行拦截其中的犯法
      @param registry
     */
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        List<String> excludePath = new ArrayList<>();
//        excludePath.add("/student/registerByEmail");
//        excludePath.add("/student/registerByPhone");
//        excludePath.add("/student/loginByPhoneAndPassword");
//        excludePath.add("/student/loginByEmailAndPassword");
//        registry.addInterceptor(tokenInterceptor).addPathPatterns("/**")
//                .excludePathPatterns(excludePath);
//        WebMvcConfigurer.super.addInterceptors(registry);
//    }
    /*
        静态资源映射
        @Param registry
     */

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**").addResourceLocations(uploadPath);
    }
}
