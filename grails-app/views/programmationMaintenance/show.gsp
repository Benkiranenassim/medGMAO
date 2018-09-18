
<%@ page import="medgmao.ProgrammationMaintenance" %>
<!doctype html>
<!--Generation template modifiée par rezz-->
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'programmationMaintenance.label', default: 'ProgrammationMaintenance')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-programmationMaintenance" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-programmationMaintenance" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table style="border-top:0px;margin-bottom:0px">
			<tr style="background:white">
			<td width="80%">
			<div class="sousform">
			<ol class="property-list programmationMaintenance" style="padding:0Px">
			
				<g:if test="${programmationMaintenanceInstance?.equipement}">
				<li class="fieldcontain">
					<span id="equipement-label" class="property-label"><g:message code="programmationMaintenance.equipement.label" default="Equipement" /></span>
					
						<span class="property-value" aria-labelledby="equipement-label"><g:link controller="equipement" action="show" id="${programmationMaintenanceInstance?.equipement?.id}">${programmationMaintenanceInstance?.equipement?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${programmationMaintenanceInstance?.model}">
				<li class="fieldcontain">
					<span id="model-label" class="property-label"><g:message code="programmationMaintenance.model.label" default="Model" /></span>
					
						<span class="property-value" aria-labelledby="model-label"><g:link controller="modelMaintenance" action="show" id="${programmationMaintenanceInstance?.model?.id}">${programmationMaintenanceInstance?.model?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${programmationMaintenanceInstance?.description}">
				<li class="fieldcontain">
					<span id="description-label" class="property-label"><g:message code="programmationMaintenance.description.label" default="Description" /></span>
					
						<span class="property-value" aria-labelledby="description-label"><g:fieldValue bean="${programmationMaintenanceInstance}" field="description"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${programmationMaintenanceInstance?.interval}">
				<li class="fieldcontain">
					<span id="interval-label" class="property-label"><g:message code="programmationMaintenance.interval.label" default="Interval" /></span>
					
						<span class="property-value" aria-labelledby="interval-label"><g:fieldValue bean="${programmationMaintenanceInstance}" field="interval"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${programmationMaintenanceInstance?.responsable}">
				<li class="fieldcontain">
					<span id="responsable-label" class="property-label"><g:message code="programmationMaintenance.responsable.label" default="Responsable" /></span>
					
						<span class="property-value" aria-labelledby="responsable-label"><g:link controller="user" action="show" id="${programmationMaintenanceInstance?.responsable?.id}">${programmationMaintenanceInstance?.responsable?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${programmationMaintenanceInstance?.interventions}">
				<li class="fieldcontain">
					<span id="interventions-label" class="property-label"><g:message code="programmationMaintenance.interventions.label" default="Interventions" /></span>
					
						<g:each in="${programmationMaintenanceInstance.interventions}" var="i">
						<span class="property-value" aria-labelledby="interventions-label"><g:link controller="intervention" action="show" id="${i.id}">${i?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
			</ol>
			</div>
			</td>
			<td>
				<div class="sousform">
					<ul  style="margin-left:18px">
						<li></li>
					</ul>
				</div>
			</td>
			</tr>
			</table>
			
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${programmationMaintenanceInstance?.id}" />
					<g:link class="edit" action="edit" id="${programmationMaintenanceInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
