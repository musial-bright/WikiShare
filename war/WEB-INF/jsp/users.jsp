<%@ include file="html_head.jsp" %>

<% // bo: controllerViewContent %>
<div id="controllerViewContent">

<div class="contentBox">
  <h3>Wiki users</h3>

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
          <a href="<%= W_PREFIX %>user_create/${user.id}?action=update">Edit</a>
          <a href="<%= W_PREFIX %>users/${user.id}?action=delete" onclick="return confirmAction()">Delete</a>
        </td>
      </tr>
    </c:forEach>
  </table>
</div>

</div> <% // eo: controllerViewContent %>

<%@ include file="html_footer.jsp" %>