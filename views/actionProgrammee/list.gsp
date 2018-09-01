
<%@ page import="medgmao.ActionProgrammee" %>
<!doctype html>
<!--Generation template modifiée par rezz-->
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'actionProgrammee.label', default: 'ActionProgrammee')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-actionProgrammee" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-actionProgrammee" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="libelle" title="${message(code: 'actionProgrammee.libelle.label', default: 'Libelle')}" />
					
						<g:sortableColumn property="description" title="${message(code: 'actionProgrammee.description.label', default: 'Description')}" />
					
						<th><g:message code="actionProgrammee.modelMaintenance.label" default="Model Maintenance" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${actionProgrammeeInstanceList}" status="i" var="actionProgrammeeInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${actionProgrammeeInstance.id}">${fieldValue(bean: actionProgrammeeInstance, field: "libelle")}</g:link></td>
					
						<td>${fieldValue(bean: actionProgrammeeInstance, field: "description")}</td>
					
						<td>${fieldValue(bean: actionProgrammeeInstance, field: "modelMaintenance")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${actionProgrammeeInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
