<%@ page import="medgmao.ModelMaintenance" %>

<!--Generation template modifiée par rezz-->

<div class="fieldcontain ${hasErrors(bean: modelMaintenanceInstance, field: 'libelle', 'error')} ">
	<label for="libelle">
		<g:message code="modelMaintenance.libelle.label" default="Libelle" />
		
	</label>
	<g:textField name="libelle" value="${modelMaintenanceInstance?.libelle}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: modelMaintenanceInstance, field: 'description', 'error')} ">
	<label for="description">
		<g:message code="modelMaintenance.description.label" default="Description" />
		
	</label>
	<g:textArea name="description" cols="40" rows="5" maxlength="1000" value="${modelMaintenanceInstance?.description}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: modelMaintenanceInstance, field: 'typeEquipement', 'error')} required">
	<label for="typeEquipement">
		<g:message code="modelMaintenance.typeEquipement.label" default="Type Equipement" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="typeEquipement" name="typeEquipement.id" from="${medgmao.TypeEquipement.list()}" optionKey="id" required="" value="${modelMaintenanceInstance?.typeEquipement?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: modelMaintenanceInstance, field: 'interval', 'error')} required">
	<label for="interval">
		<g:message code="modelMaintenance.interval.label" default="Interval" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="interval" type="number" value="${modelMaintenanceInstance.interval}" required=""/> jours
</div>

<div class="fieldcontain ${hasErrors(bean: modelMaintenanceInstance, field: 'actions', 'error')} ">
	<label for="actions">
		<g:message code="modelMaintenance.actions.label" default="Actions" />
		
	</label>
	
<ul class="one-to-many">
	<g:each in="${modelMaintenanceInstance?.actions?}" var="a">
		<li><g:link controller="actionProgrammee" action="show" id="${a.id}">${a?.encodeAsHTML()}</g:link></li>
	</g:each>
</ul>

</div>

