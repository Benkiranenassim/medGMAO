
<%@ page import="mederp.Localisation" %>
<!doctype html>
<!--Generation template modifiée par rezz-->
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'localisation.label', default: 'Localisation')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
	
		<link href="/medgmao/css/dynatree/ui.dynatree.css" rel="stylesheet" type="text/css">
		<script src="/medgmao/js/jquery.cookie.js" type="text/javascript"></script>
		<script src="/medgmao/js/jquery.dynatree.js" type="text/javascript"></script>		
	
		<a href="#show-localisation" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-localisation" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table style="border-top:0px;margin-bottom:0px">
			<tr style="background:white">
			<td width="80%">
			<div class="sousform">
			<ol class="property-list localisation" style="padding:0Px">
			
				<g:if test="${localisationInstance?.libelle}">
				<li class="fieldcontain">
					<span id="libelle-label" class="property-label"><g:message code="localisation.libelle.label" default="Libelle" /></span>			
					<span class="property-value" aria-labelledby="libelle-label"><g:fieldValue bean="${localisationInstance}" field="libelle"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${localisationInstance?.libelleCourt}">
				<li class="fieldcontain">
					<span id="libelleCourt-label" class="property-label"><g:message code="localisation.libelleCourt.label" default="libelleCourt" /></span>
					<span class="property-value" aria-labelledby="libelleCourt-label"><g:fieldValue bean="${localisationInstance}" field="libelleCourt"/></span>					
				</li>
				</g:if>
			
				<g:if test="${localisationInstance?.photo}">
				<li class="fieldcontain">
					<span id="libelleCourt-label" class="property-label"><g:message code="localisation.photo.label" default="Photo" /></span>
					
				</li>
				<img src="${localisationInstance?.photo}" width="750"/>
				</g:if>
			
				<g:if test="${localisationInstance?.capacite}">
				<li class="fieldcontain">
					<span id="capacite-label" class="property-label"><g:message code="localisation.capacite.label" default="Capacité" /></span>
					<span class="property-value" aria-labelledby="capacite-label"><g:fieldValue bean="${localisationInstance}" field="capacite"/></span>					
				</li>
				</g:if>
			
				<g:if test="${localisationInstance?.superficie}">
				<li class="fieldcontain">
					<span id="superficie-label" class="property-label"><g:message code="localisation.superficie.label" default="Superficie" /></span>
					<span class="property-value" aria-labelledby="superficie-label"><g:fieldValue bean="${localisationInstance}" field="superficie"/> m²</span>					
				</li>
				</g:if>
			
				<g:if test="${localisationInstance?.ordre}">
				<li class="fieldcontain">
					<span id="ordre-label" class="property-label"><g:message code="localisation.ordre.label" default="Ordre" /></span>
					<span class="property-value" aria-labelledby="ordre-label"><g:fieldValue bean="${localisationInstance}" field="ordre"/></span>					
				</li>
				</g:if>
			
				<g:if test="${localisationInstance?.typeLocalisation}">
				<li class="fieldcontain">
					<span id="typeLocalisation-label" class="property-label"><g:message code="localisation.typeLocalisation.label" default="Type Localisation" /></span>
					<span class="property-value" aria-labelledby="typeLocalisation-label"><g:link controller="typeLocalisation" action="show" id="${localisationInstance?.typeLocalisation?.id}">${localisationInstance?.typeLocalisation?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${localisationInstance?.unite}">
				<li class="fieldcontain">
					<span id="unite-label" class="property-label"><g:message code="localisation.unite.label" default="Unite" /></span>
					
						<span class="property-value" aria-labelledby="parent-label"><g:link controller="unite" action="show" id="${localisationInstance?.unite?.id}">${localisationInstance?.unite?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${localisationInstance?.parent}">
				<li class="fieldcontain">
					<span id="parent-label" class="property-label"><g:message code="localisation.parent.label" default="Parent" /></span>
					
						<span class="property-value" aria-labelledby="parent-label"><g:link controller="localisation" action="show" id="${localisationInstance?.parent?.id}">${localisationInstance?.parent?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<li class="fieldcontain">
					<span id="sousLocalisations-label" class="property-label"><g:message code="localisation.sousLocalisations.label" default="Sous Localisations" /></span>
					
					<script type="text/javascript">
					  $(function(){
						// Attach the dynatree widget to an existing <div id="tree"> element
						// and pass the tree options as an argument to the dynatree() function:
						$("#tree").dynatree({
						  onActivate: function(node) {
							// A DynaTreeNode object is passed to the activation handler
							// Note: we also get this event, if persistence is on, and the page is reloaded.
							window.location="/medgmao/localisation/show/"+node.data.key
						  },
						  autoCollapse: true,
						  children: ${childrenJson}
						});
					  });
					  //$("#tree").dynatree("option", "fx", { height: "toggle", duration: 300 });
					</script>
					<span class="property-value" aria-labelledby="sousLocalisations-label">
						<div id="tree"> </div>
					</span>
					
				</li>
			
			</ol>
			</div>
			</td>
			<td>
				<div class="sousform">
					Créer :
					<ul  style="margin-left:18px">
						<li>
							<g:link controller="localisation" action="create" params="['parent.id': localisationInstance?.id]">Sous-local</g:link>
						</li>
					</ul>
					Générer :
					<ul  style="margin-left:18px">
						<li>
							<g:link controller="equipement" action="inventaire" params="['localisationid': localisationInstance?.id]">Inventaire</g:link></li>
						
					</ul>
				</div>
			</td>
			</tr>
			</table>
			
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${localisationInstance?.id}" />
					<g:link class="edit" action="edit" id="${localisationInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
