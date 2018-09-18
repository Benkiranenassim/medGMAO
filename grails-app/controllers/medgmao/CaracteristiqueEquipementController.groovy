package medgmao

import org.springframework.dao.DataIntegrityViolationException
//Generation template modifiée par rezz
class CaracteristiqueEquipementController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [caracteristiqueEquipementInstanceList: CaracteristiqueEquipement.list(params), caracteristiqueEquipementInstanceTotal: CaracteristiqueEquipement.count()]
    }

    def create() {
        [caracteristiqueEquipementInstance: new CaracteristiqueEquipement(params)]
    }

    def save() {
        def caracteristiqueEquipementInstance = new CaracteristiqueEquipement(params)
        if (!caracteristiqueEquipementInstance.save(flush: true)) {
            render(view: "create", model: [caracteristiqueEquipementInstance: caracteristiqueEquipementInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'caracteristiqueEquipement.label', default: 'CaracteristiqueEquipement'), caracteristiqueEquipementInstance.id])
        redirect(action: "show", id: caracteristiqueEquipementInstance.id)
    }

    def show(Long id) {
        def caracteristiqueEquipementInstance = CaracteristiqueEquipement.get(id)
        if (!caracteristiqueEquipementInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'caracteristiqueEquipement.label', default: 'CaracteristiqueEquipement'), id])
            redirect(action: "list")
            return
        }

        [caracteristiqueEquipementInstance: caracteristiqueEquipementInstance]
    }

    def edit(Long id) {
        def caracteristiqueEquipementInstance = CaracteristiqueEquipement.get(id)
        if (!caracteristiqueEquipementInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'caracteristiqueEquipement.label', default: 'CaracteristiqueEquipement'), id])
            redirect(action: "list")
            return
        }

        [caracteristiqueEquipementInstance: caracteristiqueEquipementInstance]
    }

    def update(Long id, Long version) {
        def caracteristiqueEquipementInstance = CaracteristiqueEquipement.get(id)
        if (!caracteristiqueEquipementInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'caracteristiqueEquipement.label', default: 'CaracteristiqueEquipement'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (caracteristiqueEquipementInstance.version > version) {
                caracteristiqueEquipementInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'caracteristiqueEquipement.label', default: 'CaracteristiqueEquipement')] as Object[],
                          "Another user has updated this CaracteristiqueEquipement while you were editing")
                render(view: "edit", model: [caracteristiqueEquipementInstance: caracteristiqueEquipementInstance])
                return
            }
        }

        caracteristiqueEquipementInstance.properties = params

        if (!caracteristiqueEquipementInstance.save(flush: true)) {
            render(view: "edit", model: [caracteristiqueEquipementInstance: caracteristiqueEquipementInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'caracteristiqueEquipement.label', default: 'CaracteristiqueEquipement'), caracteristiqueEquipementInstance.id])
        redirect(action: "show", id: caracteristiqueEquipementInstance.id)
    }

    def delete(Long id) {
        def caracteristiqueEquipementInstance = CaracteristiqueEquipement.get(id)
        if (!caracteristiqueEquipementInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'caracteristiqueEquipement.label', default: 'CaracteristiqueEquipement'), id])
            redirect(action: "list")
            return
        }

        try {
            caracteristiqueEquipementInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'caracteristiqueEquipement.label', default: 'CaracteristiqueEquipement'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'caracteristiqueEquipement.label', default: 'CaracteristiqueEquipement'), id])
            redirect(action: "show", id: id)
        }
    }
}
