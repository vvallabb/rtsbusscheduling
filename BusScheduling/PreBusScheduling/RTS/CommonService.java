//Common Reusable routines of Complaint Management System
//CreatedBy : Mohan
import java.awt.Color;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;


public class CommonService {
	
	
	class retData{
		String rowFirstcol;
		Integer  rowSecondcol;
		public retData(String rF, int rS){rowFirstcol=rF;
			rowSecondcol=rS;
		}
	}
	

	Connection con;
	String url;
	String userName;
	String password;
	ResultSet results; // The ResultSet to interpret
	
	public Connection initiateCon() {
		Connection connection;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			url = "jdbc:mysql://localhost:3306/test";
			connection = DriverManager.getConnection(url, "root", "vkmohan123");
			return connection;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void closeCon(Connection connection) {
		try {
			connection.close();
		} // Try to close the connection
		catch (Exception e) {
			} // Do nothing on error. At least we tried.
		connection = null;
	}
	

	public ResultSet  getResultSet(Connection connection, String query1) {
		ResultSet res;
		//System.out.println("GETRESULT");
		try {
			// throws SQLException
			// If we've called close(), then we can't call this method
			// if (connection == null)
			// throw new IllegalStateException("Connection already closed.");
			Statement statement = connection.createStatement(
			ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			// Run the query, creating a ResultSet
			res = statement.executeQuery(query1);
			// Create and return a TableModel for the ResultSet
			//System.out.println("NO NULL rs"+res.getRow()+"Q:"+query1);
			//res.last();
			//System.out.println("last:"+res.getRow());
			return res;
		}
		catch (Exception e) {
			//System.out.println("NULL rs");
			return null;
		} // Do nothing on error. At least we tried.
	}
	
	
	public void sqlCon2(ResultSet rs2) {
		try {
			JFrame frame = new JFrame("List");
			JPanel panel = new JPanel();
			ResultSetMetaData metadata1 = rs2.getMetaData(); // Get metadata on them
			int numcols = metadata1.getColumnCount(); // How many columns?
			rs2.last();                         // Move to last row
			int numrows = rs2.getRow();             // How many rows?		
			rs2.first();//Move to first row	
				
			String[][] data = new String[numrows][numcols];//check the numRows?? how to get it dynamically??--todo
			int r = 0;
			int c = 0;
			int o = 0;
			String str;
			//System.out.println("SQLCON2C:"+numcols+"R:"+numrows);
			//while (|rs2.next()) {
			while(true) {
			//System.out.println("NSQLCON2C:"+numcols+"R:"+numrows);				
				for (c = 0;c< numcols ; c++) {
					o = c + 1;
					str = rs2.getString(o);
					data[r][c] = str;
					//System.out.println("SQL2: r:"+r+"C:"+c+"STR:"+str+"DATA:"+data[r][c]);
				}
				r++;
				if(!rs2.next()) break;
			}
			
			//Column Headings
			String col[] = new String[numcols];
			for(int x=1;x<=numcols;x++){
				//System.out.println("COLNAME: "+metadata1.getColumnLabel(x));
				col[x-1]=metadata1.getColumnLabel(x);
			}
			
			JTable table1 = new JTable(data,col) {
				public boolean isCellEditable(int rowIndex, int colIndex) {
					return false;
				}
			};
			//table1.setBounds(100,100,600,600);
			JTableHeader header = table1.getTableHeader();
			header.setBackground(Color.yellow);
			JScrollPane pane = new JScrollPane(table1,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
					JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS );
			//JScrollPane scroller = new JScrollPane( jtextArea,
            //        JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            //        JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS );


			table1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			//panel.setBounds(100,100,600,600);
			//pane.setBounds(100,100,600,600);
			//panel.add(pane);
			
			//frame.add(panel);
			frame.add(pane);
			frame.setBounds(100,100,600,600);
			//frame.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);
			
			//frame.setSize(500,160);
			//frame.setSize(500,500);
			
			//frame.setUndecorated(true);
			
			//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);		
		} catch (Exception e) {
			CommonService x2 = new CommonService();
			x2.errorLog(""+5, "","", "SQLCON:"+e.getMessage());			
			e.printStackTrace();
		}// end catch
	}
	
	public String[][] Serlist(String q1) {
		try {
			CommonService x1 = new CommonService();
			Connection c1 = x1.initiateCon();
			//System.out.println("Query"+q1);
			ResultSet rs1;
			rs1 = x1.getResultSet(c1, q1);
			ResultSetMetaData metadata1 = rs1.getMetaData(); // Get metadata on them
			int numcols = metadata1.getColumnCount(); // How many columns?
			rs1.last();                         // Move to last row
			int numrows = rs1.getRow();             // How many rows?		
			rs1.first();//Move to first row		
			String[][] data = new String[numrows][numcols];//check the numRows?? how to get it dynamically??--todo
			
			int r = 0;
			int c = 0;
			int o = 0;
			String str;
			//System.out.println("SQLCON2C:"+numcols+"R:"+numrows);
			//while (|rs2.next()) {
			
			while(true) {
			//System.out.println("NSQLCON2C:"+numcols+"R:"+numrows);				
				for (c = 0;c< numcols; c++) {
					o = c + 1;
					str = rs1.getString(o);
					data[r][c] = str;
					//System.out.println("SQL2: r:"+r+"C:"+c+"STR:"+str+"DATA:"+data[r][c]);
				}
				r++;
				if(!rs1.next()) break;
			}	
			x1.closeCon(c1);
			return data;
		} catch (Exception e) {
			CommonService x2 = new CommonService();
			x2.errorLog(""+5, "","", "SERLIST:"+e.getMessage());			
			e.printStackTrace();
		}// end catch
		return null;
	}
	
	public String[][] Rptlist(String q1) {
		try {
			CommonService x1 = new CommonService();
			Connection c1 = x1.initiateCon();
			//System.out.println("Query"+q1);
			ResultSet rs1;
			rs1 = x1.getResultSet(c1, q1);
			ResultSetMetaData metadata1 = rs1.getMetaData(); // Get metadata on them
			int numcols = metadata1.getColumnCount(); // How many columns?
			rs1.last();                         // Move to last row
			int numrows = rs1.getRow();             // How many rows?		
			rs1.first();//Move to first row		
			String[][] data = new String[numrows+1][numcols];//check the numRows?? how to get it dynamically??--todo
			
			int r = 0;
			int c = 0;
			int o = 0;
			String str;
			//System.out.println("SQLCON2C:"+numcols+"R:"+numrows);
			//while (|rs2.next()) {
			for(int x=1;x<=numcols;x++){
				//System.out.println("COLNAME: "+metadata1.getColumnLabel(x));
				data[r][x-1]=metadata1.getColumnLabel(x);
			}
			r++;
			
			while(true) {
			//System.out.println("NSQLCON2C:"+numcols+"R:"+numrows);				
				for (c = 0;c< numcols; c++) {
					o = c + 1;
					str = rs1.getString(o);
					data[r][c] = str;
					//System.out.println("SQL2: r:"+r+"C:"+c+"STR:"+str+"DATA:"+data[r][c]);
				}
				r++;
				if(!rs1.next()) break;
			}	
			x1.closeCon(c1);
			return data;
		} catch (Exception e) {
			CommonService x2 = new CommonService();
			x2.errorLog(""+5, "","", "RPTLST:"+e.getMessage());
			e.printStackTrace();
		}// end catch
		return null;
	}
	
	
	public int sequence(String type) {
		try {
			CommonService x1 = new CommonService();
			Connection c1 = x1.initiateCon();
			ResultSet rs1;
			String q1,sql;
			if(type.equals("Complaint")) {
				q1="Select complaintId from sequences";
				sql="UPDATE Sequences SET complaintId=complaintId+1";
			}
			else if (type.equals("Category")) {
				q1="Select categoryId from sequences";
				sql="UPDATE Sequences SET categoryId=categoryId+1";
			}
			else if (type.equals("User")) {
				q1="Select userId from sequences";
				sql="UPDATE Sequences SET userId=userId+1";
			}
			else if (type.equals("status")){
				q1="Select statusId from sequences";
				sql="UPDATE sequences SET statusId=statusId+1";
			}
			else if (type.equals("request")){
				q1="Select requestId from sequences";
				sql="UPDATE sequences SET requestId=requestId+1";
			}			
			else {
				q1="Select accessTypeId from sequences";
				sql="UPDATE sequences SET accessTypeId=accessTypeId+1";
			}
			rs1 = x1.getResultSet(c1, q1);			
			rs1.first();//Move to first row
			int seqId = rs1.getInt(1);
	    	
	        PreparedStatement prest = c1.prepareStatement(sql);			    		        
			int count = prest.executeUpdate();
			x1.closeCon(c1);			
			return seqId+1;			
		} catch (Exception e) {
			CommonService x2 = new CommonService();
			x2.errorLog(""+5, "","", "SEQUENCE:"+type+":"+e.getMessage());			
			e.printStackTrace();
		}// end catch		
		return -1;
	}
	
	public void backup(CommonService ser,String tableName) {
		String q1="select * from "+tableName;
		String data[][];
		data = ser.Rptlist(q1);		
		Calendar cal = new GregorianCalendar();
	    int month = cal.get(Calendar.MONTH);
	    int year = cal.get(Calendar.YEAR);
	    int day = cal.get(Calendar.DAY_OF_MONTH);
	    //System.out.println("Current date : " + day + "/" + (month + 1) + "/" + year);
	    String fN = tableName+"_"+day + "_" + (month + 1) + "_" + year+".txt";	
		ser.writeFile(data,fN);
	}
	
	public void writeFile(String[][] data,String fN) {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(fN));

			String writeString = null;

			//System.out.println("Rows:"+data.length+"Col:"+data[0].length);
			int i,j;
			for (i = 0; i < data.length; i++) {
				for (j=0;j<(data[0].length-1);j++ ) {
					//System.out.println("data["+i+"]["+j+"]"+data[i][j]);
					writeString = data[i][j] + "|" ;
					out.write(writeString);	
				}
				//System.out.println("NewLine data["+i+"]["+j+"]"+data[i][j]);
				writeString = data[i][j] + " " ;
				out.write(writeString);
				out.newLine();
			}
			out.close();
			//System.out.println("Done");
		} catch (IOException e) {
			CommonService x2 = new CommonService();
			x2.errorLog(""+5, "","", "WRITEFILE:"+e.getMessage());			
			//System.out.println(e);
		}
	}
	
