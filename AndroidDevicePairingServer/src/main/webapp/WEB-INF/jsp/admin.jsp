<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>
<!doctype html>
<html>
	<head>
		<meta charset="utf-8">
		<title>Hello</title>
		<%@ include file="/WEB-INF/jsp/include/bootstrap.jsp" %>
	</head>
	<body>
		<div class="container-fluid">
			<nav class="navbar navbar-default" role="navigation">
				<div class="container-fluid">
					<div class="navbar-header">
						<a class="navbar-brand" href="/admin">AndroidDevicePairing - admin</a>
					</div>
					<div class="collapse navbar-collapse">
						<ul class="nav navbar-nav">
							<li<c:if test="${page=='index'}"> class="active"</c:if>><a href="/admin">Main page</a></li>
							<li<c:if test="${page=='devices'}"> class="active"</c:if>><a href="/admin/devices">Devices</a></li>
						</ul>
						<ul class="nav navbar-nav navbar-right">
							<li><a href="/j_spring_security_logout">Logout</a></li>
						</ul>
					</div>
				</div>
			</nav>
		</div>
		
		<div class="container-fluid">
			<jsp:include page="/WEB-INF/jsp/admin/${page}.jsp" />
		</div>
	</body>
</html>
