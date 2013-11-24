<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="m" uri="http://mojo.example.org/component" %>

<fmt:setBundle basename="memo.view.web.server.messages" scope="request" />

<c:if test="${not empty contextUser}">
	<script type="text/javascript">
		obj.ns('session.user', {
			nickname : "${contextUser.nickname}",
			fullname : "${contextUser.fullname}",
			gender   : "${contextUser.gender}"
		});
	</script>
</c:if>

<div id="headers">
	<div class="container">
		<div class="header row">
			<div class="col-md-9 col">

				<div class="menu">
					<div class="menu-item forum"><a href="#">Συζητήσεις</a></div>
					<div class="menu-item users"><a href="#">Μέλη</a></div>
				</div>

			</div>
			<div class="col-md-3 col">

				<form id="search" action="">
					<input id="searchField" type="text" name="search" value="αναζήτηση" />
				</form>

			</div>
		</div>
	</div>
</div>

<div id="content">

	<m:child />

</div>

<div id="footers">
	<div class="container">
		<div class="footer row">
			<div class="col-md-12 col">

				<div>&copy; memo.gr &nbsp;&nbsp;|&nbsp;&nbsp; Επικοινωνία</div>

			</div>
		</div>
	</div>
</div>
