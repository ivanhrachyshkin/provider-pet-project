<%@ page import="by.hrachyshkin.provider.model.Account" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="ru">
<head>
    <c:set var="url">${pageContext.request.contextPath}</c:set>
    <title>Admin cabinet</title>
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
<jsp:include page="header.jsp" />
<div class="container">
    <h1 class="text-center">Admin account information</h1>
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
        <tr>
            <form action="${url}/cabinet/update" method="POST">
                <td><input name="email" type="text" value="${account.email}" placeholder="${account.email}"/>
                </td>
                <td><select name="role">
                    <c:set var="roles" value="<%=Account.Role.values()%>"/>
                    <c:forEach var="role" items="${roles}">
                        <option value="${role.name()}" ${account.role.name()==role ?'selected':''}>${role.name()}</option>
                    </c:forEach>
                </select></td>
                <td><input name="name" type="text" value="${account.name}" placeholder="${account.name}"/></td>
                <td><input name="phone" type="text" value="${account.phone}" placeholder="${account.phone}"/>
                </td>
                <td><input name="address" type="text" value="${account.address}"
                           placeholder="${account.address}"/></td>
                <td>${account.balance}</td>
                <td>
                    <button type="submit" class="btn btn-info">update</button>
                </td>
                <input name="accountId" type="hidden" value="${account.id}">
                <input name="balance" type="hidden" value="${account.balance}">
            </form>
        </tr>
    </table>


    <h3>
        <div class="row text-center">
            <div class="col-md-4">
                Accounts
                <a href="${url}/cabinet/accounts" class="btn btn-info ">show accounts</a>
            </div>
            <div class="col-md-4">
                Tariffs
                <a href="${url}/tariffs" class="btn btn-info">show tariffs</a>
            </div>
            <div class="col-md-4">
                Discounts
                <a href="${url}/discounts" class="btn btn-info">show discounts</a>
            </div>
        </div>
    </h3>
</div>
<jsp:include page="footer.jsp" />
</body>
</html>