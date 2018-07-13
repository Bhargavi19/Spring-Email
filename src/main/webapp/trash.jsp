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
<body>

<jsp:include page="header.jsp" />
<c:if test = "${empty mails}">
	<h2>YOUR TRASH BOX IS EMPTY</h2>
	</c:if>
	
	<c:if test  = "${not empty mails}">
	<h3>Your trash box</h3>
	<table>
	<c:forEach var="mail" items="${mails}">
			<tr>
			<td><span style="margin-left:20px;color:blue;font-size:20px;">From</span> <span style="margin-left:20px;"> ${mail.frma} </span></td>
			<td><span style="margin-left:20px;color:blue;font-size:20px;">Subject</span> <span style="margin-left:20px;"> ${mail.sbjt} </span></td>
			<%-- <span><a href="/restore/${pageContext.request.userPrincipal.name}/${mail.id}">restore mail</a></span>
			<span></span><a href="/delete/${pageContext.request.userPrincipal.name}/${mail.id}/perm">delete mail permanently</a><br><br></span> --%>
			<td><div style="margin-left:25px;"><form action="${contextPath}/restore" method="get">
				<input name="name" value="${pageContext.request.userPrincipal.name}" type="hidden" />
				<input name="id" value="${mail.id}" type="hidden" />
				<input type="submit" value="restore" />
			</form></div></td>
			
		   <%-- <span></span><a href="/delete/${pageContext.request.userPrincipal.name}/${mail.id}/move">delete mail</a><br><br></span> --%>
		  <td><div style="margin-left:25px;color:red;"> <form action="${contextPath}/delete" method="get">
				<input name="name" value="${pageContext.request.userPrincipal.name}" type="hidden" />
				<input name="id" value="${mail.id}" type="hidden" />
				<input name="page" value="trash" type="hidden" />
				<input name="move" value="perm" type="hidden" />
				<input type="submit" value="delete" />
			</form></div></td>
			</tr>
	</c:forEach>	
	</table>
	</c:if>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</html>