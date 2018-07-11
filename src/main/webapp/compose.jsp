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
<p id="username" hidden>${pageContext.request.userPrincipal.name}</p>
<form:form commandName="compose">
	<form:input id="ID" path="id" type="hidden" />
    <form:label path="to">to Address</form:label><br>
    <form:input id="to" path="to" /><br>
    <form:label path="subject">subject</form:label><br>
    <form:input id="subject" path="subject" /><br>
    <form:label path="body">body</form:label><br>
    <form:textarea id="body" path="body"></form:textarea>
    
</form:form>
<button onclick="sendmail()"> submit </button>
<span id="message" >  </span>

<script>
function sendmail()
{
	
	var id = document.getElementById("ID").value;
	var to = document.getElementById("to").value;
	var subject = document.getElementById("subject").value;
	var body = document.getElementById("body").value;
	var curl= "compose/"+document.getElementById("username").innerHTML;
		draft = 
		  {
					
			  "id":id,
		      "to" : to,
		      "subject" : subject,
		      "body" : body
		   };
			 $.ajax({
			      type: "POST",
			      contentType : 'application/json; charset=utf-8',
			      dataType : 'text',
			      url: curl,
			      data: JSON.stringify(draft), // Note it is important
			      
			      success: function(response)
			      {
			    	     console.log(response);
			    	     document.getElementById("message").innerHTML = response;
			    	     document.getElementById("ID").value= "";
			    	 	 document.getElementById("to").value= "";
			    	 	 document.getElementById("subject").value= "";
			    	 	 document.getElementById("body").value= "";
			       }
			      
			   });
		
	
}
	window.addEventListener('unload', function(event) 
	{
		var id = document.getElementById("ID").value;
		console.log("ID = "+id);
		var to = document.getElementById("to").value;
		var subject = document.getElementById("subject").value;
		var body = document.getElementById("body").value;
		console.log(to+subject+body);
		var draft = null;
		if(to!="" || subject!="" || body!="")
		{
			console.log("In if");
			draft = {
				  "id":id,
			      "to" : to,
			      "subject" : subject,
			      "body" : body
			   };
				 $.ajax({
				      type: "POST",
				      contentType : 'application/json; charset=utf-8',
				      dataType : 'text',
				      url: "savedraft",
				      data: JSON.stringify(draft), // Note it is important
				      async: false,
				      success :function(result) 
				      {
				       console.log(result);
				       }
				   });
		}
			  
		console.log("after func");
	});
</script>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</html>