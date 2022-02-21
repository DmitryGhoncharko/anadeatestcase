
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<script type="text/javascript">
    <%@include file="/WEB-INF/editor/ed.js"%>
</script>
<style>
    <%@include file="/WEB-INF/editor/styles.css"%>
</style>
<c:if test="${not empty requestScope.page}">
<head>
    <title>${requestScope.page.title}</title>
    <meta name="${requestScope.page.desription}">
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
            <c:if test="${not empty requestScope.fail}">
                <div class="row">
                    <div class="col-md-12">
                        <div class="alert alert-dismissable alert-danger" style="width: 100%">
                            <strong>Fail!</strong> ${requestScope.fail}
                        </div>
                    </div>
                </div>
            </c:if>
        </div>
    </div>
</div>
<h1>${requestScope.page.h1}</h1>
    ${requestScope.page.content}
</body>
</c:if>
</html>
