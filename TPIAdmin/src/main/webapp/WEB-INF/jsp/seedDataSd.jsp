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

<title>Initialize Service Dashboard Data</title>
</head>
<body>
	<div>
	<f:form name="SeedDataForm" modelAttribute="seedDataSd" method="post" action="${contextPath}/loadSeedDataSd.do" id="seedDataForm" enctype="multipart/form-data">
		<label for="entityName">Select table to update</label>
		<f:select path="entityName" id="entityName" name="entityName"
				placeholder="Table to Load">
							<option value="">--Service Dashboard--</option>
							<option value="BUSINESS_UNIT">BUSINESS UNIT</option>
							<option value="SdServiceCategory">SD Service Category</option>
							<option value="SdBusinessService">SD Business Service</option>						

							
							
		</f:select>
		<!--<input type="file" accept=".csv, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel" />-->
		<input type="file" accept=".csv,.xlsx" name="file" id="file"/>
		<f:button>Submit</f:button>
	</f:form>
	</div>
	<div>
	</br>
		Total Lines: </br>
		<f:label path="linesProcessed"><c:out value="${linesProcessed}" /></f:label>
		</br>
		Lines rejected: </br>
		<f:label path="linesFailed"><c:out value="${linesFailed}" /></f:label>
		
	</div>
	<!-- <h2>Row Number: Reason for skip </h2> -->
	<div>
	</br>
		Error messages: </br>
		<c:forEach items="${seedDataInsertStatMsgList}" var="seedDataInsertStatMsg">
    		<c:out value="${seedDataInsertStatMsg}" /><c:out value=": " /><c:out value="${seedDataInsertStatMsg}" /><br />
		</c:forEach>
	</div>
</body>
</html>