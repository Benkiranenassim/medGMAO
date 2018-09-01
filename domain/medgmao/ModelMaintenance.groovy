package medgmao

import mederp.*

class ModelMaintenance {
	String libelle
	TypeEquipement typeEquipement
	String description
	Integer interval
	
	static hasMany = [actions: ActionProgrammee]
	static belongsTo = [TypeEquipement]
	
	static mapping = {
		id generator: "increment"
		sort typeEquipement: "desc"
		version false
	}

	static constraints = {
		libelle nullable: false
		description  maxSize: 1000
		typeEquipement nullable: false
		interval nullable: false
	}
	
	String toString () {
		libelle
	}

}