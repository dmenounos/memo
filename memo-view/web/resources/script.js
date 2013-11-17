function isNil(obj) {
	return obj == null || typeof obj == 'undefined';
}

function isObject(obj) {
	return !isNil(obj) && typeof obj == 'object';
}

function isScalar(obj) {
	return !isNil(obj) && typeof obj != 'object' && typeof obj != 'function';
}

function isPrimitive(obj) {
	return isString(obj) || isNumber(obj) || this.isBoolean(obj);
}

function isBoolean(obj) {
	return typeof obj == 'boolean' || obj instanceof Boolean;
}

function isNumber(obj) {
	return typeof obj == 'number' || obj instanceof Number;
}

function isString(obj) {
	return typeof obj == 'string' || obj instanceof String;
}

function isArray(obj) {
	return obj instanceof Array;
}

function obj(config)
{
	obj.cp(this, config);

	if (this.init) {
		this.init();
	}
}

obj.extend = function(superClass, subClassDefinition)
{
	// create prototype class
	var subClassPrototype = function() {};

	// delegates directly to superClass prototype
	subClassPrototype.prototype = superClass.prototype;

	// keep super class for convenience
	subClassPrototype.superClass = superClass;

	// create sub class
	var subClass = function() {
		// call the super constructor
		superClass.apply(this, arguments);
	};

	// our prototype is neat!
	subClass.prototype = new subClassPrototype();

	obj.cp(subClass.prototype, subClassDefinition);

	return subClass;
};

obj.cp = function(targetObject, sourceObject)
{
	if (!isObject(targetObject)) {
		targetObject = {};
	}

	for (var i = 1; i < arguments.length; i++) {
		var sourceObject = arguments[i];

		if (isObject(sourceObject)) {
			for (var property in sourceObject) {
				targetObject[property] = sourceObject[property];
			}
		}
	}

	return targetObject;
};

obj.ns = function(str, val)
{
	str = str || '';
	val = val || {};

	var ref = window;
	var parts = str.split('.');

	for (var x = 0; x < parts.length; x++) {
		var p = parts[x];

		if (!ref[p]) {
			ref[p] = {};
		}

		ref = ref[p];
	}

	obj.cp(ref, val);
};

function showLoadingMask() {
	window.loadingMaskId = setTimeout(function() {
		$('.ajaxOverlay').fadeIn('fast');
	}, 250);
}

function hideLoadingMask() {
	clearTimeout(window.loadingMaskId);
	$('.ajaxOverlay').fadeOut('fast');
}

$(function() {
	$("<div class='ajaxOverlay'>"
	+ "<div class='ajaxIndicator'></div>"
	+ "</div>").appendTo("html > body");
});
