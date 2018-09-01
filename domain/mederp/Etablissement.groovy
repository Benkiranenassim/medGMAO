package mederp

class Etablissement {

	String libelle
	String raisonSociale
	String adresse
	String ville
	String tel
	String tel2
	String logo
	User directeur
	
	static hasMany = [unites: Unite]
	
	static mapping = {
		id generator: "increment"
		sort libelle: "asc"
		unites sort : "libelle"
		version false
	}

	static constraints = {
		libelle maxSize: 50, nullable: false
		raisonSociale nullable: true
		adresse nullable: true, maxSize: 1000
		ville nullable: true
		tel nullable: true
		tel2 nullable: true
		directeur nullable: true
		logo nullable: true
	}
	
	String toString () {
		libelle
	}
}