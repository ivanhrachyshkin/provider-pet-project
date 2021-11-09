<%@ page import="by.hrachyshkin.provider.model.Account" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="ru_RU"/>
<fmt:setBundle basename="langs"/>

<!doctype html>
<head>
    <c:set var="url">${pageContext.request.contextPath}</c:set>
    <title>User cabinet</title>
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
        <h1 class="text-center"><fmt:message key="userCabinetHeaderLabel"/></h1>
        <br>
        <table class="table table-hover table-stripped">
            <tr>
                <th><fmt:message key="emailLabel"/></th>
                <th><fmt:message key="roleLabel"/></th>
                <th><fmt:message key="nameLabel"/></th>
                <th><fmt:message key="telephoneLabel"/></th>
                <th><fmt:message key="addressLabel"/></th>
                <th><fmt:message key="balanceLabel"/></th>
            </tr>
            <tr>
                <form action="${url}/cabinet/update" method="POST">
                    <td><input name="email" type="text" value="${account.email}" placeholder="${account.email}"/></td>
                    <td><select name="role">
                        <c:set var="roles" value="<%=Account.Role.values()%>"/>
                        <c:forEach var="role" items="${roles}">
                            <option value="${role.name()}" ${account.role.name()==role ?'selected':''}>${role.name()}</option>
                        </c:forEach>
                    </select></td>
                    <td><input name="name" type="text" value="${account.name}" placeholder="${account.name}"/></td>
                    <td><input name="phone" type="text" value="${account.phone}" placeholder="${account.phone}"/></td>
                    <td><input name="address" type="text" value="${account.address}" placeholder="${account.address}"/>
                    </td>
                    <td>${account.balance}</td>
                    <td>
                        <button type="submit" class="btn btn-info"><fmt:message key="updateLabel"/></button>
                    </td>
                    <input name="accountId" type="hidden" value="${account.id}">
                    <input name="balance" type="hidden" value="${account.balance}">
                </form>
            </tr>
        </table>
    </div>

    <div class="container">
        <h3>
            <div class="row">
                <div class="col-md-2">
                </div>
                <div class="col-md-4 text-center">
                    <h3>Subscriptions</h3>
                    <a href="${url}/cabinet/subscriptions" class="btn btn-info">my subscriptions</a>
                </div>
                <div class="col-md-4">
                    <form action="${url}/cabinet/deposit" method="POST">
                        <input class="form-control input-sm" name="card" type="number"
                               placeholder="card number xxxx xxxx xxxx xxxx"/>
                        <input class="form-control input-sm" name="sum" type="number" value="sum" placeholder="sum"/>
                        <input class="form-control input-sm" name="validity" type="date" placeholder="VALID THRU"/>
                        <button type="submit" class="btn btn-info">deposit</button>
                    </form>

                </div>
            </div>
            <div class="col-md-2"></div>
        </h3>
    </div>
<jsp:include page="footer.jsp"/>
</body>
</html>