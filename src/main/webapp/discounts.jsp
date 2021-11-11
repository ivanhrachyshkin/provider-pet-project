<%@ page import="by.hrachyshkin.provider.entity.Tariff" %>
<%@ page import="by.hrachyshkin.provider.entity.Discount" %>
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
            <a href="${url}/index.jsp" class="navbar-brand">Provider</a>
        </div>
        <div>
            <ul class="nav navbar-nav">
                <li><a href="${url}/tariffs"><b>Tariff plans</b></a></li>
                <li><a href="${url}/discounts"><b>Discounts</b></a></li>
                <li><a href="#"><b>About us</b></a></li>
                <li><a href="#"><b>Personal Cabinet</b></a></li>
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
            <th>Action</th>
        </tr>
        <c:forEach var="discount" items="${discounts}">
            <tr>
                <form action="${url}/discounts/update" method="POST">
                    <td><input name="name" type="text" value="${discount.name}" placeholder="${discount.name}"/></td>
                    <td><select name="type">
                        <c:set var="types" value="<%=Discount.Type.values()%>"/>
                        <c:forEach var="type" items="${types}">
                            <option value="${type.name()}" ${discount.type.name()==type ?'selected':''}>${type.name()}</option>
                        </c:forEach>
                    </select></td>
                    <td><input name="value" type="number" value="${discount.value}" placeholder="${discount.value}"/></td>
                    <td><input name="dateFrom" type="date" value="${discount.dateFrom}" placeholder="${discount.dateFrom}"/></td>
                    <td><input name="dateTo" type="date" value="${discount.dateTo}" placeholder="${discount.dateTo}"/></td>
                    <td><button type="submit" class="btn btn-info">update</button></td>
                    <input name="id" type="hidden" value="${discount.id}">
                </form>
                <form action="${url}/discounts/delete" method="POST">
                    <td><button type="submit" class="btn btn-info">delete</button></td>
                    <input name="id" type="hidden" value="${discount.id}">
                </form>
            </tr>
        </c:forEach>
        <tr>
            <form action="${url}/discounts/create" method="POST">
                <td><input name="name" type="text"/></td>
                <td><select name="type">
                    <c:set var="types" value="<%=Discount.Type.values()%>"/>
                    <c:forEach var="type" items="${types}">
                        <option value="${type.name()}">${type.name()}</option>
                    </c:forEach>
                </select></td>
                <td><input name="value" type="number"/></td>
                <td><input name="dateFrom" type="date"/></td>
                <td><input name="dateTo" type="date"/></td>
                <td><button type="submit" class="btn btn-info">create</button></td>
            </form>
        </tr>
    </table>
</div>
<div>
    ${error}
</div>
</body>
</html>