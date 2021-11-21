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
    <h2 class="text-center"><fmt:message key="tariffsLabel"/></h2>
    <br>
    <div class="btn-group">
        <a href="${url}/tariffs?filter=trafficked" class="btn btn-info btn-sm" aria-current="page"><fmt:message
                key="traffickedLabel"/></a>
        <a href="${url}/tariffs?filter=unlimited" class="btn btn-info btn-sm"><fmt:message key="unlimitedLabel"/></a>
        <a href="${url}/tariffs?filter=all" class="btn btn-info btn-sm"><fmt:message key="allLabel"/></a>
    </div>

    <div class="table-responsive">
        <table class="table table-hover table-stripped w-auto small">
            <tr>
                <th><fmt:message key="nameLabel"/></th>
                <th><fmt:message key="typeLabel"/></th>
                <th><fmt:message key="speedLabel"/></th>
                <th><fmt:message key="priceLabel"/></th>
                <th><fmt:message key="discountsLabel"/></th>
            </tr>
            <c:forEach var="tariff" items="${tariffs}">
                <tr>
                    <td>${tariff.name}</td>
                    <td>${tariff.type}</td>
                    <td>${tariff.speed}</td>
                    <td>${tariff.price}</td>

                    <form action="${url}/tariffs/discounts" method="POST">
                        <td>
                            <button type="submit" class="btn btn-info btn-sm"><fmt:message key="discountsLabel"/></button>
                        </td>
                        <input name="tariffId" type="hidden" value="${tariff.id}">
                    </form>

                    <c:forEach var="accountTariff" items="${accountTariffs}">
                        <c:if test="${tariff.id == accountTariff.id}">
                            <td>
                                <button disabled class="btn btn-success">connected</button>
                            </td>
                        </c:if>
                    </c:forEach>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>
<jsp:include page="footer.jsp"/>
</body>
</html>