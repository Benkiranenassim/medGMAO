package medgmao

class CaracteristiqueEquipement {

	String libelle
	TypeCaracteristique typeCaracteristique
	
	static belongsTo = [TypeCaracteristique, Equipement]
	static hasMany = [equipements : Equipement]
	
	static mapping = {
		id generator: "increment"
		version false
	}

	static constraints = {
		libelle maxSize: 150, blank: false
		typeCaracteristique nullable: false
	}
	
	String toString () {
		"" + typeCaracteristique +": "+ libelle
	}
}
