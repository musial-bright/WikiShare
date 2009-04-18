/*
2009.01.06 Adam Musial-Bright
Cookie manipulation in Javascript

@requires: Javascript Prototype: www.prototypejs.org
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
	var cookieValue = -1;
	cookiesAsArray.each( function(i) {
		var cookieAndValue = i;
		
		var cookieAndValueAsArray = cookieAndValue.split("=");
		if ( name == cookieAndValueAsArray[0]) {
			cookieValue = cookieAndValueAsArray[1];
			return cookieValue;
		}
	});
	return cookieValue;
}
