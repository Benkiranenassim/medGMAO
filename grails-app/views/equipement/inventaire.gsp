<%@ page import="medgmao.Equipement" %>
<!doctype html>
<!--Generation template modifiée par rezz-->
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'equipement.label', default: 'Equipement')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-equipement" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-equipement" class="content scaffold-list" role="main">
			<h1>
			Inventaire 
				<g:if test="${localisation}">de la localisation : ${localisation}</g:if>
				<g:if test="${typeEquipement}">du type d'équipements : ${typeEquipement}</g:if>
				<g:if test="${groupeEquipements}">du groupe d'équipements : ${groupeEquipements}</g:if>
				<g:if test="${caracteristiqueEquipement}">de la caractéristique : ${caracteristiqueEquipement}</g:if>
			</h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			
			<g:if test="${equipements.size()>0}">
			<g:form method="post" controller="groupeEquipements" action="grouper">
			<table>
				<thead>
					<tr>
						<th><g:message code="equipement.choix.label" default="Choix" /></th>
						<th><g:message code="equipement.localisation.label" default="Localisation" /></th>					
						<g:sortableColumn property="libelle" title="${message(code: 'equipement.libelle.label', default: 'Libelle')}" />					
						<g:sortableColumn property="code" title="${message(code: 'equipement.code.label', default: 'Code')}" />					
						<g:sortableColumn property="marque" title="${message(code: 'equipement.marque.label', default: 'Marque')}" />					
						<th><g:message code="equipement.typeEquipement.label" default="Type Equipement" /></th>					
						<th><g:message code="equipement.action.label" default="action" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${equipements}" status="i" var="equipementInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
						<td><g:checkBox name="_equ_${equipementInstance.id}" /></td>
						<td>${fieldValue(bean: equipementInstance, field: "localisation")}</td>
						<td><g:link action="show" id="${equipementInstance.id}">${fieldValue(bean: equipementInstance, field: "libelle")}</g:link></td>
						<td>${fieldValue(bean: equipementInstance, field: "code")}</td>
						<td>${fieldValue(bean: equipementInstance, field: "marque")}</td>
						<td>${fieldValue(bean: equipementInstance, field: "typeEquipement")}</td>
						<td><g:link controller="demandeIntervention" action="create">intervention</g:link></td>
					</tr>
				</g:each>
				</tbody>
			</table>
			
			<table>
				<tr>
					<td>Mettre les equipement dans un nouveau groupe :</td>
					<td><g:textField name="groupname" maxlength="20" /></td>
					<td><g:submitButton name="grouper" action="grouper" class="save" value="Grouper" /></td>
				</tr>
				<tr>
					<td>Mettre les equipement dans un groupe existant :</td>
					<td><g:select id="groupeid" name="groupeid" from="${medgmao.GroupeEquipements.list()}" optionKey="id" class="many-to-one"/></td>
					<td><g:submitButton name="grouper" action="grouper" class="save" value="Ajouter" /></td>
				</tr>
			</table>
				 
			</span>
			</g:form>
			</g:if>
			<g:else>
				aucun matériel répertorié pour le type ou local choisi
			</g:else>
		</div>
	</body>
</html>
