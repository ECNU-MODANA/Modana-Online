package com.wls.manage.crawler.general;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

//访问数据库类
public class DataAccess {
    private final static String db = "";
    private static DataAccess instance = null;
    private static ResourceBundle rb = ResourceBundle.getBundle("db");
    private static Connection connection = null;
    private DataAccess(){
        initRelationDB();
    }
    public synchronized static DataAccess getInstance(){
        if(instance==null){
            instance = new DataAccess();
        }
        return instance;
    }
    private synchronized static void initRelationDB(){
        try {
            Class.forName(rb.getString("DRIVER"));
            connection  = DriverManager.getConnection(rb.getString("URL"),rb.getString("user"),rb.getString("password"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static Connection getConnection(){
       return connection;
    }


}
