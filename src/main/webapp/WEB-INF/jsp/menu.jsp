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
                                ${publishedPage.id}
                            </td>
                            <td>
                                <a href="${publishedPage.slug}" class="btn btn-primary">
                                        ${publishedPage.menuLabel}
                                </a>

                            </td>
                            <td>
                               ${publishedPage.priority}
                            </td>
                            <td>
                                ${publishedPage.publishedAt}
                            </td>
                        </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <div class="dropdown">
                <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    Show  unpublished pages
                </button>
                <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                    <table class="table table-bordered dropdown-item">
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
                        <c:forEach var="notPublishedPage" items="${requestScope.notPublishedPages}">
                            <tr>
                                <td>
                                        ${notPublishedPage.id}
                                </td>
                                <td>
                                    <a href="${notPublishedPage.slug}" class="btn btn-primary">
                                            ${notPublishedPage.menuLabel}
                                    </a>

                                </td>
                                <td>
                                        ${notPublishedPage.priority}
                                </td>
                                <td>
                                        ${notPublishedPage.publishedAt}
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
