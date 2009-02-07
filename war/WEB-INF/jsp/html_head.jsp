<!DOCTYPE html 
     PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
     "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="com.amb.wikishare.domain.User"%>
<%@page import="com.amb.wikishare.helper.WikiShareHelper"%>
<%@page import="com.amb.wikishare.service.UserService"%>
<%
	// Remote user representaion
	User USER = UserService.getSessionUser(request);
	
	// Application context parameter, something like /WikiShare/wiki/
	String W_PREFIX = request.getContextPath() + getServletContext().getInitParameter("webappPrefix");
	
	// Search text parameter
	String searchText = request.getParameter("search_text");
	if( searchText == null) {
		searchText = "";
	}
%>
<html> 
<head>
<title><fmt:message key="webapp" /></title>
<link href="<%= request.getContextPath() %>/public/css/main.css" rel="stylesheet" type="text/css"/>
</head>

<body style="padding:0; margin:0;">

<div id="main_navigation">
	<a href="<%= W_PREFIX %>frontpage">Frontpage</a> 
	<a href="<%= W_PREFIX %>wikipages">Wikipages</a> 
	<a href="<%= W_PREFIX %>files">Files</a> 
	<a href="<%= W_PREFIX %>users">Users</a>
	<div id="login_box">
		<% if(USER != null) {  %>
			<%= USER.getUsername() %><br/> 
			<a href="<%= W_PREFIX %>wikipages?action=logout">Logout</a>
		<% } else { %>
			<a href="<%= W_PREFIX %>login" title="Login">Login</a>
		<% } %>
	</div> 
	<form action="<%= W_PREFIX %>search" method="get">
		<input type="text" name="search_text" value="<%= searchText %>"/>
		<input type="submit" value="Search" />
	</form>
</div>

<div id="wikipage_content">