<%--
  Created by IntelliJ IDEA.
  User: Acer
  Date: 17.04.2023
  Time: 13:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error</title>
</head>
<body>
<c:if test="${not empty requestScope.error}">
    <div style="color: red">
        <c:forEach var="error" items="${requestScope.error}">
            <span>${error.message}</span>
        </c:forEach>
    </div>
</c:if>
</body>
</html>
