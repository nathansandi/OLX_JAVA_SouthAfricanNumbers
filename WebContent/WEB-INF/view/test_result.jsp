<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
	<head>
		<title>Validate Single Number</title>
		<style>
			.error{color:red}
		</style>
	</head>
	<h3>Test Results</h3>
	<body>
		<br>
		${telephoneModel.status} 
		<br><br>
		
		<form:form action="test">
			<input type = "submit"  value ="Return to the test page"/>
		</form:form>
	</body>
</html>