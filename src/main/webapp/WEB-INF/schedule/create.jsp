<?xml version="1.0" encoding="ISO-8859-1" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:c="http://java.sun.com/jsp/jstl/core" version="2.0">
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
			<script type="text/javascript">
				var repeatScale = '1';
				var repeatLimit = '10';
		        function scopeChange(element)
		        {
		        	if(element === 'NONE')
		        	{
		           	 	document.getElementById("repeatScale").style.display = 'none';
		           	 	repeatScale = document.getElementById("repeatScaleField").value;
		           	 	document.getElementById("repeatScaleField").value = '0';
		        	}
		        	else if(document.getElementById("repeatScale").style.display === 'none')
		        	{
		        		document.getElementById("repeatScale").style.display = 'block';
		           	 	document.getElementById("repeatScaleField").value = repeatScale;
		        	}
		        }
		        function repeatLimitedChange(element)
		        {
		        	if(!element)
		        	{
		           	 	document.getElementById("repeatLimit").style.display = 'none';
		           	 	repeatLimit = document.getElementById("repeatLimitField").value;
		           	 	document.getElementById("repeatLimitField").value = '0';
		        	}
		        	else if(document.getElementById("repeatLimit").style.display === 'none')
		        	{
		        		document.getElementById("repeatLimit").style.display = 'block';
		           	 	document.getElementById("repeatLimitField").value = repeatLimit;
		        	}
		        }
		        window.onload = function()
		        {
		        	scopeChange(document.getElementById("scopeField").value);
		        	repeatLimitedChange(document.getElementById("repeatLimitedField").checked);
		        }
        	</script>
		</head>
		<body>
			<h3>Create Schedule</h3>
			<form action="schedule/create" method="post">
				<input type="hidden" name="id" value="${not empty id ? id : 0}" />
    			Name <i>(optional)</i>: <input type="text" name="name" value="${param.name}" />
    			<br />
				Music: 
				<select name="selectedMusic">
    				<c:forEach var="music" items="${musics}">
    					<c:choose>
    						<c:when test="${music.id == param.selectedMusic}">
    							<option selected="selected" value="${music.id}">${music.name}</option>
    						</c:when>
    						<c:otherwise>
    							<option value="${music.id}">${music.name}</option>
    						</c:otherwise>
    					</c:choose>
    				</c:forEach>
    			</select>
    			<br />
    			Repeat: 
    			<select id="scopeField" name="selectedScope" onchange="scopeChange(this.value);">
    				<c:forEach var="scope" items="${scopes}">
    					<c:choose>
    						<c:when test="${scope == param.selectedScope}">
    							<option selected="selected" value="${scope}">${scope.label}</option>
    						</c:when>
    						<c:otherwise>
    							<option value="${scope}">${scope.label}</option>
    						</c:otherwise>
    					</c:choose>
    				</c:forEach>
    			</select>
   				<br />
    			<div id="repeatScale">
    				Repeat Scale: <input id="repeatScaleField" type="number" name="repeatScale" value="${not empty param.repeatScale ? param.repeatScale : '1'}" />
    				<br />
    				Repeat Limited?
    				<c:choose>
   						<c:when test="${not empty param.repeatLimited}">
   							<input id="repeatLimitedField" type="checkbox" name="repeatLimited" value="true" onchange="repeatLimitedChange(this.checked);" checked="checked" />
   						</c:when>
   						<c:otherwise>
   							<input id="repeatLimitedField" type="checkbox" name="repeatLimited" value="true" onchange="repeatLimitedChange(this.checked);" />
   						</c:otherwise>
    				</c:choose>
    				<div id="repeatLimit">
    					Repeat Limit: <input id="repeatLimitField" type="number" name="repeatLimit" value="${not empty param.repeatLimit ? param.repeatLimit : '10'}" />
    				</div>
    			</div>
    			
    			Start Date: <input type="date" name="creationDate" value="${param.creationDate}" /> <i>Posts as YYYY-MM-DD</i>
   				<br />
    			Start Time: <input type="time" name="creationTime" value="${param.creationTime}" /> <i>Posts as 24 HR. HH:MM</i>
    			<br />
    			Enabled? <input type="checkbox" name="enabled" value="true" checked="checked" />
    			<br />
    			<input type="submit" value="Submit" />
			</form>
			<br />
			<a href="">Return Home</a>
		</body>
	</html>
</jsp:root>