package com.liaohanqi.gmall.cart;

import java.sql.*;

public class jdbc {
    String dbUrl="jdbc:oracle:thin:@127.0.0.1:1521:orcl";
    String theUser="admin";
    String thePw="manager";
    Connection c=null;
    Statement conn;
    ResultSet rs=null;
    public jdbc() {
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
            c = DriverManager.getConnection(dbUrl,theUser,thePw);
            conn=c.createStatement();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public boolean executeUpdate(String sql) {
        try {
            conn.executeUpdate(sql);
            return true;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public ResultSet executeQuery(String sql) {
        rs=null;
        try {
            rs=conn.executeQuery(sql);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }
    public void close() {
        try {
            conn.close();
            c.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        ResultSet rs;
        jdbc conn = new jdbc();
        rs=conn.executeQuery("select * from test");
        try{
            while (rs.next()) {
                System.out.println(rs.getString("id"));
                System.out.println(rs.getString("name"));
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}
