<%@ page session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<ul class="search_result_list">
    <c:forEach items="${model.pages}" var="page">
        <li>
            <span class="selectme"><a href="/WikiShare/wiki/wikipage/<c:out value="${page.id}"/>"><c:out value="${page.title}" /></a></span>
            <p class="selectme_subcontent">
                <c:out value="${page.date}" /><br/>
                <c:out value="${page.content}" />
            </p>
        </li>
    </c:forEach>
</ul>