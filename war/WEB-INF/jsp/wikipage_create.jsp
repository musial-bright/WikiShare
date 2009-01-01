<%@ include file="include.jsp" %> 
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="html_head.jsp" %>

<!-- TinyMCE -->
<script type="text/javascript" src="/WikiShare/public/js/tiny_mce/tiny_mce.js"></script>
<script type="text/javascript">
	tinyMCE.init({
		mode : "textareas",
		theme : "advanced",
		elements : "content"
	});
</script>
<!-- /TinyMCE -->

<h2><fmt:message key="create-new-page"/></h2>
<% String formSubmitText = "Create"; %>


<form:form method="post" commandName="wikipage">
<% 
	if( request.getParameter("action") != null && request.getParameter("action").equals("update") ) { 
		formSubmitText = "Update";	
%>
		<input type="hidden" name="action" value="update"/>
<% } %>
	
	<table>
		<tr>
			<td>Title:</td>
			<td><form:input path="title" size="80" /></td>
		</tr>
		<tr>
			<td>Content:</td>
			<td><form:textarea path="content" id="content" cols="80" rows="20" /></td>
			<!--  <td><form:errors path="title" /></td>-->
		</tr>
	</table>
	<%
		if( request.getParameter("action") != null && request.getParameter("action").equals("update") ) {
	%>
		<br/>
		<form:checkbox path="skipNewVersionFlag"/>Skip version
	<% } %>
	<br/>
	<input type="submit" align="center" value="<%= formSubmitText %>">
</form:form>
<a href="<%= W_PREFIX %>wikipages" title="Cancel">Cancel</a>
 
<%@ include file="html_footer.jsp" %>