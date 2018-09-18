
<%@ page import="medgmao.FournisseurEquipement" %>
<!doctype html>
<!--Generation template modifiée par rezz-->
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'fournisseurEquipement.label', default: 'FournisseurEquipement')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-fournisseurEquipement" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-fournisseurEquipement" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table style="border-top:0px;margin-bottom:0px">
			<tr style="background:white">
			<td width="80%">
			<div class="sousform">
			<ol class="property-list fournisseurEquipement" style="padding:0Px">
			
				<g:if test="${fournisseurEquipementInstance?.libelle}">
				<li class="fieldcontain">
					<span id="libelle-label" class="property-label"><g:message code="fournisseurEquipement.libelle.label" default="Libelle" /></span>
					
						<span class="property-value" aria-labelledby="libelle-label"><g:fieldValue bean="${fournisseurEquipementInstance}" field="libelle"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${fournisseurEquipementInstance?.adresse}">
				<li class="fieldcontain">
					<span id="adresse-label" class="property-label"><g:message code="fournisseurEquipement.adresse.label" default="Adresse" /></span>
					
						<span class="property-value" aria-labelledby="adresse-label"><g:fieldValue bean="${fournisseurEquipementInstance}" field="adresse"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${fournisseurEquipementInstance?.tel}">
				<li class="fieldcontain">
					<span id="tel-label" class="property-label"><g:message code="fournisseurEquipement.tel.label" default="Tel" /></span>
					
						<span class="property-value" aria-labelledby="tel-label"><g:fieldValue bean="${fournisseurEquipementInstance}" field="tel"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${fournisseurEquipementInstance?.tel2}">
				<li class="fieldcontain">
					<span id="tel2-label" class="property-label"><g:message code="fournisseurEquipement.tel2.label" default="Tel2" /></span>
					
						<span class="property-value" aria-labelledby="tel2-label"><g:fieldValue bean="${fournisseurEquipementInstance}" field="tel2"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${fournisseurEquipementInstance?.email}">
				<li class="fieldcontain">
					<span id="email-label" class="property-label"><g:message code="fournisseurEquipement.email.label" default="Email" /></span>
					
						<span class="property-value" aria-labelledby="email-label"><g:fieldValue bean="${fournisseurEquipementInstance}" field="email"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${fournisseurEquipementInstance?.fournisseur}">
				<li class="fieldcontain">
					<span id="fournisseur-label" class="property-label"><g:message code="fournisseurEquipement.fournisseur.label" default="Fournisseur" /></span>
					
						<span class="property-value" aria-labelledby="fournisseur-label"><g:formatBoolean boolean="${fournisseurEquipementInstance?.fournisseur}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${fournisseurEquipementInstance?.fabriquant}">
				<li class="fieldcontain">
					<span id="fabriquant-label" class="property-label"><g:message code="fournisseurEquipement.fabriquant.label" default="Fabriquant" /></span>
					
						<span class="property-value" aria-labelledby="fabriquant-label"><g:formatBoolean boolean="${fournisseurEquipementInstance?.fabriquant}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${fournisseurEquipementInstance?.installateurReparateur}">
				<li class="fieldcontain">
					<span id="installateurReparateur-label" class="property-label"><g:message code="fournisseurEquipement.installateurReparateur.label" default="Installateur Reparateur" /></span>
					
						<span class="property-value" aria-labelledby="installateurReparateur-label"><g:formatBoolean boolean="${fournisseurEquipementInstance?.installateurReparateur}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${fournisseurEquipementInstance?.representant}">
				<li class="fieldcontain">
					<span id="representant-label" class="property-label"><g:message code="fournisseurEquipement.representant.label" default="Representant" /></span>
					
						<span class="property-value" aria-labelledby="representant-label"><g:formatBoolean boolean="${fournisseurEquipementInstance?.representant}" /></span>
					
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
					<g:hiddenField name="id" value="${fournisseurEquipementInstance?.id}" />
					<g:link class="edit" action="edit" id="${fournisseurEquipementInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
