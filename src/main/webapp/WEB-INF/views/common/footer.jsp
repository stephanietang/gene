<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<!-- Footer -->
<footer class="footer" role="contentinfo">
	<div class="container">
		<p class="text-muted"><spring:message code="footer.copyright" /></p>
		
		<!-- Test web page cache, will be deleted -->
		<p><%=new Date()%></p>
	</div>
	
</footer>
</body>
</html>