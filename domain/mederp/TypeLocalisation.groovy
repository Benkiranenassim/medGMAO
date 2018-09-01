package mederp

class TypeLocalisation {

	String libelle
	Integer niveau // les niveaux servent pour interdire d'imbriquer un niveau superieur dans un niveau inferieur (on ne peut pas trouver un etablissement dans un couloir !)
	
	TypeLocalisation parent
	
	static hasMany = [sousTypes: TypeLocalisation]
	
	static mapping = {
		id generator: "increment"
		sort niveau: "asc"
		version false
	}

	static constraints = {
		libelle maxSize: 150, blank: false
		niveau nullable : false
		parent nullable : true
	}
	
	String toString () {
		libelle
	}
}
