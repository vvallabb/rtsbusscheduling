//package bus;
import java.io.*;
import java.lang.*;
import java.util.StringTokenizer;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Line2D;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.WindowConstants;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.Toolkit;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;


import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.List;



 class NewBus extends JFrame
{
      Writer output1 = null;
	  File file = new File("userlog.doc");
      
	long currentTime,previousTime,timeToQuery;
	//variables to construct frame are declared here
  Image map,logo;
//  List lis=new List(10,true);
    JComboBox sourceNode = new JComboBox();
	JComboBox destinationNode = new JComboBox();
	JComboBox userRequest = new JComboBox();
	JLabel userRequestLabel = new JLabel("Select User Place:");
	JLabel label3 = new JLabel("Select Source :");
	JLabel label4 = new JLabel("Select Destination :");
	//JLabel noOfReqLabel=new JLabel("");//future purpose to display intermediate nodes in the frame
	JButton helpButton = new JButton("Help");
	JButton testButton = new JButton("Test");
	JButton cancelButton = new JButton("Exit");
	JButton resetButton= new JButton("Reset");
	JButton getPath = new JButton("Get path");
//	JLabel intermediateLabel = new JLabel("Select Intermediate Nodes :");

	JLabel requestLabel = new JLabel("Enter the Number of Persons");
	JTextField requestText= new JTextField(10);


	String optimalPathHelp =
		"                                          Bus scheduling  Application Help                \n"+
		"\nBus Scheduling  application scdules buses efficiently based on user rquirements\n"+
		"It will also provide accurate distance given source node to the destination node by considering requirements dynamically.\n"+
		"\nStep1:\n"+
		"\"Select Source Node:\" dropdown-list lists a set of nodes\n"+
		"The user can choose anyone entry in this list which would be his starting point\n"+
		"\nNext the user has to choose the destination place he is inteneded to go.\n"+
		"\nStep2:\n"+
		"\"Select Destination: \" dropdown-list lists a set of nodes\n"+
		"The user can choose anyone entry in this list as his destination point.\n"+
		"\nOnce the user selects a source and destination node the user must\n"+
		"click on button \"GetPath Button\" to display path from source to destination\n"+
		"\nAfter clicking the \"GetPath Button\" another list would be visible.\n"+
		"There are some list of intermediate nodes are shown here to allow user to select intermediate places\"\n"+
		"\n through they would reach destination\n"+
		"\nOptional Step:\n"+
		"Intermediate nodes are places of interest of user which when selected by user will \n"+
		"retrace the Optimal path from source to destination node through the intermediate nodes.\n"+
		"\n";
	// variables for actual algorithm are declared here
	int noOfNodes = 80;//Actually we have to get from My_Quadtree.objectCount;
	int x[]=new int[80];		//Array To hold X-Coordinates of places
	int y[]=new int[80];		//Array To hold Y-Coordinates of places
	int[] intermediateNodes;
	int initialFlag=0;
	String[] places = new String[noOfNodes];
	int srcindx,destindx;

	String s=null;//used to read from file by line wise
	int dCounter=1;//for counter variable

	int[][] output=new int[80][80];//to store values coming from shortest
	int outputLength=0;
	int intermediateIndex[];
	float velocity=30;	//this is the fixed velocity of a bus
	float totalDistance;
	Container cp = getContentPane();

