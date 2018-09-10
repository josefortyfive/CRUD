package com.ph.db;

import java.sql.*;

public class Database {

	public static Connection conn = null;
	public static Statement st;
	public static ResultSet rs;
	public static PreparedStatement pst = null;
	public static String driver = "com.mysql.jdbc.Driver";
	public static String url = "jdbc:mysql://localhost:3306/employee_info";
	
	public static Connection ConnectDB(){
		try{
			Class.forName(driver);
			conn = DriverManager.getConnection(url, "root", "");
						
			System.out.println("Nice Catch!");
			return conn;
		}
		catch(Exception err){
			System.out.println("Error in: "+err);
			
		}
		return null;
	}
}