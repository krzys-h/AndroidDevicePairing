<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>
<h2>Permissions for device <i>${device.id}</i></h2>
<form method="POST" action="?">
	<input type="hidden" name="deviceid" value="${device.id}" />
	<select name="permissionLevel" class="form-control">
		<option value="admin"<c:if test="${permissions == 'admin'}"> selected="selected"</c:if>>Admin</option>
		<option value="editor"<c:if test="${permissions == 'editor'}"> selected="selected"</c:if>>Editor</option>
		<option value="user"<c:if test="${permissions == 'user'}"> selected="selected"</c:if>>User</option>
		<option value="nobody"<c:if test="${permissions == null}"> selected="selected"</c:if>>Nobody</option>
	</select>
	<br />
	<button type="submit" class="btn btn-default"><span class="glyphicon glyphicon-floppy-disk"></span></button>
</form>