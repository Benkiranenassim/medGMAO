package medgmao

import mederp.*

class ParametrageController {
	def index() {
		def lignes=""""""
		
		println TypeLocalisation.list()
		
		for (def ligne: lignes.split("\n")) {
			TypeLocalisation bloc = TypeLocalisation.get(7)
			def token = ligne.split(";")
			if (token.length!=3)
				println "#############################################################"
			else {
				Localisation lparent = Localisation.findByLibelleCourt(token[0])
				if (lparent) {
					Localisation loc = new Localisation(parent: lparent, libelleCourt:token[1], libelle:token[2], typeLocalisation: bloc)
					loc.save(flush:true)
				} else {
					println "    =========>   " + ligne
				}
			}
		}
		
		render ('wiw');
	}
}
