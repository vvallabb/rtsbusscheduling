//*********************Shortest Path Implementation Using Floyd Warshall********************//
//*********************************Mujahid****Manish****Dilshad*****************************//

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.StringTokenizer;
//import java.util.Calendar;

import javax.swing.JFrame;
//import java.awt.*;

class NodeXY {
	int x;
	int y;
	int cost;
	int type;
	public NodeXY(int x1,int y1) {
		x=x1;
		y=y1;
	}

	public NodeXY(int x1,int y1,int t1) {
		x=x1;
		y=y1;
		type=t1;
	}

	public NodeXY(){}

	public NodeXY(int ct){cost=ct;}
}

class Shortest extends JFrame //implements MouseMotionListener
{
	private static final long serialVersionUID = 1L;
	String failure [][]= {{""}};

	int xCoordinate[]=new int[80];
	int yCoordinate[]=new int[80];
	int intermediateNodes[]=new int[80];
	
	public int nodeDistance[][]=new int[80][80];//Matrix containing distance between two nodes
	int predecessorNode[][]=new int[80][80];// Matrix containing predecessor nodes

	int flag=1;
	int tem,i;
	
	String[] places = new String[80];
	public int count=0;
	int src,dest;
	//String BNHGYJ`TU

//The constructor  which the main matrix containing the shortest path
//between two nodes is calculated...

	public Shortest()
	   {
		String s = null;
		StringTokenizer st2 = null;
		int d=1,i=0;
		if(true)
		//System.out.println()
		;
		else
		//System.out.println("asdf")
		;
		
// Making tokens of the data given in Graph1.txt such as the first token is
// the x coordinate next is Y coordinate and the last token is Name of the place

		try{
			FileReader dis = new FileReader("GRAPH1.TXT");
			BufferedReader in = new BufferedReader(dis);

			s=in.readLine();
			while(s!=null)
			{

				st2=new StringTokenizer(s,",");
				while(st2.hasMoreTokens()){
					
					if(d==1)
					{   
						xCoordinate[i]=Integer.parseInt(st2.nextToken());
						d++;
						continue;
					}
					if(d==2)
					{
						yCoordinate[i]=Integer.parseInt(st2.nextToken());
						d++;
						continue;
					}
					if(d==3)
					{
						places[i]=(st2.nextToken());
						d=1;
						i++;
						continue;
					}
				}
				s=in.readLine();

			}
		}

		catch(Exception e){
		e.printStackTrace();// Exception Messages
		}
		
		
		
		String s1 = null;
		StringTokenizer st,st1;
		String[] temp = new String[160];
		int n1=0,i1=0,j1=0,z=1;

		for (int k = 0; k < 80; k++) // k is a loop variable
			for(int l=0;l<80;l++)    // l is a loop variable
			{
				if(k==l)
					nodeDistance[k][l]=0; // Dilstance of node with itself is 0...
				else
					nodeDistance[k][l]=999; // Taking as maximum Distance..subject to change in real application... 

			}

// Making a predecessor matrix which will contain the predecessor node in 
// order to to reach from a given source to destination...
// initializing all elements of predecessor matrix with the value of source
// assuming that for a given pair the shortest path is direct path not involving
// any intermediate node...This value might change after applying floyd warshall...

		for (int k = 0; k < 80; k++) 
			for(int l=0;l<80;l++)
				predecessorNode[k][l]=999;

// Making tokens of the data given in edge1.txt such that first element acts as source(i)
// next token as destination(j) and the last token as the direct distance between them...
// say a[i][j] = 10 =>  distance between i and j is 10... 

		try{
			FileReader dis = new FileReader("edge1.txt");
			BufferedReader in = new BufferedReader(dis);

			s1=in.readLine();
			while(s1!=null)
			{

				st=new StringTokenizer(s1,";");
				while(st.hasMoreTokens()){
					temp[n1]=st.nextToken();
					
					n1++;
				}
				s1=in.readLine();

			}
		}

		catch(Exception e){
		e.printStackTrace();// Exception Messages
		}

		for (int k = 0; k < n1; k++) 
		{
			st1=new StringTokenizer(temp[k]," ");
			while(st1.hasMoreTokens())
			{
				if(z==1)
				{
					i1=Integer.parseInt(st1.nextToken());
					z++; 
					continue;
				}
				if(z==2)
				{   
					j1=Integer.parseInt(st1.nextToken());
					z++;
					continue;
				}
				if(z==3)
				{
					nodeDistance[i1][j1]=Integer.parseInt(st1.nextToken());
					nodeDistance[j1][i1]=nodeDistance[i1][j1];
					z=1;
					continue;
				}
			}
		}
		
//changing the predecessor matrix because if for a given pair of i and j matrix the distance matrix has some value 
// >0 and <999 then we have direct distance between the nodes involving no intermediate node hence predecessor
// matrix contains source as the intermediate node...
		for (int k = 0; k < 80; k++) 
			for (int l = 0; l < 80; l++) 
			{
				if(nodeDistance[k][l]>0 && nodeDistance[k][l]<999)
					predecessorNode[k][l]=k;
			}

// Floyd Warshall implementation...Finding the shortest path between the two nodes through every other node
// if we have a intermediate node through which we have shortest path then we have that node as predecessor node...


		for (int k = 0; k < 80; k++) 
			for (int l = 0; l < 80; l++) 
				for (int m = 0; m < 80; m++)
				{
					if(nodeDistance[l][m]>nodeDistance[l][k]+nodeDistance[k][m])
					{
						nodeDistance[l][m]=nodeDistance[l][k]+nodeDistance[k][m];
						predecessorNode[l][m]=predecessorNode[k][m];
					}
				}

	

	}
	
// The function that would be called by other modules...
	
