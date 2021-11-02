<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page import="by.hrachyshkin.provider.model.Tariff" %>
<%@ page import="by.hrachyshkin.provider.model.Discount" %>
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
<jsp:include page="navbar.jsp"/>

<div class="container">
    <h2 class="text-center"><fmt:message key="discountsLabel"/></h2>
    <br>
    <div class="btn-group">
        <a href="${url}/discounts?filter=percentage" class="btn btn-info btn-sm" aria-current="page"><fmt:message
                key="percentageLabel"/></a>
        <a href="${url}/discounts?filter=coefficient" class="btn btn-info btn-sm"><fmt:message key="coefficientLabel"/></a>
        <a href="${url}/discounts?filter=all" class="btn btn-info btn-sm"><fmt:message key="allLabel"/></a>
    </div>

    <div class="table-responsive">
        <table class="table table-hover table-stripped w-auto small">
            <tr>
                <th><fmt:message key="nameLabel"/></th>
                <th><fmt:message key="typeLabel"/></th>
                <th><fmt:message key="valueLabel"/></th>
                <th><fmt:message key="startLabel"/></th>
                <th><fmt:message key="endLabel"/></th>
            </tr>
            <c:forEach var="discount" items="${discounts}">
                <tr>
                    <form action="${url}/discounts/update" method="POST">
                        <td><input name="name" type="text" value="${discount.name}" placeholder="${discount.name}"/>
                        </td>
                        <td><select name="type">
                            <c:set var="types" value="<%=Discount.Type.values()%>"/>
                            <c:forEach var="type" items="${types}">
                                <option value="${type.name()}" ${discount.type.name()==type ?'selected':''}>${type.name()}</option>
                            </c:forEach>
                        </select></td>
                        <td><input name="value" type="number" value="${discount.value}"
                                   placeholder="${discount.value}"/>
                        </td>
                        <td><input name="dateFrom" type="date" value="${discount.dateFrom}"
                                   placeholder="${discount.dateFrom}"/></td>
                        <td><input name="dateTo" type="date" value="${discount.dateTo}"
                                   placeholder="${discount.dateTo}"/>
                        </td>
                        <td>
                            <button type="submit" class="btn btn-info btn-sm"><fmt:message key="updateLabel"/></button>
                        </td>
                        <input name="id" type="hidden" value="${discount.id}">
                        <input name="page" type="hidden" value="${requestScope.page}">
                    </form>
                    <form action="${url}/discounts/delete" method="POST">
                        <td>
                            <button type="submit" class="btn btn-info btn-sm"><fmt:message key="deleteLabel"/></button>
                        </td>
                        <input name="discountId" type="hidden" value="${discount.id}">
                        <input name="page" type="hidden" value="${requestScope.page}">
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