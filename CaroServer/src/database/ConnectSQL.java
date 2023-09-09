package database;

import java.sql.*;


public class ConnectSQL {
    
    public Connection getConnection(){
        Connection conn=null;
        String jdbcURL = "jdbc:sqlserver://localhost:1433;databaseName=Account;encrypt=true;trustServerCertificate=true;";
        String jdbcUsername = "sa";
        String jdbcPassword = "";
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
            if(conn!=null){
                System.out.println("Connection to database succesful");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Connection to database failed");
        }
        return conn;
    }  
}
