<%@ page import="mederp.Localisation" %>

<!-- Generation template modifiée par rezz -->

<div class="fieldcontain ${hasErrors(bean: localisationInstance, field: 'libelle', 'error')} required">
	<label for="libelle">
		<g:message code="localisation.libelle.label" default="Libelle" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="libelle" maxlength="150" required="" value="${localisationInstance?.libelle}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: localisationInstance, field: 'libelleCourt', 'error')} required">
	<label for="libelleCourt">
		<g:message code="localisation.libelleCourt.label" default="Libelle court" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="libelleCourt" maxlength="150" required="" value="${localisationInstance?.libelleCourt}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: localisationInstance, field: 'photo', 'error')} required">
	<label for="photo">
		<g:message code="localisation.photo.label" default="Photo" />
	</label>
	<g:textField name="photo" maxlength="250" value="${localisationInstance?.photo}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: localisationInstance, field: 'capacite', 'error')} required">
	<label for="capacite">
		<g:message code="localisation.capacite.label" default="Capacité" />
	</label>
	<g:textField name="capacite" maxlength="150" value="${localisationInstance?.capacite}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: localisationInstance, field: 'superficie', 'error')} required">
	<label for="superficie">
		<g:message code="localisation.superficie.label" default="Superficie" />
	</label>
	<g:textField name="superficie" maxlength="150" value="${localisationInstance?.superficie}"/> m²
</div>

<div class="fieldcontain ${hasErrors(bean: localisationInstance, field: 'ordre', 'error')} required">
	<label for="ordre">
		<g:message code="localisation.ordre.label" default="Ordre" />
	</label>
	<g:textField name="ordre" maxlength="150" value="${localisationInstance?.ordre}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: localisationInstance, field: 'typeLocalisation', 'error')} required">
	<label for="typeLocalisation">
		<g:message code="localisation.typeLocalisation.label" default="Type Localisation" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="typeLocalisation" name="typeLocalisation.id" from="${mederp.TypeLocalisation.list()}" optionKey="id" required="" value="${localisationInstance?.typeLocalisation?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: localisationInstance, field: 'parent', 'error')} ">
	<label for="parent">
		<g:message code="localisation.parent.label" default="Parent" />
		
	</label>
	<g:select id="parent" name="parent.id" from="${mederp.Localisation.list()}" optionKey="id" value="${localisationInstance?.parent?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: localisationInstance, field: 'unite', 'error')} ">
	<label for="unite">
		<g:message code="localisation.unite.label" default="Unite" />
		
	</label>
	<g:select id="unite" name="unite.id" from="${mederp.Unite.list()}" optionKey="id" value="${localisationInstance?.unite?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

