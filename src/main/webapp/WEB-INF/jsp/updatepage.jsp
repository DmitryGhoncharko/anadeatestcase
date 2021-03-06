<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<script type="text/javascript">
    <%@include file="/WEB-INF/editor/ed.js"%>
</script>
<style>
    <%@include file="/WEB-INF/editor/styles.css"%>
</style>
<head>
    <title>Create page</title>
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
            <c:if test="${not empty requestScope.pageforupdate}">
            <form role="form" action="/controller?command=updatePage" class="container-fluid" method="post">
                <input hidden name="pageId" value="${requestScope.pageforupdate.id}">
                <div class="row">
                    <div class="col-md-12">
                        <div class="form-group">
                            <label for="pageTitleId" class="text-center" style="padding-left: 50%">
                                Page title
                            </label>
                            <input type="text" name="pageTitleForUpdate" pattern="^.{1,200}$" value="${requestScope.pageforupdate.title}" required class="form-control" id="pageTitleId"/>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <div class="form-group">
                            <label for="pageDescriptionId" class="text-center" style="padding-left: 50%">
                                Page description
                            </label>
                            <textarea  type="text" pattern=".{1,}" name="pageDescriptionForUpdate" required lang="en" class="form-control" style="height: 400px" id="pageDescriptionId">
                                ${requestScope.pageforupdate.description}
                            </textarea>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <div class="form-group">
                            <label for="pageSlugId" class="text-center" style="padding-left: 50%">
                                Page slug
                            </label>
                            <input type="text" value="${pageforupdate.slug}" pattern="^.{1,200}$" name="pageSlugForUpdate" required lang="en" class="form-control" id="pageSlugId"/>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <div class="form-group">
                            <label for="pageMenuLabelId" class="text-center" style="padding-left: 50%">
                                Page menu label
                            </label>
                            <input type="text" value="${requestScope.pageforupdate.menuLabel}" pattern="^.{1,200}$" name="pageMenuLabelForUpdate" required lang="en" class="form-control" id="pageMenuLabelId" />
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <div class="form-group">
                            <label for="pageH1Id" class="text-center" style="padding-left: 50%">
                                Page H1
                            </label>
                            <input type="text" value="${requestScope.pageforupdate.h1}" pattern="^.{1,200}$" name="pageH1ForUpdate" required lang="en" class="form-control" id="pageH1Id" />
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <div class="form-group">
                            <label for="pageContentForCreate" class="text-center" style="padding-left: 50%">
                                Page content
                            </label>
                            <p>
                                <script>edToolbar('pageContentForCreate'); </script>
                                <textarea id="pageContentForCreate" required name="pageContentForUpdate" class="ed" style="width: 100%; height: 400px">
                                    ${requestScope.pageforupdate.content}
                                </textarea>
                            </p>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <div class="form-group">
                            <label for="pageDatePublishedId" class="text-center" style="padding-left: 50%">
                                Page date published
                            </label>
                            <input type="date" value="${requestScope.pageforupdate.publishedAt}" required name="pageDatePublishedForUpdate" class="form-control" id="pageDatePublishedId" />
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <div class="form-group">
                            <label for="pagePriorityId" class="text-center" style="padding-left: 50%">
                                Page priority
                            </label>
                            <input type="number" value="${requestScope.pageforupdate.priority}" max="10" min="1" name="pagePriorityForUpdate" maxlength="2" minlength="1" required class="form-control" id="pagePriorityId" />
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <div class="row">
                            <div class="col-md-6">
                                <button type="submit" class="btn btn-primary" style="padding-left: 50%">
                                    Update page
                                </button>
                            </div>
                            <div class="col-md-6">
                                <a href="/controller?command=menuPage" class="btn btn-primary" style="padding-left: 50%">
                                    Cancel
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </form>
            </c:if>
        </div>
    </div>
</div>
</body>
</html>
