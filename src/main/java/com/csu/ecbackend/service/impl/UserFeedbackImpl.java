//package com.csu.ecbackend.service.impl;
//
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.csu.ecbackend.bean.User;
//import com.csu.ecbackend.bean.UserFeedback;
//import com.csu.ecbackend.bean.UserInfo;
//import com.csu.ecbackend.bean.UserRecord;
//import com.csu.ecbackend.commom.CommonResponse;
//import com.csu.ecbackend.dao.RemoteCompeteDao;
//import com.csu.ecbackend.persistence.UserDao;
//import com.csu.ecbackend.persistence.UserFeedbackDao;
//import com.csu.ecbackend.service.UserFeedbackService;
//import lombok.extern.slf4j.Slf4j;
//import org.mybatis.spring.annotation.MapperScan;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Calendar;
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//
//@Slf4j
//@Service
//@MapperScan("com.csu.ecbackend.persistence")
//public class UserFeedbackImpl implements UserFeedbackService {
//
//    @Autowired
//    UserDao userDao;
//
//    @Autowired
//    UserFeedbackDao userFeedbackDao;
//
//    @Autowired
//    RemoteCompeteDao remoteCompeteDao;
//
//    @Override
//    public CommonResponse<String> updateFeedback(String username,String keyword,String competeword,int evalute) {
//        //首先将本次记录插入数据库
//        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
//        queryWrapper.eq("username",username);
//        int userid =  userDao.selectOne(queryWrapper).getUserid();
//        userFeedbackDao.insert(new UserFeedback(userid,keyword,competeword,evalute,new Date()));
//
//        //然后修正模型竞争度
//        double userIndex = getUserIndex(userid);
//        double keywordIndex = getCompeteWordIndex(keyword,competeword,evalute);
//        double compete = Double.parseDouble(remoteCompeteDao.getCompete(keyword,competeword));
//
//        //如果用户点赞
//        if(evalute==1){
//            compete = compete * ( 1 + userIndex * keywordIndex);
//        }
//        else{//点踩
//            compete = compete * ( 1 - userIndex * keywordIndex);
//        }
//
//        //修改数据库中的竞争度
//        remoteCompeteDao.updateCompete(keyword,competeword,new Double(compete).toString());
//        return CommonResponse.createForSuccess("insert UserFeedBack success");
//    }
//
//
//    public double getUserIndex(int userid){
//        QueryWrapper<UserFeedback> queryWrapper=new QueryWrapper<>();
//        queryWrapper.eq("userid",userid).eq("evaluate",1);
//        List<UserFeedback> listGood = userFeedbackDao.selectList(queryWrapper);
//        int singleUserGoodNum = listGood.size();
//
//        QueryWrapper<UserFeedback> queryWrapper1=new QueryWrapper<>();
//        queryWrapper1.eq("userid",userid).eq("evaluate",0);
//        List<UserFeedback> listBad = userFeedbackDao.selectList(queryWrapper1);
//        int singleUserBadNum = listBad.size();
//
//        double index= (singleUserGoodNum+100)/((double) singleUserBadNum+100);
//        if(index>1){
//            index = 1.0/index;
//        }
//        return index;
//    }
//
//
//    public double getCompeteWordIndex(String keyword,String competeWord , int type) {
//        QueryWrapper<UserFeedback> queryWrapper=new QueryWrapper<>();
//        queryWrapper.eq("keyword",keyword).eq("competeword",competeWord).eq("evaluate",1);
//        List<UserFeedback> listGood = userFeedbackDao.selectList(queryWrapper);
//        int allUserGoodNum = listGood.size();
//
//        QueryWrapper<UserFeedback> queryWrapper1=new QueryWrapper<>();
//        queryWrapper1.eq("keyword",keyword).eq("competeword",competeWord).eq("evaluate",0);
//        List<UserFeedback> listBad = userFeedbackDao.selectList(queryWrapper1);
//        int allUserBadNum =listBad.size();
//
//        double index = 0;
//        if(type==1){
//            index = ((double)allUserGoodNum+1) / (100000000 + allUserGoodNum + allUserBadNum);
//        }
//        else {
//            index = ((double)allUserBadNum+1) / (100000000 + allUserGoodNum + allUserBadNum);
//        }
//        return index;
//    }
//
//    @Override
//    public CommonResponse<String> getUserFeedback3Min(String username) {
//        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("username", username);
//        int userid = userDao.selectOne(queryWrapper).getUserid();
//
//        QueryWrapper<UserFeedback> queryWrapper1 = new QueryWrapper<>();
//        Date date = threeMinPreOne(new Date());//清空时分秒
//
//        queryWrapper1.eq("userid", userid).between("time", date, new Date());
//        List<Map<String, Object>> maps = userFeedbackDao.selectMaps(queryWrapper1);
//
//        if(maps.size()<=10){
//            return CommonResponse.createForSuccess("success");
//        }
//        else{
//            return CommonResponse.createForError("error");
//        }
//    }
//
//    private Date threeMinPreOne(Date date) {
//        Calendar instance = Calendar.getInstance();
//        instance.setTime(date);
//        instance.set(Calendar.MINUTE, Calendar.MINUTE - 3);
//        date = instance.getTime();
//        return date;
//    }
//
//    @Override
//    public CommonResponse<String> userAction(String username,String keyword, String competeWord, int ranking,int time) {
//        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("username", username);
//        int userid = userDao.selectOne(queryWrapper).getUserid();
//
//        double userIndex = getUserIndex(userid);
//        double rankingIndex = 1.0/ranking;
//
//        if(time>60){
//            time=60;
//        }
//        double compete = Double.parseDouble(remoteCompeteDao.getCompete(keyword,competeWord));
//        double index = 1.0/((1+Math.pow(Math.E, -1 * time))*5000000);
//        compete = (1+userIndex*rankingIndex*index)*compete;
//        remoteCompeteDao.updateCompete(keyword,competeWord,new Double(compete).toString());
//        return CommonResponse.createForSuccess("success");
//    }
//
//}
