<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="m" uri="http://mojo.example.org/component" %>

<fmt:message key="memo.view.web.html.login.prompt.LoginPromptPage.basic"  var="basic" />
<fmt:message key="memo.view.web.html.login.prompt.LoginPromptPage.openID" var="openID" />

<div class="main container">
	<div class="${bean.id} row">
		<div class="col-md-12 col">

			<ul class="nav nav-tabs">
				<li class="active">
					<a href="#basic-prompt" data-toggle="tab">${basic}</a></li>
				<li>
					<a href="#openid-prompt" data-toggle="tab">${openID}</a>
				</li>
			</ul>

			<div class="tab-content">
				<div class="tab-pane active" id="basic-prompt">
					<m:component key="BasicLoginPrompt" />
				</div>
				<div class="tab-pane" id="openid-prompt">
					<m:component key="OpenIDLoginPrompt" />
				</div>
			</div>

		</div>
	</div>
</div>
