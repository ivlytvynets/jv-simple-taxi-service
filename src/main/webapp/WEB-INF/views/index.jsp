<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Taxi Service</title>
</head>
<body>
<h1>Welcome to Taxi Service!</h1>
<a href="${pageContext.request.contextPath}/drivers/add">Inject driver to DB</a>
<a href="${pageContext.request.contextPath}/drivers/all">Get all drivers from DB</a>
<a href="${pageContext.request.contextPath}/manufacturers/add">Inject manufacturer to DB</a>
<a href="${pageContext.request.contextPath}/cars/add">Inject car to DB</a>
<a href="${pageContext.request.contextPath}/cars/drivers/add">Add driver to car</a>
</body>
</html>
