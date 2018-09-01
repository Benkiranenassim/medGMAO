
<%@ page import="medgmao.TypeCaracteristique" %>
<!doctype html>
<!--Generation template modifiée par rezz-->
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'typeCaracteristique.label', default: 'TypeCaracteristique')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-typeCaracteristique" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-typeCaracteristique" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="libelle" title="${message(code: 'typeCaracteristique.libelle.label', default: 'Libelle')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${typeCaracteristiqueInstanceList}" status="i" var="typeCaracteristiqueInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${typeCaracteristiqueInstance.id}">${fieldValue(bean: typeCaracteristiqueInstance, field: "libelle")}</g:link></td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${typeCaracteristiqueInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
