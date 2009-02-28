<%@ include file="include.jsp" %> 
<%@ include file="html_head.jsp" %>

<h1>Wiki users</h1> 

<table border="1">
	<tr>
		<th>ID</th>
		<th>Title</th>
		<th><a href="<%= W_PREFIX %>user_create">Create</a></th>
	</tr>
	<c:forEach items="${model.users}" var="user">
		<tr>
			<td><c:out value="${user.id}"/></td>
			<td>
				<c:out value="${user.username}"/>
			</td>
			<td>
				<a href="<%= W_PREFIX %>user_create?action=update&object_id=${user.id}">Edit</a>
				<a href="<%= W_PREFIX %>users?action=delete&object_id=${user.id}">Delete</a>
			</td>
		</tr>
	</c:forEach>
</table>

<%@ include file="html_footer.jsp" %>