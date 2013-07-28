<jsp:directive.page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
<div class="page-header">
	<h1>Spring MVC Template</h1>
</div>
<p>Use this code to quickly setup a new Spring MVC webapp using the following frameworks;</p>
<ul class="unstyled">
	<li>Spring Framework 3.2.3</li>
	<li>Spring Security 3.1.4</li>
	<li>Hibernate Validator (JSR-303) 4.3.1</li>
	<li>Tiles 2.2</li>
	<li>Twitter Bootstrap 2.3.2</li>
	<li>jQuery 1.10.2</li>
	<li>jQuery UI 1.10.3</li>
	<li>RequireJS 2.1.8</li>
	<li>Modernizr 2.6.2</li>
</ul>
<script type="text/javascript">
	require(['common'], function() {
		require(['app/main-home']);
	});
</script>