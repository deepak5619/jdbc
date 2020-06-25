//SelectTest5.java
package com.nit.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/* app to get employee details
 * based on the given department no.
 */

public class SelectTest5 {

	public static void main(String[] args) {
		Scanner sc=null;
		Connection con=null;
		Statement st=null;
		ResultSet rs=null;
		boolean flag=false;
		try {
//			read inputs
			sc=new Scanner(System.in);
			System.out.println("enter department no:");
			int d=sc.nextInt();
			
//			register jdbc driver
			Class.forName("oracle.jdbc.driver.OracleDriver");

//			establish the connection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","scott","tiger");
			
//			create statement
			if(con!=null)
				st=con.createStatement();
			
//			prepare query
//			select empno,ename,sal,deptno from emp where deptno=n;
			String query="SELECT EMPNO,ENAME,SAL,DEPTNO FROM EMP WHERE DEPTNO="+d;
			System.out.println(query);
			
//			send and execute SQL query
			if(st!=null)
				rs=st.executeQuery(query);
//			process the ResultSet
			if(rs!=null) {
				while(rs.next()) {
					flag=true;
					System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getInt(3)+" "+rs.getInt(4));
				}//while
				if(flag==false)
					System.out.println("no records found");
			}//if
			
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
				if(rs!=null)
					rs.close();
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
			try {
				if(st!=null)
					st.close();
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