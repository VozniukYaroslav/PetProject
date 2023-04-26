<%--
  Created by IntelliJ IDEA.
  User: Acer
  Date: 10.04.2023
  Time: 14:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Create Order</title>
</head>
<body>
<%@include file="headers.jsp"%>
<br>
<br>
<form action="${pageContext.request.contextPath}/create-order" method="post">
    <label for="customerName">
        <input type="text" name="customerName" id="customerName">
    </label><br>
    <label for="bookId">
        <c:forEach var="book" items="${requestScope.books}">
            <input type="checkbox" name="bookId" id="bookId" value="${book.id}">
            ${book.name},${book.author},${book.year},${book.genre}, ${book.cost}
            <select name="${book.id}" id="count">
                <option></option>
                <c:forEach var="count" begin="1" end="10">
                    count:
                    <option name="${book.id}" value="${count}">${count}</option>
                </c:forEach>
            </select>
            <br>
        </c:forEach>
    </label>
    <button type="submit">Confirm</button>
    <%@include file="errors.jsp"%>
</form>
</body>
</html>
