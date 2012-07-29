//UserAccess page of Complaint Management System
//Functionality: User Management for Administrator and Manager Actors
//CreatedBy : Mohan
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

public class UserAccess extends Login {//JFrame {
	int result;
	String message;
	int uSer;
	int userIdInt;
	//JFrame frame;
	JLabel label1 = new JLabel();
	private JList accessList;
	//String [] accessOperations ={"Normal", "SolutionProvider", "Manager", "Adminstrator","Adhoc","SrManager"};
	String [] accessOperations ={"Normal", "Adminstrator"};
	//int [] accessOperationValues={1,2,3,4,5};
	private JScrollPane accessScroller;
	public static void main(String[] args) {
		UserAccess gui = new UserAccess(1);
		gui.go();
	}
	
	public UserAccess(int uS){
		uSer=uS;
	}
	
	public UserAccess() {}

	void go() {
		frame = new JFrame();
		// a frame represents a window on the screen
		accessList   = new JList(accessOperations);
		accessList.setVisibleRowCount(4);
		accessList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		accessScroller = new JScrollPane(accessList);
		
		userIdLabel = setUpLabel("UserId", 20, 100, 150,  20);
		userIdText = setUpTextField(100, 100, 100, 100, 20);
		userIdText.setBackground(Color.lightGray);
		userIdText.setBorder(BorderFactory.createLineBorder(Color.gray));
		
		userNameLabel = setUpLabel("User Name",250, 100, 150,  20);
		userNameText = setUpTextField(100, 350, 100, 150, 20);
		userNameText.setToolTipText("Allowed Length is >0 and <31 and Only ALPHA Characters");
		accessLabel = setUpLabel("AccessType", 20, 150, 100,  20);
		accessScroller.setBounds(100, 150, 120, 40);
		emailIdLabel = setUpLabel("Email Id", 250, 150, 100,  20);
		emailIdText = setUpTextField(100, 350, 150, 100, 20);
		emailIdText.setToolTipText("Allowed Length is >=0 and <51");
		creationDateLabel = setUpLabel("Creation Date", 20, 200, 150,  20);
		creationDateText = setUpTextField(100, 100, 200, 100, 20);
		creationDateText.setEditable(false);
		creationDateText.setBackground(Color.lightGray);
		creationDateText.setBorder(BorderFactory.createLineBorder(Color.gray));		
		createdByLabel = setUpLabel("Created By", 250, 200, 150,  20);
		createdByText = setUpTextField(100, 350, 200, 100, 20);
		createdByText.setEditable(false);
		createdByText.setBackground(Color.lightGray);
		createdByText.setBorder(BorderFactory.createLineBorder(Color.gray));
	
		passwordLabel = setUpLabel("Password", 20, 250, 150,  20);		
		passwordText = setUpTextField(20, 100, 250, 100, 20);
		passwordText.setToolTipText("Allowed Length is >0 and <31");
		phoneNoLabel = setUpLabel("Phone No", 250, 250, 100,  20);
		phoneNoText = setUpTextField(100, 350, 250, 100, 20);
		phoneNoText.setToolTipText("Allowed Length is >=0 and <31");
		//endDateLabel = setUpLabel("End Date", 20, 300, 150,  20);
		//endDateText = setUpTextField(100, 100, 300, 100, 20);
		
		createButton= setUpButton("Create", 50, 350, 100, 20);
		queryButton = setUpButton("Query", 150, 350, 100, 20);
		listButton = setUpButton("List", 250, 350, 100, 20);
		modifyButton = setUpButton("Modify", 350, 350, 100, 20);		
		deleteButton = setUpButton("Delete", 50, 380, 100, 20);
		resetButton = setUpButton("Reset", 150, 380, 100, 20);
		backButton = setUpButton("Back", 250, 380, 100, 20);
		exitButton = setUpButton("Exit", 350, 380, 100, 20);

		//System.out.println("UserAccessopenFC:"+openFrameCount);
		//frame=lg.createFrame("Change Password",lg.openFrameCount,++lg.cnt,350,350);
		//openFrameCount=openFrameCount+3;
		//frame=createFrame("UserAccess Screen",openFrameCount,++cnt,600,500);
		
		frame.getContentPane().setLayout(null);
		frame.getContentPane().add(accessLabel);
		frame.getContentPane().add(userIdLabel);
		frame.getContentPane().add(userIdText);
		frame.getContentPane().add(creationDateLabel);
		frame.getContentPane().add(creationDateText);
		frame.getContentPane().add(passwordLabel);
		frame.getContentPane().add(passwordText);
		//frame.getContentPane().add(endDateLabel);
		//frame.getContentPane().add(endDateText);
		frame.getContentPane().add(userNameLabel);
		frame.getContentPane().add(userNameText);
		frame.getContentPane().add(emailIdLabel);
		frame.getContentPane().add(emailIdText);
		frame.getContentPane().add(createdByLabel);
		frame.getContentPane().add(createdByText);
		frame.getContentPane().add(phoneNoLabel);
		frame.getContentPane().add(phoneNoText);
		
		//buttons
		frame.getContentPane().add(createButton);
		frame.getContentPane().add(modifyButton);
		frame.getContentPane().add(queryButton);
		frame.getContentPane().add(listButton);
		frame.getContentPane().add(deleteButton);
		frame.getContentPane().add(exitButton);
		frame.getContentPane().add(resetButton);
		frame.getContentPane().add(backButton);		
		frame.getContentPane().add(accessScroller);
		//frame.setTitle("User Access Screen");
		//frame.setResizable(false);
		//frame.setClosable(false);
		frame.setBounds(100,100,600,500);
		//frame.setSize(600, 500);
		frame.setVisible(true);
		accessList.setSelectedIndex(0); //to set default accesslist as Normal		
		createButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			    Date date = new Date(0000-00-00);
				//System.out.println("into add"+uSer+":::");
				CommonService x1 = new CommonService();
				Connection c1 = x1.initiateCon();
				//int userIdInt=x1.sequence("User");
				//System.out.println("userIdInt:"+userIdInt);
			     try{
			    	String sql = "INSERT into test.User VALUES(?,upper(?),curdate(),?,?,?,?,?,?,?)";
			        PreparedStatement prest = c1.prepareStatement(sql);			    	
					//String userIdStr = userIdText.getText().trim();
					//int userIdInt = Integer.parseInt(userIdStr);					
					String userNameStr = userNameText.getText().trim().toUpperCase();
					//String createdByStr = createdByText.getText().trim();
					//int createdByInt = Integer.parseInt(createdByStr);
					String passwordStr = passwordText.getText().trim();
					String emailIdStr = emailIdText.getText().trim();
					String phoneNoStr = phoneNoText.getText().trim();
					
					int accessInt = accessList.getSelectedIndex();
					int createdByInt = uSer;
					if(userNameStr.length()!=0 && passwordStr.length()!=0 && x1.fieldLength(userNameStr, 30) && x1.fieldLength(passwordStr, 30)) {
						if(emailIdStr.length()==0 || x1.fieldLength(emailIdStr, 50)) {
							if(phoneNoStr.length()==0 || x1.fieldLength(phoneNoStr, 30)) {
								int userIdInt=x1.sequence("User");
						        prest.setInt(1, userIdInt);
						        prest.setString(2, userNameStr);
								//prest.setDate(3,date.valueOf("1998-1-17"));
								prest.setInt(3, createdByInt);
						        prest.setString(4, passwordStr);
						        prest.setString(5, emailIdStr);
						        prest.setString(6, phoneNoStr);
						        prest.setInt(7, accessInt);
								//prest.setDate(8, date.valueOf("1998-1-18"));
						        prest.setDate(8, null);
						        prest.setString(9, null);
				
								int count = prest.executeUpdate();					
								//System.out.println(count + "row(s) affected");	
								Calendar cal = new GregorianCalendar();
							    int month = cal.get(Calendar.MONTH);
							    int year = cal.get(Calendar.YEAR);
							    int day = cal.get(Calendar.DAY_OF_MONTH);
							    //System.out.println("Current date : " + day + "/" + (month + 1) + "/" + year);						    					
								//createdByText.setText(""+uSer);
								ResultSet createdByrs1;
								String q1 = "select userName from complaintuser where userid="
										+ uSer;
								createdByrs1 = x1.getResultSet(c1, q1);
								createdByrs1.first();
								createdByText.setText(createdByrs1.getString(1));
								createdByText.setCaretPosition(0);
								createdByText.setAutoscrolls(true);
								userIdText.setText(""+userIdInt);
								creationDateText.setText(year+"-"+(month+1)+"-"+day);
								userNameText.setText(userNameStr);
								//if (count > 0) {
									//displayMessage("Creation Successful");
									//Login logcat = new Login();
									//logcat.owner();							
								//}
								//else displayMessage("Creation Failed");
							}
							else displayMessage("PhoneNo > 30 Characters");
						}
						else displayMessage("EmailId > 50 Characters");
					}
					else displayMessage("Either UserName OR Password is null,OR either UserName field content greater than 30 characters");
			       }
			       catch (Exception e){
						//System.out.println("ERROR:"+e.getMessage());
						if(e.getMessage().equals("For input string: \"\""))	displayMessage("UserId need to be provided as Input");
						else displayMessage("ERROR:"+e.getMessage());
						CommonService x2 = new CommonService();
						x2.errorLog(""+2, "",""+uSer, "CREATE:"+e.getMessage());						
						e.printStackTrace();
			       }
			       x1.closeCon(c1);		
			       frame.setVisible(true);
			}
		});

		listButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//System.out.println("into List");
				CommonService x1 = new CommonService();
				Connection c1 = x1.initiateCon();
				//String q1 = "SELECT * from ComplaintUser WHERE DeleteFlag is NULL";
				String q1="SELECT c.userid,c.username,c.creationdate,c1.username createdByUserName,c.password,c.emailid,"+
				" a.accesstypename,c.phone FROM test.useraccesstype a,test.user c1,test.user c "+
				"where a.accesstypeid=c.accesstype and c1.userid=c.createdby and c.deleteflag is null"+
				" order by c.userid";
				//System.out.println("Query"+q1);
				ResultSet rs1;
				rs1 = x1.getResultSet(c1, q1);
				x1.sqlCon2(rs1);
				x1.closeCon(c1);
			}
		});
		
		queryButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CommonService x1 = new CommonService();
				Connection c1 = x1.initiateCon();

				try{
					//System.out.println("into Query");
					String userIdStr = userIdText.getText().trim();
					userIdInt = Integer.parseInt(userIdStr);
					String q1 = "SELECT * from test.User WHERE DeleteFlag is null and userId="+userIdInt;
					//System.out.println("Query:"+q1);
					ResultSet rs1;
					rs1 = x1.getResultSet(c1, q1);
					rs1.first();
					userNameText.setText(rs1.getString(2));
					creationDateText.setText(rs1.getString(3));
					ResultSet createdByrs1;
					String q11 = "select userName from test.user where userid="
							+ rs1.getString(4);
					createdByrs1 = x1.getResultSet(c1, q11);
					createdByrs1.first();
					createdByText.setText(createdByrs1.getString(1));					
					createdByText.setCaretPosition(0);
					createdByText.setAutoscrolls(true);					
					//createdByText.setText(rs1.getString(4));					
					passwordText.setText(rs1.getString(5));
					emailIdText.setText(rs1.getString(6));
					phoneNoText.setText(rs1.getString(7));
					//int accessInt = accessList.getSelectedIndex();
					//System.out.println("ACT:"+rs1.getInt(8));
					accessList.setSelectedIndex(rs1.getInt(8));
					//endDateText.setText(rs1.getString(9));
					frame.setVisible(true);					
		       }
		       catch (Exception e){
					//System.out.println("ERROR:"+e.getMessage());
					if(e.getMessage().equals("Illegal operation on empty result set.")) displayMessage("Invalid UserId OR UserId has been deleted earlier");
					else if(e.getMessage().equals("For input string: \"\""))	displayMessage("UserId need to be provided as Input");
					else displayMessage("ERROR:"+e.getMessage());
					CommonService x2 = new CommonService();
					x2.errorLog(""+2, ""+userIdInt,""+uSer, "QUERY:"+e.getMessage());					
		    	   e.printStackTrace();
					userIdText.setText("");
					userNameText.setText("");
					creationDateText.setText("");
					createdByText.setText("");					
					passwordText.setText("");
					emailIdText.setText("");
					phoneNoText.setText("");
					accessList.clearSelection();
		       }
				x1.closeCon(c1);
			}
		});
		
		modifyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CommonService x1 = new CommonService();
				Connection c1 = x1.initiateCon();
				//System.out.println("into Modify");
				try{
			    	String sql = "UPDATE test.User SET userName=upper(?), password=?, emailid=?,phone=?,accesstype=? WHERE DeleteFlag is NULL and userId=?";
			    	PreparedStatement prest = c1.prepareStatement(sql);			    	
					String userIdStr = userIdText.getText().trim();
					userIdInt = Integer.parseInt(userIdStr);					
					String userNameStr = userNameText.getText().trim().toUpperCase();
					if(userNameStr.length()!=0 && passwordText.getText().length()!=0 && x1.fieldLength(userNameStr, 30) && x1.fieldLength(passwordText.getText(), 30)) {
						if(emailIdText.getText().trim().length()==0 || x1.fieldLength(emailIdText.getText().trim(), 50)) {
							if(phoneNoText.getText().trim().length()==0 || x1.fieldLength(phoneNoText.getText().trim(), 30)) {						
								prest.setInt(6, userIdInt);
						        prest.setString(1, userNameStr);			        
								prest.setString(2,passwordText.getText().trim());
								prest.setString(3,emailIdText.getText().trim());
								prest.setString(4,phoneNoText.getText().trim());
								prest.setInt(5,	accessList.getSelectedIndex());
								//endDateText.setText(rs1.getString(9));
						        			        
								int count = prest.executeUpdate();					
								//System.out.println(count + "row(s) affected");
								//if (count > 0) {
									//displayMessage("Modification Successful");
									//Login logcat = new Login();
									//logcat.owner();												
								//}
								//else displayMessage("Modification Failed");
							}
							else displayMessage("PhoneNo > 30 Characters");
						}
						else displayMessage("EmailId > 50 Characters");
					}
					else displayMessage("Either UserName OR Password is null,OR either UserName OR CategoryName field content greater than 30 characters");		        
		       }
		       catch (Exception e){ 
					//System.out.println("ERROR:"+e.getMessage());
					if(e.getMessage().equals("For input string: \"\""))	displayMessage("UserId need to be provided as Input");
					else displayMessage("ERROR:"+e.getMessage());
					CommonService x2 = new CommonService();
					x2.errorLog(""+2, ""+userIdInt,""+uSer, "MODIFY:"+e.getMessage());										
		    	   e.printStackTrace();
		       }
				x1.closeCon(c1);
			}
		});

		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CommonService x1 = new CommonService();
				Connection c1 = x1.initiateCon();
				//System.out.println("into Delete");
				try{
			    	String sql = "UPDATE test.user SET DeleteFlag='Y' WHERE DeleteFlag is NULL and UserId=?";
			        PreparedStatement prest = c1.prepareStatement(sql);			    	
					String userIdStr = userIdText.getText().trim();
					userIdInt = Integer.parseInt(userIdStr);					
			        prest.setInt(1, userIdInt);
					int count = prest.executeUpdate();					
					//System.out.println(count + "row(s) affected");
					//if (count > 0) {
						//displayMessage("Deletion Successful");
						//Login logcat = new Login();
						//logcat.owner();						
					//}
					//else displayMessage("Deletion Failed");
					
		       }
		       catch (Exception e){ 
					//System.out.println("ERROR:"+e.getMessage());
					if(e.getMessage().equals("For input string: \"\""))	displayMessage("UserId need to be provided as Input");
					else displayMessage("ERROR:"+e.getMessage());
					CommonService x2 = new CommonService();
					x2.errorLog(""+2, ""+userIdInt,""+uSer, "DELETE:"+e.getMessage());										
		    	   e.printStackTrace();
		       }
				x1.closeCon(c1);
			}
		});
		
		
		
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.setVisible(false);
			}
		});

		
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				userIdText.setText("");
				userNameText.setText("");
				creationDateText.setText("");
				createdByText.setText("");					
				passwordText.setText("");
				emailIdText.setText("");
				phoneNoText.setText("");
				accessList.clearSelection();
				//endDateText.setText("");
				frame.setVisible(true);					

			}
		});


		userIdText.addFocusListener(new FocusAdapter() {
	        public void focusLost(FocusEvent e) {
	          JTextField textField =
	            (JTextField)e.getSource();
	          String content = textField.getText();
	          if (content.length() != 0) {
	            try {
	              Integer.parseInt(content);
	            } catch (NumberFormatException nfe) {
	              //getToolkit().beep();
	              textField.requestFocus();
	            }
	          }
	        }
	      });
		/*
		passwordText.addFocusListener(new FocusAdapter() {
	        public void focusLost(FocusEvent e) {
	          JTextField textField =
	            (JTextField)e.getSource();
	          String content = textField.getText();
	          //System.out.println("pa:"+content);
	          if (content.length() == 0 || content.length()>30) {
	              getToolkit().beep();
	             // System.out.println("pabeep:"+content);
	              textField.requestFocus();
	          }
	        }
	      });
		*/
		userNameText.addFocusListener(new FocusAdapter() {
	        public void focusLost(FocusEvent e) {
		          JTextField textField =
		            (JTextField)e.getSource();
		          String content = textField.getText();
		          //System.out.println("username:"+content);
		          if (content.length() == 0 || content.length()>30 || !content.matches("^[A-Za-z]+$") ) {		        	  
		        	  //System.out.println("usernamebeep:"+content);
		              //getToolkit().beep();	              
		              textField.requestFocus();
		          }
		        }
		      });
		
	//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	//frame.setVisible(true);
	}
	private JButton setUpButton(String text, int x, int y, int width, int height) {
		JButton button = new JButton(text);
		button.setBounds(x, y, width, height);
		return button;
	}

	private JTextField setUpTextField(int columns, int x, int y, int width, int height) {
		JTextField textField = new JTextField(columns);
		textField.setBounds(x, y, width, height);
		return textField;
	}

	private JLabel setUpLabel(String text, int x, int y, int width, int height) {
		JLabel label = new JLabel(text);
		label.setBounds(x, y, width, height);
		return label;
	}

	public static void displayMessage(String message) {
		JFrame frame = new JFrame();
		JOptionPane.showMessageDialog(frame, message);
	}

	private JLabel accessLabel;
	private JLabel userIdLabel;
	private JLabel creationDateLabel;
	private JLabel passwordLabel;
	private JLabel endDateLabel;
	private JLabel userNameLabel;
	private JLabel emailIdLabel;
	private JLabel phoneNoLabel;
	private JLabel createdByLabel;
	private JTextField userIdText;
	private JTextField creationDateText;
	private JTextField passwordText;
	private JTextField endDateText;
	private JTextField userNameText;
	private JTextField emailIdText;
	private JTextField createdByText;
	private JTextField phoneNoText;
	private JTextField numberText2;
	private JButton createButton;
	private JButton listButton;
	private JButton queryButton;
	private JButton modifyButton;
	private JButton deleteButton;
	private JButton backButton;
	private JButton exitButton;
	private JButton resetButton;
}
