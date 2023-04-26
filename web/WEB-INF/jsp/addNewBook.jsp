<%--
  Created by IntelliJ IDEA.
  User: Acer
  Date: 13.04.2023
  Time: 01:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>AddBook</title>
</head>
<body>
<%@include file="headers.jsp"%>
<br>
<br>
<form action="${pageContext.request.contextPath}/add-new-book" method="post">
    <label for="name">Book Name:
        <input type="text" name="name" id="name">
    </label><br>
    <label for="author">Author Name:
        <input type="text" name="author" id="author">
    </label><br>
    <label for="year">The year of publishing
        <input type="date" name="year" id="year">
    </label><br>
    <select name="genre" id="genre">Genre:
        <c:forEach var="genre" items="${requestScope.genre}">
            <option value="${genre}">${genre}</option>
        </c:forEach>
    </select><br>
    <label for="cost">Cost
        <input type="text" name="cost" id="cost">
    </label><br>
    <select name="count" id="count">Count:
        <c:forEach var="count" begin="1" end="10">
            <option name="${count}" value="${count}">${count}</option>
        </c:forEach>
    </select><br>
    <button type="submit">Confirm</button><br>
    <div style="color: red">
        <c:if test="${not empty requestScope.error}">
            <c:forEach var="error" items="${requestScope.error}">
                <span>${error.massage}</span>
            </c:forEach>
        </c:if>
    </div>
    <%@include file="errors.jsp"%>
</form>
</body>
</html>
