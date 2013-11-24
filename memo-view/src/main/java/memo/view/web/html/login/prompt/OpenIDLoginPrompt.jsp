<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="m" uri="http://mojo.example.org/component" %>

<fmt:message key="memo.view.web.html.login.prompt.OpenIDLoginPrompt.buttonsLabel" var="buttonsLabel" />
<fmt:message key="memo.view.web.html.login.prompt.OpenIDLoginPrompt.emptyOpenID"  var="emptyOpenID" />
<fmt:message key="memo.view.web.html.login.prompt.OpenIDLoginPrompt.openidInfo"   var="openidInfo" />
<fmt:message key="memo.view.web.html.login.prompt.OpenIDLoginPrompt.openidLabel"  var="openidLabel" />
<fmt:message key="memo.view.web.html.login.prompt.OpenIDLoginPrompt.submitButton" var="submitButton" />

<div class="${bean.id} row">

	<div class="col-md-7">
		<h3>${buttonsLabel}</h3>
		<div class="buttons-panel">
			<button class="btn btn-default btn-google" title="Google"></button>
			<button class="btn btn-default btn-yahoo"  title="Yahoo"></button>
			<button class="btn btn-default btn-openid" title="OpenID"></button>
		</div>
		<div class="form-panel">
			<label for="openid_identifier">${openidLabel}</label>
			<div class="input-group">
				<input type="text" name="openid_identifier" class="form-control" />
				<span class="input-group-btn">
					<button class="btn btn-default btn-submit">${submitButton}</button>
				</span>
			</div>
		</div>
	</div>

	<div class="col-md-5">
		<div class="info-panel">${openidInfo}</div>
	</div>

</div>

<script>
$(function() {
	var OpenIDLoginPrompt = UIView.extend({

		render: function() {
			$(".btn-google", this.getEl()).on("click", $.proxy(function() {
				this.openPopup1("https://www.google.com/accounts/o8/id", 500, 500);
			}, this));

			$(".btn-yahoo", this.getEl()).on("click", $.proxy(function() {
				this.openPopup1("http://open.login.yahooapis.com/openid20/www.yahoo.com/xrds", 500, 500);
			}, this));

			$(".btn-submit", this.getEl()).on("click", $.proxy(function() {
				var $openidField = $("input[name=openid_identifier]", this.getEl());

				if (!$openidField.val()) {
					alert("${emptyOpenID}");
					return;
				}

				$openidField.prop('disabled', true);
				$(this).prop('disabled', true);

				this.openPopup1($openidField.val(), 700, 500);
			}, this));
		},

		openPopup1: function(identifier, width, height) {
			var setupURL = contextPath + "/app/login/openid/setup";
			var url = setupURL + "?openid_identifier=" + encodeURIComponent(identifier);
			var features = "width=" + width + ",height=" + height;
			this.openPopup2(url, features);
		},

		openPopup2: function(url, features) {
			var name = "openid_popup";
			var popup = window.open("", name, features);
			popup.document.write("Loading...");
			popup.document.close();
			window.open(url, name);
		}
	});

	(new OpenIDLoginPrompt({ el: ".OpenIDLoginPrompt" })).render();
});
</script>
