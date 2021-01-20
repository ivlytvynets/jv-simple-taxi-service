<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add driver to car</title>
</head>
<body>
<h1>Add driver to car</h1>
<form method="post" action="${pageContext.request.contextPath}/cars/drivers/add">
    Enter, please, driver's id:<input type="number" name="driver_id">
    Enter, please, car's id:<input type="number" name="car_id">
    <button type="submit">ADD</button>
</form>
</body>
</html>
