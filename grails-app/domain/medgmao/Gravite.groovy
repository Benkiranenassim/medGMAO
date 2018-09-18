package medgmao

class Gravite {

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
