<!DOCTYPE html
     PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
     "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
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
        <a href="<%= W_PREFIX %>user_create/${user.id}?action=update">Edit</a>
        <a href="<%= W_PREFIX %>users/${user.id}?action=delete">Delete</a>
      </td>
    </tr>
  </c:forEach>
</table>

<%@ include file="html_footer.jsp" %>