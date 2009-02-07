<%@ include file="include.jsp" %> 
<%@ include file="html_head.jsp" %>

<c:forEach items="${model.pages}" var="page">
	<div>
		<strong><c:out value="${page.title}"/></strong> <a href="<%= W_PREFIX %>wikipage_create?action=update&object_id=${page.id}">Edit</a>
		<p><c:out value="${page.content}" escapeXml="false"/></p>
	</div>
</c:forEach>

<%@ include file="html_footer.jsp" %>