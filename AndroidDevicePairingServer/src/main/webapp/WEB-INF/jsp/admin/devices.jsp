<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>
<h2>Devices</h2>

<c:if test="${message != null}">
	<c:if test="${message == 'removed'}">
		<div class="alert alert-success" role="alert">
			<span class="glyphicon glyphicon-ok"></span> Device removed!
		</div>
	</c:if>
</c:if>

<table class="table table-striped table-bordered">
	<thead>
		<tr>
			<th>Device ID</th>
			<th>Actions</th>
		</tr>
	</thead>
	<tbody>
	<c:forEach items="${devices}" var="device">
		<tr>
			<td>${device.id}</td>
			<td>
				<a href="/admin/devices/remove?deviceid=${device.id}" class="btn btn-default">Remove</a>
			</td>
		</tr>
	</c:forEach>
	</tbody>
</table>