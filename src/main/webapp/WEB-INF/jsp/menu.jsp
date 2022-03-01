<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    <%@include file="/WEB-INF/css/checkbox.css"%>
</style>
<html>
<script>
   function showNotPublishedPagesOnpage() {
       if (document.getElementById('dontPublishedPages').getAttribute('hidden')==null) {
           document.getElementById('dontPublishedPages').setAttribute('hidden', 'true')
       }else {
           document.getElementById('dontPublishedPages').removeAttribute('hidden')
       }
   }
</script>
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
                        Pages
                    </h3>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <h1>Show not published pages</h1>
                    <label class="checkbox-green" title="show not published pages">
                        <input type="checkbox" value="0" name="showNotPublishedPagesCheckBox" onclick="showNotPublishedPagesOnpage()">
                        <span class="checkbox-green-switch" data-label-on="On" data-label-off="Off"></span>
                    </label>
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
                        <tbody id="dontPublishedPages" hidden>
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
