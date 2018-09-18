
<%@ page import="mederp.Unite" %>
<!doctype html>
<!--Generation template modifiée par rezz-->
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'unite.label', default: 'Unite')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-unite" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-unite" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table style="border-top:0px;margin-bottom:0px">
			<tr style="background:white">
			<td width="80%">
			<div class="sousform">
			<ol class="property-list unite" style="padding:0Px">
			
				<g:if test="${uniteInstance?.etablissement}">
				<li class="fieldcontain">
					<span id="etablissement-label" class="property-label"><g:message code="unite.etablissement.label" default="Etablissement" /></span>
					
						<span class="property-value" aria-labelledby="etablissement-label"><g:link controller="etablissement" action="show" id="${uniteInstance?.etablissement?.id}">${uniteInstance?.etablissement?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${uniteInstance?.libelle}">
				<li class="fieldcontain">
					<span id="libelle-label" class="property-label"><g:message code="unite.libelle.label" default="Libelle" /></span>
					
						<span class="property-value" aria-labelledby="libelle-label"><g:fieldValue bean="${uniteInstance}" field="libelle"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${uniteInstance?.libelleCourt}">
				<li class="fieldcontain">
					<span id="libelleCourt-label" class="property-label"><g:message code="unite.libelleCourt.label" default="Libelle Court" /></span>
					
						<span class="property-value" aria-labelledby="libelleCourt-label"><g:fieldValue bean="${uniteInstance}" field="libelleCourt"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${uniteInstance?.typeUnite}">
				<li class="fieldcontain">
					<span id="typeUnite-label" class="property-label"><g:message code="unite.typeUnite.label" default="Type Unite" /></span>
					
						<span class="property-value" aria-labelledby="typeUnite-label"><g:fieldValue bean="${uniteInstance}" field="typeUnite"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${uniteInstance?.medicale}">
				<li class="fieldcontain">
					<span id="medicale-label" class="property-label"><g:message code="unite.medicale.label" default="Medicale" /></span>
					
						<span class="property-value" aria-labelledby="medicale-label"><g:formatBoolean boolean="${uniteInstance?.medicale}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${uniteInstance?.equipements}">
				<li class="fieldcontain">
					<span id="equipements-label" class="property-label"><g:message code="unite.equipements.label" default="Equipements" /></span>
					
						<g:each in="${uniteInstance.equipements}" var="e">
						<span class="property-value" aria-labelledby="equipements-label"><g:link controller="equipement" action="show" id="${e.id}">${e?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
				<g:if test="${uniteInstance?.localisations}">
				<li class="fieldcontain">
					<span id="localisations-label" class="property-label"><g:message code="unite.localisations.label" default="Localisations" /></span>
					
						<g:each in="${uniteInstance.localisations}" var="l">
						<span class="property-value" aria-labelledby="localisations-label"><g:link controller="localisation" action="show" id="${l.id}">${l?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
			</ol>
			</div>
			</td>
			<td>
				<div class="sousform">
					<ul  style="margin-left:18px">
						<li></li>
					</ul>
				</div>
			</td>
			</tr>
			</table>
			
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${uniteInstance?.id}" />
					<g:link class="edit" action="edit" id="${uniteInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
