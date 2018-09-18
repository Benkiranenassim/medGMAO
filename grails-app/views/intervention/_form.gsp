<%@ page import="medgmao.Intervention" %>

<!--Generation template modifiée par rezz-->

<div class="fieldcontain ${hasErrors(bean: interventionInstance, field: 'dateProgrammee', 'error')} required">
	<label for="dateProgrammee">
		<g:message code="intervention.dateProgrammee.label" default="Date Programmee" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="dateProgrammee" precision="day"  value="${interventionInstance?.dateProgrammee}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: interventionInstance, field: 'dateIntervention', 'error')} ">
	<label for="dateIntervention">
		<g:message code="intervention.dateIntervention.label" default="Date Intervention" />
		
	</label>
	<g:datePicker name="dateIntervention" precision="day"  value="${interventionInstance?.dateIntervention}" default="none" noSelection="['': '']" />
</div>

<div class="fieldcontain ${hasErrors(bean: interventionInstance, field: 'ordonneePar', 'error')} ">
	<label for="ordonneePar">
		<g:message code="intervention.ordonneePar.label" default="Ordonnee Par" />
		
	</label>
	<g:select id="ordonneePar" name="ordonneePar.id" from="${mederp.User.list()}" optionKey="id" value="${interventionInstance?.ordonneePar?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: interventionInstance, field: 'localisation', 'error')} required">
	<label for="localisation">
		<g:message code="intervention.localisation.label" default="Localisation" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="localisation" name="localisation.id" from="${mederp.Localisation.list()}" optionKey="id" required="" value="${interventionInstance?.localisation?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: interventionInstance, field: 'texte', 'error')} required">
	<label for="texte">
		<g:message code="intervention.texte.label" default="Texte" />
		<span class="required-indicator">*</span>
	</label>
	<g:textArea name="texte" cols="40" rows="5" maxlength="1000" required="" value="${interventionInstance?.texte}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: interventionInstance, field: 'equipement', 'error')} ">
	<label for="equipement">
		<g:message code="intervention.equipement.label" default="Equipement" />
		
	</label>
	<g:select id="equipement" name="equipement.id" from="${medgmao.Equipement.list()}" optionKey="id" value="${interventionInstance?.equipement?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: interventionInstance, field: 'demande', 'error')} ">
	<label for="demande">
		<g:message code="intervention.demande.label" default="Demande" />
		
	</label>
	<g:select id="demande" name="demande.id" from="${medgmao.DemandeIntervention.list()}" optionKey="id" value="${interventionInstance?.demande?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: interventionInstance, field: 'niveauUrgence', 'error')} required">
	<label for="niveauUrgence">
		<g:message code="intervention.niveauUrgence.label" default="Niveau Urgence" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="niveauUrgence" type="number" value="${interventionInstance.niveauUrgence}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: interventionInstance, field: 'executeePar', 'error')} required">
	<label for="executeePar">
		<g:message code="intervention.executeePar.label" default="Executee Par" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="executeePar" name="executeePar.id" from="${mederp.User.list()}" optionKey="id" required="" value="${interventionInstance?.executeePar?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: interventionInstance, field: 'intervenantExterne', 'error')} ">
	<label for="intervenantExterne">
		<g:message code="intervention.intervenantExterne.label" default="Intervenant Externe" />
		
	</label>
	<g:checkBox name="intervenantExterne" value="${interventionInstance?.intervenantExterne}" />
</div>

<div class="fieldcontain ${hasErrors(bean: interventionInstance, field: 'programmationMaintenance', 'error')} required">
	<label for="programmationMaintenance">
		<g:message code="intervention.programmationMaintenance.label" default="Programmation Maintenance" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="programmationMaintenance" name="programmationMaintenance.id" from="${medgmao.ProgrammationMaintenance.list()}" optionKey="id" required="" value="${interventionInstance?.programmationMaintenance?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: interventionInstance, field: 'type', 'error')} required">
	<label for="type">
		<g:message code="intervention.type.label" default="Type" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="type" name="type.id" from="${medgmao.TypeIntervention.list()}" optionKey="id" required="" value="${interventionInstance?.type?.id}" class="many-to-one"/>
</div>