	String[][] path(String dest1,String src1)
	{
		
		for(int i=0;i<places.length;i++)
			if(src1.equals(places[i]))
			{
				src=i;
				break;
			}
		for(int i=0;i<places.length;i++)
			if(dest1.equals(places[i]))
			{
				dest=i;
				break;
			}
		if(src==dest)
			return failure;
		
		//System.out.println("src= "+src);
		//System.out.println("dest= "+dest);
		

		int length=0;
		flag=1;

// Finding the intermediate nodes fro a given Pair of source and destination...
		
			intermediateNodes[length]=dest;
			length++;
			while(true)
			{
				intermediateNodes[length]=predecessorNode[src][dest];
				if(predecessorNode[src][dest]==src)
					break;

				
				dest=predecessorNode[src][dest];
				length++;
			}
			
		 //catch (IOException ioe) {  }
		System.out.println("Path:");
		
// Finding the path coorinates for a given set of source , destination and intermediate nodes...
		
        String [][] pathCoordinates=new String[length+1][2];
        
		for(int t=length;t>=0;t--)
		{
			tem=intermediateNodes[t];
			
			flag=0;
		
			System.out.println(xCoordinate[tem]+" "+yCoordinate[tem]);
			pathCoordinates[t][0]=Integer.toString(xCoordinate[tem]);
			pathCoordinates[t][1]=Integer.toString(yCoordinate[tem]);
			
			tem=length;

		}
		return pathCoordinates;
	}

//       Service being called by
		 ArrayList path(int srcX,int srcY,int destX,int destY,int interX,int interY)
	{    
			 ArrayList aList=new ArrayList();
		int xyz=0,cost=0,calcDestX,calcDestY;
		while(xyz<2){
		
		if(xyz==0)
			{
			 calcDestX=interX;
		     calcDestY=interY;
			 //aList.add(srcX);
			 //aList.add(srcY);
			}
		else
			{srcX=interX;
			 srcY=interY;
				calcDestX=destX;
				calcDestY=destY;
				//aList.add(interX);
				//aList.add(interY);
			}
				
		for(int i=0;i<xCoordinate.length;i++)
			if((srcX==xCoordinate[i]) && (srcY==yCoordinate[i]))
			{
				src=i;
				break;
			}
		for(int i=0;i<xCoordinate.length;i++)
			if((calcDestX==xCoordinate[i]) && (calcDestY==yCoordinate[i]))
			{
				dest=i;
				break;
			}
		if(src==dest)
			{aList.add("");
			return aList;}
		cost+= nodeDistance[src][dest];
		//System.out.println("Distance : "+ nodeDistance[src][dest]);
		
		//System.out.println("src= "+src);
		//System.out.println("dest= "+dest);
		

		int length=0;
		flag=1;

// Finding the intermediate nodes fro a given Pair of source and destination...
		
			intermediateNodes[length]=dest;
			length++;
			while(true)
			{
				intermediateNodes[length]=predecessorNode[src][dest];
				if(predecessorNode[src][dest]==src)
					break;

				
				dest=predecessorNode[src][dest];
				length++;
			}
			
		 //catch (IOException ioe) {  }
		//System.out.println("Path:");
		
// Finding the path coorinates for a given set of source , destination and intermediate nodes...
		
       int [][]Coordinates=new int[length+1][2];
        
		for(int t=length;t>=0;t--)
		{
			tem=intermediateNodes[t];
			
			flag=0;
		
			//System.out.println(xCoordinate[tem]+" "+yCoordinate[tem]);
			Coordinates[t][0]=xCoordinate[tem];
			//aList.add(xCoordinate[tem]);
			//aList.add(yCoordinate[tem]);
			Coordinates[t][1]=yCoordinate[tem];
			//aList.add(places[tem]);
			
			tem=length;

		}
		for(int t=length;t>0;t--){
			aList.add(Coordinates[t][0]);
			aList.add(Coordinates[t][1]);
			
			}
		xyz++;
		
	
	
		}
		aList.add(destX);
		aList.add(destY);
     aList.add(cost);
	 return aList;
	}

