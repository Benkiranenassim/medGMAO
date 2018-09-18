<%@ page import="mederp.User" %>



<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'username', 'error')} required">
	<label for="username">
		<g:message code="user.username.label" default="Username" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="username" required="" value="${userInstance?.username}"/>
</div>

<g:if test="${ ! userInstance?.id}">
<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'password', 'error')} required">
	<label for="password">
		<g:message code="user.password.label" default="Password" />
		<span class="required-indicator">*</span>
	</label>
	<g:passwordField name="password" required="" value="${userInstance?.password}"/>
</div>
</g:if>

<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'nom', 'error')} ">
	<label for="nom">
		<g:message code="user.nom.label" default="Nom" />
		
	</label>
	<g:textField name="nom" value="${userInstance?.nom}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'prenom', 'error')} ">
	<label for="prenom">
		<g:message code="user.prenom.label" default="Prenom" />
		
	</label>
	<g:textField name="prenom" value="${userInstance?.prenom}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'titre', 'error')} ">
	<label for="titre">
		<g:message code="user.titre.label" default="Titre" />
		
	</label>
	<g:textField name="titre" value="${userInstance?.titre}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'email', 'error')} ">
	<label for="email">
		<g:message code="user.email.label" default="Email" />
		
	</label>
	<g:field type="email" name="email" value="${userInstance?.email}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'tel', 'error')} ">
	<label for="tel">
		<g:message code="user.tel.label" default="Tel" />
		
	</label>
	<g:textField name="tel" pattern="${userInstance.constraints.tel.matches}" value="${userInstance?.tel}"/>
</div>


<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'enabled', 'error')} ">
	<label for="enabled">
		<g:message code="user.enabled.label" default="Actif" />
	</label>
	<g:checkBox name="enabled" value="${userInstance?.enabled}" />
</div>

<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'permanent', 'error')} ">
	<label for="permanent">
		<g:message code="user.permanent.label" default="Permanent" />
	</label>
	<g:checkBox name="permanent" value="${userInstance?.permanent}" />
</div>

<g:if test="${userInstance.roles*.role?.authority?.contains("ROLE_RESPONSABLESTOCK")}">
<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'stocks', 'error')} ">
	<label for="stocks">
		<g:message code="user.stocks.label" default="Stocks" />
	</label>
	<g:select name="stocks" from="${mederp.Stock.list()}" multiple="multiple" optionKey="id" size="5" value="${userInstance?.stocks*.id}" class="many-to-many"/>
</div>
</g:if>
