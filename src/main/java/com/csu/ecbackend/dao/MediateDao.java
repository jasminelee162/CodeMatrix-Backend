package com.csu.ecbackend.dao;


import com.csu.ecbackend.path.Path;
import com.csu.ecbackend.util.StringUtils;
import com.csu.ecbackend.util.remoteDBUtil;
import com.csu.ecbackend.util.remoteDBUtil2;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class MediateDao {

      public void insertMediate(String word, String[] mediates) {

            String s = StringUtils.getFirstLetter(word);
            int k = s.charAt(0) - 'A';
            if (checkWordExist(word, k + 1)) {
                  System.out.println("关键词" + word + "已存在，取消插入");
                  return;
            }
            for (int i = 0; i < new Path().getNumOfKey(); i++) {
                  if (i < 15) {
                        addmediate_word(word, mediates[i], 1, k + 1);
                  }
            }

            addIndex(word, k + 1);
      }

      public boolean checkWordExist(String key, int i) {

            Connection masterConn = remoteDBUtil2.getMasterConnection();
            PreparedStatement masterPs = null;    //对主库进行操作的对象

            try {
                  String sqlQuery = " select * from index_" + i + " where key_word='" + key + "'";
                  masterPs = masterConn.prepareStatement(sqlQuery);
                  ResultSet resultSet = masterPs.executeQuery();
                  if (resultSet.next()) {
                        return true;
                  }
            } catch (SQLException e) {
                  e.printStackTrace();
            } catch (Exception e) {
                  e.printStackTrace();
            } finally {
                  remoteDBUtil.close(masterConn, masterPs, null);
            }
            return false;
      }

      public int addmediate_word(String key, String mediate_word, double mediate, int i) {
            int result = 0;

            Connection masterConn = remoteDBUtil2.getMasterConnection();

            PreparedStatement masterPs = null;    //对主库进行操作的对象
            try {
                  String sqlString = " insert into track_" + i + "(key_word,mediate_word,mediate) values('" + key + "','" + mediate_word + "','" + mediate + "')  ";
                  System.out.println(sqlString);
                  masterPs = masterConn.prepareStatement(sqlString);

                  result = masterPs.executeUpdate();
            } catch (SQLException e) {
                  e.printStackTrace();
            } catch (Exception e) {
                  e.printStackTrace();
            } finally {
                  remoteDBUtil.close(masterConn, masterPs, null);
            }
            return result;
      }


      //i表示第几个表
      public void addIndex(String key, int i) {
            String mediate_row = getCount(key, i);

            Connection connection = remoteDBUtil2.getMasterConnection();
            PreparedStatement statement = null;
            try {
                  String sqlString = " insert into index_" + i + "(table_index,key_word,mediate_row) values('" + i + "','" + key + "','" + mediate_row + "')  ";
                  System.out.println(sqlString);
                  statement = connection.prepareStatement(sqlString);
                  statement.executeUpdate();
                  //result=masterPs.executeUpdate();
            } catch (SQLException e) {
                  e.printStackTrace();
            } catch (Exception e) {
                  e.printStackTrace();
            } finally {
                  remoteDBUtil.close(connection, statement, null);
            }
      }

      private String getCount(String key_word, int i) {

            Connection masterConn = remoteDBUtil2.getMasterConnection();
            String resultString = null;

            String sqlString = " select id from track_" + i + " where key_word=?";
            PreparedStatement masterPs = null;
            try {

                  masterPs = masterConn.prepareStatement(sqlString);
                  masterPs.setString(1, key_word);
                  System.out.println(sqlString);
                  ResultSet resultSet = masterPs.executeQuery();
                  while (resultSet.next()) {
                        resultString = resultString + " " + resultSet.getInt(1);

                  }
            } catch (SQLException e) {
                  e.printStackTrace();
            } catch (Exception e) {
                  e.printStackTrace();
            } finally {
                  remoteDBUtil.close(masterConn, masterPs, null);
            }
            String temp = resultString.substring(5, resultString.length());
            return temp;
      }
}
