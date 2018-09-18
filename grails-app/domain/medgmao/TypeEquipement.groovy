package medgmao
import mederp.User

class TypeEquipement {

	String libelle
	TypeEquipement parent
	TypeEquipement corps
	String prefix
	User responsable
	
	static hasMany = [sousTypes: TypeEquipement, typesOrganes:TypeEquipement,modelsMaintenance:ModelMaintenance]
	static mappedBy = [sousTypes: "parent", typesOrganes: "corps"]
	
	static mapping = {
		id generator: "increment"
		sort prefix: "asc"
		version false
		sousTypes sort : "prefix"
	}

	static constraints = {
		libelle maxSize: 150
		parent nullable: true
		corps nullable: true
		prefix nullable: true
		responsable nullable: true
	}
	
	String toString () {
		libelle
	}
	
	String getLibelleComplet() {
		prefix + " : " + libelle
	}
	
	List allSousTypes(int depth=0) {
		def ast = []
		if (depth > 9) {
			printlln "!!!!!!!!!!!!!!!!!!!!!le système des typesEquipement ne peut pas avoir une arborescence de plus de 10, la reccursuvité pourrait faire souffrire les perfs"
			return ast
		}
		if (sousTypes.size()>0) {
			ast += sousTypes
			for (def st : sousTypes) {
				ast += st.allSousTypes(depth +1)
			}
		}
		return ast
	}
	
	List typeAndAllSousTypes() {
		return allSousTypes()+this
	}
	
	List allEquipements () {
		Equipement.executeQuery("select e from Equipement as e where e.typeEquipement in (:types)", [types: typeAndAllSousTypes()])
	}
}