<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ex" uri="/WEB-INF/custom.tld" %>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${cookie['lang'].value}"/>
<fmt:setBundle basename="langs"/>

<c:set var="url">${pageContext.request.contextPath}</c:set>

<html>
<head>
    <ex:Head/>
</head>
<body>
<jsp:include page="navbar.jsp"/>
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
<jsp:include page="footer.jsp"/>
</body>
</html>