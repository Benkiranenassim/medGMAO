package mederp

class Acces {
	String username
	String adress
	String path
	String query
	String requestid
	Double duration
	Date dateAcces
	Date heureAcces
	String problem
	
	static mapping = {
		id generator: "increment"
		version false
		sort heureAcces: "desc"
	}
	
	static constraints = {
		username blank: false
		requestid nullable: true
		path nullable: false
		query nullable: true
		adress nullable: false
		duration nullable: true
		dateAcces nullable: false
		heureAcces nullable: false
		problem nullable: true, maxSize: 2500
	}
}