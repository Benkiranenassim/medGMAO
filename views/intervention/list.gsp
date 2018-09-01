
<%@ page import="medgmao.Intervention" %>
<!doctype html>
<!--Generation template modifiée par rezz-->
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'intervention.label', default: 'Intervention')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-intervention" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-intervention" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="dateProgrammee" title="${message(code: 'intervention.dateProgrammee.label', default: 'Date Programmee')}" />
					
						<g:sortableColumn property="dateIntervention" title="${message(code: 'intervention.dateIntervention.label', default: 'Date Intervention')}" />
					
						<th><g:message code="intervention.ordonneePar.label" default="Ordonnee Par" /></th>
					
						<th><g:message code="intervention.localisation.label" default="Localisation" /></th>
					
						<g:sortableColumn property="texte" title="${message(code: 'intervention.texte.label', default: 'Texte')}" />
					
						<th><g:message code="intervention.equipement.label" default="Equipement" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${interventionInstanceList}" status="i" var="interventionInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${interventionInstance.id}">${fieldValue(bean: interventionInstance, field: "dateProgrammee")}</g:link></td>
					
						<td><g:formatDate date="${interventionInstance.dateIntervention}" /></td>
					
						<td>${fieldValue(bean: interventionInstance, field: "ordonneePar")}</td>
					
						<td>${fieldValue(bean: interventionInstance, field: "localisation")}</td>
					
						<td>${fieldValue(bean: interventionInstance, field: "texte")}</td>
					
						<td>${fieldValue(bean: interventionInstance, field: "equipement")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${interventionInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
