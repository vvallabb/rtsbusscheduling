/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package schedule;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
/**
 *
 * @author vinaycp
 */
public class CommonService {

    Connection con;
	String url;
	String userName;
	String password;
	ResultSet results; // The ResultSet to interpret

	public Connection initiateCon() {
		Connection connection;
		try {
			//System.out.println(getPropertyValue("common","Url"));
			//Class.forName("com.mysql.jdbc.Driver");
			Class.forName(getPropertyValue("common","Drivername"));
			//url = "jdbc:mysql://localhost:3306/test";
			url = getPropertyValue("common","Url");
		//	connection = DriverManager.getConnection(url, "root", "vkmohan123");
			//connection = DriverManager.getConnection(url, "root", "root321");
			connection = DriverManager.getConnection(url, getPropertyValue("common","Username"), getPropertyValue("common","Password"));
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

        public String getPropertyValue(String module_name,String name)
	{
        	//System.out.println("inside get property value");
		try {
			  File file = new File("D://config.xml");
			  DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			  DocumentBuilder db = dbf.newDocumentBuilder();
			  Document doc = db.parse(file);
			  doc.getDocumentElement().normalize();
			 // System.out.println("Root element " + doc.getDocumentElement().getNodeName());
			  NodeList nodeLst = doc.getElementsByTagName(module_name);
			// System.out.println("Information about "+module_name);

			    Element fstElmnt;
			      NodeList urlElmntLst;
			      Element urlElmnt;
			      NodeList url;
			      String str;

			    Node fstNode = nodeLst.item(0);

			    if (fstNode.getNodeType() == Node.ELEMENT_NODE) {

			            fstElmnt = (Element) fstNode;
			       urlElmntLst = fstElmnt.getElementsByTagName(name);
			       urlElmnt = (Element) urlElmntLst.item(0);
			       url = urlElmnt.getChildNodes();
			     // System.out.println("Url: "  + ((Node) url.item(0)).getNodeValue());
			      str = ((Node) url.item(0)).getNodeValue();
			      return (String)str;
			    }


			  } catch (Exception e) {
			    e.printStackTrace();

			  }

			  return null;

	}
   
   public int authenticateUser(String username, String password,Connection c)
   {
		
		String sLogin="select count(*) from user where userName='"+username+"' and Password='"+password+"'";
		//System.out.println(sLogin);
		try {
			PreparedStatement ps = c.prepareStatement(sLogin);
			ResultSet rs1 = ps.executeQuery();
			if(rs1.next())
			{
				if(rs1.getInt(1)>0)
				return 1;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 0;

   }

}
