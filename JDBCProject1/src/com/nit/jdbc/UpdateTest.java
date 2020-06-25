//updateTest.java
package com.nit.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/* App to reduce commission from employee sal if
 * employee having commission
 */
public class UpdateTest {

	public static void main(String[] args) {
		Connection con=null;
		Statement st=null;
		String query=null;
		int result=0;
		try {
//			register driver
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
//			establish the connection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","scott","tiger");
			
//			create statement
			if(con!=null)
				st=con.createStatement();
			
//			prepare query
			query="UPDATE EMP SET SAL=SAL-NVL(COMM,0)";
			System.out.println(query);
			
//		    send and execute query
			if(st!=null) {
				result=st.executeUpdate(query);
			}
		    if(result==0)
			   System.out.println("no rows updated");
		    else
			   System.out.println(result+" no of rows are updated");
			
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
		}//finally

	}//main

}//class
