package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    
    private static Connection myConnection = null;
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/volunteersystem";
    private static final String DB_USERNAME = "root";
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_PASSWORD = "";

	 public static Connection getConnection() { 
	  try { 
	  
	   Class.forName(DB_DRIVER); 
	   try { 
	    
	    myConnection = DriverManager.getConnection(JDBC_URL,DB_USERNAME,DB_PASSWORD); 
	    System.out.println("Connected"); 
	   } 
	   catch (SQLException ex) { 
	    ex.printStackTrace(); 
	   } 
	 
	  } 
	  catch  (ClassNotFoundException e) { 
	   System.out.println(e); 
	  } 
	  return myConnection; 
	 } 
}	
