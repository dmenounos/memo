<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- 1st render --%>
<%-- browser will create the session cookie --%>
<%-- however java container does not know it yet --%>
<%-- as such jsessionid will be present in the redirect url --%>

<c:if test="${empty sessionScope.jsessionrd}">
	<c:set var="jsessionrd" value="true" scope="session" />
	<c:redirect url="/" />
</c:if>

<%-- 2nd render --%>
<%-- java container detects browser session cookie --%>
<%-- as such the redirect url will now be clean --%>

<c:if test="${not empty sessionScope.jsessionrd}">
	<c:redirect url="/app/" />
</c:if>
