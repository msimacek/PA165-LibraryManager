<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="x" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<x:base title="Create new loan">
<jsp:attribute name="content">
<form method="GET" action="${pageContext.request.contextPath}/loans/find_book">
    <div class="form-group">
        <label for="book">Select book:</label>
        <div class="row">
            <div class="col-xs-4">
                <input id="book" class="form-control" type="text" placeholder="No item selected" value="${book.name}" readonly/>
            </div>
            <div class="col-xs-6">
                <div class="input-group">
                    <input id="book" type="text" class="form-control" name="book" placeholder="Search books"/>
                    <input id="member" type="text" name="member" hidden="true" value="${member.id}"/>
                    <span class="input-group-btn">
                        <button type="submit" class="btn btn-default">
                            <span class="glyphicon glyphicon-search"></span>
                        </button>
                    </span>
                </div>
            </div>
        </div>
    </div>
</form>
<form method="GET" action="${pageContext.request.contextPath}/loans/find_member">
    <div class="form-group">
        <label for="member">Select member:</label>
        <div class="row">
            <div class="col-xs-4">
                <input id="member" class="form-control" type="text" placeholder="No item selected" value="${member.email}" readonly/>
            </div>
            <div class="col-xs-6">
                <div class="input-group">
                    <input id="member" type="text" class="form-control" name="member" placeholder="Search members"/>
                    <input id="book" name="book" type="hidden" value="${book.id}"/>
                    <span class="input-group-btn">
                        <button type="submit" class="btn btn-default">
                            <span class="glyphicon glyphicon-search"></span>
                        </button>
                    </span>
                </div>
            </div>
        </div>
    </div>
</form>
<form:form method="POST" action="${pageContext.request.contextPath}/loans/create" modelAttribute="createLoan">
    <div>
        <input id="memberId" name="memberId" type="hidden" value="${member.id}"/>
        <input id="bookId" name="bookId" type="hidden" value="${book.id}"/>
    </div>
    <button type="submit" class="btn btn-default">Create</button>
    </form:form>
    </jsp:attribute>
    </x:base>
