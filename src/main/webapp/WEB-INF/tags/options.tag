<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="list" required="true" type="java.util.List" description="List name" %>
<%@ attribute name="name" required="true" %>
<%@ attribute name="key" required="true" %>

<select name="${name}" class="form-control">
    <c:forEach items="${list}" var="item">
        <option value="${item.labelKey}" ${item.labelKey == key ? 'selected' : ''}>${item.labelName}</option>
    </c:forEach>
</select>