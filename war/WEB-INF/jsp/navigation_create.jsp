<%@ include file="html_head.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>




<% // bo: controller view... %>
<div id="controllerViewContent">
    <div class="contentBox">
	    <form:form method="post" commandName="navigation">
		    <h3>Navigation</h3>
		    <%
		      String formSubmitText = "Create";
		      if( request.getParameter("action") != null && request.getParameter("action").equals("update") ) {
		        formSubmitText = "Update";
		    %>
		        <input type="hidden" name="action" value="update"/>
		    <% } %>
		    <p>
		        <a href="#" onclick="document.getElementById('navigation').submit(); return;"><%= formSubmitText %></a> | 
		        <a href="<%= W_PREFIX %>frontpage" title="Cancel">Cancel</a>
		    </p>
		    
	        <table>
		        <tr>
		        <td>Navi name</td>
		        <td><form:input path="name" /></td>
		        </tr><tr>
		        <td>Content</td>
		        <td><form:textarea path="content" cols="30" rows="5" /></td>
		        </tr>
	        </table>
	    </form:form>
    </div>
</div> <% // eo: controller view. %>

<div id="contentRight">
    <div class="bixRight">
        <h3>Clipboard</h3>
        <ul class="clipboard">
          <c:forEach items="${navigation.clipboardItems}" var="clipboard">
              <li><a href="#" onclick="javascript:insertAtCaret('content','${clipboard}');"><c:out value="${clipboard}"/></a></li>
          </c:forEach>
        </ul>
    </div>
</div>

<%@ include file="html_footer.jsp" %>