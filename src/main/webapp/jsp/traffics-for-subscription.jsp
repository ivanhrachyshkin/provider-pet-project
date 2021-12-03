<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ex" uri="/WEB-INF/custom.tld" %>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${cookie['lang'].value}"/>
<fmt:setBundle basename="langs"/>
<c:set var="url">${pageContext.request.contextPath}</c:set>

<html>
<head>
    <ex:Head/>
</head>
<body>
<jsp:include page="navbar.jsp"/>

<div class="container">
    <h2 class="text-center"><fmt:message key="trafficForSubscriptionLabel"/> "${account.email}" <fmt:message
            key="subscriptionLabel"/> "${tariff.name}"</h2>
    <br>
    <div class="row">
        <div class="col-md-3"></div>
        <div class="col-md-6 table-responsive">
            <table class="table table-hover table-stripped w-auto small">
                <tr>
                    <th><fmt:message key="valueLabel"/></th>
                    <th><fmt:message key="dateLabel"/></th>
                </tr>
                <c:forEach var="subscriptionTraffic" items="${subscriptionTraffics}">
                    <tr>
                        <td>${subscriptionTraffic.value}</td>
                        <td>${subscriptionTraffic.date}</td>
                    </tr>
                </c:forEach>
            </table>
        </div>
        <div class="col-md-3"></div>
    </div>
</div>
<jsp:include page="footer.jsp"/>
</body>
</html>