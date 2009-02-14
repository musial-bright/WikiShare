/*
2009.01.06 Adam Musial-Bright
Cookie manipulation,
*/

/** Set cookie and its value. 
 * Expiration date will be set automatically for few years.
 * @param name : cookie name
 * @param value : cookie value
*/
function setCookie(name,value) {
	var date = new Date();
	date = new Date(date.getTime() +1000*60*60*24*365);
	document.cookie = name + '='+ value +'; expires='+ date.toGMTString() +';'; 
}

/** Get value from cookie.
 * @param name : cookie name
 * @return value of cookie; if cookie not available -1 will be returned
*/
function getCookieValue(name) {
	var cookies = document.cookie;
	var cookiesAsArray = cookies.split(";");
	for (var i in cookiesAsArray) {
		var cookieAndValue = cookiesAsArray[i];
		var cookieAndValueAsArray = cookieAndValue.split("=");
		if ( name == cookieAndValueAsArray[0]) {
			return cookieAndValueAsArray[1];
		}
	}
	return -1;
}
/*
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
}*/