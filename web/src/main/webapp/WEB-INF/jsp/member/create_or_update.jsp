<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="x"%>
<x:base title="${action} member">
    <jsp:attribute name="head">
        <script>
            function checkPassword() {
                if ($("#password").val() !== $("#passwordConfirmation").val()) {
                    $("#passwordError").css("display", "inline");
                    return false;
                } else {
                    return true;
                }
            }
        </script>
    </jsp:attribute>
    <jsp:attribute name="content">
        <form:form method="POST" onSubmit="return checkPassword()" modelAttribute="member">
            <div class="form-group">
                <div class="form-group">
                    <form:label path="givenName">Given name</form:label>
                    <form:input path="givenName" type="text" cssClass="form-control" />
                    <form:errors path="givenName" />
                </div>
                <div class="form-group">
                    <form:label path="surname">Surname</form:label>
                    <form:input path="surname" type="text" cssClass="form-control" />
                    <form:errors path="surname" />
                </div>
                <div class="form-group">
                    <form:label path="email">E-mail</form:label>
                    <form:input path="email" type="text" cssClass="form-control" />
                    <form:errors path="email" />
                </div>
                <div class="form-group">
                    <form:label path="password">Password</form:label>
                    <form:input path="password" id="password" type="password" cssClass="form-control" />
                    <form:errors path="password" />
                </div>
                <div class="form-group">
                    <label for="passwordConfirmation">Password confirmation</label>
                    <input id="passwordConfirmation" type="password" class="form-control" />
                    <span id="passwordError" style="display: none">
                        Passwords do not match
                    </span>
                </div>
                <sec:authorize access="hasRole('ADMIN')">
                    <div class="checkbox">
                        <form:label path="admin">
                            <form:checkbox path="admin" /> Should have administrative priviledges
                        </form:label>
                        <form:errors path="admin" />
                    </div>
                </sec:authorize>
            </div>
            <button type="submit" class="btn btn-default">${action} member</button>
        </form:form>
    </jsp:attribute>
</x:base>
