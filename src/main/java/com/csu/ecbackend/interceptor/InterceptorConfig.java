//package com.csu.ecbackend.interceptor;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class InterceptorConfig implements WebMvcConfigurer {
//
//    @Bean
//    public SearchTimeInterceptor searchTimeInterceptor(){
//        return new SearchTimeInterceptor();
//    }
//
//    @Bean
//    public FeedbackInterceptor feedbackInterceptor(){
//        return  new FeedbackInterceptor();
//    }
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        // 只拦截 age 的请求
//        //registry.addInterceptor(new SearchTimeInterceptor()).addPathPatterns("/account/users/login");
//        registry.addInterceptor(searchTimeInterceptor())
//                .addPathPatterns("/keyword/**");
//
//        registry.addInterceptor(feedbackInterceptor())
//                .addPathPatterns("/userFeedback/comment");
//    }
//
//}
