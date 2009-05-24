<!DOCTYPE html
     PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
     "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="html_head.jsp" %>

<% // bo: controller view... %>
<div id="controllerViewContent">

<div class="contentBox">

<form method="post" action="<%= W_PREFIX %>file_upload" enctype="multipart/form-data" style="border: 1px dotted lightgray">
  <fieldset>
    <legend>File upload:</legend>
    <input type="file" name="multipartFile" /> <input type="submit" value="Upload" />
  </fieldset>
</form>


<c:forEach items="${model.files}" var="file">
  <div class="fileBox">
    <c:out value="${file.fileName}"/> | <a href="<%= W_PREFIX %>files?delete=${file.fileName}">Delete</a><br/>
    <c:if test="${file.isGraphics}">
      <a href="<%= request.getContextPath() %>/public/files/<c:out value="${file.fileName}"/>" target="_blank">
        <img height="100" src="<%= request.getContextPath() %>/public/files/<c:out value="${file.fileName}"/>"/>
      </a>
    </c:if>
        <p style="font-size: 10px; margin:0; madding:0;">
            <%= request.getContextPath() %>/public/files/${file.fileName}
            <br/>
            <a href="<%= W_PREFIX %>files?clipboard=<%= request.getContextPath() %>/public/files/${file.fileName}">Copy image path</a>
    </p>
  </div>
</c:forEach>
<div style="clear:both;">&nbsp;</div>
</div>

</div> <% // eo: controller view. %>

<%@ include file="html_footer.jsp" %>