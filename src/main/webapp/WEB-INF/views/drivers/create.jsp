<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add a driver</title>
</head>
<body>
<h1>Hello! Enter, please, driver's details:</h1>
<form method="post" action="${pageContext.request.contextPath}/drivers/add">
    Please, enter driver's name: <input type="text" name="name">
    Please, enter driver's licence number: <input type="text" name="licenceNumber">
    <button type="submit">CREATE</button>
</form>
</body>
</html>
