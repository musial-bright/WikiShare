/* Wikishare navigations.
 *
 * @author Adam Musial-Bright
 * @require Prototype JavaScript framework, version 1.6.0.3 or higher
 * @require cookie.js
*/

/*
 * @requires You have to vreate a navigation array first in you jsp!

  var navigationArray = new Hash();
  <c:forEach items="${model.navigationList}" var="singleNavigation">
    navigationArray.set('${singleNavigation.id}#${singleNavigation.name}','<c:out value="${singleNavigation.content}" escapeXml="false" />');
  </c:forEach>
*/

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
