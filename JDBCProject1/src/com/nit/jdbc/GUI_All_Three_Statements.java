package com.nit.jdbc;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class GUI_All_Three_Statements extends JFrame implements ActionListener{
	
	private static final String student_sno_query="SELECT SNO FROM ALL_STUDENT";
    private static final String student_marks_query="SELECT SNAME,M1,M2,M3 FROM ALL_STUDENT WHERE SNO=?";
    private static final String student_result_query="{call p_find_pass_fail(?,?,?,?)}";
    
	private JLabel lno,lname,lm1,lm2,lm3,lresult;
    private JTextField tname,tm1,tm2,tm3,tresult;
    private JComboBox<Integer> tno;
    private JButton bDetails,bResults;
    private Connection con=null;
    private Statement st=null;
    private PreparedStatement ps=null;
    private CallableStatement cs=null;
    ResultSet rs1,rs2;
    
    public GUI_All_Three_Statements(){
		System.out.println("constructor:0-param ");
		setSize(400,400);
		setBackground(Color.GRAY);
		setTitle("Mini project");
		setLayout(new FlowLayout());
		//add components
		lno=new JLabel("select sno.::");
		add(lno);
		tno=new JComboBox<Integer>();
		add(tno);
		bDetails=new JButton("Details");
		add(bDetails);
		bDetails.addActionListener(this);
		lname=new JLabel("sname");
		add(lname);
		tname=new JTextField(10);
		add(tname);
		lm1=new JLabel("Marks1");
		add(lm1);
		tm1=new JTextField(10);
		add(tm1);
		lm2=new JLabel("Marks2");
		add(lm2);
		tm2=new JTextField(10);
		add(tm2);
		lm3=new JLabel("Marks3");
		add(lm3);
		tm3=new JTextField(10);
		add(tm3);
		bResults=new JButton("Result");
		add(bResults);
		bResults.addActionListener(this);
		lresult=new JLabel("Result");
		add(lresult);
		tresult=new JTextField(10);
		add(tresult);
		setVisible(true);
		//disable editing on text boxes

		tm1.setEditable(false);

		tm2.setEditable(false);

		tm3.setEditable(false);

		tname.setEditable(false);

		tresult.setEditable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				//close jdbbc objs
				try {
				  if(rs2!=null)
				    rs2.close();
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
			    	if(cs!=null)
		               cs.close();
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
		
		initialize_jdbc();
	}
    private void initialize_jdbc() {
    	
    	try {
    	  Class.forName("oracle.jdbc.driver.OracleDriver");
    	  con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","scott","tiger");
    	  st=con.createStatement();
    	  ps=con.prepareStatement(student_marks_query);
    	  cs=con.prepareCall(student_result_query);
    	  //register out parameter
    	  cs.registerOutParameter(4, Types.VARCHAR);
    	  rs1=st.executeQuery(student_sno_query);
    	  while(rs1.next()) {
    		  tno.addItem(rs1.getInt(1));
    	  
    	  }
    }//try
    catch(SQLException se)
    {
    	se.printStackTrace();
   	}
    catch(ClassCastException cnf) {
    	cnf.printStackTrace();
    }
    catch(Exception e) {
    	e.printStackTrace();
    }
    finally {
    	try {
    		if(rs1!=null) {
    			rs1.close();
    		}
    	}
    	catch(SQLException se){
    		se.printStackTrace();
    	}
    	try {
    		if(st!=null) {
    			st.close();
    		}
    	}
    	catch(SQLException se) {
    		se.printStackTrace();
    	}
    }//finally
    }//initialize_jdbc
    
    public static void main(String[] args) {
		System.out.println("main method start");
		new GUI_All_Three_Statements();
		System.out.println("main method end");
	}
	@Override
	public void actionPerformed(ActionEvent ae) {
		System.out.println("action performed()");
		int no=0;
		int m1=0,m2=0,m3=0;
		if(ae.getSource()==bDetails) {
			System.out.println("Details button is clicked ");
			no=(int)tno.getSelectedItem();
			try{
				ps.setInt(1,no);
				rs2=ps.executeQuery();
				if(rs2.next()) {
					tname.setText(rs2.getString(1));
					tm1.setText(rs2.getString(2));
					tm2.setText(rs2.getString(3));
					tm3.setText(rs2.getString(4));
				}
				tresult.setText("");
			}//try
			catch(SQLException se) {
				se.printStackTrace();
			}
		}//if
		
		else {
			System.out.println("result button is clicked");
			try{
				m1=Integer.parseInt(tm1.getText());
				m2=Integer.parseInt(tm2.getText());
				m3=Integer.parseInt(tm3.getText());
				cs.setInt(1, m1);
				cs.setInt(2, m2);
				cs.setInt(3, m3);
				cs.execute();
				tresult.setText(cs.getString(4));
				
			}//try
			catch(SQLException se) {
				se.printStackTrace();
			}
		}//else
		
	}//action performed
}
