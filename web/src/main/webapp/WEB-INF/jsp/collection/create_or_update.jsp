<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="x"%>
<x:base title="${action} book collection">
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
        <form:form method="POST" modelAttribute="collection"
            onSubmit="prepareBooks()">
            <div class="form-group">
                <form:label path="name">Name</form:label>
                <form:input path="name" cssClass="form-control" />
                <form:errors path="name" />
            </div>
            <div class="panel panel-default">
                <div class="panel-body">Add books to collection by clicking on them
                    in "Available books" table.</div>
            </div>
            <div class="row">
                <div class="col-xs-6">
                    <div class="panel panel-default" id="available-panel">
                        <div class="panel-heading">
                            <div class="row">
                                <div class="col-xs-6">Available books</div>
                                <div class="col-xs-6">
                                    <%-- TODO filter --%>
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
                </div>
            </div>
            <form:hidden path="bookIds" id="selected-books"/>
            <button type="submit" class="btn btn-default">${action} book collection</button>
        </form:form>
</jsp:attribute>
</x:base>
