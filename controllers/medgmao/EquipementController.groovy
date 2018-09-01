package medgmao

import org.springframework.dao.DataIntegrityViolationException
import org.springframework.web.multipart.MultipartHttpServletRequest
import mederp.Localisation
//Generation template modifiée par rezz
class EquipementController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        HashSet equipementInstanceSet = new HashSet()
		if (params.code && params.code.length() >2) {
			if (params.code.startsWith("ECD-")) {
				def equipements = Equipement.findAllByCode(params.code.substring(4))
				if (equipements.size()>0) {
					equipementInstanceSet.addAll(equipements)
				}
			} else if (params.code.startsWith("EID-")) {
				def equipement = Equipement.findByIdentifiant(params.code.substring(4))
				if (equipement) {
					equipementInstanceSet.add(equipement)
				}
			} else if (params.code.length()>2) {
				def equipements = Equipement.findAllByCodeIlike(params.code+'%')
				if (equipements.size()>0) {
					equipementInstanceSet.addAll(equipements)
				}
				def equipement = Equipement.findByIdentifiant(params.code)
				if (equipement) {
					equipementInstanceSet.add(equipement)
				}
			}
			
			if (equipementInstanceSet.size()==0) {
				flash.message = "Aucun équipement ne correspond à l'identifiant saisi"
			} else if (equipementInstanceSet.size()==1) {
				for (def eq: equipementInstanceSet) {
					redirect(action:"show", id: eq.id)
					return
				}
			} else {
				flash.message = ""+equipementInstanceSet.size()+" Résultats trouvés"
			}
			
		} else if (params.libelle && params.libelle.length()>2) {
			equipementInstanceSet.addAll(Equipement.findAllByLibelleIlike('%'+params.libelle+'%'))
			equipementInstanceSet.addAll(Equipement.findAllByMarqueIlike('%'+params.libelle+'%'))
			equipementInstanceSet.addAll(Equipement.findAllByModelIlike('%'+params.libelle+'%'))
			
			/** Par type equipement attention aux perfs*/
			def tes=TypeEquipement.findAllByLibelleIlike('%'+params.libelle+'%')
			//println tes
			for (def te : tes){
				equipementInstanceSet.addAll(Equipement.findAllByTypeEquipement(te))
			}
			
			flash.message = ""+equipementInstanceSet.size()+" Résultats trouvés"
		} else {
			if (params.libelle || params.code)
				flash.message = "Le texte recherché doit contenir au moin 3 caractères"
		}
		
        [equipementInstanceSet: equipementInstanceSet]
    }

    def create() {
		Equipement equipementInstance = new Equipement(params)
				
		Equipement model = Equipement.get(params.modelid)
		
		if (! equipementInstance.typeEquipement?.id && ! model && !equipementInstance.corps?.id){
			flash.message = "Veuillez d'abord choisir un type d'équipement"
			redirect(action: "list", controller:"typeEquipement")
            return 
		}
		
		if (params.modelid && model) {
			equipementInstance.typeEquipement = model.typeEquipement
			equipementInstance.libelle = model.libelle
			equipementInstance.code = model.typeEquipement.prefix
			equipementInstance.marque = model.marque
			equipementInstance.model = model.model
			equipementInstance.localisation = model.localisation
			equipementInstance.fournisseur = model.fournisseur
			equipementInstance.parent = model.parent
			equipementInstance.parent = model.corps
			equipementInstance.dateFinGarantie = model.dateFinGarantie
			equipementInstance.caracteristiques = model.caracteristiques
			equipementInstance.caracteristiquesSupplementaires = model.caracteristiquesSupplementaires
						
		} else {
			equipementInstance.libelle = equipementInstance.typeEquipement?.libelle
			//FIXME i need to be parametrized
			equipementInstance.marque = "Sans"
		}
		def numeroOrdreMax
		if (equipementInstance.parent) {
			equipementInstance.localisation = equipementInstance.parent.localisation
			numeroOrdreMax = Equipement.executeQuery("select max(e.numeroOrdre) from Equipement e where parent = :parent",['parent':equipementInstance.parent])[0]
		} else if (equipementInstance.corps) {
			equipementInstance.localisation = equipementInstance.corps.localisation
			numeroOrdreMax = Equipement.executeQuery("select max(e.numeroOrdre) from Equipement e where corps = :corps",['corps':equipementInstance.corps])[0]
		} else {
			numeroOrdreMax = Equipement.executeQuery("select max(e.numeroOrdre) from Equipement e where typeEquipement = :te",['te':equipementInstance.typeEquipement])[0]
		}
		equipementInstance.numeroOrdre = (numeroOrdreMax?:0)+1
        [equipementInstance: equipementInstance]
    }

    def save() {
        def equipementInstance = new Equipement(params)
		def idProvided = equipementInstance.identifiant
		if (!idProvided) {
			equipementInstance.identifiant = 10000000 + (int)(Math.random()*90000000)
		}
		
		if (equipementInstance.parent) {
			def numeroOrdreMax = Equipement.executeQuery("select max(e.numeroOrdre) from Equipement e where parent = :parent",['parent':equipementInstance.parent])[0]
			equipementInstance.code=equipementInstance.parent.code+"-"+numeroOrdreMax
		} else {
			equipementInstance.code=((equipementInstance.localisation.libelleCourt+'-')?:'')+equipementInstance.typeEquipement.prefix+"-"+equipementInstance.numeroOrdre
		}
		
        if (!equipementInstance.save(flush: true)) {
            render(view: "create", model: [equipementInstance: equipementInstance])
            return
        }
		
		if (!idProvided) {
			equipementInstance.identifiant = equipementInstance.id
			equipementInstance.save(flush: true)
		}
        flash.message = message(code: 'default.created.message', args: [message(code: 'equipement.label', default: 'Equipement'), equipementInstance.id])
        redirect(action: "show", id: equipementInstance.id)
    }

    def show(Long id) {
        def equipementInstance = Equipement.get(id)
        //equipementInstance.getProchaineIntervention()
        if (!equipementInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'equipement.label', default: 'Equipement'), id])
            redirect(action: "list")
            return
        }

        [equipementInstance: equipementInstance]
    }

    def edit(Long id) {
        def equipementInstance = Equipement.get(id)
        if (!equipementInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'equipement.label', default: 'Equipement'), id])
            redirect(action: "list")
            return
        }

        [equipementInstance: equipementInstance]
    }

    def update(Long id, Long version) {
        def equipementInstance = Equipement.get(id)
        if (!equipementInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'equipement.label', default: 'Equipement'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (equipementInstance.version > version) {
                equipementInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'equipement.label', default: 'Equipement')] as Object[],
                          "Another user has updated this Equipement while you were editing")
                render(view: "edit", model: [equipementInstance: equipementInstance])
                return
            }
        }

        equipementInstance.properties = params
		
		if (! equipementInstance.identifiant) {
			equipementInstance.identifiant = equipementInstance.id
		}

        if (!equipementInstance.save(flush: true)) {
            render(view: "edit", model: [equipementInstance: equipementInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'equipement.label', default: 'Equipement'), equipementInstance.id])
        redirect(action: "show", id: equipementInstance.id)
    }

    def delete(Long id) {
        def equipementInstance = Equipement.get(id)
		def parentid = equipementInstance.parent?.id
		def corpsid = equipementInstance.corps?.id
        if (!equipementInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'equipement.label', default: 'Equipement'), id])
            redirect(action: "list")
            return
        }

        try {
            equipementInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'equipement.label', default: 'Equipement'), id])
            if (parentid) {
				redirect(action: "show", id: parentid)
			} else if (corpsis){
				redirect(action: "show", id: corpsid)
			} else {
				redirect(action: "list")
			}
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'equipement.label', default: 'Equipement'), id])
            redirect(action: "show", id: id)
        }
    }
	
	def associerParent() {
		Equipement parent
		Equipement equipement = Equipement.get(params.equipementid)
		
		if (params.parentcode && (parent = Equipement.findByCode(params.parentcode))) {
			def numeroOrdreMax = Equipement.executeQuery("select max(e.numeroOrdre) from Equipement e where parent = :parent",['parent':parent])[0]
			equipement.parent = parent
			equipement.code = parent.code + "-" + ((numeroOrdreMax?:0) + 1)
			equipement.corps = null
			equipement.save(flush:true)
			redirect(action:'show', id: equipement.id)
		} else {
			flash.message="impossible d'associer à un équipement avec l'identifiant donné"
			redirect(action:'show', id: equipement.id)
		}
		
	}
	
	def associerCorps() {
		Equipement corps
		Equipement equipement = Equipement.get(params.equipementid)
		
		if (params.corpscode && (corps = Equipement.findByCode(params.corpscode))) {
			def numeroOrdreMax = Equipement.executeQuery("select max(e.numeroOrdre) from Equipement e where corps = :corps",['corps':corps])[0]
			equipement.corps = corps
			equipement.code = corps.code + "-" + ((numeroOrdreMax?:0) + 1)
			equipement.parent = null
			equipement.save(flush:true)
			redirect(action:'show', id: equipement.id)
		} else {
			flash.message="impossible d'associer à un équipement avec l'identifiant donné"
			redirect(action:'show', id: equipement.id)
		}
		
	}
	
	def desassocier() {
		Equipement equipement = Equipement.get(params.equipementid)
		
		equipement.corps = null
		equipement.parent = null
		def numeroOrdreMax = Equipement.executeQuery("select max(e.numeroOrdre) from Equipement e where typeEquipement = :te",['te':equipement.typeEquipement])[0]
		equipement.code=((equipement.localisation.libelleCourt+'-')?:'')+equipement.typeEquipement.prefix+"-"+((numeroOrdreMax?:0)+1)
		
		equipement.save(flush:true)
		redirect(action:'show', id: equipement.id)
	}
	
    def inventaire() {
		ArrayList equipements = new ArrayList()
		Localisation localisation = null
		TypeEquipement typeEquipement= null
		GroupeEquipements groupeEquipements = null
		CaracteristiqueEquipement caracteristiqueEquipement = null
        if (params.localisationid) {
			localisation = Localisation.get(params.localisationid)
			equipements.addAll(Equipement.executeQuery("select e from Equipement as e where e.localisation in (:localisations)", [localisations: localisation.localisationAndAllSousLocalisations()]))
		} else if (params.typeequipementid){
			typeEquipement = TypeEquipement.get(params.typeequipementid)
			equipements.addAll(typeEquipement.allEquipements())
		} else if (params.groupeid){
			groupeEquipements = GroupeEquipements.get(params.groupeid)
			equipements.addAll(groupeEquipements.equipements)
		} else if (params.caracteristiqueid){
			caracteristiqueEquipement = CaracteristiqueEquipement.get(params.caracteristiqueid)
			equipements.addAll(caracteristiqueEquipement.equipements)
		} else {
			render ("localisation, type, caractéristique ou groupe équipement requis")
			return
		}
        
        [equipements: equipements, localisation: localisation, typeEquipement: typeEquipement, groupeEquipements: groupeEquipements, caracteristiqueEquipement: caracteristiqueEquipement]
    }
	
	def uploadFile() {
		if (request instanceof MultipartHttpServletRequest) {
			def file = request.getFile('fichier')
			def docPath = grailsApplication.parentContext.getResource("loadFiles").file.toString()
			if (!file?.empty && file.size < 10*1024*1024) {
				def extention = ""
				def indx = file.originalFilename.lastIndexOf('.')
				if (indx>0) {
					extention = file.originalFilename.substring(indx)
				}
				
				if( extention.toLowerCase().equals(".csv")) {
					def folder = new File("${docPath}")
					file.transferTo(new File("${docPath}/${file.originalFilename.substring(0,indx)}${new Date().format('yy_MM_dd_HH_mm')}.csv"))				
				} else {
					flash.message = "Seuls les fichiers CSV sont acceptés !"
				}
				
				
			} else {
				flash.message = "Fichier vide ou trop grand (>10M)!"
			}
		}
		
		def dossierChargement = grailsApplication.parentContext.getResource("loadFiles").file
		
		def loadFiles = dossierChargement.listFiles().sort { a,b -> b.lastModified() <=> a.lastModified() } 
		[loadFiles: loadFiles]
	}
	
	def deleteLoadFile() {
		File file = new File(grailsApplication.parentContext.getResource("loadFiles").file.toString()+"/"+params.filename)
		if (file.exists()) {
			file.delete()
		}
		
		flash.message = "Fichier " + params.filename + " Supprimé ! "
		redirect (action:"uploadFile")
	}
	
	def chargement() {
		File file = grailsApplication.parentContext.getResource("loadFiles/${params.filename}").file
		def ligneProb = []
		def ligneWarn = []
		def equipementsCharges = []
		def i=0
		file.eachLine { line ->
			def st = line.split(";")
			//println "###############  " + st.length + " -> " + st
			i++
			Equipement eq
			if (st.length<15) {
				ligneProb.add(line + ";nombre de colonnes doit être de 15 (ou superieur si info supplémentaires non prises en charge)")
				return null
			}
			
			String codeLocal=st[0]
			if (codeLocal.length()==2 && !codeLocal.startsWith('I'))
				codeLocal=codeLocal.substring(0,1)+"00"+codeLocal.substring(1)
			if (codeLocal.length()==3 && !codeLocal.startsWith('I'))
				codeLocal=codeLocal.substring(0,1)+"0"+codeLocal.substring(1)
			
			Localisation local = Localisation.findByLibelleCourt(codeLocal)
			
			if (local) {
				eq = new Equipement(localisation: local, code:st[2], libelle:st[5], caracteristiquesSupplementaires:st[6], marque:st[10])
			} else {
				ligneProb.add(line + ";no local found for libelle " + codeLocal)
				return null
			}
			
			TypeEquipement typeEquipement = TypeEquipement.findByPrefix(st[1])
			
			if (typeEquipement) {
				eq.typeEquipement = typeEquipement
			} else {
				ligneProb.add(line+";no type found for prefix " + st[1])
				return null
			}
			
			if (st[3]){
				eq.code=st[3]
				eq.parent = Equipement.findByCode(st[2])
			}
			
			if (Equipement.findByCode(eq.code)){
				ligneProb.add(line+";equipement avec le même code déjà chargé dans la BD " + eq.code)
				return null
			}
			
			if (st[7]) {
				FournisseurEquipement fournisseur = FournisseurEquipement.findByLibelle(st[7])
				if (fournisseur) {
					eq.fournisseur = fournisseur
				} else {
					ligneWarn.add("fournisseur non trouvé;"+st[7])
				}
			}
			if (st[8]) {
				FournisseurEquipement fabriquant = FournisseurEquipement.findByLibelle(st[8])
				if (fabriquant) {
					eq.fabriquant = fabriquant
				} else {
					ligneWarn.add("fabriquant non trouvé;"+st[8])
				}
			}
			if (st[9]) {
				FournisseurEquipement installateurReparateur = FournisseurEquipement.findByLibelle(st[9])
				if (installateurReparateur) {
					eq.installateurReparateur = installateurReparateur
				} else {
					ligneWarn.add("installateur reparateur non trouvé;"+st[9])
				}		
			}
			if (st[11]) {
				DocumentTechnique doc = DocumentTechnique.findByLibelle(st[11])
				if ( ! doc) {
					doc = new DocumentTechnique(libelle:st[11], chemin:st[11], type:"plan")
					doc.save(flush:true)
				}
				eq.addToDocuments(doc)
			}
			
			if (st[12]) {
				DocumentTechnique doc = DocumentTechnique.findByLibelle(st[12])
				if ( ! doc) {
					doc = new DocumentTechnique(libelle:st[12], chemin:st[12], type:"manuel")
					doc.save(flush:true)
				}
				eq.addToDocuments(doc)
			}
			
			if (st[13]) {
				DocumentTechnique doc = DocumentTechnique.findByLibelle(st[13])
				if ( ! doc) {
					doc = new DocumentTechnique(libelle:st[13], chemin:st[13], type:"maintenance")
					doc.save(flush:true)
				}
				eq.addToDocuments(doc)
			}
			
			def numeroOrdreMax = Equipement.executeQuery("select max(e.numeroOrdre) from Equipement e where typeEquipement = :te",['te':eq.typeEquipement])[0]
			eq.numeroOrdre = (numeroOrdreMax?:0)+1
			//eq.numeroOrdre = st[5]
			
			if (eq.parent) {
				//def numeroOrdrePMax = Equipement.executeQuery("select max(e.numeroOrdre) from Equipement e where parent = :parent",['parent':eq.parent])[0]
				//eq.code=eq.parent.code+"-"+numeroOrdrePMax
			} else {
				//eq.code=((eq.localisation.libelleCourt+'-')?:'')+eq.typeEquipement.prefix+"-"+eq.numeroOrdre
				//deja donne
			}
			
			/*
			if (unite) {
				eq.unite = unite
			} else {
				eq.unite = 
			}
			*/
			
			eq.identifiant = 10000000 + (int)(Math.random()*90000000)
			eq.save(flush:true)
			//println loc.errors
			/*
			if (eq.errors){
				println eq.errors
			}*/
			eq.identifiant = eq.id
			eq.save(flush:true)
			
			equipementsCharges.add(eq)

		}
		
		def eqids=""
		for(def eq:equipementsCharges) {
			eqids+=eq.id+","
		}
		
		
		[equipementsCharges:equipementsCharges, ligneProb: ligneProb, ligneWarn: ligneWarn, eqids:eqids]
	}
	
	def deleteChargement() {
		def eqids = params.eqids.split(",")
		Equipement eq
		int deleted = 0
		for (def eqid: eqids){
			eq = Equipement.get(eqid)
			if (eq) {
				eq.delete(flush:true)
				deleted++
			}
		}
		
		flash.message = "Les équipements chargés "+"("+deleted+")"+" ont été supprimés" 
        redirect(action: "uploadFile")
	}
		
	def fillEquipements(){
		def eqeq="""U;001;U001;2EPL;01;01;2EPL01;2EPL01-01;Cellule inverseur arrivees 1 et 2;630A surisole a 24 KV type IM/IS
U;001;U001;2EPL;01;02;2EPL01;2EPL01-02;Cellule depart;630A surisole a 24 KV type IM/IS
U;001;U001;2EPL;01;03;2EPL01;2EPL01-03;Cellule comptage MT;630A surisole a 24 KV type CM/TT
U;001;U001;2EPL;01;04;2EPL01;2EPL01-04;Cellule protection generale par disjoncteur;630A surisole a 24 KV type DC/DM1
U;001;U001;2EPL;02;01;2EPL02;2EPL02-01;Cellule arrivee secours par disjoncteur;630A surisole a 24 KV type DC/DM1
U;001;U001;2EPL;02;02;2EPL02;2EPL02-02;Cellule arrivee secours par disjoncteur;630A surisole a 24 KV type DC/DM1
U;001;U001;2EPL;02;03;2EPL02;2EPL02-03;Cellule arrivee secours par disjoncteur;630A surisole a 24 KV type DC/DM1
U;001;U001;2EPL;02;04;2EPL02;2EPL02-04;Cellule depart secours 1;630A surisole a 24 KV type IM/IS
U;001;U001;2EPL;02;05;2EPL02;2EPL02-05;Cellule depart secours 1;630A surisole a 24 KV type IM/IS
U;001;U001;2EPL;03;01;2EPL03;2EPL03-01;Cellule arrivee secours 1;630A surisole a 24 KV type IM/IS
U;001;U001;2EPL;03;02;2EPL03;2EPL03-02;Cellule arrivee secours 1;630A surisole a 24 KV type IM/IS
U;001;U001;2EPL;03;03;2EPL03;2EPL03-03;Cellule depart secours;630A surisole a 24 KV type IM/IS
U;001;U001;2EPL;03;04;2EPL03;2EPL03-04;Cellule inverseur N/S;630A surisole a 24 KV type NE/NSM
U;001;U001;2EPL;03;05;2EPL03;2EPL03-05;Cellule protection depart pour postes B087;630A surisole a 24 KV type DC/DM1
U;001;U001;2EPL;03;06;2EPL03;2EPL03-06;Cellule protection depart pour postes B088;630A surisole a 24 KV type DC/DM1
U;001;U001;2EPL;03;07;2EPL03;2EPL03-07;Cellule protection depart pour poste TX 5A;630A surisole a 24 KV type DC/DM1
U;001;U001;2EPL;03;08;2EPL03;2EPL03-08;Cellule protection depart pour poste TX 6A;630A surisole a 24 KV type DC/DM1
U;001;U001;2EPL;03;09;2EPL03;2EPL03-09;Cellule protection depart pour poste TX 5B;630A surisole a 24 KV type DC/DM1
U;001;U001;2EPL;03;10;2EPL03;2EPL03-10;Cellule protection depart pour poste TX 6B;630A surisole a 24 KV type DC/DM1"""
		def i=0
		mederp.Unite unite = mederp.Unite.findByLibelle("Service ingenierie")
		
		for (String s: eqeq.split("\n")) {
			def st = s.split(";")
			i++
			Equipement eq
			Localisation local = Localisation.findByLibelle(st[2])
			if (local) {
				eq = new Equipement(localisation: local, code:st[7], libelle:st[8], caracteristiquesSupplementaires:st[9])
			} else {
				println "no local found for libelle " + st[2]
				continue
			}
			TypeEquipement typeEquipement = TypeEquipement.findByPrefix(st[6])
			if (typeEquipement) {
				eq.typeEquipement = typeEquipement
			} else {
				println "no type found for prefix " + st[6]
				continue
			}
			
			
			def numeroOrdreMax = Equipement.executeQuery("select max(e.numeroOrdre) from Equipement e where typeEquipement = :te",['te':eq.typeEquipement])[0]
			eq.numeroOrdre = (numeroOrdreMax?:0)+1
			eq.numeroOrdre = st[5]
			
			if (eq.parent) {
				def numeroOrdrePMax = Equipement.executeQuery("select max(e.numeroOrdre) from Equipement e where parent = :parent",['parent':eq.parent])[0]
				eq.code=eq.parent.code+"-"+numeroOrdrePMax
			} else {
				eq.code=((eq.localisation.libelleCourt+'-')?:'')+eq.typeEquipement.prefix+"-"+eq.numeroOrdre
			}
			
			if (unite) {
				eq.unite = unite
			}
			
			eq.identifiant = 10000000 + (int)(Math.random()*90000000)
			eq.save(flush:true)
			
			if (eq.errors){
				println eq.errors
			}
			eq.identifiant = eq.id
			eq.save(flush:true)
		}
		render "done ! "	
	}

	 def demandesNonResolue() {
        HashSet equipementInstanceSet = new HashSet()

				def equipements = Equipement.demandesNonResolue()
				if (equipements.size()>0) {
					equipementInstanceSet.addAll(equipements)
				}
				
        [equipementInstanceSet: equipementInstanceSet]
    }


}
