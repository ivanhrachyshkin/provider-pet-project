<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="langs"/>

<!doctype html>
<head>
    <c:set var="url">${pageContext.request.contextPath}</c:set>
    <title>Provider</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"
          integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
            integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
            crossorigin="anonymous"></script>
</head>
<body>
<jsp:include page="navbar.jsp" />

<div class="container">
    <h1 class="text-center"><fmt:message key="tariffsLabel"/></h1>
    <br>
    <a href="${url}/tariffs?filter=trafficked" class="btn btn-info"><fmt:message key="traffickedLabel"/></a>
    <a href="${url}/tariffs?filter=unlimited" class="btn btn-info"><fmt:message key="unlimitedLabel"/></a>
    <a href="${url}/tariffs?filter=all" class="btn btn-info">all</a>
</div>

<div class="container">
    <table class="table table-hover table-stripped">
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
                        <button type="submit" class="btn btn-info"><fmt:message key="discountsLabel"/></button>
                    </td>
                    <input name="tariffId" type="hidden" value="${tariff.id}">
                </form>

                <c:forEach var="tariff" items="${tariffs}">
                    <c:if test="${tariff.id == tariff.id}">
                        <td><button disabled class="btn btn-success">connected</button></td>
                    </c:if>
                </c:forEach>
            </tr>
        </c:forEach>
    </table>
</div>
<jsp:include page="footer.jsp" />
</body>
</html>