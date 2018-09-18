<%@ page import="medgmao.FournisseurEquipement" %>

<!--Generation template modifiée par rezz-->

<div class="fieldcontain ${hasErrors(bean: fournisseurEquipementInstance, field: 'libelle', 'error')} ">
	<label for="libelle">
		<g:message code="fournisseurEquipement.libelle.label" default="Libelle" />
		
	</label>
	<g:textField name="libelle" maxlength="50" value="${fournisseurEquipementInstance?.libelle}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: fournisseurEquipementInstance, field: 'adresse', 'error')} ">
	<label for="adresse">
		<g:message code="fournisseurEquipement.adresse.label" default="Adresse" />
		
	</label>
	<g:textArea name="adresse" cols="40" rows="5" maxlength="500" value="${fournisseurEquipementInstance?.adresse}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: fournisseurEquipementInstance, field: 'tel', 'error')} ">
	<label for="tel">
		<g:message code="fournisseurEquipement.tel.label" default="Tel" />
		
	</label>
	<g:textField name="tel" maxlength="20" pattern="${fournisseurEquipementInstance.constraints.tel.matches}" value="${fournisseurEquipementInstance?.tel}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: fournisseurEquipementInstance, field: 'tel2', 'error')} ">
	<label for="tel2">
		<g:message code="fournisseurEquipement.tel2.label" default="Tel2" />
		
	</label>
	<g:textField name="tel2" maxlength="20" pattern="${fournisseurEquipementInstance.constraints.tel2.matches}" value="${fournisseurEquipementInstance?.tel2}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: fournisseurEquipementInstance, field: 'email', 'error')} ">
	<label for="email">
		<g:message code="fournisseurEquipement.email.label" default="Email" />
		
	</label>
	<g:field type="email" name="email" value="${fournisseurEquipementInstance?.email}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: fournisseurEquipementInstance, field: 'fournisseur', 'error')} ">
	<label for="fournisseur">
		<g:message code="fournisseurEquipement.fournisseur.label" default="Fournisseur" />
		
	</label>
	<g:checkBox name="fournisseur" value="${fournisseurEquipementInstance?.fournisseur}" />
</div>

<div class="fieldcontain ${hasErrors(bean: fournisseurEquipementInstance, field: 'fabriquant', 'error')} ">
	<label for="fabriquant">
		<g:message code="fournisseurEquipement.fabriquant.label" default="Fabriquant" />
		
	</label>
	<g:checkBox name="fabriquant" value="${fournisseurEquipementInstance?.fabriquant}" />
</div>

<div class="fieldcontain ${hasErrors(bean: fournisseurEquipementInstance, field: 'installateurReparateur', 'error')} ">
	<label for="installateurReparateur">
		<g:message code="fournisseurEquipement.installateurReparateur.label" default="Installateur Reparateur" />
		
	</label>
	<g:checkBox name="installateurReparateur" value="${fournisseurEquipementInstance?.installateurReparateur}" />
</div>

<div class="fieldcontain ${hasErrors(bean: fournisseurEquipementInstance, field: 'representant', 'error')} ">
	<label for="representant">
		<g:message code="fournisseurEquipement.representant.label" default="Representant" />
		
	</label>
	<g:checkBox name="representant" value="${fournisseurEquipementInstance?.representant}" />
</div>

