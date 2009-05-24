<!DOCTYPE html
     PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
     "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="html_head.jsp" %>

<!-- TinyMCE -->
<script type="text/javascript" src="/WikiShare/public/wiki/js/tiny_mce/tiny_mce.js"></script>
<script type="text/javascript">
  tinyMCE.init({
    mode : "textareas",
    theme : "advanced",
    elements : "content",
    relative_urls : false,
    content_css : "/WikiShare/public/wiki/css/tinymce.css"
  });

  function convertAndInsertAtCaret(content) {
      var html = '';
    if(content.match(/(.jpg|.jpeg|.gif|.png)$/)) {
      html = '<img src="' + content + '"/>';
    }
    if(content.match(/s[0-9]*$/)) {
            html = '<a href="wikipage/'+ content +'">'+ content +'</a>';
        }
      tinyMCE.execCommand('mceInsertContent',false,html);
  }
</script>
<!-- /TinyMCE -->

<form:form method="post" commandName="wikipage">

  <% // bo: controller view... %>
  <div id="controllerViewContent">

    <div class="contentBox">
      <% String formSubmitText = "Create"; %>

      <c:set var="wikipageId" value="${wikipage.id}"></c:set>
      <c:set var="signature" value="${wikipage.signature}"></c:set>
      <%
        if( request.getParameter("action") != null &&
            request.getParameter("action").equals("update") ) {
          formSubmitText = "Update";
      %>
          <input type="hidden" name="action" value="update"/>
      <% } %>

      <h3><form:input path="title" size="80" cssClass="page_title" /></h3>
      <form:textarea path="content" id="content" cols="70" rows="30" />
      <!--  <form:errors path="title" />-->
    </div>

  </div> <% // eo: controller view. %>


  <div id="contentRight">

    <div class="boxRight">
      <h3 class="select">Page</h3>
      <ul>
        <li class="normal"><form:checkbox path="frontPage"/> Show on Frontpage</li>
        <%
          if( request.getParameter("action") != null &&
            request.getParameter("action").equals("update") ) {
         %>
        <li class="normal">
          <form:checkbox path="skipNewVersionFlag"/> Override current version
        </li>
        <% } %>
        <li><a href="#" onclick="document.getElementById( 'wikipage' ).submit(); return;"><%= formSubmitText %></a></li>
        <li><a href="<%= W_PREFIX %>wikipage/${signature}" title="Cancel">Cancel</a></li>
       </ul>
    </div>

    <div class="boxRight">
      <h3 class="select">Clipboard</h3>
      <ul>
        <c:forEach items="${wikipage.clipboardItems}" var="clipboard">
         <li><a href="#" onclick="convertAndInsertAtCaret('${clipboard}');return false;"><c:out value="${clipboard}"/></a></li>
        </c:forEach>
      </ul>
    </div>

  </div>

</form:form>

<%@ include file="html_footer.jsp" %>