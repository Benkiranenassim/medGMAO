<%@ page import="medgmao.GroupeEquipements" %>

<!--Generation template modifiée par rezz-->

<div class="fieldcontain ${hasErrors(bean: groupeEquipementsInstance, field: 'libelle', 'error')} ">
	<label for="libelle">
		<g:message code="groupeEquipements.libelle.label" default="Libelle" />
		
	</label>
	<g:textField name="libelle" maxlength="150" value="${groupeEquipementsInstance?.libelle}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: groupeEquipementsInstance, field: 'equipements', 'error')} ">
	<label for="equipements">
		<g:message code="groupeEquipements.equipements.label" default="Equipements" />
		
	</label>
	<g:select name="equipements" from="${medgmao.Equipement.list()}" multiple="multiple" optionKey="id" size="5" value="${groupeEquipementsInstance?.equipements*.id}" class="many-to-many"/>
</div>

