<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="langs"/>

<c:set var="url">${pageContext.request.contextPath}</c:set>

<title>Provider</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

<style type="text/css">
    #error {
        position: fixed;
        left: 0;
        bottom: 0;
        padding-top: 10px;
        width: 30%;
    }

    #footer {
        left: 0;
        bottom: 0;
        width: 100%;
    }

    input {
        max-width: 150px;
    }

    @media (max-width: 400px) {
        .table-hover {
            font-size:10px !important;
        }
    }

</style>