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
		document.write('<select id="navigationSelectId" onchange="javascript:renderNavigationContentById(this.selectedIndex)" onkeydown="javascript:renderNavigationContentById(this.selectedIndex)" onkeyup="javascript:renderNavigationContentById(this.selectedIndex)">');
		for (var i in navigationArray) {
			var navigationAsString = i;
			var idNameArray = navigationAsString.split('#');
			var alreadySelectedId = getCookieValue("wikishare_navi_id");
			var selectedAttribute = "";
			if( alreadySelectedId != -1 && alreadySelectedId == idNameArray[0] ) {
				selectedAttribute = 'selected="selected"';
			}
			document.write('<option value="'+ idNameArray[0] +' '+ selectedAttribute +'">('+ idNameArray[0] +') '+ idNameArray[1] +'</option>');
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
</script>


<div>
	<script type="text/javascript">
		renderNavigationSelection();		
	</script>
	<div id="navigationContent">Navigation content</div>
	<script type="text/javascript">
		/* Show initial navigation content selection. */
		renderNavigationContentById(document.getElementById('navigationSelectId').selectedIndex);
	</script>
</div>

<h1><c:out value="${model.page.title}"/> [<c:out value="${model.page.id}"/>]</h1> 
<p style="border: 1px dotted lightgray;">
	<c:out value="${model.page.content}" escapeXml="false"/>
</p>
<%@ include file="html_footer.jsp" %>