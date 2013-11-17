<%@ taglib prefix="jwr" uri="http://jawr.net/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="m" uri="http://mojo.example.org/component" %>

<fmt:setBundle basename="memo.view.web.server.messages" scope="session" />
<c:set value="${pageContext.request.contextPath}" var="contextPath" scope="session" />

<jwr:script src="/bundles/messages.js" />

<c:if test="${not empty contextUser}">
	<script type="text/javascript">
		obj.ns('session.user', {
			nickname : "${contextUser.nickname}",
			fullname : "${contextUser.fullname}",
			gender   : "${contextUser.gender}"
		});
	</script>
</c:if>

<div id="root">

<!-- outer-alpha -->

<div class="headers">
	<div class="container_12">
		<div class="grid_col grid_9">

			<ul id="menu">
				<li id="menu-forum"><a href="#">Συζητήσεις</a></li>
				<li id="menu-users"><a href="#">Μέλη</a></li>
			</ul>

		</div>
		<div class="grid_col grid_3">

			<form id="search" action="">
				<input id="searchField" type="text" name="search" value="αναζήτηση" />
			</form>

		</div>
		<div class="clear"></div>
	</div>
</div>

<!-- inner-alpha -->

<div class="content body">
	<div class="container_12">
		<div class="grid_col grid_12">

			<m:child />

		</div>
		<div class="clear"></div>
	</div>
</div>

<!-- inner-omega -->

<div class="footers">
	<div class="container_12">
		<div class="grid_col grid_12">

			<div>&copy; memo.gr &nbsp;&nbsp;|&nbsp;&nbsp; Επικοινωνία</div>

		</div>
		<div class="clear"></div>
	</div>
</div>

<!-- outer-omega -->

</div>

<iframe src="javascript:''" id="__gwt_historyFrame" style="display: none;"></iframe>
