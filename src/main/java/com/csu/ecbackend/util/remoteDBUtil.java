package com.csu.ecbackend.util;

import redis.clients.jedis.Jedis;

import java.sql.*;

public class remoteDBUtil {
    private remoteDBUtil() {
    }
   private static String mySQLDriver="com.mysql.cj.jdbc.Driver";
    private static String masterUrl="jdbc:mysql://47.101.195.251:3306/redisDemo?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static String slaveUrl="jdbc:mysql://47.92.128.133:3316/redisDemo?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static  String user="root";
    private static String pwd="123456";
    static{
        try{
            Class.forName(mySQLDriver);
        /*masterConn= DriverManager.getConnection(masterUrl,user,pwd);
        slaveConn= DriverManager.getConnection(slaveUrl,user,pwd);
        jedis=new Jedis("47.101.195.251",6379);*/
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public static Connection getMasterConnection(){
        Connection connection=null;
        try {
            connection=DriverManager.getConnection(masterUrl,user,pwd);
        } catch (SQLException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return connection;
    }
    public static Connection getSalveConnection(){
        Connection connection=null;
        try {
            connection=DriverManager.getConnection(slaveUrl,user,pwd);
        } catch (SQLException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return connection;
    }
    public static Jedis getJedis(){
        Jedis jedis=new Jedis("47.92.128.133",6379);

        return jedis;
    }
    public static void close(Connection connection, Statement stm, ResultSet rs) {
        if (connection!=null) {
            try {
                connection.close();
            } catch (SQLException e) {
                // TODO: handle exception
                e.printStackTrace();
            }

        }
        if (stm!=null) {
            try {
                stm.close();
            } catch (SQLException e) {
                // TODO: handle exception
                e.printStackTrace();
            }


        }
        if (rs!=null) {
            try {
                rs.close();
            } catch (SQLException e) {
                // TODO: handle exception
                e.printStackTrace();
            }

        }

    }

}


