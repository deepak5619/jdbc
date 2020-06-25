package com.nit.jdbc;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class TextFileInsert {
    private static final String query="UPDATE ARTIST_PHOTO SET ARTSADDRS=? WHERE ARTSID=?";
	public static void main(String[] args) {
		Scanner sc=null;
		int id=0;
		String path=null;
		File file=null;
		Reader reader=null;
		Connection con=null;
		PreparedStatement ps=null;
		int result=0;
		try {
			sc = new Scanner(System.in);
			if(sc!=null) 
			{
				System.out.println("enter artist id::");
				id=sc.nextInt();
				System.out.println("enter address file path::");
				path=sc.next();
				
		    }
			file=new File(path);
			reader=new FileReader(file);
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","scott","tiger");
			
			if(con!=null) {
				ps=con.prepareStatement(query);
			}
			if(ps!=null) {
				
				ps.setCharacterStream(1,reader,(int)file.length());
				ps.setInt(2, id);
			}
			if(ps!=null) {
				result=ps.executeUpdate();
			}
			if(result==0) {
				System.out.println("record can not be inserted");
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
