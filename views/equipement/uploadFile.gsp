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
	<h3 style="margin:15Px">Ajout d'un nouveau fichier de chargement</h3>
	<div class="sousform" style="margin:15Px">
		<g:uploadForm action='uploadFile' name="uploadFile" >
			Fichier : <g:field type="file" name="fichier" required="" value="" />
			<g:field type="submit" name="ok" value="Ajouter" />
		</g:uploadForm>
	</div>
	
	<h3 style="margin:15Px">Fichiers déjà chargés</h3>
	<table>
		<tr>
			<th>
				Fichier
			</th>
			<th>
				Actions
			</th>
		</tr>

		<g:each in="${loadFiles}" var="${file}">
		<tr>
			<td>
				<a href="/medgmao/loadFiles/${file.name}">${file.name}</a>
			</td>
			<td>
				<g:link action="deleteLoadFile" params="[filename:file.name]" onclick="return confirm('êtes vou sûr de vouloir supprimer ce fichier ? les données déjà chargée ne seront pas impactés.');">supprimer</g:link>
				<g:link action="chargement" params="[filename:file.name]" onclick="return confirm('êtes vou sûr de vouloir charger le contenu de ce fichier dans la base de données ?');">charger</g:link>
			</td>
		</tr>
		</g:each>
	</table>

	</body>
</html>