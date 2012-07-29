//Split Search Screens page
//Functionality: Calls functionalities tied to Administrator Actor
//CreatedBy : Mohan
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class SplitSearchScreens extends Login { //JFrame { //JPanel {
	//JFrame frame;
	int uSer;
	int accessType;
	
	public SplitSearchScreens(int uS){
		uSer=uS;
	}
	
	public SplitSearchScreens() {}

	public static void main(String[] args) {
		SplitSearchScreens gui = new SplitSearchScreens();
		gui.go();
	}

	void go() {
		frame = new JFrame();
		// a frame represents a window on the screen
		spCAllEntitiesButton = setUpButton("Shortest Path Covering All Entities", 50, 140, 450, 25);
		sprpCAllEntitiesButton = setUpButton("Shortest RoundTrip Path Covering all Entities with Priority Order", 50, 180, 450, 25);
		sprwpCAllEntitiesButton = setUpButton("Shortest RoundTrip Path Covering all Entities without Priority Order", 50, 220, 450, 25);
		exitButton = setUpButton("Exit", 50, 260, 450, 25);

		//System.out.println("Admin openFC:"+openFrameCount);
		//frame=lg.createFrame("Change Password",lg.openFrameCount,++lg.cnt,350,350);
		//openFrameCount=openFrameCount+3;
		//frame=createFrame("Administrator Screen",openFrameCount,++cnt,450,400);
		
		frame.getContentPane().setLayout(null);

		// buttons
		frame.getContentPane().add(spCAllEntitiesButton);
		frame.getContentPane().add(sprpCAllEntitiesButton);
		frame.getContentPane().add(sprwpCAllEntitiesButton);
		frame.getContentPane().add(exitButton);
		frame.setTitle("Split Search Screens");
		frame.setSize(550, 400);
		//frame.setBounds(100,100,450,400);
		frame.setResizable(false);
		//frame.setClosable(false);
		frame.setVisible(true);
	
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		
		spCAllEntitiesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Category com2 = new Category(uSer);			
				//categoryButton.setEnabled(false);
				//com2.go();
				SplitSearch splitObj = new SplitSearch();
				splitObj.SplitSearch();
			}
		});
		
		sprpCAllEntitiesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//UserAccess com3 = new UserAccess(uSer);
				//userAccessButton.setEnabled(false);
				//com3.go();				
			}
		});
		
		sprwpCAllEntitiesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {	
				try {
				//CommonService com4 = new CommonService();
				//com4.backup(com4,"Complaint");				
				//com4.backup(com4,"ComplaintUser");
				//com4.backup(com4,"ComplaintCategory");
				//com4.backup(com4,"ComplaintStatus");
				//com4.backup(com4,"ErrorLog");
				//displayMessage("Backup Done");
				}
				catch (Exception e) {
					displayMessage("Backup Failed");
				}
				
			}
		});
	
		//frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

	private JLabel setUpLabel(String text, int x, int y, int width, int height) {
		JLabel label = new JLabel(text);
		label.setBounds(x, y, width, height);
		return label;
	}

	public static void displayMessage(String message) {
		JFrame frame = new JFrame();
		JOptionPane.showMessageDialog(frame, message);
	}

	private JButton spCAllEntitiesButton;
	private JButton sprpCAllEntitiesButton;
	private JButton sprwpCAllEntitiesButton;
	private JButton exitButton;
	
}