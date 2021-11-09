<%@ page import="by.hrachyshkin.provider.model.Account" %>
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
    <h1 class="text-center">Accounts</h1>
    <br>
    <table class="table table-hover table-stripped">
        <tr>
            <th>Email</th>
            <th>Role</th>
            <th>Name</th>
            <th>Phone</th>
            <th>Address</th>
            <th>Balance</th>
        </tr>
        <c:forEach var="account" items="${accounts}">
            <tr>
                <form action="${url}/cabinet/update" method="POST">
                    <td><input name="email" type="text" value="${account.email}"
                               placeholder="${account.email}"/></td>
                    <td><select name="role">
                        <c:set var="roles" value="<%=Account.Role.values()%>"/>
                        <c:forEach var="role" items="${roles}">
                            <option value="${role.name()}" ${account.role.name()==role ?'selected':''}>${role.name()}</option>
                        </c:forEach>
                    </select></td>
                    <td><input name="name" type="text" value="${account.name}" placeholder="${account.name}"/>
                    </td>
                    <td><input name="phone" type="text" value="${account.phone}"
                               placeholder="${account.phone}"/></td>
                    <td><input name="address" type="text" value="${account.address}"
                               placeholder="${account.address}"/></td>
                    <td>${account.balance}</td>
                    <td>
                        <button type="submit" class="btn btn-info"><fmt:message key="updateLabel"/></button>
                    </td>
                    <input name="accountId" type="hidden" value="${account.id}">
                    <input name="balance" type="hidden" value="${account.balance}">
                </form>
            </tr>
        </c:forEach>
    </table>

    <div class="row">
        <div class="col-md-2"></div>
        <div class="col-md-8">
            <form action="${url}/cabinet/accounts/create" method="POST">
                <input name="email" type="text" placeholder="email"/>
                <input name="password" type="text" placeholder="password"/>
                <select name="role">
                    <c:set var="roles" value="<%=Account.Role.values()%>"/>
                    <c:forEach var="role" items="${roles}">
                        <option value="${role.name()}">${role.name()}</option>
                    </c:forEach>
                </select>
                <input name="name" type="text" placeholder="name"/>
                <input name="phone" type="text" placeholder="phone"/>
                <input name="address" type="text" placeholder="address"/>
                <button type="submit" class="btn btn-info"><fmt:message key="createLabel"/></button>
            </form>
        </div>
        <div class="col-md-2"></div>
    </div>
</div>
<jsp:include page="footer.jsp"/>
</body>
</html>
