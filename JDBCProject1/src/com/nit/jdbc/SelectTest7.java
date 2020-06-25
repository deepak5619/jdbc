//SelectTest7.java
package com.nit.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/*  App for calculating
 *  nth highest sallery
 */

public class SelectTest7 {

	public static void main(String[] args) {
		Scanner sc=null;
		Connection con =null;
		Statement st =null;
		ResultSet rs=null;
		try {
//			read inputs
			sc=new Scanner(System.in);
			System.out.println("Enter n for nth highest sallery");
			int n=sc.nextInt();
			
//			register jdbc driver
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
//			Establish the connection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","scott","tiger");
			
//			create statement
			if(con!=null)
				st=con.createStatement();
			
//			prepare query
//			select * from(select empno,ename,sal,deptno, dense_rank() over(order by sal desc) r from emp) where n=1;
			String query="SELECT * FROM(SELECT EMPNO,ENAME,SAL,DEPTNO, DENSE_RANK() OVER(ORDER BY SAL DESC) R FROM EMP) WHERE R="+n;
		    System.out.println(query);
//			send and execute query
			if(st!=null) {
				rs=st.executeQuery(query);
			}
			
//			process the result
			if(rs!=null) {
				while(rs.next()) {
					System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getInt(3)+" "+rs.getInt(4));
				}//while
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
