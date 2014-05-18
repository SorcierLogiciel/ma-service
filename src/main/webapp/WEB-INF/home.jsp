<?xml version="1.0" encoding="ISO-8859-1" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:c="http://java.sun.com/jsp/jstl/core" version="2.0">
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
			<h3>Music Alarm Service</h3>
			<br />
			<a href="schedule">Create a Schedule</a>
			<br />
			<a href="upload">Upload a File</a>
			<br />
			<c:forEach var="schedule" items="${schedules}">
				<a href="schedule?id=${schedule.id}">${schedule.id}</a>
			</c:forEach>
		</body>
	</html>
</jsp:root>