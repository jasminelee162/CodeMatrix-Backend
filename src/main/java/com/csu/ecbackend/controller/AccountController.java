//package com.csu.ecbackend.controller;
//
//
//import com.alibaba.fastjson.JSONObject;
//import com.csu.ecbackend.bean.User;
//import com.csu.ecbackend.commom.CommonResponse;
//import com.csu.ecbackend.service.UserService;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//
//import javax.servlet.http.HttpSession;
//
//@Slf4j
//@Controller
//@CrossOrigin
//@RequestMapping("/account")
//@Api(value = "用户有关接口", tags = "用户")
//public class AccountController {
//
//      @Autowired
//      private UserService userService;
//
//      @PostMapping("/users/login")
//      @ResponseBody
//      @ApiOperation("登录")
//      public CommonResponse<User> login(@RequestParam String username, @RequestParam String password, HttpSession session) {
//            log.info("getAge");
////            password = DigestUtils.md5DigestAsHex(password.getBytes());//加密
//            CommonResponse<User> response = userService.getUser(username, password);
//            if (response.isSuccess()) {
//                  session.setAttribute("login_user", response.getData());
//            }
//            return response;
//      }
//
//
//      @PostMapping("/users/logout")
//      @ResponseBody
//      @ApiOperation("退出")
//      public CommonResponse<String> logout(HttpSession session) {
//            User login_user = (User) session.getAttribute("login_user");
//            if (login_user != null) {
//                  session.removeAttribute("login_user");
//                  return CommonResponse.createForSuccessMessage("退出登录成功");
//            }
//            return CommonResponse.createForError("用户未登录，不能退出");
//      }
//
//
//      @GetMapping("/users/info")
//      @ResponseBody
//      @ApiOperation("获得用户信息")
//      public CommonResponse<User> getUserinfo(HttpSession session) {
//
//            User login_user = (User) session.getAttribute("login_user");
//
//            return userService.getUser(login_user.getUsername());
//      }
//
//
//      @GetMapping("/users/check")
//      @ResponseBody
//      @ApiOperation("确认用户名是否存在")
//      CommonResponse checkUsername(@RequestParam String username, HttpSession session) {
//            if (userService.getUser(username).getStatus() != 0)
//                  return CommonResponse.createForSuccess("用户名可用");
//            else
//                  return CommonResponse.createForError("用户名已经存在");
//      }
//
//      @GetMapping("/users/record")
//      @ResponseBody
//      @ApiOperation("获取用户记录")
//      CommonResponse getUserRecord(@RequestParam String username) {
//            //User login_user = (User) session.getAttribute("login_user");
//            if (username != null) {
//                  System.out.println(username);
//                  //return userService.getUserRecord(login_user.getUsername());
//                  if (userService.getUserRecord(username) != null) {
//                        return userService.getUserRecord(username);
//                  } else {
//                        return CommonResponse.createForError("暂无搜索记录");
//                  }
//            } else {
//                  return CommonResponse.createForError("请先登录");
//            }
////            return userService.getUserRecord("hbbb");
//      }
//
//
//}
