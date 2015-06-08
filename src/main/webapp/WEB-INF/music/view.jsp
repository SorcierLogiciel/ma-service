<?xml version="1.0" encoding="ISO-8859-1" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:c="http://java.sun.com/jsp/jstl/core" xmlns:fmt="http://java.sun.com/jsp/jstl/fmt" xmlns:fn="http://java.sun.com/jsp/jstl/functions" version="2.0">
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
			<h3>${music.name}</h3>
			<a href="schedule/create?selectedMusic=${music.id}">Create a Schedule for this Music</a>
			<br />
			<c:forEach var="schedule" items="${schedules}">
				<p style="${not schedule.enabled or (schedule.repeatLimited and schedule.repeatLimit le 0) ? 'color: grey;' : ''}">${schedule.name}<br />
				Next event at <fmt:formatDate value="${schedule.nextEventTime}" pattern="MM/dd/yyyy hh:mm:ss a" /><br />
				<c:if test="${schedule.scheduleScope != 'NONE'}" >
					Repeating every ${schedule.repeatScale gt 1 ? schedule.repeatScale += ' ' += fn:toLowerCase(schedule.scheduleScope.labelPlural) : fn:toLowerCase(schedule.scheduleScope.label)} 
					<c:if test="${schedule.repeatLimited}" >
						${schedule.repeatLimit} more ${schedule.repeatLimit == 1 ? ' time' : ' times'}
					</c:if>
					<br />
				</c:if></p>
				<form action="schedule/enable" method="post">
					<c:if test="${not schedule.enabled}" >
						<input type="hidden" name="enable" value="true" />
					</c:if>
					<input type="hidden" name="scheduleId" value="${schedule.id}" />
    				<input type="submit" value="${schedule.enabled ? 'Disable' : 'Enable' } this Schedule" />
				</form>
				<form action="schedule/delete" method="post">
					<input type="hidden" name="scheduleId" value="${schedule.id}" />
    				<input type="submit" value="Delete this Schedule" />
				</form>
			</c:forEach>
			<br />
			<form action="music/delete" method="post">
				<input type="hidden" name="musicId" value="${music.id}" />
    			<input type="submit" value="Delete this Music" />
			</form>
			<br />
			<a href="">Return Home</a>
		</body>
	</html>
</jsp:root>