	//Added New by Mohan 9/12/08
	//Signature: input points in ArrayList<NodeXY> and returned value is again in 
	//ArraList<NodeXY>
	public ArrayList<NodeXY> path(ArrayList<NodeXY> aList1) {    
		int srcX,srcY,listCnt,cost=0;
		ArrayList<NodeXY> aList=new ArrayList<NodeXY>();
		ArrayList<NodeXY> aListcon=new ArrayList<NodeXY>();

		//System.out.println("Array Length:"+aList1.size());

		if(aList1.size()<2) {NodeXY tmp0 = new NodeXY(0);
							//aList.add(0);
							aList.add(tmp0);
							return aList;}//No points

		if(aList1.size()==2) {
			NodeXY srcN=new NodeXY();
			srcN=aList1.get(0); 
			NodeXY destN=new NodeXY();
			destN=aList1.get(1); 
			aList=generateNodes(srcN,destN);
			return aList;
		}
		else{
			listCnt=0;
			while(aList1.size()-1>listCnt) {
				NodeXY srcN=new NodeXY();
				srcN=aList1.get(listCnt); 
				NodeXY destN=new NodeXY();
				destN=aList1.get(listCnt+1); 
				//System.out.println("INTO>>>"+listCnt+"SRC:"+srcN.x+" "+srcN.y+"DEST:"+destN.x+" "+destN.y);
				aList=generateNodes(srcN,destN);
				for(int i=0;i<aList.size()-1;i++) {//Co-ordinates
					NodeXY tmp1 = new NodeXY();
					tmp1=aList.get(i);
					aListcon.add(tmp1);
				}
				if (aList.size()>1){ //Cost
					NodeXY tmp2 = new NodeXY();
					int j=aList.size()-1;
					tmp2=aList.get(j);
					System.out.println("int cost:"+tmp2.cost);
					cost=cost+(int) tmp2.cost;
				}
				listCnt++;
				//System.out.println("COST:"+cost+"final:"+aList.get(aList.size()-1));
			}
		}
		//aListcon.add(cost); //last field would be the cummulative cost
		NodeXY tmp3 = new NodeXY(cost);
		aListcon.add(tmp3);
		return aListcon;
	}

