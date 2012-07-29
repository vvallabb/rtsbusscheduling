//Main Login page of Complaint Management System
//Functionality: It calls Complaint/Admin/Manager Screens depending on the User AccessType
//CreatedBy : Mohan

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

class Login //extends JFrame // JPanel
{
	
    //static JDesktopPane jdpDesktop;
    //static int openFrameCount;
    //static int cnt;
    //Object g;
    //JInternalFrame frame;
    JFrame frame = new JFrame();
	
	//JInternalFrame frame;
	//JFrame frame = new JFrame();
    //JLayeredPane desktop;
    //JInternalFrame frame;
       
	void go(Object gui) {
		//new JFrame();
		//g=gui;
	    //super("Complaint Management System");
		//setTitle("Complaint Management System");
	    //setSize(1280,800);
	    //setSize(500,400);
		//setResizable(false);	    
	    //jdpDesktop = new JDesktopPane(); //a specialized layered pane to be used with JInternalFrames
	    //frame=createFrame("Login Screen ",openFrameCount,++cnt,350,350); //Create first window
        //setContentPane(jdpDesktop);
        //openFrameCount = openFrameCount+3;
        //createFrame(openFrameCount,300,300); //Create first window	    

		userNameLabel = setUpLabel("User Name", 30, 100, 140, 20);
		userNameText = setUpTextField(30, 100, 100, 140, 20);
		passwordLabel = setUpLabel("Password", 30, 150, 140, 20);
		passwordText = setUpPasswordField(30, 100, 150, 140, 20);

		submitButton = setUpButton("Login", 30, 200, 140, 20);
		resetButton = setUpButton("Reset", 170, 220, 140, 20);
		setpasswordButton = setUpButton("Change Password", 30, 220, 140, 20);
		exitButton = setUpButton("Exit", 170, 200, 140, 20);

		frame.getContentPane().setLayout(null);
		frame.getContentPane().add(userNameLabel);
		frame.getContentPane().add(userNameText);
		frame.getContentPane().add(passwordLabel);
		frame.getContentPane().add(passwordText);
		frame.getContentPane().add(submitButton);
		// frame.getContentPane().add(resetButton);
		frame.getContentPane().add(setpasswordButton);
		frame.getContentPane().add(exitButton);
		//frame.setLocationRelativeTo(null);
		//frame.setResizable(false);
		//frame.setClosable(false);
		frame.setTitle("Login Screen");
		 
		//frame.setSize(350, 350);
		frame.setBounds(100, 100, 350, 350);
		//System.out.println("into setvi");
		//createFrame(openFrameCount,350,350); //Create first window
		frame.setVisible(true);


		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				userNameText.setText("");
				passwordText.setText("");
				userNameText.setEditable(true);
				passwordText.setEditable(true);
				frame.setVisible(true);

			}
		});

		setpasswordButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String value1 = userNameText.getText();
				if (value1.length() != 0) {
					//NextPage page = new NextPage(value1,jdpDesktop,g);
					NextPage page = new NextPage(value1);
				} else
					JOptionPane.showMessageDialog(frame,
							"UserName is Mandatory", "Error",
							JOptionPane.ERROR_MESSAGE);
			}
		});

		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});

		submitButton.addActionListener(new ActionListener() {
			// public void actionPerformed(ActionEvent arg0) {
			public void actionPerformed(ActionEvent ae) {
				String value1 = userNameText.getText().toUpperCase();
				String value2 = passwordText.getText();
				CommonService x1 = new CommonService();
				Connection c1 = x1.initiateCon();
				try {
					String q1 = "SELECT * from test.User WHERE DeleteFlag is NULL and userName= '"
							+ value1 + "' and password='" + value2 + "'";
					System.out.println("Query:" + q1);
					ResultSet rs1;
					rs1 = x1.getResultSet(c1, q1);
					rs1.first();
					System.out.println("AccessList:" + rs1.getInt(8)+"Val:"+value1);
					//System.out.println("Val:"+value1);
					if (rs1.getInt(8) == 0) { // NormalUser
						System.out.println("Normal User");
						userNameText.setEditable(false);
						passwordText.setEditable(false);
						submitButton.setEnabled(false);
						resetButton.setEnabled(false);
						setpasswordButton.setEnabled(false);
								
						SplitSearch splitObj = new SplitSearch();
						splitObj.SplitSearch();						
						//gui0.go();
					} else if (rs1.getInt(8) == 1) { // Administrator
						System.out.println("Administrator");
						Admin gui3 = new Admin(rs1.getInt(1));
						userNameText.setEditable(false);
						passwordText.setEditable(false);
						submitButton.setEnabled(false);
						resetButton.setEnabled(false);
						setpasswordButton.setEnabled(false);						
						gui3.go();
						//gui3.setVisible(true);
					}
				} catch (Exception e) {
					//System.out.println("enter the valid username and password");
					JOptionPane.showMessageDialog(frame,
							"Incorrect login or password", "Error",
							JOptionPane.ERROR_MESSAGE);
					//CommonService x2 = new CommonService();
					//x2.errorLog(""+4, "","", "LOGIN for User:"+value1+":"+e.getMessage());					
					// e.printStackTrace();
				}
				x1.closeCon(c1);
			}
		});
	}

	public static void main(String arg[]) {
		try {
		
			Login gui = new Login();
			gui.go(gui);
			//gui.setVisible(true);

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}


	private JButton setUpButton(String text, int x, int y, int width, int height) {
		JButton button = new JButton(text);
		button.setBounds(x, y, width, height);
		return button;
	}

	private JTextField setUpTextField(int columns, int x, int y, int width,
			int height) {
		JTextField textField = new JTextField(columns);
		textField.setBounds(x, y, width, height);
		return textField;
	}

	private JTextField setUpPasswordField(int columns, int x, int y, int width,
			int height) {
		JTextField textField = new JPasswordField(columns);
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

    public JInternalFrame createFrame(String tit, int o,int c,int x,int y) {
        //MyInternalFrame frame = new MyInternalFrame(tit,o,c,x,y);
        //frame.setVisible(true); 
        //System.out.println("C::"+c);
        //jdpDesktop.add(frame,new Integer(c));		
        //try {
        	System.out.println("");
          //  frame.setSelected(true);
          //  return frame;
        return null;
        //} catch (java.beans.PropertyVetoException e) {}
        //return null;
    }
	
	
	private JLabel userNameLabel;
	private JLabel passwordLabel;
	private JTextField userNameText;
	private JTextField passwordText;
	private JButton submitButton;
	private JButton resetButton;
	private JButton exitButton;
	private JButton setpasswordButton;
}
