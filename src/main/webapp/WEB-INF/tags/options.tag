<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="list" required="true" type="java.util.List" description="List name" %>
<%@ attribute name="name" required="true" %>
<%@ attribute name="key" required="false" %>

<select name="${name}" class="form-control">
    <c:forEach items="${list}" var="item">
    	<c:if test="${not empty key}">
    		<option value="${item.labelKey}" ${item.labelKey == key ? 'selected' : ''}>${item.labelName}</option>
    	</c:if>
        <c:if test="${empty key}">
        	<option value="${item.labelKey}">${item.labelName}</option>
        </c:if>
    </c:forEach>
</select>