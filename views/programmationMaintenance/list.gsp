
<%@ page import="medgmao.ProgrammationMaintenance" %>
<!doctype html>
<!--Generation template modifiée par rezz-->
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'programmationMaintenance.label', default: 'ProgrammationMaintenance')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-programmationMaintenance" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-programmationMaintenance" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<th><g:message code="programmationMaintenance.equipement.label" default="Equipement" /></th>
					
						<th><g:message code="programmationMaintenance.model.label" default="Model" /></th>
					
						<g:sortableColumn property="description" title="${message(code: 'programmationMaintenance.description.label', default: 'Description')}" />
					
						<g:sortableColumn property="interval" title="${message(code: 'programmationMaintenance.interval.label', default: 'Interval')}" />
					
						<th><g:message code="programmationMaintenance.responsable.label" default="Responsable" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${programmationMaintenanceInstanceList}" status="i" var="programmationMaintenanceInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${programmationMaintenanceInstance.id}">${fieldValue(bean: programmationMaintenanceInstance, field: "equipement")}</g:link></td>
					
						<td>${fieldValue(bean: programmationMaintenanceInstance, field: "model")}</td>
					
						<td>${fieldValue(bean: programmationMaintenanceInstance, field: "description")}</td>
					
						<td>${fieldValue(bean: programmationMaintenanceInstance, field: "interval")}</td>
					
						<td>${fieldValue(bean: programmationMaintenanceInstance, field: "responsable")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${programmationMaintenanceInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
