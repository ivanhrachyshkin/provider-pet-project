<%@ page import="by.hrachyshkin.provider.model.Tariff" %>
<%@ page import="by.hrachyshkin.provider.model.Discount" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
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
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header">
            <a href="${url}/index" class="navbar-brand">Provider</a>
        </div>
        <div>
            <ul class="nav navbar-nav">
                <li><a href="${url}/tariffs"><b>Tariff plans</b></a></li>
                <li><a href="${url}/discounts"><b>Discounts</b></a></li>
                <li><a href="#"><b>About us</b></a></li>
                <li><a href="${url}/cabinet"><b>Personal Cabinet</b></a></li>
            </ul>
        </div>
    </div>
</nav>

<div class="container">
    <h1 class="text-center">Discounts</h1>
    <h1></h1>
    <h1></h1>
    <a href="${url}/discounts?filter=percentage" class="btn btn-info">percentage</a>
    <a href="${url}/discounts?filter=coefficient" class="btn btn-info">coefficient</a>
</div>

<div class="container">
    <table class="table table-hover table-stripped">
        <tr>
            <th>Name</th>
            <th>Type</th>
            <th>Value</th>
            <th>Start</th>
            <th>End</th>
        </tr>
        <c:forEach var="discount" items="${discounts}">
            <tr>
                    <td>${discount.name}</td>
                    <td>${discount.type}</td>
                    <td>${discount.value}</td>
                    <td>${discount.dateFrom}</td>
                    <td>${discount.dateTo}</td>
            </tr>
        </c:forEach>
    </table>
</div>
<div>
    ${error}
</div>
</body>
</html>