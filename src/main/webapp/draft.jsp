<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
	<link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
</head>
<style>
td {
  
  padding: 10px;
  
}
table {
  border-spacing: 20px;
 
}
</style>
<body>

<jsp:include page="header.jsp" />
	<c:if test = "${empty mails}">
	<h2>YOU HAVE NO SAVED DRAFTS</h2>
	</c:if>
	
	<c:if test  = "${not empty mails}">
	<h1>YOUR DRAFTS</h1>
	<table>
	<c:forEach var="mail" items="${mails}">
		<div style="margin-top: 15px;">
			<tr>
			<td><span style="color:blue;font-size: 20px;margin-right:20px" >From</span></td><td><span style="margin-right:20px"> ${mail.frma} </span></td>
			<td><span style="color:blue;font-size: 20px;margin-right:20px">Subject</span></td> <td> <span style="margin-right:20px">${mail.sbjt}</span></td>
			<%-- <span><a href="edit/${pageContext.request.userPrincipal.name}/${mail.id}">edit draft</a></span>
			<span><a href="delete/${pageContext.request.userPrincipal.name}/${mail.id}">delete draft</a></span>
			<br><br> --%>
			<td>
			<div style="margin-left:30px" >
				<form action="${contextPath}/edit" method="POST">
				<input name="id" value="${mail.id}" type="hidden" />
				<input type="submit" value="EDIT" />
				</form>
			</div>
			</td>
			
		   <td>
		   <div style="margin-left:30px;color:red">
		   <form action="${contextPath}/delete" method="get">
				<input name="name" value="${pageContext.request.userPrincipal.name}" type="hidden" />
				<input name="id" value="${mail.id}" type="hidden" />
				<input name="page" value="draft" type="hidden" />
				<input name="move" value="perm" type="hidden" />
				<input type="submit" value="delete" />
			</form>
			</div>
			</td>
			</tr>
		</div>
	</c:forEach>	
	</table>
	</c:if>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</html>