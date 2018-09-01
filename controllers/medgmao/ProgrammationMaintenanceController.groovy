package medgmao

import org.springframework.dao.DataIntegrityViolationException
//Generation template modifiée par rezz
class ProgrammationMaintenanceController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [programmationMaintenanceInstanceList: ProgrammationMaintenance.list(params), programmationMaintenanceInstanceTotal: ProgrammationMaintenance.count()]
    }

    def create() {
        [programmationMaintenanceInstance: new ProgrammationMaintenance(params)]
    }

    def save() {
        def programmationMaintenanceInstance = new ProgrammationMaintenance(params)
        if (!programmationMaintenanceInstance.save(flush: true)) {
            render(view: "create", model: [programmationMaintenanceInstance: programmationMaintenanceInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'programmationMaintenance.label', default: 'ProgrammationMaintenance'), programmationMaintenanceInstance.id])
        redirect(action: "show", id: programmationMaintenanceInstance.id)
    }

    def show(Long id) {
        def programmationMaintenanceInstance = ProgrammationMaintenance.get(id)
        if (!programmationMaintenanceInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'programmationMaintenance.label', default: 'ProgrammationMaintenance'), id])
            redirect(action: "list")
            return
        }

        [programmationMaintenanceInstance: programmationMaintenanceInstance]
    }

    def edit(Long id) {
        def programmationMaintenanceInstance = ProgrammationMaintenance.get(id)
        if (!programmationMaintenanceInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'programmationMaintenance.label', default: 'ProgrammationMaintenance'), id])
            redirect(action: "list")
            return
        }

        [programmationMaintenanceInstance: programmationMaintenanceInstance]
    }

    def update(Long id, Long version) {
        def programmationMaintenanceInstance = ProgrammationMaintenance.get(id)
        if (!programmationMaintenanceInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'programmationMaintenance.label', default: 'ProgrammationMaintenance'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (programmationMaintenanceInstance.version > version) {
                programmationMaintenanceInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'programmationMaintenance.label', default: 'ProgrammationMaintenance')] as Object[],
                          "Another user has updated this ProgrammationMaintenance while you were editing")
                render(view: "edit", model: [programmationMaintenanceInstance: programmationMaintenanceInstance])
                return
            }
        }

        programmationMaintenanceInstance.properties = params

        if (!programmationMaintenanceInstance.save(flush: true)) {
            render(view: "edit", model: [programmationMaintenanceInstance: programmationMaintenanceInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'programmationMaintenance.label', default: 'ProgrammationMaintenance'), programmationMaintenanceInstance.id])
        redirect(action: "show", id: programmationMaintenanceInstance.id)
    }

    def delete(Long id) {
        def programmationMaintenanceInstance = ProgrammationMaintenance.get(id)
        if (!programmationMaintenanceInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'programmationMaintenance.label', default: 'ProgrammationMaintenance'), id])
            redirect(action: "list")
            return
        }

        try {
            programmationMaintenanceInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'programmationMaintenance.label', default: 'ProgrammationMaintenance'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'programmationMaintenance.label', default: 'ProgrammationMaintenance'), id])
            redirect(action: "show", id: id)
        }
    }
}
