package medgmao


class DocumentTechnique {
	String libelle
	String chemin
	String commentaire
	String type //plan, manuel, maintenance

	static hasMany = [equipements: Equipement] 
	static belongsTo = [Equipement]

	static mapping = {
		id generator: "increment"
		version false
	}

	static constraints = {
		libelle unique: true,blank: false
		chemin blank: false, maxSize: 100
		commentaire nullable: true, maxSize: 500
	}
	
	String toString () {
		libelle
	}
}