//Admin page of Complaint Management System
//Functionality: Calls functionalities tied to Administrator Actor
//CreatedBy : Mohan
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Admin extends Login { //JFrame { //JPanel {
	//JFrame frame;
	int uSer;
	int accessType;
	
	public Admin(int uS){
		uSer=uS;
	}
	
	public Admin() {}

	public static void main(String[] args) {
		Admin gui = new Admin();
		gui.go();
	}

	void go() {
		frame = new JFrame();
		// a frame represents a window on the screen
		buildDataStoreButton = setUpButton("Build Data Store", 100, 140, 200, 20);
		userAccessButton = setUpButton("Maintain Users", 100, 180, 200, 20);
		splitSearchButton = setUpButton("Split Search", 100, 220, 200, 20);
		busButton = setUpButton("Bus Scheduling", 100, 260, 200, 20);
		//busTestButton = setUpButton("Bus Scheduling Test", 100, 300, 200, 20);
		splitGoldenButton = setUpButton("Create Split Search Golden", 100, 300, 200, 20);		
		exitButton = setUpButton("Exit", 100, 340, 200, 20);

		//System.out.println("Admin openFC:"+openFrameCount);
		//frame=lg.createFrame("Change Password",lg.openFrameCount,++lg.cnt,350,350);
		//openFrameCount=openFrameCount+3;
		//frame=createFrame("Administrator Screen",openFrameCount,++cnt,450,400);
		
		frame.getContentPane().setLayout(null);

		// buttons
		frame.getContentPane().add(userAccessButton);
		frame.getContentPane().add(buildDataStoreButton);
		frame.getContentPane().add(splitSearchButton);
		frame.getContentPane().add(splitGoldenButton);
		frame.getContentPane().add(busButton);
		//frame.getContentPane().add(busTestButton);		
		frame.getContentPane().add(exitButton);
		frame.setTitle("Administrator Screen");
		//frame.setSize(500, 500);
		frame.setBounds(100,100,500,500);
		frame.setResizable(false);
		//frame.setClosable(false);
		frame.setVisible(true);
	
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		
		splitSearchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Category com2 = new Category(uSer);			
				//categoryButton.setEnabled(false);
				//com2.go();
				//SplitSearchScreens splitObj = new SplitSearchScreens();
				SplitSearch splitObj = new SplitSearch();
				splitObj.SplitSearch();						
				
			}
		});
		
		splitGoldenButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Category com2 = new Category(uSer);			
				//categoryButton.setEnabled(false);
				//com2.go();
				//SplitSearchScreens splitObj = new SplitSearchScreens();
				SplitSearch splitObj = new SplitSearch();
				splitObj.SplitSearch();	
				splitObj.TestCaseGolden();
			}
		});
		
		
		busButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Category com2 = new Category(uSer);			
				//categoryButton.setEnabled(false);
				//com2.go();
				//SplitSearchScreens splitObj = new SplitSearchScreens();
				new NewBus();				
			}
		});
		
		/*
		busTestButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Category com2 = new Category(uSer);			
				//categoryButton.setEnabled(false);
				//com2.go();
				//SplitSearchScreens splitObj = new SplitSearchScreens();
				Testing busTestObj = new Testing();
				busTestObj.testingGo();

			}
		});
		*/
		userAccessButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				UserAccess com3 = new UserAccess(uSer);
				//userAccessButton.setEnabled(false);
				com3.go();				
			}
		});
		
		buildDataStoreButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int i;
				try {
					CommonService com4 = new CommonService();					
					i=com4.rebuildNodesTable("nodes.txt");
					if(i==0) displayMessage("rebuild Done");
					else displayMessage("rebuild Failed");
				}
				catch (Exception e) {
					displayMessage("rebuild Failed");
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

	private JButton userAccessButton;
	private JButton buildDataStoreButton;
	private JButton splitSearchButton;
	private JButton splitGoldenButton;
	private JButton busButton;
	//private JButton busTestButton;
	private JButton exitButton;
	
}