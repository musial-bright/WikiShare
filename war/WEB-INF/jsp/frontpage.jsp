<%@ include file="include.jsp" %> 
<%@ include file="html_head.jsp" %>

<c:forEach items="${model.pages}" var="page">
	<div>
		<strong><c:out value="${page.title}"/></strong>
		<p><c:out value="${page.content}" escapeXml="false"/></p>
	</div>
</c:forEach>

<%@ include file="html_footer.jsp" %>