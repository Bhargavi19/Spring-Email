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

    <title>Logged In</title>

    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">

    <c:if test="${pageContext.request.userPrincipal.name != null}">
        <form id="logoutForm" method="POST" action="${contextPath}/logout">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>

        <h5 style="align:right">Welcome ${pageContext.request.userPrincipal.name} | <a onclick="document.forms['logoutForm'].submit()">Logout</a></h5>
	
    </c:if>
    
    
    
	 
	<div class="navbar navbar-default">
		<c:set var="user" value="${pageContext.request.userPrincipal.name}"/>
		<ul class="nav navbar-nav">
      		<li><a class="/active" href="${contextPath}/inbox/${user}"> inbox</a></li>
      		<li><a href="${contextPath}/sent/${user}"> sent</a></li>
      		<li><a href="${contextPath}/draft/${user}"> draft</a></li>
      		<li><a href="${contextPath}/trash/${user}"> trash</a></li>
      		<li><a href="${contextPath}/compose"> compose</a></li>
    	</ul>
	</nav>

</div>
<!-- /container -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</html>
