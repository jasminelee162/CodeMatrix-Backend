//package com.csu.ecbackend.dao;
//
//
//import com.csu.ecbackend.util.DBUtil;
//import com.csu.ecbackend.util.StringUtils;
//import com.csu.ecbackend.util.remoteDBUtil;
//import org.springframework.stereotype.Repository;
//import redis.clients.jedis.Jedis;
//
//import java.sql.*;
//
//@Repository
//public class RemoteCompeteDao {
//      //创建操作Redis和数据库的对象
//      private Jedis jedis;
//      private Connection masterConn;  //连接主库的对象
//      private Connection slaveConn;   //连接从库的对象
//      PreparedStatement masterPs = null;    //对主库进行操作的对象
//      PreparedStatement slavePs = null;     //对从库进行操作的对象
//
//      public RemoteCompeteDao() {
//            init();
//      }
//
//      //初始化环境
//      public void init() {
//            //MYSQL的连接参数
//            String mySQLDriver = "com.mysql.cj.jdbc.Driver";
//            String masterUrl = "jdbc:mysql://47.101.195.251:3306/redisDemo?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
//            String slaveUrl = "jdbc:mysql://47.92.128.133:3316/redisDemo?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
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
//      public int updateCompete(String keyword, String competeWord, String compete) {
//            Connection masterConn = remoteDBUtil.getMasterConnection();
//
//            PreparedStatement masterPs = null;    //对主库进行操作的对象
//
//            try {
//                  int i = StringUtils.getFirstLetterIndex(keyword);
//                  masterPs = masterConn.prepareStatement("update track_" + i + " set compete='" + compete + "'   where key_word='" + keyword + "'  and compete_word= '" + competeWord + "'");
//                  int i1 = masterPs.executeUpdate();
//                  return i1;
//            } catch (SQLException e) {
//                  e.printStackTrace();
//            } catch (Exception e) {
//                  e.printStackTrace();
//            } finally {
//                  DBUtil.close(masterConn, masterPs, null);
//            }
//            return 0;
//      }
//
//      public String getCompete(String keyword, String competeWord) {
//
//            Connection slaveConn = remoteDBUtil.getMasterConnection();
//
//            PreparedStatement slavePs = null;    //对主库进行操作的对象
//
//            String compete = "0";
//            try {
//                  int i = StringUtils.getFirstLetterIndex(keyword);
//                  slavePs = slaveConn.prepareStatement("select * from track_" + i + " where key_word='" + keyword + "'  and compete_word= '" + competeWord + "'");
//                  ResultSet rs = slavePs.executeQuery();
//                  if (rs.next()) {
//                        compete = rs.getString("compete");
//                  }
//                  return compete;
//            } catch (SQLException e) {
//                  e.printStackTrace();
//            } catch (Exception e) {
//                  e.printStackTrace();
//            }finally {
//                  remoteDBUtil.close(slaveConn, slavePs, null);
//            }
//            return compete;
//      }
//
//      public String getCompetes(String keyword) {
//
//            String compete_row = "";
//            //如果存在于Redis,就先从Redis里获取
//            String competes = "";
//            if (jedis.exists(keyword + "0")) {
//                  System.out.println("keyword: " + keyword + "的竞争关键词 exists in Redis");
//                  System.out.println("str is :" + jedis.get(keyword + "0"));
//                  return jedis.get(keyword + "0");
//            } else {  //如果没在Redis里，就到从MySQL里去读
//                  try {
//                        int i = StringUtils.getFirstLetterIndex(keyword);
//                        slavePs = slaveConn.prepareStatement("select * from index_" + i + " where key_word='" + keyword + "'");
////                        slavePs = slaveConn.prepareStatement("select compete_row from index_"+"i where key_word="+keyword);
//                        ResultSet rs = slavePs.executeQuery();
//                        if (rs.next()) {
//                              compete_row = rs.getString("compete_row");
//                              int trackid = rs.getInt("table_index");
//                              System.out.println("compete_row is: " + compete_row);
//                              competes = getCompeteByIndex(compete_row, trackid);
//                              //放入Redis缓存
//                              jedis.set(keyword + "0", competes);
//                        }
//                        return competes;
//                  } catch (SQLException e) {
//                        e.printStackTrace();
//                  } catch (Exception e) {
//                        e.printStackTrace();
//                  }
//            }
//            return competes;
//      }
//
//      //根据行名和id返回string
//      private String getCompeteByIndex(String compete_row, int trackid) {
//            String all = "";
//
//            try {
//                  for (String s : compete_row.split(" ")) {
////                        slavePs = slaveConn.prepareStatement("select * from track_" + trackid + " where id=" + s);
//                        slavePs = slaveConn.prepareStatement("select * from track_" + trackid + " limit " + (Integer.parseInt(s) - 1) + ",1");
////                        slavePs = slaveConn.prepareStatement("select compete_row from index_"+"i where key_word="+keyword);
////                        System.out.println();
//                        ResultSet rs = slavePs.executeQuery();
//                        if (rs.next()) {
//                              String temp = rs.getString("id") + " " + rs.getString("key_word") + " " + rs.getString("compete_word") + " " + rs.getString("compete");
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
//
//}
//
//
//
