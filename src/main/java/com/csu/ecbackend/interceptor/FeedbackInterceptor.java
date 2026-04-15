//package com.csu.ecbackend.interceptor;
//
//import com.csu.ecbackend.commom.CommonResponse;
//import com.csu.ecbackend.persistence.UserFeedbackDao;
//import com.csu.ecbackend.service.UserFeedbackService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.servlet.HandlerInterceptor;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//@Slf4j
//public class FeedbackInterceptor implements HandlerInterceptor {
//
//    @Autowired
//    UserFeedbackService userFeedbackService;
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
//
//        log.info("before interceptor");
//
//        String username = request.getParameter("username");
////        String username ="gbl";
//        CommonResponse<String> check= userFeedbackService.getUserFeedback3Min(username);
//        //log.info(check;
//        if (!check.isSuccess())
//            response.setStatus(403);
//        return check.isSuccess();
//    }
//}
