<!DOCTYPE html
     PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
     "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="html_head.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<form:form method="post" commandName="navigation">

<% // bo: controller view... %>
<div id="controllerViewContent">
  <div class="contentBox">
    <h3>Navigation</h3>
    <%
      String formSubmitText = "Create";
      if( request.getParameter("action") != null && request.getParameter("action").equals("update") ) {
        formSubmitText = "Update";
    %>
        <input type="hidden" name="action" value="update"/>
    <% } %>
        <table>
        <tr>
        <td>Navi name</td>
        <td><form:input path="name" /></td>
        </tr><tr>
        <td>Content</td>
        <td><form:textarea path="content" cols="30" rows="5" /></td>
        </tr>
        </table>
  </div>
</div> <% // eo: controller view. %>

<div id="contentRight">
    <div class="boxRight">
        <h3>Create user</h3>
        <ul>
            <li><a href="#" onclick="document.getElementById('navigation').submit(); return;"><%= formSubmitText %></a></li>
            <li><a href="<%= W_PREFIX %>wikipages" title="Cancel">Cancel</a></li>
        </ul>
    </div>
    <div class="bixRight">
        <h3>Clipboard</h3>
        <ul class="clipboard">
          <c:forEach items="${navigation.clipboardItems}" var="clipboard">
              <li><a href="#" onclick="javascript:insertAtCaret('content','${clipboard}');"><c:out value="${clipboard}"/></a></li>
          </c:forEach>
        </ul>
    </div>
</div>

</form:form>

<%@ include file="html_footer.jsp" %>