package com.nit.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class PersonAge {
	private static final String Person_Age_Query="SELECT ROUND((SYSDATE-DOB)/365.25) FROM PERSON_DATE_TAB WHERE PID=?";
    public static void main(String[] args){
		Scanner sc=null;
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs =null;
		int no=0;
    	try{
//    		read input
    		sc=new Scanner(System.in);
    		System.out.println("enter person id");
    		no=sc.nextInt();
    		
    		//register jdbc driver
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			//establish the connection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","scott","tiger");
			
			//create prepare statement
			if(con!=null)
				ps=con.prepareStatement(Person_Age_Query);
			
			//set input value to sql param
			if(ps!=null) {
				ps.setInt(1,no);
			}
			//execute the query
			if(ps!=null) {
				rs=ps.executeQuery();
			}
//			process the result
			if(rs!=null)
              if(rs.next())
		         System.out.println("Age of person is :"+rs.getInt(1));
              else
			     System.out.println("person with id "+no+" is not present");
    	}//try
    	catch(SQLException se) {
			se.printStackTrace();
		}
		catch(ClassNotFoundException cnf) {
			cnf.printStackTrace();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			//close objects
			try {
				if(ps!=null)
					ps.close();
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
			try {
				if(con!=null)
					con.close();
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
			try {
				if(sc!=null)
					sc.close();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}//finally
	}//main
}//class
