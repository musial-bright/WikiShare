<%@ include file="include.jsp" %> 
<%@ include file="html_head.jsp" %>

<form method="post" action="<%= W_PREFIX %>file_upload" enctype="multipart/form-data" style="border: 1px dotted lightgray">
	<fieldset>
		<legend>File upload:</legend>
		<input type="file" name="multipartFile" /> <input type="submit" value="Upload" />
	</fieldset>
</form>


<c:forEach items="${model.files}" var="file">
	<div style="float: left; border: 1px dotted #888; height:130px; padding: 0.5em; margin: 1em;">
		<c:out value="${file.fileName}"/> | <a href="<%= W_PREFIX %>files?delete=${file.fileName}">Delete</a><br/>
		<c:if test="${file.isGraphics}">
			<a href="<%= request.getContextPath() %>/public/files/<c:out value="${file.fileName}"/>" target="_blank">
				<img height="100" src="<%= request.getContextPath() %>/public/files/<c:out value="${file.fileName}"/>"/>
			</a>
		</c:if>
		<p style="font-size: 10px; margin:0; madding:0;">Path: <%= request.getContextPath() %>/public/files/${file.fileName}</p>
	</div>
</c:forEach>
<div style="clear:both;">&nbsp;</div>

<%@ include file="html_footer.jsp" %>