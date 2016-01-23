<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="title"%>
<%@ attribute name="head" fragment="true"%>
<%@ attribute name="content" fragment="true" required="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<sec:authorize var="loggedIn" access="isAuthenticated()" />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel='stylesheet' href='${contextPath}/webjars/bootstrap/3.3.6/css/bootstrap.min.css'>
<link rel='stylesheet' href='${contextPath}/webjars/patternfly/2.8.0/dist/css/patternfly.min.css'>
<link rel='stylesheet' href='${contextPath}/webjars/patternfly/2.8.0/dist/css/patternfly-additions.min.css'>
<link rel='stylesheet' href='http://fonts.googleapis.com/css?family=Open+Sans:300&subset=latin,latin-ext'/>
<script src="${contextPath}/webjars/jquery/2.1.4/jquery.min.js"></script>
<script src='${contextPath}/webjars/bootstrap/3.3.6/js/bootstrap.min.js'></script>
<script>
    $(function () {
        var inputField = $("#filter")
        if (inputField.length == 0) return;
        var initialVal = /filter=([^&]*)/.exec(location.search)
        if (initialVal) {
            inputField.val(initialVal[1]);
        }
        var filterFn = function () {
            $(".filtered tbody tr").each(function() {
                if ($(this).find("td").text().toLowerCase().indexOf(inputField.val().toLowerCase()) >= 0) {
                    $(this).show();
                } else {
                    $(this).hide();
                }
            });
        }
        inputField.keyup(filterFn);
        filterFn();
    });
</script>
<style>
.key {
    font-weight: bold;
    width: 0;
    white-space: nowrap;
}
</style>
<title>${title}</title>
<jsp:invoke fragment="head" />
</head>
<body>
    <nav class="navbar navbar-default">
        <div class="container">
            <div class="navbar-header">
                <a class="navbar-brand" href="${contextPath}">Library system</a>
            </div>
            <ul class="nav navbar-nav">
                <li><a href="${contextPath}/books/list">Books</a></li>
                <li><a href="${contextPath}/collection/list">Book collections</a></li>
                <sec:authorize access="hasRole('ADMIN')">
                    <li><a href="${contextPath}/loans/list">Loans</a></li>
                    <li><a href="${contextPath}/member/list">Members</a></li>
                </sec:authorize>
                <sec:authorize access="hasRole('USER')">
                    <li><a href="${contextPath}/member/<sec:authentication property="principal.dto.id" />">My profile</a></li>
                </sec:authorize>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <c:choose>
                    <c:when test="${loggedIn}">
                        <li class="navbar-text">Logged in as <sec:authentication property="principal.username" /></li>
                        <li><a href="${contextPath}/logout">Log out</a></li>
                    </c:when>
                    <c:otherwise>
                        <li><a href="${contextPath}/login">Log in</a></li>
                    </c:otherwise>
                </c:choose>

                <li>
                    <form role="search" class="navbar-form" action="<c:url value="/books/list" />">
                        <div class="input-group menu-search">
                            <input name="filter" type="text" class="form-control" placeholder="Search books" /> <span
                                class="input-group-btn">
                                <button type="submit" class="btn btn-default">
                                    <span class="glyphicon glyphicon-search"></span>
                                </button>
                            </span>
                        </div>
                    </form>
                </li>
            </ul>
        </div>
    </nav>
    <div class="container">
        <c:if test="${not empty alert_success}">
            <div class="alert alert-success" role="alert"><c:out value="${alert_success}"/></div>
        </c:if>
        <c:if test="${not empty alert_warning}">
            <div class="alert alert-warning" role="alert"><c:out value="${alert_warning}"/></div>
        </c:if>
        <h1>${title}</h1>
        <jsp:invoke fragment="content" />
    </div>
</body>
</html>