	//Added New by Mohan 9/12/08
	//Signature: inputs SrcNode and DestNode - both these are of datatype Node Class
	//returned value is ArrayList<NodeXY>
	public ArrayList<NodeXY> generateNodes(NodeXY srcN, NodeXY destN) {
		ArrayList<NodeXY> aList=new ArrayList<NodeXY>();
		int srcX=srcN.x;
		int srcY=srcN.y;
		int destX=destN.x;
		int destY=destN.y;
		int xyz=0,cost=0,calcDestX,calcDestY;
		int listCnt=0;
		xyz=0;
		for(int i=0;i<xCoordinate.length;i++) if((srcX==xCoordinate[i]) && (srcY==yCoordinate[i])){	src=i;break;}
		for(int i=0;i<xCoordinate.length;i++)if((destX==xCoordinate[i]) && (destY==yCoordinate[i])){dest=i;	break;}
		if(src==dest){	NodeXY tmp0 = new NodeXY(0);
						aList.add(tmp0);
						//aList.add(0);
						return aList;	}
		cost+= nodeDistance[src][dest];
		int length=0;
		flag=1;
		// Finding the intermediate nodes fro a given Pair of source and destination...
		intermediateNodes[length]=dest;
		length++;
		while(true)	{
			intermediateNodes[length]=predecessorNode[src][dest];
			if(predecessorNode[src][dest]==src)	break;
			dest=predecessorNode[src][dest];length++;
		}
		// Finding the path coorinates for a given set of source , destination and intermediate nodes...
		int [][]Coordinates=new int[length+1][2];
			for(int t=length;t>=0;t--){
				tem=intermediateNodes[t];
				flag=0;
				Coordinates[t][0]=xCoordinate[tem];
				Coordinates[t][1]=yCoordinate[tem];
				tem=length;
			}
		for(int t=length;t>0;t--){
			NodeXY tmp1=new NodeXY(Coordinates[t][0],Coordinates[t][1]);
			//aList.add(Coordinates[t][0]);
			//aList.add(Coordinates[t][1]);
			aList.add(tmp1);
		}
		xyz++;
		NodeXY tmp2=new NodeXY(destX,destY);
		aList.add(tmp2);
		NodeXY tmp3=new NodeXY(cost);
		aList.add(tmp3);
		return aList;
	}

	public static void main(String args[])
	 {
		ArrayList<NodeXY> aList1 = new ArrayList<NodeXY>();
	// Calendar d= Calendar.getInstance();
	 //Calendar d1=Calendar.getInstance();
	 //long time,time1;
	 //time=d.getTimeInMillis();
	 //System.out.println(time);
	 //for(int  j=0;j<10;j++){
		 new Shortest();
	 Shortest sh=new Shortest();
	/* String distance[][]=sh.path("KrishnaPark","RajajiNagar");
	 for(int i=distance.length-1;i>=1;i--){
		 System.out.println(distance[i][0]+"  "+distance[i][1]);
		  
		 
	 }*/
	 System.out.println(sh.path(457,383,275,387,345,395));//}
	 NodeXY s1 = new NodeXY(345,395);
	 aList1.add(s1);
	 NodeXY s2 = new NodeXY(326,440);
	 aList1.add(s2);
	 NodeXY s20 = new NodeXY(295,439);
	 aList1.add(s20);


	 NodeXY s21 = new NodeXY(275,387);
	 aList1.add(s21);

	 //System.out.println(sh.path(457,383,275,387,aList1));//}
	 //Node s2 = new Node(326,440);
	 //aList1.add(s2);
	 //System.out.println(sh.path(457,383,275,387,aList1));//}
	 //Node s3 = new Node(295,439);
	 //aList1.add(s3);
	 //System.out.println(sh.path(457,383,275,387,aList1));//}


	//NodeXY s4 = new NodeXY(457,383);
	//NodeXY s5 = new NodeXY(275,387);
	NodeXY s4 = new NodeXY(312,226);
	NodeXY s5 = new NodeXY(308,204);


  	//System.out.println(sh.generateNodes(s4,s5));
	ArrayList<NodeXY> tmpList0;
	tmpList0=sh.generateNodes(s4,s5);
	for(int i=0;i<tmpList0.size()-1;i++) {
		NodeXY tmp0 = new NodeXY();
		tmp0=tmpList0.get(i);
		System.out.println("GenNode:x:"+tmp0.x+"  y:"+tmp0.y);
	}
	NodeXY tmp1 = new NodeXY();
	tmp1=tmpList0.get(tmpList0.size()-1);
	System.out.println("Cost:"+tmp1.cost);
	
	System.out.println("PRINT");
	ArrayList<NodeXY> tmpList1;
	tmpList1=sh.path(aList1);
	for(int i=0;i<tmpList1.size()-1;i++) {
		NodeXY tmp2 = new NodeXY();
		tmp2=tmpList1.get(i);
		System.out.println("GenNode:x:"+tmp2.x+"  y:"+tmp2.y);
	}
	NodeXY tmp3 = new NodeXY();
	tmp3=tmpList1.get(tmpList1.size()-1);
	System.out.println("Cost:"+tmp3.cost);

	//System.out.println(sh.path(aList1));


	 //time1=d1.getTimeInMillis();
	// System.out.println(time1+"   "+time);
	 //System.out.println("Time elapsed   "+(time1-time));
	 /*System.out.println("Distance "+distance);
	 System.out.println("Length "+sh.length);*/
	 }
}