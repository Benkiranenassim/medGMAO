
<%@ page import="medgmao.ModelMaintenance" %>
<!doctype html>
<!--Generation template modifiée par rezz-->
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'modelMaintenance.label', default: 'ModelMaintenance')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-modelMaintenance" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-modelMaintenance" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="libelle" title="${message(code: 'modelMaintenance.libelle.label', default: 'Libelle')}" />
					
						<g:sortableColumn property="description" title="${message(code: 'modelMaintenance.description.label', default: 'Description')}" />
					
						<th><g:message code="modelMaintenance.typeEquipement.label" default="Type Equipement" /></th>
					
						<g:sortableColumn property="interval" title="${message(code: 'modelMaintenance.interval.label', default: 'Interval')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${modelMaintenanceInstanceList}" status="i" var="modelMaintenanceInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${modelMaintenanceInstance.id}">${fieldValue(bean: modelMaintenanceInstance, field: "libelle")}</g:link></td>
					
						<td>${fieldValue(bean: modelMaintenanceInstance, field: "description")}</td>
					
						<td>${fieldValue(bean: modelMaintenanceInstance, field: "typeEquipement")}</td>
					
						<td>${fieldValue(bean: modelMaintenanceInstance, field: "interval")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${modelMaintenanceInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
