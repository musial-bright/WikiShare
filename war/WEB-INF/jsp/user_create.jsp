<%@ include file="include.jsp" %> 
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="html_head.jsp" %>


<h2><fmt:message key="create-new-user"/></h2>

<% String formSubmitText = "Create"; %>
<form:form method="post" commandName="user">
<% 
	if( request.getParameter("action") != null && request.getParameter("action").equals("update") ) { 
		formSubmitText = "Update";	
%>
		<input type="hidden" name="action" value="update"/>
<% } %>
	<table>
		<tr>
			<td>Username:</td>
			<td><form:input path="username" /></td>
		</tr>
		<tr>
			<td>Passowrd:</td>
			<td><form:input path="password" /></td>
		</tr>
	</table>
	<br>
	<input type="submit" align="center" value="<%= formSubmitText %>"/>
</form:form>
<a href="<%= W_PREFIX %>users" title="Cancel">Cancel</a>
 
<%@ include file="html_footer.jsp" %>