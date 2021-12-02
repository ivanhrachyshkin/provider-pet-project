<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ex" uri="/WEB-INF/custom.tld" %>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="langs"/>
<c:set var="url">${pageContext.request.contextPath}</c:set>

<html>
<head>
    <ex:Head/>
</head>
<body>
<jsp:include page="navbar.jsp"/>
<div class="container">
    <h2 class="text-center">Discounts for "${tariff.name}" tariff plan</h2>
    <br>
    <div class="table-responsive">
        <table class="table table-hover table-stripped w-auto small">
            <tr>
                <th><fmt:message key="nameLabel"/></th>
                <th><fmt:message key="typeLabel"/></th>
                <th><fmt:message key="valueLabel"/></th>
                <th><fmt:message key="startLabel"/></th>
                <th><fmt:message key="endLabel"/></th>
            </tr>
            <c:forEach var="discount" items="${tariffDiscounts}">
                <tr>
                    <td>${discount.name}</td>
                    <td>${discount.value}</td>
                    <td>${discount.value}</td>
                    <td>${discount.dateFrom}</td>
                    <td>${discount.dateTo}</td>
                </tr>
            </c:forEach>
        </table>
    </div>
    <jsp:include page="footer.jsp"/>
</body>
</html>