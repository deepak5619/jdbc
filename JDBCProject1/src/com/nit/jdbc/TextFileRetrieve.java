package com.nit.jdbc;

import java.io.FileWriter;
import java.io.Reader;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class TextFileRetrieve {
    private static final String query="SELECT ARTSID,ARTSNAME,ARTSADDRS FROM ARTIST_PHOTO WHERE ARTSID=?";
	public static void main(String[] args) {
		Scanner sc=null;
	    Connection con=null;
	    PreparedStatement ps=null;
	    ResultSet rs=null;
	    Reader reader=null;
	    Writer writer=null;
	    char[] buffer=new char[4096];
	    
	    int no=0;
	    String name=null;
		int id=0;
		int count=0;
		try {
			sc =new Scanner(System.in);
			if(sc!=null) {
			   System.out.println("enter artist id");
			   id=sc.nextInt();
			   
			}
			//register jdbc driver
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			//establish jdbc connection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","scott","tiger");
			
			//create statement
			if(con!=null)
				ps=con.prepareStatement(query);
			
//			send and execute query
			if(ps!=null) {
				ps.setInt(1, id);
			    rs=ps.executeQuery();
			}
			if(rs.next()) {
				
			    no=rs.getInt(1);
			    name=rs.getString(2);
			    reader=rs.getCharacterStream(3);
			
//			    create writer stream pointing to empty destination file
				writer=new FileWriter("E:\\Address\\new_Address.txt");
				while((count=reader.read(buffer))!=-1) {
					writer.write(buffer,0,count);
				}
				System.out.println(no+" "+name);
				System.out.println("CLOB value retrieved");
			}//if
			else
				System.out.println("record is not there");
			
			
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
			try {
				if(reader!=null)
					reader.close();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			try {
				if(writer!=null)
					writer.close();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}//finally
	}
}
