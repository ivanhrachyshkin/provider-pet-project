<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="url">${pageContext.request.contextPath}</c:set>

<nav class="navbar navbar-inverse">
    <div class="container">
        <div class="navbar-header">
            <a href="${url}/main" class="navbar-brand">Provider</a>
        </div>
        <ul class="nav navbar-nav">
            <li><a href="${url}/tariffs"><b>Tariff plans</b></a></li>
            <li><a href="${url}/discounts"><b>Discounts</b></a></li>
            <li><a href="#"><b>About us</b></a></li>
            <li><a href="${url}/cabinet"><b>Personal Cabinet</b></a></li>
        </ul>
        <ul class="nav navbar-nav navbar-right">
            <li><a href="${url}/logout"><span class="glyphicon glyphicon-user"></span> Log Out</a></li>
        </ul>
    </div>
</nav>