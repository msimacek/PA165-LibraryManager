<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="x"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<x:base>
    <jsp:attribute name="title">
        <c:out value="${member.givenName}" /> - member detail
    </jsp:attribute>
    <jsp:attribute name="content">
        <h1>
            <c:out value="${member.givenName}" />
        </h1>
        ID:<c:out value="${member.id}" />
        E-mail: <c:out value="${member.email}" /><br/>
        Surname: <c:out value="${member.surname}" /><br/>
        Date of registration: <fmt:formatDate value="${member.registrationDate}" pattern="yyyy-MM-dd"/><br/>

        <div class="panel panel-default">
            <div class="panel-heading">Loans</div>
            <table class="table">
                <thead>
                    <tr>
                        <th>Id</th>
                        <th>Book</th>
                        <th>Loan Date</th>
                        <th>Returned</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${loans}" var="loan">
                        <tr>
                            <td><c:out value="${loan.id}" /></td>
                            <td><c:out value="${loan.book.name}"/></td>
                            <td><fmt:formatDate value="${loan.loanDate}" pattern="yyyy-MM-dd"/></td>
                            <td>${loan.returned ? 'Returned' : 'Loaned'}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </jsp:attribute>
</x:base>