<%@ include file="include.jsp" %> 
<%@ include file="html_head.jsp" %>

<div>
	<c:out value="${model.navigation}" escapeXml="false"/>
</div>

<h1><c:out value="${model.page.title}"/> [<c:out value="${model.page.id}"/>]</h1> 
<p style="border: 1px dotted lightgray;">
	<c:out value="${model.page.content}" escapeXml="false"/>
</p>
<%@ include file="html_footer.jsp" %>