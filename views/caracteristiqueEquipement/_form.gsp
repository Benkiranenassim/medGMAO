<%@ page import="medgmao.CaracteristiqueEquipement" %>

<!--Generation template modifiée par rezz-->

<div class="fieldcontain ${hasErrors(bean: caracteristiqueEquipementInstance, field: 'libelle', 'error')} required">
	<label for="libelle">
		<g:message code="caracteristiqueEquipement.libelle.label" default="Libelle" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="libelle" maxlength="150" required="" value="${caracteristiqueEquipementInstance?.libelle}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: caracteristiqueEquipementInstance, field: 'typeCaracteristique', 'error')} required">
	<label for="typeCaracteristique">
		<g:message code="caracteristiqueEquipement.typeCaracteristique.label" default="Type Caracteristique" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="typeCaracteristique" name="typeCaracteristique.id" from="${medgmao.TypeCaracteristique.list()}" optionKey="id" required="" value="${caracteristiqueEquipementInstance?.typeCaracteristique?.id}" class="many-to-one"/>
</div>

