<%@ include file="include.jsp" %> 
<%@ include file="html_head.jsp" %>


<script src="/WikiShare/public/js/cookie.js"></script>
<script type="text/javascript">
	var navigationArray = new Array();
	<c:forEach items="${model.navigationList}" var="singleNavigation">
		navigationArray['${singleNavigation.id}#${singleNavigation.name}'] = '<c:out value="${singleNavigation.content}" escapeXml="false" />';
	</c:forEach>
	
	/* Get real navigation id from selected navigation. */
	function getNaviId() {
		var selectedNaviId = 0;
		try {
			selectedNaviId = document.getElementById("navigationSelectId").options[document.getElementById("navigationSelectId").selectedIndex].value
			if ( selectedNaviId < 0 ) {
				selectedNaviId = 0;
			}
		} catch(error) { selectedNaviId = 0; }
		/* trim and return id */
		return selectedNaviId.replace(/^\s+|\s+$/g, '');
	}
	
	function renderNavigationSelection() {
		document.write('<form>');
		document.write('<select id="navigationSelectId" onchange="javascript:renderNavigationContentById(); setNavigationEditLink(); setNavigationDeleteLink();">');
		for (var i in navigationArray) {
			var navigationAsString = i;
			var idNameArray = navigationAsString.split('#');
			var alreadySelectedId = getCookieValue("wikishare_navi_id");
			var selectedAttribute = "";
			if( alreadySelectedId != -1 && alreadySelectedId == idNameArray[0] ) {
				selectedAttribute = 'selected="selected"';
				document.write('<option id="selectedNavigation" value="'+ idNameArray[0] +'" '+ selectedAttribute +'">('+ idNameArray[0] +') '+ idNameArray[1] +'</option>');
			} else {
				document.write('<option value="'+ idNameArray[0] +' '+ selectedAttribute +'">('+ idNameArray[0] +') '+ idNameArray[1] +'</option>');
			}
		}
		document.write('</select>');
		document.write('</form>');
	}

	
	function renderNavigationContentById() {
		id = getNaviId();
		for (var i in navigationArray) {
			var navigationKey = i;
			var idNameArray = navigationKey.split('#');
			if( idNameArray[0] == id) {
				document.getElementById('navigationContent').innerHTML = navigationArray[i];
				break;
			}
		}
		setCookie("wikishare_navi_id",id);
	}
	
	
	var w_prefix = "<%= W_PREFIX %>";
	
	/* Create delete link with object id parameter. */
	function setNavigationDeleteLink() {
		selectedNavigationId = getNaviId();
		var link = w_prefix + 'wikipage/' + '<c:out value="${model.page.id}" />';
		link = link + '?action=delete_navi&object_id=' + selectedNavigationId;
		document.getElementById('navigation_delete_link').href = link;
	}
	
	/* Create edit link with object id parameter. */
	function setNavigationEditLink() {
		selectedNavigationId = getNaviId();
		link = w_prefix + 'navigation_create?action=update&object_id=' + selectedNavigationId;
		document.getElementById('navigation_edit_link').href = link;
	}
</script>

<div>
	<script type="text/javascript">
		renderNavigationSelection();		
	</script>
	<span class="edit_button"><a href="<%= W_PREFIX %>navigation_create">Create</a> | <a id="navigation_edit_link" href="">Edit</a> | <a id="navigation_delete_link" href="">Delete</a></span>
	<script type="text/javascript">
		setNavigationDeleteLink();
		setNavigationEditLink();
	</script>
	<div id="navigationContent"></div>
	<script type="text/javascript">	
		/* Show initial navigation content selection. */
		renderNavigationContentById();
	</script>
</div>


<h1><c:out value="${model.page.title}"/></h1>
<p><c:out value="${model.page.content}" escapeXml="false"/></p>
<p class="edit_button">
	<a href="<%= W_PREFIX %>wikipage_create?action=update&object_id=${model.page.id}">Edit</a> |
	<a href="<%= W_PREFIX %>wikipage_versions?signature=${model.page.signature}&action=delete&object_id=${model.page.id}">Delete</a>
	<i>Copy signature [s<c:out value="${model.page.signature}"/>]</i>
</p> 

<%@ include file="html_footer.jsp" %>