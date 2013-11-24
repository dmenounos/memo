<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="m" uri="http://mojo.example.org/component" %>

<fmt:message key="memo.view.web.client.login.OpenIDPanel.buttonsLabel"  var="buttonsLabel" />
<fmt:message key="memo.view.web.client.login.OpenIDPanel.emptyOpenID"   var="emptyOpenID" />
<fmt:message key="memo.view.web.client.login.OpenIDPanel.openidInfo"    var="openidInfo" />
<fmt:message key="memo.view.web.client.login.OpenIDPanel.openidLabel"   var="openidLabel" />
<fmt:message key="memo.view.web.client.login.OpenIDPanel.submitButton"  var="submitButton" />

<div class="container">
	<div class="OpenIDPanel row">

		<div class="centerPanel col-md-7">
			<h3>${buttonsLabel}</h3>
			<div class="buttonsPanel row">
				<div class="col-md-12">
					<button class="btn btn-default btn-google" title="Google"></button>
					<button class="btn btn-default btn-yahoo"  title="Yahoo"></button>
					<button class="btn btn-default btn-openid" title="OpenID"></button>
				</div>
			</div>
			<div class="formPanel row">
				<div class="col-md-12">
					<label for="openid_identifier">${openidLabel}</label>
					<div class="input-group">
						<input type="text" name="openid_identifier" class="form-control" />
						<span class="input-group-btn">
							<button class="btn btn-default btn-submit">${submitButton}</button>
						</span>
					</div>
				</div>
			</div>
		</div>

		<div class="eastPanel col-md-5 col">
			${openidInfo}
		</div>

	</div>
</div>

<script>
$(function() {
	var OpenIDPanel = UIView.extend({

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

	(new OpenIDPanel({ el: ".OpenIDPanel" })).render();
});
</script>
