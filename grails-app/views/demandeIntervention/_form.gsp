<%@ page import="medgmao.DemandeIntervention" %>

<!--Generation template modifiée par rezz-->

<div class="fieldcontain ${hasErrors(bean: demandeInterventionInstance, field: 'dateDappelle', 'error')} ">
	<label for="dateDappelle">
		<g:message code="demandeIntervention.dateDappelle.label" default="Date d'appel" />
		
	</label>
	<g:datePicker name="dateDappelle" precision="day"  value="${demandeInterventionInstance?.dateDappelle}" default="none" noSelection="['': '']" />
</div>

<div class="fieldcontain ${hasErrors(bean: demandeInterventionInstance, field: 'dateResolution', 'error')} ">
	<label for="dateResolution">
		<g:message code="demandeIntervention.dateResolution.label" default="Date Resolution" />
		
	</label>
	<g:datePicker name="dateResolution" precision="day"  value="${demandeInterventionInstance?.dateResolution}" default="none" noSelection="['': '']" />
</div>

<div class="fieldcontain ${hasErrors(bean: demandeInterventionInstance, field: 'demandeePar', 'error')} required">
	<label for="demandeePar">
		<g:message code="demandeIntervention.demandeePar.label" default="Demandee Par" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="demandeePar" name="demandeePar.id" from="${mederp.User.list()}" optionKey="id" required="" value="${demandeInterventionInstance?.demandeePar?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: demandeInterventionInstance, field: 'dateDemande', 'error')} required">
	<label for="dateDemande">
		<g:message code="demandeIntervention.dateDemande.label" default="Date Demande" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="dateDemande" precision="minute"  value="${demandeInterventionInstance?.dateDemande}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: demandeInterventionInstance, field: 'localisation', 'error')} required">
	<label for="localisation">
		<g:message code="demandeIntervention.localisation.label" default="Localisation" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="localisation" name="localisation.id" from="${mederp.Localisation.list()}" optionKey="id" required="" value="${demandeInterventionInstance?.localisation?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: demandeInterventionInstance, field: 'categorie', 'error')} required">
	<label for="categorie">
		<g:message code="demandeIntervention.categorie.label" default="Categorie" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="categorie" name="categorie.id" from="${medgmao.CategorieDemande.list()}" optionKey="id" required="" value="${demandeInterventionInstance?.categorie?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: demandeInterventionInstance, field: 'description', 'error')} required">
	<label for="description">
		<g:message code="demandeIntervention.description.label" default="Description" />
		<span class="required-indicator">*</span>
	</label>
	<g:textArea name="description" cols="40" rows="5" maxlength="1000" required="" value="${demandeInterventionInstance?.description}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: demandeInterventionInstance, field: 'equipement', 'error')} ">
	<label for="equipement">
		<g:message code="demandeIntervention.equipement.label" default="Equipement" />
		
	</label>
	<g:select id="equipement" name="equipement.id" from="${medgmao.Equipement.list()}" optionKey="id" value="${demandeInterventionInstance?.equipement?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: demandeInterventionInstance, field: 'niveauUrgence', 'error')} required">
	<label for="niveauUrgence">
		<g:message code="demandeIntervention.niveauUrgence.label" default="Niveau Urgence" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="niveauUrgence" type="number" value="${demandeInterventionInstance.niveauUrgence}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: demandeInterventionInstance, field: 'blocage', 'error')} ">
	<label for="blocage">
		<g:message code="demandeIntervention.blocage.label" default="Blocage" />
		
	</label>
	<g:checkBox name="blocage" value="${demandeInterventionInstance?.blocage}" />
</div>

<div class="fieldcontain ${hasErrors(bean: demandeInterventionInstance, field: 'cloturee', 'error')} ">
	<label for="cloturee">
		<g:message code="demandeIntervention.cloturee.label" default="Cloturee" />
		
	</label>
	<g:checkBox name="cloturee" value="${demandeInterventionInstance?.cloturee}" />
</div>

<div class="fieldcontain ${hasErrors(bean: demandeInterventionInstance, field: 'interventions', 'error')} ">
	<label for="interventions">
		<g:message code="demandeIntervention.interventions.label" default="Interventions" />
		
	</label>
	
<ul class="one-to-many">
<g:each in="${demandeInterventionInstance?.interventions?}" var="i">
    <li><g:link controller="intervention" action="show" id="${i.id}">${i?.encodeAsHTML()}</g:link></li>
</g:each>
<li class="add">
<g:link controller="intervention" action="create" params="['demandeIntervention.id': demandeInterventionInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'intervention.label', default: 'Intervention')])}</g:link>
</li>
</ul>

</div>

