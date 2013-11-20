<h2>HOME PAGE</h2>

<a href='${contextPath}/app/' class="home-link">HOME</a>
<a href='${contextPath}/app/test/login' class="login-link">LOGIN</a>
<a href='http://google.com/'>GOOGLE.COM</a>

<script>
$(function() {
	$(".login-link").on("click", function(e) {
		e.preventDefault();
		var $this = $(this);
		$.get($this.attr("href"), function() {
			alert("koko");
		});
	});
});
</script>
