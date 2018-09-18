package medgmao

import org.springframework.dao.DataIntegrityViolationException
//Generation template modifiée par rezz
class TypeCaracteristiqueController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [typeCaracteristiqueInstanceList: TypeCaracteristique.list(params), typeCaracteristiqueInstanceTotal: TypeCaracteristique.count()]
    }

    def create() {
        [typeCaracteristiqueInstance: new TypeCaracteristique(params)]
    }

    def save() {
        def typeCaracteristiqueInstance = new TypeCaracteristique(params)
        if (!typeCaracteristiqueInstance.save(flush: true)) {
            render(view: "create", model: [typeCaracteristiqueInstance: typeCaracteristiqueInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'typeCaracteristique.label', default: 'TypeCaracteristique'), typeCaracteristiqueInstance.id])
        redirect(action: "show", id: typeCaracteristiqueInstance.id)
    }

    def show(Long id) {
        def typeCaracteristiqueInstance = TypeCaracteristique.get(id)
        if (!typeCaracteristiqueInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'typeCaracteristique.label', default: 'TypeCaracteristique'), id])
            redirect(action: "list")
            return
        }

        [typeCaracteristiqueInstance: typeCaracteristiqueInstance]
    }

    def edit(Long id) {
        def typeCaracteristiqueInstance = TypeCaracteristique.get(id)
        if (!typeCaracteristiqueInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'typeCaracteristique.label', default: 'TypeCaracteristique'), id])
            redirect(action: "list")
            return
        }

        [typeCaracteristiqueInstance: typeCaracteristiqueInstance]
    }

    def update(Long id, Long version) {
        def typeCaracteristiqueInstance = TypeCaracteristique.get(id)
        if (!typeCaracteristiqueInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'typeCaracteristique.label', default: 'TypeCaracteristique'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (typeCaracteristiqueInstance.version > version) {
                typeCaracteristiqueInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'typeCaracteristique.label', default: 'TypeCaracteristique')] as Object[],
                          "Another user has updated this TypeCaracteristique while you were editing")
                render(view: "edit", model: [typeCaracteristiqueInstance: typeCaracteristiqueInstance])
                return
            }
        }

        typeCaracteristiqueInstance.properties = params

        if (!typeCaracteristiqueInstance.save(flush: true)) {
            render(view: "edit", model: [typeCaracteristiqueInstance: typeCaracteristiqueInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'typeCaracteristique.label', default: 'TypeCaracteristique'), typeCaracteristiqueInstance.id])
        redirect(action: "show", id: typeCaracteristiqueInstance.id)
    }

    def delete(Long id) {
        def typeCaracteristiqueInstance = TypeCaracteristique.get(id)
        if (!typeCaracteristiqueInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'typeCaracteristique.label', default: 'TypeCaracteristique'), id])
            redirect(action: "list")
            return
        }

        try {
            typeCaracteristiqueInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'typeCaracteristique.label', default: 'TypeCaracteristique'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'typeCaracteristique.label', default: 'TypeCaracteristique'), id])
            redirect(action: "show", id: id)
        }
    }
}
