<?xml version="1.0" encoding="ISO-8859-1" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:c="http://java.sun.com/jsp/jstl/core" version="2.0">
<jsp:directive.page import="org.wchc.mas.enums.ScheduleScope"/>
	<jsp:directive.page contentType="text/html; charset=ISO-8859-1" 
		pageEncoding="ISO-8859-1" session="false"/>
	<jsp:output doctype-root-element="html"
		doctype-public="-//W3C//DTD XHTML 1.0 Transitional//EN"
		doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"
		omit-xml-declaration="true" />
	<html xmlns="http://www.w3.org/1999/xhtml">
		<head>
			<title>Music Alarm Service</title>
		</head>
		<body>
			<h3>Schedule</h3>
			<br />
			<form action="schedule" method="post" enctype="multipart/form-data">
				Alarm: 
				<select name="alarm">
    				<c:forEach var="alarm" items="${alarms}">
    					<option value="${alarm.id}">${alarm.name}</option>
    				</c:forEach>
    			</select>
    			<br />
    			Schedule Scope: 
    			<select name="scheduleScope">
    				<c:forEach var="scope" items="${scopes}">
    					<option value="${scope}">${scope}</option>
    				</c:forEach>
    			</select>
    			<br />
    			Start: <input type="datetime" name="creationTime" />
   				<br />
    			Repeat: <input type="number" name="repeatScale" value="0" />
    			<br />
    			<input type="submit" />
			</form>
		</body>
	</html>
</jsp:root>