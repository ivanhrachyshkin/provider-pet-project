<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="langs"/>

<c:set var="url">${pageContext.request.contextPath}</c:set>

<html>
<head>
    <jsp:include page="head.jsp"/>
</head>
<body>
<jsp:include page="navbar.jsp" />
<div class="container">
    <h2 class="text-center"><fmt:message key="mainTitleLabel"/></h2>
    <br>
    <h2 class="text-center"><fmt:message key="mainDescriptionLabel"/></h2>
    <br>
    <h2 class="text-center"><fmt:message key="mainDescriptionLabel"/></h2>
    <br>
    <h2 class="text-center"><fmt:message key="mainDescriptionLabel"/></h2>
    <br>
    <h2 class="text-center"><fmt:message key="mainDescriptionLabel"/></h2>
</div>
<jsp:include page="footer.jsp" />
</body>
</html>