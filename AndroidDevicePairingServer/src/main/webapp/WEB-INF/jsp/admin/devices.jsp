<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>
<h2>Devices</h2>

<c:if test="${message != null}">
	<c:if test="${message == 'removed'}">
		<div id="status-message" class="alert alert-success" role="alert">
			<span class="glyphicon glyphicon-ok"></span> Device removed!
		</div>
	</c:if>
	<c:if test="${message == 'permissions_updated'}">
		<div id="status-message" class="alert alert-success" role="alert">
			<span class="glyphicon glyphicon-ok"></span> Permissions updated!
		</div>
	</c:if>
	<c:if test="${message == 'name_updated'}">
		<div id="status-message" class="alert alert-success" role="alert">
			<span class="glyphicon glyphicon-ok"></span> Name updated!
		</div>
	</c:if>
	<c:if test="${message == 'device_not_found'}">
		<div id="status-message" class="alert alert-danger" role="alert">
			<span class="glyphicon glyphicon-exclamation-sign"></span> Device not found!
		</div>
	</c:if>
	<script>
		window.history.pushState({}, "", window.location.href.split("?")[0]);
		setTimeout(function() {
			$("#status-message").slideUp(1500);
		}, 5000);
	</script>
</c:if>

<table class="table table-striped table-bordered">
	<thead>
		<tr>
			<th>Device ID</th>
			<th>Name</th>
			<th>Permission level</th>
			<th>Actions</th>
		</tr>
	</thead>
	<tbody>
	<c:forEach items="${devices}" var="device">
		<tr>
			<td>${device.id}</td>
			<td>${device.name}</td>
			<td>${device.permissionLevel}</td>
			<td>
				<a href="/admin/devices/rename?deviceid=${device.id}" class="btn btn-default"><span class="glyphicon glyphicon-pencil"></span></a>
				<a href="/admin/devices/permissions?deviceid=${device.id}" class="btn btn-default"><span class="glyphicon glyphicon-lock"></span></a>
				<a href="/admin/devices/remove?deviceid=${device.id}" class="btn btn-default"><span class="glyphicon glyphicon-remove"></span></a>
			</td>
		</tr>
	</c:forEach>
	</tbody>
</table>