<!DOCTYPE html 
     PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
     "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="html_head.jsp" %>


<% String formSubmitText = "Create"; %>
<form:form method="post" commandName="user">
<% 
	if( request.getParameter("action") != null && request.getParameter("action").equals("update") ) { 
		formSubmitText = "Update";	
%>
		<input type="hidden" name="action" value="update"/>
<% } %>
	<div class="left_col">
			<p>Username: <form:input path="username" /></p>
			<p>Passowrd: <form:password path="password" /></p>
    </div>
	<div class="right_col">
	   <a href="#" onclick="document.getElementById( 'user' ).submit(); return;"><%= formSubmitText %></a>
        <br/>
	   <a href="<%= W_PREFIX %>users" title="Cancel">Cancel</a>
    </div>
</form:form>
<div style="clear:both;">&nbsp;</div>
 
<%@ include file="html_footer.jsp" %>