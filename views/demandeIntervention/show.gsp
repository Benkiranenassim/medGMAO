
<%@ page import="medgmao.DemandeIntervention" %>
<!doctype html>
<!--Generation template modifiée par rezz-->
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'demandeIntervention.label', default: 'DemandeIntervention')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-demandeIntervention" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-demandeIntervention" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table style="border-top:0px;margin-bottom:0px">
			<tr style="background:white">
			<td width="80%">
			<div class="sousform">
			<ol class="property-list demandeIntervention" style="padding:0Px">
			
				<g:if test="${demandeInterventionInstance?.dateResolution}">
				<li class="fieldcontain">
					<span id="dateResolution-label" class="property-label"><g:message code="demandeIntervention.dateResolution.label" default="Date Resolution" /></span>
					
						<span class="property-value" aria-labelledby="dateResolution-label"><g:formatDate date="${demandeInterventionInstance?.dateResolution}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${demandeInterventionInstance?.demandeePar}">
				<li class="fieldcontain">
					<span id="demandeePar-label" class="property-label"><g:message code="demandeIntervention.demandeePar.label" default="Demandee Par" /></span>
					
						<span class="property-value" aria-labelledby="demandeePar-label"><g:link controller="user" action="show" id="${demandeInterventionInstance?.demandeePar?.id}">${demandeInterventionInstance?.demandeePar?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${demandeInterventionInstance?.dateDemande}">
				<li class="fieldcontain">
					<span id="dateDemande-label" class="property-label"><g:message code="demandeIntervention.dateDemande.label" default="Date Demande" /></span>
					
						<span class="property-value" aria-labelledby="dateDemande-label"><g:formatDate date="${demandeInterventionInstance?.dateDemande}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${demandeInterventionInstance?.localisation}">
				<li class="fieldcontain">
					<span id="localisation-label" class="property-label"><g:message code="demandeIntervention.localisation.label" default="Localisation" /></span>
					
						<span class="property-value" aria-labelledby="localisation-label"><g:link controller="localisation" action="show" id="${demandeInterventionInstance?.localisation?.id}">${demandeInterventionInstance?.localisation?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${demandeInterventionInstance?.categorie}">
				<li class="fieldcontain">
					<span id="categorie-label" class="property-label"><g:message code="demandeIntervention.categorie.label" default="Categorie" /></span>
					
						<span class="property-value" aria-labelledby="categorie-label"><g:link controller="categorieDemande" action="show" id="${demandeInterventionInstance?.categorie?.id}">${demandeInterventionInstance?.categorie?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${demandeInterventionInstance?.description}">
				<li class="fieldcontain">
					<span id="description-label" class="property-label"><g:message code="demandeIntervention.description.label" default="Description" /></span>
					
						<span class="property-value" aria-labelledby="description-label"><g:fieldValue bean="${demandeInterventionInstance}" field="description"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${demandeInterventionInstance?.equipement}">
				<li class="fieldcontain">
					<span id="equipement-label" class="property-label"><g:message code="demandeIntervention.equipement.label" default="Equipement" /></span>
					
						<span class="property-value" aria-labelledby="equipement-label"><g:link controller="equipement" action="show" id="${demandeInterventionInstance?.equipement?.id}">${demandeInterventionInstance?.equipement?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${demandeInterventionInstance?.niveauUrgence}">
				<li class="fieldcontain">
					<span id="niveauUrgence-label" class="property-label"><g:message code="demandeIntervention.niveauUrgence.label" default="Niveau Urgence" /></span>
					
						<span class="property-value" aria-labelledby="niveauUrgence-label"><g:fieldValue bean="${demandeInterventionInstance}" field="niveauUrgence"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${demandeInterventionInstance?.blocage}">
				<li class="fieldcontain">
					<span id="blocage-label" class="property-label"><g:message code="demandeIntervention.blocage.label" default="Blocage" /></span>
					
						<span class="property-value" aria-labelledby="blocage-label"><g:formatBoolean boolean="${demandeInterventionInstance?.blocage}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${demandeInterventionInstance?.cloturee}">
				<li class="fieldcontain">
					<span id="cloturee-label" class="property-label"><g:message code="demandeIntervention.cloturee.label" default="Cloturee" /></span>
					
						<span class="property-value" aria-labelledby="cloturee-label"><g:formatBoolean boolean="${demandeInterventionInstance?.cloturee}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${demandeInterventionInstance?.interventions}">
				<li class="fieldcontain">
					<span id="interventions-label" class="property-label"><g:message code="demandeIntervention.interventions.label" default="Interventions" /></span>
					
						<g:each in="${demandeInterventionInstance.interventions}" var="i">
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
						<li><g:link action="infoFournisseur" params="[id:demandeInterventionInstance.id]" target="_blank">Contacter le fournisseur</g:link></li>
					</ul>
				</div>
			</td>
			</tr>
			</table>
			
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${demandeInterventionInstance?.id}" />
					<g:link class="edit" action="edit" id="${demandeInterventionInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
