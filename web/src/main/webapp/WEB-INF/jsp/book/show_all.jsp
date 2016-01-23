<%-- 
    Document   : show_all
    Created on : 16.12.2015, 11:15:36
    Author     : xkubist
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="x"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page import="cz.muni.fi.pa165.dto.BookDTO,java.util.List"%>

<x:base title="Library book listing">
    <jsp:attribute name="content">
        <sec:authorize access="hasRole('ADMIN')">
            <a href="<c:url value="/books/create"/>" class="btn btn-default">Create Book</a>
        </sec:authorize>
        <h1>Books</h1>
        <x:filter/>
        <table class="table table-default filtered">
            <thead>
                <tr>
                    <th>Name</th>
                    <th>Author</th>
                    <th>ISBN</th>
                    <sec:authorize access="hasRole('ADMIN')">
                        <th></th>
                    </sec:authorize>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="book" items="${books}">
                    <tr>
                        <td>
                            <a href="<c:url value="/books/${book.id}"/>"><c:out value="${book.name}" /></a>
                        </td>
                        <td><c:out value="${book.authorName}" /></td>
                        <td><c:out value="${book.isbn}" /></td>
                        <sec:authorize access="hasRole('ADMIN')">
                            <td><a href="<c:url value="/books/update/${book.id}" />" class="btn btn-default">Change state</a></td>
                        </sec:authorize>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        
    </jsp:attribute>
</x:base>
