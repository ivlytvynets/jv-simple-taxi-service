<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Manufacturer</title>
</head>
<body>
<h1>Hello! Enter, please, manufacturer's details:</h1>

<form method="post" action="${pageContext.request.contextPath}/manufacturers/add">
    Please, enter manufacturer's name: <input type="text" name="name">
    Please, enter manufacturer's country: <input type="text" name="country">

    <button type="submit">CREATE</button>
</form>
</body>
</html>
