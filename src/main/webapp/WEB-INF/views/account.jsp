<jsp:directive.page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" />
<div id="accountContainer">
	<div class="page-header">
		<h1>Account</h1>
	</div>
	<div id="accountContent">
		This is the account page...
	</div>
</div>
<script type="text/javascript">
	require(['common'], function() {
		require(['app/account']);
	});
</script>