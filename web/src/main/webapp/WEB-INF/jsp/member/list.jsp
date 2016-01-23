<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="x"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<x:base>
    <jsp:attribute name="title">
        Members - list
    </jsp:attribute>
    <jsp:attribute name="content">
        <div cssClass="form-control">

            <a href="create" class="btn btn-default">Create member</a>

        </div>
        <h1>Members</h1>
        
        <div class="panel panel-default">
            <x:filter/>
            <table class="table filtered">
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>Email</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${members}" var="member">
                        <c:set var="name">
                            <c:out value="${member.givenName}" />
                            <c:out value="${member.surname}" />
                        </c:set>
                        <tr>
                            <td><a href="${member.id}"><c:out value="${name}" /></a></td>
                            <td><c:out value="${member.email}" /></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>


        

    </jsp:attribute>
</x:base>