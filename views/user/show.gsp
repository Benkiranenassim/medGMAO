
<%@ page import="mederp.User" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'user.label', default: 'User')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-user" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list">Liste utilisateurs</g:link></li>
				<li><g:link class="create" action="create">Nouvel utilisateur</g:link></li>
			</ul>
		</div>
		<div id="show-user" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table style="border-top:0px;margin-bottom:0px">
			<tr style="background:white">
			<td width="80%">
			<div class="sousform">
			<ol class="property-list user">
			
				<g:if test="${userInstance?.username}">
				<li class="fieldcontain">
					<span id="username-label" class="property-label"><g:message code="user.username.label" default="Username" /></span>
					
						<span class="property-value" aria-labelledby="username-label"><g:fieldValue bean="${userInstance}" field="username"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${userInstance?.password}">
				<li class="fieldcontain">
					<span id="password-label" class="property-label"><g:message code="user.password.label" default="Password" /></span>
					
						<span class="property-value" aria-labelledby="password-label">*****</span>
					
				</li>
				</g:if>
			
				<g:if test="${userInstance?.nom}">
				<li class="fieldcontain">
					<span id="nom-label" class="property-label"><g:message code="user.nom.label" default="Nom" /></span>
					
						<span class="property-value" aria-labelledby="nom-label"><g:fieldValue bean="${userInstance}" field="nom"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${userInstance?.prenom}">
				<li class="fieldcontain">
					<span id="prenom-label" class="property-label"><g:message code="user.prenom.label" default="Prenom" /></span>
					
						<span class="property-value" aria-labelledby="prenom-label"><g:fieldValue bean="${userInstance}" field="prenom"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${userInstance?.titre}">
				<li class="fieldcontain">
					<span id="titre-label" class="property-label"><g:message code="user.titre.label" default="Titre" /></span>
					
						<span class="property-value" aria-labelledby="titre-label"><g:fieldValue bean="${userInstance}" field="titre"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${userInstance?.email}">
				<li class="fieldcontain">
					<span id="email-label" class="property-label"><g:message code="user.email.label" default="Email" /></span>
					
						<span class="property-value" aria-labelledby="email-label"><g:fieldValue bean="${userInstance}" field="email"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${userInstance?.tel}">
				<li class="fieldcontain">
					<span id="tel-label" class="property-label"><g:message code="user.tel.label" default="Tel" /></span>
					
						<span class="property-value" aria-labelledby="tel-label"><g:fieldValue bean="${userInstance}" field="tel"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${userInstance?.accountExpired}">
				<li class="fieldcontain">
					<span id="accountExpired-label" class="property-label"><g:message code="user.accountExpired.label" default="Account Expired" /></span>
					
						<span class="property-value" aria-labelledby="accountExpired-label"><g:formatBoolean boolean="${userInstance?.accountExpired}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${userInstance?.accountLocked}">
				<li class="fieldcontain">
					<span id="accountLocked-label" class="property-label"><g:message code="user.accountLocked.label" default="Account Locked" /></span>
					
						<span class="property-value" aria-labelledby="accountLocked-label"><g:formatBoolean boolean="${userInstance?.accountLocked}" /></span>
					
				</li>
				</g:if>
				
				<li class="fieldcontain">
					<span id="qr-label" class="property-label"><g:message code="user.qr.label" default="VCard" /></span>
					<span class="property-value" aria-labelledby="qr-label"><img src="/medgmao/rendering/userVcard?userid=${userInstance?.id}"/></span>
				</li>
				
				<li class="fieldcontain">
					<span id="permanent-label" class="property-label"><g:message code="user.permanent.label" default="Permanent" /></span>
					<g:if test="${userInstance?.permanent}"><span class="property-value" aria-labelledby="permanent-label">Oui</span></g:if>
					<g:else><span class="property-value" aria-labelledby="permanent-label">Non</span></g:else>
				</li>
				
				
				<li class="fieldcontain">
					<span id="enabled-label" class="property-label"><g:message code="user.enabled.label" default="Actif" /></span>
					<g:if test="${userInstance?.enabled}">
						<span class="property-value" aria-labelledby="enabled-label">Oui</span>
					</g:if>
					<g:else>
						<span class="property-value" aria-labelledby="enabled-label">Non</span>
					</g:else>
				</li>
				
			
				<li class="fieldcontain">
					<span id="affectations-label" class="property-label"><g:message code="user.affectations.label" default="Affectations" /></span>
					
						<g:each in="${userInstance.affectations}" var="a">
						<span class="property-value" aria-labelledby="affectations-label"><g:link controller="affectation" action="show" id="${a.id}">${a?.encodeAsHTML()}</g:link></span>
						</g:each>
				</li>
			
			
				<g:if test="${userInstance?.passwordExpired}">
				<li class="fieldcontain">
					<span id="passwordExpired-label" class="property-label"><g:message code="user.passwordExpired.label" default="Password Expired" /></span>
					
						<span class="property-value" aria-labelledby="passwordExpired-label"><g:formatBoolean boolean="${userInstance?.passwordExpired}" /></span>
					
				</li>
				</g:if>
			
				
				<li class="fieldcontain">
					<span id="roles-label" class="property-label"><g:message code="user.roles.label" default="Roles" /></span>
					
						<g:each in="${userInstance.roles}" var="r">
						<span class="property-value" aria-labelledby="roles-label">
							<g:link controller="userRole" action="show" id="${r.id}">${r?.encodeAsHTML()}</g:link> &nbsp;&nbsp;
							<g:link action="remove" controller="userRole" params="[userid: userInstance.id, roleid:r.roleId]" >(retirer)</g:link>
						</span>
						
						</g:each>
					
				</li>
				
				<g:if test="${userInstance.roles*.role.authority.contains("ROLE_RESPONSABLESTOCK") }">
				
				
				<li class="fieldcontain">
					<span id="stocks-label" class="property-label"><g:message code="user.stocks.label" default="Stocks" /></span>
					
						<g:each in="${userInstance.stocks}" var="s">
						<span class="property-value" aria-labelledby="stocks-label"><g:link controller="stock" action="show" id="${s.id}">${s?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				
				
				</g:if>
				
			</ol>
			
			</div>
			</td>
			<td>
				<div class="sousform">
					Ajouter
					<ul  style="margin-left:18px">
						<li>
							<span class="property-value" aria-labelledby="roles-label"><g:link controller="userRole" action="create" params="['user.id': userInstance?.id]">un rôle</g:link></span>
						</li>
					</ul>
					Affecter à
					<ul  style="margin-left:18px">
						<li>
							<g:link controller="affectation" action="create" params="['user.id': userInstance?.id]">Une unité</g:link>
						</li>
					</ul>
					Réinitialiser
					<ul  style="margin-left:18px">
						<li>
							<g:link action="reinitPasswd" controller="securite" params="[userid: userInstance.id]" onclick="return confirm('Êtes vous sûr de vouloir réinitialiser le mot de passe de l utilisateur ?')">Le mot de passe (password=username)</g:link>
						</li>
					</ul>
				</div>
			</td>
			</tr>
			</table>
			
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${userInstance?.id}" />
					<g:link class="edit" action="edit" id="${userInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
