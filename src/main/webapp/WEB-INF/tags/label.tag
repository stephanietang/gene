<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="list" required="true" type="java.util.List" description="List name" %>
<%@ attribute name="key" required="true" %>

<c:forEach var="item" items="${list}">
	<c:if test="${item.labelKey eq key}">
		${item.labelName}
	</c:if>
</c:forEach>