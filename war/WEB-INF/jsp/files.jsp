<!DOCTYPE html
     PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
     "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="html_head.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<% // bo: controller view... %>
<div id="controllerViewContent">



<div class="contentBox">
<h3>Public files</h3>
<c:forEach items="${model.publicFiles}" var="file">
  <div class="fileBox">
    <c:out value="${file.fileName}"/>
    <p>
    <c:if test="${file.isGraphics}">
      <a href="<%= request.getContextPath() %>/public/files/<c:out value="${file.fileName}"/>" target="_blank">
        <img width="100" height="75" src="<%= request.getContextPath() %>/public/files/<c:out value="${file.fileName}"/>"/>
      </a>
    </c:if>
    </p>
    <p style="font-size: 10px; margin:0; madding:0;">
      <c:out value="${file.sizeForHuman}"/>
      <br/>
        <a href="<%= W_PREFIX %>files?clipboard=<%= request.getContextPath() %>/public/files/${file.fileName}">Copy image path</a>
        <br/>
        <a href="<%= W_PREFIX %>files?moveToProtected=${file.fileName}">Copy to protected</a>
        <br/>
        <a href="<%= W_PREFIX %>files?deletePublic=${file.fileName}">Delete</a>
    </p>
  </div>
</c:forEach>
<div style="clear:both;">&nbsp;</div>
</div>


<div class="contentBox">
<h3>Protected files</h3>
<c:forEach items="${model.protectedFiles}" var="file">
  <div class="fileBox">
    <c:out value="${file.fileName}"/>
    <p>
    <c:if test="${file.isGraphics}">
      <a href="<%= request.getContextPath() %>/protected/files/<c:out value="${file.fileName}"/>" target="_blank">
        <img width="100" height="75" src="<%= request.getContextPath() %>/protected/files/<c:out value="${file.fileName}"/>"/>
      </a>
    </c:if>
    </p>
    <p style="font-size: 10px; margin:0; madding:0;">
        <c:out value="${file.sizeForHuman}"/>
        <br/>
        <a href="<%= W_PREFIX %>files?clipboard=<%= request.getContextPath() %>/protected/files/${file.fileName}">Copy image path</a>
        <br/>
        <a href="<%= W_PREFIX %>files?moveToPublic=${file.fileName}">Copy to public</a>
        <br/>
        <a href="<%= W_PREFIX %>files?deleteProtected=${file.fileName}">Delete</a>
    </p>
  </div>
</c:forEach>
<div style="clear:both;">&nbsp;</div>
</div>


</div> <% // eo: controller view. %>


<div id="contentRight">
  <div class="boxRight">
    <h3>File upload</h3>
    <form id="wikiFile" method="post" action="<%= W_PREFIX %>file_upload" enctype="multipart/form-data" style="border: 1px dotted lightgray">
      <ul>
        <li><input type="file" name="multipartFile" /></li>
        <li><input type="checkbox" id="publicFile" name="publicFile"/> Public</li>
        <li><a href="#" onclick="document.getElementById( 'wikiFile' ).submit(); return;">Upload file</a></li>
        </ul>
    </form>
  </div>
</div>


<%@ include file="html_footer.jsp" %>