//package com.csu.ecbackend.service.impl;
//
//
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.csu.ecbackend.bean.User;
//import com.csu.ecbackend.bean.UserInfo;
//import com.csu.ecbackend.bean.UserRecord;
//import com.csu.ecbackend.commom.CommonResponse;
//import com.csu.ecbackend.persistence.UserDao;
//import com.csu.ecbackend.persistence.UserInfoDao;
//import com.csu.ecbackend.persistence.UserRecordDao;
//import com.csu.ecbackend.service.UserService;
//import lombok.extern.slf4j.Slf4j;
//import org.mybatis.spring.annotation.MapperScan;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.*;
//
//@Slf4j
//@Service
//@MapperScan("com.csu.ecbackend.persistence")
//public class UserServiceImpl implements UserService {
//
//      private static long DAY = 86400000;//一天的毫秒
//
//      @Autowired
//      private UserDao userDao;
//
//      @Autowired
//      private UserInfoDao userInfoDao;
//
//      @Autowired
//      private UserRecordDao userRecordDao;
//
//      @Override
//      public CommonResponse<User> getUser(String username) {
//            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//            queryWrapper.eq("username", username);
//
//            return CommonResponse.createForSuccess(userDao.selectOne(queryWrapper));
//      }
//
//      @Override
//      public CommonResponse<User> getUser(String username, String password) {
//            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//            queryWrapper.eq("username", username);
//            User user = userDao.selectOne(queryWrapper);
//            if (user == null)
//                  return CommonResponse.createForError("用户不存在");
//            UserInfo userInfo = userInfoDao.selectById(user.getUserid());
//            if (userInfo.getPassword().equals(password))
//                  return CommonResponse.createForSuccess(user);
//            else
//                  return CommonResponse.createForError("密码错误");
//      }
//
//      @Override
//      public CommonResponse<List<Map<String, Object>>> getUserRecord(String username) {
//            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//            queryWrapper.eq("username", username);
//            int userid = userDao.selectOne(queryWrapper).getUserid();
//
//            QueryWrapper<UserRecord> queryWrapper1 = new QueryWrapper<>();
//            queryWrapper1.eq("userid", userid);
//            return CommonResponse.createForSuccess(userRecordDao.selectMaps(queryWrapper1));
//      }
//
//      @Override
//      public CommonResponse<String> getUserRecordToday(String username ,String ip) {
//            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//            queryWrapper.eq("username", username);
//            int userid = userDao.selectOne(queryWrapper).getUserid();
//
//            QueryWrapper<UserRecord> queryWrapper1 = new QueryWrapper<>();
//            QueryWrapper<UserRecord> queryWrapper2 = new QueryWrapper<>();
//            Date date = transferDate(new Date());//清空时分秒
//            Date date1 = hourPreOne(new Date());
//            QueryWrapper<UserRecord> queryWrapperIp = new QueryWrapper<>();
//            queryWrapperIp.eq("ip",ip).between("time", date1, new Date());
//            List<Integer> list=new ArrayList<>();
//            for (UserRecord userRecord : userRecordDao.selectList(queryWrapperIp)) {
//                  if(!list.contains(userRecord.getUserid()))
//                        list.add(userRecord.getUserid());
//            }
//            if(list.size()>5){
//                  return CommonResponse.createForError("当前ip一小时内使用登录账户过多");
//            }
//
//
//            queryWrapper1.eq("userid", userid).between("time", date, new Date());
//            List<Map<String, Object>> maps = userRecordDao.selectMaps(queryWrapper1);
//
//            queryWrapper2.eq("userid", userid).between("time", date1, new Date());
//            List<Map<String, Object>> maps2 = userRecordDao.selectMaps(queryWrapper2);
//
//            QueryWrapper<UserInfo> queryWrapper3 = new QueryWrapper<>();
//            queryWrapper3.eq("userid", userid);
//
//            int permission = userInfoDao.selectOne(queryWrapper3).getPermission();
//            if (permission == 2) {
//                  if(maps2.size()<=100){
//                        return CommonResponse.createForSuccess("success");
//                  }
//                  else{
//                        return CommonResponse.createForError("too many");
//                  }
//            } else {
//                  if (maps.size() <= 10) {
//                        log.info(String.valueOf(maps.size()));
//                        return CommonResponse.createForSuccess("success");
//                  } else {
//                        return CommonResponse.createForError("failure");
//                  }
//            }
//
//      }
//
//
//
//      private Date transferDate(Date date) {
//            Calendar instance = Calendar.getInstance();
//            instance.setTime(date);
//            instance.set(Calendar.HOUR_OF_DAY, 0);
//            instance.set(Calendar.MINUTE, 0);
//            instance.set(Calendar.SECOND, 0);
//            instance.set(Calendar.MILLISECOND, 0);
//            date = instance.getTime();
//            return date;
//      }
//
//      private Date hourPreOne(Date date) {
//            Calendar instance = Calendar.getInstance();
//            instance.setTime(date);
//            instance.set(Calendar.HOUR_OF_DAY, Calendar.HOUR_OF_DAY - 1);
//            date = instance.getTime();
//            return date;
//      }
//}

