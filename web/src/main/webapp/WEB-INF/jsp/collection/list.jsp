<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="x"%>
<x:base title="Book collection listing">
    <jsp:attribute name="content">
        
        <sec:authorize access="hasRole('ADMIN')">
            <a href="<c:url value="/collection/create"/>" class="btn btn-default">Create book collection</a>
        </sec:authorize>
        
        <h1>
            Book collection listing
        </h1>
        
        <div class="panel panel-default">
            <table class="table">
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>Book count</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="collection" items="${collections}">
                        <tr>
                            <td>
                                <a href="${collection.id}">
                                    <c:out value="${collection.name}" />
                                </a>
                            </td>
                            <td><c:out value="${collection.books.size()}" /></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        
    </jsp:attribute>
</x:base>