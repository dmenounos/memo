<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="m" uri="http://mojo.example.org/component" %>

<%-- server side messages --%>
<fmt:setBundle basename="memo.view.server.messages" scope="request" />

<c:if test="${not empty contextUser}">
	<script type="text/javascript">
		obj.ns('session.user', {
			nickname : "${contextUser.nickname}",
			fullname : "${contextUser.fullname}",
			gender   : "${contextUser.gender}"
		});
	</script>
</c:if>

<m:child />
