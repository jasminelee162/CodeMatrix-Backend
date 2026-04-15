//package com.csu.ecbackend.service.impl;
//
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.csu.ecbackend.bean.UserRecord;
//import com.csu.ecbackend.commom.CommonResponse;
//import com.csu.ecbackend.persistence.UserRecordDao;
//import com.csu.ecbackend.service.RankingListService;
//import com.csu.ecbackend.vo.RankingListVO;
//import io.swagger.models.auth.In;
//import org.json.JSONObject;
//import org.mybatis.spring.annotation.MapperScan;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.*;
//
//@Service
//@MapperScan("com.csu.ecbackend.persistence")
//public class RankingListServiceImpl implements RankingListService {
//
//    @Autowired
//    private UserRecordDao userRecordDao;
//
//    @Override
//    public CommonResponse<List<RankingListVO>> getRankingList() {
//        QueryWrapper<UserRecord> queryWrapper=new QueryWrapper<>();
//        queryWrapper.last("limit 100");
////        List<Map<String, Object>> resultMap=userRecordDao.selectMaps(queryWrapper);
//        List<UserRecord> userRecords = userRecordDao.selectList(queryWrapper);
//        Map<String,Integer> record=  new HashMap<>();
//        userRecords.forEach(record1->record.merge(record1.getRecord(), 1, Integer::sum));
//
//        List<Map.Entry<String, Integer>> entryList2 = new ArrayList<Map.Entry<String, Integer>>(record.entrySet());
//        Collections.sort(entryList2, new Comparator<Map.Entry<String, Integer>>() {
//            @Override
//            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
//                return o2.getValue().compareTo(o1.getValue()); // 升序排序
//            }
//        });
//        List<RankingListVO> list = new ArrayList<RankingListVO>();
//        for(int i=0;i<entryList2.size();i++){
//            RankingListVO rankingListVO = new RankingListVO();
//            rankingListVO.setWord(entryList2.get(i).getKey());
//            rankingListVO.setNum(entryList2.get(i).getValue());
//            list.add(rankingListVO);
//        }
//
////        System.out.println(resultMap);
////        System.out.println(userRecords);
//        return CommonResponse.createForSuccess(list);
//    }
//}
