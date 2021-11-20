<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
      integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"
      integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
        integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
        crossorigin="anonymous"></script>

<script>$('#error').on('closed.bs.alert', function () {

})</script>

<style type="text/css">
    #footer {
        position: fixed;
        rght: 0;
        bottom: 0;
        padding: 10px;
        width: 100%;
    }
</style>

<div id="footer">
    <div class="container text-right">
        <nav aria-label="pagination">
            <ul class="pagination">
                <c:forEach var="i" begin="1" end="${totalPages}">
                    <c:if test="${i == requestScope.page}">
                        <li class="page-item active"><a class="active" href="?page=${i}">${i}</a></li>
                    </c:if>
                    <c:if test="${i != requestScope.page}">
                        <li class="page-item"><a href="?page=${i}">${i}</a></li>
                    </c:if>
                </c:forEach>
            </ul>
        </nav>
    </div>
</div>

<style type="text/css">
    #error {
        position: fixed;
        left: 0;
        bottom: 0;
        padding-top: 10px;
        width: 30%;
    }
</style>


<c:if test="${error != null}">
    <div id="error" class="alert alert-danger alert-dismissible" role="alert">
            ${error}
        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
            <span aria-hidden="true">&times;</span>
        </button>
    </div>
    <c:remove var="error" scope="session" />
</c:if>

