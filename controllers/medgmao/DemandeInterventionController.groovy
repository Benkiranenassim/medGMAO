package medgmao

import org.springframework.dao.DataIntegrityViolationException
//Generation template modifiée par rezz
class DemandeInterventionController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [demandeInterventionInstanceList: DemandeIntervention.list(params), demandeInterventionInstanceTotal: DemandeIntervention.count()]
    }

    def create() {
        [demandeInterventionInstance: new DemandeIntervention(params)]
    }

    def save() {
        def demandeInterventionInstance = new DemandeIntervention(params)
        if (!demandeInterventionInstance.save(flush: true)) {
            render(view: "create", model: [demandeInterventionInstance: demandeInterventionInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'demandeIntervention.label', default: 'DemandeIntervention'), demandeInterventionInstance.id])
        redirect(action: "show", id: demandeInterventionInstance.id)
    }

    def show(Long id) {
        def demandeInterventionInstance = DemandeIntervention.get(id)
        if (!demandeInterventionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'demandeIntervention.label', default: 'DemandeIntervention'), id])
            redirect(action: "list")
            return
        }

        [demandeInterventionInstance: demandeInterventionInstance]
    }

    def edit(Long id) {
        def demandeInterventionInstance = DemandeIntervention.get(id)
        if (!demandeInterventionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'demandeIntervention.label', default: 'DemandeIntervention'), id])
            redirect(action: "list")
            return
        }

        [demandeInterventionInstance: demandeInterventionInstance]
    }

    def update(Long id, Long version) {
        def demandeInterventionInstance = DemandeIntervention.get(id)
        if (!demandeInterventionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'demandeIntervention.label', default: 'DemandeIntervention'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (demandeInterventionInstance.version > version) {
                demandeInterventionInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'demandeIntervention.label', default: 'DemandeIntervention')] as Object[],
                          "Another user has updated this DemandeIntervention while you were editing")
                render(view: "edit", model: [demandeInterventionInstance: demandeInterventionInstance])
                return
            }
        }

        demandeInterventionInstance.properties = params

        if (!demandeInterventionInstance.save(flush: true)) {
            render(view: "edit", model: [demandeInterventionInstance: demandeInterventionInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'demandeIntervention.label', default: 'DemandeIntervention'), demandeInterventionInstance.id])
        redirect(action: "show", id: demandeInterventionInstance.id)
    }

    def delete(Long id) {
        def demandeInterventionInstance = DemandeIntervention.get(id)
        if (!demandeInterventionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'demandeIntervention.label', default: 'DemandeIntervention'), id])
            redirect(action: "list")
            return
        }

        try {
            demandeInterventionInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'demandeIntervention.label', default: 'DemandeIntervention'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'demandeIntervention.label', default: 'DemandeIntervention'), id])
            redirect(action: "show", id: id)
        }
    }

    def infoFournisseur(Long id) {
        def demandeInterventionInstance = DemandeIntervention.get(id)
        if (!demandeInterventionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'demandeIntervention.label', default: 'DemandeIntervention'), id])
            redirect(action: "list")
            return
        }

        [demandeInterventionInstance: demandeInterventionInstance]
    }
}
