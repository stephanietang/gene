<%@ attribute name="list" required="true" type="java.util.List" description="List name" %>
<%-- <%@ attribute name="name" required="true" %> --%>

<select class="form-control">
	<c:forEach var="l" items="${list}">
		<option value="${l.labelKey}">${l.labelName}</option>
	</c:forEach>
</select>