<!DOCTYPE html
     PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
     "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="html_head.jsp" %>

<form:form method="post" commandName="user">

<% // bo: controller view... %>
<div id="controllerViewContent">
  <div class="contentBox">
    <%
      String formSubmitText = "Create";
      if( request.getParameter("action") != null && request.getParameter("action").equals("update") ) {
        formSubmitText = "Update";
    %>
        <input type="hidden" name="action" value="update"/>
    <% } %>
      <p>
        Username: <form:input path="username" />
        Passowrd: <form:password path="password" />
       </p>
  </div>
</div> <% // eo: controller view. %>

<div id="contentRight">
    <div class="boxRight">
        <h3>Create user</h3>
        <ul>
            <li><a href="#" onclick="document.getElementById( 'user' ).submit(); return;"><%= formSubmitText %></a></li>
            <li><a href="<%= W_PREFIX %>users" title="Cancel">Cancel</a></li>
        </ul>
    </div>
</div>

</form:form>

<%@ include file="html_footer.jsp" %>