<!DOCTYPE html 
     PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
     "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="html_head.jsp" %>


<script src="/WikiShare/public/wiki/js/cookie.js"></script>
<script type="text/javascript">
    // Navigation by Adam Musial-Bright
    // Require/Tested: Prototype JavaScript framework, version 1.6.0.3 or higher
    
	var navigationArray = new Hash();
	<c:forEach items="${model.navigationList}" var="singleNavigation">
		navigationArray.set('${singleNavigation.id}#${singleNavigation.name}','<c:out value="${singleNavigation.content}" escapeXml="false" />');
	</c:forEach>
	
	/* Get real navigation id from selected navigation. */
	function getNaviId() {
		var selectedNaviId = 0;
		try {
			selectedNaviId = $('navigationSelectId').options[$('navigationSelectId').selectedIndex].value
			if ( selectedNaviId < 0 ) {
				selectedNaviId = 0;
			}
		} catch(error) { selectedNaviId = 0; }
		/* trim and return id */
		return selectedNaviId.replace(/^\s+|\s+$/g, '');
	}
	
	function renderNavigationSelection() {
		document.write('<form>');
		document.write('<select id="navigationSelectId" onchange="javascript:renderNavigationContentById(true); setNavigationEditLink(); setNavigationDeleteLink();">');
		//for (var i in navigationArray) {
		navigationArray.each( function(pair) {
			var navigationAsString = pair.key;
			var idNameArray = navigationAsString.split('#');
			var alreadySelectedId = getCookieValue("wikishare_navi_id");
			var selectedAttribute = "";
			if( alreadySelectedId != -1 && alreadySelectedId == idNameArray[0] ) {
				selectedAttribute = 'selected="selected"';
				document.write('<option id="selectedNavigation" value="'+ idNameArray[0] +'" '+ selectedAttribute +'">('+ idNameArray[0] +') '+ idNameArray[1] +'</option>');
			} else {
				document.write('<option value="'+ idNameArray[0] +' '+ selectedAttribute +'">('+ idNameArray[0] +') '+ idNameArray[1] +'</option>');
			}
		});
		document.write('</select>');
		document.write('</form>');
	}

	
	function renderNavigationContentById(setCookieFlag) {
		id = getNaviId();
		navigationArray.each( function(pair) {
			var navigationKey = pair.key;
			var idNameArray = navigationKey.split('#');
			if( idNameArray[0] == id) {
				document.getElementById('navigationContent').innerHTML = pair.value;
			}
		});
		if(setCookieFlag == true) {
			  setCookie("wikishare_navi_id",id);
		}
	}
	
	
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



<div class="left_col">
    <strong><c:out value="${model.page.title}"/></strong>
    <span class="edit_button">
        <a href="<%= W_PREFIX %>wikipage_create?action=update&object_id=${model.page.id}">Edit</a> |
        <a href="<%= W_PREFIX %>wikipage_versions?signature=${model.page.signature}&action=delete&object_id=${model.page.id}">Delete</a> | 
        <a href="<%= W_PREFIX %>wikipage/${model.page.id}?clipboard=s${model.page.signature}">Copy signature [s<c:out value="${model.page.signature}"/>]</a>
    </span>
    <p><c:out value="${model.page.content}" escapeXml="false"/></p>
</div>

<div class="right_col">
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
	        renderNavigationContentById(false);
	    </script>
	</div>
</div>

<%@ include file="html_footer.jsp" %>