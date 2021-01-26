<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Taxi Service</title>
</head>
<body>
<h1>Welcome to Taxi Service!</h1>
<a href="${pageContext.request.contextPath}/drivers/add">Inject driver to DB</a><br>
<a href="${pageContext.request.contextPath}/drivers/">Get all drivers from DB</a><br>
<a href="${pageContext.request.contextPath}/manufacturers/add">Inject manufacturer to DB</a><br>
<a href="${pageContext.request.contextPath}/cars/add">Inject car to DB</a><br>
<a href="${pageContext.request.contextPath}/cars/drivers/add">Add driver to car</a><br>
<a href="${pageContext.request.contextPath}/cars/all">Get all my cars</a>
</body>
</html>
