<%@ include file="html_head.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!-- TinyMCE -->
<script type="text/javascript" src="/WikiShare/public/wiki/js/tiny_mce/tiny_mce.js"></script>
<script type="text/javascript">
  tinyMCE.init({
    mode : "textareas",
    theme : "advanced",
    elements : "content",
    relative_urls : false,
    content_css : "/WikiShare/public/wiki/css/tinymce.css"
  });

  function convertAndInsertAtCaret(content) {
    var html = '';
    if(content.match(/(.jpg|.jpeg|.gif|.png)$/)) {
      html = '<img src="' + content + '"/>';
    }
    if(content.match(/s[0-9]*$/)) {
            html = '<a href="wikipage/'+ content +'">'+ content +'</a>';
        }
      tinyMCE.execCommand('mceInsertContent',false,html);
  }
</script>
<!-- /TinyMCE -->


<%  // bo: controller view... %>
<div id="controllerViewContent">

	<div class="contentBox">
		<form:form method="post" commandName="wikipage">
	    	<c:set var="wikipageId" value="${wikipage.id}">
	    	</c:set> <c:set var="signature" value="${wikipage.signature}"></c:set>
	
	    	<h3><form:input path="title" size="80" cssClass="page_title" /></h3>
	    	<p>
	    	<form:checkbox path="frontPageFlag" id="frontPageFlag" />
	    	<span onclick="checkCheckbox('frontPageFlag')">Show on frontpage</span>
	    	<%
		    String formSubmitText = "Create";
		    if (request.getParameter("action") != null && 
		    		request.getParameter("action").equals("update")) {
		       formSubmitText = "Update";
		    %>
		       <input type="hidden" name="action" value="update" />
		       <form:checkbox path="skipNewVersionFlag" id="skipNewVersionFlag" />
		       <span onclick="checkCheckbox('skipNewVersionFlag')">Minor change</span>
		    <%
			}
			%>
			<a href="#" onclick="document.getElementById( 'wikipage' ).submit(); return;"><%=formSubmitText%></a> | 
			<a href="<%=W_PREFIX%>wikipage/${signature}" title="Cancel">Cancel</a>
			</p>
			<form:textarea path="content" id="content" cols="70" rows="25" />
		</form:form>
	</div>

</div> <% // eo: controller view. %>

<div id="contentRight">

	<div class="boxRight">
	  <h3 class="select">Clipboard</h3>
	  <ul>
	    <c:forEach items="${wikipage.clipboardItems}" var="clipboard">
	     <li><a href="#" onclick="convertAndInsertAtCaret('${clipboard}');return false;"><c:out value="${clipboard}"/></a></li>
	    </c:forEach>
	  </ul>
	</div>

</div>


<%@ include file="html_footer.jsp" %>