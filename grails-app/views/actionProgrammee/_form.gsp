<%@ page import="medgmao.ActionProgrammee" %>

<!--Generation template modifiée par rezz-->

<div class="fieldcontain ${hasErrors(bean: actionProgrammeeInstance, field: 'libelle', 'error')} ">
	<label for="libelle">
		<g:message code="actionProgrammee.libelle.label" default="Libelle" />
		
	</label>
	<g:textField name="libelle" value="${actionProgrammeeInstance?.libelle}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: actionProgrammeeInstance, field: 'description', 'error')} ">
	<label for="description">
		<g:message code="actionProgrammee.description.label" default="Description" />
		
	</label>
	<g:textArea name="description" cols="40" rows="5" maxlength="1000" value="${actionProgrammeeInstance?.description}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: actionProgrammeeInstance, field: 'modelMaintenance', 'error')} required">
	<label for="modelMaintenance">
		<g:message code="actionProgrammee.modelMaintenance.label" default="Model Maintenance" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="modelMaintenance" name="modelMaintenance.id" from="${medgmao.ModelMaintenance.list()}" optionKey="id" required="" value="${actionProgrammeeInstance?.modelMaintenance?.id}" class="many-to-one"/>
</div>

