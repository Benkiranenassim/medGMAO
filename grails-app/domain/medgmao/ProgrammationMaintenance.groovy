package medgmao

import mederp.*

class ProgrammationMaintenance {
	
	Equipement equipement
	ModelMaintenance model
	User responsable
	String description
	Integer interval
	
	static hasMany = [interventions: Intervention]
	//static belongsTo = [equipement:Equipement]
	
	static mapping = {
		id generator: "increment"
		sort 'equipement.typeEquipement': "desc"
		version false
	}

	static constraints = {
		equipement nullable: false
		model nullable: false
		description  maxSize: 1000, nullable: true
		interval nullable: false
		responsable nullable: false
	}
	
	String toString () {
		"" + equipement + ": " +model
	}

}