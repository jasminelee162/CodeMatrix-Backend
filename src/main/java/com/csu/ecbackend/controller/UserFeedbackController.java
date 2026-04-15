//package com.csu.ecbackend.controller;
//
//import com.csu.ecbackend.commom.CommonResponse;
//import com.csu.ecbackend.service.UserFeedbackService;
//import com.csu.ecbackend.vo.RankingListVO;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//
//import javax.servlet.http.HttpSession;
//import java.util.List;
//
//@Controller
//@CrossOrigin
//@RequestMapping("/userFeedback")
//@Api(value = "用户反馈接口", tags = "用户反馈")
//public class UserFeedbackController {
//
//    @Autowired
//    UserFeedbackService userFeedbackService;
//
//    @PostMapping("/comment")
//    @ResponseBody
//    @ApiOperation("点赞or点踩")
//    public CommonResponse<String> userFeedback(@RequestParam String username,@RequestParam String keyword,@RequestParam String competeword,@RequestParam int evalute,HttpSession session){
////        String username = (String) session.getAttribute("username");
////        String keyword = (String) session.getAttribute("keyword");
////        String competeword = (String) session.getAttribute("competeword");
////        int evalute = (int) session.getAttribute("evalute");
//        userFeedbackService.updateFeedback(username,keyword,competeword,evalute);
//        return CommonResponse.createForSuccess("用户评价成功");
//    }
//
//    @PostMapping("/action")
//    @ResponseBody
//    @ApiOperation("用户行为捕捉")
//    public CommonResponse<String> userAction(HttpSession session){
////        String username = (String) session.getAttribute("username");
////        String keyword = (String) session.getAttribute("keyword");
////        String competeword = (String) session.getAttribute("competeword");
////        int evalute = (int) session.getAttribute("evalute");
//        String username="gbl";
//        String keyword= "宝马";
//        String competeword="保时捷";
//        int ranking =1;
//        int time =20;
//        userFeedbackService.userAction(username,keyword,competeword,ranking,time);
//        return CommonResponse.createForSuccess("捕获用户行为成功");
//    }
//}
