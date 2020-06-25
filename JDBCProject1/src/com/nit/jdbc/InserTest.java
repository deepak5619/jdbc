//insertTest
package com.nit.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/* app to insert values into emp table in columns empno,
 * ename,job and sallery column
 */
public class InserTest {

	public static void main(String[] args) {
		Scanner sc=null;
		Connection con=null;
		Statement st=null;
		String query=null;
		int result=0;
		
		try {
//			read inputs
			sc=new Scanner(System.in);
			System.out.println("enter empno :");
			int no=sc.nextInt();
			System.out.println("enter ename :");
			String name=sc.next();
			System.out.println("enter job :");
			String job=sc.next();
			System.out.println("enter sallery :");
			Float sal=sc.nextFloat();
			
//			convert the inputs as required for the sql query
			name="'"+name+"'";
			job="'"+job+"'";
			
//			register jdbc software
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
//			establish the connection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","scott","tiger");
			
//			create statement
			if(con!=null) {
				st=con.createStatement();
			}
			
//			prepare query
//			insert into emp (empno,ename,job,sal) values(no,name,job,sal);
			query="INSERT INTO EMP (EMPNO,ENAME,JOB,SAL) VALUES("+no+","+name+","+job+","+sal+")";
			System.out.println(query);
			
//			send and execute query
			if(st!=null)
				result=st.executeUpdate(query);
			
//			process the result
			if(result==0) {
				System.out.println(" NO record is inserted");
			}
			else
				System.out.println("record is inserted");
		}
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
