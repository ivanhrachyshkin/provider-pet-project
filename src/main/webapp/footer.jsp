<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style type="text/css">
    #footer {
        position: fixed; /* Фиксированное положение */
        rght: 0;
        bottom: 0; /* Правый нижний угол */
        padding: 10px; /* Поля вокруг текста */
        width: 100%; /* Ширина слоя */
    }
</style>

<div id="footer">
    <div class="container text-right">
        <nav aria-label="pagination">
            <ul class="pagination">
                <c:forEach var="i" begin="1" end="${totalPages}">
                    <li class="page-item"><a href="?page=${i}">${i}</a></li>
                </c:forEach>
            </ul>
        </nav>
    </div>
</div>

<style type="text/css">
    #error {
        position: fixed; /* Фиксированное положение */
        left: 0;
        bottom: 0; /* Правый нижний угол */
        padding-top: 10px; /* Поля вокруг текста */
        width: 20%; /* Ширина слоя */
    }
</style>


<c:if test="${error != null}">
    <div id="error">
        <div class="alert alert-danger" role="alert">
                ${error}</div>
    </div>
</c:if>