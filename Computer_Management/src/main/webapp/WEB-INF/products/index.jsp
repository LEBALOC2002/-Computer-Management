<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="vi">

<head>
    <title>DarkPan - Bootstrap 5 Admin Template</title>
    <jsp:include page="/WEB-INF/layout/headerHtml.jsp"></jsp:include>
</head>

<body>
<div class="container-fluid position-relative d-flex p-0">
    <!-- Spinner Start -->
    <div id="spinner"
         class="show bg-dark position-fixed translate-middle w-100 vh-100 top-50 start-50 d-flex align-items-center justify-content-center">
        <div class="spinner-border text-primary" style="width: 3rem; height: 3rem;" role="status">
            <span class="sr-only">Loading...</span>
        </div>
    </div>
    <!-- Spinner End -->


    <jsp:include page="/WEB-INF/layout/sidebar.jsp"></jsp:include>


    <!-- Content Start -->
    <div class="content">
        <!-- Navbar Start -->
        <jsp:include page="/WEB-INF/layout/header.jsp"></jsp:include>
        <!-- Navbar End -->

        <!-- Recent Sales Start -->

        <div class="col-sm-6">
            <form action="product" style="padding: 10px;">
                <input style="padding: 7px; border-radius: 10px" placeholder="search" type="text" hint="search"
                       value="${requestScope.q}" name="q"> Category:
                <select name="category_id" id="">
                    <option value="-1" style="padding: 7px; border-radius: 10px">All</option>

                    <c:forEach items="${applicationScope.listCategory}" var="category">

                        <c:choose>
                            <c:when test="${category.getId() == requestScope.idcategory}">
                                <option selected value="${category.getId()}">${category.getName()}</option>
                            </c:when>
                            <c:otherwise>
                                <option value="${category.getId()}">${category.getName()}</option>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </select>
                <button type="submit" class="btn btn-primary"><span class="fa fa-search"></span>Search</button>
            </form>
        </div>
        <div class="bg-secondary text-center rounded p-4">
            <div class="d-flex align-items-center justify-content-between mb-4">
                <h6 class="mb-0">Computer</h6>

                <div style="float: left; justify-content: space-between;">
                    <a href="/product?action=create" type="button" class="btn btn-success w-21;">Create</a>
                </div>
            </div>
            <div class="table-responsive">
                <table class="table text-start align-middle table-bordered table-hover mb-0">
                    <thead>
                    <tr class="text-white">
                        <th scope="col">image</th>
                        <th scope="col" class="text-center">Title</th>
                        <th scope="col-4" class="text-center">Price</th>
                        <th scope="col" class="text-center">Quanity</th>
                        <th scope="col" class="text-center">description</th>
                        <th scope="col" class="text-center">Category</th>
                        <th scope="col" colspan="2" class="text-center">Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    <%--                                <tr>--%>
                    <%--                                    <td>01 Jan 2045</td>--%>
                    <%--                                    <td>INV-0123</td>--%>
                    <%--                                    <td>Jhon Doe</td>--%>
                    <%--                                    <td>$123</td>--%>
                    <%--                                    <td>Paid</td>--%>
                    <%--                                    <td class="text-center">--%>
                    <%--                                        <a href="/products?action=edit" class="btn btn-secondary"><i--%>
                    <%--                                                class="far fa-edit"></i>Edit</a>--%>
                    <%--                                    </td>--%>
                    <%--                                    <td class="text-center"><button type="button" class="btn btn-warning"><i--%>
                    <%--                                                class="far fa-trash-alt"></i>Delete</button>--%>
                    <%--                                    </td>--%>
                    <%--                                </tr>--%>
                    <c:forEach var="product" items="${requestScope.listProduct}">
                        <tr>
                            <td>
                                <img src="${product.getImages()}" alt="images product"
                                     style="width: 150px;height: 100px;"/>
                            </td>
                            <td>${product.getTitle()}</td>
                            <td><fmt:formatNumber
                             type="number" maxFractionDigits="3"
                                                  value="${product.getPrice()}"/></td>
                            <td>${product.getQuantity()}</td>
                            <td>${product.getDescription()}</td>
                            <td>
                                <c:forEach items="${applicationScope.listCategory}" var="category">
                                    <c:if test="${category.getId()==product.getIdcategory()}">
                                        ${category.getName()}
                                    </c:if>
                                </c:forEach>
                            </td>
                            <td class="text-center">
                                <a href="/product?action=edit&id=${product.getId()}" class="btn btn-secondary"><i
                                        class="far fa-edit"></i>Edit</a>
                            </td>
                            <td class="text-center">
                                <a href="/product?action=delete&id=${product.getId()}" class="btn btn-warning"
                                   onclick="return confirm('Bạn Có Chắc Chắn Muốn Xóa Sản Phẩm Này Không?')"><i
                                        class="far fa-trash-alt"></i>Delete
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
        <nav class=col-sm-12 style="display: flex; justify-content: center" aria-label="Page navigation example"
             style="position: relative; left: 500px;">
            <ul class="pagination">
                <c:if test="${requestScope.currentPage != 1}">
                    <li class="page-item ">
                        <a class="page-link"
                           href="product?page=${requestScope.currentPage - 1}&q=${requestScope.q}&category_id=${requestScope.category_id}"
                           style="background-color: aquamarine">Back</a>
                    </li>
                </c:if>


                <c:forEach begin="1" end="${noOfPages}" var="i">
                    <c:choose>
                        <c:when test="${requestScope.currentPage eq i}">
                            <li class="page-item"><a class="page-link"
                                                     href="product?page=${i}&q=${requestScope.q}&category_id=${requestScope.category_id}">${i}</a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="page-item ">
                                <a class="page-link"
                                   href="product?page=${i}&q=${requestScope.q}&category_id=${requestScope.category_id}">${i}</a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>


                <c:if test="${requestScope.currentPage lt requestScope.noOfPages}">
                    <li class="page-item ">
                        <a class="page-link"
                           href="product?page=${requestScope.currentPage + 1}&q=${requestScope.q}&category_id=${requestScope.category_id}"
                           style="background-color: aquamarine">Next</a>
                    </li>
                </c:if>
            </ul>
        </nav>
    </div>
    <jsp:include page="/WEB-INF/layout/scriptHtml.jsp"></jsp:include>
</div>
</div>
</body>

</html>