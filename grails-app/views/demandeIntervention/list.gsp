
<%@ page import="medgmao.DemandeIntervention" %>
<!doctype html>
<!--Generation template modifiée par rezz-->
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'demandeIntervention.label', default: 'DemandeIntervention')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-demandeIntervention" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-demandeIntervention" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="dateResolution" title="${message(code: 'demandeIntervention.dateResolution.label', default: 'Date Resolution')}" />
					
						<th><g:message code="demandeIntervention.demandeePar.label" default="Demandee Par" /></th>
					
						<g:sortableColumn property="dateDemande" title="${message(code: 'demandeIntervention.dateDemande.label', default: 'Date Demande')}" />
					
						<th><g:message code="demandeIntervention.localisation.label" default="Localisation" /></th>
					
						<th><g:message code="demandeIntervention.categorie.label" default="Categorie" /></th>
					
						<g:sortableColumn property="description" title="${message(code: 'demandeIntervention.description.label', default: 'Description')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${demandeInterventionInstanceList}" status="i" var="demandeInterventionInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${demandeInterventionInstance.id}">${fieldValue(bean: demandeInterventionInstance, field: "dateResolution")}</g:link></td>
					
						<td>${fieldValue(bean: demandeInterventionInstance, field: "demandeePar")}</td>
					
						<td><g:formatDate date="${demandeInterventionInstance.dateDemande}" /></td>
					
						<td>${fieldValue(bean: demandeInterventionInstance, field: "localisation")}</td>
					
						<td>${fieldValue(bean: demandeInterventionInstance, field: "categorie")}</td>
					
						<td>${fieldValue(bean: demandeInterventionInstance, field: "description")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${demandeInterventionInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
