package mederp

class Role {

	String authority

	static mapping = {
		cache true
		sort authority: "asc"
	}

	static constraints = {
		authority blank: false, unique: true
	}
	
	String toString() {
		authority
	}
	
	String[] controllers() {		
		//println "#######> controllers : for " + authority + " -> " + ProfileAccess.map.get(authority)

		mederp.ProfileAccess.map.get(authority)
	}
}
