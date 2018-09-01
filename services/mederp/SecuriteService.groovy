package mederp

class SecuriteService {
	//def springSecurityService

	def getUserNomComplet() {	
		if (LicenceInfo.getTypeLicence() == "standard" || LicenceInfo.getTypeLicence() == "etendue") {
			return 'Dr ' + LicenceInfo.nom + ' ' + LicenceInfo.prenom
		} else {
			//FIXME review this !
			return session.user.nomComplet()
		}
	}
	
	def getUserUniteDefaut(User user) {	
		def unite
		for (Affectation aff: Affectation.findAllByUser(user)) {
			unite = aff.unite
			if (aff.parDefaut) {
				break
			}
		}
		return unite
	}
	
	def encodePassword(String s) {
		return s.encodeAsSHA256()
	}
		
	def authenticate(String username, String password) {
		def user = User.findByUsername(username)
		org.codehaus.groovy.grails.web.servlet.mvc.GrailsWebRequest request = org.springframework.web.context.request.RequestContextHolder.currentRequestAttributes()
		org.codehaus.groovy.grails.web.servlet.mvc.GrailsHttpSession session = request.session
		
		if (user && (password.encodeAsSHA256() == user.password) && user.enabled) {
			//println "authentified"
			session.user = user
			session.unite = getUserUniteDefaut(user)
			session.authorities = user.roles*.role.authority
			println user.roles*.role
			HashSet controllers = new HashSet()
			
			for (def r : user.roles*.role) {
				if (r.controllers()?.size())
					controllers += Arrays.asList(r.controllers())
			}
			
			session.controllers = controllers
			
			return true
		} else {
			return false
		}
	}
	

}