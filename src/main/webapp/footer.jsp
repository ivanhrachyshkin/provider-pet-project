<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>

<div class="container">
    <nav class="navbar navbar-nav navbar-fixed-bottom">
        <br>
        <footer class="page-footer">
            <div class="row">
                <div class="col-md-4"></div>
                <div class="col-md-4"></div>
                <div class="col-md-4">
                    <c:if test="${error != null}">
                    <div class="alert alert-danger" role="alert">
                            ${error}
                        </c:if>
                    </div>
                </div>
            </div>
        </footer>
    </nav>
</div>