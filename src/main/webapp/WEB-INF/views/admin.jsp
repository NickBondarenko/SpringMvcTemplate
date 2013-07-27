<jsp:directive.page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" />

<div id="loginContainer" class="container">
	<div class="page-header">
		<h1>Admin</h1>
	</div>
	<div id="adminContainer">
		This is the admin page...
	</div>
</div>
<script type="text/javascript">
	require(['common'], function() {
		require(['app/main-admin']);
	});
</script>