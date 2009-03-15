<!DOCTYPE html 
     PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
     "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="html_head.jsp" %>

<% 
String paramUsername = request.getParameter(WikiShareHelper.USERNAME);
if(paramUsername == null) { paramUsername = ""; }
%>
<form id="login_form" name="login_form" method="post" action="<%= W_PREFIX %>frontpage">
	<fieldset>
		<legend>Login</legend>
		<input type="hidden" name="action" value="login"/>
		<p>Username <input name="username" type="text" value="<%= paramUsername %>" tabindex="1"/></p>
		<p>Password <input name="password" type="password" tabindex="2"/></p>
		<input type="submit" value="Login"/>
	</fieldset>
</form>

<script language="javascript" type="text/javascript">
<!--
	document.login_form.username.focus();
//-->
</script>

<%@ include file="html_footer.jsp" %>