
<%@ page import="medgmao.TypeEquipement" %>
<!doctype html>
<!--Generation template modifiée par rezz-->
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'typeEquipement.label', default: 'TypeEquipement')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-typeEquipement" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-typeEquipement" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="libelle" title="${message(code: 'typeEquipement.libelle.label', default: 'Libelle')}" />
					
						<g:sortableColumn property="prefix" title="${message(code: 'typeEquipement.prefix.label', default: 'Prefix')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${typeEquipementInstanceList}" status="i" var="typeEquipementInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${typeEquipementInstance.id}">${fieldValue(bean: typeEquipementInstance, field: "libelle")}</g:link></td>
					
						<td>${fieldValue(bean: typeEquipementInstance, field: "prefix")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
		</div>
	</body>
</html>
