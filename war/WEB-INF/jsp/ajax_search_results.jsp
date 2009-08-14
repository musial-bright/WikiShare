<%@ page session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<ul class="search_result_list">
    <c:forEach items="${model.pages}" var="page">
        <li class="selectme">
            <a href="/WikiShare/wiki/wikipage/${page.signature}"><c:out value="${page.title}" />
	        <p class="selectme_subcontent">
                <c:out value="${page.date}" /><br/>
                <c:out value="${page.content}" />
	        </p>
	        </a>
        </li>
    </c:forEach>
</ul>