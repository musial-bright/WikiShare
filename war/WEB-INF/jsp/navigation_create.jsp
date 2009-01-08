<%@ include file="include.jsp" %> 
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="html_head.jsp" %>


<h2>Navigation</h2>

<% String formSubmitText = "Create"; %>
<form:form method="post" commandName="navigation">
<% 
	if( request.getParameter("action") != null && request.getParameter("action").equals("update") ) { 
		formSubmitText = "Update";	
%>
		<input type="hidden" name="action" value="update"/>
<% } %>
	<table>
		<tr>
			<td>Navi name:</td>
			<td><form:input path="name" /></td>
		</tr>
		<tr>
			<td>Content:</td>
			<td><form:textarea path="content" cols="30" rows="5" /></td>
		</tr>
	</table>
	<br>
	<input type="submit" align="center" value="<%= formSubmitText %>"/>
</form:form>
<a href="<%= W_PREFIX %>wikipages" title="Cancel">Cancel</a>
 
<%@ include file="html_footer.jsp" %>