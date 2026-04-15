package com.csu.ecbackend.dao;


import com.csu.ecbackend.util.remoteDBUtil;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class CompeteDao {


      public boolean checkWordExist(String key, int i) {

            Connection masterConn = remoteDBUtil.getMasterConnection();
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

      public int addCompete_word(String key, String compete_word, double compete, int i) {
            int result = 0;

            Connection masterConn = remoteDBUtil.getMasterConnection();

            PreparedStatement masterPs = null;    //对主库进行操作的对象

            try {
                  String sqlString = " insert into track_" + i + "(key_word,compete_word,compete) values('" + key + "','" + compete_word + "','" + compete + "')  ";
                  // String sqlString=" insert into test_track_"+i+"(key_word,compete_word,compete) values('"+key+"','"+compete_word+"','"+compete+"')  ";
                  /* masterPs=masterConn.prepareStatement(" insert into track_\"+i+\"(key_word,compete_word,compete) values('"+key+"','"+compete_word+"','"+compete+"')  ");*/
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

      /*  String compete_row=getCount(key,i);

        try{
            String sqlString=" insert into index_"+i+"(table_index,key_word,compete_row) values('"+i+"','"+key+"','"+compete_row+"')  ";
            // String sqlString=" insert into test_track_"+i+"(key_word,compete_word,compete) values('"+key+"','"+compete_word+"','"+compete+"')  ";
            *//* masterPs=masterConn.prepareStatement(" insert into track_\"+i+\"(key_word,compete_word,compete) values('"+key+"','"+compete_word+"','"+compete+"')  ");*//*
            System.out.println(sqlString);
            masterPs=masterConn.prepareStatement(sqlString);
            masterPs.executeUpdate();
            //result=masterPs.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }*/

            return result;
      }

      //i表示第几个表
      public void addIndex(String key, int i) {
            String compete_row = getCount(key, i);

            Connection connection = remoteDBUtil.getMasterConnection();
            PreparedStatement statement = null;
            try {
                  String sqlString = " insert into index_" + i + "(table_index,key_word,compete_row) values('" + i + "','" + key + "','" + compete_row + "')  ";
                  // String sqlString=" insert into test_track_"+i+"(key_word,compete_word,compete) values('"+key+"','"+compete_word+"','"+compete+"')  ";
                  /* masterPs=masterConn.prepareStatement(" insert into track_\"+i+\"(key_word,compete_word,compete) values('"+key+"','"+compete_word+"','"+compete+"')  ");*/
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

            Connection masterConn = remoteDBUtil.getMasterConnection();

            String resultString = null;

            String sqlString = " select id from track_" + i + " where key_word=?";
            PreparedStatement masterPs = null;
            try {

                  // String sqlString=" insert into test_track_"+i+"(key_word,compete_word,compete) values('"+key+"','"+compete_word+"','"+compete+"')  ";
                  /* masterPs=masterConn.prepareStatement(" insert into track_\"+i+\"(key_word,compete_word,compete) values('"+key+"','"+compete_word+"','"+compete+"')  ");*/
                  //System.out.println(sqlString);
                  masterPs = masterConn.prepareStatement(sqlString);
                  masterPs.setString(1, key_word);
                  System.out.println(sqlString);
                  ResultSet resultSet = masterPs.executeQuery();
                  while (resultSet.next()) {
                        resultString = resultString + " " + resultSet.getInt(1);

                  }
                  //List<Integer>indexlist= Collections.singletonList(masterPs.executeUpdate());
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

      public static void main(String[] args) {

      }
}
