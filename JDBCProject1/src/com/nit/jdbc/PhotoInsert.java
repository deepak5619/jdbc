package com.nit.jdbc;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class PhotoInsert {
    private static final String query="INSERT INTO ARTIST_PHOTO (ARTSID,ARTSNAME,ARTSPHOTO) VALUES(?,?,?)";
	public static void main(String[] args) {
		Scanner sc=null;
		int id=0;
		String name=null;
		String path=null;
		File file=null;
		InputStream is=null;
		Connection con=null;
		PreparedStatement ps=null;
		int result=0;
		try {
			sc = new Scanner(System.in);
			if(sc!=null) 
			{
				System.out.println("enter artist id::");
				id=sc.nextInt();
				System.out.println("enter artist name::");
				name=sc.next();
				System.out.println("enter photo path::");
				path=sc.next();
				
		    }
			file=new File(path);
			is=new FileInputStream(file);
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","scott","tiger");
			
			if(con!=null) {
				ps=con.prepareStatement(query);
			}
			if(ps!=null) {
				ps.setInt(1, id);
				ps.setString(2, name);
				ps.setBlob(3,is, file.length());
			}
			if(ps!=null) {
				result=ps.executeUpdate();
			}
			if(result==0) {
				System.out.println("result can not be inserted");
			}
			else
				System.out.println("record inserted");
			
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
