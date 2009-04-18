<%@ page session="true"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 

<%@page import="com.amb.wikishare.domain.User"%>
<%@page import="com.amb.wikishare.helper.WikiShareHelper"%>
<%@page import="com.amb.wikishare.service.UserService"%>
<%
	// Remote user representaion
	User USER = UserService.getSessionUser(request);
	
	// Application context parameter, something like /WikiShare/wiki/
	String W_PREFIX = request.getContextPath() + 
	   getServletContext().getInitParameter("webappPrefix");
	
	// Search text parameter
	String searchText = request.getParameter("search_text");
	if( searchText == null) {
		searchText = "";
	}
%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en"> 
<head>
<title><fmt:message key="webapp" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="description" content="WikiShare 0.1" />
<meta name="author" content="Adam Musial-Bright" />
<link href="<%= request.getContextPath() %>/public/wiki/css/main.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="/WikiShare/public/wiki/js/prototype.js"></script>
<script type="text/javascript" src="/WikiShare/public/wiki/js/scriptaculous/scriptaculous.js"></script>
<script type="text/javascript" src="/WikiShare/public/wiki/js/text.js"></script>
</head>

<body>

<div id="main_navigation">
	<a href="<%= W_PREFIX %>frontpage">Frontpage</a>
<% if(USER == null) {  %> 
	<a href="<%= W_PREFIX %>login" title="Login">Login</a>
<% } %>
<% if(USER != null) {  %> 
	<a href="<%= W_PREFIX %>wikipage_create">New Page</a>
	<a href="<%= W_PREFIX %>wikipages">Wikipages</a> 
	<a href="<%= W_PREFIX %>files">Files</a> 
	<a href="<%= W_PREFIX %>users">Users</a>
	<form class="search_form" action="<%= W_PREFIX %>search" method="get">
		<input type="text" id="search_text" name="search_text" value="<%= searchText %>" autocomplete="on"/>
	</form>
	<div id="login_box">
		<%= USER.getUsername() %><br/> 
		<a style="padding:0;" href="<%= W_PREFIX %>wikipages?action=logout">logout</a>
	</div> 
<% } %>
</div>

<div id="ajax_search_indicator" style="display:none;position:absolute;left:500px;">NOW FETCHING RESULTS</div>
<div id="ajax_search_result_box" style="display:none;border:1px solid black;background-color:white;position:absolute;left:500px;"></div>
<script type="text/javascript" language="javascript" charset="utf-8">
// <![CDATA[
  new Ajax.Autocompleter('search_text','ajax_search_result_box','<%= W_PREFIX %>ajax_search', { 
    tokens: ',', indicator: 'ajax_search_indicator'
  } );
// ]]>
</script>

<div id="wikipage_content">