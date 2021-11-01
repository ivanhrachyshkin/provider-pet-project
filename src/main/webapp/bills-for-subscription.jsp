<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="langs"/>

<!doctype html>
<html lang="ru">
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
    <h1 class="text-center"><fmt:message key="billsForSubscriptionLabel"/> "${account.email}" <fmt:message key="subscriptionLabel"/> "${tariff.name}"</h1>
    <br>
</div>
<div class="row">
    <div class="col-md-3"></div>
    <div class="col-md-6">
        <table class="table table-hover table-stripped">
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
                                    <button type="submit" class="btn btn-danger"><fmt:message key="payLabel"/></button>
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
<jsp:include page="footer.jsp" />
</body>
</html>