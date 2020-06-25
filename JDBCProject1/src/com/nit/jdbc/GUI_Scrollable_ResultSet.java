package com.nit.jdbc;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class GUI_Scrollable_ResultSet extends JFrame implements ActionListener{
	private static final String query="SELECT SNO,NAME,SADD FROM STUDENT";
	
	private JLabel lno,lname,ladd;
	private JTextField tno,tname,tadd;
	private JButton bfirst,blast,bnext,bprev;
	private Connection con=null;
	private PreparedStatement ps=null;
	private ResultSet rs=null;
	
	public GUI_Scrollable_ResultSet(){
    System.out.println("GUI_Scrollable_ResultSet.0 param constructer");
	setTitle("Student details");
	setSize(400,400);
	setBackground(Color.GRAY);
	setLayout(new FlowLayout());
	lno=new JLabel("Student no");
	add(lno);
	tno=new JTextField(10);
	add(tno);
	lname=new JLabel("Student name");
	add(lname);
	tname=new JTextField(10);
	add(tname);
	ladd=new JLabel("Student address");
	add(ladd);
	tadd=new JTextField(20);
	add(tadd);
	bfirst=new JButton("First");
	add(bfirst);
	bfirst.addActionListener(this);
	
	blast=new JButton("Last");
	add(blast);
	blast.addActionListener(this);
	
	bprev=new JButton("Previous");
	add(bprev);
	bprev.addActionListener(this);
	
	bnext=new JButton("Next");
	add(bnext);
	bnext.addActionListener(this);
	
	setVisible(true);
	setDefaultCloseOperation(EXIT_ON_CLOSE);
	this.addWindowListener(new WindowAdapter() {
		public void windowClosing(WindowEvent e) {
			//close jdbbc objs
			try {
			  if(rs!=null)
			    rs.close();
			}
			catch(SQLException se) {
			  se.printStackTrace();
			}
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
		}
	});
	
	initializeJdbc();
	}
	
	private void initializeJdbc() {
		System.out.println("GUI_Scrollable_ResultSet.initializeJdbc()");
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","scott","tiger");
			if(con!=null) {
				ps=con.prepareStatement(query,1005,1008);
			}
			if(ps!=null) {
				rs=ps.executeQuery();
			}
			
		}
		catch(SQLException se) {
			se.printStackTrace();
		}
		catch(ClassNotFoundException cnf) {
			cnf.printStackTrace();
		}
	}

   public static void main(String[] args) {
     System.out.println("GUI_Scrollable_ResultSet.main(start)");
     new GUI_Scrollable_ResultSet();
     System.out.println("GUI_Scrollable_ResultSet.main(end)");
   }


@Override
public void actionPerformed(ActionEvent e) {
	System.out.println("GUI_Scrollable_ResultSet.actionPerformed()");
	boolean flag=false;
	if(e.getSource()==bfirst) {
		System.out.println("first button is clicked");
		flag=true;
		try {
			if(rs!=null)
		       rs.first();
		}
		catch(SQLException se) {
			se.printStackTrace();
		}
	}
	else if(e.getSource()==blast) {
		System.out.println("last button is clicked");
		flag=true;
		try {
			if(rs!=null)
			   rs.last();
		}
		catch(SQLException se) {
			se.printStackTrace();
		}
	}
	else if(e.getSource()==bprev) {
		System.out.println("prev button is clicked");
		try{
    		if(rs!=null & rs.isFirst()==false) {
    	       flag=true;
    	       rs.previous();
    	     }
    		
    	}
    	catch(SQLException se) {
    		se.printStackTrace();
    	}
		
	}
	else {
		System.out.println("next button is clicked");
		try {
			if(rs!=null && rs.isLast()==false) {
    		   flag=true;
    		   rs.next();
    	}
		}
		catch(SQLException se) {
			se.printStackTrace();
		}
	}
	if(flag==true) {
		try {
		   if(rs!=null) {
		     tno.setText(rs.getString(1));
		     tname.setText(rs.getString(2));
		     tadd.setText(rs.getString(3));
		   }
		}
		catch(SQLException se) {
			se.printStackTrace();
		}
	}
}
}