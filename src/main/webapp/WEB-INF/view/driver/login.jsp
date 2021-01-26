<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<h4 style="color: red">${message}</h4>
<form method="post" action="${pageContext.request.contextPath}/login">
    Input your login:<input type="text" name="login">
    <br>
    Input your password:<input type="password" name="password">
    <br>
    <button type="submit">Login</button>
</form>
Not registered? <a href="${pageContext.request.contextPath}/registration">Register</a>
</body>
</html>
