<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${error != null}">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>

    <div class="container">
        <button type="button" class="btn btn-danger" data-toggle="modal" data-target="#myModal">
            <h5> Exception, click to see message </h5>
        </button>
        <div class="modal" id="myModal">
            <div class="modal-dialog">
                <div class="modal-content">

                            <div class="modal-header">
                                <h4 class="modal-title">Exception message</h4>
                                <button type="button" class="close" data-dismiss="modal">&times;</button>
                            </div>
                            <div class="modal-body">
                                <h2>${error}</h2>
                            </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</c:if>

<nav class="navbar navbar-nav navbar-fixed-bottom">
    <br>
    <footer class="page-footer">
        <div class="footer-copyright text-center">&copy; 2021 Copyright: Ivan Hrachyshkin
        </div>
    </footer>
</nav>