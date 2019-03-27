<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
	<head>
		<title>Validate Single Number</title>
		<style>
			.error{color:red}
		</style>
	</head>
	<h3>Test a single number</h3>
	<body>
		<form:form action="validateform" modelAttribute = "telephoneModel">

			Telephone: <form:input  path="telephone" />
			<form:errors path="telephone" cssClass="error" />
			<br><br>
			<input type = "submit"  value ="Submit"/>
		</form:form>
	</body>
</html>