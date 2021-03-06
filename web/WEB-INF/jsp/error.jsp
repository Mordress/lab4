<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="u"%>

<u:html title="ошибка" message="${message}">
<div id="page">
    <c:choose>
        <c:when test="${not empty error}">
            <h2>${error}</h2>
        </c:when>
        <c:when test="${not empty pageContext.errorData.requestURI}">
            <h2>Запрошенная страница ${pageContext.errorData.requestURI} не может быть обработана сервером</h2>
        </c:when>
        <c:otherwise>Непредвиденная ошибка приложения</c:otherwise>
    </c:choose>
    <c:url value="/index.html" var="mainUrl"/>
    <A href="${mainUrl}">На главную</A>
</u:html>
