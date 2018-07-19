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
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Create an account</title>

    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/common.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>

<body>

<div class="container">
	<div class="alert alert-info">${successMessage}</div>
	
    <form:form method="POST" modelAttribute="userForm" class="form-signin" onsubmit="return validateDetails()">
        <h2 class="form-signin-heading">Create your mail account</h2>
        <spring:bind path="username">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input id="username" type="text" path="username" class="form-control" placeholder="Username@domain.com"
                            autofocus="true" ></form:input>
                <form:errors path="username"></form:errors>
                <span id="error" style="color:red;font-size:22px;"></span>
            </div>
        </spring:bind>

        <spring:bind path="password">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="password" path="password" class="form-control" placeholder="Password"></form:input>
                <form:errors path="password" ></form:errors>
            </div>
        </spring:bind>

        <spring:bind path="passwordConfirm">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="password" path="passwordConfirm" class="form-control"
                            placeholder="Confirm your password"></form:input>
                <form:errors path="passwordConfirm" ></form:errors>
            </div>
        </spring:bind>

        <button class="btn btn-lg btn-primary btn-block" type="submit">Register</button>
    </form:form>
	<h4 style="margin-top: 40px" class="text-center"><a href="${contextPath}/login">Go back to Login</a></h4>
</div>
<!-- /container -->
<script type="text/javascript">
function validateDetails()
{
	console.log("validate function is called");
	var username = document.getElementById('username').value;
	console.log(username);
	 var reg = /^([A-Za-z0-9_\-\.]+)\@([A-Za-z0-9_\-\.]+)\.([A-Za-z]{2,4})$/;
	 
      
      
     if (reg.test(username) == true) 
     {
    	 
    	 var position = username.indexOf('.');
      	 console.log(position);
         var str = username.slice(position+1);
         console.log(str);
         console.log(str.length);
         if((str.length) > 3)
         { 
        	 document.getElementById("error").innerHTML = "invalid user email";
         	return false;    
         }
     }
     else
     {
    	 
         
    	 document.getElementById("error").innerHTML = "invalid user email";
       	 return false;
     }
     return true;

	
}
</script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</html>
