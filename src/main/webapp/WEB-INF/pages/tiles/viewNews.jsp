<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" var="loc" />    
<fmt:message bundle="${loc}" key="local.loc.name.newses" var="newses" />
<fmt:message bundle="${loc}" key="local.loc.name.newsView" var="news_view" />
<fmt:message bundle="${loc}" key="local.loc.name.title" var="title" />
<fmt:message bundle="${loc}" key="local.loc.name.date" var="date" />
<fmt:message bundle="${loc}" key="local.loc.name.brief" var="brief" />
<fmt:message bundle="${loc}" key="local.loc.name.content" var="content" />
<fmt:message bundle="${loc}" key="local.loc.name.edit" var="edit" />
<fmt:message bundle="${loc}" key="local.loc.name.delete" var="delete" />
<fmt:message bundle="${loc}" key="local.loc.name.cancel" var="cancel" />

<div class="body-title">
	<a href="controller?command=go_to_news_list&local=${local}">${newses} >> </a>${news_view}
</div>
<div class="add-table-margin">
	<table class="news_text_format">
		<tr>
			<td class="space_around_title_text">${title}:</td>
			<td class="space_around_view_text">
			<div class="word-breaker">
			<strong>
				<c:out value="${requestScope.news.title}" />
			</strong>
			</div>
			</td>
		</tr>
		<tr>
			<td class="space_around_title_text">${date}:</td>
			<td class="space_around_view_text">
			<div class="word-breaker">
				<c:out value="${requestScope.news.date}" />
			</div>
			</td>
		</tr>
		<tr>
			<td class="space_around_title_text">${brief}:</td>
			<td class="space_around_view_text">
			<div class="word-breaker">
				<c:out value="${requestScope.news.brief}" />
			</div>
			</td>
		</tr>
		<tr>
			<td class="space_around_title_text">${content}:</td>
			<td class="space_around_view_text">
			<div class="word-breaker">
				<c:out value="${requestScope.news.content}" />
			</div>
			</td>
		</tr>
	</table>
</div>
<c:if test="${sessionScope.role eq 'admin'}">
<div class="first-view-button">
	<form action="controller" method="post">
		<input type="hidden" name="command" value="go_to_edit_news_page" />
		<input type="hidden" name="id" value="${news.id}" />
		<input type="hidden" name="local" value="${local}" />
		<input type="submit" value="${edit}" />
	</form>
</div>
<div class="second-view-button">
	<form action="controller" method="post">
		<input type="hidden" name="command" value="do_delete_news" />
		<input type="hidden" name="id" value="${news.id}" />
		<input type="hidden" name="local" value="${local}" />
		<input type="submit" value="${delete}" />
	</form>
</div>
</c:if>
<br /><br />
<div class="cancel-button">
		<form action="controller" method="post">
		     <input type="hidden" name="command" value="go_to_news_list" />
		     <input type="hidden" name="local" value="${local}" />
		     <input type="submit" value="${cancel}" />
		</form>
</div>