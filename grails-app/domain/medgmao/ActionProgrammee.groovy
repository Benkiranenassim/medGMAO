package medgmao

import mederp.*

class ActionProgrammee {
	String libelle
	ModelMaintenance modelMaintenance
	String description
	
	static belongsTo = [ModelMaintenance]
	
	static mapping = {
		id generator: "increment"
		sort modelMaintenance: "desc"
		version false
	}

	static constraints = {
		libelle nullable: false
		description  maxSize: 1000
		modelMaintenance nullable: false
	}
	
	String toString () {
		libelle
	}

}