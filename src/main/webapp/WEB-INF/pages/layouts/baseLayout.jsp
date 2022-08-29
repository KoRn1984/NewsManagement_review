<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" var="loc" />    
<fmt:message bundle="${loc}" key="local.loc.name.welcome" var="welcome" />

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="script/validation.js"></script>
<title>News Management</title>
<link rel="stylesheet" type="text/css" href="styles/newsStyle.css">
</head>
<body>
	<div class="page">	    
		<div class="header">
			<c:import url="/WEB-INF/pages/tiles/header.jsp" />
		</div>				
		<div class="base-layout-wrapper">
			<div class="menu">
				<c:if test="${not (sessionScope.user_status eq 'active')}">				
				    <center><h1>${welcome}!</h1></center>
					<%-- <c:import url=""></c:import> --%>
				</c:if>
				<c:if test="${sessionScope.user_status eq 'active'}">
					<c:import url="/WEB-INF/pages/tiles/menu.jsp" />
				</c:if>
			</div>			
		    <div class="content">
		        <c:if test="${not (sessionScope.user_status eq 'active')}"> 
				    <c:import url="/WEB-INF/pages/tiles/guestInfo.jsp" />
				</c:if>				
				<c:if test="${sessionScope.user_status eq 'active'}">
					<c:import url="/WEB-INF/pages/tiles/body.jsp" />
				</c:if>				
               <div class="registration">
		            <c:if test="${sessionScope.register_user eq 'not_registered'}">
		            <c:if test="${sessionScope.commandsName eq 'register'}">
				         <c:import url="/WEB-INF/pages/tiles/registration.jsp" />
				    </c:if>
				    </c:if>				     
		      </div>		      
		      <div class="addNews">
		            <c:if test="${sessionScope.commandsName eq 'addNews'}">
		                <c:import url="/WEB-INF/pages/tiles/addNews.jsp" />	                 
					</c:if>
		      </div>		      
		      <div class="editNews">
		            <c:if test="${sessionScope.commandsName eq 'editNews'}">
		                <c:import url="/WEB-INF/pages/tiles/editNews.jsp" />
	                </c:if>			     
		      </div>		      
		     </div>				
		</div>
		<div class="footer">
			<c:import url="/WEB-INF/pages/tiles/footer.jsp" />
		</div>		
	</div>
</body>
</html>