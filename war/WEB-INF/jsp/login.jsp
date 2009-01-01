<%@ include file="include.jsp" %> 
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="html_head.jsp" %>
<%@page import="com.amb.wikishare.helper.WikiShareHelper"%>

<% 
String paramUsername = request.getParameter(WikiShareHelper.USERNAME);
if(paramUsername == null) { paramUsername = ""; }
%>

<form name="loginForm" method="POST" action="<%= W_PREFIX %>wikipages">
	<input type="hidden" name="action" value="login"/>
	Username: <input name="username" type="text" value="<%= paramUsername %>" tabindex="1"/>
	<input name="password" type="password" tabindex="2"/>
	<input type="submit" value="Login"/>
</form>

<script language="javascript">
<!--
	document.loginForm.username.focus();
//-->
</script>
 
<%@ include file="html_footer.jsp" %>