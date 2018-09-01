package mederp

class Affectation {

	Unite unite
	User user
	
	Date dateAffectation
	Date dateFin
	
	Boolean parDefaut
	
	static belongsTo = [User, Unite]
	
	static mapping = {
		id generator: "increment"
		version false
	}

	static constraints = {
		unite nullable: false
		user nullable: false
		dateAffectation nullable: false
		dateFin nullable: true
		parDefaut nullable: true
	}
	
	String toString () { 
		//message(code: 'affectation.toString', args: [user.toString(), unite.toString(), dateAffectation.format('dd/MM/yy')]) + (dateFin?(" fin : "+dateFin):"")
		"Affectation de " + user +" a " + unite + ", debut " + dateAffectation.format('dd/MM/yy')+ (dateFin?(" fin : "+dateFin):"")
	}
}