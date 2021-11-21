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
    <h2 class="text-center"><fmt:message key="subscriptionsForAccountLabel"/> "${account.email}"</h2>
    <br>
    <div class="row table-responsive">
        <table class="table table-hover table-stripped w-auto small">
            <tr>
                <th><fmt:message key="nameLabel"/></th>
                <th><fmt:message key="typeLabel"/></th>
                <th><fmt:message key="speedLabel"/></th>
                <th><fmt:message key="priceLabel"/></th>
                <th><fmt:message key="trafficsLabel"/></th>
                <th><fmt:message key="billsLabel"/></th>
                <th><fmt:message key="removeLabel"/></th>
            </tr>
            <c:forEach var="tariff" items="${accountTariffs}">
                <tr>
                    <td>${tariff.name}</td>
                    <td>${tariff.type}</td>
                    <td>${tariff.speed}</td>
                    <td>${tariff.price}</td>
                    <form action="${url}/cabinet/subscriptions/traffics" method="POST">
                        <td>
                            <button type="submit" class="btn btn-info btn-sm"><fmt:message key="trafficsLabel"/></button>
                        </td>
                        <input name="tariffId" type="hidden" value="${tariff.id}">
                    </form>
                    <form action="${url}/cabinet/subscriptions/bills" method="POST">
                        <td>
                            <button type="submit" class="btn btn-info btn-sm"><fmt:message key="billsLabel"/></button>
                        </td>
                        <input name="tariffId" type="hidden" value="${tariff.id}">
                    </form>
                    <form action="${url}/cabinet/subscriptions/delete" method="POST">
                        <td>
                            <button type="submit" class="btn btn-danger"><fmt:message key="removeLabel"/></button>
                        </td>
                        <input name="tariffId" type="hidden" value="${tariff.id}">
                    </form>
                </tr>
            </c:forEach>
        </table>
    </div>
    <div>
<span class="text-center">
<form action="${url}/cabinet/subscriptions/create" method="POST">
    <label><fmt:message key="tariffChooseLabel"/></label>
    <select name="tariffId">
        <c:forEach var="tariff" items="${tariffs}">
            <option value="${tariff.id}">${tariff.name} | ${tariff.type} | ${tariff.speed} | ${tariff.price}</option>
        </c:forEach>
    </select>
    <input type="submit" class="btn btn-info btn-sm" value=<fmt:message key="tariffAddLabel"/>>
    <input name="tariffId" type="hidden" value="${tariff.id}">
</form>
</span>
    </div>
</div>
<jsp:include page="footer.jsp"/>
</body>
</html>