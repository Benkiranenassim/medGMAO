package mederp

import medgmao.*

class Localisation {
	String libelleCourt
	String libelle
	String photo
	Integer ordre
	Localisation parent
	TypeLocalisation typeLocalisation
	Integer capacite
	Double  superficie
	Unite unite
		
	static belongsTo = [Localisation]
	static hasMany = [sousLocalisations: Localisation]
	
	static mapping = {
		id generator: "increment"
		sort libelleCourt: "asc"
		version false
		sousLocalisations sort : "ordre"
	}

	static constraints = {
		libelle maxSize: 150, blank: false
		libelleCourt maxSize: 20, nullable: true, unique:true
		photo nullable: true
		typeLocalisation nullable:false
		parent nullable: true
		ordre  nullable: true
		capacite   nullable: true
		superficie nullable: true
		unite nullable: true
	}
	
	String toString () {
		libelle
	}
	
	String getLibelleLong(){
		""+libelleCourt+": "+libelle
	}
	
	List allSousLocalisations(int depth=0) {
		def loc = []
		if (depth > 9) {
			printlln "!!!!!!!!!!!!!!!!!!!!!le système des Localisations ne peut pas avoir une arborescence de plus de 10, la reccursuvité pourrait faire souffrire les perfs"
			return loc
		}
		if (sousLocalisations.size()>0) {
			loc += sousLocalisations
			for (def st : sousLocalisations) {
				loc += st.allSousLocalisations(depth +1)
			}
		}
		return loc
	}
	
	List localisationAndAllSousLocalisations() {
		return allSousLocalisations()+this
	}
}