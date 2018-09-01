
<%@ page import="medgmao.GroupeEquipements" %>
<!doctype html>
<!--Generation template modifiée par rezz-->
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'groupeEquipements.label', default: 'GroupeEquipements')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-groupeEquipements" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-groupeEquipements" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table style="border-top:0px;margin-bottom:0px">
			<tr style="background:white">
			<td width="80%">
			<div class="sousform">
			<ol class="property-list groupeEquipements" style="padding:0Px">
			
				<g:if test="${groupeEquipementsInstance?.libelle}">
				<li class="fieldcontain">
					<span id="libelle-label" class="property-label"><g:message code="groupeEquipements.libelle.label" default="Libelle" /></span>
					
						<span class="property-value" aria-labelledby="libelle-label"><g:fieldValue bean="${groupeEquipementsInstance}" field="libelle"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${groupeEquipementsInstance?.equipements}">
				<li class="fieldcontain">
					<span id="equipements-label" class="property-label"><g:message code="groupeEquipements.equipements.label" default="Equipements" /></span>
					
						<g:each in="${groupeEquipementsInstance.equipements}" var="e">
						<span class="property-value" aria-labelledby="equipements-label">
							<g:link controller="equipement" action="show" id="${e.id}">${e?.encodeAsHTML()}</g:link> 
							<g:link action="retirer" params="[groupeid:groupeEquipementsInstance.id,equipementid:e.id]" onclick="return confirm('Êtes vous sûr de vouloir retirer cet équipement du groupe');">(retirer)</g:link>
						</span>
						</g:each>
					
				</li>
				</g:if>
			
			</ol>
			</div>
			</td>
			<td>
				<div class="sousform">
					Générer :
					<ul  style="margin-left:18px">
						<li>
							<g:link controller="equipement" action="inventaire" params="['groupeid': groupeEquipementsInstance?.id]">Inventaire</g:link>
						</li>
					</ul>
				</div>
			</td>
			</tr>
			</table>
			
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${groupeEquipementsInstance?.id}" />
					<g:link class="edit" action="edit" id="${groupeEquipementsInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
