<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="footer">
    <div class="container text-right">
        <ul class="pagination">
            <c:forEach var="i" begin="1" end="${totalPages}">
                <li class="page-item">
                    <form action="${url}?page=${i}" method="POST" style="display:inline-block;">
                        <input class="form-control input-sm" name="filter" type="hidden"
                               value="${requestScope.filter}"/>
                        <button type="submit" class="btn ${i == requestScope.page ? 'btn-info btn-sm' : ''}">${i}</button>
                    </form>
                </li>
            </c:forEach>
        </ul>
    </div>
</div>

<c:if test="${error != null}">
    <div id="error" class="alert alert-danger alert-dismissible" role="alert">
            ${error}
        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
            <span aria-hidden="true">&times;</span>
        </button>
    </div>
    <c:remove var="error" scope="session"/>
</c:if>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>

