<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>
<h2>Rename device <b>${device.name} <i>(${device.id})</i></b></h2>
<form method="POST" action="?">
	<input type="hidden" name="deviceid" value="${device.id}">
	<input class="form-control" name="name" type="text" value="${device.name}">
	<br />
	<button type="submit" class="btn btn-default"><span class="glyphicon glyphicon-floppy-disk"></span></button>
</form>