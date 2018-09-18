<%@ page import="mederp.Unite" %>

<!--Generation template modifiée par rezz-->

<div class="fieldcontain ${hasErrors(bean: uniteInstance, field: 'etablissement', 'error')} required">
	<label for="etablissement">
		<g:message code="unite.etablissement.label" default="Etablissement" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="etablissement" name="etablissement.id" from="${mederp.Etablissement.list()}" optionKey="id" required="" value="${uniteInstance?.etablissement?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: uniteInstance, field: 'libelle', 'error')} ">
	<label for="libelle">
		<g:message code="unite.libelle.label" default="Libelle" />
		
	</label>
	<g:textField name="libelle" maxlength="150" value="${uniteInstance?.libelle}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: uniteInstance, field: 'libelleCourt', 'error')} ">
	<label for="libelleCourt">
		<g:message code="unite.libelleCourt.label" default="Libelle Court" />
		
	</label>
	<g:textField name="libelleCourt" maxlength="15" value="${uniteInstance?.libelleCourt}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: uniteInstance, field: 'typeUnite', 'error')} ">
	<label for="typeUnite">
		<g:message code="unite.typeUnite.label" default="Type Unite" />
		
	</label>
	<g:select name="typeUnite" value="${uniteInstance?.typeUnite}" from="${['administrative','medicale','chirurgicale','medicotechnique']}" noSelection="['':'']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: uniteInstance, field: 'medicale', 'error')} ">
	<label for="medicale">
		<g:message code="unite.medicale.label" default="Medicale" />
		
	</label>
	<g:checkBox name="medicale" value="${uniteInstance?.medicale}" /> (reçois les patient?)
</div>

<div class="fieldcontain ${hasErrors(bean: uniteInstance, field: 'equipements', 'error')} ">
	<label for="equipements">
		<g:message code="unite.equipements.label" default="Equipements" />
		
	</label>
	
<ul class="one-to-many">
<g:each in="${uniteInstance?.equipements?}" var="e">
    <li><g:link controller="equipement" action="show" id="${e.id}">${e?.encodeAsHTML()}</g:link></li>
</g:each>
<li class="add">
<g:link controller="equipement" action="create" params="['unite.id': uniteInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'equipement.label', default: 'Equipement')])}</g:link>
</li>
</ul>

</div>

<div class="fieldcontain ${hasErrors(bean: uniteInstance, field: 'localisations', 'error')} ">
	<label for="localisations">
		<g:message code="unite.localisations.label" default="Localisations" />
		
	</label>
	<g:select name="localisations" from="${mederp.Localisation.findAllByTypeLocalisation(mederp.TypeLocalisation.findByLibelle('Bloc'))}" multiple="multiple" optionKey="id" size="5" value="${uniteInstance?.localisations*.id}" class="many-to-many"/>
</div>

