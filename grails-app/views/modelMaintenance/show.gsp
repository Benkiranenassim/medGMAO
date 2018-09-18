
<%@ page import="medgmao.ModelMaintenance" %>
<!doctype html>
<!--Generation template modifiée par rezz-->
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'modelMaintenance.label', default: 'ModelMaintenance')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-modelMaintenance" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-modelMaintenance" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table style="border-top:0px;margin-bottom:0px">
			<tr style="background:white">
			<td width="80%">
			<div class="sousform">
			<ol class="property-list modelMaintenance" style="padding:0Px">
			
				<g:if test="${modelMaintenanceInstance?.libelle}">
				<li class="fieldcontain">
					<span id="libelle-label" class="property-label"><g:message code="modelMaintenance.libelle.label" default="Libelle" /></span>
					
						<span class="property-value" aria-labelledby="libelle-label"><g:fieldValue bean="${modelMaintenanceInstance}" field="libelle"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${modelMaintenanceInstance?.description}">
				<li class="fieldcontain">
					<span id="description-label" class="property-label"><g:message code="modelMaintenance.description.label" default="Description" /></span>
					
						<span class="property-value" aria-labelledby="description-label"><g:fieldValue bean="${modelMaintenanceInstance}" field="description"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${modelMaintenanceInstance?.typeEquipement}">
				<li class="fieldcontain">
					<span id="typeEquipement-label" class="property-label"><g:message code="modelMaintenance.typeEquipement.label" default="Type Equipement" /></span>
					
						<span class="property-value" aria-labelledby="typeEquipement-label"><g:link controller="typeEquipement" action="show" id="${modelMaintenanceInstance?.typeEquipement?.id}">${modelMaintenanceInstance?.typeEquipement?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${modelMaintenanceInstance?.interval}">
				<li class="fieldcontain">
					<span id="interval-label" class="property-label"><g:message code="modelMaintenance.interval.label" default="Interval" /></span>
					
						<span class="property-value" aria-labelledby="interval-label"><g:fieldValue bean="${modelMaintenanceInstance}" field="interval"/> jours</span>
					
				</li>
				</g:if>
			
				<g:if test="${modelMaintenanceInstance?.actions}">
				<li class="fieldcontain">
					<span id="actions-label" class="property-label"><g:message code="modelMaintenance.actions.label" default="Actions" /></span>
					
						<g:each in="${modelMaintenanceInstance.actions}" var="a">
						<span class="property-value" aria-labelledby="actions-label"><g:link controller="actionProgrammee" action="show" id="${a.id}">${a?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
			</ol>
			</div>
			</td>
			<td>
				<div class="sousform">
					Ajouter :
					<ul  style="margin-left:18px">
						<li>
							<g:link controller="actionProgrammee" action="create" params="['modelMaintenance.id': modelMaintenanceInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'actionProgrammee.label', default: 'Action')])}</g:link>
						</li>
					</ul>
					Lister :
					<ul  style="margin-left:18px">
						<li>
							<g:link controller="modelMaintenance" action="listeEquipements" id="${modelMaintenanceInstance?.id}">Equipements</g:link>
						</li>
					</ul>
				</div>
			</td>
			</tr>
			</table>
			
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${modelMaintenanceInstance?.id}" />
					<g:link class="edit" action="edit" id="${modelMaintenanceInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
