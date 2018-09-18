
<%@ page import="medgmao.FournisseurEquipement" %>
<!doctype html>
<!--Generation template modifiée par rezz-->
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'fournisseurEquipement.label', default: 'FournisseurEquipement')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-fournisseurEquipement" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-fournisseurEquipement" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="libelle" title="${message(code: 'fournisseurEquipement.libelle.label', default: 'Libelle')}" />
					
						<g:sortableColumn property="adresse" title="${message(code: 'fournisseurEquipement.adresse.label', default: 'Adresse')}" />
					
						<g:sortableColumn property="tel" title="${message(code: 'fournisseurEquipement.tel.label', default: 'Tel')}" />
					
						<g:sortableColumn property="tel2" title="${message(code: 'fournisseurEquipement.tel2.label', default: 'Tel2')}" />
					
						<g:sortableColumn property="email" title="${message(code: 'fournisseurEquipement.email.label', default: 'Email')}" />
					
						<g:sortableColumn property="fournisseur" title="${message(code: 'fournisseurEquipement.fournisseur.label', default: 'Fournisseur')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${fournisseurEquipementInstanceList}" status="i" var="fournisseurEquipementInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${fournisseurEquipementInstance.id}">${fieldValue(bean: fournisseurEquipementInstance, field: "libelle")}</g:link></td>
					
						<td>${fieldValue(bean: fournisseurEquipementInstance, field: "adresse")}</td>
					
						<td>${fieldValue(bean: fournisseurEquipementInstance, field: "tel")}</td>
					
						<td>${fieldValue(bean: fournisseurEquipementInstance, field: "tel2")}</td>
					
						<td>${fieldValue(bean: fournisseurEquipementInstance, field: "email")}</td>
					
						<td><g:formatBoolean boolean="${fournisseurEquipementInstance.fournisseur}" /></td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${fournisseurEquipementInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
