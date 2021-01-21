<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add car</title>
</head>
<body>
<h1>Hello! Enter, please, car's details:</h1>
<h4 style="color:red">${message}</h4>
<form method="post" action="${pageContext.request.contextPath}/cars/add">
    Please, enter car's model: <input type="text" name="model">
    Please, enter manufacturer's id for car: <input type="number" name="manufacturer_id">

    <button type="submit">CREATE</button>
</form>
</body>
</html>
