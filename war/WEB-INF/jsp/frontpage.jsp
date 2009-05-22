<!DOCTYPE html
     PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
     "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="html_head.jsp" %>

<c:forEach items="${model.pages}" var="page">
  <div>
    <strong><c:out value="${page.title}"/></strong>
    <% if (USER != null) { %>
      <a class="edit_button" href="<%= W_PREFIX %>wikipage_create/${page.id}?action=update">Edit</a>
    <% } %>
    <p><c:out value="${page.content}" escapeXml="false"/></p>
  </div>
</c:forEach>
<c:if test="${model.error!=null}">
    <center><c:out value="${model.error}" /></center>
</c:if>

<%@ include file="html_footer.jsp" %>