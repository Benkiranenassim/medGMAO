
<%@ page import="mederp.Affectation" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'affectation.label', default: 'Affectation')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-affectation" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create">Nouvelle affectation</g:link></li>
			</ul>
		</div>
		<div id="show-affectation" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list affectation">
			
				<g:if test="${affectationInstance?.unite}">
				<li class="fieldcontain">
					<span id="unite-label" class="property-label"><g:message code="affectation.unite.label" default="Unité" /></span>
					
						<span class="property-value" aria-labelledby="unite-label"><g:link controller="unite" action="show" id="${affectationInstance?.unite?.id}">${affectationInstance?.unite?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${affectationInstance?.user}">
				<li class="fieldcontain">
					<span id="user-label" class="property-label"><g:message code="affectation.user.label" default="User" /></span>
					
						<span class="property-value" aria-labelledby="user-label"><g:link controller="user" action="show" id="${affectationInstance?.user?.id}">${affectationInstance?.user?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${affectationInstance?.dateAffectation}">
				<li class="fieldcontain">
					<span id="dateAffectation-label" class="property-label"><g:message code="affectation.dateAffectation.label" default="Date Affectation" /></span>
					
						<span class="property-value" aria-labelledby="dateAffectation-label"><g:formatDate date="${affectationInstance?.dateAffectation}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${affectationInstance?.dateFin}">
				<li class="fieldcontain">
					<span id="dateFin-label" class="property-label"><g:message code="affectation.dateFin.label" default="Date Fin" /></span>
					
						<span class="property-value" aria-labelledby="dateFin-label"><g:formatDate date="${affectationInstance?.dateFin}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${affectationInstance?.parDefaut}">
				<li class="fieldcontain">
					<span id="parDefaut-label" class="property-label"><g:message code="affectation.parDefaut.label" default="Par Defaut" /></span>
					
						<span class="property-value" aria-labelledby="parDefaut-label"><g:formatBoolean boolean="${affectationInstance?.parDefaut}" /></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${affectationInstance?.id}" />
					<g:link class="edit" action="edit" id="${affectationInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
