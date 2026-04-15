//package com.csu.ecbackend.dao;
//
//import com.csu.ecbackend.util.DBUtil;
//import com.csu.ecbackend.util.StringUtils;
//import org.springframework.stereotype.Repository;
//import redis.clients.jedis.Jedis;
//
//import java.sql.*;
//
//@Repository
//public class RemoteMediateDao {
//      //创建操作Redis和数据库的对象
//      private Jedis jedis;
//      private Connection masterConn;  //连接主库的对象
//      private Connection slaveConn;   //连接从库的对象
//      PreparedStatement masterPs = null;    //对主库进行操作的对象
//      PreparedStatement slavePs = null;     //对从库进行操作的对象
//
//
//      public RemoteMediateDao() {
//            init();
//      }
//
//      //初始化环境
//      public void init() {
//            //MYSQL的连接参数
//            String mySQLDriver = "com.mysql.cj.jdbc.Driver";
//            String masterUrl = "jdbc:mysql://47.101.195.251:3306/redisDemo2?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
//            String slaveUrl = "jdbc:mysql://47.92.128.133:3316/redisDemo2?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
////            String slaveUrl = "jdbc:mysql://47.101.195.251:3306/redisDemo?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
//            String user = "root";
//            String pwd = "123456";
//            try {
//                  Class.forName(mySQLDriver);
//                  masterConn = DriverManager.getConnection(masterUrl, user, pwd);
//                  slaveConn = DriverManager.getConnection(slaveUrl, user, pwd);
//                  jedis = new Jedis("47.101.195.251", 6379);
//            } catch (SQLException e) {
//                  e.printStackTrace();
//            } catch (Exception e) {
//                  e.printStackTrace();
//            }
//      }
//
//      public String getMediates(String keyword) {
//
//            String mediate_row = "";
//            //如果存在于Redis,就先从Redis里获取
//            String mediates = "";
//            if (jedis.exists(keyword + "21")) {
//                  System.out.println("keyword: " + keyword + "的竞争关键词 exists in Redis");
//                  System.out.println("str is :" + jedis.get(keyword + "21"));
//                  return jedis.get(keyword + "21");
//            } else {  //如果没在Redis里，就到从MySQL里去读
//                  try {
//                        int i = StringUtils.getFirstLetterIndex(keyword);
//                        slavePs = slaveConn.prepareStatement("select * from index_" + i + " where key_word='" + keyword + "'");
////                        slavePs = slaveConn.prepareStatement("select mediate_row from index_"+"i where key_word="+keyword);
//                        ResultSet rs = slavePs.executeQuery();
//                        if (rs.next()) {
//                              mediate_row = rs.getString("mediate_row");
//                              int trackid = rs.getInt("table_index");
//                              System.out.println("mediate_row is: " + mediate_row);
//                              mediates = getMediatesByIndex(mediate_row, trackid);
//                              //放入Redis缓存
//                              jedis.set(keyword + "21", mediates);
//                        }
//                        return mediates;
//                  } catch (SQLException e) {
//                        e.printStackTrace();
//                  } catch (Exception e) {
//                        e.printStackTrace();
//                  } finally {
//                        DBUtil.close(masterConn, masterPs, null);
//                  }
//            }
//            return mediates;
//      }
//
//      //根据行名和id返回string
//      private String getMediatesByIndex(String mediate_row, int trackid) {
//            String all = "";
//
//            try {
//                  for (String s : mediate_row.split(" ")) {
//                        slavePs = slaveConn.prepareStatement("select * from track_" + trackid + " limit " + (Integer.parseInt(s) - 1) + ",1");
//                        ResultSet rs = slavePs.executeQuery();
//                        if (rs.next()) {
//                              String temp = rs.getString("id") + " " + rs.getString("key_word") + " " + rs.getString("mediate_word") + " " + rs.getString("mediate");
//                              System.out.println("temp is: " + temp);
//                              all += temp + "\n";
//                        }
//                  }
//            } catch (SQLException e) {
//                  e.printStackTrace();
//            } catch (Exception e) {
//                  e.printStackTrace();
//            }
//            return all;
//      }
//}
