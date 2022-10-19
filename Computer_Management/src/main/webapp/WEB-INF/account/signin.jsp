<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <title>DarkPan - Bootstrap 5 Admin Template</title>
    <jsp:include page="/WEB-INF/layout/headerHtml.jsp"></jsp:include>
</head>

<body>
<div class="container-fluid position-relative d-flex p-0">
    <div id="spinner"
         class="show bg-dark position-fixed translate-middle w-100 vh-100 top-50 start-50 d-flex align-items-center justify-content-center">
        <div class="spinner-border text-primary" style="width: 3rem; height: 3rem;" role="status">
            <span class="sr-only">Loading...</span>
        </div>
    </div>
    <div class="container-fluid">
        <div class="row h-100 align-items-center justify-content-center" style="min-height: 100vh;">
            <div class="col-12 col-sm-8">
                <div class="bg-secondary rounded p-4 p-sm-5 my-4 mx-3">
                    <form action="/login?action=list" method="post">
                        <div class="d-flex align-items-center justify-content-between mb-3">
                            <a href="index.jsp" class="">
                                <h3 class="text-primary"><i class="fa fa-user-edit me-2"></i>Lê Bá Lộc</h3>
                            </a>
                            <h3>Sign In</h3>
                        </div>
                        <div class="form-floating mb-3">
                            <input type="email" class="form-control" id="email" name="email" placeholder="name@example.com">
                            <label for="email">Email address</label>
                        </div>
                        <div class="form-floating mb-4">
                            <input type="password" class="form-control" id="password" name="password" placeholder="Password">
                            <label for="password">Password</label>
                        </div>
                        <button type="submit" class="btn btn-primary py-3 w-100 mb-4">Sign In</button>
                    </form>
                </div>
                <div style="color: #0d8226" class="text-center"> <h2>${message}</h2></div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="/WEB-INF/layout/scriptHtml.jsp"></jsp:include>
</body>

</html>