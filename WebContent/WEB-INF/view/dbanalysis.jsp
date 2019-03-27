<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!-- Reference JSTL TAG FOR COLLECTION // looping over a collection -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
	<head>
		<title>DB Analysis</title>
		<style>
			.error{color:red}
		</style>
	</head>
	 
	<body>
		<h4>DB Analysis:</h4>  
		 
        
		<!--loop with jstl-->
        Statistic data from correct telephone numbers:
        <br>
		<c:if test="${empty(dbanalysis)}">
			  <ul>
			 		<li>Correct Numbers - 0</li> 
			  </ul>
		</c:if>
        <c:if test="${!empty(dbanalysis)}">
			<ul>
				<c:forEach var = "temp" items="${dbanalysis}">
					<li>${temp[0]} - ${temp[1]}</li>
				</c:forEach>
			</ul>
		</c:if>
	    <br>
		Statistic data from incorrect telephone numbers:
		<br>
		<c:if test="${empty(dbanalysisIn)}">
			  <ul>
			 		<li>Incorrect Numbers - 0</li> 
			  </ul>
		</c:if>
		<c:if test="${!empty(dbanalysisIn)}">
			<ul>
				<c:forEach var = "temp" items="${dbanalysisIn}">
					<li>${temp[0]} - ${temp[1]}</li>
				</c:forEach>
			</ul>
		</c:if>
		<h4>Results</h4>  
		<a href="downloadfile">Download Excel File</a>
	</body>
</html>