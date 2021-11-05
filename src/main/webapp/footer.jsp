<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
    <div id="error">
        <div class="alert alert-danger" role="alert">
                ${error}</div>
    </div>
</c:if>