
<%@ page import="mederp.Unite" %>
<!doctype html>
<!--Generation template modifiée par rezz-->
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'unite.label', default: 'Unite')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-unite" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-unite" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<th><g:message code="unite.etablissement.label" default="Etablissement" /></th>
					
						<g:sortableColumn property="libelle" title="${message(code: 'unite.libelle.label', default: 'Libelle')}" />
					
						<g:sortableColumn property="libelleCourt" title="${message(code: 'unite.libelleCourt.label', default: 'Libelle Court')}" />
					
						<g:sortableColumn property="typeUnite" title="${message(code: 'unite.typeUnite.label', default: 'Type Unite')}" />
					
						<g:sortableColumn property="medicale" title="${message(code: 'unite.medicale.label', default: 'Medicale')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${uniteInstanceList}" status="i" var="uniteInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td>${fieldValue(bean: uniteInstance, field: "etablissement")}</td>
					
						<td><g:link action="show" id="${uniteInstance.id}">${fieldValue(bean: uniteInstance, field: "libelle")}</g:link></td>
					
						<td>${fieldValue(bean: uniteInstance, field: "libelleCourt")}</td>
					
						<td>${fieldValue(bean: uniteInstance, field: "typeUnite")}</td>
					
						<td><g:formatBoolean boolean="${uniteInstance.medicale}" /></td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${uniteInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
