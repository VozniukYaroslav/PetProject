<%--
  Created by IntelliJ IDEA.
  User: Acer
  Date: 21.04.2023
  Time: 12:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Detail of Order</title>
</head>
<body>
<%@include file="headers.jsp"%>
<br>
<br>
<c:forEach var="stock" items="${requestScope.stock}">
    <form action="${pageContext.request.contextPath}/detail-order">
        Stock Id: ${stock.id} , Book Id: ${stock.bookId} , Book Count: ${stock.count}
    </form>
</c:forEach>
<c:forEach var="book" items="${requestScope.books}">
    Books: Id:${book.id}| Name:${book.name}| Author:${book.author}| Year:${book.year}| Genre:${book.genre}<br>
</c:forEach>
</body>
</html>
