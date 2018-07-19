<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">
	<link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">

</head>
<body>
	<jsp:include page="header.jsp" />
	<div>
	<c:set var="mail" value="${mail}"/>
	<h3 style="margin-left:20px">From</span>
	<span style="margin-left:20px">${mail.fromAddress}</span><br>
	<h3 style="margin-left:20px">Subject</span>
	<span style="margin-left:20px">${mail.subject}</span><br>
	<h3 style="margin-left:20px;">body</h3>
	<span style="margin-left:20px;font-size:20px">${mail.body}</span><br>
	</div>
</body>
</html>