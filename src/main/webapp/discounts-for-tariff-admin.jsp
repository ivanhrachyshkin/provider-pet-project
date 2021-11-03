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

<jsp:include page="header.jsp" />
<div class="container">
    <h1 class="text-center">Discounts for "${tariff.name}" tariff plan</h1>
    <br>
    <table class="table table-hover table-stripped">
        <tr>
            <th>Name</th>
            <th>Type</th>
            <th>Value</th>
            <th>Start</th>
            <th>End</th>
            <th>Action</th>
        </tr>
        <c:forEach var="discount" items="${tariffDiscounts}">
            <tr>
                    <td>${discount.name}</td>
                    <td>${discount.value}</td>
                    <td>${discount.value}</td>
                    <td>${discount.dateFrom}</td>
                    <td>${discount.dateTo}</td>
                <form action="${url}/tariffs/discounts/remove" method="POST">
                    <td><button type="submit" class="btn btn-info">remove</button></td>
                    <input name="tariffId" type="hidden" value="${tariff.id}">
                    <input name="discountId" type="hidden" value="${discount.id}">
                </form>
            </tr>
        </c:forEach>
    </table>
</div>

<span class="text-center">
<form action="${url}/tariffs/discounts/add" method="POST">
    <label>Choose a discount:</label>
    <select name="discountId">
        <c:forEach var="discount" items="${discounts}">
            <option value="${discount.id}">${discount.name} | ${discount.type} | ${discount.value} | ${discount.dateFrom} | ${discount.dateTo}</option>
        </c:forEach>
    </select>
    <input type="submit" class="btn btn-info" value="add discount">
    <input name="tariffId" type="hidden" value="${tariff.id}">
</form>
</span>
<jsp:include page="footer.jsp" />
</body>
</html>