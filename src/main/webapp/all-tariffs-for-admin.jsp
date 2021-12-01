<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ex" uri="/WEB-INF/custom.tld" %>

<%@ page import="by.hrachyshkin.provider.model.Tariff" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="langs"/>
<c:set var="url">${pageContext.request.contextPath}</c:set>

<html>
<head>
        <ex:Head/>
</head>
<body>
<jsp:include page="navbar.jsp"/>

<div class="container">
    <h2 class="text-center"><fmt:message key="tariffsLabel"/></h2>
    <br>

    <div class="btn-group">
        <a href="${url}/tariffs?filter=trafficked" class="btn btn-info btn-sm" aria-current="page"><fmt:message
                key="traffickedLabel"/></a>
        <a href="${url}/tariffs?filter=unlimited" class="btn btn-info btn-sm"><fmt:message key="unlimitedLabel"/></a>
        <a href="${url}/tariffs?filter=all" class="btn btn-info btn-sm"><fmt:message key="allLabel"/></a>
    </div>

    <div class="table-responsive">
        <table class="table table-hover table-stripped w-auto small">
            <tr>
                <th><fmt:message key="nameLabel"/></th>
                <th><fmt:message key="typeLabel"/></th>
                <th><fmt:message key="speedLabel"/></th>
                <th><fmt:message key="priceLabel"/></th>
            </tr>
            <c:forEach var="tariff" items="${tariffs}">
                <tr>
                    <form action="${url}/tariffs/update" method="POST">
                        <td><input name="name" type="text" value="${tariff.name}" placeholder="${tariff.name}"/></td>
                        <td><select name="type">
                            <c:set var="types" value="<%=Tariff.Type.values()%>"/>
                            <c:forEach var="type" items="${types}">
                                <option value="${type.name()}" ${tariff.type.name()==type ?'selected':''}>${type.name()}</option>
                            </c:forEach>
                        </select></td>
                        <td><input name="speed" type="number" value="${tariff.speed}" placeholder="${tariff.speed}"/>
                        </td>
                        <td><input name="price" type="number" value="${tariff.price}" placeholder="${tariff.price}"/>
                        </td>
                        <td>
                            <button type="submit" class="btn btn-info btn-sm"><fmt:message key="updateLabel"/></button>
                        </td>
                        <input name="tariffId" type="hidden" value="${tariff.id}">
                        <input name="page" type="hidden" value="${requestScope.page}">
                    </form>
                    <form action="${url}/tariffs/delete" method="POST">
                        <td>
                            <button type="submit" class="btn btn-info btn-sm"><fmt:message key="deleteLabel"/></button>
                        </td>
                        <input name="tariffId" type="hidden" value="${tariff.id}">
                        <input name="page" type="hidden" value="${requestScope.page}">
                    </form>
                    <form action="${url}/tariffs/discounts" method="POST">
                        <td>
                            <button type="submit" class="btn btn-info btn-sm"><fmt:message key="discountsLabel"/></button>
                        </td>
                        <input name="tariffId" type="hidden" value="${tariff.id}">
                    </form>
                </tr>
            </c:forEach>
            <tr>
                <form action="${url}/tariffs/create" method="POST">
                    <td><input name="name" type="text"/></td>
                    <td><select name="type">
                        <c:set var="types" value="<%=Tariff.Type.values()%>"/>
                        <c:forEach var="type" items="${types}">
                            <option value="${type.name()}">${type.name()}</option>
                        </c:forEach>
                    </select></td>
                    <td><input name="speed" type="number"/></td>
                    <td><input name="price" type="number"/></td>
                    <td>
                        <button type="submit" class="btn btn-info btn-sm"><fmt:message key="createLabel"/></button>
                    </td>
                    <input name="page" type="hidden" value="${requestScope.page}">
                </form>
            </tr>
        </table>
    </div>
</div>
<jsp:include page="footer.jsp"/>
</body>
</html>