/**
 * UI View base class.
 */
UIView = Backbone.View.extend({

	initialize: function() {
		$.extend(this, this.options);
	},

	/**
	 * The host ui element (jquery).
	 */
	getEl: function() {
		return this.$el;
	}
});

/**
 * Server View base class.
 * Supports server side rendering. 
 */
ServerView = UIView.extend({

	/**
	 * The url that will be called to fetch the ui content.
	 */
	getUrl: function() {
		return this.url;
	},

	/**
	 * The data that will be passed to fetch the ui content.
	 */
	getData: function() {
		return this.data || "";
	},

	/**
	 * The http method that will be used to make the call.
	 */
	getMethod: function() {
		return this.method || "GET";
	},

	/**
	 * Fetches the ui content from the server.
	 */
	render: function() {
		$.ajax({
			url: this.getUrl(),
			data: this.getData(),
			type: this.getMethod(),
			success: $.proxy(function(data, status, xhr) {
				this.getEl().html(data);
				this.setup();
			}, this)
		});
	},

	/**
	 * Callback method, after the ui content has been rendered.
	 * Used in order to setup event handlers and control logic.
	 */
	setup: function() {
	}
});

LoginPopup = UIView.extend({

	render: function() {
		$.ajax({
			url: contextPath + "/app/login/prompt",
			success: $.proxy(function(data, status, xhr) {
				var $wrap = $("<div>" + data + "</div>");
				var title = $(".ajax-title", $wrap);

				this.win = this.getEl().kendoWindow({
					title: title.html(),
					modal: true,
					deactivate: $.proxy(function() {
						this.win.destroy();
					}, this)
				}).getKendoWindow();

				this.getEl().html($wrap.html());

				this.win.center();
				this.win.open();
			}, this)
		});
	}
});

ErrorPopup = UIView.extend({

	render: function() {
		var $wrap = $("<div>" + this.responseText + "</div>");
		var title = $(".ajax-title", $wrap);

		this.win = this.getEl().kendoWindow({
			title: title.html(),
			modal: true,
			deactivate: $.proxy(function() {
				this.win.destroy();
			}, this)
		}).getKendoWindow();

		this.getEl().html($wrap.html());

		this.win.center();
		this.win.open();
	}
});
