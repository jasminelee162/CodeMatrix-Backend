package com.csu.ecbackend.dao;

import com.csu.ecbackend.util.DBUtil;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class localCompeteDao {
    public   int addCompete_word(String key,String compete_word,double compete,int i) {
        int result=0;

        Connection masterConn= DBUtil.getConnection();

        PreparedStatement masterPs=null;    //对主库进行操作的对象
        PreparedStatement slavePs=null;
        try{
            String sqlString=" insert into track_"+i+"(key_word,compete_word,compete) values('"+key+"','"+compete_word+"','"+compete+"')  ";
            // String sqlString=" insert into test_track_"+i+"(key_word,compete_word,compete) values('"+key+"','"+compete_word+"','"+compete+"')  ";
            /* masterPs=masterConn.prepareStatement(" insert into track_\"+i+\"(key_word,compete_word,compete) values('"+key+"','"+compete_word+"','"+compete+"')  ");*/
            System.out.println(sqlString);
            masterPs=masterConn.prepareStatement(sqlString);

            masterPs.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBUtil.close(masterConn, masterPs, null);
        }

       /* String compete_row=getCount(key,i);

        Connection connection=DBUtil.getConnection();
        PreparedStatement statement=null;
        try{
            String sqlString=" insert into index_"+i+"(table_index,key_word,compete_row) values('"+i+"','"+key+"','"+compete_row+"')  ";
            // String sqlString=" insert into test_track_"+i+"(key_word,compete_word,compete) values('"+key+"','"+compete_word+"','"+compete+"')  ";
            *//* masterPs=masterConn.prepareStatement(" insert into track_\"+i+\"(key_word,compete_word,compete) values('"+key+"','"+compete_word+"','"+compete+"')  ");*//*
            System.out.println(sqlString);
            statement=connection.prepareStatement(sqlString);
            statement.executeUpdate();
            //result=masterPs.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBUtil.close(connection, statement, null);
        }*/

        return result;
    }
    //i表示第几个表
    public void  addIndex(String key,int i){
        String compete_row=getCount(key,i);

        Connection connection=DBUtil.getConnection();
        PreparedStatement statement=null;
        try{
            String sqlString=" insert into index_"+i+"(table_index,key_word,compete_row) values('"+i+"','"+key+"','"+compete_row+"')  ";
            // String sqlString=" insert into test_track_"+i+"(key_word,compete_word,compete) values('"+key+"','"+compete_word+"','"+compete+"')  ";
            /* masterPs=masterConn.prepareStatement(" insert into track_\"+i+\"(key_word,compete_word,compete) values('"+key+"','"+compete_word+"','"+compete+"')  ");*/
            System.out.println(sqlString);
            statement=connection.prepareStatement(sqlString);
            statement.executeUpdate();
            //result=masterPs.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBUtil.close(connection, statement, null);
        }

    }
    private String getCount(String key_word,int i){

        Connection masterConn= DBUtil.getConnection();
        List<Integer> indexlist=new ArrayList<>();
        String resultString =null;
        PreparedStatement masterPs = null;
        ResultSet resultSet=null;
        //PreparedStatement masterPs=null;    //对主库进行操作的对象
        String sqlString=" select id from track_"+i+" where key_word=?";
        try{

            // String sqlString=" insert into test_track_"+i+"(key_word,compete_word,compete) values('"+key+"','"+compete_word+"','"+compete+"')  ";
            /* masterPs=masterConn.prepareStatement(" insert into track_\"+i+\"(key_word,compete_word,compete) values('"+key+"','"+compete_word+"','"+compete+"')  ");*/
            //System.out.println(sqlString);
             masterPs=masterConn.prepareStatement(sqlString);
            masterPs.setString(1,key_word);
            System.out.println(sqlString);
             resultSet=masterPs.executeQuery();
            while (resultSet.next()){
                resultString=resultString+" "+resultSet.getInt("id");

            }
            //List<Integer>indexlist= Collections.singletonList(masterPs.executeUpdate());
        }catch (SQLException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
                DBUtil.close(masterConn, masterPs, resultSet);
        }
        String  temp=resultString.substring(5,resultString.length());
        return temp;
    }
}
