package com.nit.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

/*App to insert person id,name,DOB, DOJ , and DOM
in Person_Date_Tab table
*/

public class DateInsert {
    private static final String Insert_Date_Query="INSERT INTO PERSON_DATE_TAB VALUES(?,?,?,?,?)";
	public static void main(String[] args) {
		Scanner sc=null;
		Connection con=null;
		PreparedStatement ps=null;
		int no=0;
		int result=0;
		String name=null,dob=null,doj=null,dom=null;
		SimpleDateFormat sdf1=null,sdf2=null,sdf3=null;
		java.util.Date udob=null,udoj=null,udom=null;
		java.sql.Date sdob=null,sdoj=null,sdom=null;
		long ms1=0L;
		try{
			//reading inputs
			sc=new Scanner(System.in);
			System.out.println("enter person id::");
			no=sc.nextInt();
			System.out.println("enter person name::");
			name=sc.next();
			System.out.println("enter DOB(dd-MM-yyyy)");
			dob=sc.next();
			System.out.println("enter DOJ(yyyy-MM-dd)");
			doj=sc.next();
			System.out.println("enter DOM(MM-dd-yyyy)");
			dom=sc.next();
			
			//converting String date values to java.sql.date format
			sdf1=new SimpleDateFormat("dd-MM-yyyy");
			udob=sdf1.parse(dob);//it will gives util date format
			ms1=udob.getTime(); //gives milliseconds of given date
			sdob=new java.sql.Date(ms1);
			
			sdf2=new SimpleDateFormat("yyyy-MM-dd");
			udoj=sdf2.parse(doj);
			sdoj=new java.sql.Date(udoj.getTime());
			
			sdf3=new SimpleDateFormat("MM-dd-yyyy");
			udom=sdf3.parse(dom);
			sdom=new java.sql.Date(udom.getTime());
			
			//register jdbc driver
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			//establish the connection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","scott","tiger");
			
			//create prepare statement
			if(con!=null)
				ps=con.prepareStatement(Insert_Date_Query);
			
			//set values to the query params
			if(ps!=null) {
				ps.setInt(1,no);
				ps.setString(2, name);
				ps.setDate(3, sdob);
				ps.setDate(4, sdoj);
				ps.setDate(5, sdom);	
			}
			
			//execute the query
			if(ps!=null) {
				result=ps.executeUpdate();
			}
			//process the result
			if(result==0)
				System.out.println("record not inserted");
			
			else
				System.out.println("record inserted");
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
