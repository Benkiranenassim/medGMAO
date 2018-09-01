<%@ page import="medgmao.DemandeIntervention" %>
<!doctype html>
<!--Generation template modifiée par rezz-->
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'demandeIntervention.label', default: 'DemandeIntervention')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-demandeIntervention" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-demandeIntervention" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table style="border-top:0px;margin-bottom:0px">
			<tr style="background:white">
			<td width="80%">
			<div class="sousform">
			<ol class="property-list demandeIntervention" style="padding:0Px">
			
					<g:form method="post" >
				<g:hiddenField name="id" value="${demandeInterventionInstance?.id}" />
				<g:hiddenField name="version" value="${demandeInterventionInstance?.version}" />


				<div class="fieldcontain ${hasErrors(bean: demandeInterventionInstance, field: 'dateDappelle', 'error')} required">
					<label for="dateDappelle">
					<g:message code="demandeIntervention.dateDappelle.label" default="Date De l'appel" />
					<span class="required-indicator">*</span>
					</label>
				<g:datePicker name="dateDappelle" precision="minute"  value="${demandeInterventionInstance?.dateDappelle}"  />
				</div>

				<div class="fieldcontain ${hasErrors(bean: demandeInterventionInstance, field: 'fournisseurAppele', 'error')} ">
					<label for="fournisseurAppele">
					<g:message code="demandeIntervention.fournisseurAppele.label" default="Nom du fournisseur appele" />
					</label>
					<g:field name="fournisseurAppele" type="text" value="${demandeInterventionInstance.fournisseurAppele}" />
				</div>

				<fieldset class="buttons">
					<g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" />
				</fieldset>
			</g:form>

		</div>
	</body>
</html>
