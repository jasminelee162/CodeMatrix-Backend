//package com.csu.ecbackend.service.impl;
//
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.csu.ecbackend.bean.CompeteWord;
//import com.csu.ecbackend.bean.UserFeedback;
//import com.csu.ecbackend.bean.UserRecord;
//import com.csu.ecbackend.commom.CommonResponse;
//import com.csu.ecbackend.commom.SortTool;
//import com.csu.ecbackend.commom.SortTool1;
//import com.csu.ecbackend.commom.SortTool2;
//import com.csu.ecbackend.dao.RemoteCompeteDao;
//import com.csu.ecbackend.dao.RemoteMediateDao;
//import com.csu.ecbackend.persistence.UserFeedbackDao;
//import com.csu.ecbackend.persistence.UserRecordDao;
//import com.csu.ecbackend.service.KeywordService;
//import com.csu.ecbackend.util.SimilarWords;
//import com.csu.ecbackend.vo.FeedbackVO;
//import com.csu.ecbackend.vo.KeywordVO;
//import com.csu.ecbackend.vo.DateAndTimes;
//import com.csu.ecbackend.vo.RecordVO;
//import org.mybatis.spring.annotation.MapperScan;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.*;
//
//@Service
//@MapperScan("com.csu.ecbackend.dao")
//public class KeywordServiceImpl implements KeywordService {
//
//      private static long DAY = 86400000;//一天的毫秒
//
//
//      @Autowired
//      UserRecordDao userRecordDao;
//
//      @Autowired
//      UserFeedbackDao userFeedbackDao;
//
//      @Autowired
//      RemoteCompeteDao remoteCompeteDao;
//
//      @Autowired
//      RemoteMediateDao remoteMediateDao;
//
//
//      @Override
//      public CommonResponse<KeywordVO> getCompetes(String keyword, int sort) {
//            String competes = remoteCompeteDao.getCompetes(keyword);
//            if (!competes.equals("")) {
//                  KeywordVO keywordVO = getKeywordVO(keyword, competes, sort);
//                  return CommonResponse.createForSuccess(keywordVO);
//            }
//
//            for (String similarWord : SimilarWords.getSimilarWords(keyword)) {
//                  competes = remoteCompeteDao.getCompetes(similarWord);
//                  if (!competes.equals("")) {
//                        System.out.println("找到 " + keyword + "的相近词：" + similarWord);
//                        KeywordVO keywordVO = getKeywordVO(keyword, competes, sort);
//                        return CommonResponse.createForSuccess(keywordVO);
//                  }
//            }
//            return CommonResponse.createForError("抱歉，该关键词不存在");
//      }
//
//      @Override
//      public CommonResponse<KeywordVO> getMediate(String keyword) {
//            String mediates = remoteMediateDao.getMediates(keyword);
//            if (!mediates.equals("")) {
//                  KeywordVO keywordVO = new KeywordVO(keyword, mediates);
//                  return CommonResponse.createForSuccess(keywordVO);
//            }
//
//            for (String similarWord : SimilarWords.getSimilarWords(keyword)) {
//                  mediates = remoteMediateDao.getMediates(similarWord);
//                  if (!mediates.equals("")) {
//                        System.out.println("找到 " + keyword + "的相近词：" + similarWord);
//                        KeywordVO keywordVO = new KeywordVO(keyword, mediates);
//                        return CommonResponse.createForSuccess(keywordVO);
//                  }
//            }
//            return CommonResponse.createForError("抱歉，该关键词不存在");
//      }
//
//      @Override
//      public CommonResponse<RecordVO> getRecordWeek(String keyword) {
//            RecordVO recordVO = new RecordVO();
//            recordVO.setKeyword(keyword);
//
//            Date date = transferDate(new Date());//清空时分秒
//
//            List<DateAndTimes> list = new ArrayList<>();
//            list.add(getRecord(keyword, new Date(date.getTime() - 6 * DAY), new Date(date.getTime() - 5 * DAY)));
//            list.add(getRecord(keyword, new Date(date.getTime() - 5 * DAY), new Date(date.getTime() - 4 * DAY)));
//            list.add(getRecord(keyword, new Date(date.getTime() - 4 * DAY), new Date(date.getTime() - 3 * DAY)));
//            list.add(getRecord(keyword, new Date(date.getTime() - 3 * DAY), new Date(date.getTime() - 2 * DAY)));
//            list.add(getRecord(keyword, new Date(date.getTime() - 2 * DAY), new Date(date.getTime() - 1 * DAY)));
//            list.add(getRecord(keyword, new Date(date.getTime() - 1 * DAY), date));
//            list.add(getRecord(keyword, date, new Date()));
//            recordVO.setDateAndTimes(list);
//            return CommonResponse.createForSuccess(recordVO);
//      }
//
//      @Override
//      public CommonResponse<FeedbackVO> getFeedBackWeek(String keyword, String compete) {
//            FeedbackVO feedbackVO = new FeedbackVO();
//            feedbackVO.setKeyword(keyword);
//            feedbackVO.setCompete(compete);
//
//            Date date = transferDate(new Date());//清空时分秒
//
//            List<DateAndTimes> list = new ArrayList<>();
//            list.add(getFeedback(keyword, compete, new Date(date.getTime() - 6 * DAY), new Date(date.getTime() - 5 * DAY)));
//            list.add(getFeedback(keyword, compete, new Date(date.getTime() - 5 * DAY), new Date(date.getTime() - 4 * DAY)));
//            list.add(getFeedback(keyword, compete, new Date(date.getTime() - 4 * DAY), new Date(date.getTime() - 3 * DAY)));
//            list.add(getFeedback(keyword, compete, new Date(date.getTime() - 3 * DAY), new Date(date.getTime() - 2 * DAY)));
//            list.add(getFeedback(keyword, compete, new Date(date.getTime() - 2 * DAY), new Date(date.getTime() - 1 * DAY)));
//            list.add(getFeedback(keyword, compete, new Date(date.getTime() - 1 * DAY), date));
//            list.add(getFeedback(keyword, compete, date, new Date()));
//            feedbackVO.setDateAndTimes(list);
//            return CommonResponse.createForSuccess(feedbackVO);
//      }
//
//
//      @Override
//      public CommonResponse insertRecord(int userid, String keyword, String ip) {
//            UserRecord userRecord = new UserRecord(userid, keyword, new Date(), ip);
//            userRecordDao.insert(userRecord);
//            return CommonResponse.createForSuccess("插入成功");
//      }
//
//
//      @Override
//      public CommonResponse checkSearchTimes(String ip) {
//            QueryWrapper<UserRecord> queryWrapper = new QueryWrapper<>();
//            queryWrapper.eq("ip", ip);
//            List<UserRecord> userRecordList = userRecordDao.selectList(queryWrapper);
//            return CommonResponse.createForSuccess(userRecordList);
//      }
//
//      public int getFeedbackNum(String keyword, String competeWord, int type) {
//            QueryWrapper<UserFeedback> queryWrapper = new QueryWrapper<>();
//            queryWrapper.eq("keyword", keyword).eq("competeword", competeWord).eq("evaluate", type);
//            List<UserFeedback> list = userFeedbackDao.selectList(queryWrapper);
//
//            return list.size();
//      }
//
//      private KeywordVO getKeywordVO(String keyword, String row, int sort) {
//            List<CompeteWord> lists = new ArrayList<>();
//            String[] split = row.split("\n");
//            for (String s : split) {
//                  String[] s1 = s.split(" ");
//                  int good = getFeedbackNum(s1[1], s1[2], 1);
//                  int bad = getFeedbackNum(s1[1], s1[2], 0);
//                  lists.add(new CompeteWord(s1[0], keyword, s1[2], s1[3], good, bad));
//            }
//            switch (sort) {
//                  case 0:
//                        Collections.sort(lists, new SortTool());
//                        break;
//                  case 1:
//                        Collections.sort(lists, new SortTool1());
//                        break;
//                  case 2:
//                        Collections.sort(lists, new SortTool2());
//                        break;
//                  default:
//                        Collections.sort(lists, new SortTool());
//            }
//            for (int i=0;i<lists.size();i++){
//                  lists.get(i).setId(String.valueOf(i+1));
//            }
//            KeywordVO keywordVO = new KeywordVO(keyword, lists);
//
//            return keywordVO;
//      }
//
//      private DateAndTimes getFeedback(String keyword, String compete, Date date, Date date1) {
//            QueryWrapper<UserFeedback> queryWrapper = new QueryWrapper<>();
//            queryWrapper.eq("keyword", keyword).eq("competeword", compete).eq("evaluate", 1).between("time", date, date1);
//            List<UserFeedback> userFeedbackList = userFeedbackDao.selectList(queryWrapper);
//
//            DateAndTimes dateAndTimes = new DateAndTimes();
//            dateAndTimes.setTimes(userFeedbackList.size());
//            dateAndTimes.setDate(date.toString().substring(4, 10));
//            return dateAndTimes;
//      }
//
//      private DateAndTimes getRecord(String keyword, Date date, Date date1) {
//            QueryWrapper<UserRecord> queryWrapper = new QueryWrapper<>();
//            queryWrapper.eq("record", keyword).between("time", date, date1);
//            List<UserRecord> userRecords = userRecordDao.selectList(queryWrapper);
//
//            DateAndTimes dateAndTimes = new DateAndTimes();
//            dateAndTimes.setTimes(userRecords.size());
//            dateAndTimes.setDate(date.toString().substring(4, 10));
//            return dateAndTimes;
//      }
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
//}
