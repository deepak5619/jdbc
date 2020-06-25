//SelectTest6.java
package com.nit.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/* app to count the no of employees
 *  in emp table
 */

public class SelectTest6 {

	public static void main(String[] args) {
		Connection con=null;
		Statement st=null;
		ResultSet rs=null;
	
		try {
//			register jdbc driver
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
//			establish the connection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","scott","tiger");
			
//			create statement
			if(con!=null)
				st=con.createStatement();
			
//			prepare query
//			select count(*) from emp;
			String query="SELECT COUNT(*) FROM EMP";
			System.out.println(query);
//			send and execute query
			if(st!=null) {
				rs=st.executeQuery(query);
			}
			
//			process the ResultSet
			if(rs!=null) {
				if(rs.next()) {
					System.out.println(rs.getInt(1));
				}//if
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
		}//finally
	}//main

}//class
