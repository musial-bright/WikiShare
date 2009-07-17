<!DOCTYPE html
     PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
     "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="html_head.jsp" %>

<% // bo: controller view... %>
<div id="controllerViewContent">

  <div class="contentBox">
    <h3>Wikipages</h3>

    <table border="1">
      <tr>
        <th>ID</th>
        <th>Signature</th>
        <th>User (ID)</th>
        <th>Active</th>
        <th>Front page</th>
        <th>Title</th>
        <th>Date</th>
        <th><a href="<%= W_PREFIX %>wikipage_create">Create</a></th>
      </tr>
      <c:forEach items="${model.pages}" var="page">
        <tr>
          <td><c:out value="${page.id}"/></td>
          <td><c:out value="${page.signature}"/></td>
          <td><c:out value="${page.userId}"/> (<c:out value="${page.userId}"/>)</td>
          <td><c:out value="${page.activePage}"/></td>
          <td><c:out value="${page.frontPage}"/></td>
          <td><a href="<%= W_PREFIX %>wikipage/<c:out value="${page.id}"/>"><c:out value="${page.title}"/></a></td>
          <td><c:out value="${page.date}"/></td>
          <td>
            <a href="<%= W_PREFIX %>wikipage_create/${page.id}?action=update">Edit</a> |
            <a href="<%= W_PREFIX %>wikipage_versions/${page.signature}">Versions (${page.versionAmount})</a>
          </td>
        </tr>
      </c:forEach>
    </table>
  </div>

</div> <% // eo: controller view. %>

<div id="contentRight">

  <div class="boxRight">
    <h3 class="select">Visual options</h3>
    <ul>
      <li>
      <% if(request.getParameter("action") != null && request.getParameter("action").equals("showAllPageVersions")) { %>
        <a href="<%= W_PREFIX %>wikipages">Hide older page versions</a>
      <% } else { %>
        <a href="<%= W_PREFIX %>wikipages?action=showAllPageVersions">Show all page versions</a>
      <% } %>
      </li>
    </ul>
  </div>
</div>

<%@ include file="html_footer.jsp" %>