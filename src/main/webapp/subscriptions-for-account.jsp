<%@ page import="by.hrachyshkin.provider.model.Tariff" %>
<%@ page import="by.hrachyshkin.provider.model.Discount" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <h1 class="text-center">Subscriptions for "${account.email}" account</h1>
    <h1></h1>
    <h1></h1>
</div>

<div class="container">
    <table class="table table-hover table-stripped">
        <tr>
            <th>Name</th>
            <th>Type</th>
            <th>Speed</th>
            <th>Price</th>
            <th>Traffic</th>
            <th>Bills</th>
            <th>Remove</th>
        </tr>
        <c:forEach var="tariff" items="${accountTariffs}">
            <tr>
                    <td>${tariff.name}</td>
                    <td>${tariff.type}</td>
                    <td>${tariff.speed}</td>
                    <td>${tariff.price}</td>
                <form action="${url}/cabinet/subscriptions/traffics" method="POST">
                    <td><button type="submit" class="btn btn-info">traffics</button></td>
                    <input name="tariffId" type="hidden" value="${tariff.id}">
                </form>
                <form action="${url}/cabinet/subscriptions/bills" method="POST">
                    <td><button type="submit" class="btn btn-info">bills</button></td>
                    <input name="tariffId" type="hidden" value="${tariff.id}">
                </form>
                <form action="${url}/cabinet/subscriptions/remove" method="POST">
                    <td><button type="submit" class="btn btn-danger">remove</button></td>
                    <input name="tariffId" type="hidden" value="${tariff.id}">
                </form>
            </tr>
        </c:forEach>
    </table>
</div>

<span class="text-center">
<form action="${url}/cabinet/subscriptions/add" method="POST">
    <label>Choose a tariff:</label>
    <select name="tariffId">
        <c:forEach var="tariff" items="${tariffs}">
            <option value="${tariff.id}">${tariff.name} | ${tariff.type} | ${tariff.speed} | ${tariff.price}</option>
        </c:forEach>
    </select>
    <input type="submit" class="btn btn-info" value="add tariff">
    <input name="tariffId" type="hidden" value="${tariff.id}">
</form>
</span>
<jsp:include page="footer.jsp" />
</body>
</html>