package medgmao

import org.springframework.dao.DataIntegrityViolationException
//Generation template modifiée par rezz
class ModelMaintenanceController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [modelMaintenanceInstanceList: ModelMaintenance.list(params), modelMaintenanceInstanceTotal: ModelMaintenance.count()]
    }

    def create() {
        [modelMaintenanceInstance: new ModelMaintenance(params)]
    }

    def save() {
        def modelMaintenanceInstance = new ModelMaintenance(params)
        if (!modelMaintenanceInstance.save(flush: true)) {
            render(view: "create", model: [modelMaintenanceInstance: modelMaintenanceInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'modelMaintenance.label', default: 'ModelMaintenance'), modelMaintenanceInstance.id])
        redirect(action: "show", id: modelMaintenanceInstance.id)
    }

    def show(Long id) {
        def modelMaintenanceInstance = ModelMaintenance.get(id)
        if (!modelMaintenanceInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'modelMaintenance.label', default: 'ModelMaintenance'), id])
            redirect(action: "list")
            return
        }

        [modelMaintenanceInstance: modelMaintenanceInstance]
    }

    def edit(Long id) {
        def modelMaintenanceInstance = ModelMaintenance.get(id)
        if (!modelMaintenanceInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'modelMaintenance.label', default: 'ModelMaintenance'), id])
            redirect(action: "list")
            return
        }

        [modelMaintenanceInstance: modelMaintenanceInstance]
    }

    def update(Long id, Long version) {
        def modelMaintenanceInstance = ModelMaintenance.get(id)
        if (!modelMaintenanceInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'modelMaintenance.label', default: 'ModelMaintenance'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (modelMaintenanceInstance.version > version) {
                modelMaintenanceInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'modelMaintenance.label', default: 'ModelMaintenance')] as Object[],
                          "Another user has updated this ModelMaintenance while you were editing")
                render(view: "edit", model: [modelMaintenanceInstance: modelMaintenanceInstance])
                return
            }
        }

        modelMaintenanceInstance.properties = params

        if (!modelMaintenanceInstance.save(flush: true)) {
            render(view: "edit", model: [modelMaintenanceInstance: modelMaintenanceInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'modelMaintenance.label', default: 'ModelMaintenance'), modelMaintenanceInstance.id])
        redirect(action: "show", id: modelMaintenanceInstance.id)
    }

    def delete(Long id) {
        def modelMaintenanceInstance = ModelMaintenance.get(id)
        if (!modelMaintenanceInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'modelMaintenance.label', default: 'ModelMaintenance'), id])
            redirect(action: "list")
            return
        }

        try {
            modelMaintenanceInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'modelMaintenance.label', default: 'ModelMaintenance'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'modelMaintenance.label', default: 'ModelMaintenance'), id])
            redirect(action: "show", id: id)
        }
    }
	
	def listeEquipements(Long id) {
		def modelMaintenanceInstance = ModelMaintenance.get(id)
		
		List equipementsProgrammes = new ArrayList()
		List equipementsNonProgrammes = new ArrayList()
		
		for (def eq : modelMaintenanceInstance.typeEquipement.allEquipements()) {
			if (eq.programmationsMaintenance.size()>0) {
				equipementsProgrammes.add(eq)
			} else {
				equipementsNonProgrammes.add(eq)
			}
		}
		[modelMaintenanceInstance:modelMaintenanceInstance, equipementsProgrammes:equipementsProgrammes, equipementsNonProgrammes:equipementsNonProgrammes]
	}
	
	def programmer() {
		def modelMaintenanceInstance = ModelMaintenance.get(params.modelid)
		List equipements = []
		for (def p: params) {
			//println p.key
			if (p.key.startsWith('_equ_') && p.key.substring(5).isNumber()){
				Equipement equipement = Equipement.get(new Integer(p.key.substring(5)))
				equipements.add(equipement)
			}
		}
		
		for (def eq: equipements){
			ProgrammationMaintenance programmation = new ProgrammationMaintenance(equipement:eq, model:modelMaintenanceInstance, interval: params.interval, responsable: mederp.User.get(params.responsableid))
			programmation.save(flush:true)
			println "##########" +programmation.errors
			Intervention intervention = new Intervention(dateProgrammee: params.dateDebut, ordonneePar: session.user, executeePar: mederp.User.get(params.responsableid), texte: modelMaintenanceInstance, equipement:eq, localisation: eq.localisation, type:TypeIntervention.get(1))
			intervention.save(flush:true)
		}
		
		redirect (action:"listeEquipements", id:modelMaintenanceInstance.id)
	}
}
