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
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
            integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
            crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>

</head>
<body>
<nav class="navbar navbar-inverse">
    <div class="container">
        <div class="navbar-header">
            <a href="${url}/main" class="navbar-brand">Provider</a>
        </div>
        <ul class="nav navbar-nav">
            <li><a href="${url}/tariffs"><b><fmt:message key="tariffsLabel"/></b></a></li>
            <li><a href="${url}/discounts"><b><fmt:message key="discountsLabel"/></b></a></li>
            <li><a href="#"><b><fmt:message key="aboutUsLabel"/></b></a></li>
            <li><a href="${url}/cabinet"><b><fmt:message key="cabinetLabel"/></b></a></li>
        </ul>
        <ul class="nav navbar-nav navbar-right">
            <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"
                   aria-expanded="false"> &#127760;<fmt:message key="languageLabel"/><span class="caret"></span></a>
                <ul class="dropdown-menu">
                    <li><a href="?sessionLocale=en_US">eng</a></li>
                    <li><a href="?sessionLocale=ru_RU">rus</a></li>
                </ul>
            </li>
        </ul>
    </div>
</nav>

<div class="container">
    <div class="row">
        <div class="col-md-4"></div>
        <div class="col-md-4">
            <form role="form" action="${url}/login" method="POST">
                <div class="form-group">
                    <label><fmt:message key="emailLabel"/></label>
                    <input type="text" class="form-control" name="email" placeholder="<fmt:message key="emailLabel"/>">
                </div>
                <div class="form-group">
                    <label><fmt:message key="passwordLabel"/></label>
                    <input type="text" class="form-control" name="password"
                           placeholder="<fmt:message key="passwordLabel"/>">
                </div>
                <input type="submit" class="btn btn-info" value=<fmt:message key="loginLabel"/>>
            </form>
        </div>
        <div class="col-md-4"></div>
    </div>
</div>
<jsp:include page="footer.jsp"/>
</body>
</html>