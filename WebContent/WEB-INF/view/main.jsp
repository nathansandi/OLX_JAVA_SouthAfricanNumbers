<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
	<head>
		
		<title>Student Registration Form</title>
		<style>
			.error{color:red}
		</style>
	</head>
	<h2>Olx Acessment:</h2>
	<body>
		  <form:form method="POST" action="savefile" enctype="multipart/form-data" modelAttribute="fileUploadModel">
	        File to Upload: <input type="file" name="file">
	      
	        <input type="submit" value="Upload">
	       
	        <form:errors path="file" style="color:red;"/>
		 </form:form>
	 
	</body>
</html>