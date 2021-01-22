<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>
<h1>Hello! Enter, please, driver's details:</h1>
<form method="post" action="${pageContext.request.contextPath}/registration">
    Please, enter driver's name: <input type="text" name="name">
    <br>
    Please, enter driver's licence number: <input type="text" name="licence_number">
    <br>
    Please, enter driver's login: <input type="text" name="login">
    <br>
    Please, enter driver's password: <input type="password" name="password">
    <br>
    <button type="submit">REGISTER</button>
</form>
</body>
</html>
