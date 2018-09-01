package medgmao

class GroupeEquipements {
	String libelle

	static hasMany = [equipements: Equipement]
	
	static mapping = {
		id generator: "increment"
		sort libelle: "asc"
		version false
	}

	static constraints = {
		libelle maxSize: 150, nullable: false
	}
	
	String toString () {
		libelle
	}

}