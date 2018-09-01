package medgmao

import org.codehaus.groovy.runtime.TimeCategory
import groovy.time.TimeCategory
import mederp.*

class Equipement {
	String libelle
	String code
	Integer numeroOrdre
	String identifiant
	String codeFournisseur	
	Date dateFinGarantie
	String marque
	String model
	String numeroDeSerie
	TypeEquipement typeEquipement
	String caracteristiquesSupplementaires
	String consignesSecurite
	Date dateMiseEnService
	
	Unite unite
	Equipement parent
	Equipement corps
	Localisation localisation
	FournisseurEquipement fournisseur
	FournisseurEquipement fabriquant
	FournisseurEquipement installateurReparateur
	FournisseurEquipement representant
	Date prochaineIntervention
	ProgrammationMaintenance programmationMaintenance

	

	static belongsTo = [programmationMaintenance: ProgrammationMaintenance]
	static hasMany = [pieces: PieceDeRechange, sousEquipements: Equipement,intervention: Intervention,demandeIntervention: DemandeIntervention,demandesNonResolue: DemandeIntervention, organes: Equipement, caracteristiques: CaracteristiqueEquipement, documents: DocumentTechnique] 
	static mappedBy = [sousEquipements: "parent", organes: "corps"]
	
	
	static mapping = {
		id generator: "increment"
		sort libelle: "asc"
		version false
		sousEquipements sort : "libelle"
		organes sort : "typeEquipement"
	}

	static constraints = {

		unite nullable: true
		libelle maxSize: 150, blank: false
		code blank: false
		identifiant unique: true, blank: false
		marque blank: false
		typeEquipement nullable:false
		parent nullable:true
		corps nullable: true
		localisation nullable:true
		dateMiseEnService nullable: true
		prochaineIntervention nullable: true
		fournisseur nullable: true
		fabriquant nullable: true
		representant nullable: true
		numeroDeSerie nullable: true
		installateurReparateur nullable: true
		caracteristiquesSupplementaires  maxSize: 500,nullable: true
		consignesSecurite maxSize: 500,nullable: true
		numeroOrdre unique: 'typeEquipement'
		codeFournisseur nullable: true
		dateFinGarantie nullable: true
		marque nullable: true
		model nullable: true
		programmationMaintenance nullable: true
	}
	
	String toString () {
		libelle
	}

	List getDemandesNonResolue () {

		return DemandeIntervention.findAllByDateResolutionIsNull()
	}


	//FIXME : prendre la date de la dernière intervention si existe sinon de la date de Mise en service 
	//ajouter l'interval de la programmation intervention
	Date getProchaineIntervention(){

		if(programmationMaintenance){
		
			Integer unInterval=programmationMaintenance.interval
		
			if(intervention){
				use(TimeCategory) {
					prochaineIntervention=intervention.listOrderDateIntervention(max: 1, order: "desc")+unInterval.days
				}
			}
		
			else{
				use(TimeCategory) {
					prochaineIntervention=getDateMiseEnService()+unInterval.days
				}

			}
			return prochaineIntervention
		}
	else return null
	}

}
