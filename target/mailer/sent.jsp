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

	<h1>VIEW</h1>
	<c:forEach var="mail" items="${mails}">
		
			<div style="margin-top:15px; border:1px solid white;">
				<div style=" display:inline-block">
					<span style="color:blue;font-size: 20px;">To</span><span> ${mail.toa} </span>
					<span style="color:blue;font-size:20px;margin-left:20px;">Subject </span> <span> ${mail.sbjt} </span>
				</div>
					<div style="margin-left:15px; display:inline-block">
					<form action="/view" method="get" >
						<input name="name" value="${pageContext.request.userPrincipal.name}" type="hidden" />
						<input name="id" value="${mail.id}" type="hidden" />
						<input type="submit" value="view" style="display:inline-block;"/>
					</form>
					</div>
					 
					   
					<div style="margin-left:15px;display:inline-block">
					<form action="/delete" method="get"  >
							<input name="name" value="${pageContext.request.userPrincipal.name}" type="hidden" />
							<input name="id" value="${mail.id}" type="hidden" />
							<input name="page" value="sent" type="hidden" />
							<input name="move" value="move" type="hidden" />
							<input type="submit" value="delete" />
					</form>
					</div>
			</div>
	</c:forEach>	


<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</html>