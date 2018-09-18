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
		<a href="#show-equipement" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-equipement" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table style="border-top:0px;margin-bottom:0px">
			<tr style="background:white">
			<td width="80%">
			<div class="sousform">
			<ol class="property-list equipement" style="padding:0Px">
			
				<g:if test="${equipementInstance?.unite}">
				<li class="fieldcontain">
					<span id="unite-label" class="property-label"><g:message code="equipement.unite.label" default="Unite" /></span>
					<span class="property-value" aria-labelledby="unite-label"><g:link controller="unite" action="show" id="${equipementInstance?.unite?.id}">${equipementInstance?.unite?.encodeAsHTML()}</g:link></span>
				</li>
				</g:if>
			
				<g:if test="${equipementInstance?.libelle}">
				<li class="fieldcontain">
					<span id="libelle-label" class="property-label"><g:message code="equipement.libelle.label" default="Libelle" /></span>
					<span class="property-value" aria-labelledby="libelle-label"><g:fieldValue bean="${equipementInstance}" field="libelle"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${equipementInstance?.numeroOrdre}">
				<li class="fieldcontain">
					<span id="numeroOrdre-label" class="property-label"><g:message code="equipement.numeroOrdre.label" default="Numero ordre" /></span>
					<span class="property-value" aria-labelledby="numeroOrdre-label">${equipementInstance?.numeroOrdre}</span>
					
				</li>
				</g:if>
			
				<g:if test="${equipementInstance?.code}">
				<li class="fieldcontain">
					<span id="code-label" class="property-label"><g:message code="equipement.code.label" default="Code" /></span>
					<span class="property-value" aria-labelledby="code-label">${equipementInstance?.code}<br/><img width="250Px" src="/medgmao/rendering/equipementBarcode?equipementcode=${equipementInstance?.code}"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${equipementInstance?.identifiant}">
				<li class="fieldcontain">
					<span id="identifiant-label" class="property-label"><g:message code="equipement.identifiant.label" default="Identifiant" /></span>
					<span class="property-value" aria-labelledby="identifiant-label">${equipementInstance?.identifiant}<br/><img width="250Px" src="/medgmao/rendering/equipementBarid?equipementidentifiant=${equipementInstance?.identifiant}"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${equipementInstance?.marque}">
				<li class="fieldcontain">
					<span id="marque-label" class="property-label"><g:message code="equipement.marque.label" default="Marque" /></span>
					<span class="property-value" aria-labelledby="marque-label"><g:fieldValue bean="${equipementInstance}" field="marque"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${equipementInstance?.typeEquipement}">
				<li class="fieldcontain">
					<span id="typeEquipement-label" class="property-label"><g:message code="equipement.typeEquipement.label" default="Type Equipement" /></span>
					<span class="property-value" aria-labelledby="typeEquipement-label"><g:link controller="typeEquipement" action="show" id="${equipementInstance?.typeEquipement?.id}">${equipementInstance?.typeEquipement?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${equipementInstance?.parent}">
				<li class="fieldcontain">
					<span id="parent-label" class="property-label"><g:message code="equipement.parent.label" default="Parent" /></span>
					<span class="property-value" aria-labelledby="parent-label"><g:link controller="equipement" action="show" id="${equipementInstance?.parent?.id}">${equipementInstance?.parent?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${equipementInstance?.corps}">
				<li class="fieldcontain">
					<span id="corps-label" class="property-label"><g:message code="equipement.parent.label" default="Organe de" /></span>
					<span class="property-value" aria-labelledby="corps-label"><g:link controller="equipement" action="show" id="${equipementInstance?.corps?.id}">${equipementInstance?.corps?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${equipementInstance?.localisation && !equipementInstance?.corps}">
				<li class="fieldcontain">
					<span id="localisation-label" class="property-label"><g:message code="equipement.localisation.label" default="Localisation" /></span>
					<span class="property-value" aria-labelledby="localisation-label"><g:link controller="localisation" action="show" id="${equipementInstance?.localisation?.id}">${equipementInstance?.localisation?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${equipementInstance?.fournisseur}">
				<li class="fieldcontain">
					<span id="fournisseur-label" class="property-label"><g:message code="equipement.fournisseur.label" default="Fournisseur" /></span>
					<span class="property-value" aria-labelledby="fournisseur-label"><g:link controller="fournisseurEquipement" action="show" id="${equipementInstance?.fournisseur?.id}">${equipementInstance?.fournisseur?.encodeAsHTML()}</g:link></span>
				</li>
				</g:if>
			
				<g:if test="${equipementInstance?.fabriquant}">
				<li class="fieldcontain">
					<span id="fabriquant-label" class="property-label"><g:message code="equipement.fabriquant.label" default="Fabriquant" /></span>
					
						<span class="property-value" aria-labelledby="fabriquant-label"><g:link controller="fournisseurEquipement" action="show" id="${equipementInstance?.fabriquant?.id}">${equipementInstance?.fabriquant?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${equipementInstance?.installateurReparateur}">
				<li class="fieldcontain">
					<span id="installateurReparateur-label" class="property-label"><g:message code="equipement.installateurReparateur.label" default="Installateur/Reparateur" /></span>
					
						<span class="property-value" aria-labelledby="installateurReparateur-label"><g:link controller="fournisseurEquipement" action="show" id="${equipementInstance?.installateurReparateur?.id}">${equipementInstance?.installateurReparateur?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${equipementInstance?.representant}">
				<li class="fieldcontain">
					<span id="representant-label" class="property-label"><g:message code="equipement.representant.label" default="Representant" /></span>
					
						<span class="property-value" aria-labelledby="representant-label"><g:link controller="fournisseurEquipement" action="show" id="${equipementInstance?.representant?.id}">${equipementInstance?.representant?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${equipementInstance?.codeFournisseur}">
				<li class="fieldcontain">
					<span id="codeFournisseur-label" class="property-label"><g:message code="equipement.codeFournisseur.label" default="Code Fournisseur" /></span>
					
						<span class="property-value" aria-labelledby="codeFournisseur-label"><g:fieldValue bean="${equipementInstance}" field="codeFournisseur"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${equipementInstance?.dateFinGarantie}">
				<li class="fieldcontain">
					<span id="dateFinGarantie-label" class="property-label"><g:message code="equipement.dateFinGarantie.label" default="Date Fin Garantie" /></span>
					
						<span class="property-value" aria-labelledby="dateFinGarantie-label"><g:formatDate format="dd/MM/yy" date="${equipementInstance?.dateFinGarantie}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${equipementInstance?.model}">
				<li class="fieldcontain">
					<span id="model-label" class="property-label"><g:message code="equipement.model.label" default="Model" /></span>
					
						<span class="property-value" aria-labelledby="model-label"><g:fieldValue bean="${equipementInstance}" field="model"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${equipementInstance?.numeroDeSerie}">
				<li class="fieldcontain">
					<span id="numeroDeSerie-label" class="property-label"><g:message code="equipement.numeroDeSerie.label" default="Numero De Serie" /></span>
					
						<span class="property-value" aria-labelledby="numeroDeSerie-label"><g:fieldValue bean="${equipementInstance}" field="numeroDeSerie"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${equipementInstance?.caracteristiques}">
				<li class="fieldcontain">
					<span id="caracteristiques-label" class="property-label"><g:message code="equipement.caracteristiques.label" default="Caracteristiques" /></span>
					
						<g:each in="${equipementInstance.caracteristiques}" var="c">
						<span class="property-value" aria-labelledby="caracteristiques-label"><g:link controller="caracteristiqueEquipement" action="show" id="${c.id}">${c?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
				<g:if test="${equipementInstance?.pieces}">
				<li class="fieldcontain">
					<span id="pieces-label" class="property-label"><g:message code="equipement.pieces.label" default="Pieces" /></span>
					
						<g:each in="${equipementInstance.pieces}" var="p">
						<span class="property-value" aria-labelledby="pieces-label"><g:link controller="pieceDeRechange" action="show" id="${p.id}">${p?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
				<g:if test="${equipementInstance?.sousEquipements}">
				<li class="fieldcontain">
					<span id="sousEquipements-label" class="property-label"><g:message code="equipement.sousEquipements.label" default="Sous Equipements" /></span>
						<table>
							<tr><th>Type-Equipement</th><th>Code</th><th>Localisation</th></tr>
							<g:each in="${equipementInstance.sousEquipements}" var="s">
								<tr><td>${s?.typeEquipement}</td><td><g:link controller="equipement" action="show" id="${s.id}">${s?.code}</g:link></td><td>${s?.localisation}</td></tr>
							</g:each>
						</table>
				</li>
				</g:if>


				<g:if test="${equipementInstance?.prochaineIntervention}">
				<li class="fieldcontain">
					<span id="prochaineIntervention-label" class="property-label"><g:message code="equipement.prochaineIntervention.label" default="la prochaine intervention" /></span>
					<span class="property-value" aria-labelledby="prochaineIntervention-label">${equipementInstance?.prochaineIntervention}</span>
					
				</li>
				</g:if>



				<g:if test="${equipementInstance?.demandesNonResolue}">
				<li class="fieldcontain">
					<span id="demandesNonResolue-label" class="property-label"><g:message code="equipement.demandesNonResolue.label" default="demandes Non Resolue" /></span>
						<table>
							<tr><th>Date-Demande</th>
								<th>demande par</th>
								<th>Localisation</th>
								<th>Date-D'appel</th>
								<th>fournisseur appelé</th>
							</tr>
							<g:each in="${equipementInstance.demandesNonResolue}" var="s">
								<tr><td>${s?.dateDemande.format("DD/MM/yy HH:mm")}</td>
									<td><g:link controller="demandeIntervention" action="show" id="${s.id}">${s?.demandeePar}</g:link></td>
									<td>${s?.localisation}</td>
									<g:if test="${s?.dateDappelle}"><td>${s?.dateDappelle.format("DD/MM/yy HH:mm")}</td></g:if>
									<td>${s?.fournisseurAppele}</td>
								</tr>
							</g:each>
						</table>
				</li>
				</g:if>
				

			
				<g:if test="${equipementInstance?.organes}">
				<li class="fieldcontain">
					<span id="organes-label" class="property-label"><g:message code="equipement.organes.label" default="Organes" /></span>
						<table>
							<tr><th>Type-Equipement</th><th>Libelle</th><th>Code</th></tr>
							<g:each in="${equipementInstance.organes}" var="o">
								<tr><td>${o?.typeEquipement}</td><td><g:link controller="equipement" action="show" id="${o.id}">${o?.libelle}</g:link></td><td>${o?.code}</td></tr>
							</g:each>
						</table>
				</li>
				</g:if>
				
				<g:if test="${equipementInstance?.caracteristiquesSupplementaires}">
				<li class="fieldcontain">
					<span id="caracteristiquesSupplementaires-label" class="property-label"><g:message code="equipement.caracteristiquesSupplementaires.label" default="Caracteristiques Supplementaires" /></span>
					
						<span class="property-value" aria-labelledby="caracteristiquesSupplementaires-label"><g:fieldValue bean="${equipementInstance}" field="caracteristiquesSupplementaires"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${equipementInstance?.consignesSecurite}">
				<li class="fieldcontain">
					<span id="consignesSecurite-label" class="property-label"><g:message code="equipement.consignesSecurite.label" default="Consignes Sécurité" /></span>
					<span class="property-value" aria-labelledby="consignesSecurite-label"><g:fieldValue bean="${equipementInstance}" field="consignesSecurite"/></span>
				</li>
				</g:if>
			
			</ol>
			</div>
			</td>
			<td>
				<div class="sousform">
					Créer
					<ul  style="margin-left:18px">
						<li>
							<g:link action="create" params="[modelid:equipementInstance.id]">A partir du modèle</g:link>
						</li>
					</ul>
					
					<g:if test="${equipementInstance.typeEquipement.typesOrganes.size()>0}">
					Ajouter
					<ul  style="margin-left:18px">
						<li>
							<g:link action="create" params="['corps.id':equipementInstance.id]">Organe</g:link>
						</li>
					</ul>
					</g:if>
					
					Marquer comme
					<ul  style="margin-left:18px">
						<li>
							<a href="#" onclick="$('#parentmodal').modal()">Sous-equipement</a>
						</li>
						<li>
							<a href="#" onclick="$('#corpsmodal').modal()">Sous-organe</a>
						</li>
						<g:if test="${equipementInstance.parent || equipementInstance.corps}">
						<li>
							<g:link action="desassocier" params="[equipementid:equipementInstance.id]" onclick="return confirm('Êtes vous sûr de vouloir désassocier cet équipement et le rendre indépendant ?  le code changera automatiquement ')">Equipement indépendant</g:link>
						</li>
						</g:if>
					</ul>
				</div>
			</td>
			</tr>
			</table>
			
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${equipementInstance?.id}" />
					<g:link class="edit" action="edit" id="${equipementInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
		
		<div id="parentmodal" class="modal-content">
			<h2>Equipement parent</h2><br/>
			<g:form method="post" >
				<fieldset class="form">
					<div class="fieldcontain ${hasErrors(bean: equipementInstance, field: 'code', 'error')} required">
						<label for="code">
							<g:message code="equipement.code.label" default="Code" />
							<span class="required-indicator">*</span>
						</label>
						<g:hiddenField name="equipementid" value="${equipementInstance?.id}" />
						<g:textField name="parentcode" required="" />
					</div>
					<g:actionSubmit class="save" action="associerParent" value="Associer" /> (* le code changera automatiquement)
				</fieldset>
			</g:form>
		</div>
		
		<div id="corpsmodal" class="modal-content">
			<h2>Equipement corps</h2><br/>
			<g:form method="post" >
				<fieldset class="form">
					<div class="fieldcontain ${hasErrors(bean: equipementInstance, field: 'code', 'error')} required">
						<label for="code">
							<g:message code="equipement.code.label" default="Code" />
							<span class="required-indicator">*</span>
						</label>
						<g:hiddenField name="equipementid" value="${equipementInstance?.id}" />
						<g:textField name="corpscode" required="" />
					</div>
					<g:actionSubmit class="save" action="associerCorps" value="Associer" /> (* le code changera automatiquement)
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
