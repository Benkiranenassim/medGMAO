<%@ page import="medgmao.ProgrammationMaintenance" %>

<!--Generation template modifiée par rezz-->

<div class="fieldcontain ${hasErrors(bean: programmationMaintenanceInstance, field: 'equipement', 'error')} required">
	<label for="equipement">
		<g:message code="programmationMaintenance.equipement.label" default="Equipement" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="equipement" name="equipement.id" from="${medgmao.Equipement.list()}" optionKey="id" required="" value="${programmationMaintenanceInstance?.equipement?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: programmationMaintenanceInstance, field: 'model', 'error')} required">
	<label for="model">
		<g:message code="programmationMaintenance.model.label" default="Model" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="model" name="model.id" from="${medgmao.ModelMaintenance.list()}" optionKey="id" required="" value="${programmationMaintenanceInstance?.model?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: programmationMaintenanceInstance, field: 'description', 'error')} ">
	<label for="description">
		<g:message code="programmationMaintenance.description.label" default="Description" />
		
	</label>
	<g:textArea name="description" cols="40" rows="5" maxlength="1000" value="${programmationMaintenanceInstance?.description}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: programmationMaintenanceInstance, field: 'interval', 'error')} required">
	<label for="interval">
		<g:message code="programmationMaintenance.interval.label" default="Interval" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="interval" type="number" value="${programmationMaintenanceInstance.interval}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: programmationMaintenanceInstance, field: 'responsable', 'error')} required">
	<label for="responsable">
		<g:message code="programmationMaintenance.responsable.label" default="Responsable" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="responsable" name="responsable.id" from="${mederp.User.list()}" optionKey="id" required="" value="${programmationMaintenanceInstance?.responsable?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: programmationMaintenanceInstance, field: 'interventions', 'error')} ">
	<label for="interventions">
		<g:message code="programmationMaintenance.interventions.label" default="Interventions" />
		
	</label>
	
<ul class="one-to-many">
<g:each in="${programmationMaintenanceInstance?.interventions?}" var="i">
    <li><g:link controller="intervention" action="show" id="${i.id}">${i?.encodeAsHTML()}</g:link></li>
</g:each>
<li class="add">
<g:link controller="intervention" action="create" params="['programmationMaintenance.id': programmationMaintenanceInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'intervention.label', default: 'Intervention')])}</g:link>
</li>
</ul>

</div>

