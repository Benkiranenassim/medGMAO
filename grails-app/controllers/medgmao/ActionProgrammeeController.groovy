package medgmao

import org.springframework.dao.DataIntegrityViolationException
//Generation template modifiée par rezz
class ActionProgrammeeController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [actionProgrammeeInstanceList: ActionProgrammee.list(params), actionProgrammeeInstanceTotal: ActionProgrammee.count()]
    }

    def create() {
        [actionProgrammeeInstance: new ActionProgrammee(params)]
    }

    def save() {
        def actionProgrammeeInstance = new ActionProgrammee(params)
        if (!actionProgrammeeInstance.save(flush: true)) {
            render(view: "create", model: [actionProgrammeeInstance: actionProgrammeeInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'actionProgrammee.label', default: 'ActionProgrammee'), actionProgrammeeInstance.id])
        redirect(action: "show", id: actionProgrammeeInstance.id)
    }

    def show(Long id) {
        def actionProgrammeeInstance = ActionProgrammee.get(id)
        if (!actionProgrammeeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'actionProgrammee.label', default: 'ActionProgrammee'), id])
            redirect(action: "list")
            return
        }

        [actionProgrammeeInstance: actionProgrammeeInstance]
    }

    def edit(Long id) {
        def actionProgrammeeInstance = ActionProgrammee.get(id)
        if (!actionProgrammeeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'actionProgrammee.label', default: 'ActionProgrammee'), id])
            redirect(action: "list")
            return
        }

        [actionProgrammeeInstance: actionProgrammeeInstance]
    }

    def update(Long id, Long version) {
        def actionProgrammeeInstance = ActionProgrammee.get(id)
        if (!actionProgrammeeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'actionProgrammee.label', default: 'ActionProgrammee'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (actionProgrammeeInstance.version > version) {
                actionProgrammeeInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'actionProgrammee.label', default: 'ActionProgrammee')] as Object[],
                          "Another user has updated this ActionProgrammee while you were editing")
                render(view: "edit", model: [actionProgrammeeInstance: actionProgrammeeInstance])
                return
            }
        }

        actionProgrammeeInstance.properties = params

        if (!actionProgrammeeInstance.save(flush: true)) {
            render(view: "edit", model: [actionProgrammeeInstance: actionProgrammeeInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'actionProgrammee.label', default: 'ActionProgrammee'), actionProgrammeeInstance.id])
        redirect(action: "show", id: actionProgrammeeInstance.id)
    }

    def delete(Long id) {
        def actionProgrammeeInstance = ActionProgrammee.get(id)
        if (!actionProgrammeeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'actionProgrammee.label', default: 'ActionProgrammee'), id])
            redirect(action: "list")
            return
        }

        try {
            actionProgrammeeInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'actionProgrammee.label', default: 'ActionProgrammee'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'actionProgrammee.label', default: 'ActionProgrammee'), id])
            redirect(action: "show", id: id)
        }
    }
}
