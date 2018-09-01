
<%@ page import="mederp.Affectation" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'affectation.label', default: 'Affectation')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-affectation" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create">Nouvelle affectation</g:link></li>
			</ul>
		</div>
		<div id="list-affectation" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<th><g:message code="affectation.unite.label" default="Unité" /></th>
					
						<th><g:message code="affectation.user.label" default="User" /></th>
					
						<g:sortableColumn property="dateAffectation" title="${message(code: 'affectation.dateAffectation.label', default: 'Date Affectation')}" />
					
						<g:sortableColumn property="dateFin" title="${message(code: 'affectation.dateFin.label', default: 'Date Fin')}" />
					
						<g:sortableColumn property="parDefaut" title="${message(code: 'affectation.parDefaut.label', default: 'Par Defaut')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${affectationInstanceList}" status="i" var="affectationInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${affectationInstance.id}">${fieldValue(bean: affectationInstance, field: "unite")}</g:link></td>
					
						<td>${fieldValue(bean: affectationInstance, field: "user")}</td>
					
						<td><g:formatDate date="${affectationInstance.dateAffectation}" /></td>
					
						<td><g:formatDate date="${affectationInstance.dateFin}" /></td>
					
						<td><g:formatBoolean boolean="${affectationInstance.parDefaut}" /></td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${affectationInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
