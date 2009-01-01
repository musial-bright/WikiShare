<%@ include file="include.jsp" %> 
<%@ include file="html_head.jsp" %>

<h1>Title : <c:out value="${model.pageTitle}"/></h1> 

<table border="1">
	<tr>
		<th>ID</th>
		<th>Signature</th>
		<th>User (ID)</th>
		<th>Active</th>
		<th>Title</th>
		<th>Date</th>
		<th><a href="<%= W_PREFIX %>wikipage_create">Create</a></th>
	</tr>
	<c:forEach items="${model.pages}" var="page">
		<tr>
			<td><c:out value="${page.id}"/></td>
			<td><c:out value="${page.signature}"/></td>
			<td><c:out value="${page.user.username}"/> (<c:out value="${page.user.id}"/>)</td>
			<td><c:out value="${page.activePage}"/></td>
			<td><a href="<%= W_PREFIX %>wikipage/<c:out value="${page.id}"/>"><c:out value="${page.title}"/></a></td>
			<td><c:out value="${page.date}"/></td>
			<td>
				<a href="<%= W_PREFIX %>wikipage_create?action=update&object_id=${page.id}">Edit</a> | 
				<a href="<%= W_PREFIX %>wikipage_versions?signature=${page.signature}">Versions (${page.versionAmount})</a>
			</td>
		</tr>
	</c:forEach>
</table>
<p><a href="<%= W_PREFIX %>wikipages?action=showAllPageVersions">Show all page versions</a></p>

<%@ include file="html_footer.jsp" %>