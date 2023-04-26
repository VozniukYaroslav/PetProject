<%--
  Created by IntelliJ IDEA.
  User: Acer
  Date: 24.04.2023
  Time: 13:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Edit Order</title>
</head>
<body>
<%@include file="headers.jsp" %>
<br>
<br>
Edit Order:<br>
<form action="${pageContext.request.contextPath}/edit-order" method="post">
    ${requestScope.order.id},<label>
    <input type="text" name="customerName" value="${requestScope.order.customerName}">
</label>,${requestScope.order.dateOfOrder}, total sum: ${requestScope.order.sumOfOrder}.<br>
    Books:<br>
    <label for="bookId">
        <c:forEach var="book" items="${requestScope.books}">
            <input type="checkbox" name="bookId" id="bookId" value="${book.id}">
            ${book.name},${book.author},${book.cost}
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
    <button type="submit" name="orderId" value="${requestScope.order.id}">Edit</button>
</form>
</body>
</html>