	public NewBus()
	{
			this.setTitle("Bus Scheduling Application");

		   cp.setLayout(null);
		   setBounds(0,0,1500,1500);
		   setVisible(true);
		   //cp.add(lis);


			//		lis.setVisible(false);

			//   lis.setBounds(800,280,190,150);
		  // cp.add(intermediateLabel);
		   // intermediateLabel.setBounds(810,180,190,150);
			// intermediateLabel.setVisible(false);



		   map = getToolkit().getImage("map.jpg");
		   logo=getToolkit().getImage("logo.jpg");

		   cp.add(getPath);
		   getPath.setBounds(850,150,136,20);
		   cp.add(helpButton);
		   cp.add(testButton);
		   
			helpButton.setBounds(770,150,136,20);
			//testButton.setBounds(770,170,136,20);
			
			cp.add(resetButton);
			resetButton.setBounds(930, 640, 100, 25);

			cp.add(label3);
		   cp.add(label4);
		   label3.setBounds(700,80,236,20);
		   label4.setBounds(936,80,236,20);
			cp.add(sourceNode);
			sourceNode.setBounds(700,100,136,20);
		sourceNode.setVisible(true);
		cp.add(destinationNode);
		destinationNode.setBounds(936,100,136,20);
			destinationNode.setVisible(true);
		helpButton.setBounds(710, 640, 100, 25);
		testButton.setBounds(710, 670, 100, 25);		
		this.helpButton.setToolTipText("Click here to open help on Bus Scheduling");
		this.testButton.setToolTipText("Click here to open Test on Bus Scheduling");
		this.helpButton.setVisible(true);
		this.testButton.setVisible(true);
		cp.add(this.helpButton);
		cp.add(this.testButton);

//action performed when EXIT button is pressed
			this.cancelButton.setBounds(820, 640, 100, 25);
		this.cancelButton.setToolTipText("Click here to Exit Bus Scheduling");
		this.cancelButton.setVisible(true);
		cp.add(this.cancelButton);

		this.cancelButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(final ActionEvent arg0)
			{
				Toolkit.getDefaultToolkit().beep();
				final int confirm = JOptionPane.showOptionDialog(null,
						"Really Exit Optimal Path?", "Exit Confirmation",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE,
						null, null, null);
				//if (confirm == JOptionPane.YES_OPTION) {
				//	NewBus.this.setVisible(false);
                    //new RtsLogIn();
				//}
				
				if (confirm == JOptionPane.YES_OPTION) {
					//SplitSearch.this.dispose();
					System.exit(0);
				}
				
				repaint();
			}
		});
//action performed when HELP Button is pressed
this.helpButton.addActionListener(new ActionListener()
		{

			public void actionPerformed(final ActionEvent arg0)
			{
				JOptionPane.showMessageDialog(null, NewBus.this.optimalPathHelp);
				repaint();
			}
		});
//action performed when HELP Button is pressed
this.testButton.addActionListener(new ActionListener()
		{

			public void actionPerformed(final ActionEvent arg0)
			{
				Testing busTestObj = new Testing();
				busTestObj.testingGo();
			}
		});

//action performed when RESET Button is pressed
		this.resetButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(final ActionEvent arg0)
			{
				Toolkit.getDefaultToolkit().beep();
				final int confirm = JOptionPane.showOptionDialog(null,
						"Are you sure to Reset the fields", "Reset Confirmation",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE,
						null, null, null);
				if (confirm == JOptionPane.YES_OPTION) {
					initialFlag=0;
					//repaint();
				}
				repaint();
			}
		});

		//userRequest.addItemListener

		//Following code is to load values from files to Frame

try{
				FileReader dis = new FileReader("GRAPH1.TXT");
				BufferedReader in = new BufferedReader(dis);
				StringTokenizer st2;
				int i=0;
				s=in.readLine();
				while(s!=null)
				{

						st2=new StringTokenizer(s,",");
						while(st2.hasMoreTokens()){

			 				if(dCounter==1)
			 				{
			 					x[i]=Integer.parseInt(st2.nextToken());
			 					dCounter++;
			 				}
			 				if(dCounter==2)
			 				{
			 					y[i]=Integer.parseInt(st2.nextToken());
			 	 			dCounter++;
			 				}
			 				if(dCounter==3)
			 				{
			 					places[i]=(st2.nextToken());
			 					dCounter=1;
			 					i++;
			 				}
						}
						dCounter=1;
						s=in.readLine();
				}
			}

			catch(FileNotFoundException e){
				System.out.println("Make sure that file should be present");
			}
			catch(IOException e){
				System.out.println("IOException Occured");
			}
	for(int m=0;m<79;m++)
		{
			sourceNode.addItem(places[m]);
			destinationNode.addItem(places[m]);
			userRequest.addItem(places[m]);
		}
		  requestText.addKeyListener(new KeyAdapter()	 {
		  public void keyTyped(KeyEvent e)
		  {
			  char c=e.getKeyChar();

			    if(!(Character.isDigit(c)) || (c==KeyEvent.VK_BACK_SPACE) ||  (c==KeyEvent.VK_DELETE))
			  {
				  getToolkit().beep();
				  e.consume();
			  }

		  }});



