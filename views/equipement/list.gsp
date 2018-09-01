<%@ page import="medgmao.Equipement" %>
<!doctype html>
<!--Generation template modifiée par rezz-->
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'equipement.label', default: 'Equipement')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-equipement" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-equipement" class="content scaffold-list" role="main">
			<h1>Recherche d'équipements</h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			
			<g:form method="post" >
				<fieldset class="form">
					<div class="fieldcontain ${hasErrors(bean: equipementInstance, field: 'code', 'error')} required">
						<label for="code">
							<g:message code="equipement.code.label" default="Code/Identifiant" />
							<span class="required-indicator">*</span>
						</label>
						<g:textField name="code" value="${equipementInstance?.code}"/>
					</div>
					<div class="fieldcontain ${hasErrors(bean: equipementInstance, field: 'libelle', 'error')} required">
						<label for="libelle">
							<g:message code="equipement.libelle.label" default="Libelle" />
							<span class="required-indicator">*</span>
						</label>
						<g:textField name="libelle" value="${equipementInstance?.libelle}"/>
					</div>
				</fieldset>
				<fieldset class="buttons">
					<g:actionSubmit class="save" action="list" value="Recherche" />
				</fieldset>
			</g:form>
			
			<g:if test="${equipementInstanceSet.size()>0}">
			<table id="resultlist" class="display dataTable">
				<thead>
					<tr>
					
						<th><g:message code="equipement.localisation.label" default="Localisation" /></th>
						<th><g:message code="equipement.localisation.libelle" default="libelle" /></th>
						<th><g:message code="equipement.localisation.code" default="code" /></th>
						<th><g:message code="equipement.localisation.marque" default="marque" /></th>
						<th><g:message code="equipement.typeEquipement.label" default="Type Equipement" /></th>
						<th><g:message code="equipement.parent.label" default="Parent" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${equipementInstanceSet}" status="i" var="equipementInstance">
					<tr>
					
						<td>${fieldValue(bean: equipementInstance, field: "localisation")}</td>
						<td><g:link action="show" id="${equipementInstance.id}">${fieldValue(bean: equipementInstance, field: "libelle")}</g:link></td>
						<td>${fieldValue(bean: equipementInstance, field: "code")}</td>
						<td>${fieldValue(bean: equipementInstance, field: "marque")}</td>
						<td>${fieldValue(bean: equipementInstance, field: "typeEquipement")}</td>
						<td>${fieldValue(bean: equipementInstance, field: "parent")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<script type="text/javascript" src="/medgmao/js/jquery.dataTables.min.js"></script>
			<script type="text/javascript" src="/medgmao/js/TableTools.min.js"></script>
			
			<!--link rel="stylesheet" href="/medgmao/css/datatable/demo_table.css" type="text/css"-->
			<link rel="stylesheet" href="/medgmao/css/datatable/demo_table_jui.css" type="text/css">
			<!--link rel="stylesheet" href="/medgmao/css/datatable/demo_page.css" type="text/css"-->
			
			
			<script>
				$(document).ready(function(){
					$('#resultlist').dataTable({
						"bJQueryUI": true,
						"sPaginationType": "full_numbers" , 
						"sDom": 'T<"clear">lfrtip',
						"oTableTools": {
							"sSwfPath": "/medgmao/swf/copy_csv_xls_pdf.swf"
						}
					});
				});			
			</script>
			</g:if>
		</div>
	</body>
</html>
