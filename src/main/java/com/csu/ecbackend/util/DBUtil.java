package com.csu.ecbackend.util;


import java.sql.*;


public class DBUtil {
    private   DBUtil() {
        // TODO Auto-generated constructor stub

    }
    private static String driver="com.mysql.cj.jdbc.Driver";
    private static String url="jdbc:mysql://localhost:3306/redisDemo?serverTimezone=UTC";
    private static String user="root";
    private static String password="a/ldCe>kp9!i";
    static {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        Connection connection=null;
        try {
            connection=DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return connection;
    }

    public static void close(Connection connection,Statement stm,ResultSet  rs) {
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
