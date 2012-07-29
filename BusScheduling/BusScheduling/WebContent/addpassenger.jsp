
<%@page import="java.util.ArrayList" %>
<%@page import="schedule.CityDetails" %>
<%@page import="java.util.Iterator" %>
<%@page import="schedule.Passenger" %>

<html>
<head>
 <script type="text/javascript"
 src="http://api.maps.yahoo.com/ajaxymap?v=3.8&appid=fuqo_mzV34Hp1odgCHodXgHtJnsAe60.FKnx9rM4sKWeFhBqwOU9cYLAyd.7SN0lMbN.">

 </script>
 <style type="text/css">
 #map{
 height: 800px;
 width: 800px;
 }
 </style>
 

 </head>
 <body>
 <% String validUser = (String)session.getAttribute("valid");
   if(validUser==null)
   {
   %>
   <jsp:forward page="login.jsp"></jsp:forward>
   <%} %>   
<table border="1" width="1320px">
	         <tr>
                 <td> <div id="map"></div></td>
                 
                 
<script type="text/javascript">
     // Create a Map that will be placed in the "map" div.
     var map = new YMap(document.getElementById('map'));

     function startMap(){
         // Add the ability to change between Sat, Hybrid, and Regular Maps
         map.addTypeControl();
         // Add the zoom control. Long specifies a Slider versus a "+" and "-" zoom control
         map.addZoomLong();
         // Add the Pan control to have North, South, East and West directional control
         map.addPanControl();
         // Specifying the Map starting location and zoom level
         map.drawZoomAndCenter("Bangalore", 6);
         // Add an event to report to our Logger
         //YEvent.Capture(map, EventsList.MouseClick, reportPosition);
      


      /*   function reportPosition(_e, _c){
             // It is optional to specify the location of the Logger.
             // Do so by sending a YCoordPoint to the initPos function.
             var mapCoordCenter = map.convertLatLonXY(map.getCenterLatLon());

             YLog.initPos(mapCoordCenter); //call initPos to set the starting location

             // Printing to the Logger
             //YLog.print("You Made a MouseClick!");
             //YLog.print("Latitude:" + _c.Lat);
             //YLog.print("Longitude:" + _c.Lon);

			currentGeoPoint = new YGeoPoint( _c.Lat, _c.Lon);
			placeMarker(currentGeoPoint);

         }
			function placeMarker(geoPoint){
				// Printing to the Logger
				YLog.print("Adding marker at....");
				YLog.print("Latitude:" + geoPoint.Lat + " Longitude:" + geoPoint.Lon);
				//var newMarker= new YMarker(geoPoint);
				var newMarker= new YMarker(geoPoint, createCustomMarkerImage());
				newMarker.addAutoExpand("Hovering over label");
				var markerMarkup = "<b>Hello</b>";
				    markerMarkup += "<i> easy</i>";
				YEvent.Capture(newMarker, EventsList.MouseClick,
					function(){
						newMarker.openSmartWindow(markerMarkup);
					});
				map.addOverlay(newMarker);

			}

			function createCustomMarkerImage(){
			 	var myImage = new YImage();
			  	myImage.src = 'http://l.yimg.com/a/i/us/map/gr/mt_ic_c.gif';
			 	myImage.size = new YSize(20,20);
			  	myImage.offsetSmartWindow = new YCoordPoint(0,0);
				return myImage;
			}*/
     }
			 function send()
		     {
		            alert('send');
		          //document.forms[0].action="scheduler.jsp?arr=vinay"
		          var url = arr[0];
		          for(var i=1;i<arr.length;i++)
		                 url=url+","+arr[i];
		             //alert(url);
		           
		          document.forms[0].submit1.value = url;
		            document.forms[0].submit();
		      }

		      function removepassenger(count)
		      {
			      document.removepassengerform.passenger.value = count;
			      document.removepassengerform.submit();
		      }

			
 window.onload = startMap;
 </script>

 <td valign="top" align="left">
				 <div align="right">
                 <form action="logout.do" method="post">
                 <input type="submit" value="Logout" name="logoutbutton" />
                 </form>
                 </div>
<center><b>Previous passenger details</b></center>
<p></p>
	<table border="1" align="center">
	<form name="removepassengerform" action="removepassenger.do" method="post">
	<tr>
	<th>Name</th>
	<th>Source</th>
	<th>Destination</th>
	<th>Start Time</th>
	<th>End Time</th>
	<th>Max Response Time</th>
	</tr>
<% 
ArrayList pArr = (ArrayList)request.getAttribute("passengerarr");

session.setAttribute("passengerarr",pArr); 

Iterator it1 = pArr.iterator();

int count=0;

while(it1.hasNext())
{
	Passenger p = (Passenger)it1.next();

%>
	<tr>
	<td><%=p.getName() %></td>
	<td><%=p.getSource() %></td>
	<td><%=p.getDestination() %></td>
	<td><%=p.getStarttime() %></td>
	<td><%=p.getEndtime() %></td>
	<td><%=p.getMaxresponsetime() %></td>
	<td><input type="submit" name="removepassengerbutton" value="Remove" onclick="removepassenger(<%=count %>)" ></input></td>
	</tr>

 <%
 count++;
}
     CityDetails cd = new CityDetails();
     ArrayList cities = cd.getPlaces();
     %>
     <input type="hidden" name="passenger"/>
 </form>               
 </table> 
 <p></p>
<form name="scheduleform" action="schedule.do" method="post">
                   <center><input type="submit" value="Pool 'em" name="submit1"/></center>
                   </form>
   <hr>
  <center> <b>Present passenger details</b>  </center>
   <p></p>        
                  <form name="addpassengerform" action="addpassenger.do" method="post">
                 &nbsp; Please enter the area to pick up:
                     <p>

                     &nbsp; Name:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                     <input type="text" name="pname"></input>
                     <p>
                     &nbsp; Source:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                     <select name="src">
                     <% Iterator it = cities.iterator(); 
                     while(it.hasNext())
                    	 {%>
                     <option><%= it.next() %></option>
                     <%} %>
                 </select>
                  <p></p>
                 &nbsp; Destination:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                 <select name="dest">
                     <%  it = cities.iterator(); 
                     while(it.hasNext())
                    	 {%>
                     <option><%= it.next() %></option>
                     <%} %>
                 </select>
                 
                 <p>    
                     &nbsp;Earliest Start Time:&nbsp;&nbsp;&nbsp;&nbsp;
                     <input type="text" name="starttime"></input>&nbsp;(e.g. 12:10 PM)
                       <p>                 
                     &nbsp;Latest End Time:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                     <input type="text" name="endtime"></input>&nbsp;(e.g. 12:10 PM)
                      <p>                 
                     &nbsp;Max Response Time:&nbsp;
                     <input type="text" name="maxresponsetime"></input> (e.g. 12:10 PM)
                 <br></br>
                  &nbsp;<input type="submit" value="add passenger" name="addpassengerbutton" />
                  </form>
                  
                 </td>
                 
                 
             </tr>
          
 			
     </table>

 </body>
 </html>  