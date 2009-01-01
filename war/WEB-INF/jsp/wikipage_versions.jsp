<%@ include file="include.jsp" %> 
<%@ include file="html_head.jsp" %>


<table>
	<tr>
		<th>ID</th>
		<th>Active</th>
		<th>Title</th>
		<th>Date</th>
		<th><a href="<%= W_PREFIX %>wikipage_create">Create</a></th>
	</tr>
	<c:forEach items="${model.pages}" var="page">
		<tr>
			<td><c:out value="${page.id}"/></td>
			<td><c:out value="${page.activePage}"/></td>
			<td><a href="<%= W_PREFIX %>wikipage/<c:out value="${page.id}"/>"><c:out value="${page.title}"/></a></td>
			<td><c:out value="${page.date}"/></td>
			<td>
				<a href="<%= W_PREFIX %>wikipage_versions?signature=${page.signature}&action=delete&object_id=${page.id}">Delete</a>
				<a href="<%= W_PREFIX %>wikipage_create?action=update&object_id=${page.id}">Edit</a>
			</td>
		</tr>

	</c:forEach>
</table>

<%@ include file="html_footer.jsp" %>