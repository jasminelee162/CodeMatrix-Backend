//package com.csu.ecbackend.controller;
//
//import com.csu.ecbackend.bean.User;
//import com.csu.ecbackend.bean.UserRecord;
//import com.csu.ecbackend.commom.CommonResponse;
//import com.csu.ecbackend.service.RankingListService;
//import com.csu.ecbackend.vo.RankingListVO;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import javax.servlet.http.HttpSession;
//import java.util.List;
//import java.util.Map;
//
//@Controller
//@CrossOrigin
//@RequestMapping("/rankingList")
//@Api(value = "排行榜接口", tags = "排行榜")
//public class RankingListController {
//
//    @Autowired
//    RankingListService rankingListService;
//
//    @GetMapping("/rankList")
//    @ResponseBody
//    @ApiOperation("获取排行榜")
//    public CommonResponse<List<RankingListVO>> getRankingList(){
//        System.out.println(rankingListService.getRankingList());
//        return rankingListService.getRankingList();
//    }
//}
