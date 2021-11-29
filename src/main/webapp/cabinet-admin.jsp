<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page import="by.hrachyshkin.provider.model.Account" %>
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
    <h2 class="text-center"><fmt:message key="adminCabinetHeaderLabel"/></h2>
    <br>
    <div class="row table-responsive">
        <table class="table table-hover table-stripped w-auto small">
        <tr>
            <th><fmt:message key="emailLabel"/></th>
            <th><fmt:message key="roleLabel"/></th>
            <th><fmt:message key="nameLabel"/></th>
            <th><fmt:message key="telephoneLabel"/></th>
            <th><fmt:message key="addressLabel"/></th>
            <th><fmt:message key="balanceLabel"/></th>
        </tr>
        <tr>
            <form action="${url}/cabinet/accounts/update-cabinet" method="POST">
                <td><input name="email" type="email" value="${account.email}" placeholder="${account.email}"/></td>
                <td>${account.role.name()}</td>
                <td><input name="name" type="text" value="${account.name}" placeholder="${account.name}"/></td>
                <td><input name="phone" type="tel" value="${account.phone}" placeholder="${account.phone}"/></td>
                <td><input name="address" type="text" value="${account.address}" placeholder="${account.address}"/>
                </td>
                <td>${account.balance}</td>
                <td>
                    <button type="submit" class="btn btn-info btn-sm"><fmt:message key="updateLabel"/></button>
                </td>
                <input name="accountId" type="hidden" value="${account.id}">
                <input name="role" type="hidden" value="${account.role.name()}">
                <input name="balance" type="hidden" value="${account.balance}">
            </form>
        </tr>
    </table>
    </div>

    <div class="row text-center">
        <div class="col-md-4">
            <h3>
                <fmt:message key="accountsLabel"/>
                <a href="${url}/cabinet/accounts" class="btn btn-info btn-sm "><fmt:message key="accountsShowLabel"/></a>
            </h3>
        </div>
        <div class="col-md-4">
            <h3>
                <fmt:message key="tariffsLabel"/>
                <a href="${url}/tariffs" class="btn btn-info btn-sm"><fmt:message key="tariffsShowLabel"/></a>
            </h3>
        </div>
        <div class="col-md-4">
            <h3>
                <fmt:message key="discountsLabel"/>
                <a href="${url}/discounts" class="btn btn-info btn-sm"><fmt:message key="discountsShowLabel"/></a>
            </h3>
        </div>
    </div>
</div>
<jsp:include page="footer.jsp"/>
</body>
</html>