	public void report(CommonService ser,String rptName,String q1) {
		String data[][];
		data = ser.Rptlist(q1);		
		Calendar cal = new GregorianCalendar();
	    int month = cal.get(Calendar.MONTH);
	    int year = cal.get(Calendar.YEAR);
	    int day = cal.get(Calendar.DAY_OF_MONTH);
	    String fN = rptName+"_"+day + "_" + (month + 1) + "_" + year+".txt";	
		ser.writeFile(data,fN);
	}
	
	public void emptyReport(String rptName) {
		String data[][]={{"Empty Report"}};
		Calendar cal = new GregorianCalendar();
	    int month = cal.get(Calendar.MONTH);
	    int year = cal.get(Calendar.YEAR);
	    int day = cal.get(Calendar.DAY_OF_MONTH);
	    String fN = rptName+"_"+day + "_" + (month + 1) + "_" + year+".txt";	
		writeFile(data,fN);
	}
	
	public int rebuildNodesTable(String fle) {
		ArrayList nodeList = new ArrayList();
		CommonService x1 = new CommonService();
		Connection c1 = x1.initiateCon();
		int seqNo=1;
		String sql;		
		
        PreparedStatement prest;		
		FileInputStream fileStream;
		DataInputStream in;
		int i = 0,count;
		try {
			fileStream = new FileInputStream(fle);
			in = new DataInputStream(fileStream);
			sql="truncate table test.Node";
			prest= c1.prepareStatement(sql);
			count = prest.executeUpdate();							
			while (in.available() != 0) {
				String inputLine = in.readLine();
				String[] field = inputLine.split(",");
				sql="Insert into test.Node(NodeId,CoordinateXPoint,CoordinateYPoint," +
				"EntityTypeId,LocationName,CreationDate,CreatedBy) Values ("+
				seqNo+","+Integer.parseInt(field[1])+","+
				Integer.parseInt(field[2])+","+Integer.parseInt(field[3])+",\""+
				field[4]+"\",curdate(),1)";
				System.out.println("SQL:"+sql);
				seqNo++;
				prest= c1.prepareStatement(sql);
				count = prest.executeUpdate();				
			}
			x1.closeCon(c1);						
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
			return 1;			
		}// end catch				
		return 0;
	}

		
	
	public boolean fieldLength(String str,Integer strlen) {
		if(str.length()>strlen) return false;
		else return true;
	}

	public void errorLog(String entity,String entityId,String createdBy,String mes) {
		try {
			CommonService x1 = new CommonService();
			Connection c1 = x1.initiateCon();
			//System.out.println("ERRORLOG!!!"+entity+"  "+entityId+"   "+createdBy+"  "+mes);
			String q1="insert into errorLog values(now(),?,?,?,?)";
	        PreparedStatement prest = c1.prepareStatement(q1);			    		        
			prest.setString(1, entity);
			prest.setString(2, entityId);
			prest.setString(3, createdBy);
			prest.setString(4,mes);
			int count = prest.executeUpdate();
			x1.closeCon(c1);			
		} catch (Exception e) {
			CommonService x2 = new CommonService();
			x2.errorLog(""+5, "","", "ERRORLOG:"+e.getMessage());			
			e.printStackTrace();
		}// end catch		
	}	
}

