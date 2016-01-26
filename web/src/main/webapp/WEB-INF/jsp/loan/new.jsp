<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="x" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<x:base title="Create new loan">
    <jsp:attribute name="head">
        <script>
            function selectRow(row) {
                row = $(row);
                if (row.parents("#available-table").length > 0) {
                    row.detach().appendTo("#selected-table tbody");
                } else {
                    row.detach().appendTo("#available-table tbody");
                }
            }
            function prepareBooks() {
                var data = $("#selected-table tbody tr").get().map(
                        function(x) {
                            return x.getAttribute("data-book-id");
                        });
                $("#selected-books").val(data.join(","));
            }
            $(function() {
                var selected = $("#selected-books").val().split(',');
                $("#available-table tbody tr").each(function() {
                    if ($.inArray(this.getAttribute("data-book-id"), selected) >= 0) {
                        selectRow(this);
                    }
                });
            });
        </script>
    </jsp:attribute>
<jsp:attribute name="content">
<form method="GET" action="${pageContext.request.contextPath}/loans/find_member">
    <div class="form-group">
        <label for="member">Select member:</label>
        <div class="row">
            <div class="col-xs-4">
                <input id="member" class="form-control" type="text" placeholder="No item selected" value="${member.email}" readonly/>
                        <form:errors path="createLoan.memberId"/>
            </div>
            <div class="col-xs-6">
                <div class="input-group">
                    <input id="member" type="text" class="form-control" name="member" placeholder="Search members"/>
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
<form:form method="POST" action="${pageContext.request.contextPath}/loans/create" modelAttribute="createLoan"
onSubmit="prepareBooks()">
    <div>
        <form:hidden path="memberId"/>
                    <form:hidden path="bookId" id="selected-books"/>
    </div>
    <button type="submit" class="btn btn-default">Create</button>
    </form:form>
<form>
<div class="panel panel-default">
                <div class="panel-body">Add books to loan by clicking on them
                    in "Available books" table.</div>
            </div>
            <div class="row">
                <div class="col-xs-6">
                    <div class="panel panel-default" id="available-panel">
                        <div class="panel-heading">
                            <div class="row">
                                <div class="col-xs-6">Available books</div>
                                <div class="col-xs-6">
                                    <div class="input-group">
                                        <input type="text" id="filter" placeholder="Filter" class="form-control" />
                                        <span class="input-group-addon"> <i
                                            class="glyphicon glyphicon-filter"> </i>
                                        </span>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <table class="table filtered" id="available-table">
                            <thead>
                                <tr>
                                    <th>Name</th>
                                    <th>Author</th>
                                    <th>ISBN</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="book" items="${allBooks}">
                                    <tr onClick="selectRow(this)"
                                        data-book-id="${book.id}">
                                        <td><c:out value="${book.name}" /></td>
                                        <td><c:out value="${book.authorName}" /></td>
                                        <td><c:out value="${book.isbn}" /></td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="col-xs-6">
                    <div class="panel panel-default" id="selected-panel">
                        <div class="panel-heading">Selected books</div>
                        <table class="table" id="selected-table">
                            <thead>
                                <tr>
                                    <th>Name</th>
                                    <th>Author</th>
                                    <th>ISBN</th>
                                </tr>
                            </thead>
                            <tbody></tbody>
                        </table>
                    </div>
                                                    <form:errors path="createLoan.bookId"/>
                </div>
            </div>
</form>

    </jsp:attribute>
    </x:base>
