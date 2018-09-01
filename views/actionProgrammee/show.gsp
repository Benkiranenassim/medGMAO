
<%@ page import="medgmao.ActionProgrammee" %>
<!doctype html>
<!--Generation template modifiée par rezz-->
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'actionProgrammee.label', default: 'ActionProgrammee')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-actionProgrammee" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-actionProgrammee" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table style="border-top:0px;margin-bottom:0px">
			<tr style="background:white">
			<td width="80%">
			<div class="sousform">
			<ol class="property-list actionProgrammee" style="padding:0Px">
			
				<g:if test="${actionProgrammeeInstance?.libelle}">
				<li class="fieldcontain">
					<span id="libelle-label" class="property-label"><g:message code="actionProgrammee.libelle.label" default="Libelle" /></span>
					
						<span class="property-value" aria-labelledby="libelle-label"><g:fieldValue bean="${actionProgrammeeInstance}" field="libelle"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${actionProgrammeeInstance?.description}">
				<li class="fieldcontain">
					<span id="description-label" class="property-label"><g:message code="actionProgrammee.description.label" default="Description" /></span>
					
						<span class="property-value" aria-labelledby="description-label"><g:fieldValue bean="${actionProgrammeeInstance}" field="description"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${actionProgrammeeInstance?.modelMaintenance}">
				<li class="fieldcontain">
					<span id="modelMaintenance-label" class="property-label"><g:message code="actionProgrammee.modelMaintenance.label" default="Model Maintenance" /></span>
					
						<span class="property-value" aria-labelledby="modelMaintenance-label"><g:link controller="modelMaintenance" action="show" id="${actionProgrammeeInstance?.modelMaintenance?.id}">${actionProgrammeeInstance?.modelMaintenance?.encodeAsHTML()}</g:link></span>
					
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
					<g:hiddenField name="id" value="${actionProgrammeeInstance?.id}" />
					<g:link class="edit" action="edit" id="${actionProgrammeeInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
