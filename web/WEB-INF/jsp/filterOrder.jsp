
<%--
  Created by IntelliJ IDEA.
  User: Acer
  Date: 25.04.2023
  Time: 15:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Filter</title>
</head>
<body>
<%@include file="headers.jsp" %>
<br>
<br>
<form action="${pageContext.request.contextPath}/filter-order" method="post">
    <label for="fromDate">
        From date:
        <input type="date" name="fromDate" id="fromDate">
    </label>
    <label for="toDate">
        To date:
        <input type="date" name="toDate" id="toDate">
    </label>
    <button type="submit">Filter</button>
    <a href="${pageContext.request.contextPath}/order-list">
        <button type="button">Show all</button>
    </a>
</form>
${requestScope.noOrders}
<c:forEach var="order" items="${requestScope.ordersByDate}">
    <form action="${pageContext.request.contextPath}/order-list" method="post">
        <b>${order.id}</b>, ${order.customerName}, ${order.dateOfOrder}, total sum: ${order.sumOfOrder}.
            <button type="submit" name="deleteOrder" value="${order.id}">Delete order</button>
        <a href="${pageContext.request.contextPath}/edit-order?orderId=${order.id}">
            <button type="button">Edit Order</button>
        </a><br>
        <a href="${pageContext.request.contextPath}/detail-order?orderId=${order.id}">
            <button type="button">Details</button>
        </a><br>
    </form>
</c:forEach>
</body>
</html>