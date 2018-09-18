package medgmao

import org.springframework.dao.DataIntegrityViolationException
//Generation template modifié par rezz
class FournisseurEquipementController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [fournisseurEquipementInstanceList: FournisseurEquipement.list(params), fournisseurEquipementInstanceTotal: FournisseurEquipement.count()]
    }

    def create() {
		FournisseurEquipement fournisseurEquipementInstance = new FournisseurEquipement(params)
		fournisseurEquipementInstance.fournisseur = true
        [fournisseurEquipementInstance: fournisseurEquipementInstance]
    }

    def save() {
        def fournisseurEquipementInstance = new FournisseurEquipement(params)
        if (!fournisseurEquipementInstance.save(flush: true)) {
            render(view: "create", model: [fournisseurEquipementInstance: fournisseurEquipementInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'fournisseurEquipement.label', default: 'FournisseurEquipement'), fournisseurEquipementInstance.id])
        redirect(action: "show", id: fournisseurEquipementInstance.id)
    }

    def show(Long id) {
        def fournisseurEquipementInstance = FournisseurEquipement.get(id)
        if (!fournisseurEquipementInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'fournisseurEquipement.label', default: 'FournisseurEquipement'), id])
            redirect(action: "list")
            return
        }

        [fournisseurEquipementInstance: fournisseurEquipementInstance]
    }

    def edit(Long id) {
        def fournisseurEquipementInstance = FournisseurEquipement.get(id)
        if (!fournisseurEquipementInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'fournisseurEquipement.label', default: 'FournisseurEquipement'), id])
            redirect(action: "list")
            return
        }

        [fournisseurEquipementInstance: fournisseurEquipementInstance]
    }

    def update(Long id, Long version) {
        def fournisseurEquipementInstance = FournisseurEquipement.get(id)
        if (!fournisseurEquipementInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'fournisseurEquipement.label', default: 'FournisseurEquipement'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (fournisseurEquipementInstance.version > version) {
                fournisseurEquipementInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'fournisseurEquipement.label', default: 'FournisseurEquipement')] as Object[],
                          "Another user has updated this FournisseurEquipement while you were editing")
                render(view: "edit", model: [fournisseurEquipementInstance: fournisseurEquipementInstance])
                return
            }
        }

        fournisseurEquipementInstance.properties = params

        if (!fournisseurEquipementInstance.save(flush: true)) {
            render(view: "edit", model: [fournisseurEquipementInstance: fournisseurEquipementInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'fournisseurEquipement.label', default: 'FournisseurEquipement'), fournisseurEquipementInstance.id])
        redirect(action: "show", id: fournisseurEquipementInstance.id)
    }

    def delete(Long id) {
        def fournisseurEquipementInstance = FournisseurEquipement.get(id)
        if (!fournisseurEquipementInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'fournisseurEquipement.label', default: 'FournisseurEquipement'), id])
            redirect(action: "list")
            return
        }

        try {
            fournisseurEquipementInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'fournisseurEquipement.label', default: 'FournisseurEquipement'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'fournisseurEquipement.label', default: 'FournisseurEquipement'), id])
            redirect(action: "show", id: id)
        }
    }
}
