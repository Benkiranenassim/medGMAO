
<%@ page import="medgmao.Intervention" %>
<!doctype html>
<!--Generation template modifiée par rezz-->
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'intervention.label', default: 'Intervention')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-intervention" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-intervention" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table style="border-top:0px;margin-bottom:0px">
			<tr style="background:white">
			<td width="80%">
			<div class="sousform">
			<ol class="property-list intervention" style="padding:0Px">
			
				<g:if test="${interventionInstance?.dateProgrammee}">
				<li class="fieldcontain">
					<span id="dateProgrammee-label" class="property-label"><g:message code="intervention.dateProgrammee.label" default="Date Programmee" /></span>
					
						<span class="property-value" aria-labelledby="dateProgrammee-label"><g:formatDate date="${interventionInstance?.dateProgrammee}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${interventionInstance?.dateIntervention}">
				<li class="fieldcontain">
					<span id="dateIntervention-label" class="property-label"><g:message code="intervention.dateIntervention.label" default="Date Intervention" /></span>
					
						<span class="property-value" aria-labelledby="dateIntervention-label"><g:formatDate date="${interventionInstance?.dateIntervention}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${interventionInstance?.ordonneePar}">
				<li class="fieldcontain">
					<span id="ordonneePar-label" class="property-label"><g:message code="intervention.ordonneePar.label" default="Ordonnee Par" /></span>
					
						<span class="property-value" aria-labelledby="ordonneePar-label"><g:link controller="user" action="show" id="${interventionInstance?.ordonneePar?.id}">${interventionInstance?.ordonneePar?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${interventionInstance?.localisation}">
				<li class="fieldcontain">
					<span id="localisation-label" class="property-label"><g:message code="intervention.localisation.label" default="Localisation" /></span>
					
						<span class="property-value" aria-labelledby="localisation-label"><g:link controller="localisation" action="show" id="${interventionInstance?.localisation?.id}">${interventionInstance?.localisation?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${interventionInstance?.texte}">
				<li class="fieldcontain">
					<span id="texte-label" class="property-label"><g:message code="intervention.texte.label" default="Texte" /></span>
					
						<span class="property-value" aria-labelledby="texte-label"><g:fieldValue bean="${interventionInstance}" field="texte"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${interventionInstance?.equipement}">
				<li class="fieldcontain">
					<span id="equipement-label" class="property-label"><g:message code="intervention.equipement.label" default="Equipement" /></span>
					
						<span class="property-value" aria-labelledby="equipement-label"><g:link controller="equipement" action="show" id="${interventionInstance?.equipement?.id}">${interventionInstance?.equipement?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${interventionInstance?.demande}">
				<li class="fieldcontain">
					<span id="demande-label" class="property-label"><g:message code="intervention.demande.label" default="Demande" /></span>
					
						<span class="property-value" aria-labelledby="demande-label"><g:link controller="demandeIntervention" action="show" id="${interventionInstance?.demande?.id}">${interventionInstance?.demande?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${interventionInstance?.niveauUrgence}">
				<li class="fieldcontain">
					<span id="niveauUrgence-label" class="property-label"><g:message code="intervention.niveauUrgence.label" default="Niveau Urgence" /></span>
					
						<span class="property-value" aria-labelledby="niveauUrgence-label"><g:fieldValue bean="${interventionInstance}" field="niveauUrgence"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${interventionInstance?.executeePar}">
				<li class="fieldcontain">
					<span id="executeePar-label" class="property-label"><g:message code="intervention.executeePar.label" default="Executee Par" /></span>
					
						<span class="property-value" aria-labelledby="executeePar-label"><g:link controller="user" action="show" id="${interventionInstance?.executeePar?.id}">${interventionInstance?.executeePar?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${interventionInstance?.intervenantExterne}">
				<li class="fieldcontain">
					<span id="intervenantExterne-label" class="property-label"><g:message code="intervention.intervenantExterne.label" default="Intervenant Externe" /></span>
					
						<span class="property-value" aria-labelledby="intervenantExterne-label"><g:formatBoolean boolean="${interventionInstance?.intervenantExterne}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${interventionInstance?.programmationMaintenance}">
				<li class="fieldcontain">
					<span id="programmationMaintenance-label" class="property-label"><g:message code="intervention.programmationMaintenance.label" default="Programmation Maintenance" /></span>
					
						<span class="property-value" aria-labelledby="programmationMaintenance-label"><g:link controller="programmationMaintenance" action="show" id="${interventionInstance?.programmationMaintenance?.id}">${interventionInstance?.programmationMaintenance?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${interventionInstance?.type}">
				<li class="fieldcontain">
					<span id="type-label" class="property-label"><g:message code="intervention.type.label" default="Type" /></span>
					
						<span class="property-value" aria-labelledby="type-label"><g:link controller="typeIntervention" action="show" id="${interventionInstance?.type?.id}">${interventionInstance?.type?.encodeAsHTML()}</g:link></span>
					
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
					<g:hiddenField name="id" value="${interventionInstance?.id}" />
					<g:link class="edit" action="edit" id="${interventionInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
