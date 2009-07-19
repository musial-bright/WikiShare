<!DOCTYPE html
     PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
     "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="html_head.jsp" %>

<% // bo: controller view... %>
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
</div> <% // eo: controller view. %>


<%@ include file="html_footer.jsp" %>