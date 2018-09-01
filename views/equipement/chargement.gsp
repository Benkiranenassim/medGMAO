<%@ page import="medgmao.Equipement" %>
<!doctype html>
<!--Generation template modifiée par rezz-->
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'equipement.label', default: 'Equipement')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
	
	<g:if test="${flash.message}">
		<div class="message" role="status">${flash.message}</div>
	</g:if>
	<h2>${equipementsCharges.size()}) Equipements chargés <g:link action="uploadFile">retour</g:link></h2>
	<table>
		<tr>
			<th>
				Libellé
			</th>
			<th>
				Code
			</th>
			<th>
				Parent
			</th>
			<th>
				Localisation
			</th>
		</tr>
		<g:each in="${equipementsCharges}" var="${eq}">
		<tr>
			<td>
				<g:link controller="equipement" action="show" id="${eq.id}">${eq.libelle}</g:link>
			</td>
			<td>
				${eq.code}
			</td>
			<td>
				${eq.parent}
			</td>
			<td>
				${eq.localisation}
			</td>
		</tr>
		</g:each>
	</table>
	Lignes à problème :
	<table>
		<g:each in="${ligneProb}" var="${ligne}">
		<tr>
			<td>
				${ligne}
			</td>
		</tr>
		</g:each>
	</table>
	
	Avertissements :
	<table>
		<g:each in="${ligneWarn}" var="${ligne}">
		<tr>
			<td>
				${ligne}
			</td>
		</tr>
		</g:each>
	</table>
	
	<br/>
	<br/>
	<br/>
	<br/>
	
	<h2>Résumé</h2>
	<table>
		<tr>
			<th>Equipements chargés</th>
			<th>Problèmes</th>
			<th>Avertissements</th>
			<th>Suppression Chargement</th>
		</tr>
		<tr>
			<td>${equipementsCharges.size()}</td>
			<td>${ligneProb.size()}</td>
			<td>${ligneWarn.size()}</td>
			
			<td>
			<g:form action="deleteChargement" name="deleteChargement">
				<g:hiddenField  name="eqids" value="${eqids}" />
				<g:field type="submit" name="ok" class="keepvisible" value="Supprimer" onclick="return confirm('êtes vou sûr de vouloir supprimer tout les equipements chargés ? (vous pourrez les recharger par la suite)');"/>
			</g:form>
			</td>
		</tr>
		
	</table>

	</body>
</html>
