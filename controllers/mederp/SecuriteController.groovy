package mederp

import groovy.sql.Sql

class SecuriteController {
	//def springSecurityService 
	def securiteService
	def dataSource
	def licenceService

    def index = { 
		redirect(action: "changepw", params: params)
	}
	
	def changepw = {
		if (LicenceInfo.getTypeLicence()=="standard") {
			def medecinRole = mederp.Role.findByAuthority('ROLE_MEDECIN')
			def med = mederp.UserRole.findByRole(medecinRole).user
			
			def assistantRole = mederp.Role.findByAuthority('ROLE_ASSISTANT')
			def ast = mederp.UserRole.findByRole(assistantRole).user
			[medecinlogin:med.username,assistantlogin:ast.username]
		} else {
			[user:session.user]
		}
	}
	
	def changepwmed = {
				
		if (!params.medecinpw || !params.medecinpw1 || !params.medecinpw2){
			flash.message = message(code:'passwords.remplir.medecin')
			redirect (action:"changepw")
			return
		}
		
		if (params.medecinpw1 != params.medecinpw2){
			flash.message = message(code:'passwords.different.medecin')
			redirect (action:"changepw")
			return
		}
		
		def medecinRole = mederp.Role.findByAuthority('ROLE_MEDECIN')
		def user = mederp.UserRole.findByRole(medecinRole).user
		
		if (securiteService.encodePassword(params.medecinpw)==user.password) {
			user.password=params.medecinpw1
			if (LicenceInfo.licenced && params.medecinlg) {
				user.username=params.medecinlg
			}			
			user.save(flush: true)
			flash.message = message(code:'password.changed.medecin')
			redirect (action:"changepw")
		} else {
			flash.message = message(code:'password.bad')
			redirect (action:"changepw")
		}
	}
	
	def changepwusr = {
				
		if (!params.usrpass || !params.newpass1 || !params.newpass2){
			flash.message = message(code:'passwords.remplir.user')
			redirect (action:"changepw")
			return
		}
		
		if (params.newpass1 != params.newpass2){
			flash.message = message(code:'passwords.different.medecin')
			redirect (action:"changepw")
			return
		}
		
		def user = User.get(session.user.id)
		
		if (securiteService.encodePassword(params.usrpass)==user.password) {
			user.password=params.newpass1.encodeAsSHA256()
			user.save(flush: true)
			
			flash.message = message(code:'password.changed.user')
			redirect (action:"changepw")
		} else {
			flash.message = message(code:'password.bad.user')
			redirect (action:"changepw")
		}
	}
	
	def reinitPasswd(Long userid) {
		if (session.authorities.contains('ROLE_ADMIN')) {
			def user = User.get(userid)
			
			user.password=user.username.encodeAsSHA256()
			user.save(flush: true)
			
			flash.message = message(code:'password.reinitialise.user')
		}
		redirect (controller:"user", action:"show", id:userid)
	}
	
	def backup = {
		def sql = new Sql(dataSource)
		sql.execute "BACKUP TO '"+ grailsApplication.parentContext.getResource("backup").file.toString()+"/backup.zip' "
	}
	
	def changepwass = {
				
		if (!params.medecinpwa || !params.assistantpw1 || !params.assistantpw2){
			flash.message = message(code:'passwords.remplir.assistant')
			redirect (action:"changepw")
			return
		}
		
		if (params.assistantpw1 != params.assistantpw2){
			flash.message = message(code:'passwords.different.assistant')
			redirect (action:"changepw")
			return
		}
		
		def medecinRole = mederp.Role.findByAuthority('ROLE_MEDECIN')
		def med = mederp.UserRole.findByRole(medecinRole).user
		
		def assistantRole = mederp.Role.findByAuthority('ROLE_ASSISTANT')
		def ast = mederp.UserRole.findByRole(assistantRole).user
		
		if (securiteService.encodePassword(params.medecinpwa)==med.password) {
			ast.password=params.assistantpw1
			if (LicenceInfo.licenced && params.assistantlg) {
				ast.username=params.assistantlg
			}
			ast.save(flush: true)
			flash.message = message(code:'password.changed.assistant')
			redirect (action:"changepw")
		} else {
			flash.message = message(code:'password.bad')
			redirect (action:"changepw")
		}
	}
	
	
}