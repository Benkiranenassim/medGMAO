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
			<h1>
				Modèl de maintenance : ${modelMaintenanceInstance} (${modelMaintenanceInstance.typeEquipement})
			</h1>
			<g:if test="${modelMaintenanceInstance.typeEquipement.allSousTypes().size()>0}">${modelMaintenanceInstance.typeEquipement.allSousTypes()}</g:if>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			
			<g:if test="${equipementsProgrammes.size()>0}">
			<h3><br/>Equipements programmés pour la maintenance :</h3><br/>
			<g:form method="post" controller="modelMaintenance" action="annulerProgrammation">
			<table>
				<thead>
					<tr>
						<th><g:message code="equipement.choix.label" default="Choix" /></th>
						<th><g:message code="equipement.localisation.label" default="Localisation" /></th>					
						<g:sortableColumn property="libelle" title="${message(code: 'equipement.libelle.label', default: 'Libelle')}" />					
						<g:sortableColumn property="code" title="${message(code: 'equipement.code.label', default: 'Code')}" />					
						<g:sortableColumn property="marque" title="${message(code: 'equipement.marque.label', default: 'Marque')}" />					
						<th><g:message code="equipement.typeEquipement.label" default="Type Equipement" /></th>					
						<th><g:message code="equipement.action.label" default="action" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${equipementsProgrammes}" status="i" var="equipementInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
						<td><g:checkBox name="_equ_${equipementInstance.id}" /></td>
						<td>${fieldValue(bean: equipementInstance, field: "localisation")}</td>
						<td><g:link action="show" id="${equipementInstance.id}">${fieldValue(bean: equipementInstance, field: "libelle")}</g:link></td>
						<td>${fieldValue(bean: equipementInstance, field: "code")}</td>
						<td>${fieldValue(bean: equipementInstance, field: "marque")}</td>
						<td>${fieldValue(bean: equipementInstance, field: "typeEquipement")}</td>
						<td><g:link controller="demandeIntervention" action="create">intervention</g:link></td>
					</tr>
				</g:each>
				</tbody>
			</table>
			
			<table>
				<tr>
					<td>Annuler la programmation de la maintenance :</td>
					<td><g:textField name="groupname" maxlength="20" /></td>
					<td><g:submitButton name="annuler" action="annulerProgrammation" class="save" value="Annuler maintenance" /></td>
				</tr>
			</table>
				 
			</span>
			</g:form>
			</g:if>
			
			<!---->
			<g:if test="${equipementsNonProgrammes.size()>0}">
			<h3><br/>Equipements non concérnés par la maintenance</h3><br/>
			<g:form method="post" controller="modelMaintenance" action="programmer">
			<table>
				<thead>
					<tr>
						<th><g:message code="equipement.choix.label" default="Choix" /></th>
						<th><g:message code="equipement.localisation.label" default="Localisation" /></th>					
						<g:sortableColumn property="libelle" title="${message(code: 'equipement.libelle.label', default: 'Libelle')}" />					
						<g:sortableColumn property="code" title="${message(code: 'equipement.code.label', default: 'Code')}" />					
						<g:sortableColumn property="marque" title="${message(code: 'equipement.marque.label', default: 'Marque')}" />					
						<th><g:message code="equipement.typeEquipement.label" default="Type Equipement" /></th>					
						<th><g:message code="equipement.action.label" default="action" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${equipementsNonProgrammes}" status="i" var="equipementInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
						<td><g:checkBox name="_equ_${equipementInstance.id}" /></td>
						<td>${fieldValue(bean: equipementInstance, field: "localisation")}</td>
						<td><g:link action="show" id="${equipementInstance.id}">${fieldValue(bean: equipementInstance, field: "libelle")}</g:link></td>
						<td>${fieldValue(bean: equipementInstance, field: "code")}</td>
						<td>${fieldValue(bean: equipementInstance, field: "marque")}</td>
						<td>${fieldValue(bean: equipementInstance, field: "typeEquipement")}</td>
						<td><g:link controller="demandeIntervention" action="create">intervention</g:link></td>
					</tr>
				</g:each>
				</tbody>
			</table>
			Programmer la maintenance :
			<table>
				<tr>
					<td>Commencer le :<br/><g:datePicker name="dateDebut" precision="day"  value="${new Date()}"  /></td>
					<td>Répeter tout les : <br/><g:textField name="interval" maxlength="4" size="4" value="${modelMaintenanceInstance.interval}"/> Jours</td>
					<td>Responsable : <br/><g:select id="responsable" name="responsableid" from="${mederp.User.list()}" optionKey="id" value="${typeEquipementInstance?.responsable?.id}" class="many-to-one" noSelection="['null': '']" /></td>
					<td><br/><g:submitButton name="programmer" action="programmer" class="save" value="Programmer" /><g:field type="hidden" name="modelid" value="${modelMaintenanceInstance.id}"/></td>
				</tr>
			</table>
				 
			</span>
			</g:form>
			</g:if>
			
			<g:else>
				aucun matériel répertorié pour le type ou local choisi
			</g:else>
		</div>
	</body>
</html>
