
<%@ page import="mederp.Localisation" %>
<!doctype html>
<!--Generation template modifiée par rezz-->
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'localisation.label', default: 'Localisation')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-localisation" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-localisation" class="content scaffold-list" role="main">
			<h1>Recherche d'une localisation</h1>

			<g:form method="post" >
				<fieldset class="form">
					<div class="fieldcontain ${hasErrors(bean: localisationInstance, field: 'code', 'error')} required">
						<label for="code">
							<g:message code="localisation.code.label" default="Code/Libellé court" />
							<span class="required-indicator">*</span>
						</label>
						<g:textField name="code" value="${localisationInstance?.code}"/>
					</div>
					<div class="fieldcontain ${hasErrors(bean: localisationInstance, field: 'libelle', 'error')} required">
						<label for="libelle">
							<g:message code="localisation.libelle.label" default="Libelle" />
							<span class="required-indicator">*</span>
						</label>
						<g:textField name="libelle" value="${localisationInstance?.libelle}"/>
					</div>
				</fieldset>
				<fieldset class="buttons">
					<g:actionSubmit class="save" action="list" value="Recherche" />
				</fieldset>
			</g:form>
			
			<h1>Liste des localisations</h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="libelleCourt" title="${message(code: 'localisation.libelleCourt.label', default: 'Libelle court')}" />
						
						<g:sortableColumn property="libelle" title="${message(code: 'localisation.libelle.label', default: 'Libelle')}" />						
					
						<th><g:message code="localisation.typeLocalisation.label" default="Type Localisation" /></th>
					
						<th><g:message code="localisation.parent.label" default="Parent" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${localisationInstanceList}" status="i" var="localisationInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td>${fieldValue(bean: localisationInstance, field: "libelleCourt")}</td>
					
						<td><g:link action="show" id="${localisationInstance.id}">${fieldValue(bean: localisationInstance, field: "libelle")}</g:link></td>
					
						<td>${fieldValue(bean: localisationInstance, field: "typeLocalisation")}</td>
					
						<td>${fieldValue(bean: localisationInstance, field: "parent")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			
			
		</div>
	</body>
</html>
