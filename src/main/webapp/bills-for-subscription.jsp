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
    <h2 class="text-center"><fmt:message key="billsForSubscriptionLabel"/> "${account.email}" <fmt:message
            key="subscriptionLabel"/> "${tariff.name}"</h2>
    <br>

    <div class="row">
        <div class="col-md-3"></div>
        <div class="col-md-6 table-responsive">
            <table class="table table-hover table-stripped w-auto small">
                <tr>
                    <th><fmt:message key="valueLabel"/></th>
                    <th><fmt:message key="dateLabel"/></th>
                    <th><fmt:message key="statusLabel"/></th>
                </tr>
                <c:forEach var="bill" items="${subscriptionBills}">
                    <tr>
                        <td>${bill.value}</td>
                        <td>${bill.date}</td>
                        <td>${bill.status}</td>
                        <td>
                            <form action="${url}/cabinet/subscriptions/bills/pay" method="POST">
                                <c:choose>
                                    <c:when test="${bill.status == false}">
                                        <input name="subscriptionId" type="hidden" value="${bill.subscriptionId}">
                                        <input name="value" type="hidden" value="${bill.value}">
                                        <input name="date" type="hidden" value="${bill.date}">
                                        <input name="tariffId" type="hidden" value="${tariff.id}">
                                        <button type="submit" class="btn btn-danger btn-sm"><fmt:message
                                                key="payLabel"/></button>
                                        <input name="page" type="hidden" value="${requestScope.page}">
                                    </c:when>
                                    <c:otherwise>
                                        <button disabled class="btn btn-success"><fmt:message key="paidLabel"/></button>
                                    </c:otherwise>
                                </c:choose>
                            </form>
                        </td>
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