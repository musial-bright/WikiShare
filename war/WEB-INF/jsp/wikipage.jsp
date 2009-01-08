<%@ include file="include.jsp" %> 
<%@ include file="html_head.jsp" %>


<script src="/WikiShare/public/js/cookie.js"></script>
<script type="text/javascript">
	var navigationArray = new Array();
	<c:forEach items="${model.navigationList}" var="singleNavigation">
		navigationArray['${singleNavigation.id}#${singleNavigation.name}'] = '${singleNavigation.content}';
	</c:forEach>
	
	function renderNavigationSelection() {
		document.write('<form>');
		document.write('<select id="navigationSelectId" onchange="javascript:renderNavigationContentById(this.selectedIndex); setNavigationDeleteLink();">');
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

	
	function renderNavigationContentById(id) {
		for (var i in navigationArray) {
			var navigationKey = i;
			var idNameArray = navigationKey.split('#');
			if( idNameArray[0] == id) {
				document.getElementById('navigationContent').innerHTML = navigationArray[i];
			}
		}
		setCookie("wikishare_navi_id",id);
	}
	
	var w_prefix = "<%= W_PREFIX %>";
	
	/* Create delete link with object id parameter. */
	function setNavigationDeleteLink() {
		try {
			var selectedNavigationId = document.getElementById('navigationSelectId').options[document.getElementById('navigationSelectId').selectedIndex].value;
		} catch(error) {
			var selectedNavigationId = -1;
		}
		var link = w_prefix + 'wikipage/' + '<c:out value="${model.page.id}" />';
		link = link + '?action=delete_navi&object_id=' + selectedNavigationId;
		
		document.getElementById('navigation_delete_link').href = link;
	}
</script>

<div>
	<script type="text/javascript">
		renderNavigationSelection();		
	</script>
	<div id="navigationContent">Navigation content</div>
	<a href="<%= W_PREFIX %>navigation_create">Create</a> | <a href="<%= W_PREFIX %>navigation_create?action=update&object_id=">Edit</a> | <a id="navigation_delete_link" href="">Delete</a>
	<script type="text/javascript">
		setNavigationDeleteLink(document.getElementById('navigationSelectId').selectedIndex);
		
		/* Show initial navigation content selection. */
		renderNavigationContentById(document.getElementById('navigationSelectId').selectedIndex);
	</script>
</div>


<h1><c:out value="${model.page.title}"/> [<c:out value="${model.page.id}"/>]</h1> 
<p style="border: 1px dotted lightgray;">
	<c:out value="${model.page.content}" escapeXml="false"/>
</p>
<%@ include file="html_footer.jsp" %>