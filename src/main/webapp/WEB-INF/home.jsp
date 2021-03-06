<?xml version="1.0" encoding="ISO-8859-1" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:c="http://java.sun.com/jsp/jstl/core" xmlns:fmt="http://java.sun.com/jsp/jstl/fmt" xmlns:fn="http://java.sun.com/jsp/jstl/functions" version="2.0">
	<jsp:directive.page contentType="text/html; charset=ISO-8859-1" 
		pageEncoding="ISO-8859-1" session="true"/>
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
			<h2>Music Alarm Service</h2>
			<a href="music/upload">Upload Music</a>
			<br />
			<c:if test="${not empty musics}">
				<a href="schedule/create">Create a Schedule</a>
				<br />
			</c:if>
			<a href="control">System Control</a>
			<br />
			<c:if test="${not empty schedules}">
				<h3>Upcoming Alarms</h3>
				<form action="home" method="post">
					Show up to <input type="number" name="scheduleCount" style="width: 35px;" value="${not empty sessionScope.scheduleCount ? sessionScope.scheduleCount : '5'}" /> schedules
    				<input type="submit" value="Update" />
				</form>
			</c:if>
			<c:forEach var="schedule" items="${schedules}" end="${not empty sessionScope.scheduleCount ? sessionScope.scheduleCount - 1 : '4'}">
				<p style="${not schedule.enabled or (schedule.repeatLimited and schedule.repeatLimit le 0) ? 'color: grey;' : ''}">${schedule.name}<br />
				Playing ${schedule.music.name}<br />
				Next event at <fmt:formatDate value="${schedule.nextEventTime}" pattern="MM/dd/yyyy hh:mm:ss a" />
				<c:if test="${schedule.scheduleScope != 'NONE'}" >
					<br />
					Repeating every ${schedule.repeatScale gt 1 ? schedule.repeatScale += ' ' += fn:toLowerCase(schedule.scheduleScope.labelPlural) : fn:toLowerCase(schedule.scheduleScope.label)} 
					<c:if test="${schedule.repeatLimited}" >
						${schedule.repeatLimit} more ${schedule.repeatLimit == 1 ? ' time' : ' times'}
					</c:if>
				</c:if></p>
			</c:forEach>
			<c:if test="${not empty musics}">
				<h3>Music</h3>
			</c:if>
			<c:forEach var="music" items="${musics}" >
				<a href="music?musicId=${music.id}">${music.name}</a>
				<br />
			</c:forEach>
		</body>
	</html>
</jsp:root>