package mederp

import org.springframework.dao.DataIntegrityViolationException

class AffectationController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [affectationInstanceList: Affectation.list(params), affectationInstanceTotal: Affectation.count()]
    }

    def create() {
        [affectationInstance: new Affectation(params)]
    }

    def save() {
        def affectationInstance = new Affectation(params)
        if (!affectationInstance.save(flush: true)) {
            render(view: "create", model: [affectationInstance: affectationInstance])
            return
        } else if (affectationInstance.user.affectations.size()==1) {
			affectationInstance.parDefaut=true
			affectationInstance.save(flush: true)
		}
		println affectationInstance.user.affectations.size()
        flash.message = message(code: 'default.created.message', args: [message(code: 'affectation.label', default: 'Affectation'), affectationInstance.id])
        redirect(action: "show", id: affectationInstance.id)
    }

    def show(Long id) {
        def affectationInstance = Affectation.get(id)
        if (!affectationInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'affectation.label', default: 'Affectation'), id])
            redirect(action: "list")
            return
        }

        [affectationInstance: affectationInstance]
    }

    def edit(Long id) {
        def affectationInstance = Affectation.get(id)
        if (!affectationInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'affectation.label', default: 'Affectation'), id])
            redirect(action: "list")
            return
        }

        [affectationInstance: affectationInstance]
    }

    def update(Long id, Long version) {
        def affectationInstance = Affectation.get(id)
        if (!affectationInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'affectation.label', default: 'Affectation'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (affectationInstance.version > version) {
                affectationInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'affectation.label', default: 'Affectation')] as Object[],
                          "Another user has updated this Affectation while you were editing")
                render(view: "edit", model: [affectationInstance: affectationInstance])
                return
            }
        }

        affectationInstance.properties = params

        if (!affectationInstance.save(flush: true)) {
            render(view: "edit", model: [affectationInstance: affectationInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'affectation.label', default: 'Affectation'), affectationInstance.id])
        redirect(action: "show", id: affectationInstance.id)
    }

    def delete(Long id) {
        def affectationInstance = Affectation.get(id)
        if (!affectationInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'affectation.label', default: 'Affectation'), id])
            redirect(action: "list")
            return
        }

        try {
            affectationInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'affectation.label', default: 'Affectation'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'affectation.label', default: 'Affectation'), id])
            redirect(action: "show", id: id)
        }
    }
}
