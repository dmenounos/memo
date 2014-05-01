<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags/memo/view/html/login/prompt" %>

<%-- bean fully qualified name shortcut --%>
<c:set var="fqn" value="${bean.getClass().getName()}" />

<div class="main container">
	<div class="${bean.id} row">
		<div class="col-md-12 col">

			<ul class="nav nav-tabs">
				<li class="active">
					<a href="#basic-prompt" data-toggle="tab">
						<fmt:message key="${fqn}.basic" />
					</a>
				</li>
				<li>
					<a href="#openid-prompt" data-toggle="tab">
						<fmt:message key="${fqn}.openID" />
					</a>
				</li>
			</ul>

			<div class="tab-content">
				<div class="tab-pane active" id="basic-prompt">
					<m:SimpleLoginComponent bean="${bean.simpleLoginComponent}" />
				</div>
				<div class="tab-pane" id="openid-prompt">
					<m:OpenIDLoginComponent bean="${bean.openIDLoginComponent}" />
				</div>
			</div>

		</div>
	</div>
</div>
