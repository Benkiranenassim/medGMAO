package medgmao

import org.springframework.dao.DataIntegrityViolationException
//Generation template modifiée par rezz
class GroupeEquipementsController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [groupeEquipementsInstanceList: GroupeEquipements.list(params), groupeEquipementsInstanceTotal: GroupeEquipements.count()]
    }

    def create() {
        [groupeEquipementsInstance: new GroupeEquipements(params)]
    }

    def save() {
        def groupeEquipementsInstance = new GroupeEquipements(params)
        if (!groupeEquipementsInstance.save(flush: true)) {
            render(view: "create", model: [groupeEquipementsInstance: groupeEquipementsInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'groupeEquipements.label', default: 'GroupeEquipements'), groupeEquipementsInstance.id])
        redirect(action: "show", id: groupeEquipementsInstance.id)
    }

    def show(Long id) {
        def groupeEquipementsInstance = GroupeEquipements.get(id)
        if (!groupeEquipementsInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'groupeEquipements.label', default: 'GroupeEquipements'), id])
            redirect(action: "list")
            return
        }

        [groupeEquipementsInstance: groupeEquipementsInstance]
    }

    def edit(Long id) {
        def groupeEquipementsInstance = GroupeEquipements.get(id)
        if (!groupeEquipementsInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'groupeEquipements.label', default: 'GroupeEquipements'), id])
            redirect(action: "list")
            return
        }

        [groupeEquipementsInstance: groupeEquipementsInstance]
    }

    def update(Long id, Long version) {
        def groupeEquipementsInstance = GroupeEquipements.get(id)
        if (!groupeEquipementsInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'groupeEquipements.label', default: 'GroupeEquipements'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (groupeEquipementsInstance.version > version) {
                groupeEquipementsInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'groupeEquipements.label', default: 'GroupeEquipements')] as Object[],
                          "Another user has updated this GroupeEquipements while you were editing")
                render(view: "edit", model: [groupeEquipementsInstance: groupeEquipementsInstance])
                return
            }
        }

        groupeEquipementsInstance.properties = params

        if (!groupeEquipementsInstance.save(flush: true)) {
            render(view: "edit", model: [groupeEquipementsInstance: groupeEquipementsInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'groupeEquipements.label', default: 'GroupeEquipements'), groupeEquipementsInstance.id])
        redirect(action: "show", id: groupeEquipementsInstance.id)
    }

    def delete(Long id) {
        def groupeEquipementsInstance = GroupeEquipements.get(id)
        if (!groupeEquipementsInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'groupeEquipements.label', default: 'GroupeEquipements'), id])
            redirect(action: "list")
            return
        }

        try {
            groupeEquipementsInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'groupeEquipements.label', default: 'GroupeEquipements'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'groupeEquipements.label', default: 'GroupeEquipements'), id])
            redirect(action: "show", id: id)
        }
    }
	
	def grouper() {
		GroupeEquipements grp = new GroupeEquipements(libelle:(params.groupname?:'nouveau groupe'))
		List equipements = []
		for (def p: params) {
			//println p.key
			if (p.key.startsWith('_equ_') && p.key.substring(5).isNumber()){
				Equipement equipement = Equipement.get(new Integer(p.key.substring(5)))
				grp.addToEquipements(equipement)
			}
		}
		grp.save(flush:true)
		redirect (action:"show", id:grp.id)
	}
	
	def ajouter() {
		GroupeEquipements grp = GroupeEquipements.get(params.groupeid)
		List equipements = []
		for (def p: params) {
			//println p.key
			if (p.key.startsWith('_equ_') && p.key.substring(5).isNumber()){
				Equipement equipement = Equipement.get(new Integer(p.key.substring(5)))
				grp.addToEquipements(equipement)
			}
		}
		grp.save(flush:true)
		redirect (action:"show", id:grp.id)		
	}
	
	// def ajouterGroupe(GroupeEquipements grp, Map params) {
		// List equipements = []
		// for (def p: params) {
			// //println p.key
			// if (p.key.startsWith('_equ_') && p.key.substring(5).isNumber()){
				// Equipement equipement = Equipement.get(new Integer(p.key.substring(5)))
				// grp.addToEquipements(equipement)
			// }
		// }
		// grp.save(flush:true)
		// redirect (action:"show", id:grp.id)
	// }
	
	def retirer(){
		GroupeEquipements grp = GroupeEquipements.get(params.groupeid)
		grp.removeFromEquipements(Equipement.get(params.equipementid))
		
		redirect (action:"show", id:grp.id)
	}
}
