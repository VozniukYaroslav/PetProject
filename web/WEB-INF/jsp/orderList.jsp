<%--
  Created by IntelliJ IDEA.
  User: Acer
  Date: 19.04.2023
  Time: 13:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Order List</title>
</head>
<body>
<%@include file="headers.jsp"%>
<br>
<br>
<a href="${pageContext.request.contextPath}/filter-order">
    <button type="button">Filter</button>
</a>
<br>
<br>
<c:forEach var="order" items="${requestScope.orders}">
    <form action="${pageContext.request.contextPath}/order-list" method="post">
            ${order.id}, ${order.customerName},${order.dateOfOrder}, Sum of Order: ${order.sumOfOrder}
        <button type="submit" name="deleteOrder" value="${order.id}">Delete order</button>
        <a href="${pageContext.request.contextPath}/edit-order?orderId=${order.id}">
            <button type="button">Edit Order</button>
            <br>
            <a href="${pageContext.request.contextPath}/detail-order?orderId=${order.id}">
                <button type="button">Details</button>
            </a>
    </form>
</c:forEach><br>
<a href="${pageContext.request.contextPath}/create-order">
    <button type="button">Create order</button>
</a>
</body>
</html>
