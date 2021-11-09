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
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
            integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
            crossorigin="anonymous"></script>
</head>
<body>
<jsp:include page="navbar.jsp"/>

<div class="container">
    <h2 class="text-center">Traffics for "${account.email}" for "${tariff.name}" subscription</h2>
    <br>
    <div class="row">
        <div class="col-md-3"></div>
        <div class="col-md-6">
            <table class="table table-hover table-stripped">
                <tr>
                    <th>Value</th>
                    <th>Date</th>
                </tr>
                <c:forEach var="subscriptionTraffic" items="${subscriptionTraffics}">
                    <tr>
                        <td>${subscriptionTraffic.value}</td>
                        <td>${subscriptionTraffic.date}</td>
                    </tr>
                </c:forEach>
            </table>
        </div>
        <div class="col-md-3"></div>
    </div>
</div>
<jsp:include page="footer.jsp"/>
</body>
</html>