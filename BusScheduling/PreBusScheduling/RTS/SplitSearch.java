import java.io.*;
import java.lang.*;
import java.sql.Connection;
//import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.math.*;
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
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
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
import java.util.ArrayList;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.lang.*;




/*class NodeXY {
	int x;
	int y;
	int cost;
	public NodeXY(int x1,int y1) {
		x=x1;
		y=y1;
	}
	public NodeXY(){}

	public NodeXY(int ct){cost=ct;}
}
*/


public class SplitSearch extends JFrame
{
	//Class variables
	//frame.getContentPane().setLayout(null);
	Container cp = getContentPane();
	Image map,logo;
	List lis=new List(10,true);
	JComboBox sourceNode = new JComboBox();
	JList entityList1;
	JList entityList2;
	JList entityList3;
	JList entityList4;
	final static String [] entities = {"ATM", "HOTEL","HOSPITAL","MALL"};
	HashMap<String,Integer> entityType = new HashMap<String,Integer>();

	JScrollPane entityScroller1;
	JScrollPane entityScroller2;
	JScrollPane entityScroller3;
	JScrollPane entityScroller4;
	JLabel radiusLabel = new JLabel("Radius");
	JTextField radiusText = new JTextField(20);
	JLabel label3 = new JLabel("Select Location :");
	JLabel label4 = new JLabel("Entity1 ");
	JLabel label5 = new JLabel("Entity2 ");
	JLabel label6 = new JLabel("Entity3 ");
	JLabel label7 = new JLabel("Entity4 ");
	JButton helpButton = new JButton("Help");
	JButton cancelButton = new JButton("Exit");
	JButton resetButton= new JButton("Reset");
	JButton getPath = new JButton("Get Points");
	JButton getTest = new JButton("Test");
	JButton getGolden = new JButton("Golden");
	JButton getOptimalPath = new JButton("Get Optimal Path");
	JButton getOptimalPathWP = new JButton("Get Optimal Path Without Priority");
	JButton getOptimalPathRT = new JButton("Get Optimal Path RoundTrip Without Priority");

	String splitHelp = "";

	ArrayList<Node> rangeQueryList0 = new ArrayList<Node>();
	ArrayList<Node> sourceNodeComboList = new ArrayList<Node>();
	ArrayList<Node> rangeQueryList = new ArrayList<Node>();
	PointQuadTree pointQuadTree = new PointQuadTree(0, 50, 50, 1,"Bangalore"); // Root node point
	ArrayList<Node> paintList = new ArrayList<Node>(); //Rangequery result store for painting needs
	ArrayList<Node> paintList1 = new ArrayList<Node>(); //Rangequery result store for painting needs entity1
	ArrayList<Node> paintList2 = new ArrayList<Node>(); //Rangequery result store for painting needs entity2
	ArrayList<Node> paintList3 = new ArrayList<Node>(); //Rangequery result store for painting needs entity3
	ArrayList<Node> paintList4 = new ArrayList<Node>(); //Rangequery result store for painting needs entity4
	ArrayList<String> helpArr = new ArrayList<String>(); //Help Array
	ArrayList<NodeXY> aList1 = new ArrayList<NodeXY>();
	ArrayList<NodeXY> aList2 = new ArrayList<NodeXY>();
	ArrayList<NodeXY> aList3 = new ArrayList<NodeXY>();
	ArrayList<NodeXY> aList4 = new ArrayList<NodeXY>();
	ArrayList<NodeXY> aListcon = new ArrayList<NodeXY>();
	
	ArrayList<NodeXY> minCon = new ArrayList<NodeXY>();//Consolidated
	ArrayList<NodeXY> minCon0 = new ArrayList<NodeXY>();//Consolidated
	ArrayList<NodeXY> minCon1 = new ArrayList<NodeXY>();//Consolidated
	ArrayList<NodeXY> minCon2 = new ArrayList<NodeXY>();//Consolidated

	RangeQuery query;
	RangeQuery query0;
	// variables for actual algorithm are declared here
	int xP,yP,radiusP=0;
	String srcPlace;
	int i=0;
	int initialFlag=0;
	int srcindx;
	String distance[][];

	int[][] output=new int[80][80];//to store values coming from shortest //Not required
	String entity1; //ATM
	String entity2; //HOTEL
	String entity3; //HOSPITAL
	String entity4; //MALL
	String radiusStr;
	int radius;

	BufferedWriter output1; //file to trap test results
	
	//public SplitSearch(int x){}
	
