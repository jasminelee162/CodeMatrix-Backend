package com.csu.ecbackend.util;

import redis.clients.jedis.Jedis;

import java.sql.*;
public class MySQLClusterDemo {
    //创建操作Redis和数据库的对象
    private Jedis jedis;
    private Connection masterConn;  //连接主库的对象
    private Connection slaveConn;   //连接从库的对象
    PreparedStatement masterPs=null;    //对主库进行操作的对象
    PreparedStatement slavePs=null;     //对从库进行操作的对象
    //初始化环境
    private void init(){
        //MYSQL的连接参数
        String mySQLDriver="com.mysql.cj.jdbc.Driver";
        String masterUrl="jdbc:mysql://47.101.195.251:3306/redisDemo?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
        String slaveUrl="jdbc:mysql://47.92.128.133:3316/redisDemo?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
        String user="root";
        String pwd="123456";
        try{
            Class.forName(mySQLDriver);
            masterConn= DriverManager.getConnection(masterUrl,user,pwd);
            slaveConn= DriverManager.getConnection(slaveUrl,user,pwd);
            jedis=new Jedis("47.92.128.133",6379);
        }catch (SQLException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void insertData(){
        //是向主MySQL服务器插入数据
        try{
            masterPs=masterConn.prepareStatement("insert into student(id,name,age,score) values(16,'jg',13,89)");
            masterPs.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private String getNameByID(String id){
        String key="Stu"+id;
        String name="";
        //如果存在于Redis,就先从Redis里获取
        if(jedis.exists(key)){
            System.out.println("ID:"+key+" exists in Redis");
            name=jedis.get(key);
            System.out.println("Name is :"+jedis.get(key));
            return name;
        }else{  //如果没在Redis里，就到从MySQL里去读
            try {
                slavePs=slaveConn.prepareStatement("select name from student where id=10");
                ResultSet rs=slavePs.executeQuery();
                if(rs.next()){
                    System.out.println("ID: "+key+" exists in Slave MySQL");
                    name=rs.getString("name");
                    System.out.println("Name is: "+name);
                    //放入Redis缓存
                    jedis.set(key,name);
                }
                return name;
            }catch (SQLException e){
                e.printStackTrace();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return name;
    }

//    public static void main(String[] args) {
//        MySQLClusterDemo tool=new MySQLClusterDemo();
//        tool.init();
//        tool.insertData();
//        //场景1 没有从Redis中找到，就到从MySQL服务器中去读
//        System.out.println(tool.getNameByID("10"));
//        //场景2，当前ID=10的数据已存在于Redis,所有直接读缓存
//        System.out.println(tool.getNameByID("10"));
//    }
}

