//package com.csu.ecbackend.interceptor;
//
//import com.csu.ecbackend.commom.CommonResponse;
//import com.csu.ecbackend.service.UserService;
//import com.csu.ecbackend.util.UtilKit;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.servlet.HandlerInterceptor;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.List;
//import java.util.Map;
//
//@Slf4j
//public class SearchTimeInterceptor implements HandlerInterceptor {
//
//      @Autowired
//      UserService userService;
//
//      @Override
//      public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
//
//            log.info("before interceptor");
//
//            String username = request.getParameter("username");
////            String username = "gbl";
//            String ip = UtilKit.getIp(request);
//            CommonResponse<String> check = userService.getUserRecordToday(username, ip);
//            //log.info(check;
//            if (!check.isSuccess())
//                  response.setStatus(403);
//            return check.isSuccess();
//      }
//
//}
