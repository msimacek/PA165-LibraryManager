<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="x" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<x:base title="Books search result">
    <jsp:attribute name="content">

        <table class="table">
            <thead>
            <tr>
                <th>Id</th>
                <th>Name</th>
                <th>Author</th>
                <th>Isbn</th>
                <th>Book state</th>
                <th></th>
            </tr>
            </thead>

            <tbody>
            <c:forEach items="${books}" var="book">
                <tr>
                    <td>${book.id}</td>
                    <td><c:out value="${book.name}"/></td>
                    <td><c:out value="${book.authorName}"/></td>
                    <td><c:out value="${book.isbn}"/></td>
                    <td><c:choose>
                        <c:when test="${book.state.getValue() eq 'new'}">
                            new
                        </c:when>
                        <c:when test="${book.state.getValue() eq 'light_damage'}">
                            light damage
                        </c:when>
                        <c:when test="${book.state.getValue() eq 'medium_damage'}">
                            medium damage
                        </c:when>
                        <c:when test="${book.state.getValue() eq 'heavy_damage'}">
                            heavy damage
                        </c:when>
                        <c:when test="${book.state.getValue() eq 'removed'}">
                            removed
                        </c:when>
                        <c:otherwise>
                            -
                        </c:otherwise>
                    </c:choose>
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${book.loaned}">
                                Already loaned
                            </c:when>
                            <c:otherwise>
                                <a href="${pageContext.request.contextPath}/loans/new?bookId=${book.id}&memberId=${param.member}"
                                    class="btn btn-default">Choose</a>
                            </c:otherwise>
                        </c:choose>
                    <td>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
</jsp:attribute>
</x:base>