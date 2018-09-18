package medgmao

class TypeCaracteristique {

	String libelle
	
	static hasMany = [caracteristiques: CaracteristiqueEquipement]
	
	static mapping = {
		id generator: "increment"
		version false
	}

	static constraints = {
		libelle maxSize: 150, blank: false
	}
	
	String toString () {
		libelle
	}
}
