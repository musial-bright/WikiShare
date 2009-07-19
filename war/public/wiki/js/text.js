/**
 * Form and content manipulation.
 *  
 * @version 2009-07-19
 * @author Adam Musial-Bright
 */


/** Insert text at the cursor position
 * @param areaId : text area id
 * @param text : text to insert
*/
function insertAtCaret(areaId, text) {
	var txtarea = document.getElementById(areaId);
	var scrollPos = txtarea.scrollTop;
	var strPos = 0;
	var br = ((txtarea.selectionStart || txtarea.selectionStart == '0') ? "ff"
			: (document.selection ? "ie" : false));
	if (br == "ie") {
		txtarea.focus();
		var range = document.selection.createRange();
		range.moveStart('character', -txtarea.value.length);
		strPos = range.text.length;
	} else if (br == "ff")
		strPos = txtarea.selectionStart;
	var front = (txtarea.value).substring(0, strPos);
	var back = (txtarea.value).substring(strPos, txtarea.value.length);
	txtarea.value = front + text + back;
	strPos = strPos + text.length;
	if (br == "ie") {
		txtarea.focus();
		var range = document.selection.createRange();
		range.moveStart('character', -txtarea.value.length);
		range.moveStart('character', strPos);
		range.moveEnd('character', 0);
		range.select();
	} else if (br == "ff") {
		txtarea.selectionStart = strPos;
		txtarea.selectionEnd = strPos;
		txtarea.focus();
	}
	txtarea.scrollTop = scrollPos;
}

/**
 * Check or uncheck a checkbox by id.
 * @param checkbox id 
*/
function checkCheckbox(checkboxId) {
	if (document.getElementById(checkboxId).checked == false) {
		document.getElementById(checkboxId).checked = true;
		return;
	} else {
		document.getElementById(checkboxId).checked = false;
	}
}