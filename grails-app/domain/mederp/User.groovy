package mederp

import medgmao.*

class User {

	transient securiteService

	String username
	String password
	String nom
	String prenom
	String titre
	String email
	String tel
	boolean enabled
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired
	Boolean permanent
	Integer pourcentagePercu=0
	
	static hasMany = [roles: UserRole, affectations: Affectation, maintenancesProgrammees: ProgrammationMaintenance]
	
	static constraints = {
		username blank: false, unique: true
		password blank: false
		nom nullable: true
		prenom nullable: true
		titre nullable: true
		permanent nullable: true
		email email:true, nullable:true
		tel nullable:true, maxSize:20,  matches:/[\+]{0,1}[0-9\s]{3,15}/
		pourcentagePercu nullable: true, min: 0, max: 100
	}

	static mapping = {
		password column: '`password`'
		sort nom: "asc"
		affectations sort : "dateAffectation", order:"desc"
		intervention sort : "responsable", order:"desc"
	}

	Set<Role> getAuthorities() {
		UserRole.findAllByUser(this).collect { it.role } as Set
	}
	/**/
	def beforeInsert() {
		//encodePassword()
	}

	def beforeUpdate() {
		//if (isDirty('password')) {
		//	encodePassword()
		//}
	}

	protected void encodePassword() {
		//password = securiteService.encodePassword(password)
	}
	
	String toString() {
		nomComplet()
	}
	
	String nomComplet() {
		(titre?titre+' ':'') +  nom + (prenom?' '+prenom:'')
	}
	
	String nomCourt() {
		(titre?titre+' ':'') +  nom
	}
	
	def getUniteDefaut() {	
		def unite
		for (Affectation aff: affectations) {
			unite = aff.unite
			if (aff.parDefaut) {
				break
			}
		}
		return unite
	}
}
