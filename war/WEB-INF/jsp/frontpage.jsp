<%@ include file="html_head.jsp" %>

<script src="/WikiShare/public/wiki/js/cookie.js"></script>
<script type="text/javascript">
// navigation array for navigation.js
var navigationArray = new Hash();
<c:forEach items="${model.navigationList}" var="singleNavigation">
  navigationArray.set('${singleNavigation.id}#${singleNavigation.name}','<c:out value="${singleNavigation.content}" escapeXml="false" />');
</c:forEach>

var w_prefix = "<%= W_PREFIX %>";

/* Create delete link with object id parameter. */
function setNavigationDeleteLink() {
  selectedNavigationId = getNaviId();
  var link = w_prefix + 'frontpage';
  link = link + '?action=delete_navi&object_id=' + selectedNavigationId;
  $('navigation_delete_link').href = link;
}

/* Create edit link with object id parameter. */
function setNavigationEditLink() {
  selectedNavigationId = getNaviId();
  link = w_prefix + 'navigation_create?action=update&object_id=' + selectedNavigationId;
  $('navigation_edit_link').href = link;
}
</script>
<script src="/WikiShare/public/wiki/js/navigation.js"></script>

<% // bo: controller view... %>
<div id="controllerViewContent">

	<c:forEach items="${model.pages}" var="page">
		<div class="contentBox">
	     <h3>
           <c:out value="${page.title}"/>
           <% if (USER != null) { %>
              <a class="edit_button" href="<%= W_PREFIX %>wikipage_create/${page.id}?action=update">Edit</a>
            <% } %>
         </h3>
         <div id="content_${page.id}">
            <c:out value="${page.content}" escapeXml="false"/>
         </div>
		</div>
	</c:forEach>

</div> <% // eo: controller view. %>


<div id="contentRight">

  <%
  if (USER == null) {
    String paramUsername = request.getParameter(WikiShareHelper.USERNAME);
    if(paramUsername == null) { paramUsername = ""; }
  %>

  <div class="boxRight">
    <h3>Login</h3>
    <ul>
      <li>
        <form id="login_form" name="login_form" method="post" action="<%= W_PREFIX %>frontpage">
            <input type="hidden" name="action" value="login"/>
            <table>
            <tr>
            <td width="80">Username</td><td><input name="username" type="text" value="<%= paramUsername %>" tabindex="1"/></td>
            </tr>
            <tr>
            <td>Password</td><td><input name="password" type="password" tabindex="2"/></td>
            </tr>
            <tr>
            <td><input type="submit" value="Login" style="display:none;"/></td><td style="text-align:right;"><a href="#" onclick="document.getElementById( 'login_form' ).submit(); return;">Login</a></td>
            </tr>
            </table>
        </form>
      </li>
    </ul>
  </div>

  <script language="javascript" type="text/javascript">
  <!--
    document.login_form.username.focus();
  //-->
  </script>
  <% } %>

  <% if (USER != null) { %>
  <div class="boxRight">
    <h3 class="select">Navigations</h3>
    <ul>
      <li>
        <script type="text/javascript"> renderNavigationSelection(); </script>
        <a href="<%= W_PREFIX %>navigation_create">Create</a> |
        <a id="navigation_edit_link" href="">Edit</a> |
        <a id="navigation_delete_link" href="" onclick="return confirmAction()">Delete</a>
        <script type="text/javascript">
              setNavigationDeleteLink();
              setNavigationEditLink();
        </script>
      </li>
      <li>
        <div id="navigationContent"></div>
        <script type="text/javascript">
          /* Show initial navigation content selection. */
          renderNavigationContentById(false);
        </script>
      </li>
    </ul>
  </div>
  <% } %>

</div>


<%@ include file="html_footer.jsp" %>