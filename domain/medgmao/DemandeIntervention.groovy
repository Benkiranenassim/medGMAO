package medgmao

import mederp.*

class DemandeIntervention {
	
	User demandeePar
	Date dateDemande
	Localisation localisation
	
	CategorieDemande categorie
	String description
	Boolean blocage
	Integer niveauUrgence
	Date dateResolution
	Date dateDappelle
	String fournisseurAppele
	
	Boolean cloturee
	Equipement equipement
	static belongsTo = [equipement: Equipement]
	static hasMany = [interventions: Intervention]
	
	static mapping = {
		id generator: "increment"
		sort dateDemande: "desc"
		version false
	}

	static constraints = {
		
		dateResolution nullable: true
		demandeePar nullable: false
		dateDemande nullable: false , blank: false
		dateDappelle nullable: true
		fournisseurAppele nullable: true
		localisation nullable: false
		categorie nullable: false
		
		description  maxSize: 1000, blank: false
		equipement nullable: true
		niveauUrgence nullable: false
		blocage nullable: false
		cloturee nullable: false
	}
	
	String toString () {
		"" + (dateIntervention?:dateProgrammee) + " -> " + localisation + " :" + texte
	}

}