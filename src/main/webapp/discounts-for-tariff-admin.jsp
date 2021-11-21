<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="langs"/>

<c:set var="url">${pageContext.request.contextPath}</c:set>

<html>
<head>
    <jsp:include page="head.jsp"/>
</head>
<body>
<jsp:include page="navbar.jsp"/>
<div class="container">
    <h2 class="text-center"><fmt:message key="discountsForLabel"/>" ${tariff.name}"</h2>
    <br>
    <div class="row table-responsive">
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
                    <form action="${url}/tariffs/discounts/delete" method="POST">
                        <td>
                            <button type="submit" class="btn btn-info btn-sm"><fmt:message key="removeLabel"/></button>
                        </td>
                        <input name="tariffId" type="hidden" value="${tariff.id}">
                        <input name="discountId" type="hidden" value="${discount.id}">
                    </form>
                </tr>
            </c:forEach>
        </table>
    </div>

    <div class="row">
        <span class="text-center">
<form action="${url}/tariffs/discounts/create" method="POST">
    <label><fmt:message key="discountsChooseLabel"/></label>
    <select name="discountId">
        <c:forEach var="discount" items="${discounts}">
            <option value="${discount.id}">${discount.name} | ${discount.type} | ${discount.value} | ${discount.dateFrom} | ${discount.dateTo}</option>
        </c:forEach>
    </select>
    <input type="submit" class="btn btn-info btn-sm" value=<fmt:message key="discountsAddLabel"/>>
    <input name="tariffId" type="hidden" value="${tariff.id}">
</form>
        </span>
    </div>
</div>
<jsp:include page="footer.jsp"/>
</body>
</html>