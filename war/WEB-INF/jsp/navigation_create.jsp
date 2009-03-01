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
	<div class="left_col">
		<p>Navi name: <form:input path="name" /></p>
		<p>Content: <form:textarea path="content" cols="30" rows="5" /></p>
		<p>
		  Clipboard:
		  <c:forEach items="${navigation.clipboardItems}" var="clipboard">
		      <c:out value="${clipboard}"/>
		  </c:forEach>
		</p>
	</div>
    <div class="right_col">
	   <a href="#" onclick="document.getElementById( 'navigation' ).submit(); return;"><%= formSubmitText %></a>
       <br/>
       <a href="<%= W_PREFIX %>wikipages" title="Cancel">Cancel</a>	   
    </div>
</form:form>

 
<%@ include file="html_footer.jsp" %>