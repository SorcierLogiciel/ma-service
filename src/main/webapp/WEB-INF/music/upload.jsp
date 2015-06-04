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
			<h3>Upload Music</h3>
			<form action="music/upload" method="post" enctype="multipart/form-data">
				Name <i>(optional)</i>: <input type="text" name="name" value="${param.name}" />
				<br />
    			MP3 File: <input type="file" name="file" />
    			<br />
    			<input type="submit" value="Upload" />
			</form>
			<br />
			<a href="">Return Home</a>
		</body>
	</html>
</jsp:root>