	public void SplitSearch()
	{
		
		try{ //trap test results			
			FileWriter fstream;
		}catch(Exception e1){System.out.println("errorFS");}

		this.setTitle("Split Search Application");
		setLayout(); //screen Layout + map image + buttons
		
		//Following code is to load values from files to Frame
		if(i<1) populateTree();	//load Quadtree only once
		
		helpButton.setBounds(710, 640, 100, 25);
		this.helpButton.setToolTipText("Click here to open help on SplitSearch");
		cp.add(this.helpButton);
		//this.helpButton.setVisible(true);

		this.cancelButton.setBounds(820, 640, 100, 25);
		this.cancelButton.setToolTipText("Click here to Exit SplitSearch");
		cp.add(this.cancelButton);
		//this.cancelButton.setVisible(true);

		this.resetButton.setBounds(930, 640, 100, 25);
		cp.add(resetButton);	
		//this.resetButton.setVisible(true);

		this.getOptimalPathRT.setBounds(710,460,300,20);
		cp.add(getOptimalPathRT);

		
		this.getOptimalPathWP.setBounds(710,430,300,20);
		cp.add(getOptimalPathWP);
		//this.getOptimalPath.setVisible(true);
		
		this.getOptimalPath.setBounds(710,400,300,20);
		cp.add(getOptimalPath);
		
		this.getPath.setBounds(710,490,300,20);
		cp.add(getPath);
		
		this.getTest.setBounds(710,520,300,20);
		cp.add(getTest);
		
		this.getGolden.setBounds(710,550,300,20);
		cp.add(getGolden);
		
		
		//this.getTest.setVisible(true);

		setVisible(true);
		this.helpButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(final ActionEvent arg0) 
			{
				JOptionPane.showMessageDialog(null, SplitSearch.this.splitHelp);
				repaint();
			}
		});

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
				if (confirm == JOptionPane.YES_OPTION) {
					//SplitSearch.this.dispose();
					System.exit(0);
				}
				//repaint();
			}
		});


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
					repaint();
				}
			}
		});
		
		this.getTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SplitSearch s1 = new SplitSearch();
				s1.TestCaseCompare();
		}});
		
		this.getGolden.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SplitSearch s1 = new SplitSearch();
				s1.TestCaseGolden();				
		}});
		
		

		//code to get points when the getpath button is pressed
		this.getPath.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				srcindx=sourceNode.getSelectedIndex();
				Node src0 = (Node) sourceNodeComboList.get(srcindx);
				//Node src0 = (Node) rangeQueryList0.get(srcindx);
				xP=(int)src0.x;
				yP=(int)src0.y;
				//System.out.println("XXXX:"+src0.x+"YYYYY:"+src0.y+"place:"+src0.place);
				entity1 = (String) entityList1.getSelectedValue();
				entity2 = (String) entityList2.getSelectedValue();
				entity3 = (String) entityList3.getSelectedValue();
				entity4 = (String) entityList4.getSelectedValue();
				System.out.println("getPath Ent1:"+entity1+"Ent2:"+entity2+"Type:"+entityType.get("ATM"));

				radiusStr = radiusText.getText().trim();
				try {
				radius = Integer.parseInt(radiusStr);
				//System.out.println("into getPath action:"+radiusStr+"ent1:"+entity1+"ent2:"+entity2);
				radiusP=radius;
				
				try{					
					String fn1="writer_"+src0.place+".txt";
				    //FileWriter fstream = new FileWriter(fn1,true);
					FileWriter fstream = new FileWriter(fn1);
				    output1 = new BufferedWriter(fstream);
					output1.write("***START getPath:"+src0.place+"\n");
					output1.write("ShowPoints Inputs:\n");
					output1.write("Source Points    :"+src0.place+":"+xP + ":" + yP+"\n");					
					output1.write("Radius           :"+radius+"\n");
					output1.write("Results          :\n");				    
				}catch(Exception e1){System.out.println("error");}
				

				//Array to capture the entities falling into L2 Norm from source-point xP,yP
				paintList = fetchList(xP,yP,radiusP);

				repaint(0,0,1500,1500);
				setVisible(true);
				}
			    catch (Exception e1){
					JFrame frame = new JFrame();
					JOptionPane.showMessageDialog(frame, "Radius Mandatory");
					//JOptionPane.showMessageDialog(frame,
					//"Source is Mandatory", "Error",
					//JOptionPane.ERROR_MESSAGE);
				}

		}});


		this.getOptimalPath.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				srcindx=sourceNode.getSelectedIndex();
				Node src0 = (Node) sourceNodeComboList.get(srcindx);
				//Node src0 = (Node) rangeQueryList0.get(srcindx);
				xP=(int)src0.x;
				yP=(int)src0.y;
				srcPlace = src0.place;
				try {
				entity1 = (String) entityList1.getSelectedValue();
				entity2 = (String) entityList2.getSelectedValue();
				entity3 = (String) entityList3.getSelectedValue();
				entity4 = (String) entityList4.getSelectedValue();
				System.out.println("getOptPath Ent1:"+entity1+"Ent2:"+entity2);

				radiusStr = radiusText.getText().trim();
				radius = Integer.parseInt(radiusStr);
				
				
				try{					
					String fn1="writer_"+src0.place+".txt";
					//System.out.println("FN:"+fn1);
				    FileWriter fstream = new FileWriter(fn1,true);
				    output1 = new BufferedWriter(fstream);
					output1.write("***START getOptimalPath:"+src0.place+"\n");
					output1.write("ShowPath Inputs:\n");
					output1.write("Source Points    :"+src0.place+":"+xP + ":" + yP+"\n");
					output1.write("Radius           :"+radius+"\n");
					output1.write("entity1          :"+entity1+"\n");
					output1.write("entity2          :"+entity2+"\n");
					output1.write("entity3          :"+entity3+"\n");
					output1.write("entity4          :"+entity4+"\n");
					output1.write("Results          :\n");				    
				}catch(Exception e1){System.out.println("error");}
				

				//System.out.println("into getOptimalPath action:"+radiusStr+"ent1:"+entity1+"ent2:"+entity2);
				radiusP=radius;
				paintList.clear();
				aList1.clear();
				aList2.clear();
				aList3.clear();
				aList4.clear();
				aListcon.clear();
				paintList = fetchList(xP,yP,radiusP);//paintList array stores places and other entities
				paintList1.clear();//clear Array that stores entitytype 1
				paintList2.clear();//clear Array that stores entitytype 2
				paintList3.clear();//clear Array that stores entitytype 3
				paintList4.clear();//clear Array that stores entitytype 4

				NodeXY r1 = new NodeXY(xP,yP,0);
				aListcon.add(r1);

				if(paintList.size() > 0) {
						for(ListIterator<Node> list=paintList.listIterator();list.hasNext();) {
							Node clon = list.next();
							if (clon.nodeType==entityType.get(entity1)) {paintList1.add(clon); 
																			NodeXY i1=new NodeXY((int)clon.x,(int)clon.y,(int) clon.nodeType);
																			aList1.add(i1);}
							if (clon.nodeType==entityType.get(entity2)) {paintList2.add(clon);
																			NodeXY i2=new NodeXY((int)clon.x,(int)clon.y,(int) clon.nodeType);
																			aList2.add(i2);}
							if (clon.nodeType==entityType.get(entity3)) {paintList3.add(clon);
																			NodeXY i3=new NodeXY((int)clon.x,(int)clon.y,(int) clon.nodeType);
																			aList3.add(i3);}
							if (clon.nodeType==entityType.get(entity4)) {paintList4.add(clon);
																			NodeXY i4=new NodeXY((int)clon.x,(int)clon.y,(int) clon.nodeType);
																			aList4.add(i4);}
						}
				}
				//find the min cost
				int minCost=0;
				minCon.clear();
				minCon.add(r1);
				ArrayList<NodeXY> minList = new ArrayList<NodeXY>();//Array to store only the path with min cost
				System.out.println("TESTB"+minList.size());
				minList=minCost(aListcon,aList1);
				System.out.println("TESTA"+minList.size());
				NodeXY tmp0 = new NodeXY();
				for(int i=0;i<minList.size()-1;i++) {
					tmp0=minList.get(i);
					minCon.add(tmp0);
					System.out.println("minNode1:x:"+tmp0.x+"  y:"+tmp0.y);
				}
				tmp0=minList.get(minList.size()-1);
				System.out.println("minNode1 cost:x:"+tmp0.cost);
				if(tmp0.cost>9000) return;
				minCost=tmp0.cost;
				System.out.println("minCOST:"+minCost);
				//minList=minCost(aList1,aList2);
				minList=minCost(minCon,aList2);
				for(int i=0;i<minList.size()-1;i++) {
					tmp0=minList.get(i);
					minCon.add(tmp0);
					System.out.println("minNode2:x:"+tmp0.x+"  y:"+tmp0.y);
				}
				tmp0=minList.get(minList.size()-1);
				System.out.println("minNode2 cost:x:"+tmp0.cost);
				if(tmp0.cost>9000) return;
				minCost=minCost+tmp0.cost;
				System.out.println("minCOST:"+minCost);
				//minList=minCost(aList2,aList3);
				minList=minCost(minCon,aList3);
				for(int i=0;i<minList.size()-1;i++) {
					tmp0=minList.get(i);
					minCon.add(tmp0);
					System.out.println("minNode3:x:"+tmp0.x+"  y:"+tmp0.y);
				}
				tmp0=minList.get(minList.size()-1);
				System.out.println("minNode3 cost:x:"+tmp0.cost);
				if(tmp0.cost>9000) return;
				minCost=minCost+tmp0.cost;
				System.out.println("minCOST:"+minCost);
				//minList=minCost(aList3,aList4);
				minList=minCost(minCon,aList4);
				for(int i=0;i<minList.size()-1;i++) {
					tmp0=minList.get(i);
					minCon.add(tmp0);
					System.out.println("minNode4:x:"+tmp0.x+"  y:"+tmp0.y);
				}
				tmp0=minList.get(minList.size()-1);
				System.out.println("minNode4 cost:x:"+tmp0.cost);
				if(tmp0.cost>9000) return;
				minCost=minCost+tmp0.cost;
				System.out.println("minCOST:"+minCost);
				NodeXY tmpcost = new NodeXY(minCost);
				minCon.add(tmpcost);
				initialFlag=2;
				repaint();
				setVisible(true);
				
				}
				catch (Exception e1) {
					JFrame frame = new JFrame();
					JOptionPane.showMessageDialog(frame, "Radius and Entity Selections Mandatory");
				}
			}});
		
		
		this.getOptimalPathWP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				srcindx=sourceNode.getSelectedIndex();
				Node src0 = (Node) sourceNodeComboList.get(srcindx);
				//Node src0 = (Node) rangeQueryList0.get(srcindx);
				xP=(int)src0.x;
				yP=(int)src0.y;
				srcPlace = src0.place;
				try {
				entity1 = (String) entityList1.getSelectedValue();
				entity2 = (String) entityList2.getSelectedValue();
				entity3 = (String) entityList3.getSelectedValue();
				entity4 = (String) entityList4.getSelectedValue();
				System.out.println("getOptPath Ent1:"+entity1+"Ent2:"+entity2);

				radiusStr = radiusText.getText().trim();
				radius = Integer.parseInt(radiusStr);
				//System.out.println("into getOptimalPath action:"+radiusStr+"ent1:"+entity1+"ent2:"+entity2);
				radiusP=radius;
				paintList.clear();
				aList1.clear();
				aList2.clear();
				aList3.clear();
				aList4.clear();
				aListcon.clear();
				paintList = fetchList(xP,yP,radiusP);//paintList array stores places and other entities
				paintList1.clear();//clear Array that stores entitytype 1
				paintList2.clear();//clear Array that stores entitytype 2
				paintList3.clear();//clear Array that stores entitytype 3
				paintList4.clear();//clear Array that stores entitytype 4

				NodeXY r1 = new NodeXY(xP,yP,0);
				aListcon.add(r1);

				if(paintList.size() > 0) {
						for(ListIterator<Node> list=paintList.listIterator();list.hasNext();) {
							Node clon = list.next();
							if (clon.nodeType==entityType.get(entity1)) {paintList1.add(clon); 
																			NodeXY i1=new NodeXY((int)clon.x,(int)clon.y,(int) clon.nodeType);
																			aList1.add(i1);}
							if (clon.nodeType==entityType.get(entity2)) {paintList2.add(clon);
																			NodeXY i2=new NodeXY((int)clon.x,(int)clon.y,(int) clon.nodeType);
																			aList2.add(i2);}
							if (clon.nodeType==entityType.get(entity3)) {paintList3.add(clon);
																			NodeXY i3=new NodeXY((int)clon.x,(int)clon.y,(int) clon.nodeType);
																			aList3.add(i3);}
							if (clon.nodeType==entityType.get(entity4)) {paintList4.add(clon);
																			NodeXY i4=new NodeXY((int)clon.x,(int)clon.y,(int) clon.nodeType);
																			aList4.add(i4);}
						}
				}
				
				int minCost0=0;
				int minCost1=0;
				int minCost2=0;
				int minCost3=0;
				
				ArrayList<NodeXY> ent1 = run_minCost(aListcon,aList1,aList2,aList3,aList4);
				System.out.println("After ent1");
				
				minCon.clear();
				NodeXY tmp0= new NodeXY();
				for(i=0;i<ent1.size();i++) {
					tmp0=ent1.get(i);
					minCon.add(tmp0);
					if(i==ent1.size()-1) minCost0=tmp0.cost;
					System.out.println("minNode0:x:"+tmp0.x+"  y:"+tmp0.y);
				}

				
				ArrayList<NodeXY> ent2 = run_minCost(aListcon,aList2,aList1,aList3,aList4);
				System.out.println("After ent2");
				minCon0.clear();
				tmp0= new NodeXY();
				for(i=0;i<ent2.size();i++) {
					tmp0=ent2.get(i);
					minCon0.add(tmp0);
					if(i==ent2.size()-1) minCost1=tmp0.cost;					
					System.out.println("minNode0:x:"+tmp0.x+"  y:"+tmp0.y);
				}				
				//initialFlag=4;
				//repaint();
				//setVisible(true);

				ArrayList<NodeXY> ent3 = run_minCost(aListcon,aList3,aList1,aList2,aList4);
				System.out.println("After ent3");
				minCon1.clear();
				tmp0= new NodeXY();
				for(i=0;i<ent3.size();i++) {
					tmp0=ent3.get(i);
					minCon1.add(tmp0);
					if(i==ent3.size()-1) minCost2=tmp0.cost;					
					System.out.println("minNode0:x:"+tmp0.x+"  y:"+tmp0.y);
				}				
				//initialFlag=4;
				//repaint();
				//setVisible(true);

				ArrayList<NodeXY> ent4 = run_minCost(aListcon,aList4,aList1,aList2,aList3);
				System.out.println("After ent4");				
				minCon2.clear();
				tmp0= new NodeXY();
				for(i=0;i<ent4.size();i++) {
					tmp0=ent4.get(i);
					minCon2.add(tmp0);
					if(i==ent4.size()-1) minCost3=tmp0.cost;
					System.out.println("minNode0:x:"+tmp0.x+"  y:"+tmp0.y);
				}				
				//compare ent1, ent2, ent3, ent4 for minDistance and
				//show the one with less min distance
				
				ArrayList<NodeXY> resCon = new ArrayList<NodeXY>();
				int dist[] = new int[4];
				int k;
				System.out.println("MIN0:"+minCost0);
				System.out.println("MIN1:"+minCost1);
				System.out.println("MIN2:"+minCost2);
				System.out.println("MIN3:"+minCost3);
				
				dist[0] = minCost0;
				dist[1] = minCost1;
				dist[2] = minCost2;
				dist[3] = minCost3;
				
				//get the minimum of all the costs
				int min1 = minCost0;
				for (  k=0; k<dist.length; k++ ){
					if ( dist[k] < min1 ) min1 = dist[k];
				}
				
				System.out.println("FINALLLL:"+min1);
				//send the min distance array among all the 6 minCost's
				resCon.clear();
				for(k=0;k<dist.length;k++) {
					if(min1==dist[k] && k==0) {
						System.out.println("min1--"+dist[k]+"Val:"+k );
						System.out.println("MINCON:"+minCon.size()+" MINCON0000:"+minCon0.size());
						tmp0= new NodeXY();
						for(i=0;i<minCon.size();i++) {
							tmp0=minCon.get(i);
							resCon.add(tmp0);
							System.out.println("minNode0:x:"+tmp0.x+"  y:"+tmp0.y);
						}							
						//resCon=minCon0;
						break;
					}
					if(min1==dist[k] && k==1) {
						System.out.println("min1--"+dist[k]+"Val:"+k);
						tmp0= new NodeXY();
						for(i=0;i<minCon0.size();i++) {
							tmp0=minCon0.get(i);
							resCon.add(tmp0);
							System.out.println("minNode0:x:"+tmp0.x+"  y:"+tmp0.y);
						}							
						//resCon=minCon1;
						break;
					}
					if(min1==dist[k] && k==2) {
						System.out.println("min1--"+dist[k]+"Val:"+k);
						tmp0= new NodeXY();
						for(i=0;i<minCon1.size();i++) {
							tmp0=minCon1.get(i);
							resCon.add(tmp0);
							System.out.println("minNode0:x:"+tmp0.x+"  y:"+tmp0.y);
						}							
						//resCon=minCon2;
						break;
					}
					if(min1==dist[k] && k==3) {
						System.out.println("min1--"+dist[k]+"Val:"+k);
						tmp0= new NodeXY();
						for(i=0;i<minCon2.size();i++) {
							tmp0=minCon2.get(i);
							resCon.add(tmp0);
							System.out.println("minNode0:x:"+tmp0.x+"  y:"+tmp0.y);
						}							
						//resCon=minCon3;
						break;
					}
				}
				minCon.clear();
				for(i=0;i<resCon.size();i++) {
					tmp0=resCon.get(i);
					minCon.add(tmp0);
					System.out.println("minNode0:x:"+tmp0.x+"  y:"+tmp0.y);
				}							
				initialFlag=4;
				repaint();
				setVisible(true);

				//initialFlag=4;
				//repaint();
				//setVisible(true);
				System.out.println("After ent4");				

				
				}
				catch (Exception e1) {
					JFrame frame = new JFrame();
					JOptionPane.showMessageDialog(frame, "Radius and Entity Selections Mandatory");
				}

				
			}});
		//setVisible(true);
		
		this.getOptimalPathRT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				srcindx=sourceNode.getSelectedIndex();
				Node src0 = (Node) sourceNodeComboList.get(srcindx);
				//Node src0 = (Node) rangeQueryList0.get(srcindx);
				xP=(int)src0.x;
				yP=(int)src0.y;
				srcPlace = src0.place;
				try {
				entity1 = (String) entityList1.getSelectedValue();
				entity2 = (String) entityList2.getSelectedValue();
				entity3 = (String) entityList3.getSelectedValue();
				entity4 = (String) entityList4.getSelectedValue();
				System.out.println("getOptPath Ent1:"+entity1+"Ent2:"+entity2);

				radiusStr = radiusText.getText().trim();
				radius = Integer.parseInt(radiusStr);
				//System.out.println("into getOptimalPath action:"+radiusStr+"ent1:"+entity1+"ent2:"+entity2);
				radiusP=radius;
				paintList.clear();
				aList1.clear();
				aList2.clear();
				aList3.clear();
				aList4.clear();
				aListcon.clear();
				paintList = fetchList(xP,yP,radiusP);//paintList array stores places and other entities
				paintList1.clear();//clear Array that stores entitytype 1
				paintList2.clear();//clear Array that stores entitytype 2
				paintList3.clear();//clear Array that stores entitytype 3
				paintList4.clear();//clear Array that stores entitytype 4

				NodeXY r1 = new NodeXY(xP,yP,0);
				aListcon.add(r1);

				if(paintList.size() > 0) {
						for(ListIterator<Node> list=paintList.listIterator();list.hasNext();) {
							Node clon = list.next();
							if (clon.nodeType==entityType.get(entity1)) {paintList1.add(clon); 
																			NodeXY i1=new NodeXY((int)clon.x,(int)clon.y,(int) clon.nodeType);
																			aList1.add(i1);}
							if (clon.nodeType==entityType.get(entity2)) {paintList2.add(clon);
																			NodeXY i2=new NodeXY((int)clon.x,(int)clon.y,(int) clon.nodeType);
																			aList2.add(i2);}
							if (clon.nodeType==entityType.get(entity3)) {paintList3.add(clon);
																			NodeXY i3=new NodeXY((int)clon.x,(int)clon.y,(int) clon.nodeType);
																			aList3.add(i3);}
							if (clon.nodeType==entityType.get(entity4)) {paintList4.add(clon);
																			NodeXY i4=new NodeXY((int)clon.x,(int)clon.y,(int) clon.nodeType);
																			aList4.add(i4);}
						}
				}
				
				int minCost0=0;
				int minCost1=0;
				int minCost2=0;
				int minCost3=0;
				
				ArrayList<NodeXY> ent1 = run_minCost(aListcon,aList1,aList2,aList3,aListcon);
				System.out.println("After ent1");
				
				minCon.clear();
				NodeXY tmp0= new NodeXY();
				for(i=0;i<ent1.size();i++) {
					tmp0=ent1.get(i);
					minCon.add(tmp0);
					if(i==ent1.size()-1) minCost0=tmp0.cost;
					System.out.println("minNode0:x:"+tmp0.x+"  y:"+tmp0.y);
				}

				
				ArrayList<NodeXY> ent2 = run_minCost(aListcon,aList2,aList1,aList3,aListcon);
				System.out.println("After ent2");
				minCon0.clear();
				tmp0= new NodeXY();
				for(i=0;i<ent2.size();i++) {
					tmp0=ent2.get(i);
					minCon0.add(tmp0);
					if(i==ent2.size()-1) minCost1=tmp0.cost;					
					System.out.println("minNode0:x:"+tmp0.x+"  y:"+tmp0.y);
				}				
				//initialFlag=4;
				//repaint();
				//setVisible(true);

				ArrayList<NodeXY> ent3 = run_minCost(aListcon,aList3,aList1,aList2,aListcon);
				System.out.println("After ent3");
				minCon1.clear();
				tmp0= new NodeXY();
				for(i=0;i<ent3.size();i++) {
					tmp0=ent3.get(i);
					minCon1.add(tmp0);
					if(i==ent3.size()-1) minCost2=tmp0.cost;					
					System.out.println("minNode0:x:"+tmp0.x+"  y:"+tmp0.y);
				}				
				//initialFlag=4;
				//repaint();
				//setVisible(true);

				ArrayList<NodeXY> ent4 = run_minCost(aListcon,aList4,aList1,aList2,aListcon);
				System.out.println("After ent4");				
				minCon2.clear();
				tmp0= new NodeXY();
				for(i=0;i<ent4.size();i++) {
					tmp0=ent4.get(i);
					minCon2.add(tmp0);
					if(i==ent4.size()-1) minCost3=tmp0.cost;
					System.out.println("minNode0:x:"+tmp0.x+"  y:"+tmp0.y);
				}				
				//compare ent1, ent2, ent3, ent4 for minDistance and
				//show the one with less min distance
				
				ArrayList<NodeXY> resCon = new ArrayList<NodeXY>();
				int dist[] = new int[4];
				int k;
				System.out.println("MIN0:"+minCost0);
				System.out.println("MIN1:"+minCost1);
				System.out.println("MIN2:"+minCost2);
				System.out.println("MIN3:"+minCost3);
				
				dist[0] = minCost0;
				dist[1] = minCost1;
				dist[2] = minCost2;
				dist[3] = minCost3;
				
				//get the minimum of all the costs
				int min1 = minCost0;
				for (  k=0; k<dist.length; k++ ){
					if ( dist[k] < min1 ) min1 = dist[k];
				}
				
				System.out.println("FINALLLL:"+min1);
				//send the min distance array among all the 6 minCost's
				resCon.clear();
				for(k=0;k<dist.length;k++) {
					if(min1==dist[k] && k==0) {
						System.out.println("min1--"+dist[k]+"Val:"+k );
						System.out.println("MINCON:"+minCon.size()+" MINCON0000:"+minCon0.size());
						tmp0= new NodeXY();
						for(i=0;i<minCon.size();i++) {
							tmp0=minCon.get(i);
							resCon.add(tmp0);
							System.out.println("minNode0:x:"+tmp0.x+"  y:"+tmp0.y);
						}							
						//resCon=minCon0;
						break;
					}
					if(min1==dist[k] && k==1) {
						System.out.println("min1--"+dist[k]+"Val:"+k);
						tmp0= new NodeXY();
						for(i=0;i<minCon0.size();i++) {
							tmp0=minCon0.get(i);
							resCon.add(tmp0);
							System.out.println("minNode0:x:"+tmp0.x+"  y:"+tmp0.y);
						}							
						//resCon=minCon1;
						break;
					}
					if(min1==dist[k] && k==2) {
						System.out.println("min1--"+dist[k]+"Val:"+k);
						tmp0= new NodeXY();
						for(i=0;i<minCon1.size();i++) {
							tmp0=minCon1.get(i);
							resCon.add(tmp0);
							System.out.println("minNode0:x:"+tmp0.x+"  y:"+tmp0.y);
						}							
						//resCon=minCon2;
						break;
					}
					if(min1==dist[k] && k==3) {
						System.out.println("min1--"+dist[k]+"Val:"+k);
						tmp0= new NodeXY();
						for(i=0;i<minCon2.size();i++) {
							tmp0=minCon2.get(i);
							resCon.add(tmp0);
							System.out.println("minNode0:x:"+tmp0.x+"  y:"+tmp0.y);
						}							
						//resCon=minCon3;
						break;
					}
				}
				minCon.clear();
				for(i=0;i<resCon.size();i++) {
					tmp0=resCon.get(i);
					minCon.add(tmp0);
					System.out.println("minNode0:x:"+tmp0.x+"  y:"+tmp0.y);
				}							
				initialFlag=5;
				repaint();
				setVisible(true);

				//initialFlag=4;
				//repaint();
				//setVisible(true);
				System.out.println("After ent4");				

				
				}
				catch (Exception e1) {
					JFrame frame = new JFrame();
					JOptionPane.showMessageDialog(frame, "Radius and Entity Selections Mandatory");
				}

				
			}});		
		
	}
	
	

	public ArrayList<NodeXY> run_minCost(ArrayList<NodeXY> x0,ArrayList<NodeXY> x1, ArrayList<NodeXY> x2, ArrayList<NodeXY> x3,
			ArrayList<NodeXY> x4) {
		
		ArrayList<NodeXY> minList = new ArrayList<NodeXY>();
		ArrayList<NodeXY> minCon = new ArrayList<NodeXY>();
		
		ArrayList<NodeXY> minCon0 = new ArrayList<NodeXY>();
		ArrayList<NodeXY> minCon1 = new ArrayList<NodeXY>();
		ArrayList<NodeXY> minCon2 = new ArrayList<NodeXY>();
		ArrayList<NodeXY> minCon3 = new ArrayList<NodeXY>();
		ArrayList<NodeXY> minCon4 = new ArrayList<NodeXY>();
		ArrayList<NodeXY> minCon5 = new ArrayList<NodeXY>();
		ArrayList<NodeXY> resCon = new ArrayList<NodeXY>();
		
		NodeXY tmpcost;	
		NodeXY tmp0 = new NodeXY();
		int minCost=0;
		int minCost0=0;
		int minCost1=0;
		int minCost2=0;
		int minCost3=0;
		int minCost4=0;
		int minCost5=0;
		
		NodeXY r1 = new NodeXY(xP,yP,0);
		//x1.add(r1);
		int i,j,k,l,x,y;
		int breakchk=0;
	
		for(y=0;y<x0.size();y++) {			
			minCon.clear();
			minCon.add(r1);			
			if(breakchk==1) {minCost0=9999;break;}		
			//for(i=0;i<x1.size();i++) {
				System.out.println("b4 1");
				minList = minCost(x0,x1);	
				System.out.println("a4 1");
				for(i=0;i<minList.size()-1;i++) {
					tmp0=minList.get(i);
					minCon.add(tmp0);
					System.out.println("minNode0:x:"+tmp0.x+"  y:"+tmp0.y);
				}
				tmp0=minList.get(minList.size()-1);
				if(tmp0.cost>9000) {minCost0=9999;breakchk=1;break;}
				minCost0=minCost0+tmp0.cost;
				System.out.println("Cost0:"+minCost0);
				//for(j=0;j<x2.size();j++) {
					minList = minCost(minCon,x2);
					for(i=0;i<minList.size()-1;i++) {
						tmp0=minList.get(i);
						minCon.add(tmp0);
						System.out.println("minNode1:x:"+tmp0.x+"  y:"+tmp0.y);
					}
					tmp0=minList.get(minList.size()-1);				
					if(tmp0.cost>9000) {minCost0=9999;breakchk=1;break;}
					minCost0=minCost0+tmp0.cost;
					System.out.println("Cost1:"+minCost0);
					//for(k=0;k<x3.size();k++){
						minList = minCost(minCon,x3);					
						for(i=0;i<minList.size()-1;i++) {
							tmp0=minList.get(i);
							minCon.add(tmp0);
							System.out.println("minNode2:x:"+tmp0.x+"  y:"+tmp0.y);
						}
						tmp0=minList.get(minList.size()-1);				
						if(tmp0.cost>9000) {minCost0=9999;breakchk=1;break;}
						minCost0=minCost0+tmp0.cost;
						System.out.println("Cost2:"+minCost0);
						//for(l=0;l<x4.size();l++){
							minList = minCost(minCon,x4);			
							for(i=0;i<minList.size()-1;i++) {
								tmp0=minList.get(i);
								minCon.add(tmp0);
								System.out.println("minNode1:x:"+tmp0.x+"  y:"+tmp0.y);
							}
							tmp0=minList.get(minList.size()-1);				
							if(tmp0.cost>9000) {minCost0=9999;breakchk=1;break;}
							minCost0=minCost0+tmp0.cost;
							System.out.println("Cost-final0:"+minCost0);
							//minCost0=0;
							//track routes with costs individually starting from
							//minCon to minCon2 and the cost involved
							//and initialize for the next round
						//}
					//}
				//}
			//}
		}
		//result of 1

		tmpcost= new NodeXY(minCost0);
		for(i=0;i<minCon.size();i++) {
			tmp0=minCon.get(i);
			minCon0.add(tmp0);
			System.out.println("minNode000000:x:"+tmp0.x+"  y:"+tmp0.y);
		}
		minCon0.add(tmpcost);
		System.out.println("MINCON:"+minCon.size()+" MINCON0000:"+minCon0.size());
				
		for(y=0;y<x0.size();y++) {			
			minCon.clear();
			minCon.add(r1);			
			if(breakchk==1) {minCost1=9999;break;}		
			//for(i=0;i<x1.size();i++) {
				System.out.println("b4 1");
				minList = minCost(x0,x1);	
				System.out.println("a4 1");
				for(i=0;i<minList.size()-1;i++) {
					tmp0=minList.get(i);
					minCon.add(tmp0);
					System.out.println("minNode0:x:"+tmp0.x+"  y:"+tmp0.y);
				}
				tmp0=minList.get(minList.size()-1);
				if(tmp0.cost>9000) {minCost1=9999;breakchk=1;break;}
				minCost1=minCost1+tmp0.cost;
				System.out.println("Cost0:"+minCost0);
				//for(j=0;j<x2.size();j++) {
					minList = minCost(minCon,x2);
					for(i=0;i<minList.size()-1;i++) {
						tmp0=minList.get(i);
						minCon.add(tmp0);
						System.out.println("minNode1:x:"+tmp0.x+"  y:"+tmp0.y);
					}
					tmp0=minList.get(minList.size()-1);				
					if(tmp0.cost>9000) {minCost1=9999;breakchk=1;break;}
					minCost1=minCost1+tmp0.cost;
					System.out.println("Cost1:"+minCost0);
					//for(k=0;k<x3.size();k++){
						minList = minCost(minCon,x4);					
						for(i=0;i<minList.size()-1;i++) {
							tmp0=minList.get(i);
							minCon.add(tmp0);
							System.out.println("minNode2:x:"+tmp0.x+"  y:"+tmp0.y);
						}
						tmp0=minList.get(minList.size()-1);				
						if(tmp0.cost>9000) {minCost1=9999;breakchk=1;break;}
						minCost1=minCost1+tmp0.cost;
						System.out.println("Cost2:"+minCost0);
						//for(l=0;l<x4.size();l++){
							minList = minCost(minCon,x3);			
							for(i=0;i<minList.size()-1;i++) {
								tmp0=minList.get(i);
								minCon.add(tmp0);
								System.out.println("minNode1:x:"+tmp0.x+"  y:"+tmp0.y);
							}
							tmp0=minList.get(minList.size()-1);				
							if(tmp0.cost>9000) {minCost1=9999;breakchk=1;break;}
							minCost1=minCost1+tmp0.cost;
							System.out.println("Cost-final1:"+minCost1);
							//minCost0=0;
							//track routes with costs individually starting from
							//minCon to minCon2 and the cost involved
							//and initialize for the next round
						//}
					//}
				//}
			//}
		}
		//result of 2
	
		tmpcost= new NodeXY(minCost1);
		for(i=0;i<minCon.size();i++) {
			tmp0=minCon.get(i);
			minCon1.add(tmp0);
			//System.out.println("minNode0:x:"+tmp0.x+"  y:"+tmp0.y);
		}
		minCon1.add(tmpcost);
		
		
		for(y=0;y<x0.size();y++) {			
			minCon.clear();
			minCon.add(r1);			
			if(breakchk==1) {minCost2=9999;break;}		
			//for(i=0;i<x1.size();i++) {
				System.out.println("b4 1");
				minList = minCost(x0,x1);	
				System.out.println("a4 1");
				for(i=0;i<minList.size()-1;i++) {
					tmp0=minList.get(i);
					minCon.add(tmp0);
					System.out.println("minNode0:x:"+tmp0.x+"  y:"+tmp0.y);
				}
				tmp0=minList.get(minList.size()-1);
				if(tmp0.cost>9000) {minCost2=9999;;breakchk=1;break;}
				minCost2=minCost2+tmp0.cost;
				System.out.println("Cost0:"+minCost0);
				//for(j=0;j<x2.size();j++) {
					minList = minCost(minCon,x3);
					for(i=0;i<minList.size()-1;i++) {
						tmp0=minList.get(i);
						minCon.add(tmp0);
						System.out.println("minNode1:x:"+tmp0.x+"  y:"+tmp0.y);
					}
					tmp0=minList.get(minList.size()-1);				
					if(tmp0.cost>9000) {minCost2=9999;breakchk=1;break;}
					minCost2=minCost2+tmp0.cost;
					System.out.println("Cost1:"+minCost0);
					//for(k=0;k<x3.size();k++){
						minList = minCost(minCon,x2);					
						for(i=0;i<minList.size()-1;i++) {
							tmp0=minList.get(i);
							minCon.add(tmp0);
							System.out.println("minNode2:x:"+tmp0.x+"  y:"+tmp0.y);
						}
						tmp0=minList.get(minList.size()-1);				
						if(tmp0.cost>9000) {minCost2=9999;breakchk=1;break;}
						minCost2=minCost2+tmp0.cost;
						System.out.println("Cost2:"+minCost0);
						//for(l=0;l<x4.size();l++){
							minList = minCost(minCon,x4);			
							for(i=0;i<minList.size()-1;i++) {
								tmp0=minList.get(i);
								minCon.add(tmp0);
								System.out.println("minNode1:x:"+tmp0.x+"  y:"+tmp0.y);
							}
							tmp0=minList.get(minList.size()-1);				
							if(tmp0.cost>9000) {minCost2=9999;breakchk=1;break;}
							minCost2=minCost2+tmp0.cost;
							System.out.println("Cost-final1:"+minCost1);
							//minCost0=0;
							//track routes with costs individually starting from
							//minCon to minCon2 and the cost involved
							//and initialize for the next round
						//}
					//}
				//}
			//}
		}
		//result of 3
		tmpcost= new NodeXY(minCost2);
		for(i=0;i<minCon.size();i++) {
			tmp0=minCon.get(i);
			minCon2.add(tmp0);
			//System.out.println("minNode0:x:"+tmp0.x+"  y:"+tmp0.y);
		}
		minCon2.add(tmpcost);
		
		for(y=0;y<x0.size();y++) {			
			minCon.clear();
			minCon.add(r1);			
			if(breakchk==1) {minCost3=9999;break;}		
			//for(i=0;i<x1.size();i++) {
				System.out.println("b4 1");
				minList = minCost(x0,x1);	
				System.out.println("a4 1");
				for(i=0;i<minList.size()-1;i++) {
					tmp0=minList.get(i);
					minCon.add(tmp0);
					System.out.println("minNode0:x:"+tmp0.x+"  y:"+tmp0.y);
				}
				tmp0=minList.get(minList.size()-1);
				if(tmp0.cost>9000) {minCost3=9999;;breakchk=1;break;}
				minCost3=minCost3+tmp0.cost;
				System.out.println("Cost0:"+minCost0);
				//for(j=0;j<x2.size();j++) {
					minList = minCost(minCon,x3);
					for(i=0;i<minList.size()-1;i++) {
						tmp0=minList.get(i);
						minCon.add(tmp0);
						System.out.println("minNode1:x:"+tmp0.x+"  y:"+tmp0.y);
					}
					tmp0=minList.get(minList.size()-1);				
					if(tmp0.cost>9000) {minCost3=9999;breakchk=1;break;}
					minCost3=minCost3+tmp0.cost;
					System.out.println("Cost1:"+minCost0);
					//for(k=0;k<x3.size();k++){
						minList = minCost(minCon,x4);					
						for(i=0;i<minList.size()-1;i++) {
							tmp0=minList.get(i);
							minCon.add(tmp0);
							System.out.println("minNode2:x:"+tmp0.x+"  y:"+tmp0.y);
						}
						tmp0=minList.get(minList.size()-1);				
						if(tmp0.cost>9000) {minCost3=9999;breakchk=1;break;}
						minCost3=minCost3+tmp0.cost;
						System.out.println("Cost2:"+minCost0);
						//for(l=0;l<x4.size();l++){
							minList = minCost(minCon,x2);			
							for(i=0;i<minList.size()-1;i++) {
								tmp0=minList.get(i);
								minCon.add(tmp0);
								System.out.println("minNode1:x:"+tmp0.x+"  y:"+tmp0.y);
							}
							tmp0=minList.get(minList.size()-1);				
							if(tmp0.cost>9000) {minCost3=9999;breakchk=1;break;}
							minCost3=minCost3+tmp0.cost;
							System.out.println("Cost-final1:"+minCost1);
							//minCost0=0;
							//track routes with costs individually starting from
							//minCon to minCon2 and the cost involved
							//and initialize for the next round
						//}
					//}
				//}
			//}
		}
		//result of 4
		tmpcost= new NodeXY(minCost3);
		for(i=0;i<minCon.size();i++) {
			tmp0=minCon.get(i);
			minCon3.add(tmp0);
			//System.out.println("minNode0:x:"+tmp0.x+"  y:"+tmp0.y);
		}
		minCon3.add(tmpcost);
		
		
		for(y=0;y<x0.size();y++) {			
			minCon.clear();
			minCon.add(r1);			
			if(breakchk==1) {minCost4=9999;break;}		
			//for(i=0;i<x1.size();i++) {
				System.out.println("b4 1");
				minList = minCost(x0,x1);	
				System.out.println("a4 1");
				for(i=0;i<minList.size()-1;i++) {
					tmp0=minList.get(i);
					minCon.add(tmp0);
					System.out.println("minNode0:x:"+tmp0.x+"  y:"+tmp0.y);
				}
				tmp0=minList.get(minList.size()-1);
				if(tmp0.cost>9000) {minCost4=9999;;breakchk=1;break;}
				minCost4=minCost4+tmp0.cost;
				System.out.println("Cost0:"+minCost0);
				//for(j=0;j<x2.size();j++) {
					minList = minCost(minCon,x4);
					for(i=0;i<minList.size()-1;i++) {
						tmp0=minList.get(i);
						minCon.add(tmp0);
						System.out.println("minNode1:x:"+tmp0.x+"  y:"+tmp0.y);
					}
					tmp0=minList.get(minList.size()-1);				
					if(tmp0.cost>9000) {minCost4=9999;breakchk=1;break;}
					minCost4=minCost4+tmp0.cost;
					System.out.println("Cost1:"+minCost0);
					//for(k=0;k<x3.size();k++){
						minList = minCost(minCon,x2);					
						for(i=0;i<minList.size()-1;i++) {
							tmp0=minList.get(i);
							minCon.add(tmp0);
							System.out.println("minNode2:x:"+tmp0.x+"  y:"+tmp0.y);
						}
						tmp0=minList.get(minList.size()-1);				
						if(tmp0.cost>9000) {minCost4=9999;breakchk=1;break;}
						minCost4=minCost4+tmp0.cost;
						System.out.println("Cost2:"+minCost0);
						//for(l=0;l<x4.size();l++){
							minList = minCost(minCon,x3);			
							for(i=0;i<minList.size()-1;i++) {
								tmp0=minList.get(i);
								minCon.add(tmp0);
								System.out.println("minNode1:x:"+tmp0.x+"  y:"+tmp0.y);
							}
							tmp0=minList.get(minList.size()-1);				
							if(tmp0.cost>9000) {minCost4=9999;breakchk=1;break;}
							minCost4=minCost4+tmp0.cost;
							System.out.println("Cost-final1:"+minCost1);
							//minCost0=0;
							//track routes with costs individually starting from
							//minCon to minCon2 and the cost involved
							//and initialize for the next round
						//}
					//}
				//}
			//}
		}
		//result of 5
		tmpcost= new NodeXY(minCost4);
		for(i=0;i<minCon.size();i++) {
			tmp0=minCon.get(i);
			minCon4.add(tmp0);
			//System.out.println("minNode0:x:"+tmp0.x+"  y:"+tmp0.y);
		}
		minCon4.add(tmpcost);

		
		
		for(y=0;y<x0.size();y++) {			
			minCon.clear();
			minCon.add(r1);			
			if(breakchk==1) {minCost5=9999;break;}		
			//for(i=0;i<x1.size();i++) {
				System.out.println("b4 1");
				minList = minCost(x0,x1);	
				System.out.println("a4 1");
				for(i=0;i<minList.size()-1;i++) {
					tmp0=minList.get(i);
					minCon.add(tmp0);
					System.out.println("minNode0:x:"+tmp0.x+"  y:"+tmp0.y);
				}
				tmp0=minList.get(minList.size()-1);
				if(tmp0.cost>9000) {minCost5=9999;;breakchk=1;break;}
				minCost5=minCost5+tmp0.cost;
				System.out.println("Cost0:"+minCost0);
				//for(j=0;j<x2.size();j++) {
					minList = minCost(minCon,x4);
					for(i=0;i<minList.size()-1;i++) {
						tmp0=minList.get(i);
						minCon.add(tmp0);
						System.out.println("minNode1:x:"+tmp0.x+"  y:"+tmp0.y);
					}
					tmp0=minList.get(minList.size()-1);				
					if(tmp0.cost>9000) {minCost5=9999;breakchk=1;break;}
					minCost5=minCost5+tmp0.cost;
					System.out.println("Cost1:"+minCost0);
					//for(k=0;k<x3.size();k++){
						minList = minCost(minCon,x3);					
						for(i=0;i<minList.size()-1;i++) {
							tmp0=minList.get(i);
							minCon.add(tmp0);
							System.out.println("minNode2:x:"+tmp0.x+"  y:"+tmp0.y);
						}
						tmp0=minList.get(minList.size()-1);				
						if(tmp0.cost>9000) {minCost5=9999;breakchk=1;break;}
						minCost5=minCost5+tmp0.cost;
						System.out.println("Cost2:"+minCost0);
						//for(l=0;l<x4.size();l++){
							minList = minCost(minCon,x4);			
							for(i=0;i<minList.size()-1;i++) {
								tmp0=minList.get(i);
								minCon.add(tmp0);
								System.out.println("minNode1:x:"+tmp0.x+"  y:"+tmp0.y);
							}
							tmp0=minList.get(minList.size()-1);				
							if(tmp0.cost>9000) {minCost5=9999;breakchk=1;break;}
							minCost5=minCost5+tmp0.cost;
							System.out.println("Cost-final1:"+minCost1);
							//minCost0=0;
							//track routes with costs individually starting from
							//minCon to minCon2 and the cost involved
							//and initialize for the next round
						//}
					//}
				//}
			//}
		}
		//result of 5				
		tmpcost= new NodeXY(minCost5);
		for(i=0;i<minCon.size();i++) {
			tmp0=minCon.get(i);
			minCon5.add(tmp0);
			//System.out.println("minNode0:x:"+tmp0.x+"  y:"+tmp0.y);
		}
		minCon5.add(tmpcost);
		
		
		//compare all the results and send the one with min cost as minList
		int temp=0;
		int min=0;
		
		System.out.println("MinCost0"+minCost0);
		System.out.println("MinCost1"+minCost1);
		System.out.println("MinCost2"+minCost2);
		System.out.println("MinCost3"+minCost3);
		System.out.println("MinCost4"+minCost4);
		System.out.println("MinCost5"+minCost5);
		
		int dist[] = new int[6];
		dist[0] = minCost0;
		dist[1] = minCost1;
		dist[2] = minCost2;
		dist[3] = minCost3;
		dist[4] = minCost4;
		dist[5] = minCost5;
		
		//get the minimum of all the costs
		int min1 = minCost0;
		for (  k=0; k<dist.length; k++ ){
			if ( dist[k] < min1 ) min1 = dist[k];
		}
		
		System.out.println("FINALLLL:"+min1);
		//send the min distance array among all the 6 minCost's
		for(k=0;k<dist.length;k++) {
			if(min1==dist[k] && k==0) {
							System.out.println("min1--"+dist[k]+"Val:"+k );
							System.out.println("MINCON:"+minCon.size()+" MINCON0000:"+minCon0.size());
							tmp0= new NodeXY();
							for(i=0;i<minCon0.size();i++) {
								tmp0=minCon0.get(i);
								resCon.add(tmp0);
								System.out.println("minNode0:x:"+tmp0.x+"  y:"+tmp0.y);
							}							
							//resCon=minCon0;
							break;
							}
			if(min1==dist[k] && k==1) {
				System.out.println("min1--"+dist[k]+"Val:"+k);
				tmp0= new NodeXY();
				for(i=0;i<minCon1.size();i++) {
					tmp0=minCon1.get(i);
					resCon.add(tmp0);
					System.out.println("minNode0:x:"+tmp0.x+"  y:"+tmp0.y);
				}							
				//resCon=minCon1;
				break;
				}
			if(min1==dist[k] && k==2) {
				System.out.println("min1--"+dist[k]+"Val:"+k);
				tmp0= new NodeXY();
				for(i=0;i<minCon2.size();i++) {
					tmp0=minCon2.get(i);
					resCon.add(tmp0);
					System.out.println("minNode0:x:"+tmp0.x+"  y:"+tmp0.y);
				}							
				//resCon=minCon2;
				break;
			}
			if(min1==dist[k] && k==3) {
				System.out.println("min1--"+dist[k]+"Val:"+k);
				tmp0= new NodeXY();
				for(i=0;i<minCon3.size();i++) {
					tmp0=minCon3.get(i);
					resCon.add(tmp0);
					System.out.println("minNode0:x:"+tmp0.x+"  y:"+tmp0.y);
				}							
				//resCon=minCon3;
				break;
				}
			if(min1==dist[k] && k==4) {
				System.out.println("min1--"+dist[k]+"Val:"+k);
				tmp0= new NodeXY();
				for(i=0;i<minCon4.size();i++) {
					tmp0=minCon4.get(i);
					resCon.add(tmp0);
					System.out.println("minNode0:x:"+tmp0.x+"  y:"+tmp0.y);
				}							
				//resCon=minCon4;
				break;
				}
			if(min1==dist[k] && k==5) {
				System.out.println("min1--"+dist[k]+"Val:"+k);
				tmp0= new NodeXY();
				for(i=0;i<minCon5.size();i++) {
					tmp0=minCon5.get(i);
					resCon.add(tmp0);
					System.out.println("minNode0:x:"+tmp0.x+"  y:"+tmp0.y);
				}											
				//resCon=minCon5;
				break;
				}
		}
		return resCon;
	}

	public ArrayList<NodeXY> minCost(ArrayList<NodeXY> x1, ArrayList<NodeXY> x2) {
		ArrayList<NodeXY> tmpList0=new ArrayList<NodeXY>();
		ArrayList<NodeXY> tmpListcon=new ArrayList<NodeXY>();
		tmpList0.clear();
		tmpListcon.clear();
		int costMin=9999;
		System.out.println("TESTC"+x1.size());
		System.out.println("TESTD"+x1.size());
		NodeXY nodeSrc = new NodeXY();
		NodeXY nodeDest = new NodeXY();
		for(int i=0;i<x1.size();i++) {
			NodeXY ni = new NodeXY();
			ni=x1.get(i);
			System.out.println("into ni");
			for(int j=0;j<x2.size();j++) {
				System.out.println("into nj");
				NodeXY nj = new NodeXY();
				nj=x2.get(j);
				System.out.println("NIJJJJ x:"+ni.x+" y:"+ni.y+"   NJ x:"+nj.x+"  y:"+nj.y);
				Shortest sp=new Shortest();
				tmpList0=sp.generateNodes(ni,nj);
				if(tmpList0.size()>1) {
					NodeXY tmp0= new NodeXY();
					tmp0=tmpList0.get(tmpList0.size()-1);
					System.out.println("NI x:"+ni.x+" y:"+ni.y+"   NJ x:"+nj.x+"  y:"+nj.y+"Cost: "+tmp0.cost);
					if(costMin>tmp0.cost) {
						costMin=tmp0.cost;
						nodeSrc = ni;
						nodeDest = nj;
					}
				}
			}
		}
		//tmpListcon.add(nodeSrc);
		tmpListcon.add(nodeDest);
		NodeXY cos = new NodeXY(costMin);
		tmpListcon.add(cos);
		return tmpListcon;
	}

	//@Override
	public void paint(Graphics g)
	{	
		//super.paint(g);
		if(initialFlag==0){		
			//System.out.println("PAINT111 ROUTINE InitialFlag"+initialFlag+"Populate:"+i);
			Graphics2D g2=(Graphics2D)g;
			g.drawImage(map,80,0,this);
			g.drawImage(logo,1100,40,this);
			initialFlag=3;
			//setVisible(true);
		}
		if(initialFlag==2){
			//sleep();
			ArrayList<NodeXY> aList=new ArrayList<NodeXY>();
			ArrayList<NodeXY> tmpList0=new ArrayList<NodeXY>();

			Graphics2D g2=(Graphics2D)g;
			g.drawImage(map,80,0,this);
			g.drawImage(logo,1100,40,this);
			String data[][],q1;
					

			try{
				int j=0;
				NodeXY t1 = new NodeXY();
				NodeXY t2 = new NodeXY();
				CommonService x2 = new CommonService();
				for(int i=0;i<minCon.size()-1;i++){

						t1=minCon.get(i);
						if(i==(minCon.size()-1)) t2=minCon.get(i);
						else t2=minCon.get(i+1);
						if(t2.x!=0){
							System.out.println("GRAPH x:"+t1.x+" y: "+t1.y+" t2x: "+t2.x+" y:"+t2.y);
							Line2D line =new Line2D.Double(t1.x,t1.y,t2.x,t2.y);
							g2.setStroke(new BasicStroke(3));
							g2.setColor(Color.BLUE);
							g2.draw(line);
							g.setColor(Color.RED);
							g.fillOval((int)t1.x,(int)t1.y,10,10);
							q1="select LocationName from test.Node where CoordinateXPoint="+t1.x+" and "+
							"CoordinateYPoint="+t1.y;
							System.out.println("Q1:"+q1);
							data = x2.Serlist(q1);
							if(data.length>0)
							g.drawString(data[0][0],(int)t1.x,(int)t1.y);
							q1="select LocationName from test.Node where CoordinateXPoint="+t2.x+" and "+
							"CoordinateYPoint="+t2.y;
							System.out.println("Q2:"+q1);
							data = x2.Serlist(q1);
							if(data.length>0)
							g.drawString(data[0][0],(int)t2.x,(int)t2.y);							
							try {
								output1.write(t1.x+":"+t1.y+"=>"+t2.x+":"+t2.y+"\n");
							} catch (IOException ex1) {
								// TODO Auto-generated catch block
								ex1.printStackTrace();
							}							
							//g2.drawString(t2.place,(int)t2.x,(int)t2.y);
							//g2.drawString(""+t2.cost,t2.x,t2.y);
						}
				}
				
				NodeXY t3 = new NodeXY();
				if(t2.x!=0) {
					t3=minCon.get(minCon.size()-1);
					g.setColor(Color.RED);	
					System.out.println("t30 cost:"+t3.cost+"t2 x"+t2.x+"t2y:"+t2.y);
					g.drawString("Cost:"+t3.cost,t2.x-10,t2.y+10);
					try {
						output1.write(t2.x+":"+t2.y+"\n");
						output1.write("Cost: "+t3.cost+"\n");
					} catch (IOException ex1) {
						// TODO Auto-generated catch block
						ex1.printStackTrace();
					}
					
				}
				else {
					t3=minCon.get(minCon.size()-1);
					g.setColor(Color.RED);	
					System.out.println("t31 cost:"+t3.cost+"t2 x"+t1.x+"t2y:"+t1.y);
					g.drawString("Cost:"+t3.cost,t1.x-10,t1.y+10);
					try {
						output1.write(t2.x+":"+t2.y+"\n");
						output1.write("Cost: "+t3.cost+"\n");
					} catch (IOException ex1) {
						// TODO Auto-generated catch block
						ex1.printStackTrace();
					}
				}
				output1.write("***END getOptimalPath"+"\n");			
				try{
					System.out.println("init2 close-0");					
					output1.flush();
					output1.close();					
					System.out.println("init2 close-00");					
				}catch(Exception e1){
					e1.printStackTrace();
					System.out.println("error in getOptimalPath repaint");
					System.out.println("error init2 close");
					}
				
			}
			catch (Exception e) {
				System.out.println("Exception in paint123");
			}
				//setVisible(true);
		}

		if(initialFlag==1){
			sleep();
			//System.out.println("PAINT222 ROUTINE InitialFlag"+initialFlag+"Populate:"+i);
			Graphics2D g2=(Graphics2D)g;
			g.drawImage(map,80,0,this);
			g.drawImage(logo,1100,40,this);
				try{
					if(paintList.size() > 0) {
						for(ListIterator<Node> list=paintList.listIterator();list.hasNext();) {
							Node p = list.next();
							//System.out.println(p.place+"=> "+p.x+","+p.y+"CT:"+p.childType+"NT:"+p.nodeType);
							g.setColor(Color.RED);	
							g.fillOval((int)p.x,(int)p.y,10,10);
							g.drawString(p.place,(int)p.x,(int)p.y);
						
							if(p.nodeType==0) {
							try {
								//output1.append(p.place+"=>"+p.x+":"+p.y+"\n");
								output1.write(p.place+"=>"+p.x+":"+p.y+"\n");
							} catch (IOException ex1) {
								// TODO Auto-generated catch block
								ex1.printStackTrace();
							}
							}
						}
					}
					output1.write("***END getPath\n");
					try{
						System.out.println("init1 close-0");						
						output1.flush();
						output1.close();
						System.out.println("init1 close-00");						
					}catch(Exception e1){System.out.println("error");
					System.out.println("error init2 close-0");					
					}
					
				}
				catch (Exception e) {
					System.out.println("Exception in paint111");
				}
				//setVisible(true);
		}
		
		if(initialFlag==4){		
			ArrayList<NodeXY> aList=new ArrayList<NodeXY>();
			ArrayList<NodeXY> tmpList0=new ArrayList<NodeXY>();

			Graphics2D g2=(Graphics2D)g;
			g.drawImage(map,80,0,this);
			g.drawImage(logo,1100,40,this);
			String data[][],q1;
			try{
				int j=0;
				NodeXY t1 = new NodeXY();
				NodeXY t2 = new NodeXY();
				CommonService x2 = new CommonService();
				System.out.println("INTO GRAPH");
				for(int i=0;i<minCon.size()-1;i++){
					System.out.println("INTO GRAPH min:"+minCon.size());
						t1=minCon.get(i);
						if(i==(minCon.size()-1)) t2=minCon.get(i);
						else t2=minCon.get(i+1);
						if(t2.x!=0){
							System.out.println("GRAPH x:"+t1.x+" y: "+t1.y+" t2x: "+t2.x+" y:"+t2.y);
							Line2D line =new Line2D.Double(t1.x,t1.y,t2.x,t2.y);
							g2.setStroke(new BasicStroke(3));
							g2.setColor(Color.BLUE);
							g2.draw(line);
							//g2.drawString(t2.place,(int)t2.x,(int)t2.y);
							//g2.drawString(""+t2.cost,t2.x,t2.y);
							
							g.setColor(Color.RED);
							g.fillOval((int)t1.x,(int)t1.y,10,10);
							/*q1="select LocationName from test.Node where CoordinateXPoint="+t1.x+" and "+
							"CoordinateYPoint="+t1.y;
							System.out.println("Q1:"+q1);
							data = x2.Serlist(q1);
							if(data.length>0)
							g.drawString(data[0][0],(int)t1.x,(int)t1.y);
							q1="select LocationName from test.Node where CoordinateXPoint="+t2.x+" and "+
							"CoordinateYPoint="+t2.y;
							System.out.println("Q2:"+q1);
							data = x2.Serlist(q1);
							if(data.length>0)
							g.drawString(data[0][0],(int)t2.x,(int)t2.y);
							*/
							j=10;
							String ent="";
							if(i==0) {
								q1="SELECT LocationName,e.entityname FROM node n, entitytype e"+
								" where e.entitytypeid=n.entitytypeid " +
								" and CoordinateXPoint="+t1.x+" and "+
								"CoordinateYPoint="+t1.y+" order by e.entitytypeid";
								data = x2.Serlist(q1);
								if(data.length>0)
								g.drawString(data[0][0],(int)t1.x,(int)t1.y);
								if(t1.type==1){ent="ATM";}
								else if(t1.type==2){ent="HOTEL";}
								else if(t1.type==3){ent="HOSPITAL";}
								else if(t1.type==0){ent="PLACE";}
								else {ent="MALL";}
								j=10;
								g.drawString(ent,(int)t1.x+10+j,(int)t1.y+10+j);
							}

							q1="SELECT LocationName,e.entityname FROM node n, entitytype e"+
							" where e.entitytypeid=n.entitytypeid " +
							" and CoordinateXPoint="+t2.x+" and "+
							"CoordinateYPoint="+t2.y+" order by e.entitytypeid";
							
							data = x2.Serlist(q1);
							if(data.length>0)
							if(t2.type==1){ent="ATM";}
							else if(t2.type==2){ent="HOTEL";}
							else if(t2.type==3){ent="HOSPITAL";}
							else if(t1.type==0){ent="PLACE";}
							else {ent="MALL";}								
							g.drawString(data[0][0],(int)t2.x+j,(int)t2.y+j);
							g.drawString(ent,(int)(t2.x+10+j),(int)(t2.y+10+j));
							//for(i=1;i<data.length;i++){
								j=j+10;
							//	System.out.println("Data:"+i+":"+data[i][1]);
								//g.drawString(data[i][1],(int)t1.x+30,(int)t1.y+j);
							//}							
							
						}
				}
				NodeXY t3 = new NodeXY();
				if(t2.x!=0) {
					t3=minCon.get(minCon.size()-1);
					g.setColor(Color.RED);	
					g.fillOval((int)t2.x,(int)t2.y,10,10);					
					System.out.println("t30 cost:"+t3.cost+"t2 x"+t2.x+"t2y:"+t2.y);
					g.drawString(""+t3.cost,t2.x,t2.y);
				}
				else {
					t3=minCon.get(minCon.size()-1);
					g.setColor(Color.RED);	
					g.fillOval((int)t2.x,(int)t2.y,10,10);					
					System.out.println("t31 cost:"+t3.cost+"t2 x"+t1.x+"t2y:"+t1.y);
					g.drawString(""+t3.cost,t1.x,t1.y);
				}
			}
			catch (Exception e) {
				System.out.println("Exception in paint123");
			}
				//setVisible(true);
		}

		if(initialFlag==5){		
			ArrayList<NodeXY> aList=new ArrayList<NodeXY>();
			ArrayList<NodeXY> tmpList0=new ArrayList<NodeXY>();

			Graphics2D g2=(Graphics2D)g;
			g.drawImage(map,80,0,this);
			g.drawImage(logo,1100,40,this);
			String data[][],q1;
			String ent;
			try{
				int j=0;
				NodeXY t1 = new NodeXY();
				NodeXY t2 = new NodeXY();
				CommonService x2 = new CommonService();
				System.out.println("INTO GRAPH");
				for(int i=0;i<minCon.size()-1;i++){
					System.out.println("INTO GRAPH min:"+minCon.size());
						t1=minCon.get(i);
						if(i==(minCon.size()-1)) t2=minCon.get(i);
						else t2=minCon.get(i+1);
						if(t2.x!=0){
							System.out.println("GRAPH x:"+t1.x+" y: "+t1.y+" t2x: "+t2.x+" y:"+t2.y);
							Line2D line =new Line2D.Double(t1.x,t1.y,t2.x,t2.y);
							g2.setStroke(new BasicStroke(3));
							g2.setColor(Color.BLUE);
							g2.draw(line);
							//g2.drawString(t2.place,(int)t2.x,(int)t2.y);
							//g2.drawString(""+t2.cost,t2.x,t2.y);
							
							g.setColor(Color.RED);
							g.fillOval((int)t1.x,(int)t1.y,10,10);
							j=10;
							if(i==0) {
								q1="SELECT LocationName,e.entityname FROM node n, entitytype e"+
								" where e.entitytypeid=n.entitytypeid " +
								" and CoordinateXPoint="+t1.x+" and "+
								"CoordinateYPoint="+t1.y+" order by e.entitytypeid";
								data = x2.Serlist(q1);
								if(data.length>0)
								g.drawString(data[0][0],(int)t1.x,(int)t1.y);
								g.drawString(""+t1.type,(int)t1.x+10,(int)t1.y+10);
							}

							q1="SELECT LocationName,e.entityname FROM node n, entitytype e"+
							" where e.entitytypeid=n.entitytypeid " +
							" and CoordinateXPoint="+t2.x+" and "+
							"CoordinateYPoint="+t2.y+" order by e.entitytypeid";
							
							data = x2.Serlist(q1);
							if(data.length>0)
							g.drawString(data[0][0],(int)t2.x+j,(int)t2.y+j);
							g.drawString(""+t2.type,(int)(t2.x+10+j),(int)(t2.y+10+j));							
						}
				}
				NodeXY t3 = new NodeXY();
				if(t2.x!=0) {
					t3=minCon.get(minCon.size()-1);
					g.setColor(Color.RED);	
					g.fillOval((int)t2.x,(int)t2.y,10,10);					
					System.out.println("t30 cost:"+t3.cost+"t2 x"+t2.x+"t2y:"+t2.y);
					g.drawString(""+t3.cost,t2.x+30,t2.y+30);
				}
				else {
					t3=minCon.get(minCon.size()-1);
					g.setColor(Color.RED);	
					g.fillOval((int)t2.x,(int)t2.y,10,10);					
					System.out.println("t31 cost:"+t3.cost+"t2 x"+t1.x+"t2y:"+t1.y);
					g.drawString(""+t3.cost,t1.x+30,t1.y+30);
				}
			}
			catch (Exception e) {
				System.out.println("Exception in paint123");
			}
				//setVisible(true);
		}		

	}
	
	public ArrayList<Node> fetchList(int x, int y, int radius) {
		//System.out.println("into Fetchlisti"+i);
		initialFlag=1;
		rangeQueryList.clear();
		query = new RangeQuery(pointQuadTree,x,y,radius);
		rangeQueryList=query.search();		
		i=i++;
		return rangeQueryList;

	}

	public void setLayout() {
		cp.setLayout(null);
		entityList1 = new JList(entities);
		entityList2 = new JList(entities);
		entityList3 = new JList(entities);
		entityList4 = new JList(entities);
		entityList1.setVisibleRowCount(1);
		entityList2.setVisibleRowCount(1);
		entityList3.setVisibleRowCount(1);
		entityList4.setVisibleRowCount(1);
		setBounds(0,0,1500,1500);
		lis.setBounds(800,280,190,150);
		map = getToolkit().getImage("map.jpg");
		logo=getToolkit().getImage("logo.jpg");
		cp.add(label3);
		//label3.setBounds(700,130,236,20);
		label3.setBounds(700,170,236,20);
		cp.add(label4); //Entity1
		label4.setBounds(850, 200, 100, 20);
		entityList1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		entityScroller1=new JScrollPane(entityList1);
		entityScroller1.setBounds(890, 200, 100, 20);
		cp.add(entityScroller1);

		cp.add(label5); //Entity2
		label5.setBounds(850, 240, 100, 20);
		entityList2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		entityScroller2=new JScrollPane(entityList2);
		entityScroller2.setBounds(890, 240, 100, 20);
		cp.add(entityScroller2); 

		cp.add(label6); //Entity3
		label6.setBounds(850, 280, 100, 20);
		entityList3.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		entityScroller3=new JScrollPane(entityList3);
		entityScroller3.setBounds(890, 280, 100, 20);
		cp.add(entityScroller3);

		cp.add(label7); //Entity4
		label7.setBounds(850, 320, 100, 20);
		entityList4.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		entityScroller4=new JScrollPane(entityList4);
		entityScroller4.setBounds(890, 320, 100, 20);
		cp.add(entityScroller4);

		radiusLabel.setBounds(850, 360, 100,20);
		radiusText.setBounds(890, 360, 100,20);

		cp.add(radiusLabel);
		cp.add(radiusText);

		cp.add(sourceNode);
		sourceNode.setBounds(700,200,136,20);
		//sourceNode.setVisible(true);

		entityType.put("ATM", 1);
		entityType.put("HOTEL", 2);
		entityType.put("HOSPITAL", 3);
		entityType.put("MALL", 4);
	}

	public void populateTree() {
		rangeQueryList.add(pointQuadTree.root);
		long startTime = System.nanoTime();
		pointQuadTree.populateTree();
		long endTime = System.nanoTime();
		//System.out.println("End-Time:"+endTime+" StartTime:"+startTime+"Time:"+(endTime-startTime));
		query0 = new RangeQuery(pointQuadTree,50,50,1000);
		rangeQueryList0=query0.search();		
		int size = rangeQueryList0.size();
		//System.out.println("IIII---SIZE:"+size+ "i:"+i);
		sourceNode.removeAllItems();
		sourceNodeComboList.clear();
		for (int i0 = 0; i0 < size; i0++) {
			Node p0 = (Node) rangeQueryList0.get(i0);
			//System.out.println("Id:"+node.id +"X:"+node.x+"Y:"+node.y+"nodeType:"+node.nodeType+"place:"+node.place);
			//if(p0.nodeType==0) sourceNode.insertItemAt(p0.place, i0+1); //Load only Places ie., nodeType=0, assume that atm=1, hotel=2
			if(p0.nodeType==0) {
				sourceNode.addItem(p0.place);
				sourceNodeComboList.add(p0);
			}
		}
		i++;
		helpArr=help();
		for (int i1=0; i1<helpArr.size() ;i1++ ) {
			splitHelp=splitHelp+helpArr.get(i1)+"\n";
		}
	}

	public ArrayList<String> help() {
		ArrayList<String> sArr = new ArrayList<String>();
		String s;
		FileInputStream fileStream;
		DataInputStream in;
		try {
			fileStream = new FileInputStream("SplitSearch.txt");
			in = new DataInputStream(fileStream);
			while (in.available() != 0) {
				s = in.readLine();
				sArr.add(s);
			}
			in.close();

		} catch (IOException e) {

			System.out.println(e);
		}
		return sArr;
	}
	
	
	public int TestCaseCompare() {

			ArrayList nodeList = new ArrayList();
			FileInputStream fileStream, fileStreamGolden;
		
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		    Date date1 = new Date();

			DataInputStream in;
			DataInputStream inGolden;
			
			/*Writer output1=null;
		    File file = new File("results.txt");
		    try{
				output1 = new BufferedWriter(new FileWriter(file));					
		    }catch(Exception e1){System.out.println("error");}
			*/

			int i = 0;
			
			String place[]={
					"Mattekere",
					 "Hebbal",
					"TumkurRoad",
					"BellaryRoad",
					"AshokNagar",
					"Mattadahalli",
					"SankeyTank",
					"IISc",
					"GovtSoapFactory",
					"SubahdarChhatamRoad",
					"MilkColony",
					"Malleshweram",
					"Richard'sTown",
					"Madigarpalaya",
					"BangaloreEast",
					"Maharaja'sPalace",
					"Boyapalya",
					"CleavelandTown",
					"venkatarangiengar_rd",
					"Second_LinkRoad",
					"RajajiNagar",
					"ErrappaBlock",
					"Cox_town",
					"Bangalore_cantt",
					"St_John_Hill",
					"Nehru_Nagar",
					"Odderpalya",
					"Dayanand_Nagar",
					"Vasant_Nagar",
					"Bhashyam_Nagar",
					"Indira_Nagar",
					"Gandhi_Nagar",
					"Magadi_Road",
					"Cubbon_Park",
					"Majestic",
					"Hoshalli",
					"Brigade_Road",
					"Avenue_Road",
					"Trinity_Chruch",
					"Kantirawa_Stadium",
					"Richmond_Nagar",
					"Victoria_Road",
					"Mission_Road",
					"City_Market",
					"Chamrajpet",
					"Shanti_Nagar",
					"Azad_nagar",
					"Agram",
					"Domlur",
					"BapujiNagar",
					"LalBagh_Fort_Road",
					"KampeGuda_Nagar",
					"Nitsarda_Road",
					"Annepalya",
					"HombeGowda_Nagar",
					"Mysore_Rd",
					"Vivekanagar",
					"GandhiBazar",
					"LalBaghGardens",
					"Grass_Farm",
					"KrishnaPark",
					"VaniVilasRoad",
					"Siddapura",
					"HanumantaNagar",
					"BullTemple",
					"AnnekaiRoad",
					"HosurRoad",
					"Banshankari",
					"3rdMainRd",
					"JayaNagar",
					"sudduguntapalya",
					"sarakki_agrahar",
					"KanakPuraRd",
					"BannerGattaRd",
					"ElectronicsCity"
			};
		   /* try{
				FileWriter fstream = new FileWriter("results.txt");
				BufferedWriter output1 = new BufferedWriter(fstream);					
		    }catch(Exception e1){System.out.println("error");}
			*/
			for(i=0;i<place.length;i++)
			{
			
				try {
		
				    FileWriter fstream = new FileWriter("results.txt",true);
					//FileWriter fstream = new FileWriter("results.txt");
					BufferedWriter output1 = new BufferedWriter(fstream);
					
					//fileStream = new FileInputStream("F:\\TEMP\\writer_2009_04_22_23_52_43.txt");
					fileStream = new FileInputStream("writer_"+place[i]+".txt");
					in = new DataInputStream(fileStream);
					
					//fileStreamGolden = new FileInputStream("writer_golden.txt");
					fileStreamGolden = new FileInputStream("Golden.txt");
					inGolden = new DataInputStream(fileStreamGolden);		
					String a,b="";
					int c=0;
		            a = in.readLine();
		                
		            while(inGolden.available() !=0) {
		            	b=inGolden.readLine();
		            	if(b.equals(a)) break; 
		            }
		                System.out.println("aXX:"+a);
		                System.out.println("bXX:"+b);
						output1.write("***"+dateFormat.format(date1)+"\n");

						//output1.write(a+"\n");
						output1.write(place[i]+":");
						
		            while(in.available() != 0 && inGolden.available() != 0) {
		            	a = in.readLine();
		            	b = inGolden.readLine();
		                System.out.println("a:"+a);
		                System.out.println("b:"+b);            	
		            	if(!a.equals(b))  {
		            		System.out.println("Error@@@");
		            		c=1;
		            		output1.write("Error\n");            		
		            		break;
		            	}
		            }
		            if(c==0) {System.out.println("Success");
		            			output1.write("Success\n");
		            			}
		                	
					in.close();
					inGolden.close();
					output1.close();
				} catch (IOException e) {
		
					System.out.println(e);
				}
				
			}
			/*
			try {
				output1.close();
			} catch (IOException e) {
				e.printStackTrace();
			}*/
			
			return 0;
	}
		

	public void TestCaseGolden() {
			int i=0;
			SplitSearch splitObj = new SplitSearch();
			splitObj.SplitSearch();

			splitObj.TestCaseGolden1();
			
	}

	
	public int TestCaseGolden1() {
		
		CommonService x2 = new CommonService();
		int i = 0;
		String data[][],q1;
		
		//SplitSearch splitObj = new SplitSearch();
		//splitObj.SplitSearch();

		//splitObj.TestCaseGolden();
		
		
		String place[]={
				"Mattekere",
				 "Hebbal",
				"TumkurRoad",
				"BellaryRoad",
				"AshokNagar",
				"Mattadahalli",
				"SankeyTank",
				"IISc",
				"GovtSoapFactory",
				"SubahdarChhatamRoad",
				"MilkColony",
				"Malleshweram",
				"Richard'sTown",
				"Madigarpalaya",
				"BangaloreEast",
				"Maharaja'sPalace",
				"Boyapalya",
				"CleavelandTown",
				"venkatarangiengar_rd",
				"Second_LinkRoad",
				"RajajiNagar",
				"ErrappaBlock",
				"Cox_town",
				"Bangalore_cantt",
				"St_John_Hill",
				"Nehru_Nagar",
				"Odderpalya",
				"Dayanand_Nagar",
				"Vasant_Nagar",
				"Bhashyam_Nagar",
				"Indira_Nagar",
				"Gandhi_Nagar",
				"Magadi_Road",
				"Cubbon_Park",
				"Majestic",
				"Hoshalli",
				"Brigade_Road", 
				"Avenue_Road", 
				"Trinity_Chruch",
				"Kantirawa_Stadium",
				"Richmond_Nagar",
				"Victoria_Road",
				"Mission_Road",
				"City_Market",
				"Chamrajpet",
				"Shanti_Nagar",
				"Azad_nagar",
				"Agram",
				"Domlur",
				"BapujiNagar",
				"LalBagh_Fort_Road",
				"KampeGuda_Nagar",
				"Nitsarda_Road",
				"Annepalya",
				"HombeGowda_Nagar",
				"Mysore_Rd",
				"Vivekanagar",
				"GandhiBazar",
				"LalBaghGardens",
				"Grass_Farm",
				"KrishnaPark",
				"VaniVilasRoad",
				"Siddapura",
				"HanumantaNagar",
				"BullTemple",
				"AnnekaiRoad",
				"HosurRoad",
				"Banshankari",
				"3rdMainRd",
				"JayaNagar",
				"sudduguntapalya",
				"sarakki_agrahar",
				"KanakPuraRd",
				"BannerGattaRd",
				"ElectronicsCity"
		};
		FileWriter fstream = null;

		for(i=0;i<place.length;i++)
		{
			while(true) {
				try {
				    //FileWriter fstream = new FileWriter("Golden.txt",true);
					System.out.println("open output1-0");
					try{
						   fstream = new FileWriter("Golden.txt",true);
						}catch(Exception e1){
						e1.printStackTrace();
						System.out.println("error in file openning golden");}					
				    output1 = new BufferedWriter(fstream);
					System.out.println("open output1-00");
					//BufferedWriter output1 = new BufferedWriter(fstream);
					
					//srcindx=sourceNode.getSelectedIndex();
					//Node src0 = (Node) sourceNodeComboList.get(srcindx);
					//Node src0 = (Node) rangeQueryList0.get(srcindx);
					//xP=(int)src0.x;
					//yP=(int)src0.y;
				    
					q1="select distinct CoordinateXPoint,CoordinateYPoint" +
					   " from test.Node where LocationName='"+place[i]+"'";
					System.out.println("q1:"+q1);
					data = x2.Serlist(q1);
					xP = Integer.parseInt(data[0][0]);
					yP = Integer.parseInt(data[0][1]);		
					
					//System.out.println("XXXX:"+src0.x+"YYYYY:"+src0.y+"place:"+src0.place);
					entity1 = "ATM";
					entity2 = "HOTEL";
					entity3 = "HOSPITAL";
					entity4 = "MALL";
					//System.out.println("getPath Ent1:"+entity1+"Ent2:"+entity2+"Type:"+entityType.get("ATM"));
	
					radius=200;
					radiusP=200;
				
					//try{					
						//String fn1="writer_"+src0.place+".txt";
					    //FileWriter fstream = new FileWriter(fn1,true);
						//FileWriter fstream = new FileWriter(fn1);

					    
						output1.write("***START getPath:"+place[i]+"\n");
						output1.write("ShowPoints Inputs:\n");
						output1.write("Source Points    :"+place[i]+":"+xP + ":" + yP+"\n");					
						output1.write("Radius           :"+radius+"\n");
						output1.write("Results          :\n");				    
				
						//Array to capture the entities falling into L2 Norm from source-point xP,yP
						//initialFlag=1;
						paintList.clear();
						paintList = fetchList(xP,yP,radiusP);
						//initialFlag=10;
						//validate();
						//repaint(0,0,1500,1500);
						try { 
							Thread.sleep(100); 
							} 
							catch(InterruptedException ex) {} 		
						repaint();
						setVisible(true);						
						//validate();
						try { 
							Thread.sleep(100); 
							} 
							catch(InterruptedException ex) {} 


						try{
							System.out.println("into close1");
							output1.flush();
							output1.close();					
						}catch(Exception e1){
							e1.printStackTrace();
							System.out.println("error in cose1");}
						
						System.out.println("open output1-1");
						try{
							   fstream = new FileWriter("Golden.txt",true);
							}catch(Exception e1){
							e1.printStackTrace();
							System.out.println("error in file openning golden");}						
						output1 = new BufferedWriter(fstream);			
						System.out.println("open output1-2");
						
						System.out.println("****INTO OPTI");
						output1.write("***START getOptimalPath:"+place[i]+"\n");
						output1.write("ShowPath Inputs:\n");
						output1.write("Source Points    :"+place[i]+":"+xP + ":" + yP+"\n");
						output1.write("Radius           :"+radius+"\n");
						output1.write("entity1          :"+entity1+"\n");
						output1.write("entity2          :"+entity2+"\n");
						output1.write("entity3          :"+entity3+"\n");
						output1.write("entity4          :"+entity4+"\n");
						output1.write("Results          :\n");				    
						radiusP=radius;
						//paintList.clear();
						aList1.clear();
						aList2.clear();
						aList3.clear();
						aList4.clear();
						aListcon.clear();
						//paintList = fetchList(xP,yP,radiusP);//paintList array stores places and other entities
						paintList1.clear();//clear Array that stores entitytype 1
						paintList2.clear();//clear Array that stores entitytype 2
						paintList3.clear();//clear Array that stores entitytype 3
						paintList4.clear();//clear Array that stores entitytype 4
						NodeXY r1 = new NodeXY(xP,yP,0);
						aListcon.add(r1);
			
						if(paintList.size() > 0) {
								for(ListIterator<Node> list=paintList.listIterator();list.hasNext();) {
									Node clon = list.next();
									if (clon.nodeType==entityType.get(entity1)) {paintList1.add(clon); 
																					NodeXY i1=new NodeXY((int)clon.x,(int)clon.y,(int) clon.nodeType);
																					aList1.add(i1);}
									if (clon.nodeType==entityType.get(entity2)) {paintList2.add(clon);
																					NodeXY i2=new NodeXY((int)clon.x,(int)clon.y,(int) clon.nodeType);
																					aList2.add(i2);}
									if (clon.nodeType==entityType.get(entity3)) {paintList3.add(clon);
																					NodeXY i3=new NodeXY((int)clon.x,(int)clon.y,(int) clon.nodeType);
																					aList3.add(i3);}
									if (clon.nodeType==entityType.get(entity4)) {paintList4.add(clon);
																					NodeXY i4=new NodeXY((int)clon.x,(int)clon.y,(int) clon.nodeType);
																					aList4.add(i4);}
								}
						}
						//find the min cost
						int minCost=0;
						minCon.clear();
						minCon.add(r1);
						ArrayList<NodeXY> minList = new ArrayList<NodeXY>();//Array to store only the path with min cost
						System.out.println("TESTB"+minList.size());
						minList=minCost(aListcon,aList1);
						System.out.println("TESTA"+minList.size());
						NodeXY tmp0 = new NodeXY();
						for(int i0=0;i0<minList.size()-1;i0++) {
							tmp0=minList.get(i0);
							minCon.add(tmp0);
							System.out.println("minNode1:x:"+tmp0.x+"  y:"+tmp0.y);
						}
						tmp0=minList.get(minList.size()-1);
						System.out.println("minNode1 cost:x:"+tmp0.cost);
						if(tmp0.cost>9000) break;
						minCost=tmp0.cost;
						System.out.println("minCOST:"+minCost);
						//minList=minCost(aList1,aList2);
						minList=minCost(minCon,aList2);
						for(int i0=0;i0<minList.size()-1;i0++) {
							tmp0=minList.get(i0);
							minCon.add(tmp0);
							System.out.println("minNode2:x:"+tmp0.x+"  y:"+tmp0.y);
						}
						tmp0=minList.get(minList.size()-1);
						System.out.println("minNode2 cost:x:"+tmp0.cost);
						if(tmp0.cost>9000) break;
						minCost=minCost+tmp0.cost;
						System.out.println("minCOST:"+minCost);
						//minList=minCost(aList2,aList3);
						minList=minCost(minCon,aList3);
						for(int i0=0;i0<minList.size()-1;i0++) {
							tmp0=minList.get(i0);
							minCon.add(tmp0);
							System.out.println("minNode3:x:"+tmp0.x+"  y:"+tmp0.y);
						}
						tmp0=minList.get(minList.size()-1);
						System.out.println("minNode3 cost:x:"+tmp0.cost);
						if(tmp0.cost>9000) break;
						minCost=minCost+tmp0.cost;
						System.out.println("minCOST:"+minCost);
						//minList=minCost(aList3,aList4);
						minList=minCost(minCon,aList4);
						for(int i0=0;i0<minList.size()-1;i0++) {
							tmp0=minList.get(i0);
							minCon.add(tmp0);
							System.out.println("minNode4:x:"+tmp0.x+"  y:"+tmp0.y);
						}
						tmp0=minList.get(minList.size()-1);
						System.out.println("minNode4 cost:x:"+tmp0.cost);
						if(tmp0.cost>9000) break;
						minCost=minCost+tmp0.cost;
						System.out.println("minCOST:"+minCost);
						NodeXY tmpcost = new NodeXY(minCost);
						minCon.add(tmpcost);
						initialFlag=2;
						//validate();
						try { 
							Thread.sleep(1000); 
							} 
							catch(InterruptedException ex) {}
						
						repaint();
						setVisible(true);

						//initialFlag=10;
						try { 
							Thread.sleep(1000); 
							} 
							catch(InterruptedException ex) {}
						System.out.println("****OUTO OPTI");			
						try{
							output1.flush();
							output1.close();
							System.out.println("into close2");							
						}catch(Exception e1){
							e1.printStackTrace();
							System.out.println("error in close2");}

						
						break;

					//return 0;
				}
				catch(Exception e1){System.out.println("error1");}
				break;
			}//while

		}//for
		return 0;
	}		
				

	private void sleep() {
		// TODO Auto-generated method stub
		int j;
		for (int i=0;i<1;i++){j=i+10;}
		
	}



	public static void main(String args[]) throws IOException
	{
		/*java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				//new SplitSearch().setVisible(true);
				SplitSearch splitObj = new SplitSearch();				

				splitObj.SplitSearch();
			}
		});
		*/
			int i=0;
			SplitSearch splitObj = new SplitSearch();
			splitObj.SplitSearch();

			splitObj.TestCaseGolden();
	}
}
