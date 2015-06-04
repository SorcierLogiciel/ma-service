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
			<base href="${pageContext.request.contextPath}/" />
			<title>Music Alarm Service</title>
		</head>
		<body>
			<h3>Music Uploaded</h3>
			<p>${name} uploaded successfully!</p>
			<a href="schedule/create?selectedMusic=${musicId}">Create a Schedule for this Music</a>
			<br />
			<a href="music/upload">Upload more Music</a>
			<br />
			<a href="">Return Home</a>
		</body>
	</html>
</jsp:root>