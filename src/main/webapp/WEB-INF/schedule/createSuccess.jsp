<?xml version="1.0" encoding="ISO-8859-1" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:c="http://java.sun.com/jsp/jstl/core"  xmlns:fmt="http://java.sun.com/jsp/jstl/fmt" xmlns:fn="http://java.sun.com/jsp/jstl/functions" version="2.0">
	<jsp:directive.page import="org.westcoasthonorcamp.ma.common.enums.ScheduleScope"/>
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
			<h3>Schedule Created</h3>
			<p style="${not schedule.enabled or (schedule.repeatLimited and schedule.repeatLimit le 0) ? 'color: grey;' : ''}">${schedule.name} scheduled for <fmt:formatDate value="${schedule.nextEventTime}" pattern="MM/dd/yyyy hh:mm:ss a" />
			<c:if test="${schedule.scheduleScope != 'NONE'}" >
					repeating every ${schedule.repeatScale gt 1 ? schedule.repeatScale += ' ' += fn:toLowerCase(schedule.scheduleScope.labelPlural) : fn:toLowerCase(schedule.scheduleScope.label)} 
					<c:if test="${schedule.repeatLimited}" >
						${schedule.repeatLimit} more ${schedule.repeatLimit == 1 ? ' time' : ' times'}
					</c:if>
			</c:if></p>
			<a href="schedule/create?selectedMusic=${param.selectedMusic}">Create another Schedule for this Music</a>
			<br />
			<a href="music/upload">Upload more Music</a>
			<br />
			<a href="">Return Home</a>
		</body>
	</html>
</jsp:root>