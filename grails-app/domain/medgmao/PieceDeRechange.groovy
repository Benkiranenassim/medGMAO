package medgmao

import mederp.*

class PieceDeRechange {
	String libelle
	String code
	String codeFournisseur
	String codeStock
	Date dateFinGarantie
	String marque
	String model
	String numeroDeSerie
	
	Date dateInstallation
	Date dateDesinstallation
	
	Equipement equipement
	
	static mapping = {
		id generator: "increment"
		sort libelle: "asc"
		version false
	}

	static constraints = {
		libelle maxSize: 150, blank: false
		equipement nullable:false
		code blank: false
		marque blank: false
		dateInstallation nullable:false
		dateDesinstallation nullable:true
	}
	
	String toString () {
		libelle
	}

}