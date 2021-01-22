<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>My cars</title>
</head>
<body>
<h1>My cars page</h1>
<table border="1">
    <tr>
        <th>ID</th>
        <th>Model</th>
        <th>Manufacturer</th>
    </tr>
    <c:forEach var="car" items="${cars}">
        <tr>
            <td>
                <c:out value="${car.id}"/>
            </td>
            <td>
                <c:out value="${car.model}"/>
            </td>
            <td>
                <c:out value="${car.manufacturer.name}"/>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
