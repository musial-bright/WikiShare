<%@ include file="include.jsp" %> 
<%@ include file="html_head.jsp" %>

<script type="text/javascript">

	function setNavigationIdAsCookie(naviId) {
		var date = new Date();
		date = new Date(date.getTime() +1000*60*60*24*365);
		document.cookie = 'wikishare_navi_id='+ naviId +'; expires='+ date.toGMTString() +';'; 
	}
	function getNavigationIdFromCookieValue() {
		var cookies = document.cookie;
		var cookiesAsArray = cookies.split(";");
		for (var i in cookiesAsArray) {
			var cookieAndValue = cookiesAsArray[i];
			var cookieAndValueAsArray = cookieAndValue.split("=");
			if ("wikishare_navi_id" == cookieAndValueAsArray[0]) {
				return cookieAndValueAsArray[1];
			}
		}
		return -1;
	}

	var navigationArray = new Array();
	<c:forEach items="${model.navigationList}" var="singleNavigation">
		navigationArray['${singleNavigation.id}#${singleNavigation.name}'] = '${singleNavigation.content}';
	</c:forEach>
	
	function renderNavigationSelection() {
		document.write('<form>');
		document.write('<select onchange="javascript:renderNavigationContentById(this.selectedIndex)" onkeydown="javascript:renderNavigationContentById(this.selectedIndex)" onkeyup="javascript:renderNavigationContentById(this.selectedIndex)">');
		for (var i in navigationArray) {
			var navigationAsString = i;
			var idNameArray = navigationAsString.split('#');
			var alreadySelectedId = getNavigationIdFromCookieValue();
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
		setNavigationIdAsCookie(id);
	}
</script>


<div>
	<script type="text/javascript">
		renderNavigationSelection();		
	</script>
	<div id="navigationContent">Navigation content</div>
</div>

<h1><c:out value="${model.page.title}"/> [<c:out value="${model.page.id}"/>]</h1> 
<p style="border: 1px dotted lightgray;">
	<c:out value="${model.page.content}" escapeXml="false"/>
</p>
<%@ include file="html_footer.jsp" %>