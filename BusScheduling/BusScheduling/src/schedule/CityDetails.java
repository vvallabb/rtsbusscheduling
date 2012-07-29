package schedule;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class CityDetails {
	
	public ArrayList getPlaces()
	{
		ArrayList cities = new ArrayList();
		CommonService cs = new CommonService();
		Connection c = cs.initiateCon();
		String sCities="select name from rts.bangalore_places";
		try {
			PreparedStatement ps = c.prepareStatement(sCities);
			ResultSet rs1 = ps.executeQuery();
			while(rs1.next())
			{
				cities.add(rs1.getString("name"));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cs.closeCon(c);
		return cities;
	}
	
	public double getDistanceBetweenPlaces(String source,String destination)
	{
		CommonService cs = new CommonService();
		Connection c = cs.initiateCon();
		String sGetDistance = "select distance from places_distance where source='"+source+
		"' and destination='"+destination+"'";
		double distance=999999999;
		
		try {
			PreparedStatement ps = c.prepareStatement(sGetDistance);
			ResultSet rs1 = ps.executeQuery();
			while(rs1.next())
			{
				 distance = rs1.getDouble("distance");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cs.closeCon(c);
		
		return distance;
	}
	
	public double getLat(String name)
	{
		CommonService cs = new CommonService();
		Connection c = cs.initiateCon();
		String sGetLat = "SELECT lat FROM bangalore_places where name='"+name+"'";
		double lat = 0;
		
		try {
			PreparedStatement ps = c.prepareStatement(sGetLat);
			ResultSet rs1 = ps.executeQuery();
			while(rs1.next())
			{
				 lat = rs1.getDouble("lat");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cs.closeCon(c);
		
		return lat;
	}
	
	public double getLon(String name)
	{
		CommonService cs = new CommonService();
		Connection c = cs.initiateCon();
		String sGetLat = "SELECT lon FROM bangalore_places where name='"+name+"'";
		double lon = 0;
		
		try {
			PreparedStatement ps = c.prepareStatement(sGetLat);
			ResultSet rs1 = ps.executeQuery();
			while(rs1.next())
			{
				 lon = rs1.getDouble("lon");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cs.closeCon(c);
		
		return lon;
	}

}
