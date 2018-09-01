<%@ page import="medgmao.TypeCaracteristique" %>
<!doctype html>
<!--Generation template modifiée par rezz-->
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'typeCaracteristique.label', default: 'TypeCaracteristique')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-typeCaracteristique" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-typeCaracteristique" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table style="border-top:0px;margin-bottom:0px">
			<tr style="background:white">
			<td width="80%">
			<div class="sousform">
			<ol class="property-list typeCaracteristique" style="padding:0Px">
			
				<g:if test="${typeCaracteristiqueInstance?.libelle}">
				<li class="fieldcontain">
					<span id="libelle-label" class="property-label"><g:message code="typeCaracteristique.libelle.label" default="Libelle" /></span>
					
						<span class="property-value" aria-labelledby="libelle-label"><g:fieldValue bean="${typeCaracteristiqueInstance}" field="libelle"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${typeCaracteristiqueInstance?.caracteristiques}">
				<li class="fieldcontain">
					<span id="caracteristiques-label" class="property-label"><g:message code="typeCaracteristique.caracteristiques.label" default="Caracteristiques" /></span>
					
						<g:each in="${typeCaracteristiqueInstance.caracteristiques}" var="c">
						<span class="property-value" aria-labelledby="caracteristiques-label"><g:link controller="caracteristiqueEquipement" action="show" id="${c.id}">${c?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
			</ol>
			</div>
			</td>
			<td>
				<div class="sousform">
					<ul  style="margin-left:18px">
						Ajouter
						<li><g:link controller="caracteristiqueEquipement" action="create" params="['typeCaracteristique.id': typeCaracteristiqueInstance?.id]">Caractéristique</g:link></li>
					</ul>
				</div>
			</td>
			</tr>
			</table>
			
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${typeCaracteristiqueInstance?.id}" />
					<g:link class="edit" action="edit" id="${typeCaracteristiqueInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
