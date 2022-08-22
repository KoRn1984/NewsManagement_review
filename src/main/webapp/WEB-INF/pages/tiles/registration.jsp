<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Registration</title>
</head>
<body>
<fieldset><legend><b><center>Enter your details:</center></b></legend>
     <form action="controller" method="post">
			<input type="hidden" name="command" value="do_registration" />
			Enter login:<br/>
			<input type="text" name="login" value="" required pattern="^[A-Za-z]([.A-Za-z0-9-]{1,18})([A-Za-z0-9])$" size=20 maxlength=30/><br/>
			Enter password:<br/>
			<input type="password" name="password" value="" required pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[^\w\s]).{6,}" size=20 maxlength=30/><br/>
			Enter your name:<br/>
			<input type="text" name="name" value="" required pattern="^([А-Я]{1}[а-яё]{1,23}|[A-Z]{1}[a-z]{1,23})$" size=20 maxlength=30/><br/>
			Enter your surname:<br/>
			<input type="text" name="surname" value="" required pattern="^([А-Я]{1}[а-яё]{1,23}|[A-Z]{1}[a-z]{1,23})$" size=20 maxlength=30/><br/>
			Enter your email:<br/>
			<input type="email" name="email" value="" required pattern="([A-zА-я])+([0-9\\-_\\+\\.])*([A-zА-я0-9\\-_\\+\\.])*@([A-zА-я])+([0-9\\-_\\+\\.])*([A-zА-я0-9\\-_\\+\\.])*[\\.]([A-zА-я])+" size=20 maxlength=30/><br/><br/>
			<input class="button" type="submit" value="Register" /><br/>
		</form>
</fieldset>			
</body>
</html>