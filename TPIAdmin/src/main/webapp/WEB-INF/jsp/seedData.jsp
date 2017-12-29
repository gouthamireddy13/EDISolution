<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Initialize TPI Data</title>
</head>
<body>
	<div>
	<f:form name="SeedDataForm" modelAttribute="seedData" method="post" action="${contextPath}/loadSeedData.do" id="seedDataForm" enctype="multipart/form-data">
		<label for="entityName">Select table to update</label>
		<f:select path="entityName" id="entityName" name="entityName"
				placeholder="Table to Load">
							<option value=""></option>
							<option value="ACK">ACK</option>
							<option value="DELIMITER">DELIMITER</option>
							<option value="DIRECTION">DIRECTION</option>
							<option value="DOCUMENT">DOCUMENT</option>
							<option value="MAP">MAP</option>
							<option value="PARTNER_TYPE">PARTNER TYPE</option>
							<option value="PROTOCOL">PROTOCOL</option>
							<option value="TPP_TYPE">TPP TYPE</option>
							<option value="VERSION">VERSION</option>
							<option value="">-------</option>
							<option value="PARTNER_GROUP">PARTNER GROUP</option>
							<option value="SERVICE_CATEGORY">SERVICE CATEGORY</option>
							<option value="SERVICE_TYPE">SERVICE TYPE</option>
							<option value="SERVICE_TYPE_MAP">SERVICE TYPE MAP</option>
		</f:select>
		<!--<input type="file" accept=".csv, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel" />-->
		<input type="file" accept=".csv,.xlsx" name="file" id="file"/>
		<f:button>Submit</f:button>
	</f:form>
	</div>
	<div>
		<f:label path="message"><c:out value="${message}" /></f:label>
	</div>
	<!-- <h2>Row Number: Reason for skip </h2> -->
	<div>
		<c:forEach items="${seedDataInsertStatMsgList}" var="seedDataInsertStatMsg">
    		<c:out value="${seedDataInsertStatMsg.rowNum}" /><c:out value=": " /><c:out value="${seedDataInsertStatMsg.statusMsg}" /><br />
		</c:forEach>
	</div>
</body>
</html>