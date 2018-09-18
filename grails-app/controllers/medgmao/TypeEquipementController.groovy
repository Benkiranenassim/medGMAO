package medgmao

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.JSON

//Generation template modifiée par rezz
class TypeEquipementController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        [typeEquipementInstanceList: TypeEquipement.findAllByParentIsNullAndCorpsIsNull()]
    }

    def create() {
		TypeEquipement typeEquipementInstance = new TypeEquipement(params)
		typeEquipementInstance.prefix = typeEquipementInstance.parent?.prefix
        [typeEquipementInstance: typeEquipementInstance]
    }

    def save() {
        def typeEquipementInstance = new TypeEquipement(params)
        if (!typeEquipementInstance.save(flush: true)) {
            render(view: "create", model: [typeEquipementInstance: typeEquipementInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'typeEquipement.label', default: 'TypeEquipement'), typeEquipementInstance.id])
        redirect(action: "show", id: typeEquipementInstance.id)
    }

    def show(Long id) {
        def typeEquipementInstance = TypeEquipement.get(id)
        if (!typeEquipementInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'typeEquipement.label', default: 'TypeEquipement'), id])
            redirect(action: "list")
            return
        }
		
		JSON.use('deep')
		//println (new TypeEquipementTree(typeEquipementInstance,10) as JSON)

        [typeEquipementInstance: typeEquipementInstance, childrenJson: (new TypeEquipementTree(typeEquipementInstance,10).children as JSON)]
    }

    def edit(Long id) {
        def typeEquipementInstance = TypeEquipement.get(id)
        if (!typeEquipementInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'typeEquipement.label', default: 'TypeEquipement'), id])
            redirect(action: "list")
            return
        }

        [typeEquipementInstance: typeEquipementInstance]
    }

    def update(Long id, Long version) {
        def typeEquipementInstance = TypeEquipement.get(id)
        if (!typeEquipementInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'typeEquipement.label', default: 'TypeEquipement'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (typeEquipementInstance.version > version) {
                typeEquipementInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'typeEquipement.label', default: 'TypeEquipement')] as Object[],
                          "Another user has updated this TypeEquipement while you were editing")
                render(view: "edit", model: [typeEquipementInstance: typeEquipementInstance])
                return
            }
        }

        typeEquipementInstance.properties = params

        if (!typeEquipementInstance.save(flush: true)) {
            render(view: "edit", model: [typeEquipementInstance: typeEquipementInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'typeEquipement.label', default: 'TypeEquipement'), typeEquipementInstance.id])
        redirect(action: "show", id: typeEquipementInstance.id)
    }

    def delete(Long id) {
        def typeEquipementInstance = TypeEquipement.get(id)
		def parentid = typeEquipementInstance.parent?.id
        if (!typeEquipementInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'typeEquipement.label', default: 'TypeEquipement'), id])
            redirect(action: "list")
            return
        }

        try {
            typeEquipementInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'typeEquipement.label', default: 'TypeEquipement'), id])
            if (parentid) {
				redirect(action:"show", id: parentid)
			} else {
				redirect(action: "list")
			}
			
			
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'typeEquipement.label', default: 'TypeEquipement'), id])
            redirect(action: "show", id: id)
        }
    }
	
	
	def allAsJson(){
		def testData = """[{
			id: 1,
			text: 'Languages',
			children: [{
				id: 11,
				text: 'Java'
			},{
				id: 12,
				text: 'C++'
			}]
		}]"""
		
		render (testData)
	}

}
