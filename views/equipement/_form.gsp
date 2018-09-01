<%@ page import="medgmao.Equipement" %>

<!--Generation template modifiée par rezz-->

 <script>
$(function() {
$( "#tabs" ).tabs();
});
</script>

<div id="tabs">
	<ul>
		<li><a href="#identification">Identification</a></li>
		<li><a href="#fournisseurs">Fournisseurs</a></li>
		<li><a href="#specs">Spécificités</a></li>
		<li><a href="#sousequipements">Sous Equipements</a></li>
		<li><a href="#articles">Articles Attachés</a></li>
	</ul>
	
	<div id="identification">
		<div class="fieldcontain ${hasErrors(bean: equipementInstance, field: 'unite', 'error')} required">
			<label for="unite">
				<g:message code="equipement.unite.label" default="Unite" />
			</label>
			<g:select id="unite" name="unite.id" from="${mederp.Unite.list()}" optionKey="id" required="" value="${equipementInstance?.unite?.id}" class="many-to-one" noSelection="['null': '']"/>
		</div>

		<div class="fieldcontain ${hasErrors(bean: equipementInstance, field: 'typeEquipement', 'error')} required">
			<label for="typeEquipement">
				<g:message code="equipement.typeEquipement.label" default="Type Equipement" />
				<span class="required-indicator">*</span>
			</label>
			
			<g:if test="${equipementInstance.corps?.id}">
				<g:select id="typeEquipement" name="typeEquipement.id" from="${medgmao.TypeEquipement.findAllByCorps(equipementInstance.corps.typeEquipement)}" optionKey="id" optionValue="libelleComplet" value="${equipementInstance?.typeEquipement?.id}" class="many-to-one"/>
			</g:if>
			<g:else>
				<g:if test="${ ! equipementInstance.id}">
					<g:hiddenField name="typeEquipement.id" value="${equipementInstance?.typeEquipement?.id}" /><g:link controller="typeEquipement" action="show" id="${equipementInstance?.typeEquipement?.id}">${equipementInstance?.typeEquipement}</g:link>
				</g:if>
				<g:else>
					<g:select id="typeEquipement" name="typeEquipement.id" from="${medgmao.TypeEquipement.list()}" optionKey="id" optionValue="libelleComplet" required="" value="${equipementInstance?.typeEquipement?.id}" class="many-to-one"/>
				</g:else>
			</g:else>
			
			
		</div>

		<div class="fieldcontain ${hasErrors(bean: equipementInstance, field: 'localisation', 'error')} ">
			<label for="localisation">
				<g:message code="equipement.localisation.label" default="Localisation" />
				<span class="required-indicator">*</span>
			</label>
			<g:select id="localisation" name="localisation.id" from="${mederp.Localisation.list()}" optionKey="id" optionValue="libelleLong" value="${equipementInstance?.localisation?.id}" class="many-to-one"/>
		</div>

		<div class="fieldcontain ${hasErrors(bean: equipementInstance, field: 'libelle', 'error')} required">
			<label for="libelle">
				<g:message code="equipement.libelle.label" default="Libelle" />
				<span class="required-indicator">*</span>
			</label>
			<g:textField name="libelle" id="libelle" maxlength="150" required="" value="${equipementInstance?.libelle}"/>
		</div>

		<g:if test="${equipementInstance?.id}">
		<div class="fieldcontain ${hasErrors(bean: equipementInstance, field: 'code', 'error')} required">
			<label for="code">
				<g:message code="equipement.code.label" default="Code" />
				<span class="required-indicator">*</span>
			</label>
			<g:textField name="code" required="" value="${equipementInstance?.code?:equipementInstance?.typeEquipement?.prefix}"/>
		</div>
		</g:if>

		<div class="fieldcontain ${hasErrors(bean: equipementInstance, field: 'numeroOrdre', 'error')} required">
			<label for="numeroOrdre">
				<g:message code="equipement.numeroOrdre.label" default="Numéro d'ordre" />
				<span class="required-indicator">*</span>
			</label>
			<g:textField name="numeroOrdre" required="" value="${equipementInstance?.numeroOrdre}"/> <small>(* dans la même catégorie)</small>
		</div>

		<div class="fieldcontain ${hasErrors(bean: equipementInstance, field: 'identifiant', 'error')} required">
			<label for="identifiant">
				<g:message code="equipement.identifiant.label" default="Identifiant" />
				<span class="required-indicator">*</span>
			</label>
			<g:textField name="identifiant" value="${equipementInstance?.identifiant}"/>
		</div>

		<div class="fieldcontain ${hasErrors(bean: equipementInstance, field: 'marque', 'error')} required">
			<label for="marque">
				<g:message code="equipement.marque.label" default="Marque" />
				<span class="required-indicator">*</span>
			</label>
			<g:textField name="marque" value="${equipementInstance?.marque}"/>
		</div>

		 <div class="fieldcontain ${hasErrors(bean: equipementInstance, field: 'dateMiseEnService', 'error')} required">
			<label for="dateMiseEnService">
				<g:message code="equipement.dateMiseEnService.label" default="Date mise en service" />
				<span class="required-indicator">*</span>
			</label>
			<g:datePicker name="dateMiseEnService" precision="day"  value="${equipementInstance?.dateMiseEnService}"  />
		</div> 

		<g:if test="${equipementInstance?.parent}">
		<div class="fieldcontain ${hasErrors(bean: equipementInstance, field: 'parent', 'error')} ">
			<label for="parent">
				<g:message code="equipement.parent.label" default="Parent" />
				
			</label>
			<g:hiddenField name="parent.id" value="${equipementInstance?.parent?.id}" /><g:link controller="equipement" action="show" id="${equipementInstance?.parent?.id}">${equipementInstance?.parent}</g:link>
		</div>
		</g:if>
		
		<g:if test="${equipementInstance?.corps}">
		<div class="fieldcontain ${hasErrors(bean: equipementInstance, field: 'corps', 'error')} ">
			<label for="corps">
				<g:message code="equipement.corps.label" default="Organe de" />
			</label>
			<g:hiddenField name="corps.id" value="${equipementInstance?.corps?.id}" /><g:link controller="equipement" action="show" id="${equipementInstance?.corps?.id}">${equipementInstance?.corps}</g:link>
		</div>
		</g:if>
	</div>
		
	<div id="fournisseurs">	
		<div class="fieldcontain ${hasErrors(bean: equipementInstance, field: 'fournisseur', 'error')} ">
			<label for="fournisseur">
				<g:message code="equipement.fournisseur.label" default="Fournisseur" />
				
			</label>
			<g:select id="fournisseur" name="fournisseur.id" from="${medgmao.FournisseurEquipement.findAllFournisseur()}" optionKey="id" value="${equipementInstance?.fournisseur?.id}" class="many-to-one" noSelection="['null': '']"/>
		</div>

		<div class="fieldcontain ${hasErrors(bean: equipementInstance, field: 'fabriquant', 'error')} ">
			<label for="fabriquant">
				<g:message code="equipement.fabriquant.label" default="Fabriquant" />
				
			</label>
			<g:select id="fabriquant" name="fabriquant.id" from="${medgmao.FournisseurEquipement.findAllFabriquant()}" optionKey="id" value="${equipementInstance?.fabriquant?.id}" class="many-to-one" noSelection="['null': '']"/>
		</div>

		<div class="fieldcontain ${hasErrors(bean: equipementInstance, field: 'installateurReparateur', 'error')} ">
			<label for="installateurReparateur">
				<g:message code="equipement.fabriquant.label" default="Installateur/Reparateur" />
				
			</label>
			<g:select id="installateurReparateur" name="installateurReparateur.id" from="${medgmao.FournisseurEquipement.findAllInstallateurReparateur()}" optionKey="id" value="${equipementInstance?.installateurReparateur?.id}" class="many-to-one" noSelection="['null': '']"/>
		</div>

		<div class="fieldcontain ${hasErrors(bean: equipementInstance, field: 'representant', 'error')} ">
			<label for="representant">
				<g:message code="equipement.fabriquant.label" default="Representant" />
			</label>
			<g:select id="representant" name="representant.id" from="${medgmao.FournisseurEquipement.findAllRepresentant()}" optionKey="id" value="${equipementInstance?.representant?.id}" class="many-to-one" noSelection="['null': '']"/>
		</div>

		<div class="fieldcontain ${hasErrors(bean: equipementInstance, field: 'codeFournisseur', 'error')} ">
			<label for="codeFournisseur">
				<g:message code="equipement.codeFournisseur.label" default="Code Fournisseur" />
				
			</label>
			<g:textField name="codeFournisseur" value="${equipementInstance?.codeFournisseur}"/>
		</div>

		<div class="fieldcontain ${hasErrors(bean: equipementInstance, field: 'model', 'error')} ">
			<label for="model">
				<g:message code="equipement.model.label" default="Model" />
				
			</label>
			<g:textField name="model" value="${equipementInstance?.model}"/>
		</div>

		<div class="fieldcontain ${hasErrors(bean: equipementInstance, field: 'numeroDeSerie', 'error')} ">
			<label for="numeroDeSerie">
				<g:message code="equipement.numeroDeSerie.label" default="Numero De Serie" />
				
			</label>
			<g:textField name="numeroDeSerie" value="${equipementInstance?.numeroDeSerie}"/>
		</div>

		<div class="fieldcontain ${hasErrors(bean: equipementInstance, field: 'dateFinGarantie', 'error')} required">
			<label for="dateFinGarantie">
				<g:message code="equipement.dateFinGarantie.label" default="Date Fin Garantie" />
				<span class="required-indicator">*</span>
			</label>
			<g:datePicker name="dateFinGarantie" precision="day"  value="${equipementInstance?.dateFinGarantie}"  />
		</div>
	</div>
		
	<div id="specs">	
		<div class="fieldcontain ${hasErrors(bean: equipementInstance, field: 'caracteristiques', 'error')} ">
			<label for="caracteristiques">
				<g:message code="equipement.caracteristiques.label" default="Caracteristiques" />
			</label>
			<table> 
				<tr>
					<g:each in="${medgmao.TypeCaracteristique.list()}" var="typeCaracteristique" status="i">
						<td>${typeCaracteristique.libelle}</td>
						<td>
						<g:select name="caracteristiques" from="${typeCaracteristique.caracteristiques}" multiple="multiple" optionKey="id" optionValue="libelle" size="5" value="${equipementInstance?.caracteristiques*.id}" class="many-to-many"/> <br/>
						</td>
						<g:if test="${(i+1)%3==0}"></tr><tr></g:if>
					</g:each>
				</tr>
			</table>
		</div>

		<div class="fieldcontain ${hasErrors(bean: equipementInstance, field: 'caracteristiquesSupplementaires', 'error')} required">
			<label for="caracteristiquesSupplementaires">
				<g:message code="equipement.caracteristiquesSupplementaires.label" default="Caracteristiques Supplementaires" />
				<span class="required-indicator">*</span>
			</label>
			<g:textArea name="caracteristiquesSupplementaires" cols="40" rows="5" style="width:500Px" maxlength="500" value="${equipementInstance?.caracteristiquesSupplementaires}"/>
		</div>

		<div class="fieldcontain ${hasErrors(bean: equipementInstance, field: 'consignesSecurite', 'error')} required">
			<label for="consignesSecurite">
				<g:message code="equipement.consignesSecurite.label" default="Consignes Sécurité" />
				<span class="required-indicator">*</span>
			</label>
			<g:textArea name="consignesSecurite" cols="40" rows="5" style="width:500Px" maxlength="500" value="${equipementInstance?.consignesSecurite}"/>
		</div>
	</div>
		
	<div id="sousequipements">	
		<div class="fieldcontain ${hasErrors(bean: equipementInstance, field: 'sousEquipements', 'error')} ">
			<label for="sousEquipements">
				<g:message code="equipement.sousEquipements.label" default="Sous Equipements" />
				
			</label>
				
			<ul class="one-to-many">
			<g:each in="${equipementInstance?.sousEquipements?}" var="s">
				<li><g:link controller="equipement" action="show" id="${s.id}">${s?.encodeAsHTML()}</g:link></li>
			</g:each>
			<li class="add">
			<g:link controller="equipement" action="create" params="['equipement.id': equipementInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'equipement.label', default: 'Equipement')])}</g:link>
			</li>
			</ul>

		</div>

	</div>
	
	<div id="articles">	
		

	</div>
	
</div>

<%--


<div class="fieldcontain ${hasErrors(bean: equipementInstance, field: 'pieces', 'error')} ">
	<label for="pieces">
		<g:message code="equipement.pieces.label" default="Pieces" />
		
	</label>
	
<ul class="one-to-many">
<g:each in="${equipementInstance?.pieces?}" var="p">
    <li><g:link controller="pieceDeRechange" action="show" id="${p.id}">${p?.encodeAsHTML()}</g:link></li>
</g:each>
<li class="add">
<g:link controller="pieceDeRechange" action="create" params="['equipement.id': equipementInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'pieceDeRechange.label', default: 'PieceDeRechange')])}</g:link>
</li>
</ul>

</div>
--%>