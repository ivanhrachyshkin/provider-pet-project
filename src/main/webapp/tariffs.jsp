<%@ page import="by.hrachyshkin.provider.entity.Tariff" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="ru">
<head>
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
            <a href="index.jsp" class="navbar-brand">Provider</a>
        </div>
        <div>
            <ul class="nav navbar-nav">
                <li><a href="tariffs"><b>Tariff plans</b></a></li>
                <li><a href="discounts"><b>Discounts</b></a></li>
                <li><a href="#"><b>About us</b></a></li>
                <li><a href="#"><b>Personal Cabinet</b></a></li>
            </ul>
        </div>
    </div>
</nav>

<div class="container">
    <h1 class="text-center">Tariff plans</h1>
    <h1></h1>
    <h1></h1>
    <a href="tariffs?filter=trafficked" class="btn btn-info">trafficked</a>
    <a href="tariffs?filter=unlimited" class="btn btn-info">unlimited</a>
</div>

<div class="container">
    <table class="table table-hover table-stripped">
        <tr>
            <th>Name</th>
            <th>Type</th>
            <th>Speed</th>
            <th>Price</th>
            <th>Action</th>
        </tr>
        <c:forEach var="tariff" items="${tariffs}">
            <tr>
                <form action="tariffs/update" method="POST">
                    <td><input name="name" type="text" value="${tariff.name}" placeholder="${tariff.name}"/></td>
                    <td><select name="type">
                        <c:set var="types" value="<%=Tariff.Type.values()%>"/>
                        <c:forEach var="type" items="${types}">
                            <option value="${type.name()}" ${tariff.type.name()==type ?'selected':''}>${type.name()}</option>
                        </c:forEach>
                    </select></td>
                    <td><input name="speed" type="number" value="${tariff.speed}" placeholder="${tariff.speed}"/></td>
                    <td><input name="price" type="number" value="${tariff.price}" placeholder="${tariff.price}"/></td>
                    <td><button type="submit" class="btn btn-info">update</button></td>
                    <input name="id" type="hidden" value="${tariff.id}">
                </form>
                <form action="tariffs/delete" method="POST">
                    <td><button type="submit" class="btn btn-info">delete</button></td>
                    <input name="id" type="hidden" value="${tariff.id}">
                </form>
                <form action="tariffs/discounts" method="GET">
                    <td><button type="submit" class="btn btn-info">discounts</button></td>
                    <input name="id" type="hidden" value="${tariff.id}">
                </form>
            </tr>
        </c:forEach>
        <tr>
            <form action="tariffs/create" method="POST">
                <td><input name="name" type="text"/></td>
                <td><select name="type">
                    <c:set var="types" value="<%=Tariff.Type.values()%>"/>
                    <c:forEach var="type" items="${types}">
                        <option value="${type.name()}">${type.name()}</option>
                    </c:forEach>
                </select></td>
                <td><input name="speed" type="number"/></td>
                <td><input name="price" type="number"/></td>
                <td><button type="submit" class="btn btn-info">create</button></td>
            </form>
        </tr>
    </table>
</div>

</body>
</html>