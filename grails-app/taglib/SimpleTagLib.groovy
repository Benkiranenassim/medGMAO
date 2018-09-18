import groovy.sql.Sql

class SimpleTagLib {
	def loged = { attrs ->
		out << mederp.LicenceInfo.toString()
	}
	
	def ifLoggedIn = { attrs, body ->
		if (session.user) {
			out << body()
		} else {
			out << ""
		}
	}
	
	def ifNotLoggedIn = { attrs, body ->
		if ( ! session.user) {
			out << body()
		} else {
			out << ""
		}
	}
	
	def defaultUser = {
		if (mederp.AppParams.database=='dentiste')
			out << "dentiste"
		else if (mederp.AppParams.database=='therapeute')
			out << 'therapeute'
		else
			out << 'medecin'
	}
	
	def userComplet = {
		try {
			def user = session.user
			if (user.nom||user.prenom)
				out << user.nomComplet()
			else
				out << user.username
		} catch (Exception e) {
			//FIXME traiter l'exception de non affectation a une unité
		}

	}
	
	def userCourt = {
		try {
			//mederp.User user = mederp.User.get(springSecurityService.principal.id)
			out << session.user.nomCourt()
		} catch (Exception e) {
			//FIXME traiter l'exception de non affectation a une unité
		}

	}
	
	def ifAllGranted = {attrs, body ->
		def roles = attrs.roles.split('[\\s,]')
		//println "" + roles + "--->" + session.authorities
		
		if (session.authorities.containsAll(roles))
			out << body()
		else {
			out << ""
		}
	}
	
	def ifNotGranted = {attrs, body ->
		def roles = attrs.roles.split('[\\s,]')
		def rolescol = new ArrayList()
		rolescol.addAll(roles)
		//println "" + roles + "--->" + session.authorities
		
		if (session.authorities.intersect(rolescol).size())
			out << ""
		else {
			out << body()
		}
	}
	
	def ifAnyGranted = {attrs, body ->
		def roles = attrs.roles.split('[\\s,]')
		def rolescol = new ArrayList()
		rolescol.addAll(roles)
		//println "" + rolescol + " intersect " + session.authorities + '----->' + session.authorities.intersect(rolescol)
		
		if (session.authorities?.intersect(rolescol)?.size()) {
			//println "outing"
			out << body()
		} else {
			//println "not outing"
			out << ""
		}
	}
	
}
