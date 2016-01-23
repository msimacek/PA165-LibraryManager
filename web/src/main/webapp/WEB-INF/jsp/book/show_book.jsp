<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="x"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<x:base>
    <jsp:attribute name="title">
        <c:out value="${book.name}" /> - book detail
    </jsp:attribute>
    <jsp:attribute name="content">
        <table class="table table-default">
            <tr>
                <td class="key">Author name</td>
                <td><c:out value="${book.authorName}" /></td>
            </tr>
            <tr>
                <td class="key">ISBN</td>
                <td><c:out value="${book.isbn}" /></td>
            </tr>
            <tr>
                <td class="key">Current state</td>
                <td><c:out value="${book.state.value}" /></td>
            </tr>
            <tr>
                <td class="key">Presently loaned</td>
                <td><c:out value="${book.loaned? 'Yes': 'No'}" /></td>
            </tr>
        </table>
        <sec:authorize access="hasRole('ADMIN')">
            <div class="panel panel-default">
                <div class="panel-heading">Loan history</div>
                <x:loanTable loans="${book.loans}" showMember="true" showReturn="true"/>
            </div>
            <form action="delete/${book.id}" method="POST" style="display:inline"
                    onSubmit="return confirm('Are you sure you want to permanently delete the book?')">
                <button class="btn btn-default">Delete book</button>
            </form>
        </sec:authorize>
    </jsp:attribute>
</x:base>
