<%@page import="java.util.ArrayList" %>
<%@page import="java.util.Iterator" %>
<%@page import="schedule.Bus" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Select Bus</title>
<script type="text/javascript">
function busselect(count)
{
    document.busselectform.busselection.value = count;
    document.busselectform.submit();
}
</script>
</head>
<body>
<form name="busselectform" action="buspathview.do" method="post">
<%
ArrayList buses = (ArrayList)request.getAttribute("buses");
session.setAttribute("buses",buses);

Iterator busesIterator = buses.iterator();

while(busesIterator.hasNext())
{
	Bus bus = (Bus)busesIterator.next();
	String str = "Bus "+bus.getBusId()+" path";
%>
<input type="submit" name="busselectbutton" value="<%=str %>" onclick="busselect(<%=bus.getBusId() %>)" ></input>
<%} %>
<input type="hidden" name="busselection"/>
</form>
</body>
</html>