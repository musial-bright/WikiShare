<!DOCTYPE html 
     PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
     "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="html_head.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<script type="text/javascript" src="/WikiShare/public/wiki/js/text.js"></script>


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
		<br/>
		Clipboard:
		<ul class="clipboard">
		  <c:forEach items="${navigation.clipboardItems}" var="clipboard">
		      <li><a href="#" onclick="javascript:insertAtCaret('content','${clipboard}');"><c:out value="${clipboard}"/></a></li>
		  </c:forEach>
	   </ul>
	</div>
    <div class="right_col">
	   <a href="#" onclick="document.getElementById('navigation').submit(); return;"><%= formSubmitText %></a>
       <br/>
       <a href="<%= W_PREFIX %>wikipages" title="Cancel">Cancel</a>	   
    </div>
</form:form>

 
<%@ include file="html_footer.jsp" %>