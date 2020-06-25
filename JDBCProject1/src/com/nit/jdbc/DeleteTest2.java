//DeleteTest2.java
package com.nit.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/*  App to delete student details based on
 * given start and end range of student
 */

public class DeleteTest2{

	public static void main(String[] args) {
		Scanner sc=null;
		Connection con =null;
		Statement st =null;
		int result=0;
		try {
//			read inputs
			sc=new Scanner(System.in);
			System.out.println("Enter start range");
			int start=sc.nextInt();
			System.out.println("Enter end range");
			int end=sc.nextInt();
			
//			register jdbc driver
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
//			Establish the connection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","scott","tiger");
			
//			create statement
			if(con!=null)
				st=con.createStatement();
			
//			prepare query
//			delete student where sno beween start and end;
			String query="DELETE STUDENT WHERE SNO BETWEEN "+start+" AND "+end;
			System.out.println(query);
//			send and execute query
			if(st!=null) {
			   result=st.executeUpdate(query);
			}
			
//			process the result
			if(result==0)
				System.out.println("no records deleted");
			else
				System.out.println(result+" records deleted");
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
