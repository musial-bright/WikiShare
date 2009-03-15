<!DOCTYPE html 
     PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
     "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="html_head.jsp" %>

<h1>Search results</h1> 

<ul>
	<c:forEach items="${model.pages}" var="page">
		<li><a href="<%= W_PREFIX %>wikipage/<c:out value="${page.id}"/>"><c:out value="${page.title}" /></a></li>
	</c:forEach>
</ul>

<%@ include file="html_footer.jsp" %>