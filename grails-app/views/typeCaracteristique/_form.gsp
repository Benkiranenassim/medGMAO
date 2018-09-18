<%@ page import="medgmao.TypeCaracteristique" %>

<!--Generation template modifiée par rezz-->

<div class="fieldcontain ${hasErrors(bean: typeCaracteristiqueInstance, field: 'libelle', 'error')} required">
	<label for="libelle">
		<g:message code="typeCaracteristique.libelle.label" default="Libelle" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="libelle" maxlength="150" required="" value="${typeCaracteristiqueInstance?.libelle}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: typeCaracteristiqueInstance, field: 'caracteristiques', 'error')} ">
	<label for="caracteristiques">
		<g:message code="typeCaracteristique.caracteristiques.label" default="Caracteristiques" />
		
	</label>
	
<ul class="one-to-many">
<g:each in="${typeCaracteristiqueInstance?.caracteristiques?}" var="c">
    <li><g:link controller="caracteristiqueEquipement" action="show" id="${c.id}">${c?.encodeAsHTML()}</g:link></li>
</g:each>
<li class="add">
<g:link controller="caracteristiqueEquipement" action="create" params="['typeCaracteristique.id': typeCaracteristiqueInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'caracteristiqueEquipement.label', default: 'CaracteristiqueEquipement')])}</g:link>
</li>
</ul>

</div>

