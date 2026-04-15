//package com.csu.ecbackend.controller;
//
//
//import com.csu.ecbackend.bean.User;
//import com.csu.ecbackend.commom.CommonResponse;
//import com.csu.ecbackend.service.KeywordService;
//import com.csu.ecbackend.util.UtilKit;
//import com.csu.ecbackend.vo.FeedbackVO;
//import com.csu.ecbackend.vo.KeywordVO;
//import com.csu.ecbackend.vo.RecordVO;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//
//@Controller
//@CrossOrigin
//@RequestMapping("/keyword")
//@Api(value = "关键词业务有关接口", tags = "关键词")
//public class KeywordController {
//
//      @Autowired
//      KeywordService keywordService;
//
//      @GetMapping("/compete/{keyword}")
//      @ResponseBody
//      @ApiOperation("获得某关键词的竞争关键词")
//      public CommonResponse<KeywordVO> getCompetes(@PathVariable String keyword, @RequestParam int sort, HttpServletRequest request, HttpSession session) {
//            User login_user = (User) session.getAttribute("login_user");
//            String ipAddress = UtilKit.getIp(request);
//            if (login_user != null) {
//                  keywordService.insertRecord(login_user.getUserid(), keyword, ipAddress);
//            } else
//                  keywordService.insertRecord(0, keyword, ipAddress);
//            return keywordService.getCompetes(keyword,sort);
//      }
//
//
//      @GetMapping("/mediate/{keyword}")
//      @ResponseBody
//      @ApiOperation("获得某关键词的中介关键词")
//      public CommonResponse<KeywordVO> getMediates(@PathVariable String keyword) {
//            return keywordService.getMediate(keyword);
//      }
//
//      @GetMapping("/record/{keyword}")
//      @ResponseBody
//      @ApiOperation("获得某关键词的近七天的数据统计")
//      public CommonResponse<RecordVO> getRecordWeek(@PathVariable String keyword) {
//            return keywordService.getRecordWeek(keyword);
//      }
//
//      @GetMapping("/feedback/{keyword}/{compete}")
//      @ResponseBody
//      @ApiOperation("获得某关键词和竞争词的七天点赞数据")
//      public CommonResponse<FeedbackVO> getFeedbackWeek(@PathVariable String keyword, @PathVariable String compete) {
//            return keywordService.getFeedBackWeek(keyword, compete);
//      }
//
//
////      @GetMapping("/tiled/{keyword}")
////      @ResponseBody
////      @ApiOperation("获得某关键词的竞争关键词")
////      public CommonResponse<KeywordVO> getCompetes(@PathVariable String keyword) {
////            return null;
////      }
//}
