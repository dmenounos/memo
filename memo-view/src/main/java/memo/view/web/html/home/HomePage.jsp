<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="m" uri="http://mojo.example.org/component" %>

<div class="main container">
	<div class="${bean.id} row">
		<div class="col-md-12 col">

			<h2>HOME PAGE</h2>

			<a href='${contextPath}/app/' class="home-link">HOME</a>
			<a href='${contextPath}/app/foo/bar' class="test-link">LOGIN</a>
			<a href='http://google.com/'>GOOGLE.COM</a>

		</div>
	</div>
</div>

<script>
$(function() {
	$(".test-link").on("click", function(e) {
		e.preventDefault();
		var $this = $(this);
		$.get($this.attr("href"), function() {
			alert("koko");
		});
	});
});
</script>
