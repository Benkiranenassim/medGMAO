<%@ page import="medgmao.TypeEquipement" %>

<!--Generation template modifiée par rezz-->

<div class="fieldcontain ${hasErrors(bean: typeEquipementInstance, field: 'libelle', 'error')} ">
	<label for="libelle">
		<g:message code="typeEquipement.libelle.label" default="Libelle" />
	</label>
	<g:textField name="libelle" maxlength="150" value="${typeEquipementInstance?.libelle}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: typeEquipementInstance, field: 'parent', 'error')} ">
	<label for="parent">
		<g:message code="typeEquipement.parent.label" default="Parent" />
	</label>
	<g:select id="parent" name="parent.id" from="${medgmao.TypeEquipement.findAllByCorpsIsNull()}" optionKey="id" optionValue="libelleComplet" value="${typeEquipementInstance?.parent?.id}" class="many-to-one" noSelection="['null': '']" onchange="\$('#corps').val('null')"/>
</div>


<div class="fieldcontain ${hasErrors(bean: typeEquipementInstance, field: 'corps', 'error')} ">
	<label for="corps">
		<g:message code="typeEquipement.corps.label" default="Type Organe de" />
	</label>
	<g:select id="corps" name="corps.id" from="${medgmao.TypeEquipement.findAllByCorpsIsNull()}" optionKey="id" optionValue="libelleComplet" value="${typeEquipementInstance?.corps?.id}" class="many-to-one" noSelection="['null': '']" onchange="\$('#parent').val('null')"/>
</div>

<div class="fieldcontain ${hasErrors(bean: typeEquipementInstance, field: 'prefix', 'error')} ">
	<label for="prefix">
		<g:message code="typeEquipement.prefix.label" default="Prefix" />
	</label>
	<g:textField name="prefix" value="${typeEquipementInstance?.prefix}"/>
</div>


<div class="fieldcontain ${hasErrors(bean: typeEquipementInstance, field: 'responsable', 'error')} ">
	<label for="corps">
		<g:message code="typeEquipement.responsable.label" default="Responsable" />
	</label>
	<g:select id="responsable" name="responsable.id" from="${mederp.User.list()}" optionKey="id" value="${typeEquipementInstance?.responsable?.id}" class="many-to-one" noSelection="['null': '']" />
</div>


