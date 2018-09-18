package medgmao

class TypeIntervention  {

	String libelle
	
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
