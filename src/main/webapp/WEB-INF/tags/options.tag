<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ attribute name="list" required="true" type="java.util.List" description="List name" %>
<%@ attribute name="name" required="true" %>

<form:select path="${name}" class="form-control">
    <form:options items="${list}" itemValue="labelKey" itemLabel="labelName" />
</form:select>