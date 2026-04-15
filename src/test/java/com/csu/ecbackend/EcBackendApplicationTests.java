//package com.csu.ecbackend;
//
//import com.csu.ecbackend.bean.User;
//import com.csu.ecbackend.commom.CommonResponse;
//import com.csu.ecbackend.dao.RemoteCompeteDao;
//import com.csu.ecbackend.persistence.UserDao;
//import com.csu.ecbackend.persistence.UserInfoDao;
//import com.csu.ecbackend.service.KeywordService;
//import com.csu.ecbackend.service.RankingListService;
//import com.csu.ecbackend.service.UserService;
//import com.csu.ecbackend.util.StringUtils;
//import com.csu.ecbackend.vo.KeywordVO;
//import com.hankcs.hanlp.HanLP;
//import com.hankcs.hanlp.model.crf.CRFLexicalAnalyzer;
//import com.hankcs.hanlp.restful.HanLPClient;
//import com.hankcs.hanlp.seg.Dijkstra.DijkstraSegment;
//import com.hankcs.hanlp.seg.NShort.NShortSegment;
//import com.hankcs.hanlp.seg.Segment;
//import com.hankcs.hanlp.tokenizer.IndexTokenizer;
//import com.hankcs.hanlp.tokenizer.NLPTokenizer;
//import com.hankcs.hanlp.tokenizer.SpeedTokenizer;
//import com.hankcs.hanlp.tokenizer.StandardTokenizer;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.io.IOException;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//
//@SpringBootTest
//class EcBackendApplicationTests {
//
//
//      @Autowired
//      UserService userService;
//
//      @Autowired
//      KeywordService keywordService;
//
//      @Autowired
//      RankingListService rankingListService;
//
//      @Autowired
//      RemoteCompeteDao remoteCompeteDao;
//
//      @Autowired
//      @Test
//      void testdao1() throws IOException {
//      }
//
//      @Test
//      void testdao() throws IOException {
////        CommonResponse<KeywordVO> word = keywordService.getCompetes("宝马");
////        System.out.println(word);
////            String compete = remoteCompeteDao.getCompete("宝马", "保时捷");
////            System.out.println(compete);
//            int i = remoteCompeteDao.updateCompete("宝马", "保时捷", "0.0305373333");
//            System.out.println(i);
//      }
//
//      @Test
//      void test() throws IOException {
//            HanLPClient client = new HanLPClient("https://www.hanlp.com/api", null); //第二个参数秘钥，需要申请
//            System.out.println(client.parse("图片大全"));
//      }
//
//
//      @Test
//      void test3() throws IOException {
//
//            String s = "梁朝伟与替身同框";
//            System.out.println(HanLP.segment("百度云多少钱"));
////        System.out.println(NLPTokenizer.segment("百度云多少钱"));
//            System.out.println(IndexTokenizer.segment("梁朝伟与替身同框"));
//            System.out.println();
//            System.out.println(StandardTokenizer.segment(s));
//            System.out.println(NLPTokenizer.segment(s));
//
////        Segment nShortSegment = new NShortSegment().enableCustomDictionary(false).enablePlaceRecognize(true).enableOrganizationRecognize(true);
////        Segment shortestSegment = new DijkstraSegment().enableCustomDictionary(false).enablePlaceRecognize(true).enableOrganizationRecognize(true);
////        System.out.println("N-最短分词：" + nShortSegment.seg(s) + "\n最短路分词：" + shortestSegment.seg(s));
//
////        CRFLexicalAnalyzer analyzer = new CRFLexicalAnalyzer();
////        System.out.println(analyzer.analyze(s));
//      }
//}
