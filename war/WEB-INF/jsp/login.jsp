<%@ include file="include.jsp" %> 
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="html_head.jsp" %>

<% 
String paramUsername = request.getParameter(WikiShareHelper.USERNAME);
if(paramUsername == null) { paramUsername = ""; }
%>

<form class="content_form" name="loginForm" method="POST" action="<%= W_PREFIX %>frontpage">
<fieldset>
	<legend>Login</legend>
	<input type="hidden" name="action" value="login"/>
	Username <input name="username" type="text" value="<%= paramUsername %>" tabindex="1"/><br/>
	Password <input name="password" type="password" tabindex="2"/><br/>
	<input type="submit" value="Login"/>
</fieldset>
</form>

<script language="javascript">
<!--
	document.loginForm.username.focus();
//-->
</script>
 
<%@ include file="html_footer.jsp" %>