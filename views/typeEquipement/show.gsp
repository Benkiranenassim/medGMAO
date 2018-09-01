<%@ page import="medgmao.TypeEquipement" %>
<!doctype html>
<!--Generation template modifiée par rezz-->
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'typeEquipement.label', default: 'TypeEquipement')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		
		<link href="/medgmao/css/dynatree/ui.dynatree.css" rel="stylesheet" type="text/css">
		<script src="/medgmao/js/jquery.cookie.js" type="text/javascript"></script>
		<script src="/medgmao/js/jquery.dynatree.js" type="text/javascript"></script>		
		
		<a href="#show-typeEquipement" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-typeEquipement" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table style="border-top:0px;margin-bottom:0px">
			<tr style="background:white">
			<td width="80%">
			<div class="sousform">
			<ol class="property-list typeEquipement" style="padding:0Px">
			
				<g:if test="${typeEquipementInstance?.libelle}">
				<li class="fieldcontain">
					<span id="libelle-label" class="property-label"><g:message code="typeEquipement.libelle.label" default="Libelle" /></span>
					<span class="property-value" aria-labelledby="libelle-label"><g:fieldValue bean="${typeEquipementInstance}" field="libelle"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${typeEquipementInstance?.parent}">
				<li class="fieldcontain">
					<span id="parent-label" class="property-label"><g:message code="typeEquipement.parent.label" default="Parent" /></span>
					<span class="property-value" aria-labelledby="parent-label"><g:link controller="typeEquipement" action="show" id="${typeEquipementInstance?.parent?.id}">${typeEquipementInstance?.parent?.encodeAsHTML()}</g:link></span>
				</li>
				</g:if>
			
				<g:if test="${typeEquipementInstance?.responsable}">
				<li class="fieldcontain">
					<span id="responsable-label" class="property-label"><g:message code="typeEquipement.organe.label" default="Responsable" /></span>
					<span class="property-value" aria-labelledby="responsable-label"><g:link controller="typeEquipement" action="show" id="${typeEquipementInstance?.responsable?.id}">${typeEquipementInstance?.responsable?.encodeAsHTML()}</g:link></span>
				</li>
				</g:if>
			
				<g:if test="${typeEquipementInstance?.corps}">
				<li class="fieldcontain">
					<span id="corps-label" class="property-label"><g:message code="typeEquipement.organe.label" default="Sous Organe de" /></span>
					<span class="property-value" aria-labelledby="corps-label"><g:link controller="typeEquipement" action="show" id="${typeEquipementInstance?.corps?.id}">${typeEquipementInstance?.corps?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${typeEquipementInstance?.prefix}">
				<li class="fieldcontain">
					<span id="prefix-label" class="property-label"><g:message code="typeEquipement.prefix.label" default="Prefix" /></span>
					<span class="property-value" aria-labelledby="prefix-label"><g:fieldValue bean="${typeEquipementInstance}" field="prefix"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${typeEquipementInstance?.sousTypes}">
				<li class="fieldcontain">
					<span id="sousTypes-label" class="property-label"><g:message code="typeEquipement.sousTypes.label" default="Sous Types" /></span>
					<script type="text/javascript">
					  $(function(){
						// Attach the dynatree widget to an existing <div id="tree"> element
						// and pass the tree options as an argument to the dynatree() function:
						$("#tree").dynatree({
						  onActivate: function(node) {
							// A DynaTreeNode object is passed to the activation handler
							// Note: we also get this event, if persistence is on, and the page is reloaded.
							window.location="/medgmao/typeEquipement/show/"+node.data.key
						  },
						  autoCollapse: true,
						  children: ${childrenJson}
						});
					  });
					  //$("#tree").dynatree("option", "fx", { height: "toggle", duration: 300 });
					</script>
					<span class="property-value" aria-labelledby="sousTypes-label">
						<div id="tree" > </div>
					</span>
					
				</li>
				</g:if>
				
				
				
				<g:if test="${typeEquipementInstance?.typesOrganes}">
				<li class="fieldcontain">
					<span id="organes-label" class="property-label"><g:message code="typeEquipement.organes.label" default="Sous Organes" /></span>
						<table>
							<tr><th>Type-Organe</th><th>Prefix</th></tr>
							<g:each in="${typeEquipementInstance.typesOrganes}" var="o">
								<tr><td>${o}</td><td><g:link controller="typeEquipement" action="show" id="${o.id}">${o?.prefix}</g:link></td></tr>
							</g:each>
						</table>
				</li>
				</g:if>
				
				<g:if test="${typeEquipementInstance?.modelsMaintenance}">
				<li class="fieldcontain">
					<span id="modelsMaintenance-label" class="property-label"><g:message code="typeEquipement.modelsMaintenance.label" default="Maintenance" /></span>
						<table>
							<tr><th>Model Maintenance</th><th>Actions</th></tr>
							<g:each in="${typeEquipementInstance.modelsMaintenance}" var="mm">
								<tr><td><g:link controller="modelMaintenance" action="show" id="${mm.id}">${mm}</g:link></td><td>${mm?.actions}</td></tr>
							</g:each>
						</table>
				</li>
				</g:if>
				
			</ol>
			</div>
			</td>
			<td>
				<div class="sousform">
				Ajouter :
					<ul  style="margin-left:18px">
						<li>
							<g:link controller="typeEquipement" action="create" params="['parent.id': typeEquipementInstance?.id]">Sous-type</g:link>
						</li>
						<li>
							<g:link controller="typeEquipement" action="create" params="['corps.id': typeEquipementInstance?.id]">Sous-organe</g:link>
						</li>
						<li>
							<g:link controller="equipement" action="create" params="['typeEquipement.id': typeEquipementInstance?.id]">Equipement</g:link>
						</li>
					</ul>
				associer : 
					<ul  style="margin-left:18px">
						<li>
							<g:link controller="modelMaintenance" action="create" params="['typeEquipement.id': typeEquipementInstance?.id]">Model maintenance</g:link>
						</li>
					</ul>
				Générer :
					<ul  style="margin-left:18px">
						<li>
							<g:link controller="equipement" action="inventaire" params="['typeequipementid': typeEquipementInstance?.id]">Fiche inventaire</g:link>
						</li>
					</ul>
				</div>
			</td>
			</tr>
			</table>
			
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${typeEquipementInstance?.id}" />
					<g:link class="edit" action="edit" id="${typeEquipementInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
