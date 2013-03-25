<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="jwr" uri="http://jawr.net/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tls" uri="http://tiles.apache.org/tags-tiles" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<fmt:setBundle basename="memo.admin.web.server.messages" scope="session" />
	<c:set value="${pageContext.request.contextPath}" var="contextPath" scope="session" />
	<head>
		<title>
			<fmt:message key="title" />
		</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link type="text/css" rel="stylesheet" href="${contextPath}/resources/css/reset.css" />
		<link type="text/css" rel="stylesheet" href="${contextPath}/resources/theme.css" />
		<script type="text/javascript" src="${contextPath}/gwt/gwt.nocache.js"></script>
		<jwr:script src="/bundles/messages.js" />
	</head>
	<body>
		<tls:insertAttribute name="content" />
		<iframe src="javascript:''" id="__gwt_historyFrame" style="display: none;"></iframe>
	</body>
</html>
