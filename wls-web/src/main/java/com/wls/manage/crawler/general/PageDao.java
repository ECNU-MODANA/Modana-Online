package com.wls.manage.crawler.general;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PageDao {
    private static DataAccess dataAccess = DataAccess.getInstance();
    private static Connection connection = dataAccess.getConnection();
    public static void insertPage(int sourceId,String url,String title, String pubTime, String pubSource, String content) {
        try {
            PreparedStatement pstmt = null;
            Connection conn = null;
            ResultSet rs = null;
            conn = DataAccess.getConnection();
            String sql="insert into pages_2015(source_Id,url,title,pubTime,pubSource,content) values(?,?,?,?,?,?)";
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1,sourceId);
            pstmt.setString(2, url);
            pstmt.setString(3, title);
            pstmt.setString(4,pubTime);
            pstmt.setString(5,pubSource);
            pstmt.setString(6, content);
            pstmt.executeUpdate();
            rs.close();
            conn.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //�����б�
    public static void insertList(String url,int sourceId) {
        PreparedStatement pstmt = null;
        Connection conn = null;
        try {
            conn = DataAccess.getConnection();
            ResultSet rs = null;
            String sql="insert into urlList_2015(url,source_Id) values(?,?)";
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1,url);
            pstmt.setInt(2, sourceId);
            pstmt.executeUpdate();
            rs.close();
            conn.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //ɾ���б�(ÿ��ȡһ����¼��ɾ��ü�¼)
    public static void deleteList(String url) {
        PreparedStatement pstmt = null;
        Connection conn = null;
        try {
            conn = DataAccess.getConnection();
            ResultSet rs = null;
            String sql="delete from urlList_2015 where url='"+url+"'";
            pstmt = connection.prepareStatement(sql);
            pstmt.executeUpdate();
            rs.close();
            conn.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    //�����վ����Ҷ�Ӧ��source_id
    public static int searchSource(String sourceName) {
        PreparedStatement pstmt = null;
        Connection conn = null;
        int source_id=0;
        try {
            conn = DataAccess.getConnection();
            ResultSet rs = null;
            String sql="select id from dataSource_2015 where sourceName=?";
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, sourceName);
            rs = pstmt.executeQuery();
            if(rs.next())
            {
                 source_id= rs.getInt(1);
            }
            rs.close();
            conn.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return source_id;
    }

    //ͳ����վÿ�����ȡ����
    public static void Sum(int sourceId,int num) {
        PreparedStatement pstmt = null;
        Connection conn = null;
        try {
            conn = DataAccess.getConnection();
            ResultSet rs = null;
            String sql="update dataSource_2015 set cur_day_listSize=? where id="+sourceId;
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1,num);
            pstmt.executeUpdate();
            rs.close();
            conn.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
