<%@ include file="html_head.jsp" %>

<% // bo: controller view... %>
<div id="controllerViewContent">

<div class="contentBox">
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
          <a href="<%= W_PREFIX %>wikipage_versions/${page.id}?action=delete" onclick="return confirmAction()">Delete</a>
          <a href="<%= W_PREFIX %>wikipage_create/${page.id}?action=update">Edit</a>
        </td>
      </tr>

    </c:forEach>
  </table>
</div>

</div> <% // eo: controller view. %>

<%@ include file="html_footer.jsp" %>