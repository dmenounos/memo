<%@ attribute name="bean" type="memo.view.html.login.prompt.SimpleLoginComponent" required="true" %>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- bean fully qualified name shortcut --%>
<c:set var="fqn" value="${bean.getClass().getName()}" />

<div class="${bean.id} row">

	<div class="col-md-6">
		<form role="form">
			<div class="form-group">
				<fmt:message key="${fqn}.usernameLabel" var="usernameLabel" />
				<label for="username">${usernameLabel}</label>
				<input name="username" type="email" class="form-control" placeholder="${usernameLabel}">
			</div>
			<div class="form-group">
				<fmt:message key="${fqn}.passwordLabel" var="passwordLabel" />
				<label for="password">${passwordLabel}</label>
				<input name="password" type="password" class="form-control" placeholder="${passwordLabel}">
			</div>
			<div class="checkbox">
				<label>
					<input name="remember" type="checkbox" />
					<fmt:message key="${fqn}.rememberLabel" />
				</label>
			</div>
			<button type="submit" class="btn btn-default">
				<fmt:message key="${fqn}.signInLabel" />
			</button>
		</form>
	</div>

	<div class="col-md-6">
		<div class="info-panel">
			<fmt:message key="${fqn}.signUpInfo" />
		</div>
		<button class="btn btn-default">
			<fmt:message key="${fqn}.signUpLabel" />
		</button>
	</div>

</div>
