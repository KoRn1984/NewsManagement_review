<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="body-title">
	<a href="controller?command=go_to_news_list">News >> </a>News List
</div>
<form action="controller" method="post">
    <c:if test="${sessionScope.register_user eq 'not_registered'}">
		<c:import url="/WEB-INF/pages/tiles/registration.jsp" />
	</c:if>		     
	<c:if test="${sessionScope.register_user eq 'registered'}">
		<center><font color="green">Registration completed successfully!</font></center>
	</c:if>				     
	<c:if test="${sessionScope.addNews eq 'command_executed'}">
	    <center><font color="blue">Data saved successfully!</font></center>
	</c:if>
	<c:if test="${sessionScope.editNews eq 'command_executed'}">
	    <center><font color="orange">Data updated successfully!</font></center>
	</c:if>
	<c:if test="${sessionScope.deleteNews eq 'command_executed'}">
	    <center><font color="grey">Data deleted successfully!</font></center>
	</c:if>	
<form action="controller" method="post">
	<c:forEach var="news" items="${requestScope.news}">
		<div class="single-news-wrapper">
			<div class="single-news-header-wrapper">			
				<div class="news-title">
				<strong>				
					<c:out value="${news.title}" />
				</strong>			
				</div>							
				<div class="news-date">
					<c:out value="${news.date}" />
				</div>
				<div class="news-content">
					<c:out value="${news.brief}" />
				</div>
				<div class="news-link-to-wrapper">
					<div class="link-position">
						<c:if test="${sessionScope.role eq 'admin'}">
						      <a href="controller?command=go_to_edit_news_page&id=${news.id}">Edit</a> 
						</c:if>						
						<a href="controller?command=go_to_view_news&id=${news.id}">View</a>   					    
   					    <c:if test="${sessionScope.role eq 'admin'}">   					    
   					         <input type="checkbox" name="id" value="${news.id}" />
   					         <input type="hidden" name="command" value="do_delete_news" />
   					    </c:if>
					</div>					
				</div>
			</div>
		</div>
	</c:forEach>
	<logic:notEmpty name="newsForm" property="newsList">
		<div class="delete-button-position">
			<html:submit>
				<bean:message key="locale.newslink.deletebutton" />
				<input type="submit" value="Delete" />
			</html:submit>		                
		</div>
	</logic:notEmpty>
</form>
	<c:if test="${sessionScope.showNews eq 'not_show'}">
	<div class="no-news">
		<c:if test="${requestScope.news eq null}">
		<font color="red">
        No news for unregistered user!
        </font>
	    </c:if>
	</div>
	</c:if>	
</form>