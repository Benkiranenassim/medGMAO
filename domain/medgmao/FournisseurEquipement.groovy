package medgmao

class FournisseurEquipement {

	String libelle
	String adresse
	String tel
	String tel2
	String email
	
	Boolean fournisseur
	Boolean fabriquant
	Boolean installateurReparateur
	Boolean representant

	static mapping = {
		id generator: "increment"
		sort libelle: "asc"
		version false
	}

	static constraints = {
		libelle maxSize: 50, nullable: false
		adresse maxSize: 500, nullable: true
		tel maxSize: 20, nullable: true, matches:/[\+]{0,1}[0-9\s]{3,15}/
		tel2 maxSize: 20, nullable: true, matches:/[\+]{0,1}[0-9\s]{3,15}/
		email email:true, nullable:true
		fournisseur nullable:true
		fabriquant nullable:true
		installateurReparateur nullable:true
		representant nullable:true
	}
	

	String toString () {
		libelle
	}
}