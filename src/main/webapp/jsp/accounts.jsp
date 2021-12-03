<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ex" uri="/WEB-INF/custom.tld" %>

<%@ page import="by.hrachyshkin.provider.model.Account" %>
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
    <h2 class="text-center">
        <fmt:message key="accountsLabel"/>
    </h2>
    <br>
    <div class="row">
        <table class="table table-hover table-stripped w-auto small">
            <tr>
                <th>
                    <fmt:message key="emailLabel"/>
                </th>
                <th>
                    <fmt:message key="roleLabel"/>
                </th>
                <th>
                    <fmt:message key="nameLabel"/>
                </th>
                <th>
                    <fmt:message key="telephoneLabel"/>
                </th>
                <th>
                    <fmt:message key="addressLabel"/>
                </th>
                <th>
                    <fmt:message key="balanceLabel"/>
                </th>
            </tr>
            <c:forEach var="account" items="${accounts}">
                <tr>
                    <form action="${url}/cabinet/accounts/update-list"
                          method="POST">
                        <td><input name="email" type="email"
                                   value="${account.email}"
                                   placeholder="${account.email}"/></td>
                        <td><select name="role">
                            <c:set var="roles"
                                   value="<%=Account.Role.values()%>"/>
                            <c:forEach var="role" items="${roles}">
                                <option value="${role.name()}" ${account.role.name()==role ?'selected':''}>${role.name()}</option>
                            </c:forEach>
                        </select></td>
                        <td><input name="name" type="text"
                                   value="${account.name}"
                                   placeholder="${account.name}"/>
                        </td>
                        <td><input name="phone" type="tel"
                                   pattern="[0-9]{3}-[0-9]{2}-[0-9]{2}"
                                   value="${account.phone}"
                                   placeholder="${account.phone}"/></td>
                        <td><input name="address" type="text"
                                   value="${account.address}"
                                   placeholder="${account.address}"/></td>
                        <td>${account.balance}</td>
                        <td>
                            <button type="submit" class="btn btn-info btn-sm">
                                <fmt:message key="updateLabel"/></button>
                        </td>
                        <input name="accountId" type="hidden"
                               value="${account.id}">
                        <input name="balance" type="hidden"
                               value="${account.balance}">
                        <input name="page" type="hidden"
                               value="${requestScope.page}">
                    </form>
                </tr>
            </c:forEach>
        </table>
    </div>

    <div class="row">
        <div class="col-md-2"></div>
        <div class="col-md-8">
            <form action="${url}/cabinet/accounts/create" method="POST">
                <input name="email" type="email"
                       placeholder="<fmt:message key="emailLabel"/>"/>
                <input name="password" type="password"
                       placeholder="<fmt:message key="passwordLabel"/>"/>
                <select name="role">
                    <c:set var="roles" value="<%=Account.Role.values()%>"/>
                    <c:forEach var="role" items="${roles}">
                        <option value="${role.name()}">${role.name()}</option>
                    </c:forEach>
                </select>
                <input name="name" type="text"
                       placeholder="<fmt:message key="nameLabel"/>"/>
                <input name="phone" type="tel"
                       pattern="[0-9]{3}-[0-9]{2}-[0-9]{2}"
                       placeholder="<fmt:message key="telephoneLabel"/>"/>
                <input name="address" type="text"
                       placeholder="<fmt:message key="addressLabel"/>"/>
                <button type="submit" class="btn btn-info btn-sm"><fmt:message
                        key="createLabel"/></button>
                <input name="page" type="hidden" value="${requestScope.page}">
            </form>
        </div>
        <div class="col-md-2"></div>
    </div>

</div>
<jsp:include page="footer.jsp"/>
</body>
</html>
