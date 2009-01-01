<%@ include file="include.jsp" %> 
<%@ include file="html_head.jsp" %>

<form method="post" action="<%= W_PREFIX %>file_upload" enctype="multipart/form-data" style="border: 1px dotted lightgray">
	<fieldset>
		<legend>File upload:</legend>
		<input type="file" name="multipartFile" /> <input type="submit" />
	</fieldset>
</form>

<table>
	<c:forEach items="${model.files}" var="file">
		<tr>
			<td><c:out value="${file.fileName}"/></td>
			<td>
				<a href="<%= request.getContextPath() %>/public/files/<c:out value="${file.fileName}"/>" target="_blank">
					<img height="100" src="<%= request.getContextPath() %>/public/files/<c:out value="${file.fileName}"/>"/>
				</a>
			</td>
			
		</tr>
	</c:forEach>
</table>

<%@ include file="html_footer.jsp" %>