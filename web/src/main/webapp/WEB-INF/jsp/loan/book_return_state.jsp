<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="x"%>
<%@ page import="cz.muni.fi.pa165.enums.BookState" %>

<x:base>
  <jsp:attribute name="title">Return book - <c:out value="${loan.book.name}" /></jsp:attribute>
  <jsp:attribute name="content">
      <form method="POST">
        <div class="input-group">
          <label for="state">Choose state in which book was returned</label>
          <input type="hidden" name="redir" id="redir"/>
          <select name="state" class="form-control">
              <c:forEach var="s" items="${loan.book.possibleStateTransitions}">
                  <option value="${s}">${s.value}</option>
              </c:forEach>
          </select>
        </div>
        <button type="submit" class="btn btn-default">Return</button>
      </form>
  </jsp:attribute>
</x:base>
