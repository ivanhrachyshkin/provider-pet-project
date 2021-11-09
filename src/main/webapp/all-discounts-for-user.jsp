<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="ru_RU"/>
<fmt:setBundle basename="langs"/>

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