<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="langs"/>

<c:set var="url">${pageContext.request.contextPath}</c:set>

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
                   aria-expanded="false"> &#127760;<fmt:message key="languageLabel"/> <span class="caret"></span></a>
                <ul class="dropdown-menu">
                    <li><a href="?sessionLocale=en_US">eng</a></li>
                    <li><a href="?sessionLocale=ru_RU">rus</a></li>
                </ul>
            </li>
            <li><a href="${url}/logout"><span class="glyphicon glyphicon-user"><fmt:message
                    key="logoutLabel"/></span></a></li>
        </ul>
    </div>
</nav>