<%@ page import="by.hrachyshkin.provider.model.Discount" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="ru_RU"/>
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
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
            integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
            crossorigin="anonymous"></script>
</head>
<body>
<jsp:include page="navbar.jsp" />

<div class="container">
    <h1 class="text-center"><fmt:message key="discountsLabel"/></h1>
    <br>
    <a href="${url}/discounts?filter=percentage" class="btn btn-info"><fmt:message key="percentageLabel"/></a>
    <a href="${url}/discounts?filter=coefficient" class="btn btn-info"><fmt:message key="coefficientLabel"/></a>
    <a href="${url}/discounts?filter=all" class="btn btn-info"><fmt:message key="allLabel"/></a>
</div>

<div class="container">
    <table class="table table-hover table-stripped">
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
                    <td><button type="submit" class="btn btn-info"><fmt:message key="updateLabel"/></button></td>
                    <input name="id" type="hidden" value="${discount.id}">
                </form>
                <form action="${url}/discounts/delete" method="POST">
                    <td><button type="submit" class="btn btn-info"><fmt:message key="deleteLabel"/></button></td>
                    <input name="discountId" type="hidden" value="${discount.id}">
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
                <td><button type="submit" class="btn btn-info"><fmt:message key="createLabel"/></button></td>
            </form>
        </tr>
    </table>
</div>
<jsp:include page="footer.jsp" />
</body>
</html>