package mederp

import org.springframework.dao.DataIntegrityViolationException
//Generation template modifiée par rezz
class UniteController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [uniteInstanceList: Unite.list(params), uniteInstanceTotal: Unite.count()]
    }

    def create() {
        [uniteInstance: new Unite(params)]
    }

    def save() {
        def uniteInstance = new Unite(params)
        if (!uniteInstance.save(flush: true)) {
            render(view: "create", model: [uniteInstance: uniteInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'unite.label', default: 'Unite'), uniteInstance.id])
        redirect(action: "show", id: uniteInstance.id)
    }

    def show(Long id) {
        def uniteInstance = Unite.get(id)
        if (!uniteInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'unite.label', default: 'Unite'), id])
            redirect(action: "list")
            return
        }

        [uniteInstance: uniteInstance]
    }

    def edit(Long id) {
        def uniteInstance = Unite.get(id)
        if (!uniteInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'unite.label', default: 'Unite'), id])
            redirect(action: "list")
            return
        }

        [uniteInstance: uniteInstance]
    }

    def update(Long id, Long version) {
        def uniteInstance = Unite.get(id)
        if (!uniteInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'unite.label', default: 'Unite'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (uniteInstance.version > version) {
                uniteInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'unite.label', default: 'Unite')] as Object[],
                          "Another user has updated this Unite while you were editing")
                render(view: "edit", model: [uniteInstance: uniteInstance])
                return
            }
        }

        uniteInstance.properties = params

        if (!uniteInstance.save(flush: true)) {
            render(view: "edit", model: [uniteInstance: uniteInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'unite.label', default: 'Unite'), uniteInstance.id])
        redirect(action: "show", id: uniteInstance.id)
    }

    def delete(Long id) {
        def uniteInstance = Unite.get(id)
        if (!uniteInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'unite.label', default: 'Unite'), id])
            redirect(action: "list")
            return
        }

        try {
            uniteInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'unite.label', default: 'Unite'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'unite.label', default: 'Unite'), id])
            redirect(action: "show", id: id)
        }
    }
}
