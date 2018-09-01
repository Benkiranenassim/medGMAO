package mederp

import medgmao.*

class Unite {
	String libelle
	String libelleCourt
	Etablissement etablissement
	String typeUnite
	Boolean medicale
	
	static belongsTo = [Etablissement]
	static hasMany = [equipements: Equipement,localisations: Localisation]
	
	static mapping = {
		id generator: "increment"
		sort libelle: "asc"
		chambres sort: "libelle"
		version false
	}

	static constraints = {
		etablissement nullable: false
		libelle maxSize: 150, nullable: false
		libelleCourt maxSize: 15, nullable: false
		typeUnite nullable:true
		medicale nullable:true
	}
	
	String toString () {
		libelle
	}

}