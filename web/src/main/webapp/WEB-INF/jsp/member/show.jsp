<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="x"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<c:set var="name">
    <c:out value="${member.givenName}" />
    <c:out value="${member.surname}" />
</c:set>
<x:base>
    <jsp:attribute name="title">
        ${name} - member detail
    </jsp:attribute>
    <jsp:attribute name="content">
        <table class="table table-default">
            <tr>
                <td class="key">ID</td>
                <td><c:out value="${member.id}" /></td>
            </tr>
            <tr>
                <td class="key">E-mail</td>
                <td><c:out value="${member.email}" /></td>
            </tr>
            <tr>
                <td class="key">Date of registration</td>
                <td><fmt:formatDate value="${member.registrationDate}" pattern="yyyy-MM-dd" /></td>
            </tr>
            <tr>
                <td class="key">Administrator</td>
                <td><c:out value="${member.admin}" /></td>
            </tr>
        </table>

        <div class="panel panel-default">
            <div class="panel-heading">Active loans</div>
            <x:loanTable loans="${activeloans}" showBook="true"/>
        </div>

        <div class="panel panel-default">
            <div class="panel-heading">Returned loans</div>
            <x:loanTable loans="${returnedloans}" showBook="true" showReturn="true"/>
        </div>
        <a href="${member.id}/update" class="btn btn-default">Update member</a>
        <sec:authorize access="hasRole('ADMIN')">
            <a href="${member.id}/delete" class="btn btn-default">Delete member</a>
        </sec:authorize>
    </jsp:attribute>
</x:base>
