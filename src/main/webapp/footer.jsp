
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