<%@ include file="html_head.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<% // bo: controllerViewContent %>
<div id="controllerViewContent">
	<div class="contentBox">
	<form:form method="post" commandName="user">
		<%
			String formSubmitText = "Create";
			if( request.getParameter("action") != null && request.getParameter("action").equals("update") ) {
				formSubmitText = "Update";
		%>
		    <input type="hidden" name="action" value="update"/>
		<% } %>
		<p>
            <a href="#" onclick="document.getElementById( 'user' ).submit(); return;"><%= formSubmitText %></a> | 
            <a href="<%= W_PREFIX %>users" title="Cancel">Cancel</a>
        </p>
        <p>
		    Username: <form:input path="username" />
		    Passowrd: <form:password path="password" />
		</p>
   </form:form>
	</div>
</div> <% // eo: controllerViewContent %>


<%@ include file="html_footer.jsp" %>