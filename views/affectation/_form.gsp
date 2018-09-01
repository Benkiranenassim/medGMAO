<%@ page import="mederp.Affectation" %>



<div class="fieldcontain ${hasErrors(bean: affectationInstance, field: 'unite', 'error')} required">
	<label for="unite">
		<g:message code="affectation.unite.label" default="Unité" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="unite" name="unite.id" from="${mederp.Unite.list()}" optionKey="id" required="" value="${affectationInstance?.unite?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: affectationInstance, field: 'user', 'error')} required">
	<label for="user">
		<g:message code="affectation.user.label" default="User" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="user" name="user.id" from="${mederp.User.list()}" optionKey="id" required="" value="${affectationInstance?.user?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: affectationInstance, field: 'dateAffectation', 'error')} required">
	<label for="dateAffectation">
		<g:message code="affectation.dateAffectation.label" default="Date Affectation" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="dateAffectation" precision="day"  value="${affectationInstance?.dateAffectation}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: affectationInstance, field: 'dateFin', 'error')} ">
	<label for="dateFin">
		<g:message code="affectation.dateFin.label" default="Date Fin" />
		
	</label>
	<g:datePicker name="dateFin" precision="day"  value="${affectationInstance?.dateFin}" default="none" noSelection="['': '']" />
</div>

<div class="fieldcontain ${hasErrors(bean: affectationInstance, field: 'parDefaut', 'error')} ">
	<label for="parDefaut">
		<g:message code="affectation.parDefaut.label" default="Par Defaut" />
		
	</label>
	<g:checkBox name="parDefaut" value="${affectationInstance?.parDefaut}" />
</div>

