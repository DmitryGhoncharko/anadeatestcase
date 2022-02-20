<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Menu page</title>
</head>
<body>
<div class="container-fluid">
    <div class="row">
        <div class="col-md-12">
            <div class="row">
                <div class="col-md-12">
            <jsp:include page="header.jsp"></jsp:include>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <h3 class="text-center">
                        Published pages
                    </h3>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <table class="table table-bordered">
                        <thead>
                        <tr>
                            <th>
                                Id
                            </th>
                            <th>
                                Label
                            </th>
                            <th>
                                Priority
                            </th>
                            <th>
                                Date published
                            </th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="publishedPage" items="${requestScope.publishedPages}">
                        <tr>
                            <td>
                                ${publishedPage.Id}
                            </td>
                            <td>
                                ${publishedPage.MenuLabel}
                            </td>
                            <td>
                               ${publishedPage.Priority}
                            </td>
                            <td>
                                ${publishedPage.DatePublished}
                            </td>
                        </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
