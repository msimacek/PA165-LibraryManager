<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="x" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<x:base title="Loan listing">
    <jsp:attribute name="content">
    <c:if test="${member.isAdmin()}">
        <a href="${pageContext.request.contextPath}/loans/new" class="btn btn-default">New loan</a>
    </c:if>
    <x:loanTable loans="${loans}" showMember="true" showBook="true" showReturn="true"/>
</jsp:attribute>
</x:base>