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
<jsp:include page="navbar.jsp"/>

<div class="container">
    <h1 class="text-center">Discounts</h1>
    <br>
    <a href="${url}/discounts?filter=percentage" class="btn btn-info"><fmt:message key="percentageLabel"/></a>
    <a href="${url}/discounts?filter=coefficient" class="btn btn-info"><fmt:message key="coefficientLabel"/></a>
    <a href="${url}/discounts?filter=all" class="btn btn-info">all</a>
</div>

<div class="container">
    <table class="table table-hover table-stripped">
        <tr>
            <th><fmt:message key="nameLabel"/></th>
            <th><fmt:message key="typeLabel"/></th>
            <th><fmt:message key="valueLabel"/></th>
            <th><fmt:message key="startLabel"/></th>
            <th><fmt:message key="endLabel"/></th>
        </tr>
        <c:forEach var="discount" items="${discounts}">
            <tr>
                <td>${discount.name}</td>
                <td>${discount.type}</td>
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