<!doctype html>
<html>
	<head>
		<meta name="layout" content="main"/>
		<title>GMAO</title>
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'index.css')}" type="text/css">
	</head>
	
	<body>
		<a href="#page-body" class="skip"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div id="status" role="complementary">
			<h1>Application Status</h1>
			<ul>
				<li>App version: <g:meta name="app.version"/></li>
				<li>JVM version: ${System.getProperty('java.version')}</li>
			</ul>
			<h1>Authoring</h1>
			<ul>
				<li>Author: Ossama Boughaba</li>
				<li>Author: Issam Lemchaouri</li>
				<li>Author: Nassim Benkirane</li>
				<li>Company: Cre@tive Technologies</li>
			</ul>
		</div>
		
								
		<div id="page-body" role="main">
			<g:ifLoggedIn><g:userComplet/>, <g:link controller="authentification" action="disconnect">(d&eacute;connecter)</g:link></g:ifLoggedIn>
			<h1>Prototype Inventaire</h1>
			<p>Le présent prototype a pour objectif la collecte du référentiel des équipements de l'UIR en vue de son utilisation dans le SIH et la GMAO en cours de réalisation</p>
			<br/>
			<div  style="margin:0 auto;">
				<button class="button" style="font-size:1.1em" onclick="window.location='/medgmao/equipement'" >Recherche équipement</button> 
				<button class="button" style="font-size:1.1em" onclick="window.location='/medgmao/typeEquipement'">Nav. Fonctionnelle</button> 
				<button class="button" style="font-size:1.1em" onclick="window.location='/medgmao/localisation'">Nav. Géographique</button>
			</div>
			<br/>
			<script>$( ".button" ).button();</script>
			
			<table>
				<tr>
					<td>
						<h2>Paramétrage :</h2>
						<ul>
							<g:ifAnyGranted roles="ROLE_RESPONSABLE_MAINTENANCE"><li class="controller"><g:link controller="typeEquipement">Types d'équipements</g:link></li></g:ifAnyGranted>
							<g:ifAnyGranted roles="ROLE_ADMIN"><li class="controller"><g:link controller="typeLocalisation">Types de localisations</g:link></li></g:ifAnyGranted>
							<g:ifAnyGranted roles="ROLE_RESPONSABLE_MAINTENANCE"><li class="controller"><g:link controller="typeCaracteristique">Types de caractéristiques</g:link></li></g:ifAnyGranted>
							<g:ifAnyGranted roles="ROLE_RESPONSABLE_MAINTENANCE"><li class="controller"><g:link controller="equipement" action="uploadFile">Fichiers de Chargement</g:link></li></g:ifAnyGranted>
						</ul>
						
						* Manipuler avec prudence
						
					</td>
					<td>
						<h2>Référentiel :</h2>
						<ul>
							<g:ifAnyGranted roles="ROLE_RESPONSABLE_MAINTENANCE"><li class="controller"><g:link controller="fournisseurEquipement">Fournisseurs</g:link></li></g:ifAnyGranted>
							<g:ifAnyGranted roles="ROLE_ADMIN,ROLE_RESPONSABLE_MAINTENANCE"><li class="controller"><g:link controller="localisation">Localisations</g:link></li></g:ifAnyGranted>
							<g:ifAnyGranted roles="ROLE_ADMIN"><li class="controller"><g:link controller="unite">Unités</g:link></li></g:ifAnyGranted>
							<g:ifAnyGranted roles="ROLE_ADMIN"><li class="controller"><g:link controller="user">Utilisateurs</g:link></li></g:ifAnyGranted>
							<g:ifAnyGranted roles="ROLE_RESPONSABLE_MAINTENANCE"><li class="controller"><g:link controller="groupeEquipements">Groupes d'équipement</g:link></li></g:ifAnyGranted>
						</ul>
					</td>
				</tr>
				<tr>
					<td>
						<ul>
							<h2>equipement :</h2>
							<g:ifAnyGranted roles="ROLE_RESPONSABLE_MAINTENANCE,ROLE_TECHNICIEN_MAINTENANCE"><li class="controller" ><g:link controller="intervention" action="create">faire une intervention</g:link></li></g:ifAnyGranted>
							<li class="controller"><g:link controller="demandeIntervention" action="create">demander une intervention</g:link></li>
						</ul>
					</td>
				</tr>				
			</table>
			
		</div>
	</body>
</html>
