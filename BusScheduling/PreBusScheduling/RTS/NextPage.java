import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
 
class NextPage extends Login//JFrame
{
	String uS1;
	JFrame frame = new JFrame();
    //JInternalFrame frame;
    
	//NextPage(String uS, JDesktopPane jdpDesktop, Object xx) {
	NextPage(String uS) {
		//JFrame frame = new JFrame(); 
		userNameLabel = setUpLabel("User Name", 10, 75, 150,  20);
		userNameText = setUpTextField(30, 100, 75, 150, 20);	
		oldPasswordLabel = setUpLabel("Old Password", 10, 100, 150,  20);
		oldPasswordText = setUpPasswordField(30, 100, 100, 150, 20);
		oldPasswordText.setToolTipText("Allowed Length is >0 and < 31");
		newPasswordLabel = setUpLabel("New Password", 10, 125, 150,  20);
		newPasswordText = setUpPasswordField(30, 100, 125, 150, 20);
		newPasswordText.setToolTipText("Allowed Length is >0 and < 31");		
		changeButton= setUpButton("Change", 50, 150, 100, 20);
		backButton= setUpButton("Back", 150, 150, 100, 20);
		//Login lg = new Login();
		//System.out.println("openFC:"+openFrameCount);
		//frame=lg.createFrame("Change Password",lg.openFrameCount,++lg.cnt,350,350);
		//openFrameCount=openFrameCount+3;
		//frame=createFrame("Change Password",openFrameCount,++cnt,350,350);
		frame.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		//frame.setTitle("Change Password");
		//frame.setBounds(100,100,350,350);
		frame.setSize(300, 300);       
		frame.getContentPane().setLayout(null);		
		frame.getContentPane().add(userNameLabel);
		frame.getContentPane().add(userNameText);
		frame.getContentPane().add(oldPasswordLabel);
		frame.getContentPane().add(newPasswordLabel);
		frame.getContentPane().add(oldPasswordText);
		frame.getContentPane().add(newPasswordText);
		frame.getContentPane().add(changeButton);
		frame.getContentPane().add(backButton);
		userNameText.setText(uS);
		//System.out.println("US::"+uS);
		uS1=uS;
		//System.out.println("US1::"+uS1);
		userNameText.setEditable(false);
		frame.setResizable(false);
		//frame.setClosable(false);
		frame.setVisible(true);
		
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.setVisible(false);
			}
		});
		
	
		changeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CommonService x1 = new CommonService();
				Connection c1 = x1.initiateCon();
				//System.out.println("into Change");
				String sql;
				try {
					sql = "UPDATE ComplaintUser SET Password=? WHERE Password=? and userName='"+uS1+"' and deleteflag is null";
					//System.out.println("SQL:"+sql);
					PreparedStatement prest = c1.prepareStatement(sql);
					prest.setString(1, newPasswordText.getText().trim().toUpperCase());
					prest.setString(2, oldPasswordText.getText().trim());
					if(newPasswordText.getText().trim().length()!=0 &&  newPasswordText.getText().trim().length()<31) {		
						int count = prest.executeUpdate();
						//System.out.println(count + "row(s) affected");
						if(count>0)	displayMessage("Change Successful");
						else displayMessage("Change Failed");
					}
					else displayMessage("New Password Length either 0 or >30 characters");	
				} catch (Exception e) {
					//System.out.println("ERROR:"+e.getMessage());
					displayMessage("Error: "+e.getMessage());
					CommonService x2 = new CommonService();
					x2.errorLog(""+4, "","", "CHANGE PASSWORD for User:"+uS1+":"+e.getMessage());					
					e.printStackTrace();
				}
				x1.closeCon(c1);
			}
		});
		frame.setDefaultCloseOperation(1);
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
	
	private JLabel oldPasswordLabel;
	private JLabel newPasswordLabel;
	private JTextField oldPasswordText;
	private JTextField newPasswordText;
	private JButton changeButton;
	private JButton backButton;
	private JLabel userNameLabel;
	private JTextField userNameText;
	
}