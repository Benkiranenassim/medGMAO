package mederp

import org.springframework.dao.DataIntegrityViolationException

class AuthentificationController {
	def securiteService
    static allowedMethods = [authenticate: "POST", disconnect: "GET"]

    def auth() {
		
		if (params.username) {
			if (securiteService.authenticate(params.username, params.password)) {
				redirect(controller:"index")
				return
			}
		}
		/**/
    }

    def disconnect() {
		//println "user : "+ session.user + "  disconnecting"
		def username = session.user.username
		session.invalidate()
		[username:username]
    }
}
