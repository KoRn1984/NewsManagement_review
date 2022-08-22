<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Edit News</title>
<style>
   textarea {
    background: #fce9c0;
    border: 2px solid #a9c358;
    padding: 10px;
    width: 100%;
    height: 200px;
    box-sizing: border-box;
    font-size: 14px;
   }
  </style>
</head>
<body>
<div class="body-title">
	<a href="controller?command=go_to_news_list">News >> </a>News View
</div>
<div class="add-table-margin">
	<table class="news_text_format">
<fieldset><legend><b><center>Enter your text:</center></b></legend>
<form action="controller" method="post">
		<input type="hidden" name="command" value="do_edit_news" />			              						
		<label>News Title:<br />
		<p><textarea type="text" name="title" placeholder="Enter text of title in the field" value="" style="width: 670px; height: 40px;"></textarea></p></label>
		<label>News Brief:<br />
        <p><textarea type="text" name="brief" placeholder="Enter text of brief in the field" value="" style="width: 670px; height: 60px;"></textarea></p></label>				
		<label>News Content:<br />
		<p><textarea type="text" name="content" placeholder="Enter text of content in the field" value="" style="width: 670px; height: 140px;"></textarea></p></label>	
		<br />					
		<input type="submit" value="Save" />
</form>
</fieldset>
</table>
</div>
<div class="cansel-button">
		<form action="controller" method="post">
		     <input type="hidden" name="command" value="go_to_news_list" />
		     <input type="submit" value="Cansel" />
		</form>
</div>
</body>
</html>