//code for what happened when get path button is pressed
getPath.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

				srcindx=sourceNode.getSelectedIndex();
				destindx=destinationNode.getSelectedIndex();

                Object source = sourceNode.getSelectedItem();
                Object destination = destinationNode.getSelectedItem();

                String sourceName = source.toString();
                String destinationName = destination.toString();

                System.out.println("SourceName is " + sourceName);

                try{
			    output1 = new BufferedWriter(new FileWriter(file,true));
		        }catch(Exception e1){System.out.println("error while writing to the file");}

                try {
						output1.write(sourceName+ "--" + destinationName+ "\n");
                        if(sourceName.equals(destinationName)){
                            output1.write("No path"+ "\n");
                        }
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						output1.close();
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}


			if(srcindx!=destindx)
				{
				cp.add(userRequest);
			//cp.add(userRequestLabel);
			userRequest.setBounds(800,280,136,20);
			userRequestLabel.setBounds(800,190,190,50);
			userRequest.setVisible(true);
			userRequestLabel.setVisible(true);
			cp.add(requestLabel);
			cp.add(requestText);
			requestLabel.setBounds(800,250,190,50);
			requestLabel.setVisible(true);
			requestText.setBounds(800,350,70,30);
			requestText.setVisible(true);
				}
				else
				{
					initialFlag=0;
				//	intermediateLabel.setVisible(false);
				//	lis.setVisible(false);
					userRequest.setVisible(false);
					userRequestLabel.setVisible(false);
					requestLabel.setVisible(false);
					requestText.setVisible(false);
					repaint();
				}
				shortestPath();
				}});

	}
	void shortestPath()
	{

		if (srcindx==destindx)
		 	{
				Toolkit.getDefaultToolkit().beep();

				JOptionPane.showMessageDialog(null,"Enter another destination","Alert",JOptionPane.ERROR_MESSAGE);
			    repaint();
		 	}
			else
		{
				initialFlag=1;
				//getToolkit().beep();
				 System.out.println("\007");
				String[][] shortNodes;
				currentTime=System.currentTimeMillis() ;
				Shortest sp=new Shortest();
				shortNodes=sp.path(places[destindx],places[srcindx]);//call to Shortest class
				previousTime=System.currentTimeMillis();
				timeToQuery=previousTime-currentTime;
				System.out.println("\n\nTime for calculating shortest path :"+timeToQuery);
				outputLength=shortNodes.length;
				for(int i=0;i<shortNodes.length;i++)
			{
					output[i][0]=Integer.parseInt(shortNodes[i][0]);
					output[i][1]=Integer.parseInt(shortNodes[i][1]);
					System.out.println("MINE( x-"+shortNodes[i][0]+", y-"+shortNodes[i][1]+")");
			}

			    repaint();
		}
	}
	public void paint(Graphics g)
	{	//System.out.println("Iam in Paint");
		Graphics2D g2=(Graphics2D)g;
		g.drawImage(map,80,0,this);
		g.drawImage(logo,1100,40,this);
		if(initialFlag==1)
		{
		for(int t=outputLength-1;t>0;t--)
	    {
			Line2D line =new Line2D.Double(output[t][0],output[t][1],output[t-1][0],output[t-1][1]);
			g2.setStroke(new BasicStroke(3));
			g2.setColor(Color.BLUE);
			g2.draw(line);
		   }

		    g.setColor(Color.RED);
			g.fillOval(x[srcindx]-5,y[srcindx]-5,10,10);
			g.drawString(places[srcindx],x[srcindx]-20,y[srcindx]-5);
			g.setColor(Color.RED);
			g.fillOval(x[destindx]-5,y[destindx]-5,10,10);
			g.drawString(places[destindx],x[destindx]-20,y[destindx]-5);

			for(int k=outputLength-1;k>=0;k--)
			{
				g.fillOval(output[k][0]-5,output[k][1]-5,10,10);
			}
		}
	}
	public static void main(String args[]) throws IOException
	{

		new NewBus();
	}
};
