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
  var link = w_prefix + 'wikipage/' + '<c:out value="${model.page.id}" />';
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



<% // bo: controllerViewContent %>
<div id="controllerViewContent">

  <div class="contentBox">
      <h3>
        <c:out value="${model.page.title}"/>
        <span class="edit_button">
            <a href="<%= W_PREFIX %>wikipage_create/${model.page.id}?action=update">Edit</a> |
            <a href="<%= W_PREFIX %>wikipage_versions/${model.page.signature}">Versions</a> |
            <a href="<%= W_PREFIX %>wikipage_versions/${model.page.id}?action=delete" onclick="return confirmAction()">Delete</a> |
            <a href="<%= W_PREFIX %>wikipage/${model.page.id}?clipboard=${model.page.signature}">Copy signature</a>
        </span>
      </h3>
      <p><c:out value="${model.page.content}" escapeXml="false"/></p>
  </div>

</div> <% // eo: controllerViewContent %>


<div id="contentRight">

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

</div>

<%@ include file="html_footer.jsp" %>