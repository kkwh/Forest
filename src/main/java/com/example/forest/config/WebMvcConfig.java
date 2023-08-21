package com.example.forest.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@Configuration
//public class WebMvcConfig implements WebMvcConfigurer {
//
//    private String connectPath = "/images/**";
//    //private String resourcePath = "file:///C:/Users/ITWILL/git/Forest/src/main/resources/static/img/board_profile/";
//    private String resourcePath = "file:///C:/Users/User/git/Forest/src/main/resources/static/img/board_profile/"; 
//    //private String resourcePath = "file:///Users/apple/git/Forest/src/main/resources/static/img/board_profile/";
//    
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) { 
//        registry.addResourceHandler(connectPath)
//                .addResourceLocations(resourcePath);
//    }
//    
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**") // 원하는 경로 설정
//                .allowedOrigins("http://localhost:8090", "http://1.1.1.19:8090", "http://192.168.20.14:8090") // 허용할 Origin 주소
//                .allowedMethods("GET", "POST", "PUT", "DELETE") // 허용할 HTTP 메서드
//                .allowedHeaders("*") // 허용할 헤더
//                .allowCredentials(true); // 쿠키 사용 허용 여부
//    }
//
//}