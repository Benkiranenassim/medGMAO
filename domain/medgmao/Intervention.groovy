package medgmao

import mederp.*

class Intervention {

	Equipement equipement
	Date dateProgrammee
	Date dateIntervention
	User ordonneePar
	User executeePar
	String responsableFournisseur
	Boolean intervenantExterne = false
	String texte

	
	
	Localisation localisation
	TypeIntervention type
	DemandeIntervention demande
	ProgrammationMaintenance programmationMaintenance
	Integer niveauUrgence = 0

	static belongsTo =[equipement: Equipement]

	
	
	static mapping = {
		id generator: "increment"
		sort dateIntervention: "desc"
		version false
	}

	static constraints = {
		responsableFournisseur nullable: true
		dateProgrammee nullable: false
		dateIntervention nullable: true
		ordonneePar nullable: true
		localisation nullable: false
		texte  maxSize: 1000, blank: false
		equipement nullable: true
		demande nullable: true
		niveauUrgence nullable : false
	}
	
	String toString () {
		"" + (dateIntervention?:dateProgrammee) + " -> " + localisation + " :" + texte
	}
}
