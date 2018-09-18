package mederp

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.JSON

//Generation template modifiée par rezz
class LocalisationController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
		List localisations = []
		if (params.code) {
			
			def locs = Localisation.findAllByLibelleCourt(params.code)
			if (locs.size()>0) {
				localisations.addAll(locs)
			}
			
			
		} else if (params.libelle) {
			localisations.addAll(Localisation.findAllByLibelleIlike('%'+params.libelle+'%'))
		} else {
			def types = TypeLocalisation.findAllByNiveauLessThan(2)
			for (def type: types)	
				localisations.addAll(Localisation.findAllByTypeLocalisationAndParentIsNull(type))
		}
		//localisations.addAll(Localisation.findAllByTypeLocalisation(TypeLocalisation.get(2)))
		//localisations.sort(x,y<> )
		if (localisations.size()==0) {
			flash.message = "Aucn équipement ne correspond à l'idetifiant saisi"
		} else if (localisations.size()==1) {
			redirect(action:"show", id: localisations.get(0).id)
			return
		} else if (params.code || params.libelle) {
			flash.message = "" + localisations.size()+ " résultats trouvés"
		}
			
        [localisationInstanceList: localisations]
    }

    def create() {
        [localisationInstance: new Localisation(params)]
    }

    def save() {
        def localisationInstance = new Localisation(params)
        if (!localisationInstance.save(flush: true)) {
            render(view: "create", model: [localisationInstance: localisationInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'localisation.label', default: 'Localisation'), localisationInstance.id])
        redirect(action: "show", id: localisationInstance.id)
    }

    def show(Long id) {
        def localisationInstance = Localisation.get(id)
        if (!localisationInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'localisation.label', default: 'Localisation'), id])
            redirect(action: "list")
            return
        }
		JSON.use('deep')
        [localisationInstance: localisationInstance, childrenJson: (new LocalisationTree(localisationInstance,10).children as JSON)]
    }

    def edit(Long id) {
        def localisationInstance = Localisation.get(id)
        if (!localisationInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'localisation.label', default: 'Localisation'), id])
            redirect(action: "list")
            return
        }

        [localisationInstance: localisationInstance]
    }

    def update(Long id, Long version) {
        def localisationInstance = Localisation.get(id)
        if (!localisationInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'localisation.label', default: 'Localisation'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (localisationInstance.version > version) {
                localisationInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'localisation.label', default: 'Localisation')] as Object[],
                          "Another user has updated this Localisation while you were editing")
                render(view: "edit", model: [localisationInstance: localisationInstance])
                return
            }
        }

        localisationInstance.properties = params

        if (!localisationInstance.save(flush: true)) {
            render(view: "edit", model: [localisationInstance: localisationInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'localisation.label', default: 'Localisation'), localisationInstance.id])
        redirect(action: "show", id: localisationInstance.id)
    }

    def delete(Long id) {
        def localisationInstance = Localisation.get(id)
        if (!localisationInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'localisation.label', default: 'Localisation'), id])
            redirect(action: "list")
            return
        }

        try {
            localisationInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'localisation.label', default: 'Localisation'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'localisation.label', default: 'Localisation'), id])
            redirect(action: "show", id: id)
        }
    }
	
	def fillBlocs(){
		String floor = "IR"
		for (int i =1; i<=12; i++){
			(new Localisation(libelle:floor+"-"+i, typeLocalisation:TypeLocalisation.get(7), parent: Localisation.findByLibelleCourt(floor), ordre:i, libelleCourt:floor+"-"+i)).save(flush:true)
		}
		
		render ("done ! ")
	}
	
	def fillLocaux(){
		def locloc="""B1;B001;CIRCULATION;Couloir                         ;??? ;STAIR-01;29,09;Escalier 1
B1;B002;RADIO & CHEMO THERAPY ;Radiothérapie                       ;???????? ???????;CHANGE;.;Vestiaire Radiothérapie 2
B1;B003;RADIO & CHEMO THERAPY ;Radiothérapie                       ;???????? ???????;WAITING;10,56;Attente Scanner Simulateur
B1;B004;RADIO & CHEMO THERAPY ;Radiothérapie                       ;???????? ???????;CT SIMULATION;40,71;Scanner simulateur
B1;B005;RADIO & CHEMO THERAPY ;Radiothérapie                       ;???????? ???????;CORRIDOR;46,87;Pas de plaque
B1;B006;RADIO & CHEMO THERAPY ;Radiothérapie                       ;???????? ???????;PLANNING;30,21;Planification dosimétrie curiethérapie
B1;B007;RADIO & CHEMO THERAPY ;Radiothérapie                       ;???????? ???????;HDR;61,49;Salle de Traitement
B1;B008;RADIO & CHEMO THERAPY ;Radiothérapie                       ;???????? ???????;CONTROL;11,81;Salle de contrôle curiéthérapie
B1;B009;RADIO & CHEMO THERAPY ;Radiothérapie                       ;???????? ???????;CORRIDOR;14,03;Salles de traitement curiethérapie
B1;B010;RADIO & CHEMO THERAPY ;Radiothérapie                       ;???????? ???????;PREPARATION;12,75;Salle de préparation
B1;B011;RADIO & CHEMO THERAPY ;Radiothérapie                       ;???????? ???????;APPLICATION;30,56;Salle d'application
B1;B012;RADIO & CHEMO THERAPY ;Radiothérapie                       ;???????? ???????;HOLD;20,52;Attente patients
B1;B013;RADIO & CHEMO THERAPY ;Radiothérapie                       ;???????? ???????;CONTROL ROOM;11,95;Salle de contrôle scanner simulateur
B1;B014;RADIO & CHEMO THERAPY ;Radiothérapie                       ;???????? ???????;BUNKER RADIOTHERAPY 1;68,74;Salle de Téléradiothérapie 1
B1;B015;RADIO & CHEMO THERAPY ;Radiothérapie                       ;???????? ???????;BUNKER RADIOTHERAPY 2;69,23;Salle de Téléradiothérapie 2
B1;B016;RADIO & CHEMO THERAPY ;Radiothérapie                       ;???????? ???????;BUNKER RADIOTHERAPY 3;69,23;Salle de Téléradiothérapie 3
B3;B017;CSSD;Stérilisation                           ;????? ???????;PATH TROUGH;4,30;Sas habillage
B1;B018;RADIO & CHEMO THERAPY ;Radiothérapie                       ;???????? ???????;CONTROL ROOM FOR BUNKERS 3;15,48;Salle de contrôle Radiochirurgie
B1;B019;CIRCULATION;Couloir                         ;??? ;VIP LIFT-01;4,00;Ascenseur VIP (AL1)
B1;B020;RADIO & CHEMO THERAPY ;Radiothérapie                       ;???????? ???????;ACCESS VIP;7,18;Accès Ascenseur VIP
B1;B021;RADIO & CHEMO THERAPY ;Radiothérapie                       ;???????? ???????;WC;2,53;Toilettes 1
B1;B022;RADIO & CHEMO THERAPY ;Radiothérapie                       ;???????? ???????;CONSULTATION;22,70;Consultation 2
B1;B023;RADIO & CHEMO THERAPY ;Radiothérapie                       ;???????? ???????;CONSULTATION;14,03;Consultation 1
B1;B024;RADIO & CHEMO THERAPY ;Radiothérapie                       ;???????? ???????;OFFICE PHYSICIAN;22,97;Bureau Radiothérapeuthe
B1;B025;RADIO & CHEMO THERAPY ;Radiothérapie                       ;???????? ???????;WC;1,98;Toilettes 2
B1;B026;RADIO & CHEMO THERAPY ;Radiothérapie                       ;???????? ???????;WC;1,98;Toilettes 3
B1;B027;RADIO & CHEMO THERAPY ;Radiothérapie                       ;???????? ???????;WC;2,53;Toilettes 4
B1;B028;RADIO & CHEMO THERAPY ;Radiothérapie                       ;???????? ???????;WAITING;18,62;Attente Radiothérapie
B1;B029;RADIO & CHEMO THERAPY ;Radiothérapie                       ;???????? ???????;CONSULTATION;14,03;Consultation 3
B1;B030;MEP;.;.;ELEC.;.;Tableau électrique  TEB101
B1;B031;RADIO & CHEMO THERAPY ;Radiothérapie                       ;???????? ???????;CHANGE;6,80;Vestiaire radiothérapie 1
B1;B032;RADIO & CHEMO THERAPY ;Radiothérapie                       ;???????? ???????;CHANGE;10,62;Vestiaire
B1;B033;RADIO & CHEMO THERAPY ;Radiothérapie                       ;???????? ???????;CONTROL ROOM FOR BUNKER 2;34,27;Salle de contrôle et acces Radiothérapie 2
B1;B034;RADIO & CHEMO THERAPY ;Radiothérapie                       ;???????? ???????;CONTROL ROOM FOR BUNKER 1;20,25;Salle de contrôle et acces Radiothérapie 2
B1;B035;RADIO & CHEMO THERAPY ;Radiothérapie                       ;???????? ???????;CHANGE;6,80;Vestiaire 
B1;B036;RADIO & CHEMO THERAPY ;Radiothérapie                       ;???????? ???????;NURSE STATION;66,08;Poste infirmier
B1;B037;RADIO & CHEMO THERAPY ;Radiothérapie                       ;???????? ???????;CHANGE;14,16;Vestiaire scanner simulateur
B1;B038;RADIO & CHEMO THERAPY ;Radiothérapie                       ;???????? ???????;TOILET;2,38;Toilettes 1
B1;B039;RADIO & CHEMO THERAPY ;Radiothérapie                       ;???????? ???????;TOILET;2,38;Toilettes 2
B1;B040;RADIO & CHEMO THERAPY ;Radiothérapie                       ;???????? ???????;CORRIDOR;60,64;Accès Bureaux Curiethérapie
B1;B041;MORTUARY;Morgue                              ;?????? ???????;OFFICE;8,55;Bureau responsable morgue
B1;B042;MORTUARY;Morgue                              ;?????? ???????;BODY PICK-UP;21,82;Identification 
B1;B043;MORTUARY;Morgue                              ;?????? ???????;BODY HOLDING.12No.S;27,75;Conservation
B1;B044;MORTUARY;Morgue                              ;?????? ???????;VIEW;17,00;Présentation
B1;B045;MORTUARY;Morgue                              ;?????? ???????;FAMILY;10,46;Attente familles
B1;B046;MORTUARY;Morgue                              ;?????? ???????;WC;3,53;Toilettes
B1;B047;RADIO & CHEMO THERAPY ;Radiothérapie                       ;???????? ???????;C/U;7,03;Salle Propre
B1;B048;RADIO & CHEMO THERAPY ;Radiothérapie                       ;???????? ???????;ARCHIVES;22,86;Archives
B1;B049;RADIO & CHEMO THERAPY ;Radiothérapie                       ;???????? ???????;MOLDING;12,93;Salle de Moulage
B1;B050;RADIO & CHEMO THERAPY ;Radiothérapie                       ;???????? ???????;DOSIMETRY;10,21;Planification et  dosimétrie radiothérapie
B1;B051;RADIO & CHEMO THERAPY ;Radiothérapie                       ;???????? ???????;NURSE OFFICE;10,27;Bureau techniciens
B1;B052;RADIO & CHEMO THERAPY ;Radiothérapie                       ;???????? ???????;PHYSICIANS OFFICE;10,50;Bureau Physiciens
B1;B053;RADIO & CHEMO THERAPY ;Radiothérapie                       ;???????? ???????;RECOVERY ROOM;15,63;Salle de recuperation
B1;B054;RADIO & CHEMO THERAPY;Radiothérapie                       ;???????? ???????;MEETING ROOM;24,87;Salle de réunion
B1;B055;RADIO & CHEMO THERAPY;Radiothérapie                       ;???????? ???????;DR OFFICE;19,15;Bureau Médecin
B1;B056;RADIO & CHEMO THERAPY;Radiothérapie                       ;???????? ???????;DR OFFICE;19,35;Bureau Médecin
B1;B057;RADIO & CHEMO THERAPY;Radiothérapie                       ;???????? ???????;STAFF FACILITIES CORRIDOR;42,37;Bureaux et salle de réunion
B1;B058;RADIO & CHEMO THERAPY;Radiothérapie                       ;???????? ???????;PATIENT CUB 7;12,29;Box de Chimiothérapie Lit 0007
B1;B059;RADIO & CHEMO THERAPY;Radiothérapie                       ;???????? ???????;PAT WC;6,56;Toilettes handicapés
B1;B060;RADIO & CHEMO THERAPY;Radiothérapie                       ;???????? ???????;PATIENT CUB 6;13,61;Box de Chimiothérapie lit 0006
B1;B061;RADIO & CHEMO THERAPY;Radiothérapie                       ;???????? ???????;PATIENT CUB 5;12,61;Box de Chimiothérapie Lit 0005
B1;B062;RADIO & CHEMO THERAPY;Radiothérapie                       ;???????? ???????;PATIENT CUB 4;12,61;Box de Chimiothérapie Lit 0004
B1;B063;RADIO & CHEMO THERAPY;Radiothérapie                       ;???????? ???????;PATIENT CUB 3;12,61;Box de Chimiothérapie Lit 0003
B1;B064;RADIO & CHEMO THERAPY;Radiothérapie                       ;???????? ???????;PATIENT CUB 2;12,61;Box de Chimiothérapie lit 0002
B1;B065;CHEMOTHERAPY;Chimiothérapie        ; ????? ???????? ?????????;OFFICE DIRECTOR;12,71;Chef de Pôle Oncologie
B1;B066;CHEMOTHERAPY;Chimiothérapie        ; ????? ???????? ?????????;WAITING;15,13;Salle d'attente chimiothérapie
B1;B067;RADIO & CHEMO THERAPY ;Radiothérapie                       ;???????? ???????;SECRETARY;21,48;Secrétariat
B1;B068;RADIO & CHEMO THERAPY ;Radiothérapie                       ;???????? ???????;CORRIDOR;5,57;.
B1;B069;RADIO & CHEMO THERAPY ;Radiothérapie                       ;???????? ???????;HK;6,26;Local ménage
B1;B070;RADIO & CHEMO THERAPY ;Radiothérapie                       ;???????? ???????;CORRIDOR;39,89;Oncologie
B1;B071;RADIO & CHEMO THERAPY;Radiothérapie                       ;???????? ???????;CORRIDOR;62,60;Chimiothérapie
B1;B072;CHEMOTHERAPY;Chimiothérapie        ; ????? ???????? ?????????;WAITING;46,66;.
B1;B073;CHEMOTHERAPY;Chimiothérapie        ; ????? ???????? ?????????;CASH;9,45;Admisssions
B1;B074;CHEMOTHERAPY;Chimiothérapie        ; ????? ???????? ?????????;RECEPTION;17,87;Acceuil Réception
B1;B075;RADIO & CHEMO THERAPY;Radiothérapie                       ;???????? ???????;PATIENT CUB 1 ;13,70;Box de Chimiothérapie lit 0001
B1;B076;RADIO & CHEMO THERAPY;Radiothérapie                       ;???????? ???????;HK;4,69;Local ménage
B1;B077;RADIO & CHEMO THERAPY;Radiothérapie                       ;???????? ???????;NURSES OFFICE;15,39;Bureau cadre infirmier
B1;B078;RADIO & CHEMO THERAPY;Radiothérapie                       ;???????? ???????;C/U;8,57;Salle propre
B1;B079;RADIO & CHEMO THERAPY;Radiothérapie                       ;???????? ???????;D/U;8,78;Local sale
B1;B080;RADIO & CHEMO THERAPY;Radiothérapie                       ;???????? ???????;CHEMO PREP;16,97;Salle de préparation
B1;B081;RADIO & CHEMO THERAPY;Radiothérapie                       ;???????? ???????;STORE;16,16;Pharmacie
B1;B082;RADIO & CHEMO THERAPY;Radiothérapie                       ;???????? ???????;PATIENT CUB 11;17,33;Box de Chimiothérapie Lit 0009
B1;B083;RADIO & CHEMO THERAPY;Radiothérapie                       ;???????? ???????;PATIENT CUB 10;15,53;Box de Chimiothérapie Lit 0008
B1;B084;RADIO & CHEMO THERAPY;Radiothérapie                       ;???????? ???????;LINEN;5,91;Lingerie
B3;B085;CIRCULATION;Couloir                         ;??? ;STAIR-02;30,42;Escalier de secours
B3;B086;CIRCULATION;Couloir                         ;??? ;CORRIDOR;107,08;Acces esacalier de secours
B3;B087;TRANSFORMERS;Transformateurs electriques     ;???? ???????;TRANSFORMER ROOM;98,69;Local transformateurs TRB101
B3;B088;TRANSFORMERS;Transformateurs electriques     ;???? ???????;TRANSFORMER ROOM;98,69;Local transformateurs TRB102
B3;B089;WASTE MANAGEMENT;Gestion des déchets               ;?????? ??????;MEDICAL WASTE WALK-IN REFRIGERATOR;20,85;Stockage déchets médcicaux
B3;B090;WASTE MANAGEMENT;Gestion des déchets               ;?????? ??????;TROLLEY WASH;15,39;Lavage chariots 
B3;B091;WASTE MANAGEMENT;Gestion des déchets               ;?????? ??????;TRASH SEPARATOR AREA;26,82;Stockage conteneurs
B3;B092;TRANSFORMERS;Transformateurs electriques     ;???? ???????;LV ROOM;114,06;Local tableaux généraux basse tension LVB101
B3;B093;TRANSFORMERS;Transformateurs electriques     ;???? ???????;LV ROOM;112,66;Local tableaux généraux basse tension LVB102
B3;B094;LINEN HOLDING;Lingerie et uniformes               ;???? ???????;MG ROOM;24,14;Stockage Materiel
B3;B095;LINEN HOLDING;Lingerie et uniformes               ;???? ???????;MG ROOM;23,92;Stockage Materiel
B3;B096;CIRCULATION;Couloir                         ;??? ;CORRIDOR;62,13;Accès morgue et monte malades rouge
B3;B097;A.H.U;.;.;A.H.U;221,25;Local centrales de traitement d'air TAB101
B3;B098;LINEN HOLDING;Lingerie et uniformes               ;???? ???????;OFFICE;8,30;Bureau Lingerie
B3;B099;LINEN HOLDING;Lingerie et uniformes               ;???? ???????;HK;12,03;Local ménage
B3;B100;CSSD;Stérilisation                           ;????? ???????;STERILE GOODS STAFF;8,93;SAS Zone stérile
B3;B101;LINEN HOLDING;Lingerie et uniformes               ;???? ???????;C/U;31,26;Linge propre
B3;B102;LINEN HOLDING;Lingerie et uniformes               ;???? ???????;D/U;30,59;Linge sale
B3;B103;LINEN HOLDING;Lingerie et uniformes               ;???? ???????;LINEN STORE;15,52;Stock linge neuf
B3;B104;LINEN HOLDING;Lingerie et uniformes               ;???? ???????;STAFF WC;3,35;Sanitaires personnel hommes
B3;B105;LINEN HOLDING;Lingerie et uniformes               ;???? ???????;STAFF WC;3,27;Sanitaires personnel femmes
B3;B106;LINEN HOLDING;Lingerie et uniformes               ;???? ???????;OFFICE;19,25;Bureau lingerie
B3;B107;LINEN HOLDING;Lingerie et uniformes               ;???? ???????;SEWING AND UNIFORM STORE;37,72;Distriubution uniformes
B3;B108;CSSD;Stérilisation                           ;????? ???????;LOADING + SERVING;60,84;Livraison matériel sale /Lavage
B3;B109;CSSD;Stérilisation                           ;????? ???????;WASHER;5,63;.
B3;B110;CSSD;Stérilisation                           ;????? ???????;TROLLEYS / WASH;10,09;Lavage chariots 
B3;B111;CSSD;Stérilisation                           ;????? ???????;CSSD;106,20;Zone de conditionnement
B3;B112;CSSD;Stérilisation                           ;????? ???????;CLEAN TROLLEY PARK;22,19;Parking Chariots stériles
B3;B113;CSSD;Stérilisation                           ;????? ???????;STERILE GOODS STORE;42,83;Zone stockage stérile
B3;B114;CSSD;Stérilisation                           ;????? ???????;CORRIDOR;22,44;Sterilisation 
B3;B115;LINEN HOLDING;Lingerie et uniformes               ;???? ???????;CORRIDOR;189,08;Accès Lingerie et dechets médicaux
B3;B116;CSSD;Stérilisation                           ;????? ???????;CLEAN SUPPLIES;21,68;Stock propre
B3;B117;CSSD;Stérilisation                           ;????? ???????;OFFICE;7,98;Bureau
B3;B118;CSSD;Stérilisation                           ;????? ???????;STAFF REST;6,30;Salle de repos
B3;B119;CSSD;Stérilisation                           ;????? ???????;STAFF CHANGE;3,15;Vestiaires hommes
B3;B120;CSSD;Stérilisation                           ;????? ???????;STAFF CHANGE;3,05;Vestiaires femmes
B3;B121;CSSD;Stérilisation                           ;????? ???????;STAFF WC;2,53;.
B3;B122;CSSD;Stérilisation                           ;????? ???????;STAFF WC;2,26;.
B5;B123;CIRCULATION;Couloir                         ;??? ;STAIR-03;29,77;Escalier de secours
B5;B124;CENTRAL STORE;Magasin central                 ;  ?????? ?????;LOADING & UNLOADING BAY;113,06;Quai de déchargement
B5;B125;CIRCULATION;Couloir                         ;??? ;CORRIDOR;89,65;Accès stérilisation et déchets médicaux
B5;B126;PHARMACY;Pharmacie                                  ; ??????;QUARANTINE;15,19;Quarantaine
B5;B127;PHARMACY;Pharmacie                                  ; ??????;RECEIVE + RECORDS;23,96;Pharmacie: Réception produits
B5;B128;CENTRAL STORE;Magasin central                 ;  ?????? ?????;RECEIVE + RECORDS;23,96;Magasin: Réception produits
B5;B129;CENTRAL STORE;Magasin central                 ;  ?????? ?????;OFFICE;13,44;Bureau
B5;B130;CENTRAL STORE;Magasin central                 ;  ?????? ?????;CORRIDOR;23,82;.
B5;B131;PHARMACY;Pharmacie                                  ; ??????;CORRIDOR;76,48;.
B5;B132;PHARMACY;Pharmacie                                  ; ??????;STAFF WC;2,64;Sanitaires
B5;B133;PHARMACY;Pharmacie                                  ; ??????;STAFF CHANGE;7,15;Vestiaires hommes
B5;B134;PHARMACY;Pharmacie                                  ; ??????;STORE;29,24;Stock Solutés
B5;B135;CENTRAL STORE;Magasin central                 ;  ?????? ?????;OFFICE SUPPLIES;32,42;Stock materiel de bureau
B5;B136;CENTRAL STORE;Magasin central                 ;  ?????? ?????;STAFF WC;6,26;Sanitaires Hommes
B5;B137;CENTRAL STORE;Magasin central                 ;  ?????? ?????;STAFF WC;5,46;Sanitaires femmes
B5;B138;CENTRAL STORE;Magasin central                 ;  ?????? ?????;OFFICE;9,29;Bureau
B5;B139;PHARMACY;Pharmacie                                  ; ??????;STAFF WC;2,73;Sanitaire
B5;B140;PHARMACY;Pharmacie                                  ; ??????;STAFF CHANGE;7,38;Vestaire femmes
B5;B141;PHARMACY;Pharmacie                                  ; ??????;WASH;8,78;Laverie
B5;B142;PHARMACY;Pharmacie                                  ; ??????;PACK;11,47;Emballages
B5;B143;PHARMACY;Pharmacie                                  ; ??????;STORE;9,45;Stock
B5;B144;PHARMACY;Pharmacie                                  ; ??????;PROSTHESIS STORE;30,48;Stock Conssomables
B5;B145;CENTRAL STORE;Pharmacie                                  ; ??????;GENERAL SUPPLIES;27,30;Materiel exploitation
B5;B146;CENTRAL STORE;Pharmacie                                  ; ??????;HYGENIC SUPPLIES;28,80;Materiel Hygiene
B5;B147;PHARMACY;Pharmacie                                  ; ??????;STORE;10,13;Chambre froide positive
B5;B148;PHARMACY;Pharmacie                                  ; ??????;CHEMICAL STORE;10,13;Stock réactifs
B5;B149;PHARMACY;Pharmacie                                  ; ??????;MEDICINE BULK STORE;53,76;Stock medicaments
B5;B150;CENTRAL STORE;Pharmacie                                  ; ??????;MEDICAL SUPPLIES;27,30;Instrumentation et petit materiel medical
B5;B151;CENTRAL STORE;Pharmacie                                  ; ??????;STAFF REST;10,07;Salle de repos 
B5;B152;CENTRAL STORE;Pharmacie                                  ; ??????;OFFICE;17,09;Bureau
B5;B153;PHARMACY;Pharmacie                                  ; ??????;DANGEROUS DRUG STORE;10,13;Stock stupefiants
B5;B154;PHARMACY;Pharmacie                                  ; ??????;LIBRARY;15,47;Documentation et archives
B5;B155;PHARMACY;Pharmacie                                  ; ??????;NOT USED;.;.
B5;B156;CENTRAL STORE;Magasin central                 ;  ?????? ?????;STORE SMALL;9,89;Stock Outillage
B5;B157;CENTRAL STORE;Pharmacie                                  ; ??????;TROLLEY PARK;56,30;Parking Chariots
B5;B158;PHARMACY;Pharmacie                                  ; ??????;STAFF REST;15,50;Salle de repos
B5;B159;PHARMACY;Pharmacie                                  ; ??????;OFFICE;10,13;Bureau
B5;B160;PHARMACY;Pharmacie                                  ; ??????;SEC.;10,13;Secretariat
B5;B161;PHARMACY;Pharmacie                                  ; ??????;OFFICE;10,80;Bureau
B5;B162;PHARMACY;Pharmacie                                  ; ??????;STOCK DEPORT;13,97;Stock implants
B5;B163;PHARMACY;Pharmacie                                  ; ??????;RETURN & SORTING;11,36;Retours et triage
B5;B164;PHARMACY;Pharmacie                                  ; ??????;RECEPTION;11,03;Acceuil
B5;B165;PHARMACY;Pharmacie                                  ; ??????;ISSUE + DISPATCH;25,74;Livraisons
B5;B166;CENTRAL STORE;Pharmacie                                  ; ??????;STORE PALLETS;137,56;Stockage pallettes
B5;B167;CIRCULATION;Couloir                         ;??? ;CORRIDOR;40,44;Accès Magasins et Cuisine
B5;B168;MAIN KITCHEN;Cuisine                                        ;  ???? ;GOODS IN & DECANT;11,90;Reception produits
B5;B169;MAIN KITCHEN;Cuisine                                        ;  ???? ;DRY STORE;21,75;Epicerie
B5;B170;MAIN KITCHEN;Cuisine                                        ;  ???? ;RAW WASH;26,06;Decartonnage et lavage produits
B5;B171;MAIN KITCHEN;Cuisine                                        ;  ???? ;CORRIDOR;35,64;.
B5;B172;MAIN KITCHEN;Cuisine                                        ;  ???? ;DAIRY COLD ROOM;7,76;Chambre froide journaliere
B5;B173;MAIN KITCHEN;Cuisine                                        ;  ???? ;MEAT & POULTRY COLD ROOM;9,41;Chmabre froide viande et volaille
B5;B174;MAIN KITCHEN;Cuisine                                        ;  ???? ;F&V COLD ROOM;9,96;Chambre froide fruits et légumes
B5;B175;MAIN KITCHEN;Cuisine                                        ;  ???? ;STAFF CHANGE;2,18;Vestiaire Femmes
B5;B176;MAIN KITCHEN;Cuisine                                        ;  ???? ;GENERAL PURPOSE COLD ROOM;11,86;Chambre froide Generale
B5;B177;MAIN KITCHEN;Cuisine                                        ;  ???? ;STAFF CHANGE;2,17;Vestiaire Hommes
B5;B178;MAIN KITCHEN;Cuisine                                        ;  ???? ;FISH COLD ROOM;8,87;Chambre froide Poisson
B5;B179;MAIN KITCHEN;Cuisine                                        ;  ???? ;STAFF REST;10,45;Salled e repos
B5;B180;MAIN KITCHEN;Cuisine                                        ;  ???? ;CHEF'S OFFICE;10,45;Bureau chef cuisinier
B5;B181;MAIN KITCHEN;Cuisine                                        ;  ???? ;MEAT & POULTRY PREP.;29,00;Preparation Viande et Volaille
B5;B182;MAIN KITCHEN;Cuisine                                        ;  ???? ;FISH PREP AREA;10,26;Preparation Poisson
B5;B183;MAIN KITCHEN;Cuisine                                        ;  ???? ;PAN WASH AREA;8,52;Plonge materiel de cuisine
B5;B184;MAIN KITCHEN;Cuisine                                        ;  ???? ;HOT KITCHEN;30,73;Cuisine Chaude
B5;B185;MAIN KITCHEN;Cuisine                                        ;  ???? ;TRAINING & LOADING;64,67;Dressage plateaux
B5;B186;CIRCULATION;Couloir                         ;??? ;CORRIDOR;51,24;.
B5;B187;MAIN KITCHEN;Cuisine                                        ;  ???? ;DISH WASH AREA;20,83;Plonge materiel de service
B5;B188;MAIN KITCHEN;Cuisine                                        ;  ???? ;TROLLEY WASH;13,94;Lavage chariots 
B5;B189;MAIN KITCHEN;Cuisine                                        ;  ???? ;REFUSE HOLD;3,62;Retours
B2;B190;CIRCULATION;Couloir                         ;??? ;CORRIDOR;104,45;.
B2;B191;A.H.U;.;.;A.H.U;221,25;Local centrales de traitement d'air TAB102
B2;B192;IT DEPARTMENT;Service informatique          ;????? ??????????;SERVER ROOM;89,67;Salle des serveurs
B2;B193;IT DEPARTMENT;Service informatique          ;????? ??????????;OFFICE;16,92;Bureau
B2;B194;IT DEPARTMENT;Service informatique          ;????? ??????????;IT WORK AREA;45,83;Service réseau Informatique
B2;B195;IT DEPARTMENT;Service informatique          ;????? ??????????;MEDICAL EQ.UPS;33,18;Onduleurs equipements médicaux
B1;B196;RADIO & CHEMO THERAPY ;Radiothérapie                       ;???????? ???????;D/U;7,03;Local Sale
B2;B197;IT DEPARTMENT;Service informatique          ;????? ??????????;I.T.UPS;32,94;Onduleurs équipements informatique
B2;B198;IT DEPARTMENT;Service informatique          ;????? ??????????;BATTERY ROOM;15,42;Local Batteries
B4;B199;CIRCULATION;Couloir                         ;??? ;CORRIDOR;77,12;.
B4;B200;ENG. DEPT;Service ingénierie               ;   ????? ???????;RECEPTION;14,17;Secrétariat
B4;B201;ENG. DEPT;Service ingénierie               ;   ????? ???????;WORKSHOP;42,83;Ateliers
B4;B202;BIO MEDICAL DEPARTMENT;.;.;WORKSHOP-BIOMEDICAL ENGINEERING;31,05;Atelier
B4;B203;BIO MEDICAL DEPARTMENT;.;.;PRODUCT INFORMATION + ENGINEERING OFFICE;29,46;Documentation et archives
B4;B204;CIRCULATION;Couloir                         ;??? ;CORRIDOR;57,80;.B4;B205;BIO MEDICAL DEPARTMENT;.;.;RECEPTION;7,25;Secretariat 
B4;B206;BIO MEDICAL DEPARTMENT;.;.;CORRIDOR;15,90;.B4;B207;MEP;.;.;IDF ROOM;9,45;Local distribution courants faibles CfB101
B4;B208;BIO MEDICAL DEPARTMENT;.;.;OFFICE;6,38;Bureau
B4;B209;BIO MEDICAL DEPARTMENT;.;.;STORE ROOM;9,30;Reserve
B4;B210;CIRCULATION;Couloir                         ;??? ;LIFT LOBBY;54,98;Hall Ascenceurs
B4;B211;CIRCULATION;Couloir                         ;??? ;STAIR-05;25,88;Escalier 5
B6;B213;CIRCULATION;Couloir                         ;??? ;CORRIDOR;81,69;.
B4;B214;MENS & WOMENS STAFF CHANGE & LOCKERS;Vestiaires                                ;??? ??????;TOILET LOBBY;16,26;Sanitaires
B4;B215;MENS & WOMENS STAFF CHANGE & LOCKERS;Vestiaires                                ;??? ??????;SHOWER LOBBY;16,05;Douches
B6;B216;MENS & WOMENS STAFF CHANGE & LOCKERS;Vestiaires                                ;??? ??????;STORE;13,50;Reserve
B6;B217;MENS & WOMENS STAFF CHANGE & LOCKERS;Vestiaires                                ;??? ??????;TOILET LOBBY;7,25;Toilettes
B6;B218;MENS & WOMENS STAFF CHANGE & LOCKERS;Vestiaires                                ;??? ??????;SHOWER LOBBY;17,31;Douches
B6;B219;MENS & WOMENS STAFF CHANGE & LOCKERS;Vestiaires                                ;??? ??????;MEN STAFF CHANGE & LOCKERS;.;Vestiaires Hommes
B6;B220;MENS & WOMENS STAFF CHANGE & LOCKERS;Vestiaires                                ;??? ??????;WOMEN STAFF CHANGE & LOCKERS;217,83;Vestiaires Femmes
B6;B221;CIRCULATION;Couloir                         ;??? ;LOBBY;64,92;Hall Ascenseurs
B6;B222;CIRCULATION;Couloir                         ;??? ;STAIR-06;25,19;Escalier 6
B6;B223;MEP;.;.;IDF ROOM;7,91;Local distribution courants faibles CfB102
B6;B224;MEP;.;.;ELEC;8,99;Tableau électrique  TEB102
B6;B225;MAIN KITCHEN;Cuisine                                        ;  ???? ;CAFETERIA;205,11;Cafeteria
B9;B226;AUDITORIUM;Centre de formation Continue;.;AUDITORIUM;168,04;Auditorium
B9;B227;AUDITORIUM;Centre de formation Continue;.;FEMALE WC;9,84;Sanitaires Femmes
B9;B228;AUDITORIUM;Centre de formation Continue;.;PROJECTION ROOM;7,31;Salle de projection
B9;B229;AUDITORIUM;Centre de formation Continue;.;MALE WC;9,19;Sanitaires Hommes
B9;B230;AUDITORIUM;Centre de formation Continue;.;STORE;13,48;Bureau
B9;B231;AUDITORIUM;Centre de formation Continue;.;STORE;13,53;Reserve
B9;B232;AUDITORIUM;Centre de formation Continue;.;HK;3,08;Local ménage
B9;B233;AUDITORIUM;Centre de formation Continue;.;RECEPTION / WAITING;167,71;Accueil
B9;B234;CIRCULATION;Couloir                         ;??? ;STAIR-09;24,50;Escalier 9
B9;B235;AUDITORIUM;Couloir                         ;??? ;CORRIDOR;14,24;Accès escalier de secours
B9;B236;AUDITORIUM;Centre de formation Continue;.;SEMINAR-1;42,68;Salle de séminaire A
B9;B237;AUDITORIUM;Centre de formation Continue;.;SEMINAR-2;42,00;Salle de séminaire B
B6;B238;MAIN KITCHEN;Cuisine                                        ;  ???? ;EXTERIOR DINING SPACE;108,44;Terrasse cafétéria
B3;B239;TELECOM;.;.;MTR ROOM;10,63;T
B2;B240;CIRCULATION;Couloir                         ;??? ;LIFT-02;4,00;Ascenseur visiteurs 2 (AL2)
B2;B241;CIRCULATION;Couloir                         ;??? ;LIFT-03;4,00;Ascenseur visiteurs 1 (AL3)
B4;B242;CIRCULATION;Couloir                         ;??? ;LIFT-04(GOOD'S LIFT);8,52;Monte charge sale 1 (AL5)
B4;B243;CIRCULATION;Couloir                         ;??? ;LIFT-05;8,16;Monte charge propre 1 (AL6)
B4;B244;CIRCULATION;Couloir                         ;??? ;LIFT-06;9,41;Monte malades 2 (AL7)
B4;B245;CIRCULATION;Couloir                         ;??? ;LIFT-07;9,41;Monte malades 3 (AL8)
B6;B246;CIRCULATION;Couloir                         ;??? ;LIFT-09;8,52;Monte charge sale 2 (AL9)
B6;B247;CIRCULATION;Couloir                         ;??? ;LIFT-10;8,16;Monte charge propre 2 (AL10)
B6;B248;CIRCULATION;Couloir                         ;??? ;LIFT-11;9,41;Monte malade 3 (AL11)
B6;B249;CIRCULATION;Couloir                         ;??? ;LIFT-12;9,41;Monte malade 4 (AL12)
B5;B250;MAIN KITCHEN;Cuisine                                        ;  ???? ;COLD KITCHEN;21,66;Cuisine Froide
B6;B251;CIRCULATION;Couloir                         ;??? ;CORRIDOR;93,38;.
B3;B252;WASTE MANAGEMENT;Gestion des déchets               ;?????? ??????;GENERAL WASTE WALK-IN REFRIGERATOR;20,35;Déchets Ménagers
B3;B253;WASTE MANAGEMENT;Gestion des déchets               ;?????? ??????;OFFICE;11,49;Bureau Gestion des déchets
B5;B254;MEP;.;.;ELEC.;.;Tableau électrique  TEB104
B3;B255;CSSD;Stérilisation                           ;????? ???????;DIRTY HOIST;4,20;Monte charge sale (AL13)
B1;B256;RADIO & CHEMO THERAPY;Radiothérapie                       ;???????? ???????;NURSE STATION;23,55;Poste infirmier
B5;B257;MEP;.;.;ELEC.;.;Tableau électrique  TEB105
B5;B258;MEP;.;.;ELEC.;.;Tableau électrique  TEB106
B1;B259;MEP;.;.;ELEC.;.;Tableau électrique  TEB107
B6;B260;MEP;.;.;ELEC.;.;Tableau électrique  TEB108
B5;B261;MEP;.;.;ELEC.;.;Tableau électrique  TEB109
B5;B262;CENTRAL STORE;Magasin central                 ;  ?????? ?????;OFFICE;9,89;Bureau
B3;B263;LINEN HOLDING;Lingerie;???? ???????;STAFF REST;8,05;Salle de repos
B3;B264;SEWING AND UNIFORM STORE;.;.;SECRETARY;4,32;.
B5;B265;MAIN KITCHEN;Cuisine                                        ;  ???? ;REFUSE HOLD;10,54;Denrées refusées
B5;B266;MAIN KITCHEN;Cuisine                                        ;  ???? ;DEEP FREEZE;10,91;Chambre froide négative -18
B5;B267;MAIN KITCHEN;Cuisine                                        ;  ???? ;F&V PREP.;27,21;Préparation Fruits et legumes
B5;B268;MAIN KITCHEN;Cuisine                                        ;  ???? ;LOCKER;4,18;Vestiaires
B5;B269;MAIN KITCHEN;Cuisine                                        ;  ???? ;CORRIDOR;6,06;Vestiaire sas Personnel
B5;B270;MAIN KITCHEN;Cuisine                                        ;  ???? ;CORRIDOR;5,92;Vestiaire sas Personnel
B5;B271;MAIN KITCHEN;Cuisine                                        ;  ???? ;LOCKER;2,81;.
B5;B272;MAIN KITCHEN;Cuisine                                        ;  ???? ;HK STORE;2,55;Local ménage
B4;B273;ENG. DEPT;Service ingénierie               ;   ????? ???????;OFFICE;6,76;Bureau
B4;B274;ENG. DEPT;Service ingénierie               ;   ????? ???????;OFFICE;6,42;Bureau
B4;B275;ENG. DEPT;Service ingénierie               ;   ????? ???????;STORE;13,00;Réserve
B4;B276;BIO MEDICAL DEPARTMENT;.;.;OFFICE;6,38;Bureau
B5;B277;MAIN KITCHEN;Cuisine                                        ;  ???? ;STAFF CHANGE;2,27;.
B5;B278;MAIN KITCHEN;Cuisine                                        ;  ???? ;STAFF CHANGE;2,27;.
B3;B279;TELECOM;.;.;GSM ROOM;10,48;Local GSM
B3;B280;CSSD;Stérilisation                           ;????? ???????;CLEAN HOIST;4,20;Monte charge stérile (AL14)
B5;B281;CENTRAL STORE;Magasin central                 ;  ?????? ?????;CORRIDOR;.;.
B1;B282;RT STAIR-01;.;.;LOBBY;.;Escalier de secours 1
B1;B283;RADIO & CHEMO THERAPY ;Radiothérapie                       ;???????? ???????;WC;.;Toilettes VIP
B1;B284;RADIO & CHEMO THERAPY ;Radiothérapie                       ;???????? ???????;STORE;.;Réserve
B1;B285;RADIO & CHEMO THERAPY ;Radiothérapie                       ;???????? ???????;SERVER;.;Salle informatique
B1;B286;RADIO & CHEMO THERAPY ;Radiothérapie                       ;???????? ???????;AIR LOCK/STORAGE;.;Sas Materiel
B1;B287;RADIO & CHEMO THERAPY ;Radiothérapie                       ;???????? ???????;INSTRUMENT PASSING;.;SAS Materiel
B1;B288;RADIO & CHEMO THERAPY ;Radiothérapie                       ;???????? ???????;CHANGE;.;Vestiaire Curiethérapie
B1;B289;MEP;.;.;ELEC.;.;Tableau électrique  TEB103
B1;B290;RADIO & CHEMO THERAPY ;Radiothérapie                       ;???????? ???????;CHANGE;.;?
B1;B291;RADIO & CHEMO THERAPY ;Radiothérapie                       ;???????? ???????;CHANGE;.;?
B1;B292;MORTUARY;Morgue                              ;?????? ???????;MORTUARY LOADING DOCK;.;Quai Morgue
B5;B293;MAIN KITCHEN;Cuisine                                        ;  ???? ;SERVICE YARD;.;Cour de service
B1;B294;MORTUARY;Morgue                              ;?????? ???????;SHOWER;.;Douche
B4;B295;MENS & WOMENS STAFF CHANGE & LOCKERS;Vestiaires                                ;??? ??????;DISABLE TOILET;.;Toilettes handicapés
B4;B296;MENS & WOMENS STAFF CHANGE & LOCKERS;Vestiaires                                ;??? ??????;TOILET ;.;Toilettes
B4;B297;MENS & WOMENS STAFF CHANGE & LOCKERS;Vestiaires                                ;??? ??????;TOILET ;.;Toilettes
B4;B298;MENS & WOMENS STAFF CHANGE & LOCKERS;Vestiaires                                ;??? ??????;TOILET ;.;Toilettes
B4;B300;MENS & WOMENS STAFF CHANGE & LOCKERS;Vestiaires                                ;??? ??????;TOILET ;.;Toilettes
B4;B301;MENS & WOMENS STAFF CHANGE & LOCKERS;Vestiaires                                ;??? ??????;SHOWER;.;Douches
B4;B302;MENS & WOMENS STAFF CHANGE & LOCKERS;Vestiaires                                ;??? ??????;SHOWER;.;Douches
B4;B303;MENS & WOMENS STAFF CHANGE & LOCKERS;Vestiaires                                ;??? ??????;SHOWER;.;Douches
B6;B304;MENS & WOMENS STAFF CHANGE & LOCKERS;Vestiaires                                ;??? ??????;SHOWER;.;Douches
B6;B305;MENS & WOMENS STAFF CHANGE & LOCKERS;Vestiaires                                ;??? ??????;SHOWER;.;Douches
B6;B306;MENS & WOMENS STAFF CHANGE & LOCKERS;Vestiaires                                ;??? ??????;SHOWER;.;Douches
B6;B307;MENS & WOMENS STAFF CHANGE & LOCKERS;Vestiaires                                ;??? ??????;TOILET ;.;Toilettes
B6;B308;MENS & WOMENS STAFF CHANGE & LOCKERS;Vestiaires                                ;??? ??????;TOILET ;.;Toilettes
B6;B309;MENS & WOMENS STAFF CHANGE & LOCKERS;Vestiaires                                ;??? ??????;TOILET ;.;Toilettes
B6;B310;MENS & WOMENS STAFF CHANGE & LOCKERS;Vestiaires                                ;??? ??????;TOILET ;.;Toilettes
B5;B311;PHARMACY;Pharmacie                                  ; ??????;SHOWER;.;Douche
B5;B312;PHARMACY;Pharmacie                                  ; ??????;SHOWER;.;Douche
B5;B313;PHARMACY;Pharmacie                                  ; ??????;CHANGE;.;Vestiaire
B5;B314;PHARMACY;Pharmacie                                  ; ??????;CHANGE;.;Vestiaire
B1;B315;CIRCULATION;Couloir                         ;??? ;COURTYARD BLOCK 1;.;.
B9;B316;CENTRAL STORE;Magasin central                 ;  ?????? ?????;W.C;.;Toileltte
B9;B317;CENTRAL STORE;Magasin central                 ;  ?????? ?????;W.C;.;Toileltte
B9;B318;AUDITORIUM;Centre de formation Continue;.;W.C;.;Toileltte
B9;B319;AUDITORIUM;Centre de formation Continue;.;W.C;.;Toileltte
B6;B320;MENS & WOMENS STAFF CHANGE & LOCKERS;Vestiaires                                ;??? ??????;DIS. TOILET LOBBY;.;Toilettes handicapés
B6;B321;MENS & WOMENS STAFF CHANGE & LOCKERS;Vestiaires                                ;??? ??????;DIS. TOILET  ;.;Toilettes handicapées
B9;B323;AUDITORIUM;Centre de formation Continue;.;STORE;.;Bureau"""







		locloc="""B1;F001;CARDIAC INVESTIGATION;Explorations cardiaques                                          ;  ????????? ?????;STAIR-1;29,07;Escalier 1
B1;F002;CARDIAC INVESTIGATION;Explorations cardiaques                                          ;  ????????? ?????;EQUIPMENT;19,63;Salle technique Angiographies
B1;F003;CARDIAC INVESTIGATION;Explorations cardiaques                                          ;  ????????? ?????;CARDIAC CATHETER LAB 1;40,26;Angiographie 1
B1;F004;CARDIAC INVESTIGATION;Explorations cardiaques                                          ;  ????????? ?????;CARDIAC CATHETER LAB 2;43,15;Angiographie 2
B1;F005;CARDIAC INVESTIGATION;Explorations cardiaques                                          ;  ????????? ?????;CORRIDOR;12,31;SAS Angiographie 2
B1;F006;CARDIAC INVESTIGATION;Explorations cardiaques                                          ;  ????????? ?????;CONTROL ROOM;22,22;Salle de contrôle
B1;F007;CARDIAC INVESTIGATION;Explorations cardiaques                                          ;  ????????? ?????;CORRIDOR;12,31;SAS Angiographie 2
B1;F008;CARDIAC INVESTIGATION;Explorations cardiaques                                          ;  ????????? ?????;SCRUB;2,58;.
B1;F009;CARDIAC INVESTIGATION;Explorations cardiaques                                          ;  ????????? ?????;CATH STORE;3,37;Réserve
B1;F010;CARDIAC INVESTIGATION;Explorations cardiaques                                          ;  ????????? ?????;SCRUB;2,51;.
B1;F011;CARDIAC INVESTIGATION;Explorations cardiaques                                          ;  ????????? ?????;PREPARATION;10,74;Préparation
B1;F012;CARDIAC INVESTIGATION;Explorations cardiaques                                          ;  ????????? ?????;PREPARATION;10,63;Attente patients couchés
B1;F013;CARDIAC INVESTIGATION;Explorations cardiaques                                          ;  ????????? ?????;MALE CHANGING RM ;4,73;Vestaire hommes
B1;F014;CARDIAC INVESTIGATION;Explorations cardiaques                                          ;  ????????? ?????;FEMALE CHANGING RM ;4,84;Vestiaire femmes
B1;F015;CARDIAC INVESTIGATION;Explorations cardiaques                                          ;  ????????? ?????;STAFF;5,48;Poste infirmiers
B1;F016;CARDIAC INVESTIGATION;Explorations cardiaques                                          ;  ????????? ?????;D/U;4,91;Local sale
B1;F017;INTENSIVE CARE UNIT;Réanimation Polyvalente                 ;       ??????? ??????? ?????? ???????;DR OFFICE ;13,77;Cadre Infirmier Réanimation polyvalente
B1;F018;INTENSIVE CARE UNIT;Réanimation Polyvalente                 ;       ??????? ??????? ?????? ???????;HEAD NURSE OFFICE ;11,25;Cadre Infirmier Réanimation Cardiaque
B1;F019;INTENSIVE CARE UNIT;Réanimation Polyvalente                 ;       ??????? ??????? ?????? ???????;OFFICE;15,53;Chef de Pôle Réanimation Urgences SAMU
B1;F020;CARDIAC INVESTIGATION;Explorations cardiaques                                          ;  ????????? ?????;MONITOR HOLTER;15,39;Holters tensionnel et rythmique
B1;F021;CARDIAC INVESTIGATION;Explorations cardiaques                                          ;  ????????? ?????;C/U;7,5;Local Propre
B1;F022;MEP;.;.;ELEC.;.;Tableau électrique TEL101
B1;F023;CARDIAC INVESTIGATION;Explorations cardiaques                                          ;  ????????? ?????;TROLLEY PARKING;14,57;Parking chariots
B1;F024;CARDIAC INVESTIGATION;Explorations cardiaques                                          ;  ????????? ?????;ECHOCARDIOGRAM;15,66;Echocardiographie
B1;F025;CARDIAC INVESTIGATION;Explorations cardiaques                                          ;  ????????? ?????;STRESS;15,66;Epreuve d'effort
B1;F026;CARDIAC INVESTIGATION;Explorations cardiaques                                          ;  ????????? ?????;ECG;15,69;Electrocardiographie
B1;F027;CARDIAC INVESTIGATION;Explorations cardiaques                                          ;  ????????? ?????;RECEPTION;16,60;Acceuil Secrétariat
B1;F028;CARDIAC INVESTIGATION;Explorations cardiaques                                          ;  ????????? ?????;ECHOCARDIOGRAM;15,66;Echocardiographie
B1;F029;CARDIAC INVESTIGATION;Explorations cardiaques                                          ;  ????????? ?????;WAITING ;53,76;Attente
B1;F030;CARDIAC INVESTIGATION;Explorations cardiaques                                          ;  ????????? ?????;DISABLED TOILET;5,25;Toilettes handicapés
B1;F031;CARDIAC INVESTIGATION;Explorations cardiaques                                          ;  ????????? ?????;TOILET;3,61;Toilettes
B1;F032;CARDIAC INVESTIGATION;Explorations cardiaques                                          ;  ????????? ?????;TOILET;3,61;Toilettes
B1;F033;CARDIAC INVESTIGATION;Explorations cardiaques                                          ;  ????????? ?????;VIP LIFT-01;.;Ascenseur VIP (AL1)
B1;F034;INTENSIVE CARE UNIT;Réanimation Polyvalente                 ;       ??????? ??????? ?????? ???????;PATIENT ROOM 1;19,65;Lit 1201
B1;F035;INTENSIVE CARE UNIT;Réanimation Polyvalente                 ;       ??????? ??????? ?????? ???????;PATIENT ROOM 2;19,85;Lit 1202
B1;F036;INTENSIVE CARE UNIT;Réanimation Polyvalente                 ;       ??????? ??????? ?????? ???????;PATIENT ROOM 3;24,76;Lit 1203
B1;F037;INTENSIVE CARE UNIT;Réanimation Polyvalente                 ;       ??????? ??????? ?????? ???????;PATIENT ROOM 4;24,55;Lit 1204
B1;F038;INTENSIVE CARE UNIT;Réanimation Polyvalente                 ;       ??????? ??????? ?????? ???????;PATIENT ROOM 5;24,50;Lit 1205
B1;F039;INTENSIVE CARE UNIT;Réanimation Polyvalente                 ;       ??????? ??????? ?????? ???????;PATIENT ROOM 6;25,05;Lit 1206
B1;F040;INTENSIVE CARE UNIT;Réanimation Polyvalente                 ;       ??????? ??????? ?????? ???????;STORE;11,72;Réserve
B1;F041;INTENSIVE CARE UNIT;Réanimation Polyvalente                 ;       ??????? ??????? ?????? ???????;CORRIDOR;100,18;Unité A
B1;F042;INTENSIVE CARE UNIT;Réanimation Polyvalente                 ;       ??????? ??????? ?????? ???????;DR OFFICE;16,72;Chef de pôle Cardiovasculaire
B1;F043;INTENSIVE CARE UNIT;Réanimation Polyvalente                 ;       ??????? ??????? ?????? ???????;MEETING ROOM;12,16;Salle de réunion
B1;F044;INTENSIVE CARE UNIT;Réanimation Polyvalente                 ;       ??????? ??????? ?????? ???????;LINEN;2,56;Lingerie
B1;F045;INTENSIVE CARE UNIT;Réanimation Polyvalente                 ;       ??????? ??????? ?????? ???????;H.K.;4,90;Local ménage
B1;F046;INTENSIVE CARE UNIT;Réanimation Polyvalente                 ;       ??????? ??????? ?????? ???????;D/U;10,02;local sale
B1;F047;INTENSIVE CARE UNIT;Réanimation Polyvalente                 ;       ??????? ??????? ?????? ???????;EQUIPMENT STORE;20,32;Réserve materiel
B1;F048;INTENSIVE CARE UNIT;Réanimation Polyvalente                 ;       ??????? ??????? ?????? ???????;DR OFFICE 2;8,79;Bureau médecin
B1;F049;MEP;.;.;ELEC.;.;Tableau électrique TEL102
B1;F050;INTENSIVE CARE UNIT;Réanimation Polyvalente                 ;       ??????? ??????? ?????? ???????;NURSE STATION;23,33;Poste Infirmier
B1;F051;INTENSIVE CARE UNIT;Réanimation Polyvalente                 ;       ??????? ??????? ?????? ???????;DR OFFICE 3;8,79;Bureau médecin
B1;F052;INTENSIVE CARE UNIT;Réanimation Polyvalente                 ;       ??????? ??????? ?????? ???????;C/U;16,64;Local propre
B1;F053;INTENSIVE CARE UNIT;Réanimation Polyvalente                 ;       ??????? ??????? ?????? ???????;MED;10,60;Pharmacie
B1;F054;INTENSIVE CARE UNIT;Réanimation Polyvalente                 ;       ??????? ??????? ?????? ???????;LINEN;3,64;Réserve
B1;F055;INTENSIVE CARE UNIT;Réanimation Polyvalente                 ;       ??????? ??????? ?????? ???????;TOILET;2,69;Toilettes
B1;F056;INTENSIVE CARE UNIT;Réanimation Polyvalente                 ;       ??????? ??????? ?????? ???????;TOILET;2,69;Toilettes
B1;F057;INTENSIVE CARE UNIT;Réanimation Polyvalente                 ;       ??????? ??????? ?????? ???????;STAFF REST;17,2;Salle de repos
B1;F058;INTENSIVE CARE UNIT;Réanimation Polyvalente                 ;       ??????? ??????? ?????? ???????;ON-CALL ROOM;14,51;Salle de garde médecins
B1;F059;INTENSIVE CARE UNIT;Réanimation Polyvalente                 ;       ??????? ??????? ?????? ???????;NURSE OFFICE;12,62;Secrétariat
B1;F060;INTENSIVE CARE UNIT;Réanimation Polyvalente                 ;       ??????? ??????? ?????? ???????;HEAD NURSE OFFICE;12,56;Bureau médecin
B3;F061;INTENSIVE CARE UNIT;Réanimation Polyvalente                 ;       ??????? ??????? ?????? ???????;CORRIDOR;29,15;.
B1;F062;INTENSIVE CARE UNIT;Réanimation Polyvalente                 ;       ??????? ??????? ?????? ???????;OFFICE;12,42;Bureau Médecin
B1;F063;INTENSIVE CARE UNIT;Réanimation Polyvalente                 ;       ??????? ??????? ?????? ???????;CORRIDOR;38,21;Couloir de visite - Issue de secours
B3;F064;INTENSIVE CARE UNIT;Réanimation Polyvalente                 ;       ??????? ??????? ?????? ???????;MALE CHANGING RM ;10,33;Vestiaire hommes
B3;F065;INTENSIVE CARE UNIT;Réanimation Polyvalente                 ;       ??????? ??????? ?????? ???????;CORRIDOR;73,70;Reanimation Polyvalente et Bloc opératoire Accès réservé
B3;F066;INTENSIVE CARE UNIT;Réanimation Polyvalente                 ;       ??????? ??????? ?????? ???????;FEMALE CHANGING RM ;10,33;Vestiaire femmes
B3;F067;INTENSIVE CARE UNIT;Réanimation Polyvalente                 ;       ??????? ??????? ?????? ???????;LIFT-13;.;Monte malades urgences (AL13)
B3;F068;INTENSIVE CARE UNIT;Réanimation Polyvalente                 ;       ??????? ??????? ?????? ???????;LOBBY;10,76;.
B3;F069;MEP;.;.;ELEC.;.;Tableau électrique TEL103
B3;F070;INTENSIVE CARE UNIT;Réanimation Polyvalente                 ;       ??????? ??????? ?????? ???????;STERILE STORE;15,32;Réserve stérile
B3;F071;INTENSIVE CARE UNIT;Réanimation Polyvalente                 ;       ??????? ??????? ?????? ???????;STAIR-2;30,51;Escalier 2
B3;F072;INTENSIVE CARE UNIT;Réanimation Polyvalente                 ;       ??????? ??????? ?????? ???????;EMERGENCY ESCAPE;109,13;Couloir de visite réanimation polyvalente
B3;F073;INTENSIVE CARE UNIT;Réanimation Polyvalente                 ;       ??????? ??????? ?????? ???????;PATIENT ROOM 7;20,47;Lit 1207
B3;F074;INTENSIVE CARE UNIT;Réanimation Polyvalente                 ;       ??????? ??????? ?????? ???????;PATIENT ROOM 8;19,92;Lit 1208
B3;F075;INTENSIVE CARE UNIT;Réanimation Polyvalente                 ;       ??????? ??????? ?????? ???????;PATIENT ROOM 9;19,73;Lit 1209
B3;F076;INTENSIVE CARE UNIT;Réanimation Polyvalente                 ;       ??????? ??????? ?????? ???????;PATIENT ROOM 10;20,39;Lit 1210
B3;F077;INTENSIVE CARE UNIT;Réanimation Polyvalente                 ;       ??????? ??????? ?????? ???????;PATIENT ROOM 11;20,11;Lit 1211
B3;F078;INTENSIVE CARE UNIT;Réanimation Polyvalente                 ;       ??????? ??????? ?????? ???????;PATIENT ROOM 12;20,16;Lit 1212
B3;F079;INTENSIVE CARE UNIT;Réanimation Polyvalente                 ;       ??????? ??????? ?????? ???????;CORRIDOR;62,58;Unité B
B3;F080;INTENSIVE CARE UNIT;Réanimation Polyvalente                 ;       ??????? ??????? ?????? ???????;LINEN;2,63;Lingerie
B3;F081;INTENSIVE CARE UNIT;Réanimation Polyvalente                 ;       ??????? ??????? ?????? ???????;HK;3,4;Local ménage
B3;F082;INTENSIVE CARE UNIT;Réanimation Polyvalente                 ;       ??????? ??????? ?????? ???????;D/U;6,79;local sale
B3;F083;INTENSIVE CARE UNIT;Réanimation Polyvalente                 ;       ??????? ??????? ?????? ???????;CORRIDOR;65,14;.
B3;F084;INTENSIVE CARE UNIT;Réanimation Polyvalente                 ;       ??????? ??????? ?????? ???????;EQUIPMENT STORE;16,37;Reserve materiel
B3;F085;INTENSIVE CARE UNIT;Réanimation Polyvalente                 ;       ??????? ??????? ?????? ???????;DR OFFICE ;8,95;Bureau médecin
B3;F086;INTENSIVE CARE UNIT;Réanimation Polyvalente                 ;       ??????? ??????? ?????? ???????;STORE;9,11;Réserve
B3;F087;INTENSIVE CARE UNIT;Réanimation Polyvalente                 ;       ??????? ??????? ?????? ???????;NURSE STATION;21,06;Poste infirmier
B3;F088;INTENSIVE CARE UNIT;Réanimation Polyvalente                 ;       ??????? ??????? ?????? ???????;C/U;11,13;Local propre
B3;F089;INTENSIVE CARE UNIT;Réanimation Polyvalente                 ;       ??????? ??????? ?????? ???????;MEDICATION;5,53;Pharmacie
B1;F090;CORONARY CARE UNIT;Réanimation cardiaque;   ???? ???? ??????? ??????;HEAD NURSE OFFICE;12,25;Bureau
B1;F091;CORONARY CARE UNIT;Réanimation cardiaque;   ???? ???? ??????? ??????;HK;6,24;Local ménage
B1;F092;CORONARY CARE UNIT;Réanimation cardiaque;   ???? ???? ??????? ??????;DR OFFICE ;19,37;Bureau médecin
B1;F093;CORONARY CARE UNIT;Réanimation cardiaque;   ???? ???? ??????? ??????;STAFF REST;9,67;Salle de repos
B1;F094;CORONARY CARE UNIT;Réanimation cardiaque;   ???? ???? ??????? ??????;SECRETARY;13,15;Secrétariat
B1;F095;MEP;.;.;ELEC.;.;Tableau électrique TEL104
B1;F096;CORONARY CARE UNIT;Réanimation cardiaque;   ???? ???? ??????? ??????;PATIENT ROOM 5;23,69;Lit 141
B1;F097;CORONARY CARE UNIT;Réanimation cardiaque;   ???? ???? ??????? ??????;PATIENT ROOM 4;24,2;lit 142
B1;F098;CORONARY CARE UNIT;Réanimation cardiaque;   ???? ???? ??????? ??????;PATIENT ROOM 3;24,73;Lit 143
B1;F099;CORONARY CARE UNIT;Réanimation cardiaque;   ???? ???? ??????? ??????;PATIENT ROOM 2;19,23;Lit 144
B1;F100;CORONARY CARE UNIT;Réanimation cardiaque;   ???? ???? ??????? ??????;PATIENT ROOM 1;19,37;Lit 145
B1;F101;CORONARY CARE UNIT;Réanimation cardiaque;   ???? ???? ??????? ??????;CORRIDOR;73,92;.
B1;F102;CORONARY CARE UNIT;Réanimation cardiaque;   ???? ???? ??????? ??????;WAITING ;10,33;Attente Réanimation Cardiaque
B1;F103;CORONARY CARE UNIT;Réanimation cardiaque;   ???? ???? ??????? ??????;INTERVIEW/MEETING ROOM;10,84;Salle d'entretien
B1;F104;CORONARY CARE UNIT;Réanimation cardiaque;   ???? ???? ??????? ??????;ON-CALL ROOM;16,13;Salle de garde
B1;F105;CORONARY CARE UNIT;Réanimation cardiaque;   ???? ???? ??????? ??????;WC;2,13;Toilettes
B1;F106;CORONARY CARE UNIT;Réanimation cardiaque;   ???? ???? ??????? ??????;HK;3,15;Local ménage
B1;F107;CORONARY CARE UNIT;Réanimation cardiaque;   ???? ???? ??????? ??????;C/U;4,81;Local propre
B1;F108;CORONARY CARE UNIT;Réanimation cardiaque;   ???? ???? ??????? ??????;D/U;7,66;Local sale
B1;F109;CORONARY CARE UNIT;Réanimation cardiaque;   ???? ???? ??????? ??????;NURSE STATION;21,87;Poste infirmier
B1;F110;CORONARY CARE UNIT;Réanimation cardiaque;   ???? ???? ??????? ??????;MED.;6,74;Pharmacie
B1;F111;CORONARY CARE UNIT;Réanimation cardiaque;   ???? ???? ??????? ??????;LINEN;3,31;Lingerie
B1;F112;CORONARY CARE UNIT;Réanimation cardiaque;   ???? ???? ??????? ??????;NURSES OFFICE;8,71;Cadre infirmier
B1;F113;CORONARY CARE UNIT;Réanimation cardiaque;   ???? ???? ??????? ??????;STERILE SUPPLIES;11,60;Materiel stérile
B1;F114;CORONARY CARE UNIT;Réanimation cardiaque;   ???? ???? ??????? ??????;STORE;20,29;Réserve
B1;F115;CORONARY CARE UNIT;Réanimation cardiaque;   ???? ???? ??????? ??????;CORRIDOR;126,69;.
B1;F116;CORONARY CARE UNIT;Réanimation cardiaque;   ???? ???? ??????? ??????;FEMALE CHANGING RM;15,64;Vestiaire femmes
B1;F117;CORONARY CARE UNIT;Réanimation cardiaque;   ???? ???? ??????? ??????;MALE CHANGING RM;13,36;Vestiaire hommes
B1;F118;CORONARY CARE UNIT;Réanimation cardiaque;   ???? ???? ??????? ??????;PANTRY;12,00;Office
B3;F119;OPERATING THEATERS ;Bloc opératoire                                                    ;  ???? ?????  ;CORRIDOR;4,50;Vestiaire femmes
B3;F120;OPERATING THEATERS ;Bloc opératoire                                                    ;  ???? ?????  ;T/S;4,53;Toilettes
B3;F121;OPERATING THEATERS ;Bloc opératoire                                                    ;  ???? ?????  ;FEMALE STAFF;17,38;Vestiaire femmes
B3;F122;OPERATING THEATERS ;Bloc opératoire                                                    ;  ???? ?????  ;MALE STAFF;10,24;Vestiaire hommes
B3;F123;OPERATING THEATERS ;Bloc opératoire                                                    ;  ???? ?????  ;T/S;3,37;Toilettes
B3;F124;OPERATING THEATERS ;Bloc opératoire                                                    ;  ???? ?????  ;CORRIDOR;4,27;Vestiaire hommes
B3;F125;OPERATING THEATERS ;Bloc opératoire                                                    ;  ???? ?????  ;CORRIDOR;4,58;Vestiaire médecins femmes
B3;F126;OPERATING THEATERS ;Bloc opératoire                                                    ;  ???? ?????  ;FEMALE DRS;12,05;Vestiaire médecins femmes
B3;F127;OPERATING THEATERS ;Bloc opératoire                                                    ;  ???? ?????  ;T/S;4,53;Toilettes
B3;F128;OPERATING THEATERS ;Bloc opératoire                                                    ;  ???? ?????  ;WC/S;4,90;Toilettes
B3;F129;OPERATING THEATERS ;Bloc opératoire                                                    ;  ???? ?????  ;CORRIDOR;5,34;Vestiaire médecins hommes
B3;F130;OPERATING THEATERS ;Bloc opératoire                                                    ;  ???? ?????  ;MALE DRS;23,88;Vestiaire médecins hommes
B5;F131;OPERATING THEATERS ;Bloc opératoire                                                    ;  ???? ?????  ;DR LOUNGE;17,33;Salle de repos médecins
B3;F132;OPERATING THEATERS ;Bloc opératoire                                                    ;  ???? ?????  ;BOOTS;23,63;.
B3;F133;OPERATING THEATERS ;Bloc opératoire                                                    ;  ???? ?????  ;STAFF LOUNGE;13,21;salle de repos personnel
B3;F134;MEP;.;.;ELEC.;.;Tableau électrique TEL105
B3;F135;OPERATING THEATERS ;Bloc opératoire                                                    ;  ???? ?????  ;TRANSFER LOBBY;25,73;Hall transfert patients
B3;F136;OPERATING THEATERS ;Bloc opératoire                                                    ;  ???? ?????  ;RECOVERY;109,38;Salle de surveillance post interventionnelle
B3;F137;OPERATING THEATERS ;Bloc opératoire                                                    ;  ???? ?????  ;NURSE STATION;5,68;Poste infirmier
B3;F138;OPERATING THEATERS ;Bloc opératoire                                                    ;  ???? ?????  ;BED PARK;17,25;Parking lits
B5;F139;OPERATING THEATERS ;Bloc opératoire                                                    ;  ???? ?????  ;TROLLEY WIPE;9,68;Désinfection chariots
B3;F140;OPERATING THEATERS ;Bloc opératoire                                                    ;  ???? ?????  ;TROLLEY PARK;8,82;Parking chariots
B5;F143;OPERATING THEATERS ;Bloc opératoire                                                    ;  ???? ?????  ;OT TRANSFER;36,85;.
B3;F144;OPERATING THEATERS ;Bloc opératoire                                                    ;  ???? ?????  ;C/U;15,41;Local propre
B3;F145;OPERATING THEATERS ;Bloc opératoire                                                    ;  ???? ?????  ;HK;10,89;Local ménage
B5;F146;OPERATING THEATERS ;Bloc opératoire                                                    ;  ???? ?????  ;DR OFFICE;10,66;Bureau Médecin
B3;F147;OPERATING THEATERS ;Bloc opératoire                                                    ;  ???? ?????  ;NURSE OFFICE;10,66;Cadre infirmier
B3;F148;OPERATING THEATERS ;Bloc opératoire                                                    ;  ???? ?????  ;NURSE STATION;6,45;Contrôle secrétariat
B3;F149;OPERATING THEATERS ;Bloc opératoire                                                    ;  ???? ?????  ;CLEAN HOIST;.;Monte charge stérile (AL14)
B3;F150;OPERATING THEATERS ;Bloc opératoire                                                    ;  ???? ?????  ;DIRTY HOIST;.;Monte charge sale (AL13)
B3;F151;OPERATING THEATERS ;Bloc opératoire                                                    ;  ???? ?????  ;DISP.;4,6;Local poubelles
B3;F152;OPERATING THEATERS ;Bloc opératoire                                                    ;  ???? ?????  ;STERILE STORE;57,36;Arsenal stérile
B3;F153;OPERATING THEATERS ;Bloc opératoire                                                    ;  ???? ?????  ;OB/GYN OPERATING THEATRE 7;69,2;Salle 7
B3;F154;OPERATING THEATERS ;Bloc opératoire                                                    ;  ???? ?????  ;PARK EQUIPMENT;8,73;Parking matériel
B3;F155;OPERATING THEATERS ;Bloc opératoire                                                    ;  ???? ?????  ;EQUIPMENT STORE;20,64;Réserve matériel
B3;F156;INTENSIVE CARE UNIT;Réanimation Polyvalente                 ;       ??????? ??????? ?????? ???????;PHARMACY;21,58;Pharmacie Réanimation Bloc Opératoire
B3;F157;INTENSIVE CARE UNIT;Réanimation Polyvalente                 ;       ??????? ??????? ?????? ???????;CORRIDOR;13,13;.
B3;F158;OPERATING THEATERS ;Bloc opératoire                                                    ;  ???? ?????  ;STORE;3,42;Réserve
B3;F159;OPERATING THEATERS ;Bloc opératoire                                                    ;  ???? ?????  ;NEURO,ORTHO,TRAUMA OPERATING THEATRE 1;53,55;Salle 1
B3;F160;OPERATING THEATERS ;Bloc opératoire                                                    ;  ???? ?????  ;SCRUB;6,27;.
B3;F161;OPERATING THEATERS ;Bloc opératoire                                                    ;  ???? ?????  ;DISPOSAL;6,86;Local poubelles
B3;F162;OPERATING THEATERS ;Bloc opératoire                                                    ;  ???? ?????  ;ENT,OPHTHALMIC OPERATING THEATRE 2;.;Salle 2
B3;F163;OPERATING THEATERS ;Bloc opératoire                                                    ;  ???? ?????  ;SCRUB;7,90;.
B5;F164;OPERATING THEATERS ;Bloc opératoire                                                    ;  ???? ?????  ;DISPOSAL;4,00;Local poubelles
B5;F165;OPERATING THEATERS ;Bloc opératoire                                                    ;  ???? ?????  ;CARDIO VASCULAR OPERATING THEATRE 3;48,65;Salle 3
B5;F166;OPERATING THEATERS ;Bloc opératoire                                                    ;  ???? ?????  ;SCRUB;6,82;.
B5;F167;OPERATING THEATERS ;Bloc opératoire                                                    ;  ???? ?????  ;STORE;3,54;Réserve
B5;F168;OPERATING THEATERS ;Bloc opératoire                                                    ;  ???? ?????  ;STORE;3,54;Réserve
B5;F169;OPERATING THEATERS ;Bloc opératoire                                                    ;  ???? ?????  ;GENERAL OPERATING THEATRE 4;50,66;Salle 4
B5;F170;OPERATING THEATERS ;Bloc opératoire                                                    ;  ???? ?????  ;SCRUB;6,56;.
B5;F171;OPERATING THEATERS ;Bloc opératoire                                                    ;  ???? ?????  ;DISPOSAL;6,65;Salle de consultation 2
B5;F172;OPERATING THEATERS ;Bloc opératoire                                                    ;  ???? ?????  ;GENERAL OPERATING THEATRE 5;50,66;Salle 5
B5;F173;OPERATING THEATERS ;Bloc opératoire                                                    ;  ???? ?????  ;SCRUB;5,36;.
B5;F174;OPERATING THEATERS ;Bloc opératoire                                                    ;  ???? ?????  ;STORE;4,83;Réserve
B5;F175;MEP;.;.;ELEC.;.;Tableau électrique TEL106
B5;F176;CIRCULATION;Couloir                         ;??? ;TROLLEY PARKING;2,16;Parking Chariots
B5;F177;OPERATING THEATERS ;Bloc opératoire                                                    ;  ???? ?????  ;GENERAL OPERATING THEATRE 6;42,02;Salle 6
B5;F179;OPERATING THEATERS ;Bloc opératoire                                                    ;  ???? ?????  ;DISPOSAL;6,44;Salle de consultation 2
B5;F180;OPERATING THEATERS ;Bloc opératoire                                                    ;  ???? ?????  ;SCRUB;1,37;.
B5;F181;OPERATING THEATERS ;Bloc opératoire                                                    ;  ???? ?????  ;STERILE SUPPLIES;26,20;Consommable stérile
B5;F182;OPERATING THEATERS ;Bloc opératoire                                                    ;  ???? ?????  ;PERFUSSION;15,05;Solutés
B5;F183;OPERATING THEATERS ;Bloc opératoire                                                    ;  ???? ?????  ;ANESTHESIA;14,40;Bureau anesthésistes
B5;F184;OPERATING THEATERS ;Bloc opératoire                                                    ;  ???? ?????  ;STORE;9,45;Réserve
B5;F185;OPERATING THEATERS ;Bloc opératoire                                                    ;  ???? ?????  ;OFFICE;7,84;Bureau
B5;F186;OPERATING THEATERS ;Bloc opératoire                                                    ;  ???? ?????  ;CORRIDOR;215,1;.
B5;F187;DELIVERY SUITE ;Salles d'accouchement          ;  ??? ??????? ;STORAGE;15,76;Réserve
B5;F188;DELIVERY SUITE ;Salles d'accouchement          ;  ??? ??????? ;LABOUR/DELIVERY;25,75;Salle d'accouchement 3
B5;F189;DELIVERY SUITE ;Salles d'accouchement          ;  ??? ??????? ;LABOUR/DELIVERY;25,37;Salle d'accouchement 2
B5;F190;DELIVERY SUITE ;Salles d'accouchement          ;  ??? ??????? ;LABOUR/DELIVERY;25,62;Salle d'accouchement 1
B5;F191;DELIVERY SUITE ;Salles d'accouchement          ;  ??? ??????? ;LINEN;2,46;Lingerie
B5;F192;DELIVERY SUITE ;Salles d'accouchement          ;  ??? ??????? ;CORRIDOR;50,14;.
B5;F193;MEP;.;.;ELEC.;.;Tableau électrique TEL107
B5;F194;OPERATING THEATERS ;Salles d'accouchement          ;  ??? ??????? ;INFECTIONS O.R. 8;48,5;Salle 8: Septique
B5;F195;DELIVERY SUITE ;Salles d'accouchement          ;  ??? ??????? ;SCRUB;1,13;.
B5;F196;DELIVERY SUITE ;Salles d'accouchement          ;  ??? ??????? ;SCRUB UP;9,13;.
B5;F197;DELIVERY SUITE ;Salles d'accouchement          ;  ??? ??????? ;DISP.;5,17;Local poubelles
B5;F198;DELIVERY SUITE ;Salles d'accouchement          ;  ??? ??????? ;NURSE STATION;6,05;Poste infirmier
B5;F199;DELIVERY SUITE ;Salles d'accouchement          ;  ??? ??????? ;C/U;8,68;Local propre
B5;F200;DELIVERY SUITE ;Salles d'accouchement          ;  ??? ??????? ;D/U DISPOSAL;6,08;Local sale
B5;F201;DELIVERY SUITE ;Salles d'accouchement          ;  ??? ??????? ;WAITING;19,9;Attente
B5;F202;DELIVERY SUITE ;Salles d'accouchement          ;  ??? ??????? ;WC;2,47;Toilettes
B5;F203;DELIVERY SUITE ;Salles d'accouchement          ;  ??? ??????? ;WC;2,54;Toilettes
B5;F204;CIRCULATION;Couloir                         ;??? ;CORRIDOR;89,93;.
B5;F205;NEONATAL UNIT;Réanimation Néo-natale            ;   ??????? ??????? ?????? ???????;BABY FEED ROOM;11,01;Biberonnerie
B5;F206;NEONATAL UNIT;Soins intensifs neo-nataux                    ;   ??????? ??????? ?????? ???????;DR OFFICE;11,36;Bureau médecin
B5;F207;NEONATAL UNIT;Réanimation Néo-natale            ;   ??????? ??????? ?????? ???????;MOTHERS WAITING;10,15;Attente méres
B5;F208;NEONATAL UNIT;Réanimation Néo-natale            ;   ??????? ??????? ?????? ???????;INTERVIEW;8,29;Salle d'entretiens
B5;F209;NEONATAL UNIT;Réanimation Néo-natale            ;   ??????? ??????? ?????? ???????;NURSE OFFICE;8,96;Cadre infirmier
B5;F210;MEP;.;.;ELEC.;.;Tableau électrique TEL108
B5;F211;NEONATAL UNIT;Réanimation Néo-natale            ;   ??????? ??????? ?????? ???????;CORRIDOR;68,19;.
B5;F212;NEONATAL UNIT;Réanimation Néo-natale            ;   ??????? ??????? ?????? ???????;PATIENT CUB 5&6;26,1;.
B5;F213;VIEWING GALLERY ;.;.;VIEWING GALLERY;107,19;Couloir de visite réanimation néo-natale et isolement
B5;F214;NEONATAL UNIT;Réanimation Néo-natale            ;   ??????? ??????? ?????? ???????;PATIENT CUB 3&4;26,01;.
B5;F215;NEONATAL UNIT;Réanimation Néo-natale            ;   ??????? ??????? ?????? ???????;NURSE STATION;14,91;Poste infirmier
B5;F216;NEONATAL UNIT;Réanimation Néo-natale            ;   ??????? ??????? ?????? ???????;EQUIPMENT STORE;8,32;Réserve matériel
B5;F217;NEONATAL UNIT;Réanimation Néo-natale            ;   ??????? ??????? ?????? ???????;C/U;6,38;Local propre
B5;F218;NEONATAL UNIT;Réanimation Néo-natale            ;   ??????? ??????? ?????? ???????;D/U;8,12;Local sale
B5;F219;NEONATAL UNIT;Réanimation Néo-natale            ;   ??????? ??????? ?????? ???????;PATIENT CUB 1&2;26,49;.
B5;F220;NEONATAL UNIT;Réanimation Néo-natale            ;   ??????? ??????? ?????? ???????;ISOLATION 8;14,88;.
B5;F221;NEONATAL UNIT;Réanimation Néo-natale            ;   ??????? ??????? ?????? ???????;ISOLATION 7;14,72;.
B5;F223;NEONATAL UNIT;Réanimation Néo-natale            ;   ??????? ??????? ?????? ???????;ANTE;8,46;Sas Isolement
B5;F224;NEONATAL UNIT;Réanimation Néo-natale            ;   ??????? ??????? ?????? ???????;LINEN;2,48;Lingerie
B5;F225;NEONATAL UNIT;Réanimation Néo-natale            ;   ??????? ??????? ?????? ???????;HK;1,73;.
B5;F226;NEONATAL UNIT;Réanimation Néo-natale            ;   ??????? ??????? ?????? ???????;STAFF REST;13,5;Salle de repos
B5;F227;NEONATAL UNIT;Réanimation Néo-natale            ;   ??????? ??????? ?????? ???????;FEMALE CHANGE;9,49;Vestiaire femmes
B5;F228;NEONATAL UNIT;Réanimation Néo-natale            ;   ??????? ??????? ?????? ???????;M LOCKERS;6,19;Vestiaires hommes
B5;F229;NEONATAL UNIT;Réanimation Néo-natale            ;   ??????? ??????? ?????? ???????;WC;1,90;Toilettes
B5;F230;NEONATAL UNIT;Réanimation Néo-natale            ;   ??????? ??????? ?????? ???????;WC;1,90;Toilettes
B5;F231;NEONATAL UNIT;Réanimation Néo-natale            ;   ??????? ??????? ?????? ???????;CORRIDOR;3,87;Vestiaire femmes
B5;F232;BURNS UNIT;Service des grands brulés                         ; ???? ???? ????????;F. CHANGE;7,95;Vestiaire femmes
B5;F233;BURNS UNIT;Service des grands brulés                         ; ???? ???? ????????;M. CHANGE;6,63;Vestiaire hommes
B5;F234;BURNS UNIT;Service des grands brulés                         ; ???? ???? ????????;EQUIPMENT STORE;10,87;Réserve
B5;F235;BURNS UNIT;Service des grands brulés                         ; ???? ???? ????????;ROOM 1;20,23;Lit 1401
B5;F236;BURNS UNIT;Service des grands brulés                         ; ???? ???? ????????;ROOM 2;20,23;Lit 1402
B5;F237;BURNS UNIT;Service des grands brulés                         ; ???? ???? ????????;NURSE STATION;10,97;Poste infirmier
B5;F238;BURNS UNIT;Service des grands brulés                         ; ???? ???? ????????;STAFF CH.WC;4,79;Toilettes
B5;F239;BURNS UNIT;Service des grands brulés                         ; ???? ???? ????????;STAFF CH.WC;4,79;Toilettes
B5;F240;BURNS UNIT;Service des grands brulés                         ; ???? ???? ????????;D/U;8,93;Local Sale
B5;F242;CIRCULATION;Couloir                         ;??? ;CORRIDOR;17,33;.
B5;F243;BURNS UNIT;Service des grands brulés                         ; ???? ???? ????????;LINEN;5,45;Lingerie
B5;F244;BURNS UNIT;Service des grands brulés                         ; ???? ???? ????????;DR OFFICE;9,31;Bureau 
B5;F245;BURNS UNIT;Service des grands brulés                         ; ???? ???? ????????;CORRIDOR;59,66;.
B5;F247;BURNS UNIT;Service des grands brulés                         ; ???? ???? ????????;ROOM 3;20,23;Lit 1403
B5;F248;BURNS UNIT;Service des grands brulés                         ; ???? ???? ????????;ROOM 4;20,34;Lit 1404
B5;F249;BURNS UNIT;Service des grands brulés                         ; ???? ???? ????????;C/U;5,36;Local propre
B5;F250;BURNS UNIT;Service des grands brulés                         ; ???? ???? ????????;CLINICAL BAY/TREATMENT;28,83;Salle de soins stérile
B5;F251;CIRCULATION;Couloir                         ;??? ;CORRIDOR;24,11;.
B5;F252;BURNS UNIT;Service des grands brulés                         ; ???? ???? ????????;STAIR 3;29,76;Escalier 3
B6;F253;STAFF FACILITIES & CLINICAL ADMIN;Espace réservé au personnel                          ; ???? ???  ????? ????????;OFFICE;12,96;Bureau médecins
B6;F254;STAFF FACILITIES & CLINICAL ADMIN;Espace réservé au personnel                          ; ???? ???  ????? ????????;OFFICE;10,08;Bureau médecins
B6;F255;STAFF FACILITIES & CLINICAL ADMIN;Espace réservé au personnel                          ; ???? ???  ????? ????????;OFFICE;10,24;Chef de Pôle Mère Enfant
B6;F256;STAFF FACILITIES & CLINICAL ADMIN;Espace réservé au personnel                          ; ???? ???  ????? ????????;CORRIDOR;20,31;Zone médecins Pôle Mère Enfant
B6;F257;STAFF FACILITIES & CLINICAL ADMIN;Espace réservé au personnel                          ; ???? ???  ????? ????????;CORRIDOR;15,48;Accès réservé
B6;F258;STAFF FACILITIES & CLINICAL ADMIN;Espace réservé au personnel                          ; ???? ???  ????? ????????;SECRETARY;9,76;Secrétariat Pôle Mere Enfants
B6;F259;STAFF FACILITIES & CLINICAL ADMIN;Espace réservé au personnel                          ; ???? ???  ????? ????????;OFFICE;9,61;Chef de Pôle Explorations
B6;F260;STAFF FACILITIES & CLINICAL ADMIN;Espace réservé au personnel                          ; ???? ???  ????? ????????;OFFICE;12,1;Chef de Pôle Ambulatoire
B6;F261;HOSPITAL STREET;Couloir médical                    ;??? ?????;STAIR 6;25,19;Escalier 6
B6;F262;MEP;.;.;ELEC.;9,14;Tableau électrique TEL109
B6;F263;MEP;.;.;IDF ROOM;7,91;Local distribution courants faibles CfL101
B6;F264;CIRCULATION;Couloir                         ;??? ;LIFT LOBBY;48,27;Hall Ascenseurs
B6;F265;HOSPITAL STREET;Couloir médical                    ;??? ?????;LIFT-09;.;Monte charge sale 2 (AL9)
B6;F266;HOSPITAL STREET;Couloir médical                    ;??? ?????;LIFT-10;.;Monte charge propre 2 (AL10)
B6;F267;HOSPITAL STREET;Couloir médical                    ;??? ?????;LIFT-11;.;Monte malade 3 (AL11)
B6;F268;HOSPITAL STREET;Couloir médical                    ;??? ?????;LIFT-12;.;Monte malade 4 (AL12)
B6;F269;CIRCULATION;Couloir                         ;??? ;CORRIDOR;83,00;Couloir visiteurs
B6;F270;OPD;Hopital de jour chirurgie;?????? ?????? ;LINEN;4,14;Lingerie
B6;F271;OPD;Hopital de jour chirurgie;?????? ?????? ;HK;2,06;Local ménage
B6;F272;OPD;Hopital de jour chirurgie;?????? ?????? ;STAFF WC;5,17;Toilettes personnel
B6;F273;CIRCULATION;Couloir                         ;??? ;CORRIDOR;119,29;Couloir médical
B6;F274;OPD;Hopital de jour chirurgie;?????? ?????? ;PAT WC;4,91;Toilettes patients
B6;F275;OPD;Hopital de jour chirurgie;?????? ?????? ;PAT WC;4,91;Toilettes patients
B6;F276;OPD;Hopital de jour chirurgie;?????? ?????? ;DR OFFICE/EXAM RM;10,19;Bureau médecin
B4;F277;OPD;Hopital de jour chirurgie;?????? ?????? ;NURSE STATION;7,45;Poste infirmier
B6;F278;OPD;Hopital de jour chirurgie;?????? ?????? ;CORRIDOR;63,8;.
B6;F279;OPD;Hopital de jour chirurgie;?????? ?????? ;PATIENT ROOM 1;13,72;Lit 1801
B6;F280;OPD;Hopital de jour chirurgie;?????? ?????? ;PATIENT ROOM 2;13,72;Lit 1802
B6;F281;OPD;Hopital de jour chirurgie;?????? ?????? ;PATIENT ROOM 3;13,72;Lit 1803
B6;F282;OPD;Hopital de jour chirurgie;?????? ?????? ;PATIENT ROOM 4;13,72;Lit 1804
B6;F283;OPD;Hopital de jour chirurgie;?????? ?????? ;PATIENT ROOM 5;13,72;Lit 1805
B6;F284;OPD;Hopital de jour chirurgie;?????? ?????? ;PATIENT ROOM 6;12,33;Lit 1806
B6;F285;OPD;Hopital de jour chirurgie;?????? ?????? ;PATIENT ROOM 7;13,67;Lit 1807
B4;F286;OPD;Hopital de jour chirurgie;?????? ?????? ;D/U   ;5,51;Local sale
B4;F287;OPD;Hopital de jour chirurgie;?????? ?????? ;C/U;7,17;Local propre
B4;F288;CIRCULATION;Couloir                         ;??? ;PAT. WC ;4,52;Toilettes
B4;F289;CIRCULATION;Couloir                         ;??? ;PAT. WC ;4,52;Toilettes
B4;F290;HOSPITAL STREET;Couloir médical                    ;??? ?????;STAIR 5;25,87;Escalier 5
B4;F291;OPD;Hopital de jour chirurgie;?????? ?????? ;WC;3,82;Toilettes
B6;F292;OPD;Hopital de jour chirurgie;?????? ?????? ;WAITING;5,97;Attente
B6;F293;MEP;.;.;ELEC.;.;Tableau électrique TEL110
B4;F296;CIRCULATION;Couloir                         ;??? ;CORRIDOR;93,16;Couloir médical
B4;F297;CIRCULATION;Couloir                         ;??? ;LIFT LOBBY;55,64;Hall ascenseurs
B6;F298;CIRCULATION;Couloir                         ;??? ;CORRIDOR;81,04;Couloir visiteurs
B4;F299;HOSPITAL STREET;Couloir médical                    ;??? ?????;LIFT-07;.;Monte malades 3 (AL8)
B4;F300;HOSPITAL STREET;Couloir médical                    ;??? ?????;LIFT-06;.;Monte malades 2 (AL7)
B4;F301;HOSPITAL STREET;Couloir médical                    ;??? ?????;LIFT-05;.;Monte charge propre 1 (AL6)
B4;F302;HOSPITAL STREET;Couloir médical                    ;??? ?????;LIFT-04(GOOD'S LIFT);.;Monte charge sale 1 (AL5)
B4;F303;MEP;.;.;IDF ROOM;13,79;Local distribution courants faibles CfL102
B4;F304;STAFF FACILITIES & CLINICAL ADMIN;Espace réservé au personnel                          ; ???? ???  ????? ????????;MEETING ROOM;18,62;Salle de réunions …...
B4;F305;STAFF FACILITIES & CLINICAL ADMIN;Espace réservé au personnel                          ; ???? ???  ????? ????????;MEETING ROOM;18,11;Salle de réunions …...
B4;F306;STAFF FACILITIES & CLINICAL ADMIN;Espace réservé au personnel                          ; ???? ???  ????? ????????;PANTRY;3,33;Office
B4;F307;STAFF FACILITIES & CLINICAL ADMIN;Espace réservé au personnel                          ; ???? ???  ????? ????????;WC;2,61;Toilettes
B4;F308;STAFF FACILITIES & CLINICAL ADMIN;Espace réservé au personnel                          ; ???? ???  ????? ????????;MEETING ROOM;24,16;Salle de reunions …….
B4;F309;STAFF FACILITIES & CLINICAL ADMIN;Espace réservé au personnel                          ; ???? ???  ????? ????????;CORRIDOR;35,28;Bureaux médecins-Accès réservé
B4;F310;STAFF FACILITIES & CLINICAL ADMIN;Espace réservé au personnel                          ; ???? ???  ????? ????????;OFFICE;16,07;Bureau médecins
B4;F311;STAFF FACILITIES & CLINICAL ADMIN;Espace réservé au personnel                          ; ???? ???  ????? ????????;OFFICE;13,11;Chef de Pôle Ambulatoire
B4;F312;STAFF FACILITIES & CLINICAL ADMIN;Espace réservé au personnel                          ; ???? ???  ????? ????????;OFFICE;13,11;Secrétariat Pôle Ambulatoire
B4;F313;STAFF FACILITIES & CLINICAL ADMIN;Espace réservé au personnel                          ; ???? ???  ????? ????????;STAFF WC;4,38;Toilettes
B4;F314;STAFF FACILITIES & CLINICAL ADMIN;Espace réservé au personnel                          ; ???? ???  ????? ????????;STAFF WC;4,18;Toilettes
B4;F315;CIRCULATION;Couloir                         ;??? ;CORRIDOR;116,55;Bureaux médecins Accès réservé
B4;F316;CIRCULATION;Couloir                         ;??? ;LINK CORRIDOR;22,47;Sanitaires personnel et bureaux médecins
B4;F317;STAFF FACILITIES & CLINICAL ADMIN;Espace réservé au personnel                          ; ???? ???  ????? ????????;STAFF REST ROOM;22,31;Salle de repos externes
B4;F318;CIRCULATION;Couloir                         ;??? ;CORRIDOR;131,60;Couloir médical
B2;F319;CIRCULATION;Couloir                         ;??? ;DIS. WC;4,72;Toilettes handicapés
B2;F320;CIRCULATION;Couloir                         ;??? ;DIS. WC;4,60;Toilettes handicapés
B2;F321;MEP;.;.;ELEC.;9,00;Tableau électrique TEL111
B2;F322;MEP;.;.;IDF ROOM;9,30;Local distribution courants faibles CfL103
B2;F323;CIRCULATION;Couloir                         ;??? ;VISITORS & WAITING LOUNGE;66,05;Salon visiteurs
B2;F324;CIRCULATION;Couloir                         ;??? ;BABY CHANGE;3,46;Change couches
B2;F325;CIRCULATION;Couloir                         ;??? ;DIS. WC;3,76;Toilettes handicapés
B2;F326;HOSPITAL STREET;Couloir médical                    ;??? ?????;LIFT-02;.;Ascenseur visiteurs 2 (AL2)
B2;F327;HOSPITAL STREET;Couloir médical                    ;??? ?????;LIFT-03;.;Ascenseur visiteurs 1 (AL3)
B2;F328;CIRCULATION;Couloir                         ;??? ;CORRIDOR;96,68;Couloir médical
B2;F329;CIRCULATION;Couloir                         ;??? ;WASH+BOOT LOBBY;11,12;Salle de prière hommes
B2;F330;CIRCULATION;Couloir                         ;??? ;WASH;4,96;Salle d'ablutions hommes
B2;F331;CIRCULATION;Couloir                         ;??? ;WC;1,96;Toilettes
B2;F332;CIRCULATION;Couloir                         ;??? ;WC;2,10;Toilettes
B2;F333;OTHER ;.;.;PRAYER ROOM;62,09;Salle de prière hommes
B2;F334;HOSPITAL STREET;Couloir médical                    ;??? ?????;STAIR 4;25,99;Escalier 4
B7;F335;CARDIAC NURSING UNIT/RECOVERY;Soins Intensifs cardiovasculaires;  ????? ????? ????? / ???? ?????????;DISPOSAL;1,62;Local poubelles
B7;F336;CARDIAC NURSING UNIT/RECOVERY;Soins Intensifs cardiovasculaires;  ????? ????? ????? / ???? ?????????;WC;2,74;.
B7;F337;CARDIAC NURSING UNIT/RECOVERY;Soins Intensifs cardiovasculaires;  ????? ????? ????? / ???? ?????????;CCU SINGLE BED ROOM;21,19;Lit 1001
B7;F338;CARDIAC NURSING UNIT/RECOVERY;Soins Intensifs cardiovasculaires;  ????? ????? ????? / ???? ?????????;CCU SINGLE BED ROOM;21,19;Lit 1003
B7;F339;CARDIAC NURSING UNIT/RECOVERY;Soins Intensifs cardiovasculaires;  ????? ????? ????? / ???? ?????????;WC;2,75;.
B7;F340;CARDIAC NURSING UNIT/RECOVERY;Soins Intensifs cardiovasculaires;  ????? ????? ????? / ???? ?????????;WC;2,75;.
B7;F341;CARDIAC NURSING UNIT/RECOVERY;Soins Intensifs cardiovasculaires;  ????? ????? ????? / ???? ?????????;RECOVERY SINGLE BED ;21,19;Lit 1005
B7;F342;CARDIAC NURSING UNIT/RECOVERY;Soins Intensifs cardiovasculaires;  ????? ????? ????? / ???? ?????????;RECOVERY SINGLE BED ;21,19;Lit 1007
B7;F343;CARDIAC NURSING UNIT/RECOVERY;Soins Intensifs cardiovasculaires;  ????? ????? ????? / ???? ?????????;WC;2,75;.
B7;F344;CARDIAC NURSING UNIT/RECOVERY;Soins Intensifs cardiovasculaires;  ????? ????? ????? / ???? ?????????;CORRIDOR;50,46;.
B7;F345;CARDIAC NURSING UNIT/RECOVERY;Soins Intensifs cardiovasculaires;  ????? ????? ????? / ???? ?????????;NURSE STATION;11,17;Poste infirmier
B7;F346;CARDIAC NURSING UNIT/RECOVERY;Soins Intensifs cardiovasculaires;  ????? ????? ????? / ???? ?????????;CLEAN LINEN ROOM;13,66;Lingerie
B7;F347;CARDIAC NURSING UNIT/RECOVERY;Soins Intensifs cardiovasculaires;  ????? ????? ????? / ???? ?????????;C/U;20,23;Local Propre
B7;F348;CARDIAC NURSING UNIT/RECOVERY;Soins Intensifs cardiovasculaires;  ????? ????? ????? / ???? ?????????;STORE;18,7;Réserve
B7;F349;CARDIAC NURSING UNIT/RECOVERY;Soins Intensifs cardiovasculaires;  ????? ????? ????? / ???? ?????????;D/U;11,76;Local sale
B7;F350;CARDIAC NURSING UNIT/RECOVERY;Soins Intensifs cardiovasculaires;  ????? ????? ????? / ???? ?????????;HK;6,38;Local ménage
B11;F353;OPD;Centre de la douleur;?????? ?????? ;CONS./ EX.;12,06;Salle de consultation 1
B11;F355;OPD;Centre de la douleur;?????? ?????? ;CONS./ EX.;11,81;Salle de consultation 2
B11;F356;CIRCULATION;Couloir                         ;??? ;WAITING;19,83;Attente
B11;F357;OPD;Centre de la douleur;?????? ?????? ;TREATMENT;9;Salle de soins
B11;F358;OPD;Centre de la douleur;?????? ?????? ;C/U;8,95;Local propre
B7;F359;CARDIAC NURSING UNIT/RECOVERY;Soins Intensifs cardiovasculaires;  ????? ????? ????? / ???? ?????????;CORRIDOR;39,47;.
B11;F360;OPD;Consultations externes                                   ;?????? ?????? ;OFFICE;10,43;.
B11;F361;OPD;Consultations externes                                   ;?????? ?????? ;HOME TREATMENT;9,19;Home Care
B7;F362;CARDIAC NURSING UNIT/RECOVERY;Soins Intensifs cardiovasculaires;  ????? ????? ????? / ???? ?????????;WC;2,75;.
B11;F363;CIRCULATION;Couloir                         ;??? ;RECEPTION OFFICE;9,83;Acceuil secrétariat Centre Dentaire
B11;F364;CIRCULATION;Couloir                         ;??? ;WAITING;30,37;Attente
B7;F365;CARDIAC NURSING UNIT/RECOVERY;Soins Intensifs cardiovasculaires;  ????? ????? ????? / ???? ?????????;CCU SINGLE BED ROOM;21,1;Lit 1009
B7;F366;CARDIAC NURSING UNIT/RECOVERY;Soins Intensifs cardiovasculaires;  ????? ????? ????? / ???? ?????????;WC;2,74;.
B7;F367;CARDIAC NURSING UNIT/RECOVERY;Soins Intensifs cardiovasculaires;  ????? ????? ????? / ???? ?????????;WC;2,75;.
B7;F368;CARDIAC NURSING UNIT/RECOVERY;Soins Intensifs cardiovasculaires;  ????? ????? ????? / ???? ?????????;CCU SINGLE BED ROOM;21,1;Lit 1013
B11;F369;CIRCULATION;Couloir                         ;??? ;WAITING;39,78;Attente
B11;F370;CIRCULATION;Couloir                         ;??? ;RECEPTION;20,09;Acceuil Informations
B11;F371;OPD;Consultations externes                                   ;????? ??????? ? ?? ??????;EXAM;13,26;Consultation Pré-anesthesique
B11;F372;OPD;Consultations externes                                   ;????? ??????? ? ?? ??????;HOME TREATMENT RM;9,63;Consultation Pré-anesthesique
B11;F373;OPD;Consultations externes                                   ;????? ??????? ? ?? ??????;EXAM;16,89;Consultation Pré-anesthesique
B11;F374;OBSTETRIC/GYNECOLOGY WARD ;Hospitalisation Mère Enfant;????? ??????? ? ?? ??????;E/S;3,02;.
B12;F375;OBSTETRIC/GYNECOLOGY WARD ;Hospitalisation Mère Enfant;????? ??????? ? ?? ??????;SINGLE BED ROOM;16,86;Lit 1501
B12;F376;OBSTETRIC/GYNECOLOGY WARD ;Hospitalisation Mère Enfant;????? ??????? ? ?? ??????;SINGLE BED ROOM;18,75;Lit 1502
B12;F377;OBSTETRIC/GYNECOLOGY WARD ;Hospitalisation Mère Enfant;????? ??????? ? ?? ??????;E/S;2,88;.
B12;F378;OBSTETRIC/GYNECOLOGY WARD ;Hospitalisation Mère Enfant;????? ??????? ? ?? ??????;E/S;2,88;.
B12;F379;OBSTETRIC/GYNECOLOGY WARD ;Hospitalisation Mère Enfant;????? ??????? ? ?? ??????;SINGLE BED ROOM;18,75;Lit 1503
B12;F380;OBSTETRIC/GYNECOLOGY WARD ;Hospitalisation Mère Enfant;????? ??????? ? ?? ??????;SINGLE BED ROOM;18,75;Lit 1504
B12;F381;OBSTETRIC/GYNECOLOGY WARD ;Hospitalisation Mère Enfant;????? ??????? ? ?? ??????;E/S;2,88;.
B12;F382;OBSTETRIC/GYNECOLOGY WARD ;Hospitalisation Mère Enfant;????? ??????? ? ?? ??????;E/S;2,88;.
B12;F383;OBSTETRIC/GYNECOLOGY WARD ;Hospitalisation Mère Enfant;????? ??????? ? ?? ??????;SINGLE BED ROOM;18,75;Lit 1505
B12;F384;OBSTETRIC/GYNECOLOGY WARD ;Hospitalisation Mère Enfant;????? ??????? ? ?? ??????;SINGLE BED ROOM;17,86;Lit 1506
B12;F385;OBSTETRIC/GYNECOLOGY WARD ;Hospitalisation Mère Enfant;????? ??????? ? ?? ??????;E/S;2,65;.
B12;F386;OBSTETRIC/GYNECOLOGY WARD ;Hospitalisation Mère Enfant;????? ??????? ? ?? ??????;DR OFFICE;14,63;Bureau médecin
B12;F387;OBSTETRIC/GYNECOLOGY WARD ;Hospitalisation Mère Enfant;????? ??????? ? ?? ??????;SINGLE BED ROOM;19,89;Lit 1507
B12;F388;OBSTETRIC/GYNECOLOGY WARD ;Hospitalisation Mère Enfant;????? ??????? ? ?? ??????;E/S;4,31;.
B12;F389;OBSTETRIC/GYNECOLOGY WARD ;Hospitalisation Mère Enfant;????? ??????? ? ?? ??????;E/S;4,31;.
B12;F390;OBSTETRIC/GYNECOLOGY WARD ;Hospitalisation Mère Enfant;????? ??????? ? ?? ??????;SINGLE BED ROOM;20,01;Lit 1508
B12;F391;OBSTETRIC/GYNECOLOGY WARD ;Hospitalisation Mère Enfant;????? ??????? ? ?? ??????;NURSE OFFICE;9,48;Cardre infirmier
B12;F392;OBSTETRIC/GYNECOLOGY WARD ;Hospitalisation Mère Enfant;????? ??????? ? ?? ??????;LINEN;1,82;Lingerie
B12;F393;OBSTETRIC/GYNECOLOGY WARD ;Hospitalisation Mère Enfant;????? ??????? ? ?? ??????;NURSE STATION;8,38;Poste infirmier
B12;F394;OBSTETRIC/GYNECOLOGY WARD ;Hospitalisation Mère Enfant;????? ??????? ? ?? ??????;C/U;8,43;Local propre
B12;F395;OBSTETRIC/GYNECOLOGY WARD ;Hospitalisation Mère Enfant;????? ??????? ? ?? ??????;TREATMENT PREP;15,66;Salle de soins
B12;F396;OBSTETRIC/GYNECOLOGY WARD ;Hospitalisation Mère Enfant;????? ??????? ? ?? ??????;STORE;4,58;Pharmacie
B12;F397;OBSTETRIC/GYNECOLOGY WARD ;Hospitalisation Mère Enfant;????? ??????? ? ?? ??????;SINGLE BED ROOM;18,26;Lit 1509
B12;F398;OBSTETRIC/GYNECOLOGY WARD ;Hospitalisation Mère Enfant;????? ??????? ? ?? ??????;E/S;2,78;.
B9;F399;OBSTETRIC/GYNECOLOGY WARD ;Hospitalisation Mère Enfant;????? ??????? ? ?? ??????;E/S;2,98;.
B9;F400;OBSTETRIC/GYNECOLOGY WARD ;Hospitalisation Mère Enfant;????? ??????? ? ?? ??????;SINGLE BED ROOM;19,09;Lit 1510
B9;F401;OBSTETRIC/GYNECOLOGY WARD ;Hospitalisation Mère Enfant;????? ??????? ? ?? ??????;SINGLE BED ROOM;18,86;Lit 1511
B9;F402;OBSTETRIC/GYNECOLOGY WARD ;Hospitalisation Mère Enfant;????? ??????? ? ?? ??????;E/S;2,88;.
B9;F403;OBSTETRIC/GYNECOLOGY WARD ;Hospitalisation Mère Enfant;????? ??????? ? ?? ??????;E/S;2,88;.
B9;F404;OBSTETRIC/GYNECOLOGY WARD ;Hospitalisation Mère Enfant;????? ??????? ? ?? ??????;SINGLE BED ROOM;18,86;Lit 1512
B9;F405;OBSTETRIC/GYNECOLOGY WARD ;Hospitalisation Mère Enfant;????? ??????? ? ?? ??????;SINGLE BED ROOM;18,86;Lit 1513
B9;F406;OBSTETRIC/GYNECOLOGY WARD ;Hospitalisation Mère Enfant;????? ??????? ? ?? ??????;E/S;2,88;.
B9;F407;OBSTETRIC/GYNECOLOGY WARD ;Hospitalisation Mère Enfant;????? ??????? ? ?? ??????;E/S;2,88;.
B9;F408;OBSTETRIC/GYNECOLOGY WARD ;Hospitalisation Mère Enfant;????? ??????? ? ?? ??????;SINGLE BED ROOM;20,55;Lit 1514
B9;F409;OBSTETRIC/GYNECOLOGY WARD ;Hospitalisation Mère Enfant;????? ??????? ? ?? ??????;SINGLE BED ROOM;18,70;Lit 1515
B9;F410;OBSTETRIC/GYNECOLOGY WARD ;Hospitalisation Mère Enfant;????? ??????? ? ?? ??????;E/S;5,75;.
B9;F411;OBSTETRIC/GYNECOLOGY WARD ;Hospitalisation Mère Enfant;????? ??????? ? ?? ??????;HK;2,26;Local ménage
B9;F412;OBSTETRIC/GYNECOLOGY WARD ;Hospitalisation Mère Enfant;????? ??????? ? ?? ??????;DISPOSAL;2,17;Local poubelles
B9;F413;OBSTETRIC/GYNECOLOGY WARD ;Hospitalisation Mère Enfant;????? ??????? ? ?? ??????;E/S;5,75;.
B9;F414;OBSTETRIC/GYNECOLOGY WARD ;Hospitalisation Mère Enfant;????? ??????? ? ?? ??????;SINGLE BED ROOM;18,88;Lit 1516
B9;F415;OBSTETRIC/GYNECOLOGY WARD ;Hospitalisation Mère Enfant;????? ??????? ? ?? ??????;SINGLE BED ROOM;20,72;Lit 1517
B9;F416;OBSTETRIC/GYNECOLOGY WARD ;Hospitalisation Mère Enfant;????? ??????? ? ?? ??????;E/S;2,88;.
B9;F417;OBSTETRIC/GYNECOLOGY WARD ;Hospitalisation Mère Enfant;????? ??????? ? ?? ??????;E/S;2,88;.
B9;F418;OBSTETRIC/GYNECOLOGY WARD ;Hospitalisation Mère Enfant;????? ??????? ? ?? ??????;SINGLE BED ROOM;18,92;Lit 1518
B9;F419;OBSTETRIC/GYNECOLOGY WARD ;Hospitalisation Mère Enfant;????? ??????? ? ?? ??????;SINGLE BED ROOM;18,92;Lit 1519
B9;F420;OBSTETRIC/GYNECOLOGY WARD ;Hospitalisation Mère Enfant;????? ??????? ? ?? ??????;E/S;2,88;.
B9;F421;OBSTETRIC/GYNECOLOGY WARD ;Hospitalisation Mère Enfant;????? ??????? ? ?? ??????;E/S;2,88;.
B12;F422;OBSTETRIC/GYNECOLOGY WARD ;Hospitalisation Mère Enfant;????? ??????? ? ?? ??????;CORRIDOR;108,91;Hospitalisation Mère Enfant
B9;F423;OBSTETRIC/GYNECOLOGY WARD ;Hospitalisation Mère Enfant;????? ??????? ? ?? ??????;SINGLE BED ROOM;18,92;Lit 1520
B9;F424;OBSTETRIC/GYNECOLOGY WARD ;Hospitalisation Mère Enfant;????? ??????? ? ?? ??????;SINGLE BED ROOM;19,29;Lit 1521
B9;F425;OBSTETRIC/GYNECOLOGY WARD ;Hospitalisation Mère Enfant;????? ??????? ? ?? ??????;E/S;3,02;.
B12;F426;OBSTETRIC/GYNECOLOGY WARD ;Hospitalisation Mère Enfant;????? ??????? ? ?? ??????;PANTRY;6,55;Office
B12;F427;OBSTETRIC/GYNECOLOGY WARD ;Hospitalisation Mère Enfant;????? ??????? ? ?? ??????;D/U;8,46;Local sale
B12;F428;MEP;.;.;ELEC.;.;Tableau électrique TEL112
B12;F429;OBSTETRIC/GYNECOLOGY WARD ;Hospitalisation Mère Enfant;????? ??????? ? ?? ??????;STAIR 09;24,49;Escalier 9
B12;F430;OBSTETRIC/GYNECOLOGY WARD ;Hospitalisation Mère Enfant;????? ??????? ? ?? ??????;CORRIDOR;69,35;.
B12;F431;OBSTETRIC/GYNECOLOGY WARD ;Hospitalisation Mère Enfant;????? ??????? ? ?? ??????;E/S;2,83;.
B12;F432;OBSTETRIC/GYNECOLOGY WARD ;Hospitalisation Mère Enfant;????? ??????? ? ?? ??????;SINGLE BED ROOM;18,64;Lit 1522
B12;F433;OBSTETRIC/GYNECOLOGY WARD ;Hospitalisation Mère Enfant;????? ??????? ? ?? ??????;SINGLE BED ROOM;18,92;Lit 1523
B12;F434;OBSTETRIC/GYNECOLOGY WARD ;Hospitalisation Mère Enfant;????? ??????? ? ?? ??????;E/S;2,88;.
B12;F435;OBSTETRIC/GYNECOLOGY WARD ;Hospitalisation Mère Enfant;????? ??????? ? ?? ??????;E/S;2,88;.
B12;F436;OBSTETRIC/GYNECOLOGY WARD ;Hospitalisation Mère Enfant;????? ??????? ? ?? ??????;SINGLE BED ROOM;18,92;Lit 1524
B12;F437;OBSTETRIC/GYNECOLOGY WARD ;Hospitalisation Mère Enfant;????? ??????? ? ?? ??????;SINGLE BED ROOM;18,92;Lit 1525
B12;F438;OBSTETRIC/GYNECOLOGY WARD ;Hospitalisation Mère Enfant;????? ??????? ? ?? ??????;E/S;2,88;Salle de consultation 2
B12;F439;OBSTETRIC/GYNECOLOGY WARD ;Hospitalisation Mère Enfant;????? ??????? ? ?? ??????;E/S;2,88;Salle de consultation 2
B12;F440;OBSTETRIC/GYNECOLOGY WARD ;Hospitalisation Mère Enfant;????? ??????? ? ?? ??????;SINGLE BED ROOM;18,92;Lit 1526
B12;F441;OBSTETRIC/GYNECOLOGY WARD ;Hospitalisation Mère Enfant;????? ??????? ? ?? ??????;NURSERY;22,81;Nurserie
B9;F442;OBSTETRIC/GYNECOLOGY WARD ;Hospitalisation Mère Enfant;????? ??????? ? ?? ??????;CORRIDOR;24,66;Hospitalisation Mère Enfant
B11;F443;OBSTETRIC/GYNECOLOGY WARD ;Hospitalisation Mère Enfant;????? ??????? ? ?? ??????;STAIR 08;24,50;Escalier 8
B12;F444;OBSTETRIC/GYNECOLOGY WARD ;Hospitalisation Mère Enfant;????? ??????? ? ?? ??????;LIFT-08;.;Ascenseur visiteurs 3 (AL14)
B11;F445;CIRCULATION;Couloir                         ;??? ;LOBBY;75,88;?
B11;F446;OPD;Dialyse;?????? ?????? ;RECEPTION;19,76;Accueil Secrétariat
B11;F448;OPD;Dialyse;?????? ?????? ;DIALYSIS ROOM 8;15,03;Fauteuil 8
B11;F449;OPD;Dialyse;?????? ?????? ;DIALYSIS ROOM 7;14,88;Fauteuil 7
B11;F450;OPD;Dialyse;?????? ?????? ;DIALYSIS ROOM 6;15,03;Fauteuil 6
B11;F451;OPD;Dialyse;?????? ?????? ;DIALYSIS ROOM 5;14,88;Fauteuil 5
B11;F452;OPD;Dialyse;?????? ?????? ;VIP DIALYSIS ROOM;19;Fauteuil 9
B11;F453;OPD;Dialyse;?????? ?????? ;CORRIDOR;25,58;.
B11;F454;OPD;Dialyse;?????? ?????? ;C/U;5,5;Local propre
B11;F455;OPD;Dialyse;?????? ?????? ;H.K.;5,5;Local ménage
B11;F456;OPD;Dialyse;?????? ?????? ;TOILET;3,06;Toilettes
B11;F457;OPD;Dialyse;?????? ?????? ;TOILET;3,06;Toilettes
B11;F458;OPD;Dialyse;?????? ?????? ;CORRIDOR;67,08;.
B11;F459;OPD;Dialyse;?????? ?????? ;STORE CONSUMABLE;15,55;Pharmacie
B11;F460;OPD;Consultations externes                                   ;?????? ?????? ;DR OFFICE;10,14;Salle de consultation 2
B11;F461;OPD;Dialyse;?????? ?????? ;DIALYSIS ROOM 2;16;Fauteuil 2
B11;F462;OPD;Dialyse;?????? ?????? ;DIALYSIS ROOM 3;15,89;Fauteuil 3
B11;F463;OPD;Consultations externes                                   ;?????? ?????? ;DISPOSAL D/U;9,93;Salle de consultation 2
B11;F464;OPD;Dialyse;?????? ?????? ;NURSE STATION;17,77;Poste infirmier
B11;F465;OPD;Dialyse;?????? ?????? ;DIALYSIS ROOM 4;16;Fauteuil 4
B11;F466;OPD;Dialyse;?????? ?????? ;DIALYSIS ROOM 1;15,95;Fauteuil 1
B11;F467;OPD;Dialyse;?????? ?????? ;WAITING;59,98;Attente dialyse
B11;F468;MEP;.;.;ELEC.;.;Tableau électrique TEL113
B7;F469;CARDIAC NURSING UNIT/RECOVERY;Soins Intensifs cardiovasculaires;  ????? ????? ????? / ???? ?????????;CCU SINGLE BED ROOM;21,09;Lit 1011
B7;F470;CARDIAC NURSING UNIT/RECOVERY;Soins Intensifs cardiovasculaires;  ????? ????? ????? / ???? ?????????;CCU SINGLE BED ROOM;21,2;Lit 1009
B7;F471;CARDIAC NURSING UNIT/RECOVERY;Soins Intensifs cardiovasculaires;  ????? ????? ????? / ???? ?????????;WC;2,76;.
B11;F472;OPD;Centre dentaire;?????? ?????? ;STORE;5,94;Réserve
B11;F473;OPD;Centre dentaire;?????? ?????? ;STORE;8,39;Archives
B11;F474;CIRCULATION;Couloir                         ;??? ;CORRIDOR;10,36;Accès  Centres Dentaire et de la Douleur
B11;F475;OPD;Centre dentaire;?????? ?????? ;LABORATORY;8,91;Laboratoire prothèses
B11;F476;OPD;Centre dentaire;?????? ?????? ;TREATMENT 4;17,48;Salle 4
B11;F477;OPD;Consultations externes                                   ;?????? ?????? ;DISPOSAL D/U;2,53;Salle de consultation 2
B11;F478;DENTAL CLINIC ;Clinique dentaire                                                       ;????? ???????;LINEN;2,62;Lingerie
B11;F479;OPD;Centre dentaire;?????? ?????? ;CONSUMABLE STORE;19,71;Pharmacie
B11;F480;MEP;.;.;ELEC.;.;Tableau électrique TEL114
B11;F481;OPD;Centre dentaire;?????? ?????? ;CORRIDOR;59,39;Centre Dentaire
B11;F482;OPD;Centre dentaire;?????? ?????? ;TREATMENT 1;17,57;Salle 1
B7;F483;CIRCULATION;Couloir                         ;??? ;PAT WC;2,85;Toilettes
B7;F484;CIRCULATION;Couloir                         ;??? ;PAT WC;2,76;Toilettes
B7;F485;CIRCULATION;Couloir                         ;??? ;CORRIDOR;57,69;Escalier de secours
B7;F486;CIRCULATION;Couloir                         ;??? ;STAFF WC;5,97;Toilettes personnel
B7;F487;CIRCULATION;Couloir                         ;??? ;STAIR 07;24,5;Escalier 7
B11;F488;OPD;Centre dentaire;?????? ?????? ;TREATMENT 2;14,23;Salle 2
B11;F489;OPD;Centre dentaire;?????? ?????? ;TREATMENT 3;18,07;Salle 1
B7;F490;CARDIAC NURSING UNIT/RECOVERY;Soins Intensifs cardiovasculaires;  ????? ????? ????? / ???? ?????????;CCU SINGLE BED ROOM;20,04;Lit 1012
B7;F491;CARDIAC NURSING UNIT/RECOVERY;Soins Intensifs cardiovasculaires;  ????? ????? ????? / ???? ?????????;WC;3,68;.
B7;F492;CARDIAC NURSING UNIT/RECOVERY;Soins Intensifs cardiovasculaires;  ????? ????? ????? / ???? ?????????;CCU SINGLE BED ROOM;21,06;Lit 1010
B7;F493;CARDIAC NURSING UNIT/RECOVERY;Soins Intensifs cardiovasculaires;  ????? ????? ????? / ???? ?????????;WC;2,74;.
B7;F494;CARDIAC NURSING UNIT/RECOVERY;Soins Intensifs cardiovasculaires;  ????? ????? ????? / ???? ?????????;WC;2,74;.
B7;F495;CARDIAC NURSING UNIT/RECOVERY;Soins Intensifs cardiovasculaires;  ????? ????? ????? / ???? ?????????;CCU SINGLE BED ROOM;21,05;Lit 1008
B7;F496;CARDIAC NURSING UNIT/RECOVERY;Soins Intensifs cardiovasculaires;  ????? ????? ????? / ???? ?????????;CCU SINGLE BED ROOM;21,05;Lit 1006
B7;F497;CARDIAC NURSING UNIT/RECOVERY;Soins Intensifs cardiovasculaires;  ????? ????? ????? / ???? ?????????;WC;2,74;.
B7;F498;CARDIAC NURSING UNIT/RECOVERY;Soins Intensifs cardiovasculaires;  ????? ????? ????? / ???? ?????????;WC;2,74;.
B7;F499;CARDIAC NURSING UNIT/RECOVERY;Soins Intensifs cardiovasculaires;  ????? ????? ????? / ???? ?????????;CCU SINGLE BED ROOM;21,05;Lit 1004
B7;F500;CARDIAC NURSING UNIT/RECOVERY;Soins Intensifs cardiovasculaires;  ????? ????? ????? / ???? ?????????;CCU SINGLE BED ROOM;21,05;Lit 1002
B7;F501;CARDIAC NURSING UNIT/RECOVERY;Soins Intensifs cardiovasculaires;  ????? ????? ????? / ???? ?????????;WC;2,75;.
B7;F502;CARDIAC NURSING UNIT/RECOVERY;Soins Intensifs cardiovasculaires;  ????? ????? ????? / ???? ?????????;HK;3,05;Local ménage
B1;F503;CARDIAC INVESTIGATION;Explorations cardiaques                                          ;  ????????? ?????;CORRIDOR;79,55;.
B3;F504;OPERATING THEATERS ;Bloc opératoire                                                    ;  ???? ?????  ;SCRUB-UP;.;.
B1;F505;CIRCULATION;Couloir                         ;??? ;STAIR LOBBY;.;."""





		locloc="""B1;G001;EMERGENCY;Urgences                               ;    ???????? ;STAIR-01;29,09;Escalier de secours 01
B1;G002;MEP;.;.;ELEC.;.;Tableau électrique TEL001
B1;G003;EMERGENCY;Urgences                               ;    ???????? ;EX / TR;15,58;Salle d'examen et de soins 4
B1;G004;EMERGENCY;Urgences                               ;    ???????? ;CORRIDOR;32,71;Salles d'examen et de soins
B1;G005;EMERGENCY;Urgences                               ;    ???????? ;EX / TR;15,93;Salle d'examen et de soins 5
B1;G006;EMERGENCY;Urgences                               ;    ???????? ;CORRIDOR;55,31;Accès patients couchés
B1;G007;EMERGENCY;Urgences                               ;    ???????? ;RESUSCITATION AREA;78,29;Salle d'accueil des urgences vitales
B1;G008;EMERGENCY;Urgences                               ;    ???????? ;NURSE STATION;7,27;Poste infirmier SAUV
B1;G009;EMERGENCY;Urgences                               ;    ???????? ;ON CALL ROOM;22,32;Salle de garde 1
B1;G010;EMERGENCY;Urgences                               ;    ???????? ;ON CALL ROOM;17,64;Salle de garde 2
B1;G011;EMERGENCY;Urgences                               ;    ???????? ;ON CALL ROOM;17,84;Salle de garde 3
B1;G012;EMERGENCY;Urgences                               ;    ???????? ;ON CALL ROOM;18,35;Salle de garde 4
B1;G013;EMERGENCY;Urgences                               ;    ???????? ;ON CALL ROOM;32,73;Salle de garde et repos infimiers
B6;G014;CIRCULATION;Couloir                         ;??? ;CORRIDOR;103,73;.
B1;G015;EMERGENCY;Urgences                               ;    ???????? ;EX / TR;16,04;Salle d'examen et de soins 3
B1;G016;EMERGENCY;Urgences                               ;    ???????? ;EX / TR;15,70;Salle d'examen et de soins 6
B1;G017;EMERGENCY;Urgences                               ;    ???????? ;EX / TR;15,70;Salle d'examen et de soins 2
B1;G018;EMERGENCY;Urgences                               ;    ???????? ;EX / TR;17,20;Salle d'examen et de soins 1
B1;G019;EMERGENCY;Urgences                               ;    ???????? ;HEAD NURSE;18,56;Secrétariat Médical
B1;G020;EMERGENCY;Urgences                               ;    ???????? ;NURSE STATION;21,00;Poste infiirmier urgences
B1;G021;EMERGENCY;Urgences                               ;    ???????? ;LINEN STORE;7,86;Lingerie
B1;G022;EMERGENCY;Urgences                               ;    ???????? ;HEAD OF DEPARTMENT;21,62;Bureau Superviseur/cadre infirmier urgences
B1;G023;EMERGENCY;Urgences                               ;    ???????? ;EQUIP. STORE;21,79;Réserve qéuipements médicaux
B1;G024;EMERGENCY;Urgences                               ;    ???????? ;SECRETARY;17,72;Bureau Cadre infirmier SAMU
B1;G025;EMERGENCY;Urgences                               ;    ???????? ;D/U;6,08;Local sale
B1;G026;EMERGENCY;Urgences                               ;    ???????? ;DR. OFFICE;17,72;Salle de consultation 2
B1;G027;EMERGENCY;Urgences                               ;    ???????? ;CALL CENTRE;17,84;Centre d'appels d'urgence
B1;G028;EMERGENCY;Urgences                               ;    ???????? ;W.C;3,61;Toilettes
B1;G029;EMERGENCY;Urgences                               ;    ???????? ;W.C;3,03;Toilettes
B1;G030;EMERGENCY;Urgences                               ;    ???????? ;CORRIDOR;95,84;Zone administrative
B1;G031;EMERGENCY;Urgences                               ;    ???????? ;H.K;5,78;Local ménage
B1;G032;EMERGENCY;Urgences                               ;    ???????? ;PANTRY;18,08;Office
B1;G033;EMERGENCY;Urgences                               ;    ???????? ;DISPENSARY;16,22;Salle de consultation 2
B1;G034;EMERGENCY;Urgences                               ;    ???????? ;DISPENSARY;16,87;Salle de consultation 2
B1;G035;EMERGENCY;Urgences                               ;    ???????? ;STORE CONSUMABLES;16,33;Stock Medicaments 
B1;G036;EMERGENCY;Urgences                               ;    ???????? ;STORE CONSUMABLES;12,54;Stock Solutés
B1;G038;EMERGENCY;Urgences                               ;    ???????? ;W.C;1,89;?
B1;G039;EMERGENCY;Urgences                               ;    ???????? ;LOBBY;63,12;.
B1;G040;EMERGENCY;Urgences                               ;    ???????? ;RECEPTION;102,03;Acceuil Admissions
B1;G041;EMERGENCY;Urgences                               ;    ???????? ;LOBBY/CORRIDOR;43,81;.
B1;G042;EMERGENCY;Urgences                               ;    ???????? ;EMERGENCY ENTRANCE;10,14;Urgences:  Accès piétons
B1;G043;EMERGENCY;Urgences                               ;    ???????? ;WAITING;66,48;.
B1;G044;EMERGENCY;Urgences                               ;    ???????? ;SECURITY CENTRE;57,6;PC Sécurité
B1;G045;EMERGENCY;Urgences                               ;    ???????? ;STAFF REST;10,32;Bureau officier de sécurité
B1;G046;EMERGENCY;Urgences                               ;    ???????? ;LOBBY;7;Hall des Urgences
B1;G048;CIRCULATION;Couloir                         ;??? ;CORRIDOR;44,46;.
B1;G049;EMERGENCY;Urgences                               ;    ???????? ;OFFICE;16,99;Bureau Superviseur
B1;G050;REHABILITATION;Rééducation                    ;????? ??????? ?????;TREATMENT ROOM;14,63;Salle de traitement 1
B1;G051;REHABILITATION;Rééducation                    ;????? ??????? ?????;TREATMENT ROOM;14,63;Salle de traitement 1
B1;G052;REHABILITATION;Rééducation                    ;????? ??????? ?????;TREATMENT ROOM;14,63;Salle de traitement 1
B1;G053;REHABILITATION;Rééducation                    ;????? ??????? ?????;TREATMENT ROOM;14,63;Salle de traitement 1
B1;G054;REHABILITATION;Rééducation                    ;????? ??????? ?????;GYM;41,92;Salle de psychomotricité
B1;G055;ADMIN DEPARTMENT;Administration                                  ;  ???????  ;SOCIAL SERVICES OFFICE;16,80;Assitante Sociale
B1;G056;REHABILITATION;Rééducation                    ;????? ??????? ?????;PAT.CH & WC;5,97;Toilettes personnel 1
B1;G057;REHABILITATION;Rééducation                    ;????? ??????? ?????;PAT.CH & WC;3,73;Toilettes personnel 2
B1;G058;REHABILITATION;Rééducation                    ;????? ??????? ?????;NURSE STATION;10,88;Accueil Secrétariat
B1;G059;REHABILITATION;Rééducation                    ;????? ??????? ?????;HK;4,25;Local ménage
B1;G060;REHABILITATION;Rééducation                    ;????? ??????? ?????;EQUIP.STERILE;10,30;Réserve matériel
B1;G061;ADMIN DEPARTMENT;Administration                                  ;  ???????  ;P.R.OFFICES;18,88;Bureau prises en charge
B1;G062;REHABILITATION;Rééducation                    ;????? ??????? ?????;EXAM;15,67;Salle de consultation
B1;G063;REHABILITATION;Rééducation                    ;????? ??????? ?????;DR. OFFICE;11,94;Salle de consultation 2
B1;G064;REHABILITATION;Rééducation                    ;????? ??????? ?????;WAITING;15,72;Attente
B1;G065;REHABILITATION;Rééducation                    ;????? ??????? ?????;C/U;5,79;Local propre
B1;G066;REHABILITATION;Rééducation                    ;????? ??????? ?????;D/U;5,79;Local Sale
B1;G067;REHABILITATION;Rééducation                    ;????? ??????? ?????;STAFF W.C.;3,55;Toilettes visiteurs 1
B1;G068;REHABILITATION;Rééducation                    ;????? ??????? ?????;CORRIDOR;75,17;.
B1;G069;REHABILITATION;Rééducation                    ;????? ??????? ?????;STAFF W.C.;3,54;Toilettes visiteurs 2
B1;G070;REHABILITATION;Rééducation                    ;????? ??????? ?????;STAFF REST;18,04;Salle de repos
B3;G071;IMAGING;Radiologie                           ; ????? ?? ??????;STAIR-02;30,43;Escalier 2
B3;G072;CIRCULATION;Couloir                         ;??? ;CORRIDOR;48,94;.
B3;G073;IMAGING;Radiologie                           ; ????? ?? ??????;X-RAY ROOM;32,40;Salle de radiographie 1
B3;G074;IMAGING;Radiologie                           ; ????? ?? ??????;X-RAY ROOM;32,67;Salle de radiographie 2
B3;G075;IMAGING;Radiologie                           ; ????? ?? ??????;X-RAY ROOM;32,67;Salle de radiographie 3
B3;G076;IMAGING;Radiologie                           ; ????? ?? ??????;DIGITIZER;19,74;Salle numeriseurs et reprographes
B3;G077;IMAGING;Radiologie                           ; ????? ?? ??????;PRINT ROOM;3,88;Stock films
B3;G078;IMAGING;Radiologie                           ; ????? ?? ??????;BED WAIT;21,97;Attente patients couchés
B3;G079;IMAGING;Radiologie                           ; ????? ?? ??????;STORAGE;8,64;Bureau cadre infirmier
B3;G080;IMAGING;Radiologie                           ; ????? ?? ??????;SECRETARY;8,06; Secrétariat 2
B3;G082;IMAGING;Radiologie                           ; ????? ?? ??????;DR OFFICE;15,59;Salle de consultation 2
B3;G083;IMAGING;Radiologie                           ; ????? ?? ??????;CT SCANNER;48,82;Scanner 128
B3;G084;IMAGING;Radiologie                           ; ????? ?? ??????;CORRIDOR;84,21;.
B3;G085;IMAGING;Radiologie                           ; ????? ?? ??????;CORRIDOR;57,62;.
B3;G086;IMAGING;Radiologie                           ; ????? ?? ??????;REPORTING ROOM;17,66;Salle d'interprétation 2
B3;G087;IMAGING;Radiologie                           ; ????? ?? ??????;CONTROL ROOM;16,42;Salle de contrôle Scanners
B3;G088;IMAGING;Radiologie                           ; ????? ?? ??????;NURSE STATION 3;12,11;Acceuil/Poste infirmier
B3;G089;IMAGING;Radiologie                           ; ????? ?? ??????;CONTROL ROOM;11,39;?
B3;G090;IMAGING;Radiologie                           ; ????? ?? ??????;EQUIPMENT ROOM;14,06;Salle technique
B3;G091;IMAGING;Radiologie                           ; ????? ?? ??????;MRI;35,66;Imagerie par résonnance magnétique 1
B3;G092;IMAGING;Radiologie                           ; ????? ?? ??????;SECURE ENTRANCE;10,89;Sas Imagerie par résonnance magnétique
B3;G093;IMAGING;Radiologie                           ; ????? ?? ??????;CONTROL ROOM;12,24;Salle de contrôle IRM
B3;G093;IMAGING;Radiologie                           ; ????? ?? ??????;CONTROL  ROOM;15,33;Salle de contrôle IRM
B3;G094;IMAGING;Radiologie                           ; ????? ?? ??????;MRI SHELLED;35,32;Imagerie par résonnance magnétique 2
B3;G096;IMAGING;Radiologie                           ; ????? ?? ??????;DENTAL X-RAY;18,93;Danta scanner
B3;G097;IMAGING;Radiologie                           ; ????? ?? ??????;CT SCANNER;43,07;Scanner 32
B3;G098;IMAGING;Radiologie                           ; ????? ?? ??????;ULTRASOUND;13,13;Echographie
B3;G099;IMAGING;Radiologie                           ; ????? ?? ??????;BONE DENSITYOMETRY;15,76;Ostéodensitométrie
B3;G100;IMAGING;Radiologie                           ; ????? ?? ??????;ULTRASOUND;13,12;Echographie
B3;G101;IMAGING;Radiologie                           ; ????? ?? ??????;MAMMOGRAPHY FULL FIELD;14,79;Mamographie stéreotaxique
B3;G102;IMAGING;Radiologie                           ; ????? ?? ??????;PANTOGRAPHY;13,87;Orthopantomographie
B3;G103;IMAGING;Radiologie                           ; ????? ?? ??????;INTERVENTIONAL ULTRASOUND;20,69;Echographie interventionnelle
B3;G104;IMAGING;Radiologie                           ; ????? ?? ??????;CORRIDOR;85,16;.
B3;G105;IMAGING;Radiologie                           ; ????? ?? ??????;REPORTING RM.;27,6;Salle d'interprétation 1
B3;G107;IMAGING;Radiologie                           ; ????? ?? ??????;DR.;12,84;Salle de consultation 2
B3;G108;IMAGING;Radiologie                           ; ????? ?? ??????;D/U;4,16;Local sale
B3;G109;IMAGING;Radiologie                           ; ????? ?? ??????;C/U;4,41;Local propre
B3;G110;IMAGING;Radiologie                           ; ????? ?? ??????;SECRATARY;14,31;Secrétariat 1
B3;G112;IMAGING;Radiologie                           ; ????? ?? ??????;RECEPTION ;10,24;Acceuil
B3;G114;IMAGING;Radiologie                           ; ????? ?? ??????;OFFICE ADMIN;13,87;Bureau Administrateur
B3;G115;IMAGING;Radiologie                           ; ????? ?? ??????;W.C;2,01;Toilettes
B3;G116;IMAGING;Radiologie                           ; ????? ?? ??????;W.C;3,72;Toilettes
B3;G117;IMAGING;Radiologie                           ; ????? ?? ??????;WASH;7,96;Sanitaires
B3;G118;IMAGING;Radiologie                           ; ????? ?? ??????;WAITING AREA;63,97;Attente
B3;G119;IMAGING;Radiologie                           ; ????? ?? ??????;FILM STORE;22,15;Stock films
B3;G120;WELNESS;Centre Check up                    ;???? ??????? ?????;PATIENT ROOM;17,46;Lit 036
B3;G121;WELNESS;Centre Check up                    ;???? ??????? ?????;PATIENT ROOM;15,96;Lit 035
B3;G122;WELNESS;Centre Check up                    ;???? ??????? ?????;PATIENT ROOM VIP;16,2;Lit 034
B3;G123;WELNESS;Centre Check up                    ;???? ??????? ?????;PATIENT ROOM;12,82;Lit 033
B3;G124;WELNESS;Centre Check up                    ;???? ??????? ?????;PATIENT ROOM;13,18;Lit 031
B3;G125;WELNESS;Centre Check up                    ;???? ??????? ?????;PATIENT ROOM VIP;14,35;Lit 032
B3;G126;WELNESS;Centre Check up                    ;???? ??????? ?????;W.C;2,85;.
B3;G127;WELNESS;Centre Check up                    ;???? ??????? ?????;W.C;2,85;.
B3;G128;WELNESS;Centre Check up                    ;???? ??????? ?????;W.C;2,26;Toilettes
B3;G129;WELNESS;Centre Check up                    ;???? ??????? ?????;W.C;2,32;Toiettes
B3;G130;WELNESS;Centre Check up;???? ??????? ?????;WASH;7,08;Sanitaires patients
B3;G131;WELNESS;Centre Check up;???? ??????? ?????;CORRIDOR;42,02;.
B3;G132;WELNESS;Centre Check up;???? ??????? ?????;DR;15,21;Salle de consultation 2
B3;G133;WELNESS;Centre Check up;???? ??????? ?????;RECEPTION;11,99;Accueil secrétariat
B3;G134;WELNESS;Centre Check up;???? ??????? ?????;CORRIDOR;48,40;.
B3;G135;WELNESS;Centre Check up;???? ??????? ?????;LINEN ;3,69;Lingerie
B3;G136;IMAGING;Radiologie                           ; ????? ?? ??????;DIRTY HOIST;.;Monte charge Septique (AL13)
B3;G137;IMAGING;Radiologie                           ; ????? ?? ??????;CLEAN HOIST;.;Monte charge stérile (AL14)
B3;G138;WELNESS;Centre Check up;???? ??????? ?????;OFFICE;18,39;Bureau Médecin
B3;G139;WELNESS;Centre Check up;???? ??????? ?????;NURSE STATION;11,37;Poste infirmier
B3;G140;WELNESS;Centre Check up;???? ??????? ?????;HK;9,11;Local ménage
B3;G141;WELNESS;Centre Check up;???? ??????? ?????;WAITING AREA;28,69;Attente Check up
B5;G145;NUCLEAR MEDICINE;Médecine nucléaire            ; ????? ???? ??????;PATIENT ROOM;12,75;Lit 021
B5;G146;NUCLEAR MEDICINE;Médecine nucléaire            ; ????? ???? ??????;TOILET;3,12;.
B5;G148;NUCLEAR MEDICINE;Médecine nucléaire            ; ????? ???? ??????;PATIENT ROOM;13,98;Lit 022
B5;G149;NUCLEAR MEDICINE;Médecine nucléaire            ; ????? ???? ??????;TOILET;3,00;.
B5;G150;NUCLEAR MEDICINE;Médecine nucléaire            ; ????? ???? ??????;INJECTION CUBICLE 1;3,36;Box 1
B5;G151;NUCLEAR MEDICINE;Médecine nucléaire            ; ????? ???? ??????;CONTROL RM;7,20;Salle de contrôle Imagerie
B5;G152;NUCLEAR MEDICINE;Médecine nucléaire            ; ????? ???? ??????;PET SCANNER;39,00;PET SCANNER
B5;G153;NUCLEAR MEDICINE;Médecine nucléaire            ; ????? ???? ??????;CORRIDOR;29,76;.
B5;G154;NUCLEAR MEDICINE;Médecine nucléaire            ; ????? ???? ??????;CORRIDOR;15,16;.
B5;G156;NUCLEAR MEDICINE;Médecine nucléaire            ; ????? ???? ??????;NURSE STATION;7,54;Poste infirmier
B5;G157;NUCLEAR MEDICINE;Médecine nucléaire            ; ????? ???? ??????;PATIENT CLOAK ROOM;5,37;Déshabilloir
B5;G158;NUCLEAR MEDICINE;Médecine nucléaire            ; ????? ???? ??????;INJECTION CUBICLE 4;5,20;Box 4
B5;G160;NUCLEAR MEDICINE;Médecine nucléaire            ; ????? ???? ??????;INJECTION CUBICLE 2;5,20;Box 2
B5;G161;NUCLEAR MEDICINE;Médecine nucléaire            ; ????? ???? ??????;GAMA STRESS;27,05;SPECT CT
B5;G162;NUCLEAR MEDICINE;Médecine nucléaire            ; ????? ???? ??????;CORRIDOR;26,80;.
B5;G163;NUCLEAR MEDICINE;Médecine nucléaire            ; ????? ???? ??????;HOLD;15,39;Réserve
B5;G164;NUCLEAR MEDICINE;Médecine nucléaire            ; ????? ???? ??????;HOT TOILET;3,54;Toilettes Chaudes
B5;G165;NUCLEAR MEDICINE;Médecine nucléaire            ; ????? ???? ??????;HOT TOILET;3,54;Toilettes Chaudes
B5;G167;NUCLEAR MEDICINE;Médecine nucléaire            ; ????? ???? ??????;MANLOCK;2,43;Sas 
B5;G168;NUCLEAR MEDICINE;Médecine nucléaire            ; ????? ???? ??????;CHANGE;2,43;Soins
B5;G169;NUCLEAR MEDICINE;Médecine nucléaire            ; ????? ???? ??????;CLOAK ROOM;10,35;Sas Habillage
B5;G170;NUCLEAR MEDICINE;Médecine nucléaire            ; ????? ???? ??????;CLOAK ROOM;13,03;Sas Habillage
B5;G171;NUCLEAR MEDICINE;Médecine nucléaire            ; ????? ???? ??????;INJECTION;14,89;Salle d'injection
B5;G173;NUCLEAR MEDICINE;Médecine nucléaire            ; ????? ???? ??????;HOT LAB;16,39;Laboratoire chaud
B5;G174;NUCLEAR MEDICINE;Médecine nucléaire            ; ????? ???? ??????;MANLOCK;5,76;Sas 
B5;G175;NUCLEAR MEDICINE;Médecine nucléaire            ; ????? ???? ??????;INJECTION CUBICLE 3;6,08;Box 3
B5;G176;NUCLEAR MEDICINE;Médecine nucléaire            ; ????? ???? ??????;OFFICE;8,60;Bureau
B5;G178;NUCLEAR MEDICINE;Médecine nucléaire            ; ????? ???? ??????;RECEPTION;9,04;Accueil secrétariat
B5;G179;CIRCULATION;Couloir                         ;??? ;CORRIDOR;8,93;.
B5;G180;CIRCULATION;Couloir                         ;??? ;WAITING;10,42;Attente Médecine Nucléaire
B5;G181;DAYCARE MEDICAL INVESTIGATION;Hopital de jour Médecine              ;.?????? ??????;WAITING;8,80;Attente
B5;G182;DAYCARE MEDICAL INVESTIGATION;Hopital de jour Médecine              ;.?????? ??????;PATIENT CUB 1;9,51;Lit 011
B5;G183;DAYCARE MEDICAL INVESTIGATION;Hopital de jour Médecine              ;.?????? ??????;PATIENT CUB 2;9,51;Lit 012
B5;G184;DAYCARE MEDICAL INVESTIGATION;Hopital de jour Médecine              ;.?????? ??????;PATIENT CUB 3;12,08;Lit 013
B5;G185;DAYCARE MEDICAL INVESTIGATION;Hopital de jour Médecine              ;.?????? ??????;PATIENT CUB 4;11,03;Lit 014
B5;G186;DAYCARE MEDICAL INVESTIGATION;Hopital de jour Médecine              ;.?????? ??????;PATIENT CUB 5;11,06;Lit 015
B5;G187;DAYCARE MEDICAL INVESTIGATION;Hopital de jour Médecine              ;.?????? ??????;PATIENT CUB 6;11,03;Lit 016
B5;G188;DAYCARE MEDICAL INVESTIGATION;Hopital de jour Médecine              ;.?????? ??????;PATIENT CUB 7;11,81;Lit 017
B5;G189;DAYCARE MEDICAL INVESTIGATION;Hopital de jour Médecine              ;.?????? ??????;PATIENT CUB 8;12,51;Lit 018
B5;G190;DAYCARE MEDICAL INVESTIGATION;Hopital de jour Médecine              ;.?????? ??????;PATIENT CUB 9;9,67;Lit 019
B5;G192;DAYCARE MEDICAL INVESTIGATION;Hopital de jour Médecine              ;.?????? ??????;CORRIDOR;23,04;.
B5;G193;DAYCARE MEDICAL INVESTIGATION;Hopital de jour Médecine              ;.?????? ??????;CORRIDOR;16,05;.
B5;G194;DAYCARE MEDICAL INVESTIGATION;Hopital de jour Médecine              ;.?????? ??????;NURSE STATION;16,79;Poste infirmier
B5;G195;DAYCARE MEDICAL INVESTIGATION;Hopital de jour Médecine              ;.?????? ??????;PARKING;6,75;Parking Chariots
B5;G196;DAYCARE MEDICAL INVESTIGATION;Hopital de jour Médecine              ;.?????? ??????;C/U;4,77;Local propre
B5;G197;DAYCARE MEDICAL INVESTIGATION;Hopital de jour Médecine              ;.?????? ??????;D/U;5,14;Local sale
B5;G199;DAYCARE MEDICAL INVESTIGATION;Hopital de jour Médecine              ;.?????? ??????;CORRIDOR;22,38;.
B5;G200;DAYCARE MEDICAL INVESTIGATION;Hopital de jour Médecine              ;.?????? ??????;CORRIDOR;16,27;.
B5;G201;DAYCARE MEDICAL INVESTIGATION;Hopital de jour Médecine              ;.?????? ??????;RECEPTION;11,57;Acceuil Hopital de jour medical
B5;G202;DAYCARE MEDICAL INVESTIGATION;Hopital de jour Médecine              ;.?????? ??????;OFFICE;7,00;Bureau
B5;G203;DAYCARE MEDICAL INVESTIGATION;Hopital de jour Médecine              ;.?????? ??????;REST;8,25;repos infirmiers
B5;G204;CIRCULATION;Couloir                         ;??? ;STORE;1,59;Réserve
B5;G205;DAYCARE MEDICAL INVESTIGATION;Hopital de jour Médecine              ;.?????? ??????;WC/ST;4,72;Toilettes 1
B5;G206;DAYCARE MEDICAL INVESTIGATION;Hopital de jour Médecine              ;.?????? ??????;WC/ST;5,00;Toilettes 2
B5;G208;DIAGNOSTIC LABORATORY;Laboratoires                                         ;.????? ;POST-PCR;17,08;PCR
B5;G209;DIAGNOSTIC LABORATORY;Laboratoires                                         ;.????? ;STORE/SLIDES;4,96;Archives Lames et Blocs
B8;G211;OPD;.;.;STORE;1,10;?
B5;G212;DIAGNOSTIC LABORATORY;Laboratoires                                        ;. ????? ;HISTO/CYTO;60,20;Anatomie pathologique
B5;G214;DIAGNOSTIC LABORATORY;Laboratoires                                        ;. ????? ;CORRIDOR;69,30;.
B5;G215;DIAGNOSTIC LABORATORY;Laboratoires                                        ;.  ?????;MEDIA;15,53;Salle de milieux
B5;G216;DIAGNOSTIC LABORATORY;Laboratoires                                        ;. ????? ;MICROBIOLOGY;40,15;Bactériologie parasitologie
B5;G217;DIAGNOSTIC LABORATORY;Laboratoires                                      ;   ????? ;STORE;6,68;Réserve
B5;G218;DIAGNOSTIC LABORATORY;Laboratoires                                       ;.  ????? ;RECEPTION;18,42;Acceuil Secrétariat
B5;G219;DIAGNOSTIC LABORATORY;Laboratoires                                       ;.  ????? ;PATHOLOGIST OFFICE;10,51;Bureau 8
B5;G221;DIAGNOSTIC LABORATORY;Laboratoires                                        ;. ????? ;GLASS/WASH STERILIZER;13,16;Laverie
B5;G222;DIAGNOSTIC LABORATORY;Laboratoires                                        ;. ????? ;FLOW CYTOMETRY;13,65;Cytométrie en flux
B5;G223;DIAGNOSTIC LABORATORY;Laboratoires                                    ;     ????? ;COLD STORE;13,17;Chambre froide
B5;G225;DIAGNOSTIC LABORATORY;Laboratoires                                      ;.   ????? ;SEROLOGY;22,86;Sérologie
B5;G226;DIAGNOSTIC LABORATORY;Laboratoires                                      ;.   ????? ;IMMUNOLOGY;18,37;Immunologie
B5;G227;DIAGNOSTIC LABORATORY;Laboratoires                                      ;.   ????? ;HEMATOLOGY HEMOSTASIS;27,74;Hematocytologie et Hemostase
B5;G228;DIAGNOSTIC LABORATORY;Laboratoires                                    ;     ????? ;CLINICAL CHEMISTRY;22,86;Biochimie
B5;G229;DIAGNOSTIC LABORATORY;Laboratoires                                       ;.  ????? ;WC;2,25;Toilettes
B5;G230;DIAGNOSTIC LABORATORY;Laboratoires                                       ;.  ????? ;WC;2,25;Toilettes
B5;G231;DIAGNOSTIC LABORATORY;Laboratoires                                      ;   ????? ;STORE;13,45;Réserve
B5;G232;DIAGNOSTIC LABORATORY;Laboratoires                                       ;.  ????? ;SAMPLE PROCESSING;5,21;Pré analyse
B5;G233;DIAGNOSTIC LABORATORY;Laboratoires                                      ;   ????? ;STORE;10,45;Réserve
B5;G234;DIAGNOSTIC LABORATORY;Laboratoires                                       ;.  ????? ;DELIVERY OF SAMPLES;13,63;Reception prélèvements
B5;G236;DIAGNOSTIC LABORATORY;Laboratoires                                       ;.  ????? ;RECEPTION;15,73;Accueil général
B5;G237;DIAGNOSTIC LABORATORY;Laboratoires                                      ;   ????? ;STORE;5,11;Réserve
B5;G238;DIAGNOSTIC LABORATORY;Laboratoires                                       ;.  ????? ;APHAERESIS AND BLOOD DONATION;14,30;Banque du sang et hémovigilance
B5;G239;DIAGNOSTIC LABORATORY;Laboratoires                                       ;.  ????? ;LOUNGE;10,86;Espace détente
B5;G240;DIAGNOSTIC LABORATORY;Laboratoires                                       ;.  ????? ;ADMINISTRATIVE SPACE;8,37;Bureau 7
B5;G241;DIAGNOSTIC LABORATORY;Laboratoires                                        ;. ????? ;ADMINISTRATIVE SPACE;8,37;Bureau 6
B5;G242;DIAGNOSTIC LABORATORY;Laboratoires                                        ;. ????? ;ADMINISTRATIVE SPACE;11,41;Bureau 5
B5;G243;DIAGNOSTIC LABORATORY;Laboratoires                                        ;. ????? ;ADMINISTRATIVE SPACE;11,74;Bureau 4
B5;G244;DIAGNOSTIC LABORATORY;Laboratoires                                        ;. ????? ;ADMINISTRATIVE SPACE;13,69;Bureau 3
B5;G245;DIAGNOSTIC LABORATORY;Laboratoires                                        ;. ????? ;ADMINISTRATIVE SPACE;12,25;Bureau 2
B5;G246;DIAGNOSTIC LABORATORY;Laboratoires                                        ;. ????? ;HEAD OFFICE;13,56;Bureau 1
B5;G247;DIAGNOSTIC LABORATORY;Laboratoires                                        ;. ????? ;SECRETARIES;13,13;Secrétariat
B5;G248;DIAGNOSTIC LABORATORY;Laboratoires                                        ;. ????? ;BLOOD BANK;21,95;.
B5;G249;DIAGNOSTIC LABORATORY;Laboratoires                                        ;. ????? ;RECEPTION/WAITING;25,58;Attente
B5;G250;DIAGNOSTIC LABORATORY;Laboratoires                                        ;. ????? ;CORRIDOR;74,20;.
B2;G251;ADMIN DEPARTMENT;Administration                                  ;  ???????  ;VIP VISITOR RECEIVING AND WAITING;22,59;Accueil et attente patients VIP
B2;G252;MAIN ENTRANCE;Entrée principale                      ;???? ?????;PATIENTS & VISITORS LOUNGE;40,92;Salon visiteurs
B2;G253;CIRCULATION;Couloir                         ;??? ;RECEPTION INFORMATION & ATRIUM;141,33;.
B2;G254;CIRCULATION;Couloir                         ;??? ; ENTRANCE LOBBY;15,56;.
B2;G255;CIRCULATION;Couloir                         ;??? ;CORRIDOR;149,75;.
B2;G256;OPD;Atrium;.;STORE;4,53;Réserve Caféteria
B2;G257;OPD;Atrium;?????? ?????? ;SERVERY;9,22;Caféteria
B2;G258;CIRCULATION;Couloir                         ;??? ;DIS.W.C.;5,81;Sanitaires handicapés
B2;G259;CIRCULATION;Acceuil                                            ; ???????;PAT.W.C;6,90;Sanitaires hommes
B2;G260;CIRCULATION;Couloir                         ;??? ;PAT.W.C;6,74;Sanitaires femmes
B1;G261;CIRCULATION;Couloir                         ;??? ;SHOP;45,96;Espace services
B2;G262;CIRCULATION;Couloir                         ;??? ;CAFÉ;53,06;Caféteria de l'Atrium
B2;G263;OPD;Centre de Dermatologie;?????? ?????? ; EXAM;17,91;Salle de Consultation 1
B2;G264;OPD;Centre de Dermatologie;?????? ?????? ; EXAM;13,41;Salle de Consultation 2
B4;G265;OPD;Centre de Dermatologie;?????? ?????? ;CONSULT. TREATMENT;15,55;Salle de Soins
B2;G266;OPD;Centre de Dermatologie;?????? ?????? ;RECEPTION;5,13;Acceuil secrétariat
B2;G267;OPD;Centre de Dermatologie;?????? ?????? ;WAITING;44,89;Attente
B2;G268;OPD;Centre de Dermatologie;?????? ?????? ;PUVA;9,08;UV Thérapie
B2;G269;OPD;Centre de Dermatologie;?????? ?????? ;PUVA;8,33;Laser 1
B2;G270;OPD;Centre de Dermatologie;?????? ?????? ;LASER;8,33;Laser 2
B4;G271;OPD;Centre de Prélévements;?????? ?????? ;EXAM 3;4,63;Salle de prélevement 1
B4;G272;OPD;Centre de Prélévements;?????? ?????? ;BLOOD DRAWING 1;4,33;Salle de prélevement 1
B4;G273;OPD;Centre de Prélévements;?????? ?????? ;EXAM 1;4,50;Salle de prélevement 1
B4;G274;OPD;Centre de Prélévements;?????? ?????? ;EXAM 4;4,50;Salle de prélevement 1
B4;G275;OPD;Centre de Prélévements;?????? ?????? ;WAITING;51,72;Attente
B4;G276;OPD;Centre de Prélévements;?????? ?????? ;RECEPTION;8,98;Acceuil Secrétariat
B4;G277;OPD;Centre de Prélévements;?????? ?????? ;W.C.;2,34;Toilettes
B4;G278;OPD;Centre de Prélévements;?????? ?????? ;SPECIMEN;1,98;Remise échantillons
B4;G279;OPD;Centre de Prélévements;?????? ?????? ;D/U URINE;5,40;Salle Prelevements Septiques
B4;G280;OPD;Centre de Prélévements;?????? ?????? ;BLOOD SAMPLE;5,55;Salle technique prélevements
B4;G281;OPD;Centre de Neurologie;?????? ?????? ;EXAM;13,13;Salle de Consultation 1
B4;G282;OPD;Centre de Neurologie;?????? ?????? ;EXAM;13,13;Salle de Consultation 2
B4;G283;OPD;Centre de Neurologie;?????? ?????? ;RECEPTION;10,40;Accueil Secrétariat
B4;G284;OPD;Centre de Neurologie;?????? ?????? ;STORE;3,2;Reserve
B4;G285;OPD;Centre de Neurologie;?????? ?????? ;EEG;9,34;Electroencéphalographie
B4;G286;OPD;Centre de Neurologie;?????? ?????? ;EGM;9,34;Electromyographie
B4;G287;CIRCULATION;Couloir                         ;??? ;LOBBY;55,64;.
B4;G288;HOSPITAL STREET;Couloir principal                        ;??? ?????;STAIR-05;25,88;Escalier 5
B6;G289;OUTPATIENT & VISITOR CIRCULATION;Circulations patient et visiteurs                           ;???? ?????? ? ??????;W.C;3,47;Toielttes
B6;G290;OUTPATIENT & VISITOR CIRCULATION;Circulations patient et visiteurs                           ;???? ?????? ? ??????;W.C;4,70;Toilettes handicapés
B4;G291;OPD;Centre Ophtalmologie;?????? ?????? ;LASER TREATMENT;11,45;Laser
B6;G292;OPD;Centre Ophtalmologie;?????? ?????? ;VISION FIELD TEST;9,41;Champs Visuel
B4;G293;OPD;Centre Ophtalmologie;?????? ?????? ;RETINAL SCREENING;8,08;Ahngiographie et MCO
B4;G294;OPD;Centre Ophtalmologie;?????? ?????? ;TONOMETRY;8,64;Tonometrie
B6;G295;OPD;Centre Ophtalmologie;?????? ?????? ;CORRIDOR;50,39;.
B4;G296;OPD;Centre Ophtalmologie;?????? ?????? ;W.C;2,48;Toilelttes
B4;G297;OPD;Centre Ophtalmologie;?????? ?????? ;EXAM 1;12,29;Salle de consultation 1
B4;G298;OPD;Centre Ophtalmologie;?????? ?????? ;STORE;4,63;Réserve
B4;G299;OPD;Centre Ophtalmologie;?????? ?????? ;EXAM 2;12,25;Salle de consultation 2
B6;G300;OPD;Centre Ophtalmologie;?????? ?????? ;EXAM 3;12,25;Salle de consultation 3
B6;G301;OPD;Centre ORL;?????? ?????? ;RECEPTION;6,53;Acceuil secrétariat
B6;G302;OPD;Centre ORL;?????? ?????? ;WAITING;18,24;Attente
B6;G303;OPD;Centre ORL;?????? ?????? ;MICROSCOPY;13,05;Salle d'examen microscopique
B6;G304;OPD;Centre ORL;?????? ?????? ;TREATMENT RM;8,86;Salle de traitement
B6;G305;OPD;Centre ORL;?????? ?????? ;W.C.;3,16;Toilettes
B6;G306;OPD;Centre ORL;?????? ?????? ;AUDIOMETRY;10,73;Audiométrie
B6;G307;OPD;Centre ORL;?????? ?????? ;EXAM 1;10,83;Salle de consultation 1
B6;G308;OPD;Centre ORL;?????? ?????? ;EXAM 2;14,48;Salle de consultation 2
B6;G309;OPD;Centre ORL;?????? ?????? ;DISPOSAL   ;2,48;Local Sale
B6;G310;CIRCULATION;Couloir                         ;??? ;LIFT LOBBY;48,27;Hall Ascenseurs
B6;G311;CIRCULATION;Circulation;  ???????  ;STAIR  06;25,19;Escalier 6
B6;G312;OPD;Consultations externes      ;?????? ?????? ;PANTRY;10,80;Office
B6;G313;OPD;Consultations externes                                   ;?????? ?????? ;W.C;3,06;Toilettes
B6;G314;OPD;Consultations externes                                   ;?????? ?????? ;W.C;2,89;Toilettes
B6;G315;OPD;Consultations externes                                   ;?????? ?????? ;CORRIDOR;14,98;Zone Médicale - Accès réservé
B6;G316;OPD;Consultations externes                                   ;?????? ?????? ;MEETING ROOM;17,57;Salle de réunion
B6;G317;OPD;Consultations externes                                   ;?????? ?????? ;MEETING ROOM;17,33;Salle de réunion
B6;G318;OPD;Consultations externes                                   ;?????? ?????? ;OFFICE;7,96;Bureau
B6;G319;OPD;Consultations externes                                   ;?????? ?????? ;OFFICE;8,12;Bureau
B6;G320;CIRCULATION;Couloir                         ;??? ;CORRIDOR;137,68;Couloir médical
B4;G321;CIRCULATION;Couloir                         ;??? ;CORRIDOR;145,47;Couloir médical
B2;G322;CIRCULATION;Couloir                         ;??? ;CORRIDOR;142,11;Couloir médical
B2;G323;CIRCULATION;Couloir                         ;??? ;CORRIDOR;39,52;Couloir visiteurs
B4;G324;CIRCULATION;Couloir                         ;??? ;CORRIDOR;85,74;Couloir visiteurs
B4;G325;CIRCULATION;Couloir                         ;??? ;CORRIDOR;78,52;Couloir visiteurs
B7;G326;OPD RECEPTION;Accueil Consultations externes                                   ;??????? / ?????? ??????;CORRIDOR;64,74;Accès Consultations externes/Accès Atrium
B7;G327;ADMIN DEPARTMENT;Administration                                  ;  ???????  ;WAITING IP ADMITTING;45,19;Admissions Patients Hospitalisés
B7;G328;ADMIN DEPARTMENT;Administration                                  ;  ???????  ;WAITING IP DISCHARGE;49,28;Sorties Patients ambulatoires
B7;G329;ADMIN DEPARTMENT;Administration                                  ;  ???????  ;CORRIDOR;45,09;.
B7;G330;ADMIN DEPARTMENT;Administration                                  ;  ???????  ;BACK OFFICE 1;3,97;Box 1
B7;G331;ADMIN DEPARTMENT;Administration                                  ;  ???????  ;BACK OFFICE 2;3,76;Box 2
B7;G332;ADMIN DEPARTMENT;Administration                                  ;  ???????  ;BACK OFFICE 3;4,08;Box 3
B7;G333;ADMIN DEPARTMENT;Administration                                  ;  ???????  ;BACK OFFICE 4;4,08;Box 4
B7;G334;ADMIN DEPARTMENT;Administration                                  ;  ???????  ;BACK OFFICE 5;4,21;Box 5
B7;G335;ADMIN DEPARTMENT;Administration                                  ;  ???????  ;BACK OFFICE 6;3,97;Box 6
B7;G336;ADMIN DEPARTMENT;Administration                                  ;  ???????  ;BACK OFFICE 7;3,76;Box 7
B7;G337;ADMIN DEPARTMENT;Administration                                  ;  ???????  ;BACK OFFICE 8;4,08;Box 8
B7;G338;ADMIN DEPARTMENT;Administration                                  ;  ???????  ;BACK OFFICE 9;4,08;Box 9
B7;G339;ADMIN DEPARTMENT;Administration                                  ;  ???????  ;BACK OFFICE 10;4,21;Box 10
B7;G340;ADMIN DEPARTMENT;Administration                                  ;  ???????  ;OFFICE (SOPER/MGR);14,66;Superviseur Admission facturation
B7;G341;ADMIN DEPARTMENT;Administration                                  ;  ???????  ;OFFICE (FINANCE);18,06;Chef service fAdmission Facturation
B7;G342;ADMIN DEPARTMENT;Administration                                  ;  ???????  ;OUT PATIENT REGISTRATION & WAITING;103,31;Admission Facturation Ambulatoires
B7;G343;CIRCULATION;Couloir                         ;??? ;RECEPTION;26,84;Acceuil Pricipal Consultations externes
B7;G344;CIRCULATION;Couloir                         ;??? ;ENTRANCE LOBBY;13,36;Entrée Consulations Externes
B7;G345;OPD RECEPTION;Attente principale                                  ;??????? / ?????? ??????;WAITING HALL;132,97;Attente pricipale Consultations externes
B7;G346;OPD RECEPTION;Accueil Consultations externes                                   ;??????? / ?????? ??????;STAIR-07;24,50;Escalier 7
B7;G347;CIRCULATION;Couloir                         ;??? ;BABY CH.;4,76;Change couches
B7;G348;CIRCULATION;Couloir                         ;??? ;DIS.W.C.;7,50;Salle de consultation 2
B7;G349;CIRCULATION;Couloir                         ;??? ;LOBBY;19,23;.
B8;G350;CIRCULATION;Couloir                         ;??? ;RECEPTION/OFFICE;10,21;Acceuil secrétariat Consultations de Médecine
B8;G351;CIRCULATION;Couloir                         ;??? ;PAT.W.C;5,95;Toilettes
B8;G352;CIRCULATION;Couloir                         ;??? ;PAT.W.C;5,86;Toilettes
B8;G353;OPD;Consultations externes Médecine                                 ;?????? ?????? ; EXAM 1;16,61;Salle de consultation 1
B8;G354;OPD;Consultations externes Médecine                                 ;?????? ?????? ; EXAM 2;21,84;Salle de consultation 2
B8;G355;OPD;Consultations externes Médecine                                 ;?????? ?????? ; EXAM 3;19,32;Salle de consultation 3
B8;G356;OPD;Consultations externes Médecine                                 ;?????? ?????? ; EXAM 4;13,03;Salle de consultation 4
B8;G357;OPD;Consultations externes Médecine                                 ;?????? ?????? ; EXAM 5;13,12;Salle de consultation 5
B8;G358;CIRCULATION;Couloir                         ;??? ;WAITING/CORRIDOR;120,50;Attente Consultations Médecine et Chirurgie
B8;G359;CIRCULATION;Couloir                         ;??? ;RECEPTION/OFFICE;12,31;Acceuil secrétariat Consultations de Chirurgie
B8;G360;OPD;Consultations externes Chirurgie                                 ;?????? ?????? ;TREATMENT RM;15,46;Salle de soins chirurgicaux
B8;G361;OPD;Consultations externes Chirurgie                                 ;?????? ?????? ; EXAM 4;13,16;Salle de consultation 4
B8;G362;OPD;Consultations externes Chirurgie                                 ;?????? ?????? ; EXAM 3;18,54;Salle de consultation 3
B8;G363;OPD;Consultations externes Chirurgie                                 ;?????? ?????? ; EXAM 2;18,14;Salle de consultation 2
B8;G364;OPD;Consultations externes Chirurgie                                 ;?????? ?????? ; EXAM 1;13,03;Salle de consultation 1
B8;G365;OPD;Consultations externes                                   ;?????? ?????? ;C/U;6,23;Local propre
B8;G366;OPD;Consultations externes                                   ;?????? ?????? ;D/U   ;6,32;Local sale
B8;G367;CIRCULATION;Couloir                         ;??? ;LOBBY/WAITING;150,19;Attnete Cardiologie - Ascenseurs Niveau 1
B8;G368;OPD;Consultations externes Cardiologie                                 ;?????? ?????? ;RECEPTION;11,40;Acceuil Secrétariat Cardiologie
B8;G369;OPD;Consultations externes Cardiologie ;?????? ?????? ;W.C;3,68;Sanitaires
B8;G370;OPD;Consultations externes Cardiologie                                 ;?????? ?????? ;W.C;5,24;Sanitaires
B8;G371;OPD;Consultations externes Cardiologie                                 ;?????? ?????? ;CORRIDOR;11,16;Salles de consultation de cardiologie
B8;G372;OPD;Consultations externes Cardiologie                                 ;?????? ?????? ; EXAM 1;17,34;Salle de consultation 1
B8;G373;OPD;Consultations externes Cardiologie                                 ;?????? ?????? ; EXAM 2;19,22;Salle de consultation 2
B8;G374;OPD;Consultations externes Cardiologie                                 ;?????? ?????? ;ECG;13,17;Salle électrocardiographie
B8;G375;OPD;Consultations externes Cardiologie                                 ;?????? ?????? ;ECG;11,61;Salle de consultation 3
B8;G376;OPD;Consultations externes Gynéco-Obstétricales et Pédiatriques                                ;?????? ?????? ; EXAM 4;17,34;Salle de consultation 4
B8;G377;OPD;Consultations externes Gynéco-Obstétricales et Pédiatriques                                ;?????? ?????? ; EXAM 3;19,22;Salle de consultation 3
B8;G378;OPD;Consultations externes Gynéco-Obstétricales et Pédiatriques                                ;?????? ?????? ; EXAM 2;10,47;Salle de consultation 2
B8;G379;OPD;Consultations externes Gynéco-Obstétricales et Pédiatriques                                ;?????? ?????? ; EXAM 1;14,61;Salle de consultation 1
B8;G380;OPD;Consultations externes Gynéco-Obstétricales et Pédiatriques                                ;?????? ?????? ;WAITING;14,45;Attenete gynécologie obstétrique
B8;G381;OPD;Consultations externes Gynéco-Obstétricales et Pédiatriques                                ;?????? ?????? ;NURSE STATION;16,82;Accueil secrétariat
B8;G382;OPD;Consultations externes Gynéco-Obstétricales et Pédiatriques                                ;?????? ?????? ;WAITING;15,30;Attente pédiatrique
B8;G383;OPD;Consultations externes Gynéco-Obstétricales et Pédiatriques                                ;?????? ?????? ;OP/GYN/PAEDIATRIC TREATMENT RM;11,33;Salle de soins
B8;G384;OPD;Consultations externes Gynéco-Obstétricales et Pédiatriques                                ;?????? ?????? ;CORRIDOR;25,29;Consultations pédiatriques
B8;G385;OPD;Consultations externes Gynéco-Obstétricales et Pédiatriques                                ;?????? ?????? ; EXAM 3;13,77;Consultation 3
B8;G386;OPD;Consultations externes Gynéco-Obstétricales et Pédiatriques                                ;?????? ?????? ; EXAM 2;10,79;Consultation 2
B8;G387;OPD;Consultations externes Gynéco-Obstétricales et Pédiatriques                                ;?????? ?????? ; EXAM 1;11,08;Consultation 1
B8;G388;OPD;Consultations externes Gynéco-Obstétricales et Pédiatriques                                ;?????? ?????? ;BABY CH.;2,58;Change couches
B8;G389;OPD;Consultations externes Gynéco-Obstétricales et Pédiatriques                                ;?????? ?????? ;TOY STORE;1,53;Réserve
B8;G390;OPD;Consultations externes Gynéco-Obstétricales et Pédiatriques                                ;?????? ?????? ;CORRIDOR;48,62;Consultations gynecoobstétricales
B8;G391;OPD;Consultations externes Gynéco-Obstétricales et Pédiatriques                                ;?????? ?????? ;DIS.W.C;3,60;Toielttes handicapés
B8;G392;OPD;Consultations externes Gynéco-Obstétricales et Pédiatriques                                ;?????? ?????? ;PAT.W.C;2,34;Toilettes
B8;G393;OPD;Consultations externes Gynéco-Obstétricales et Pédiatriques                                ;?????? ?????? ;PAT.W.C;2,15;Toilettes
B8;G394;OPD;Consultations externes                                   ;?????? ?????? ;C/U;6,77;Local propre
B8;G395;OPD;Consultations externes                                   ;?????? ?????? ;D/U;6,76;Local Sale
B8;G396;OPD;Consultations externes                                   ;?????? ?????? ;H.K ;4,11;Local ménage
B8;G397;OPD;Consultations externes                                   ;?????? ?????? ;STAFF REST;10,93;Salle de repos
B8;G398;OPD;Centre de Cardiologie                               ;?????? ?????? ;CORRIDOR;18,10;Consultations de cardiologie
B8;G399;MINOR OP LOBBY;Hall                                             ;   ???;STAIR-08;24,50;Escalier 8
B8;G400;CIRCULATION;Couloir                         ;??? ;LOBBY;17,07;.
B8;G401;CIRCULATION;Couloir                         ;??? ;STAFF.WC;2,67;.
B8;G402;CIRCULATION;Couloir                         ;??? ;STAFF.WC;3,01;.
B12;G403;OPD;Centre de Pneumologie               ;????? ????? ?????;RECEPTION/OFFICE;10,42;Acceuil Secrétriat centre de pneumologie
B8;G404;CIRCULATION;Couloir                         ;??? ;WASH;3,54;Toilettes hommes
B12;G405;OPD;Centre de Pneumologie                         ;?????? ?????? ;STORE;18,25;Réserve
B12;G406;OPD;Centre de Pneumologie        ;?????? ?????? ; EXAM 1;12,52;Salle de consultation 1
B12;G407;OPD;Centre de Pneumologie        ;?????? ?????? ; EXAM 2;20,66;Salle de consultation 2
B12;G408;OPD;Centre de Pneumologie        ;?????? ?????? ; EXAM 3;20,46;Salle de consultation 3
B12;G409;OPD;Centre de Pneumologie        ;?????? ?????? ;SPIROMETRY;11,47;Explorations fonctionnelles respiratoires
B12;G410;OPD;Centre de Pneumologie        ;?????? ?????? ;TREATMENT/REST;11,47;Salle de soins
B12;G411;OPD;Consultations externes                                   ;?????? ?????? ;C/U;6,54;Local propre
B12;G412;OPD;Consultations externes                                   ;?????? ?????? ;D/U;4,92;Local sale
B12;G413;CIRCULATION;Couloir                         ;??? ;CORRIDOR;76,30;.
B12;G414;OPD;Attente Urologie et Pneumologie       ;????? ????? ?????? ?????? ?????? ????? ?????;WAITING;35,59;Attente patients
B12;G415;OPD;Centre d'Urologie                                  ;?????? ?????? ;LITHOTRIPSY;22,13;Lithotripsie
B12;G416;OPD;Centre d'Urologie                                  ;?????? ?????? ;EXAM 1;19,87;Salle de consulation 1
B12;G417;OPD;Centre d'Urologie                                  ;?????? ?????? ;EXAM 2;21,10;Salle de consultation 2
B12;G418;OPD;Centre d'Urologie                                  ;?????? ?????? ;URODYNAMICS;21,73;Explorations urodynamiques
B12;G419;OPD;Consultations externes                                   ;?????? ?????? ;DIS. W.C.;3,91;Toielttes handicapés
B12;G420;OPD;Consultations externes                                   ;?????? ?????? ;BABY CH.;3,73;Change couches
B12;G421;OPD;Centre d'Urologie                                  ;????? ????? ?????? ?????? ;RECEPTION/OFFICE;10,30;Acceuil Secrétariat Centre d'Urologie
B12;G422;CIRCULATION;Couloir                         ;??? ;LOBBY;17,18;.
B12;G423;GASTROENTEROLOGY;Consultations de gastroentérologie;????? ????? ?????? ??????;STAIR-09;24,50;Escalier 9
B12;G424;GASTROENTEROLOGY;Consultations de gastroentérologie;????? ????? ?????? ??????;PAT.W.C;2,95;Toilettes
B12;G425;GASTROENTEROLOGY;Consultations de gastroentérologie;????? ????? ?????? ??????;PAT.W.C;2,79;Toilettes
B12;G426;OPD;Attente Gastroentérologie;?????? ?????? ;WAITING;36,70;Attente gastroentérologie et endoscopie
B12;G427;OPD;Centre de Gastro-entérologie;?????? ?????? ;RECEPTION;8,65;Accueil Secretariat
B12;G428;OPD;Centre de Gastro-entérologie;?????? ?????? ; EXAM 1;16,30;Salle de consultation 1
B12;G429;OPD;Centre de Gastro-entérologie;?????? ?????? ; EXAM 2;14,75;Salle de consultation 2
B12;G430;OPD;Centre de Gastro-entérologie;?????? ?????? ;C/U;8,54;Local propre
B12;G431;OPD;Centre de Gastro-entérologie;?????? ?????? ;D/U;9,57;Local Sale
B12;G432;OPD;Centre de Gastro-entérologie;?????? ?????? ;OFFICE;8,70;Bureau
B12;G433;OPD;Centre de Gastro-entérologie;?????? ?????? ;OFFICE;13,74;Bureau
B12;G434;OPD;Centre de Gastro-entérologie;?????? ?????? ;PHMETRY;16,47;Explorations fonctionelles digestives
B12;G435;OPD;Centre de Gastro-entérologie;?????? ?????? ;EXAM;16,00;Salle de consultation 3
B12;G436;OPD;Centre d'Endoscopie;?????? ?????? ;CORRIDOR;40,11;.
B9;G437;OPD;Centre d'Endoscopie;?????? ?????? ;PAT.W.C;4,01;Toilettes
B9;G438;OPD;Centre d'Endoscopie;?????? ?????? ;PAT.W.C;3,57;Toielttes
B9;G439;OPD;Centre d'Endoscopie;?????? ?????? ;SCRUB UP;3,06;.
B9;G440;OPD;Centre d'Endoscopie;?????? ?????? ;NURSE STATION;11,86;Poste infirmier
B9;G441;OPD;Centre d'Endoscopie;?????? ?????? ;ENDOSCOPY 1;35,89;Salle d'endoscopie 1
B9;G442;OPD;Centre d'Endoscopie;?????? ?????? ;ENDOSCOPY 2;21,11;Salle d'endoscopie 2
B9;G443;OPD;Centre d'Endoscopie;?????? ?????? ;ENDOSCOPY 3;21,11;Salle d'endoscopie 3
B9;G444;OPD;Centre d'Endoscopie;?????? ?????? ;ENDOSCOPY CORRIDOR;24,88;Couloir technique
B9;G445;OPD;Centre d'Endoscopie;?????? ?????? ;ENDOSCOPY 4;16,47;Salle d'endoscopie 4
B9;G446;OPD;Centre d'Endoscopie;?????? ?????? ;DECONTAMINATION RM;11,73;Salle de décontamination
B9;G447;OUTPATIENT & VISITOR CIRCULATION;Circulations patient et visiteurs                           ;???? ?????? ? ??????;DISPOSAL;4,81;Local déchets
B9;G448;OPD;Centre d'Endoscopie;?????? ?????? ;STERILIZATION RM;11,64;Salle de stérilisation
B9;G449;OPD;Centre d'Endoscopie;?????? ?????? ;CORRIDOR;65,17;Centre d'endoscopie
B9;G450;OUTPATIENT & VISITOR CIRCULATION;Circulations patient et visiteurs                           ;???? ?????? ? ??????;H.K;3,67;Local ménage
B9;G451;OPD;Centre d'Endoscopie;?????? ?????? ;W.C/SH.;5,67;Toilettes
B9;G452;OPD;Centre d'Endoscopie;?????? ?????? ;PATIENT ROOM. 8;17,73;Lit 048
B9;G453;OPD;Centre d'Endoscopie;?????? ?????? ;PATIENT ROOM. 7;19,62;Lit 047
B9;G454;OPD;Centre d'Endoscopie;?????? ?????? ;W.C./SH.;3,08;Toilettes
B9;G455;OPD;Centre d'Endoscopie;?????? ?????? ;PATIENT CUB 6;10,74;Lit 046
B9;G456;OPD;Centre d'Endoscopie;?????? ?????? ;PATIENT CUB 5;10,74;Lit 045
B9;G457;OPD;Centre d'Endoscopie;?????? ?????? ;PATIENT CUB 4;10,74;Lit 044
B9;G458;OPD;Centre d'Endoscopie;?????? ?????? ;PATIENT CUB 3;10,74;Lit 043
B9;G459;OPD;Centre d'Endoscopie;?????? ?????? ;PATIENT CUB 2;10,74;Lit 042
B9;G460;OPD;Centre d'Endoscopie;?????? ?????? ;PATIENT CUB 1;10,94;Lit 041
B5;G461;DIAGNOSTIC LABORATORY;Laboratoires                                      ;   ????? ;STAIR-03;29,77;Escalier 3
B5;G462;CIRCULATION;Couloir                         ;??? ;CORRIDOR;48,54;?
B1;G463;REHABILITATION;Rééducation                    ;????? ??????? ?????;LINEN;4,36;Lingerie
B3;G464;IMAGING;Radiologie                           ; ????? ?? ??????;W.C;1,87;Toilelttes
B1;G465;RECEPTION INFORMATION;Acceuil                                            ; ???????;VIP LIFT-01;.;Ascenseur VIP (AL1)
B2;G466;RECEPTION INFORMATION;Acceuil                                            ; ???????;LIFT-02;.;Ascenseur visiteurs 2 (AL2)
B2;G467;RECEPTION INFORMATION;Acceuil                                            ; ???????;LIFT-03;.;Ascenseur visiteurs 1 (AL3)
B4;G468;HOSPITAL STREET;Couloir principal                        ;??? ?????;LIFT-04(GOOD'S LIFT);.;Monte charge sale 1 (AL5)
B4;G469;HOSPITAL STREET;Couloir principal                        ;??? ?????;LIFT-05;.;Monte charge propre 1 (AL6)
B4;G470;HOSPITAL STREET;Couloir principal                        ;??? ?????;LIFT-06;.;Monte malades 2 (AL7)
B4;G471;HOSPITAL STREET;Couloir principal                        ;??? ?????;LIFT-07;.;Monte malades 3 (AL8)
B8;G472;MINOR OP LOBBY;Centre d'Endoscopie;   ???;LIFT-08;2,89;Ascenseur visiteurs 3 (AL14)
B6;G473;CIRCULATION;Circulation;.;LIFT-09;.;Monte charge sale 2 (AL9)
B6;G474;CIRCULATION;Circulation;.;LIFT-10;.;Monte charge propre 2 (AL10)
B6;G475;CIRCULATION;Circulation;.;LIFT-11;.;Monte malade 3 (AL11)
B6;G476;CIRCULATION;Circulation;.;LIFT-12;.;Monte malade 4 (AL12)
B3;G477;IMAGING;Radiologie                           ; ????? ?? ??????;LIFT-13;9,32;Monte malades urgences (AL13)
B2;G480;MEP;.;.;IDF ROOM;9,30;Local distribution courants faibles CfL001
B2;G481;MEP;.;.;ELECT.;9,00;Tableau électrique TEL002
B6;G482;MEP;.;.;IDF ROOM;7,91;Local distribution courants faibles CfL002
B6;G483;MEP;.;.;ELECT.;9,00;Tableau électrique TEL003
B2;G484;RECEPTION INFORMATION;Acceuil                                            ; ???????;STAIR-04;26,05;Escalier Visiteurs 4
B4;G485;MEP;.;.;IDF ;7,05;Local distribution courants faibles CfL003
B1;G486;MEP;.;.;ELEC.;.;Tableau électrique TEL004
B3;G487;MEP;.;.;ELEC.;.;Tableau électrique TEL005
B5;G488;MEP;.;.;ELEC.;.;Tableau électrique TEL006
B5;G489;MEP;.;.;ELEC.;.;Tableau électrique TEL007
B5;G490;MEP;.;.;ELEC.;.;Tableau électrique TEL008
B4;G491;MEP;.;.;ELEC.;.;Tableau électrique TEL009
B6;G492;MEP;.;.;ELEC.;.;Tableau électrique TEL010
B7;G493;MEP;.;.;ELEC.;.;Tableau électrique TEL011
B8;G495;MEP;.;.;ELEC.;.;Tableau électrique TEL012
B12;G496;MEP;.;.;ELEC.;.;Tableau électrique TEL013
B9;G497;MEP;.;.;ELEC.;.;Tableau électrique TEL014
B1;G498;EMERGENCY;Urgences                               ;    ???????? ;TERRACE;32,31;.
B8;G499;OPD;Consultations externes;  ?????? ?? ???????;OUT DOOR PLAY AREA;49,01;Zone de jeux
B3;G500;IMAGING;Radiologie                           ; ????? ?? ??????;CORRIDOR;16,47;.
B3;G501;IMAGING;Radiologie                           ; ????? ?? ??????;MAMMOGRAPHY;13,61;Mammographie
B3;G502;IMAGING;Radiologie                           ; ????? ?? ??????;W.C;1,87;Toilelttes
B3;G503;IMAGING;Radiologie                           ; ????? ?? ??????;F.CHANGE;2,15;Vestiaire
B3;G504;IMAGING;Radiologie                           ; ????? ?? ??????;M.CHANGE;2,15;Vestiaire
B4;G505;OPD;Centre de Neurologie;?????? ?????? ;WAITING/CORRIDOR;24,00;Attente Neurologie
B1;G507;EMERGENCY;Urgences                               ;    ???????? ;W.C;4,94;Toilettes
B5;G508;DIAGNOSTIC LABORATORY;Laboratoires                                         ;.????? ;PRE-PCR;17,35;Pré PCR
B5;G509;DIAGNOSTIC LABORATORY;Laboratoires                                         ;.????? ;DARK STORE;4,23;Chambre noire
B5;G510;DIAGNOSTIC LABORATORY;Laboratoires                                         ;.????? ;D/U;4,23;Local Sale
B5;G511;DIAGNOSTIC LABORATORY;Laboratoires                         ;.????? ;UNNAMED;1,58;.
B5;G512;DIAGNOSTIC LABORATORY;Laboratoires                          ;.????? ;UNNAMED;1,58;.
B8;G513;CIRCULATION;Couloir                         ;??? ;WASH;3,94;Toilettes femmes
B8;G514;MEP;.;.;ELEC.;.;Tableau électrique TEL015
B9;G515;OUTPATIENT & VISITOR CIRCULATION;Circulations patient et visiteurs                           ;???? ?????? ? ??????;WAITING;55,82;Attente"""









		locloc="""B1;S001;CIRCULATION;Couloir                         ;??? ;STAIR-01;29,09;Escalier 1
B1;S002;VIP PREMIUM & SENIOR SUITE  ;Suites VIP                   ;???? ???? ????????;PREMIUM SUITE-BED;9,73;.
B1;S003;VIP PREMIUM & SENIOR SUITE  ;Suites VIP                   ;???? ???? ????????;PANTRY / KITCHENETTE;4,46;Kitchenette
B1;S004;VIP PREMIUM & SENIOR SUITE  ;Suites VIP                   ;???? ???? ????????;PREMIUM SUITE-LIVING;19,83;Suite 2004
B1;S005;VIP PREMIUM & SENIOR SUITE  ;Suites VIP                   ;???? ???? ????????;TOILET;8,69;.
B1;S006;VIP PREMIUM & SENIOR SUITE  ;Suites VIP                   ;???? ???? ????????;PREMIUM SUITE-BED;20,31;.
B1;S007;VIP PREMIUM & SENIOR SUITE  ;Suites VIP                   ;???? ???? ????????;PREMIUM SUITE-BED;20,56;.
B1;S008;VIP PREMIUM & SENIOR SUITE  ;Suites VIP                   ;???? ???? ????????;TOILET;10,53;.
B1;S009;VIP PREMIUM & SENIOR SUITE  ;Suites VIP                   ;???? ???? ????????;PREMIUM SUITE-LIVING;26,48;Suite 2003
B1;S010;VIP PREMIUM & SENIOR SUITE  ;Suites VIP                   ;???? ???? ????????;PANTRY / KITCHENETTE;4,51;Kitchenette
B1;S011;VIP PREMIUM & SENIOR SUITE  ;Suites VIP                   ;???? ???? ????????;PREMIUM SUITE-BED;9,84;.
B1;S012;VIP PREMIUM & SENIOR SUITE  ;Suites VIP                   ;???? ???? ????????;PANTRY / KITCHENETTE;4,93;Kitchenette
B1;S013;VIP PREMIUM & SENIOR SUITE  ;Suites VIP                   ;???? ???? ????????;PREMIUM SUITE-BED;10,75;.
B1;S014;VIP PREMIUM & SENIOR SUITE  ;Suites VIP                   ;???? ???? ????????;PREMIUM SUITE-LIVING;26,48;Suite 2002
B1;S015;VIP PREMIUM & SENIOR SUITE  ;Suites VIP                   ;???? ???? ????????;TOILET;10,66;.
B1;S016;VIP PREMIUM & SENIOR SUITE  ;Suites VIP                   ;???? ???? ????????;PREMIUM SUITE-BED;20,55;.
B1;S017;VIP PREMIUM & SENIOR SUITE  ;Suites VIP                   ;???? ???? ????????;PREMIUM SUITE-BED;20,31;.
B1;S018;VIP PREMIUM & SENIOR SUITE  ;Suites VIP                   ;???? ???? ????????;TOILET;7,78;.
B1;S019;VIP PREMIUM & SENIOR SUITE  ;Suites VIP                   ;???? ???? ????????;PANTRY / KITCHENETTE;3,24;Kitchenette
B1;S020;VIP PREMIUM & SENIOR SUITE  ;Suites VIP                   ;???? ???? ????????;PREMIUM SUITE-LIVING;23,86;Suite 2001
B1;S021;VIP PREMIUM & SENIOR SUITE  ;Suites VIP                   ;???? ???? ????????;PREMIUM SUITE-BED;12,04;.
B1;S022;VIP PREMIUM & SENIOR SUITE  ;Suites VIP                   ;???? ???? ????????;NURSES OFFICE;12,54;Infirmière Chef
B1;S023;VIP PREMIUM & SENIOR SUITE  ;Suites VIP                   ;???? ???? ????????;SECRETARY;11,16;Butler
B1;S024;VIP PREMIUM & SENIOR SUITE  ;Suites VIP                   ;???? ???? ????????;MED CARE;14,76;Salle de soins
B1;S025;VIP PREMIUM & SENIOR SUITE  ;Suites VIP                   ;???? ???? ????????;WC;3,74;Toilettes
B1;S026;VIP PREMIUM & SENIOR SUITE  ;Suites VIP                   ;???? ???? ????????;NURSES REST;27,18;Salle de consultation et de soins
B1;S027;VIP PREMIUM & SENIOR SUITE  ;Suites VIP                   ;???? ???? ????????;RECEPTION ROOM;10,94;Salle d'entretiens
B1;S028;VIP PREMIUM & SENIOR SUITE  ;Suites VIP                   ;???? ???? ????????;PHARMACY;10,25;Pharmacie
B1;S029;VIP PREMIUM & SENIOR SUITE  ;Suites VIP                   ;???? ???? ????????;DU;4,79;Local Sale
B1;S030;VIP PREMIUM & SENIOR SUITE  ;Suites VIP                   ;???? ???? ????????;PANTRY;14,03;Office
B1;S031;VIP PREMIUM & SENIOR SUITE  ;Suites VIP                   ;???? ???? ????????;EXAM ROOM;14,85;Salle de consultation
B1;S032;VIP PREMIUM & SENIOR SUITE  ;Suites VIP                   ;???? ???? ????????;NURSE STATION;16,67;.
B1;S033;VIP PREMIUM & SENIOR SUITE  ;Suites VIP                   ;???? ???? ????????;VIP LOBBY;58,04;Attente visiteurs
B1;S034;VIP PREMIUM & SENIOR SUITE  ;Suites VIP                   ;???? ???? ????????;CORRIDOR;69,54;SUITES PREMIUM
B1;S035;VIP PREMIUM & SENIOR SUITE ;Suites VIP                   ;???? ???? ????????;SENIOR SUITE ROOM;18,14;Suite 2005
B1;S036;VIP PREMIUM & SENIOR SUITE ;Suites VIP                   ;???? ???? ????????;PANTRY / KITCHENETTE;6,09;Kitchenette
B1;S037;VIP PREMIUM & SENIOR SUITE ;Suites VIP                   ;???? ???? ????????;SENIOR SUITE ROOM;16,18;.
B1;S038;VIP PREMIUM & SENIOR SUITE ;Suites VIP                   ;???? ???? ????????;WC;5,64;.
B1;S039;VIP PREMIUM & SENIOR SUITE ;Suites VIP                   ;???? ???? ????????;SENIOR SUITE ROOM;13,31;.
B1;S040;VIP PREMIUM & SENIOR SUITE ;Suites VIP                   ;???? ???? ????????;WC;5,12;.
B1;S041;VIP PREMIUM & SENIOR SUITE ;Suites VIP                   ;???? ???? ????????;PANTRY / KITCHENETTE;4,39;Kitchenette
B1;S042;VIP PREMIUM & SENIOR SUITE ;Suites VIP                   ;???? ???? ????????;SENIOR SUITE ROOM;19,35;Suite 2006
B1;S043;VIP PREMIUM & SENIOR SUITE ;Suites VIP                   ;???? ???? ????????;SENIOR SUITE ROOM;19,35;Suite 2007
B1;S044;VIP PREMIUM & SENIOR SUITE ;Suites VIP                   ;???? ???? ????????;PANTRY / KITCHENETTE;5,63;Kitchenette
B1;S045;VIP PREMIUM & SENIOR SUITE ;Suites VIP                   ;???? ???? ????????;SENIOR SUITE;13,31;.
B1;S046;VIP PREMIUM & SENIOR SUITE ;Suites VIP                   ;???? ???? ????????;WC;5,64;.
B1;S047;VIP PREMIUM & SENIOR SUITE ;Suites VIP                   ;???? ???? ????????;SENIOR SUITE ROOM;12,58;Suite 2008
B1;S048;VIP PREMIUM & SENIOR SUITE ;Suites VIP                   ;???? ???? ????????;WC;5,25;.
B1;S049;VIP PREMIUM & SENIOR SUITE ;Suites VIP                   ;???? ???? ????????;SENIOR SUITE LIVING;19,75;.
B1;S050;VIP PREMIUM & SENIOR SUITE ;Suites VIP                   ;???? ???? ????????;PANTRY / KITCHENETTE;5,82;Kitchenette
B1;S051;VIP PREMIUM & SENIOR SUITE ;Suites VIP                   ;???? ???? ????????;CORRIDOR;64,29;SUITES
B1;S052;VIP PREMIUM & SENIOR SUITE ;Suites VIP                   ;???? ???? ????????;HK;10,01;Local ménage
B1;S053;VIP PREMIUM & SENIOR SUITE ;Suites VIP                   ;???? ???? ????????;LINEN;10,04;Lingerie
B1;S054;VIP PREMIUM & SENIOR SUITE ;Suites VIP                   ;???? ???? ????????;D/U;10,62;Local Sale
B1;S055;VIP PREMIUM & SENIOR SUITE ;Suites VIP                   ;???? ???? ????????;C/U;10,47;Local Propre
B1;S056;VIP PREMIUM & SENIOR SUITE ;Suites VIP                   ;???? ???? ????????;PANTRY;14,61;Office
B1;S057;VIP PREMIUM & SENIOR SUITE ;Suites VIP                   ;???? ???? ????????;OFFICE;24,52;Bureau médecins
B1;S058;VIP PREMIUM & SENIOR SUITE ;Suites VIP                   ;???? ???? ????????;SECURITY;8,56;Poste sécurité
B3;S059;Administration;Administration                                  ;  ???????  ;SECRETARY;14,78;Unité Qualité
B3;S060;Administration;Administration                                  ;  ???????  ;MEDICAL DIRECTOR;16,05;Responsable Qualité
B3;S061;Administration;Administration                                  ;  ???????  ;WC& WASH;3,75;.
B3;S062;Administration;Administration                                  ;  ???????  ;SECRETARY;16,95;Unite supervison des soins
B3;S063;Administration;Administration                                  ;  ???????  ;OFFICE  EN SUITE;16,45;Directeur des soins
B3;S064;Administration;Administration                                  ;  ???????  ;WC & WASH;3,75;.
B3;S065;Administration;Administration                                  ;  ???????  ;OFFICE;15,46;Directeur Hébergement
B3;S066;Administration;Administration                                  ;  ???????  ;OFFICE;18,72;Officier de sécurité
B3;S067;Administration;Administration                                  ;  ???????  ;OFFICE;19,26;Bureau
B3;S068;Administration;Administration                                  ;  ???????  ;BOARD ROOM;51,71;Salle de reunion du Conseil
B3;S069;Administration;Administration                                  ;  ???????  ;WC& WASH;3,75;.
B3;S070;Administration;Administration                                  ;  ???????  ;OFFICE  EN SUITE;33,27;Directeur géneral
B5;S071;Administration;Administration                                  ;  ???????  ;SECRETARY;16,41;Secrétriat Directeur Général
B5;S072;Administration;Administration                                  ;  ???????  ;DIR. OFFICE;20,48;Service Audit
B5;S073;Administration;Administration                                  ;  ???????  ;COMMUNICATION OFFICE;19,39;Service communication
B5;S074;Administration;Administration                                  ;  ???????  ;INTERNATIONAL OFFICE;18,58;Service patients internationaux
B5;S075;Administration;Administration                                  ;  ???????  ;AUDIT OFFICE;22,11;Directeur systèmes d'information
B5;S076;Administration;Administration                                  ;  ???????  ;SECRETARY;15,87;Service contrôle de gestion
B5;S077;Administration;Administration                                  ;  ???????  ;FINANCE OFFICE WITH ENSUITE;16,45;Directeur Financier
B5;S078;Administration;Administration                                  ;  ???????  ;WC & WASH;3,75;.
B5;S079;Administration;Administration                                  ;  ???????  ;FINANCE OFFICE;18,31;Service comptabiilté
B5;S080;Administration;Administration                                  ;  ???????  ;FINANCE OFFICE;18,85;Service Paie
B5;S081;Administration;Administration                                  ;  ???????  ;SECRETARY;15,73;Service Juridique et moyens generaux
B5;S082;Administration;Administration                                  ;  ???????  ;ADMIN. OFFICE WITH ENSUITE;23,18;Directeur Administratif
B5;S083;Administration;Administration                                  ;  ???????  ;WC & WASH;3,76;.
B5;S084;Administration;Administration                                  ;  ???????  ;DIR. OFFICE;19,84;Service Approvisionnement
B5;S085;Administration;Administration                                  ;  ???????  ;ADMIN. OFFICE ;29,56;Service du personnel
B5;S086;Administration;Administration                                  ;  ???????  ;PRINT ROOM;10,54;Reprographie
B5;S087;Administration;Administration                                  ;  ???????  ;FEMALE WC;9,74;Sanitaires femmes
B5;S088;Administration;Administration                                  ;  ???????  ;WAITING AREA / CORRIDOR;74,13;Attente visiteurs
B5;S089;Administration;Administration                                  ;  ???????  ;WASH;4,67;Laverie
B5;S090;Administration;Administration                                  ;  ???????  ;ARCHIVES;33,91;Archives 1
B5;S091;Administration;Administration                                  ;  ???????  ;ARCHIVES;14,49;Archives 2
B5;S092;Administration;Administration                                  ;  ???????  ;PRINT ROOM;20,46;Réserve bureautique
B5;S093;Administration;Administration                                  ;  ???????  ;MALE WC;13,93;Sanitaires Hommes
B5;S094;Administration;Administration                                  ;  ???????  ;WAITING /CORRIDOR;58,18;Attente
B3;S095;Administration;Administration                                  ;  ???????  ;MEETING ROOM;18,58;Salle de réunion 1
B3;S096;Administration;Administration                                  ;  ???????  ;WAITING ROOM ;62,23;Salle d'attente
B3;S097;Administration;Administration                                  ;  ???????  ;MEETING ROOM;18,66;Salle de réunion 2
B3;S098;Administration;Administration                                  ;  ???????  ;OFFICE;12,40;Bureau
B3;S099;Administration;Administration                                  ;  ???????  ;OFFICE;11,65;Bureau
B3;S100;Administration;Administration                                  ;  ???????  ;WAITING AREA / CORRIDOR;69,26;Attente
B3;S101;Administration;Administration                                  ;  ???????  ;FEMALE WC;6,93;Sanitaire handicapés
B3;S102;Administration;Administration                                  ;  ???????  ;MALE WC;6,93;Sanitaires handicapés
B3;S103;Administration;Administration                                  ;  ???????  ;CORRIDOR;26,36;.
B6;S104;CIRCULATION;Couloir                         ;??? ;LIFT LOBBY;48,27;Hall ascenseurs
B6;S105;CIRCULATION;Couloir                         ;??? ;STAIR-06;25,19;Escalier 6
B6;S106;MEP;.;.;ELEC. ROOM;8,99;Tableau électrique TEL201
B6;S107;MEP;.;.;IDF ROOM;7,91;Local distribution courants faibles CfL201
B6;S108;Administration;Administration                                  ;  ???????  ;WC;2,18;Toilettes femmes
B6;S109;Administration;Administration                                  ;  ???????  ;PANTRY;19,80;.
B6;S110;Administration;Administration                                  ;  ???????  ;WC;2,18;Toilettes hommes
B6;S111;Administration;Administration                                  ;  ???????  ;MEDICAL CLUB (DINING);114,91;Club des médecins Accès Règlementé
B6;S112;CIRCULATION;Couloir                         ;??? ;CORRIDOR;15,88;Club Medical - Administration
B6;S113;CIRCULATION;Couloir                         ;??? ;CORRIDOR;63,21;Couloir médical
B4;S114;CIRCULATION;Couloir                         ;??? ;LIFT LOBBY;55,64;Hall Ascenseurs
B4;S115;CIRCULATION;Couloir                         ;??? ;STAIR-05;25,88;Escalier 5
B4;S116;HOSPITAL STREET ;Couloir médical                        ;??? ?????;WC;3,96;Toilettes
B4;S117;HOSPITAL STREET ;Couloir médical                        ;??? ?????;WC;3,96;Toilettes
B4;S118;Administration;Administration                                  ;  ???????  ;VISITOR'S & PATIENTS WAITING LOUNGE (MAJLIS);69,82;Salon Visiteurs
B6;S119;Administration;Administration                                  ;  ???????  ;GENERAL OFFICE;32,87;Secrétaire général
B6;S120;Administration;Administration                                  ;  ???????  ;WC;2,81;Toilettes
B4;S121;Administration;Administration                                  ;  ???????  ;STORE;3,18;Reserve
B6;S122;Administration;Administration                                  ;  ???????  ;WC;2,81;Toilettes
B6;S123;Administration;Administration                                  ;  ???????  ;WAITING;38,92;Attente 
B6;S124;Administration;Administration                                  ;  ???????  ;DRESSING;5,56;.
B6;S125;Administration;Administration                                  ;  ???????  ;WC;4,31;.
B6;S126;Administration;Administration                                  ;  ???????  ;RECEPTION & SECRETARY;24,76;Accuil Secrétariat
B6;S127;Administration;Administration                                  ;  ???????  ;PRESIDENT OFFICE;43,01;Président Directeur
B4;S128;CIRCULATION;Couloir                         ;??? ;CORRIDOR;93,43;Couloir médical
B4;S129;Administration;Administration                                  ;  ???????  ;STAFF WC;3,85;Toilettes 
B4;S130;Administration;Administration                                  ;  ???????  ;STAFF WC;3,67;Toilettes
B4;S131;Administration;Administration                                  ;  ???????  ;OFFICE;13,30;Chef de Pôle Médecine
B4;S132;Administration;Administration                                  ;  ???????  ;OFFICE;16,99;Chef de Pôle Chirurgie
B4;S133;Administration;Administration                                  ;  ???????  ;OFFICE;16,96;Bureau
B4;S134;Administration;Administration                                  ;  ???????  ;CORRIDOR;22,93;Bureaux médecins
B4;S135;Administration;Administration                                  ;  ???????  ;STAFF REST & MEETING ROOM;43,15;Salle de réunion médecins
B4;S136;Administration;Administration                                  ;  ???????  ;WC 1 & 2;9,08;Toilettes
B4;S137;Administration;Administration                                  ;  ???????  ;LOCKERS 1 & 2;7,89;Vestiaire
B4;S138;Administration;Administration                                  ;  ???????  ;MEDICAL CLUB LOUNGE;42,88;Foyer internes et résidents
B4;S139;CIRCULATION;Couloir                         ;??? ;CORRIDOR;22,47;?
B2;S140;CIRCULATION;Couloir                         ;??? ;EMERGENCY ESCAPE;135,77;Issue de secours
B2;S141;CIRCULATION;Couloir                         ;??? ;DIS. WC;4,72;Toilettes
B2;S142;CIRCULATION;Couloir                         ;??? ;DIS. WC;4,60;Toilettes
B2;S143;MEP;.;.;ELEC. ROOM;8,99;Tableau électrique TEL202
B2;S144;MEP;.;.;IDF ROOM;9,30;Local distribution courants faibles CfL202
B2;S145;CIRCULATION;Couloir                         ;??? ;VISITOR'S WAITING LOUNGE;153,53;Salon visiteurs
B2;S146;CIRCULATION;Couloir                         ;??? ;BABY CHANGE;3,46;Change couches
B2;S147;CIRCULATION;Couloir                         ;??? ;DIS. WC;3,76;Toilettes
B2;S148;CIRCULATION;Couloir                         ;??? ;PRAYER ROOM;62,21;Salle de prière femmes
B2;S149;CIRCULATION;Couloir                         ;??? ;WASH + BOOT;11,25;Salle de prière femmes
B2;S150;CIRCULATION;Couloir                         ;??? ;WASH;4,71;Sale d'ablutions et toilettes
B2;S151;CIRCULATION;Couloir                         ;??? ;WC;1,75;Toilettes
B2;S152;CIRCULATION;Couloir                         ;??? ;WC;2,10;Toilettes
B2;S153;CIRCULATION;Couloir                         ;??? ;STAIR-04;26,05;Escalier 4
B2;S154;CIRCULATION;Couloir                         ;??? ;CORRIDOR;89,82;Couloir visiteurs
B7;S155;GENERAL WARD 4 ;Aile d'hospitalisation 4           ;???? ????????? 4;STORE;3,06;Réserve
B7;S156;GENERAL WARD 4 ;Aile d'hospitalisation 4            ;???? ????????? 4;E/S;2,88;.
B7;S157;GENERAL WARD 4 ;Aile d'hospitalisation 4            ;???? ????????? 4;SINGLE BED ROOM;18,82;Lit 2101
B7;S158;GENERAL WARD 4 ;Aile d'hospitalisation 4            ;???? ????????? 4;SINGLE BED ROOM;18,82;Lit 2103
B7;S159;GENERAL WARD 4 ;Aile d'hospitalisation 4            ;???? ????????? 4;E/S;2,88;.
B7;S160;GENERAL WARD 4 ;Aile d'hospitalisation 4            ;???? ????????? 4;E/S;2,88;.
B7;S161;GENERAL WARD 4 ;Aile d'hospitalisation 4            ;???? ????????? 4;SINGLE BED ROOM;18,82;Lit 2105
B7;S162;GENERAL WARD 4 ;Aile d'hospitalisation 4            ;???? ????????? 4;SINGLE BED ROOM;18,82;Lit 2107
B7;S163;GENERAL WARD 4 ;Aile d'hospitalisation 4            ;???? ????????? 4;E/S;2,88;.
B7;S164;GENERAL WARD 4 ;Aile d'hospitalisation 4            ;???? ????????? 4;E/S;2,88;.
B7;S165;GENERAL WARD 4 ;Aile d'hospitalisation 4            ;???? ????????? 4;SINGLE BED ROOM;18,82;Lit 2109
B7;S166;GENERAL WARD 4 ;Aile d'hospitalisation 4            ;???? ????????? 4;SINGLE BED ROOM;18,82;Lit 2111
B7;S167;GENERAL WARD 4 ;Aile d'hospitalisation 4            ;???? ????????? 4;E/S;2,88;.
B7;S168;GENERAL WARD 4 ;Aile d'hospitalisation 4            ;???? ????????? 4;E/S;2,88;.
B7;S169;GENERAL WARD 4 ;Aile d'hospitalisation 4            ;???? ????????? 4;SINGLE BED ROOM;18,82;Lit 2113
B7;S170;GENERAL WARD 4 ;Aile d'hospitalisation 4            ;???? ????????? 4;STORE;4,58;Réserve
B7;S171;GENERAL WARD 4 ;Aile d'hospitalisation 4            ;???? ????????? 4;TREATMENT PREP.;15,66;Salle de soins
B7;S172;GENERAL WARD 4 ;Aile d'hospitalisation 4            ;???? ????????? 4;CLEAN UTILITY;8,43;Local propre
B7;S173;GENERAL WARD 4 ;Aile d'hospitalisation 4            ;???? ????????? 4;LINEN;1,82;Lingerie
B7;S174;GENERAL WARD 4 ;Aile d'hospitalisation 4            ;???? ????????? 4;NURSE OFFICE;9,74;Cadre infirmier
B7;S175;GENERAL WARD 4 ;Aile d'hospitalisation 4            ;???? ????????? 4;SINGLE BED ROOM;23,93;Lit 2114
B7;S176;GENERAL WARD 4 ;Aile d'hospitalisation 4            ;???? ????????? 4;E/S;23,72;.
B7;S177;GENERAL WARD 4 ;Aile d'hospitalisation 4            ;???? ????????? 4;E/S;2,60;.
B7;S178;GENERAL WARD 4 ;Aile d'hospitalisation 4            ;???? ????????? 4;SINGLE BED ROOM;23,81;Lit 2115
B7;S179;GENERAL WARD 4 ;Aile d'hospitalisation 4            ;???? ????????? 4;DR OFFICE;14,84;Bureau médecins
B7;S180;GENERAL WARD 4 ;Aile d'hospitalisation 4            ;???? ????????? 4;E/S;3,02;.
B8;S181;GENERAL WARD 4 ;Aile d'hospitalisation 4            ;???? ????????? 4;SINGLE BED ROOM;16,77;Lit 2116
B8;S182;GENERAL WARD 4 ;Aile d'hospitalisation 4            ;???? ????????? 4;SINGLE BED ROOM;18,82;Lit 2118
B8;S183;GENERAL WARD 4 ;Aile d'hospitalisation 4            ;???? ????????? 4;E/S;2,88;.
B8;S184;GENERAL WARD 4 ;Aile d'hospitalisation 4            ;???? ????????? 4;E/S;2,88;.
B8;S185;GENERAL WARD 4 ;Aile d'hospitalisation 4            ;???? ????????? 4;SINGLE BED ROOM;18,82;Lit 2120
B8;S186;GENERAL WARD 4 ;Aile d'hospitalisation 4            ;???? ????????? 4;SINGLE BED ROOM;18,44;Lit 2112
B8;S187;GENERAL WARD 4 ;Aile d'hospitalisation 4            ;???? ????????? 4;E/S;2,74;.
B8;S188;GENERAL WARD 4 ;Aile d'hospitalisation 4            ;???? ????????? 4;E/S;2,74;.
B8;S188;GENERAL WARD 4 ;Aile d'hospitalisation 4            ;???? ????????? 4;SINGLE BED ROOM;18,53;Lit 2123
B8;S190;GENERAL WARD 4 ;Aile d'hospitalisation 4            ;???? ????????? 4;SINGLE BED ROOM;18,92;.
B8;S191;GENERAL WARD 4 ;Aile d'hospitalisation 4            ;???? ????????? 4;E/S;2,88;.
B8;S192;GENERAL WARD 4 ;Aile d'hospitalisation 4            ;???? ????????? 4;E/S;2,88;.
B8;S193;GENERAL WARD 4 ;Aile d'hospitalisation 4            ;???? ????????? 4;SINGLE BED ROOM;18,92;Lit 2119
B8;S194;GENERAL WARD 4 ;Aile d'hospitalisation 4            ;???? ????????? 4;SINGLE BED ROOM;19,30;Lit 2117
B7;S195;GENERAL WARD 4 ;Aile d'hospitalisation 4            ;???? ????????? 4;E/S;3,02;.
B7;S196;CIRCULATION;Couloir                         ;??? ;STAIR-07;24,50;Escalier 7
B7;S197;GENERAL WARD 4 ;Aile d'hospitalisation 4            ;???? ????????? 4;D/U;7,94;Local Sale
B7;S198;GENERAL WARD 4 ;Aile d'hospitalisation 4            ;???? ????????? 4;PANTRY;6,38;Office
B7;S199;GENERAL WARD 4 ;Aile d'hospitalisation 4            ;???? ????????? 4;E/S;3,02;.
B7;S200;GENERAL WARD 4 ;Aile d'hospitalisation 4            ;???? ????????? 4;SINGLE BED ROOM;19,30;Lit 2112
B7;S201;GENERAL WARD 4 ;Aile d'hospitalisation 4            ;???? ????????? 4;SINGLE BED ROOM;18,92;Lit 2110
B7;S202;GENERAL WARD 4 ;Aile d'hospitalisation 4            ;???? ????????? 4;E/S;2,88;.
B7;S203;GENERAL WARD 4 ;Aile d'hospitalisation 4            ;???? ????????? 4;E/S;2,88;.
B7;S204;GENERAL WARD 4 ;Aile d'hospitalisation 4            ;???? ????????? 4;SINGLE BED ROOM;18,92;Lit 2108
B7;S205;GENERAL WARD 4 ;Aile d'hospitalisation 4            ;???? ????????? 4;SINGLE BED ROOM;18,92;Lit 2106
B7;S206;GENERAL WARD 4 ;Aile d'hospitalisation 4            ;???? ????????? 4;E/S;2,88;.
B7;S207;GENERAL WARD 4 ;Aile d'hospitalisation 4            ;???? ????????? 4;E/S;2,88;.
B7;S208;GENERAL WARD 4 ;Aile d'hospitalisation 4            ;???? ????????? 4;SINGLE BED ROOM;18,92;Lit 2104
B7;S209;GENERAL WARD 4 ;Aile d'hospitalisation 4            ;???? ????????? 4;SINGLE BED ROOM;18,92;Lit 2102
B7;S210;GENERAL WARD 4 ;Aile d'hospitalisation 4            ;???? ????????? 4;E/S;2,88;.
B7;S211;GENERAL WARD 4 ;Aile d'hospitalisation 4            ;???? ????????? 4;STORE;3,39;Réserve
B7;S212;GENERAL WARD 4 ;Aile d'hospitalisation 4            ;???? ????????? 4;CORRIDOR;176,01;.
B8;S213;GENERAL WARD 6 ;Aile d'hospitalisation 6            ;???? ????????? 6;STORE;2,43;Réserve
B8;S214;GENERAL WARD 6 ;Aile d'hospitalisation 6            ;???? ????????? 6;E/S;5,75;.
B8;S215;GENERAL WARD 6 ;Aile d'hospitalisation 5;???? ????????? 6;SINGLE BED ROOM;18,88;Lit 2201
B8;S216;GENERAL WARD 6 ;Aile d'hospitalisation 5;???? ????????? 6;SINGLE BED ROOM;20,72;Lit 2203
B8;S217;GENERAL WARD 6 ;Aile d'hospitalisation 6            ;???? ????????? 6;E/S;2,88;.
B8;S218;GENERAL WARD 6 ;Aile d'hospitalisation 6            ;???? ????????? 6;E/S;2,88;.
B8;S219;GENERAL WARD 6 ;Aile d'hospitalisation 5;???? ????????? 6;SINGLE BED ROOM;18,92;Lit 2205
B8;S220;MICU ;Soins Intensifs cardiovasculaires                      ; ??????? ??????? ???????;MICU SINGLE BED ROOM;18,92;Lit 2207
B8;S221;MICU ;Soins Intensifs cardiovasculaires                      ; ??????? ??????? ???????;E/S;2,88;.
B8;S222;MICU ;Soins Intensifs cardiovasculaires                      ; ??????? ??????? ???????;E/S;2,88;.
B8;S223;MICU ;Soins Intensifs cardiovasculaires                      ; ??????? ??????? ???????;MICU SINGLE BED ROOM;18,92;Lit 2209
B8;S224;MICU ;Soins Intensifs cardiovasculaires                      ; ??????? ??????? ???????;MICU SINGLE BED ROOM;19,30;Lit 2211
B8;S225;MICU ;Soins Intensifs cardiovasculaires                      ; ??????? ??????? ???????;E/S;3,02;.
B8;S226;GENERAL WARD 6 ;Aile d'hospitalisation 6            ;???? ????????? 6;LINEN;2,58;Lingerie
B8;S227;GENERAL WARD 6 ;Aile d'hospitalisation 6           ;???? ????????? 6;TREATMENT;13,47;Salle de soins
B8;S228;GENERAL WARD 6 ;Aile d'hospitalisation 6           ;???? ????????? 6;STORE;8,20;Réserve
B8;S229;GENERAL WARD 6 ;Aile d'hospitalisation 6            ;???? ????????? 6;D/U;6,45;Local Sale
B8;S230;GENERAL WARD 6 ;Aile d'hospitalisation 6            ;???? ????????? 6;C/U;8,33;Local propre
B8;S231;GENERAL WARD 6 ;Aile d'hospitalisation 6            ;???? ????????? 6;E/S;2,95;.
B8;S232;GENERAL WARD 6 ;Aile d'hospitalisation 5;???? ????????? 6;SINGLE BED ROOM;19,11;Lit 2216
B8;S233;GENERAL WARD 6 ;Aile d'hospitalisation 5;???? ????????? 6;SINGLE BED ROOM;19,11;Lit 2218
B8;S234;GENERAL WARD 6 ;Aile d'hospitalisation 6            ;???? ????????? 6;E/S;2,95;.
B8;S235;GENERAL WARD 6 ;Aile d'hospitalisation 6            ;???? ????????? 6;E/S;2,95;.
B8;S236;GENERAL WARD 6 ;Aile d'hospitalisation 5;???? ????????? 6;SINGLE BED ROOM;19,01;Lit 2217
B8;S237;GENERAL WARD 6 ;Aile d'hospitalisation 5;???? ????????? 6;SINGLE BED ROOM;18,23;Lit 2215
B8;S238;GENERAL WARD 6 ;Aile d'hospitalisation 6            ;???? ????????? 6;E/S;2,72;.
B8;S239;GENERAL WARD 6 ;Aile d'hospitalisation 6            ;???? ????????? 6;NURSE OFFICE;13,59;Cadre infirmier
B8;S240;GENERAL WARD 6 ;Aile d'hospitalisation 5;???? ????????? 6;SINGLE BED ROOM;21,03;Lit 2213
B8;S241;GENERAL WARD 6 ;Aile d'hospitalisation 6            ;???? ????????? 6;E/S;4,39;.
B8;S242;GENERAL WARD 6 ;Aile d'hospitalisation 6           ;???? ????????? 6;E/S;2,97;.
B8;S243;GENERAL WARD 6 ;Aile d'hospitalisation 5;???? ????????? 6;SINGLE BED ROOM;22,56;Lit 2214
B8;S244;GENERAL WARD 6 ;Aile d'hospitalisation 6            ;???? ????????? 6;DR OFFICE;13,59;Bureau Médecins
B8;S245;GENERAL WARD 6 ;Aile d'hospitalisation 6            ;???? ????????? 6;E/S;3,02;.
B12;S246;GENERAL WARD 6 ;Aile d'hospitalisation 5;???? ????????? 6;SINGLE BED ROOM;16,77;Lit 2219
B12;S247;GENERAL WARD 6 ;Aile d'hospitalisation 5;???? ????????? 6;SINGLE BED ROOM;18,82;Lit 2221
B12;S248;GENERAL WARD 6 ;Aile d'hospitalisation 6            ;???? ????????? 6;E/S;2,88;.
B12;S249;GENERAL WARD 6 ;Aile d'hospitalisation 6            ;???? ????????? 6;E/S;2,88;.
B12;S250;GENERAL WARD 6 ;Aile d'hospitalisation 5;???? ????????? 6;SINGLE BED ROOM;18,92;Lit 2213
B12;S251;GENERAL WARD 6 ;Aile d'hospitalisation 5;???? ????????? 6;SINGLE BED ROOM;19,30;Lit 2220
B8;S252;GENERAL WARD 6 ;Aile d'hospitalisation 6            ;???? ????????? 6;E/S;3,02;.
B8;S253;CIRCULATION;Couloir                         ;??? ;STAIR-08;24,50;Escalier 8
B8;S255;MICU ;Soins Intensifs cardiovasculaires                      ; ??????? ??????? ???????;E/S;3,02;.
B8;S256;MICU ;Soins Intensifs cardiovasculaires                      ; ??????? ??????? ???????;MICU SINGLE BED ROOM;19,30;Lit 2212
B8;S257;MICU ;Soins Intensifs cardiovasculaires                      ; ??????? ??????? ???????;MICU SINGLE BED ROOM;18,92;Lit 2210
B8;S258;MICU ;Soins Intensifs cardiovasculaires                      ; ??????? ??????? ???????;E/S;2,88;.
B8;S259;MICU ;Soins Intensifs cardiovasculaires                      ; ??????? ??????? ???????;E/S;2,88;.
B8;S260;MICU ;Soins Intensifs cardiovasculaires                      ; ??????? ??????? ???????;MICU SINGLE BED ROOM;18,92;Lit 2208
B8;S261;GENERAL WARD 6 ;Aile d'hospitalisation 5;???? ????????? 6;SINGLE BED ROOM;18,92;Lit 2206
B8;S262;GENERAL WARD 6 ;Aile d'hospitalisation 6            ;???? ????????? 6;E/S;2,88;.
B8;S263;GENERAL WARD 6 ;Aile d'hospitalisation 6            ;???? ????????? 6;E/S;2,88;.
B8;S264;GENERAL WARD 6 ;Aile d'hospitalisation 5;???? ????????? 6;SINGLE BED ROOM;20,72;Lit 2204
B8;S265;GENERAL WARD 6 ;Aile d'hospitalisation 5;???? ????????? 6;SINGLE BED ROOM;18,88;Lit 2202
B8;S266;GENERAL WARD 6 ;Aile d'hospitalisation 6            ;???? ????????? 6;E/S;5,83;.
B8;S267;GENERAL WARD 6 ;Aile d'hospitalisation 6            ;???? ????????? 6;HK;2,23;Local ménage
B8;S268;GENERAL WARD 6 ;Aile d'hospitalisation 6            ;???? ????????? 6;CORRIDOR;173,83;.
B12;S269;GENERAL WARD 5 ;Aile d'hospitalisation 5            ;???? ????????? 5;E/S;2,88;.
B12;S270;GENERAL WARD 5 ;Aile d'hospitalisation 6            ;???? ????????? 5;SINGLE BED ROOM;18,92;Lit 2322
B12;S271;GENERAL WARD 5 ;Aile d'hospitalisation 6            ;???? ????????? 5;SINGLE BED ROOM;18,92;Lit 2320
B12;S272;GENERAL WARD 5 ;Aile d'hospitalisation 5            ;???? ????????? 5;E/S;2,88;.
B12;S273;GENERAL WARD 5 ;Aile d'hospitalisation 5            ;???? ????????? 5;E/S;2,88;.
B12;S274;GENERAL WARD 5 ;Aile d'hospitalisation 6            ;???? ????????? 5;SINGLE BED ROOM;18,92;Lit 2318
B12;S275;GENERAL WARD 5 ;Aile d'hospitalisation 6            ;???? ????????? 5;SINGLE BED ROOM;18,79;Lit 2316
B12;S276;GENERAL WARD 5 ;Aile d'hospitalisation 5            ;???? ????????? 5;E/S;2,83;.
B12;S277;CIRCULATION;Couloir                         ;??? ;STAIR-09;24,50;Escalier 9
B12;S278;GENERAL WARD 5 ;Aile d'hospitalisation 5            ;???? ????????? 5;D/U;8,28;Local Sale
B12;S279;GENERAL WARD 5 ;Aile d'hospitalisation 5            ;???? ????????? 5;PANTRY;6,66;Office
B9;S280;GENERAL WARD 5 ;Aile d'hospitalisation 5            ;???? ????????? 5;E/S;3,02;.
B9;S281;GENERAL WARD 5 ;Aile d'hospitalisation 6            ;???? ????????? 5;SINGLE BED ROOM;19,31;Lit 2311
B9;S282;GENERAL WARD 5 ;Aile d'hospitalisation 6            ;???? ????????? 5;SINGLE BED ROOM;18,92;Lit 2309
B9;S283;GENERAL WARD 5 ;Aile d'hospitalisation 5            ;???? ????????? 5;E/S;2,88;.
B9;S284;GENERAL WARD 5 ;Aile d'hospitalisation 5            ;???? ????????? 5;E/S;2,88;.
B9;S285;GENERAL WARD 5 ;Aile d'hospitalisation 6            ;???? ????????? 5;SINGLE BED ROOM;18,92;Lit 2307
B9;S286;GENERAL WARD 5 ;Aile d'hospitalisation 6            ;???? ????????? 5;SINGLE BED ROOM;18,92;Lit 2305
B9;S287;GENERAL WARD 5 ;Aile d'hospitalisation 5            ;???? ????????? 5;E/S;2,88;.
B9;S288;GENERAL WARD 5 ;Aile d'hospitalisation 5            ;???? ????????? 5;E/S;2,88;.
B9;S289;GENERAL WARD 5 ;Aile d'hospitalisation 6            ;???? ????????? 5;SINGLE BED ROOM;20,72;Lit 2303
B9;S290;GENERAL WARD 5 ;Aile d'hospitalisation 6            ;???? ????????? 5;SINGLE BED ROOM;18,88;Lit 2301
B9;S291;GENERAL WARD 5 ;Aile d'hospitalisation 5            ;???? ????????? 5;E/S;5,75;.
B9;S292;GENERAL WARD 5 ;Aile d'hospitalisation 5            ;???? ????????? 5;STORE;2,11;Réserve
B9;S293;GENERAL WARD 5 ;Aile d'hospitalisation 5            ;???? ????????? 5;STORE;2,15;Réserve
B9;S294;GENERAL WARD 5 ;Aile d'hospitalisation 5            ;???? ????????? 5;E/S;5,75;.
B9;S295;GENERAL WARD 5 ;Aile d'hospitalisation 6            ;???? ????????? 5;SINGLE BED ROOM;18,70;Lit 2302
B9;S296;GENERAL WARD 5 ;Aile d'hospitalisation 6            ;???? ????????? 5;SINGLE BED ROOM;20,55;Lit 2304
B9;S297;GENERAL WARD 5 ;Aile d'hospitalisation 5            ;???? ????????? 5;E/S;2,88;.
B9;S298;GENERAL WARD 5 ;Aile d'hospitalisation 5            ;???? ????????? 5;E/S;2,88;.
B9;S299;GENERAL WARD 5 ;Aile d'hospitalisation 6            ;???? ????????? 5;SINGLE BED ROOM;18,82;Lit 2306
B9;S300;GENERAL WARD 5 ;Aile d'hospitalisation 6            ;???? ????????? 5;SINGLE BED ROOM;18,82;Lit 2308
B9;S301;GENERAL WARD 5 ;Aile d'hospitalisation 5            ;???? ????????? 5;E/S;2,88;.
B9;S302;GENERAL WARD 5 ;Aile d'hospitalisation 5            ;???? ????????? 5;E/S;2,88;.
B9;S303;GENERAL WARD 5 ;Aile d'hospitalisation 6            ;???? ????????? 5;SINGLE BED ROOM;18,82;Lit 2310
B9;S304;GENERAL WARD 5 ;Aile d'hospitalisation 6            ;???? ????????? 5;SINGLE BED ROOM;18,94;Lit 2312
B9;S305;GENERAL WARD 5 ;Aile d'hospitalisation 5          ;???? ????????? 5;E/S;2,91;.
B12;S306;GENERAL WARD 5 ;Aile d'hospitalisation 5            ;???? ????????? 5;E/S;2,84;.
B9;S307;GENERAL WARD 5 ;Aile d'hospitalisation 6            ;???? ????????? 5;SINGLE BED ROOM;18,69;Lit 2313
B12;S308;GENERAL WARD 5 ;Aile d'hospitalisation 5            ;???? ????????? 5;STORE;4,58;Réserve
B12;S309;GENERAL WARD 5 ;Aile d'hospitalisation 5          ;???? ????????? 5;TREATMENT PREP.;15,66;Salle de soins
B12;S310;GENERAL WARD 5 ;Aile d'hospitalisation 5            ;???? ????????? 5;C/U;8,43;Local propre
B12;S311;GENERAL WARD 5 ;Aile d'hospitalisation 5            ;???? ????????? 5;LINEN;1,82;Lingerie
B12;S312;GENERAL WARD 5 ;Aile d'hospitalisation 5            ;???? ????????? 5;NURSE OFFICE;9,54;Cadre infirmier
B9;S313;GENERAL WARD 5 ;Aile d'hospitalisation 6            ;???? ????????? 5;SINGLE BED ROOM;20,77;Lit 2314
B12;S314;GENERAL WARD 5 ;Aile d'hospitalisation 5            ;???? ????????? 5;E/S;3,90;.
B12;S315;GENERAL WARD 5 ;Aile d'hospitalisation 5            ;???? ????????? 5;E/S;3,90;.
B9;S316;GENERAL WARD 5 ;Aile d'hospitalisation 6            ;???? ????????? 5;SINGLE BED ROOM;20,61;Lit 2315
B12;S317;GENERAL WARD 5 ;Aile d'hospitalisation 5            ;???? ????????? 5;DR OFFICE;14,84;Bureau médecins
B12;S318;GENERAL WARD 5 ;Aile d'hospitalisation 5            ;???? ????????? 5;E/S;2,65;.
B9;S319;GENERAL WARD 5 ;Aile d'hospitalisation 6            ;???? ????????? 5;SINGLE BED ROOM;17,94;Lit 2317
B9;S320;GENERAL WARD 5 ;Aile d'hospitalisation 6            ;???? ????????? 5;SINGLE BED ROOM;18,82;Lit 2316
B12;S321;GENERAL WARD 5 ;Aile d'hospitalisation 5            ;???? ????????? 5;E/S;2,88;.
B12;S322;GENERAL WARD 5 ;Aile d'hospitalisation 5            ;???? ????????? 5;E/S;2,88;.
B9;S323;GENERAL WARD 5 ;Aile d'hospitalisation 6            ;???? ????????? 5;SINGLE BED ROOM;18,82;Lit 2321
B9;S324;GENERAL WARD 5 ;Aile d'hospitalisation 6            ;???? ????????? 5;SINGLE BED ROOM;18,82;Lit 2323
B12;S325;GENERAL WARD 5 ;Aile d'hospitalisation 5            ;???? ????????? 5;E/S;2,88;.
B12;S326;GENERAL WARD 5 ;Aile d'hospitalisation 5            ;???? ????????? 5;CORRIDOR;168,55;.
B6;S327;CIRCULATION;Couloir                         ;??? ;CORRIDOR;83,70;Couloir visiteurs
B4;S328;CIRCULATION;Couloir                         ;??? ;CORRIDOR;106,90;Couloir visiteurs
B3;S329;ROOF ;Terrasse;??? ;PLANT ROOM 2;500,19;Local centrales de traitement d'air TAL202 
B3;S330;ROOF ;Terrasse;??? ;PLANT ROOM 1;509,67;Local centrales de traitement d'air TAL201 
B3;S331;CIRCULATION;Couloir                         ;??? ;STAIR-02;30,43;Escalier 2
B5;S332;CIRCULATION;Couloir                         ;??? ;STAIR-03;29,77;Escalier 3
B5;S333;ROOF ;Terrasse;??? ;TERRACE;1753,52;Terrasse
B1;S334;VIP PREMIUM & SENIOR SUITE  ;Suites VIP                   ;???? ???? ????????;VIP LIFT-01;.;Ascenseur VIP (AL1)
B2;S335;HOSPITAL STREET ;Couloir médical                        ;??? ?????;LIFT-02;.;Ascenseur visiteurs 2 (AL2)
B2;S336;HOSPITAL STREET ;Couloir médical                        ;??? ?????;LIFT-03;.;Ascenseur visiteurs 1 (AL3)
B4;S337;HOSPITAL STREET ;Couloir médical                        ;??? ?????;LIFT-04(GOOD'S LIFT);.;Monte charge sale 1 (AL5)
B4;S338;HOSPITAL STREET ;Couloir médical                        ;??? ?????;LIFT-05;.;Monte charge propre 1 (AL6)
B4;S339;HOSPITAL STREET ;Couloir médical                        ;??? ?????;LIFT-06;.;Monte malades 2 (AL7)
B4;S340;HOSPITAL STREET ;Couloir médical                        ;??? ?????;LIFT-07;.;Monte malades 3 (AL8)
B8;S341;HOSPITAL STREET ;Couloir médical                        ;???? ????????? 6;LIFT-08;.;Ascenseur visiteurs 3 (AL14)
B6;S342;HOSPITAL STREET ;Couloir médical                        ;??? ?????;LIFT-09;.;Monte charge sale 2 (AL9)
B6;S343;HOSPITAL STREET ;Couloir médical                        ;??? ?????;LIFT-10;.;Monte charge propre 2 (AL10)
B6;S344;HOSPITAL STREET ;Couloir médical                        ;??? ?????;LIFT-11;.;Monte malade 3 (AL11)
B6;S345;HOSPITAL STREET ;Couloir médical                        ;??? ?????;LIFT-12;.;Monte malade 4 (AL12)
B1;S346;MEP;.;.;ELEC.;.;Tableau électrique TEL203
B1;S347;MEP;.;.;ELEC.;.;Tableau électrique TEL204
B4;S348;MEP;.;.;IDF ROOM;9,00;Local distribution courants faibles CfL203
B3;S349;MEP;.;.;ELEC.;.;Tableau électrique TEL205
B5;S350;MEP;.;.;ELEC.;.;Tableau électrique TEL206
B7;S351;MEP;.;.;ELEC.;.;Tableau électrique TEL207
B8;S352;MEP;.;.;ELEC.;.;Tableau électrique TEL208
B12;S353;MEP;.;.;ELEC.;.;Tableau électrique TEL209
B3;S354;Administration;Administration                                  ;  ???????  ;FEMALE WC;7,08;Toilettes femmes
B3;S355;Administration;Administration                                  ;  ???????  ;MALE WC;7,08;Toilettes hommes
B7;S356;GENERAL WARD 4 ;Aile d'hospitalisation 4          ;???? ????????? 4;NURSE STATION;14,31;Poste infirmier
B8;S357;GENERAL WARD 6 ;Aile d'hospitalisation 6            ;???? ????????? 6;NURSE STATION;19,18;Poste infirmier
B12;S358;GENERAL WARD 5 ;Aile d'hospitalisation 5            ;???? ????????? 5;NURSE STATION;17,82;Poste infirmier
B5;S359;Administration;Administration                                  ;  ???????  ;SERVER ROOM;9,29;Local serveurs IT
B6;S360;CIRCULATION;Couloir                         ;??? ;CORRIDOR;2,09;?
B6;S361;CIRCULATION;Couloir                         ;??? ;STAIR LOBBY;.;Escalier 01"""

		
		
		
		
		locloc = """B2;R002;MEP;;;ELEC. ROOM;12,00;Tableau électrique TEL301
B4;R003;OPEN TERRACE ;;;LIFT MOTOR ROOM;37,63;Local machinerie ascenseurs MAL301
B4;R004;CIRCULATION;Couloir                         ;??? ;STAIR-05;25,88;Escalier 5
B6;R005;OPEN TERRACE ;;;LIFT MOTOR ROOM;37,63;Local machinerie ascenseurs MAL302
B6;R006;MEP;;;ELEC. ROOM;9,00;Tableau électrique TEL302
B7;R007;MEP;;;PLANT ROOM;123,27;Local centrales de traitement d'air TAL301
B8;R008;MEP;;;PLANT ROOM;123,27;Local centrales de traitement d'air TAL302
B9;R009;MEP;;;PLANT ROOM;123,16;Local centrales de traitement d'air TAL303
B10;R010;MEP;;;PLANT ROOM;114,07;Local chaufferie
B11;R011;CIRCULATION;Couloir                         ;??? ;STAIR-08;24,50;Escalier  8
B4;R012;TELECOM;;;TELECOM ROOM;;Local TELECOM
B1;R013;OPEN TERRACE ;;;OPEN TERRACE;5869,05;."""

		def i=0
		TypeLocalisation local = TypeLocalisation.findByLibelle("Local")
		TypeLocalisation couloir = TypeLocalisation.findByLibelle("Couloir")
		
		for (String s:locloc.split("\n")) {
			def st = s.split(";")
			//println "########"+st.length
			//println "#####"+(i)+""+st[0]+"-"+st[1]+"-"+st[7]
			i++
			Localisation loc
			Localisation parent = Localisation.findByLibelle("IR-"+st[0].substring(1))
			if (parent) {
				loc = new Localisation(parent: parent, libelle:st[7], libelleCourt:st[1], ordre:new Integer(st[1].substring(1)))
			} else {
				println "no parent found for libelle " + "IR-"+st[0].substring(1) + " and local " + st[7]
				continue
			}
				
			if (st[3].trim().startsWith("Couloir")) {
				//println "######********-----> voici un couloir" 
				loc.typeLocalisation = couloir
			} else {
				loc.typeLocalisation = local
			}
			
			Unite unite = Unite.findByLibelle(st[3].trim())
			if (unite) {
				loc.unite = unite
			} else {
				if (loc.typeLocalisation != couloir){
					println "########Unite ----> Not found "+st[3]
				}
			}
			
			loc.save(flush:true)
			//println loc.errors
			if (loc.errors){
				println loc.errors
			}
		}
		render "done ! "
	}
	
	def corrigerLocaux(){
		println "#############corections"
		def i = 0
		def locloc="""B001;STAIR-01
B002;CHANGE
B003;WAITING
B004;CT SIMULATION
B005;CORRIDOR
B006;PLANNING
B007;HDR
B008;CONTROL
B009;CORRIDOR
B010;PREPARATION
B011;APPLICATION
B012;HOLD
B013;CONTROL ROOM
B014;BUNKER RADIOTHERAPY 1
B015;BUNKER RADIOTHERAPY 2
B016;BUNKER RADIOTHERAPY 3
B017;PATH TROUGH
B018;CONTROL ROOM FOR BUNKERS 3
B019;VIP LIFT-01
B020;ACCESS VIP
B021;WC
B022;CONSULTATION
B023;CONSULTATION
B024;OFFICE PHYSICIAN
B025;WC
B026;WC
B027;WC
B028;WAITING
B029;CONSULTATION
B030;ELEC.
B031;CHANGE
B032;CHANGE
B033;CONTROL ROOM FOR BUNKER 2
B034;CONTROL ROOM FOR BUNKER 1
B035;CHANGE
B036;NURSE STATION
B037;CHANGE
B038;TOILET
B039;TOILET
B040;CORRIDOR
B041;OFFICE
B042;BODY PICK-UP
B043;BODY HOLDING.12No.S
B044;VIEW
B045;FAMILY
B046;WC
B047;C/U
B048;ARCHIVES
B049;MOLDING
B050;DOSIMETRY
B051;NURSE OFFICE
B052;PHYSICIANS OFFICE
B053;RECOVERY ROOM
B054;MEETING ROOM
B055;DR OFFICE
B056;DR OFFICE
B057;STAFF FACILITIES CORRIDOR
B058;PATIENT CUB 7
B059;PAT WC
B060;PATIENT CUB 6
B061;PATIENT CUB 5
B062;PATIENT CUB 4
B063;PATIENT CUB 3
B064;PATIENT CUB 2
B065;OFFICE DIRECTOR
B066;WAITING
B067;SECRETARY
B068;CORRIDOR
B069;HK
B070;CORRIDOR
B071;CORRIDOR
B072;WAITING
B073;CASH
B074;RECEPTION
B075;PATIENT CUB 1 
B076;HK
B077;NURSES OFFICE
B078;C/U
B079;D/U
B080;CHEMO PREP
B081;STORE
B082;PATIENT CUB 11
B083;PATIENT CUB 10
B084;LINEN
B085;STAIR-02
B086;CORRIDOR
B087;TRANSFORMER ROOM
B088;TRANSFORMER ROOM
B089;MEDICAL WASTE WALK-IN REFRIGERATOR
B090;TROLLEY WASH
B091;TRASH SEPARATOR AREA
B092;LV ROOM
B093;LV ROOM
B094;MG ROOM
B095;MG ROOM
B096;CORRIDOR
B097;A.H.U
B098;OFFICE
B099;HK
B100;STERILE GOODS STAFF
B101;C/U
B102;D/U
B103;LINEN STORE
B104;STAFF WC
B105;STAFF WC
B106;OFFICE
B107;SEWING AND UNIFORM STORE
B108;LOADING + SERVING
B109;WASHER
B110;TROLLEYS / WASH
B111;CSSD
B112;CLEAN TROLLEY PARK
B113;STERILE GOODS STORE
B114;CORRIDOR
B115;CORRIDOR
B116;CLEAN SUPPLIES
B117;OFFICE
B118;STAFF REST
B119;STAFF CHANGE
B120;STAFF CHANGE
B121;STAFF WC
B122;STAFF WC
B123;STAIR-03
B124;LOADING & UNLOADING BAY
B125;CORRIDOR
B126;QUARANTINE
B127;RECEIVE + RECORDS
B128;RECEIVE + RECORDS
B129;OFFICE
B130;CORRIDOR
B131;CORRIDOR
B132;STAFF WC
B133;STAFF CHANGE
B134;STORE
B135;OFFICE SUPPLIES
B136;STAFF WC
B137;STAFF WC
B138;OFFICE
B139;STAFF WC
B140;STAFF CHANGE
B141;WASH
B142;PACK
B143;STORE
B144;PROSTHESIS STORE
B145;GENERAL SUPPLIES
B146;HYGENIC SUPPLIES
B147;STORE
B148;CHEMICAL STORE
B149;MEDICINE BULK STORE
B150;MEDICAL SUPPLIES
B151;STAFF REST
B152;OFFICE
B153;DANGEROUS DRUG STORE
B154;LIBRARY
B155;NOT USED
B156;STORE SMALL
B157;TROLLEY PARK
B158;STAFF REST
B159;OFFICE
B160;SEC.
B161;OFFICE
B162;STOCK DEPORT
B163;RETURN & SORTING
B164;RECEPTION
B165;ISSUE + DISPATCH
B166;STORE PALLETS
B167;CORRIDOR
B168;GOODS IN & DECANT
B169;DRY STORE
B170;RAW WASH
B171;CORRIDOR
B172;DAIRY COLD ROOM
B173;MEAT & POULTRY COLD ROOM
B174;F&V COLD ROOM
B175;STAFF CHANGE
B176;GENERAL PURPOSE COLD ROOM
B177;STAFF CHANGE
B178;FISH COLD ROOM
B179;STAFF REST
B180;CHEF'S OFFICE
B181;MEAT & POULTRY PREP.
B182;FISH PREP AREA
B183;PAN WASH AREA
B184;HOT KITCHEN
B185;TRAINING & LOADING
B186;CORRIDOR
B187;DISH WASH AREA
B188;TROLLEY WASH
B189;REFUSE HOLD
B190;CORRIDOR
B191;A.H.U
B192;SERVER ROOM
B193;OFFICE
B194;IT WORK AREA
B195;MEDICAL EQ.UPS
B196;D/U
B197;I.T.UPS
B198;BATTERY ROOM
B199;CORRIDOR
B200;RECEPTION
B201;WORKSHOP
B202;WORKSHOP-BIOMEDICAL ENGINEERING
B203;PRODUCT INFORMATION + ENGINEERING OFFICE
B204;CORRIDOR
B205;RECEPTION
B206;CORRIDOR
B207;IDF ROOM
B208;OFFICE
B209;STORE ROOM
B210;LIFT LOBBY
B211;STAIR-05
B213;CORRIDOR
B214;TOILET LOBBY
B215;SHOWER LOBBY
B216;STORE
B217;TOILET LOBBY
B218;SHOWER LOBBY
B219;MEN STAFF CHANGE & LOCKERS
B220;WOMEN STAFF CHANGE & LOCKERS
B221;LOBBY
B222;STAIR-06
B223;IDF ROOM
B224;ELEC
B225;CAFETERIA
B226;AUDITORIUM
B227;FEMALE WC
B228;PROJECTION ROOM
B229;MALE WC
B230;STORE
B231;STORE
B232;HK
B233;RECEPTION / WAITING
B234;STAIR-09
B235;CORRIDOR
B236;SEMINAR-1
B237;SEMINAR-2
B238;EXTERIOR DINING SPACE
B239;MTR ROOM
B240;LIFT-02
B241;LIFT-03
B242;LIFT-04(GOOD'S LIFT)
B243;LIFT-05
B244;LIFT-06
B245;LIFT-07
B246;LIFT-09
B247;LIFT-10
B248;LIFT-11
B249;LIFT-12
B250;COLD KITCHEN
B251;CORRIDOR
B252;GENERAL WASTE WALK-IN REFRIGERATOR
B253;OFFICE
B254;ELEC.
B255;DIRTY HOIST
B256;NURSE STATION
B257;ELEC.
B258;ELEC.
B259;ELEC.
B260;ELEC.
B261;ELEC.
B262;OFFICE
B263;STAFF REST
B264;SECRETARY
B265;REFUSE HOLD
B266;DEEP FREEZE
B267;F&V PREP.
B268;LOCKER
B269;CORRIDOR
B270;CORRIDOR
B271;LOCKER
B272;HK STORE
B273;OFFICE
B274;OFFICE
B275;STORE
B276;OFFICE
B277;STAFF CHANGE
B278;STAFF CHANGE
B279;GSM ROOM
B280;CLEAN HOIST
B281;CORRIDOR
B282;LOBBY
B283;WC
B284;STORE
B285;SERVER
B286;AIR LOCK/STORAGE
B287;INSTRUMENT PASSING
B288;CHANGE
B289;ELEC.
B290;CHANGE
B291;CHANGE
B292;MORTUARY LOADING DOCK
B293;SERVICE YARD
B294;SHOWER
B295;DISABLE TOILET
B296;TOILET 
B297;TOILET 
B298;TOILET 
B300;TOILET 
B301;SHOWER
B302;SHOWER
B303;SHOWER
B304;SHOWER
B305;SHOWER
B306;SHOWER
B307;TOILET 
B308;TOILET 
B309;TOILET 
B310;TOILET 
B311;SHOWER
B312;SHOWER
B313;CHANGE
B314;CHANGE
B315;COURTYARD BLOCK 1
B316;W.C
B317;W.C
B318;W.C
B319;W.C
B320;DIS. TOILET LOBBY
B321;DIS. TOILET  
B323;STORE
F001;STAIR-1
F002;EQUIPMENT
F003;CARDIAC CATHETER LAB 1
F004;CARDIAC CATHETER LAB 2
F005;CORRIDOR
F006;CONTROL ROOM
F007;CORRIDOR
F008;SCRUB
F009;CATH STORE
F010;SCRUB
F011;PREPARATION
F012;PREPARATION
F013;MALE CHANGING RM 
F014;FEMALE CHANGING RM 
F015;STAFF
F016;D/U
F017;DR OFFICE 
F018;HEAD NURSE OFFICE 
F019;OFFICE
F020;MONITOR HOLTER
F021;C/U
F022;ELEC.
F023;TROLLEY PARKING
F024;ECHOCARDIOGRAM
F025;STRESS
F026;ECG
F027;RECEPTION
F028;ECHOCARDIOGRAM
F029;WAITING 
F030;DISABLED TOILET
F031;TOILET
F032;TOILET
F033;VIP LIFT-01
F034;PATIENT ROOM 1
F035;PATIENT ROOM 2
F036;PATIENT ROOM 3
F037;PATIENT ROOM 4
F038;PATIENT ROOM 5
F039;PATIENT ROOM 6
F040;STORE
F041;CORRIDOR
F042;DR OFFICE
F043;MEETING ROOM
F044;LINEN
F045;H.K.
F046;D/U
F047;EQUIPMENT STORE
F048;DR OFFICE 2
F049;ELEC.
F050;NURSE STATION
F051;DR OFFICE 3
F052;C/U
F053;MED
F054;LINEN
F055;TOILET
F056;TOILET
F057;STAFF REST
F058;ON-CALL ROOM
F059;NURSE OFFICE
F060;HEAD NURSE OFFICE
F061;CORRIDOR
F062;OFFICE
F063;CORRIDOR
F064;MALE CHANGING RM 
F065;CORRIDOR
F066;FEMALE CHANGING RM 
F067;LIFT-13
F068;LOBBY
F069;ELEC.
F070;STERILE STORE
F071;STAIR-2
F072;EMERGENCY ESCAPE
F073;PATIENT ROOM 7
F074;PATIENT ROOM 8
F075;PATIENT ROOM 9
F076;PATIENT ROOM 10
F077;PATIENT ROOM 11
F078;PATIENT ROOM 12
F079;CORRIDOR
F080;LINEN
F081;HK
F082;D/U
F083;CORRIDOR
F084;EQUIPMENT STORE
F085;DR OFFICE 
F086;STORE
F087;NURSE STATION
F088;C/U
F089;MEDICATION
F090;HEAD NURSE OFFICE
F091;HK
F092;DR OFFICE 
F093;STAFF REST
F094;SECRETARY
F095;ELEC.
F096;PATIENT ROOM 5
F097;PATIENT ROOM 4
F098;PATIENT ROOM 3
F099;PATIENT ROOM 2
F100;PATIENT ROOM 1
F101;CORRIDOR
F102;WAITING 
F103;INTERVIEW/MEETING ROOM
F104;ON-CALL ROOM
F105;WC
F106;HK
F107;C/U
F108;D/U
F109;NURSE STATION
F110;MED.
F111;LINEN
F112;NURSES OFFICE
F113;STERILE SUPPLIES
F114;STORE
F115;CORRIDOR
F116;FEMALE CHANGING RM
F117;MALE CHANGING RM
F118;PANTRY
F119;CORRIDOR
F120;T/S
F121;FEMALE STAFF
F122;MALE STAFF
F123;T/S
F124;CORRIDOR
F125;CORRIDOR
F126;FEMALE DRS
F127;T/S
F128;WC/S
F129;CORRIDOR
F130;MALE DRS
F131;DR LOUNGE
F132;BOOTS
F133;STAFF LOUNGE
F134;ELEC.
F135;TRANSFER LOBBY
F136;RECOVERY
F137;NURSE STATION
F138;BED PARK
F139;TROLLEY WIPE
F140;TROLLEY PARK
F143;OT TRANSFER
F144;C/U
F145;HK
F146;DR OFFICE
F147;NURSE OFFICE
F148;NURSE STATION
F149;CLEAN HOIST
F150;DIRTY HOIST
F151;DISP.
F152;STERILE STORE
F153;OB/GYN OPERATING THEATRE 7
F154;PARK EQUIPMENT
F155;EQUIPMENT STORE
F156;PHARMACY
F157;CORRIDOR
F158;STORE
F159;NEURO,ORTHO,TRAUMA OPERATING THEATRE 1
F160;SCRUB
F161;DISPOSAL
F162;ENT,OPHTHALMIC OPERATING THEATRE 2
F163;SCRUB
F164;DISPOSAL
F165;CARDIO VASCULAR OPERATING THEATRE 3
F166;SCRUB
F167;STORE
F168;STORE
F169;GENERAL OPERATING THEATRE 4
F170;SCRUB
F171;DISPOSAL
F172;GENERAL OPERATING THEATRE 5
F173;SCRUB
F174;STORE
F175;ELEC.
F176;TROLLEY PARKING
F177;GENERAL OPERATING THEATRE 6
F179;DISPOSAL
F180;SCRUB
F181;STERILE SUPPLIES
F182;PERFUSSION
F183;ANESTHESIA
F184;STORE
F185;OFFICE
F186;CORRIDOR
F187;STORAGE
F188;LABOUR/DELIVERY
F189;LABOUR/DELIVERY
F190;LABOUR/DELIVERY
F191;LINEN
F192;CORRIDOR
F193;ELEC.
F194;INFECTIONS O.R. 8
F195;SCRUB
F196;SCRUB UP
F197;DISP.
F198;NURSE STATION
F199;C/U
F200;D/U DISPOSAL
F201;WAITING
F202;WC
F203;WC
F204;CORRIDOR
F205;BABY FEED ROOM
F206;DR OFFICE
F207;MOTHERS WAITING
F208;INTERVIEW
F209;NURSE OFFICE
F210;ELEC.
F211;CORRIDOR
F212;PATIENT CUB 5&6
F213;VIEWING GALLERY
F214;PATIENT CUB 3&4
F215;NURSE STATION
F216;EQUIPMENT STORE
F217;C/U
F218;D/U
F219;PATIENT CUB 1&2
F220;ISOLATION 8
F221;ISOLATION 7
F223;ANTE
F224;LINEN
F225;HK
F226;STAFF REST
F227;FEMALE CHANGE
F228;M LOCKERS
F229;WC
F230;WC
F231;CORRIDOR
F232;F. CHANGE
F233;M. CHANGE
F234;EQUIPMENT STORE
F235;ROOM 1
F236;ROOM 2
F237;NURSE STATION
F238;STAFF CH.WC
F239;STAFF CH.WC
F240;D/U
F242;CORRIDOR
F243;LINEN
F244;DR OFFICE
F245;CORRIDOR
F247;ROOM 3
F248;ROOM 4
F249;C/U
F250;CLINICAL BAY/TREATMENT
F251;CORRIDOR
F252;STAIR 3
F253;OFFICE
F254;OFFICE
F255;OFFICE
F256;CORRIDOR
F257;CORRIDOR
F258;SECRETARY
F259;OFFICE
F260;OFFICE
F261;STAIR 6
F262;ELEC.
F263;IDF ROOM
F264;LIFT LOBBY
F265;LIFT-09
F266;LIFT-10
F267;LIFT-11
F268;LIFT-12
F269;CORRIDOR
F270;LINEN
F271;HK
F272;STAFF WC
F273;CORRIDOR
F274;PAT WC
F275;PAT WC
F276;DR OFFICE/EXAM RM
F277;NURSE STATION
F278;CORRIDOR
F279;PATIENT ROOM 1
F280;PATIENT ROOM 2
F281;PATIENT ROOM 3
F282;PATIENT ROOM 4
F283;PATIENT ROOM 5
F284;PATIENT ROOM 6
F285;PATIENT ROOM 7
F286;D/U   
F287;C/U
F288;PAT. WC 
F289;PAT. WC 
F290;STAIR 5
F291;WC
F292;WAITING
F293;ELEC.
F296;CORRIDOR
F297;LIFT LOBBY
F298;CORRIDOR
F299;LIFT-07
F300;LIFT-06
F301;LIFT-05
F302;LIFT-04(GOOD'S LIFT)
F303;IDF ROOM
F304;MEETING ROOM
F305;MEETING ROOM
F306;PANTRY
F307;WC
F308;MEETING ROOM
F309;CORRIDOR
F310;OFFICE
F311;OFFICE
F312;OFFICE
F313;STAFF WC
F314;STAFF WC
F315;CORRIDOR
F316;LINK CORRIDOR
F317;STAFF REST ROOM
F318;CORRIDOR
F319;DIS. WC
F320;DIS. WC
F321;ELEC.
F322;IDF ROOM
F323;VISITORS & WAITING LOUNGE
F324;BABY CHANGE
F325;DIS. WC
F326;LIFT-02
F327;LIFT-03
F328;CORRIDOR
F329;WASH+BOOT LOBBY
F330;WASH
F331;WC
F332;WC
F333;PRAYER ROOM
F334;STAIR 4
F335;DISPOSAL
F336;WC
F337;CCU SINGLE BED ROOM
F338;CCU SINGLE BED ROOM
F339;WC
F340;WC
F341;RECOVERY SINGLE BED 
F342;RECOVERY SINGLE BED 
F343;WC
F344;CORRIDOR
F345;NURSE STATION
F346;CLEAN LINEN ROOM
F347;C/U
F348;STORE
F349;D/U
F350;HK
F353;CONS./ EX.
F355;CONS./ EX.
F356;WAITING
F357;TREATMENT
F358;C/U
F359;CORRIDOR
F360;OFFICE
F361;HOME TREATMENT
F362;WC
F363;RECEPTION OFFICE
F364;WAITING
F365;CCU SINGLE BED ROOM
F366;WC
F367;WC
F368;CCU SINGLE BED ROOM
F369;WAITING
F370;RECEPTION
F371;EXAM
F372;HOME TREATMENT RM
F373;EXAM
F374;E/S
F375;SINGLE BED ROOM
F376;SINGLE BED ROOM
F377;E/S
F378;E/S
F379;SINGLE BED ROOM
F380;SINGLE BED ROOM
F381;E/S
F382;E/S
F383;SINGLE BED ROOM
F384;SINGLE BED ROOM
F385;E/S
F386;DR OFFICE
F387;SINGLE BED ROOM
F388;E/S
F389;E/S
F390;SINGLE BED ROOM
F391;NURSE OFFICE
F392;LINEN
F393;NURSE STATION
F394;C/U
F395;TREATMENT PREP
F396;STORE
F397;SINGLE BED ROOM
F398;E/S
F399;E/S
F400;SINGLE BED ROOM
F401;SINGLE BED ROOM
F402;E/S
F403;E/S
F404;SINGLE BED ROOM
F405;SINGLE BED ROOM
F406;E/S
F407;E/S
F408;SINGLE BED ROOM
F409;SINGLE BED ROOM
F410;E/S
F411;HK
F412;DISPOSAL
F413;E/S
F414;SINGLE BED ROOM
F415;SINGLE BED ROOM
F416;E/S
F417;E/S
F418;SINGLE BED ROOM
F419;SINGLE BED ROOM
F420;E/S
F421;E/S
F422;CORRIDOR
F423;SINGLE BED ROOM
F424;SINGLE BED ROOM
F425;E/S
F426;PANTRY
F427;D/U
F428;ELEC.
F429;STAIR 09
F430;CORRIDOR
F431;E/S
F432;SINGLE BED ROOM
F433;SINGLE BED ROOM
F434;E/S
F435;E/S
F436;SINGLE BED ROOM
F437;SINGLE BED ROOM
F438;E/S
F439;E/S
F440;SINGLE BED ROOM
F441;NURSERY
F442;CORRIDOR
F443;STAIR 08
F444;LIFT-08
F445;LOBBY
F446;RECEPTION
F448;DIALYSIS ROOM 8
F449;DIALYSIS ROOM 7
F450;DIALYSIS ROOM 6
F451;DIALYSIS ROOM 5
F452;VIP DIALYSIS ROOM
F453;CORRIDOR
F454;C/U
F455;H.K.
F456;TOILET
F457;TOILET
F458;CORRIDOR
F459;STORE CONSUMABLE
F460;DR OFFICE
F461;DIALYSIS ROOM 2
F462;DIALYSIS ROOM 3
F463;DISPOSAL D/U
F464;NURSE STATION
F465;DIALYSIS ROOM 4
F466;DIALYSIS ROOM 1
F467;WAITING
F468;ELEC.
F469;CCU SINGLE BED ROOM
F470;CCU SINGLE BED ROOM
F471;WC
F472;STORE
F473;STORE
F474;CORRIDOR
F475;LABORATORY
F476;TREATMENT 4
F477;DISPOSAL D/U
F478;LINEN
F479;CONSUMABLE STORE
F480;ELEC.
F481;CORRIDOR
F482;TREATMENT 1
F483;PAT WC
F484;PAT WC
F485;CORRIDOR
F486;STAFF WC
F487;STAIR 07
F488;TREATMENT 2
F489;TREATMENT 3
F490;CCU SINGLE BED ROOM
F491;WC
F492;CCU SINGLE BED ROOM
F493;WC
F494;WC
F495;CCU SINGLE BED ROOM
F496;CCU SINGLE BED ROOM
F497;WC
F498;WC
F499;CCU SINGLE BED ROOM
F500;CCU SINGLE BED ROOM
F501;WC
F502;HK
F503;CORRIDOR
F504;SCRUB-UP
F505;STAIR LOBBY
G001;STAIR-01
G002;ELEC.
G003;EX / TR
G004;CORRIDOR
G005;EX / TR
G006;CORRIDOR
G007;RESUSCITATION AREA
G008;NURSE STATION
G009;ON CALL ROOM
G010;ON CALL ROOM
G011;ON CALL ROOM
G012;ON CALL ROOM
G013;ON CALL ROOM
G014;CORRIDOR
G015;EX / TR
G016;EX / TR
G017;EX / TR
G018;EX / TR
G019;HEAD NURSE
G020;NURSE STATION
G021;LINEN STORE
G022;HEAD OF DEPARTMENT
G023;EQUIP. STORE
G024;SECRETARY
G025;D/U
G026;DR. OFFICE
G027;CALL CENTRE
G028;W.C
G029;W.C
G030;CORRIDOR
G031;H.K
G032;PANTRY
G033;DISPENSARY
G034;DISPENSARY
G035;STORE CONSUMABLES
G036;STORE CONSUMABLES
G038;W.C
G039;LOBBY
G040;RECEPTION
G041;LOBBY/CORRIDOR
G042;EMERGENCY ENTRANCE
G043;WAITING
G044;SECURITY CENTRE
G045;STAFF REST
G046;LOBBY
G048;CORRIDOR
G049;OFFICE
G050;TREATMENT ROOM
G051;TREATMENT ROOM
G052;TREATMENT ROOM
G053;TREATMENT ROOM
G054;GYM
G055;SOCIAL SERVICES OFFICE
G056;PAT.CH & WC
G057;PAT.CH & WC
G058;NURSE STATION
G059;HK
G060;EQUIP.STERILE
G061;P.R.OFFICES
G062;EXAM
G063;DR. OFFICE
G064;WAITING
G065;C/U
G066;D/U
G067;STAFF W.C.
G068;CORRIDOR
G069;STAFF W.C.
G070;STAFF REST
G071;STAIR-02
G072;CORRIDOR
G073;X-RAY ROOM
G074;X-RAY ROOM
G075;X-RAY ROOM
G076;DIGITIZER
G077;PRINT ROOM
G078;BED WAIT
G079;STORAGE
G080;SECRETARY
G082;DR OFFICE
G083;CT SCANNER
G084;CORRIDOR
G085;CORRIDOR
G086;REPORTING ROOM
G087;CONTROL ROOM
G088;NURSE STATION 3
G089;CONTROL ROOM
G090;EQUIPMENT ROOM
G091;MRI
G092;SECURE ENTRANCE
G093;CONTROL ROOM
G093;CONTROL  ROOM
G094;MRI SHELLED
G096;DENTAL X-RAY
G097;CT SCANNER
G098;ULTRASOUND
G099;BONE DENSITYOMETRY
G100;ULTRASOUND
G101;MAMMOGRAPHY FULL FIELD
G102;PANTOGRAPHY
G103;INTERVENTIONAL ULTRASOUND
G104;CORRIDOR
G105;REPORTING RM.
G107;DR.
G108;D/U
G109;C/U
G110;SECRATARY
G112;RECEPTION 
G114;OFFICE ADMIN
G115;W.C
G116;W.C
G117;WASH
G118;WAITING AREA
G119;FILM STORE
G120;PATIENT ROOM
G121;PATIENT ROOM
G122;PATIENT ROOM VIP
G123;PATIENT ROOM
G124;PATIENT ROOM
G125;PATIENT ROOM VIP
G126;W.C
G127;W.C
G128;W.C
G129;W.C
G130;WASH
G131;CORRIDOR
G132;DR
G133;RECEPTION
G134;CORRIDOR
G135;LINEN 
G136;DIRTY HOIST
G137;CLEAN HOIST
G138;OFFICE
G139;NURSE STATION
G140;HK
G141;WAITING AREA
G145;PATIENT ROOM
G146;TOILET
G148;PATIENT ROOM
G149;TOILET
G150;INJECTION CUBICLE 1
G151;CONTROL RM
G152;PET SCANNER
G153;CORRIDOR
G154;CORRIDOR
G156;NURSE STATION
G157;PATIENT CLOAK ROOM
G158;INJECTION CUBICLE 4
G160;INJECTION CUBICLE 2
G161;GAMA STRESS
G162;CORRIDOR
G163;HOLD
G164;HOT TOILET
G165;HOT TOILET
G167;MANLOCK
G168;CHANGE
G169;CLOAK ROOM
G170;CLOAK ROOM
G171;INJECTION
G173;HOT LAB
G174;MANLOCK
G175;INJECTION CUBICLE 3
G176;OFFICE
G178;RECEPTION
G179;CORRIDOR
G180;WAITING
G181;WAITING
G182;PATIENT CUB 1
G183;PATIENT CUB 2
G184;PATIENT CUB 3
G185;PATIENT CUB 4
G186;PATIENT CUB 5
G187;PATIENT CUB 6
G188;PATIENT CUB 7
G189;PATIENT CUB 8
G190;PATIENT CUB 9
G192;CORRIDOR
G193;CORRIDOR
G194;NURSE STATION
G195;PARKING
G196;C/U
G197;D/U
G199;CORRIDOR
G200;CORRIDOR
G201;RECEPTION
G202;OFFICE
G203;REST
G204;STORE
G205;WC/ST
G206;WC/ST
G208;POST-PCR
G209;STORE/SLIDES
G211;STORE
G212;HISTO/CYTO
G214;CORRIDOR
G215;MEDIA
G216;MICROBIOLOGY
G217;STORE
G218;RECEPTION
G219;PATHOLOGIST OFFICE
G221;GLASS/WASH STERILIZER
G222;FLOW CYTOMETRY
G223;COLD STORE
G225;SEROLOGY
G226;IMMUNOLOGY
G227;HEMATOLOGY HEMOSTASIS
G228;CLINICAL CHEMISTRY
G229;WC
G230;WC
G231;STORE
G232;SAMPLE PROCESSING
G233;STORE
G234;DELIVERY OF SAMPLES
G236;RECEPTION
G237;STORE
G238;APHAERESIS AND BLOOD DONATION
G239;LOUNGE
G240;ADMINISTRATIVE SPACE
G241;ADMINISTRATIVE SPACE
G242;ADMINISTRATIVE SPACE
G243;ADMINISTRATIVE SPACE
G244;ADMINISTRATIVE SPACE
G245;ADMINISTRATIVE SPACE
G246;HEAD OFFICE
G247;SECRETARIES
G248;BLOOD BANK
G249;RECEPTION/WAITING
G250;CORRIDOR
G251;VIP VISITOR RECEIVING AND WAITING
G252;PATIENTS & VISITORS LOUNGE
G253;RECEPTION INFORMATION & ATRIUM
G254; ENTRANCE LOBBY
G255;CORRIDOR
G256;STORE
G257;SERVERY
G258;DIS.W.C.
G259;PAT.W.C
G260;PAT.W.C
G261;SHOP
G262;CAFÉ
G263; EXAM
G264; EXAM
G265;CONSULT. TREATMENT
G266;RECEPTION
G267;WAITING
G268;PUVA
G269;PUVA
G270;LASER
G271;EXAM 3
G272;BLOOD DRAWING 1
G273;EXAM 1
G274;EXAM 4
G275;WAITING
G276;RECEPTION
G277;W.C.
G278;SPECIMEN
G279;D/U URINE
G280;BLOOD SAMPLE
G281;EXAM
G282;EXAM
G283;RECEPTION
G284;STORE
G285;EEG
G286;EGM
G287;LOBBY
G288;STAIR-05
G289;W.C
G290;W.C
G291;LASER TREATMENT
G292;VISION FIELD TEST
G293;RETINAL SCREENING
G294;TONOMETRY
G295;CORRIDOR
G296;W.C
G297;EXAM 1
G298;STORE
G299;EXAM 2
G300;EXAM 3
G301;RECEPTION
G302;WAITING
G303;MICROSCOPY
G304;TREATMENT RM
G305;W.C.
G306;AUDIOMETRY
G307;EXAM 1
G308;EXAM 2
G309;DISPOSAL   
G310;LIFT LOBBY
G311;STAIR  06
G312;PANTRY
G313;W.C
G314;W.C
G315;CORRIDOR
G316;MEETING ROOM
G317;MEETING ROOM
G318;OFFICE
G319;OFFICE
G320;CORRIDOR
G321;CORRIDOR
G322;CORRIDOR
G323;CORRIDOR
G324;CORRIDOR
G325;CORRIDOR
G326;CORRIDOR
G327;WAITING IP ADMITTING
G328;WAITING IP DISCHARGE
G329;CORRIDOR
G330;BACK OFFICE 1
G331;BACK OFFICE 2
G332;BACK OFFICE 3
G333;BACK OFFICE 4
G334;BACK OFFICE 5
G335;BACK OFFICE 6
G336;BACK OFFICE 7
G337;BACK OFFICE 8
G338;BACK OFFICE 9
G339;BACK OFFICE 10
G340;OFFICE (SOPER/MGR)
G341;OFFICE (FINANCE)
G342;OUT PATIENT REGISTRATION & WAITING
G343;RECEPTION
G344;ENTRANCE LOBBY
G345;WAITING HALL
G346;STAIR-07
G347;BABY CH.
G348;DIS.W.C.
G349;LOBBY
G350;RECEPTION/OFFICE
G351;PAT.W.C
G352;PAT.W.C
G353; EXAM 1
G354; EXAM 2
G355; EXAM 3
G356; EXAM 4
G357; EXAM 5
G358;WAITING/CORRIDOR
G359;RECEPTION/OFFICE
G360;TREATMENT RM
G361; EXAM 4
G362; EXAM 3
G363; EXAM 2
G364; EXAM 1
G365;C/U
G366;D/U   
G367;LOBBY/WAITING
G368;RECEPTION
G369;W.C
G370;W.C
G371;CORRIDOR
G372; EXAM 1
G373; EXAM 2
G374;ECG
G375;ECG
G376; EXAM 4
G377; EXAM 3
G378; EXAM 2
G379; EXAM 1
G380;WAITING
G381;NURSE STATION
G382;WAITING
G383;OP/GYN/PAEDIATRIC TREATMENT RM
G384;CORRIDOR
G385; EXAM 3
G386; EXAM 2
G387; EXAM 1
G388;BABY CH.
G389;TOY STORE
G390;CORRIDOR
G391;DIS.W.C
G392;PAT.W.C
G393;PAT.W.C
G394;C/U
G395;D/U
G396;H.K 
G397;STAFF REST
G398;CORRIDOR
G399;STAIR-08
G400;LOBBY
G401;STAFF.WC
G402;STAFF.WC
G403;RECEPTION/OFFICE
G404;WASH
G405;STORE
G406; EXAM 1
G407; EXAM 2
G408; EXAM 3
G409;SPIROMETRY
G410;TREATMENT/REST
G411;C/U
G412;D/U
G413;CORRIDOR
G414;WAITING
G415;LITHOTRIPSY
G416;EXAM 1
G417;EXAM 2
G418;URODYNAMICS
G419;DIS. W.C.
G420;BABY CH.
G421;RECEPTION/OFFICE
G422;LOBBY
G423;STAIR-09
G424;PAT.W.C
G425;PAT.W.C
G426;WAITING
G427;RECEPTION
G428; EXAM 1
G429; EXAM 2
G430;C/U
G431;D/U
G432;OFFICE
G433;OFFICE
G434;PHMETRY
G435;EXAM
G436;CORRIDOR
G437;PAT.W.C
G438;PAT.W.C
G439;SCRUB UP
G440;NURSE STATION
G441;ENDOSCOPY 1
G442;ENDOSCOPY 2
G443;ENDOSCOPY 3
G444;ENDOSCOPY CORRIDOR
G445;ENDOSCOPY 4
G446;DECONTAMINATION RM
G447;DISPOSAL
G448;STERILIZATION RM
G449;CORRIDOR
G450;H.K
G451;W.C/SH.
G452;PATIENT ROOM. 8
G453;PATIENT ROOM. 7
G454;W.C./SH.
G455;PATIENT CUB 6
G456;PATIENT CUB 5
G457;PATIENT CUB 4
G458;PATIENT CUB 3
G459;PATIENT CUB 2
G460;PATIENT CUB 1
G461;STAIR-03
G462;CORRIDOR
G463;LINEN
G464;W.C
G465;VIP LIFT-01
G466;LIFT-02
G467;LIFT-03
G468;LIFT-04(GOOD'S LIFT)
G469;LIFT-05
G470;LIFT-06
G471;LIFT-07
G472;LIFT-08
G473;LIFT-09
G474;LIFT-10
G475;LIFT-11
G476;LIFT-12
G477;LIFT-13
G480;IDF ROOM
G481;ELECT.
G482;IDF ROOM
G483;ELECT.
G484;STAIR-04
G485;IDF 
G486;ELEC.
G487;ELEC.
G488;ELEC.
G489;ELEC.
G490;ELEC.
G491;ELEC.
G492;ELEC.
G493;ELEC.
G495;ELEC.
G496;ELEC.
G497;ELEC.
G498;TERRACE
G499;OUT DOOR PLAY AREA
G500;CORRIDOR
G501;MAMMOGRAPHY
G502;W.C
G503;F.CHANGE
G504;M.CHANGE
G505;WAITING/CORRIDOR
G507;W.C
G508;PRE-PCR
G509;DARK STORE
G510;D/U
G511;UNNAMED
G512;UNNAMED
G513;WASH
G514;ELEC.
G515;WAITING
GR001;GUARD ROOM                    IN GATE 5
GR002;GUARD ROOM                    IN GATE 3
GR003;GUARD ROOM                    IN GATE 1
MGP001;MEDICAL AIR
MGP002;MEDICAL VACCUM PLANT
MGP003;BOTTLE STORAGE AREA
MGP004;AGSS ROOM
MGP005;TECHNICAL ROOM FOR NO2 PLANT
NT001;STAIR CASE
NT002;STAIRCASE & LIFT LOBBY
NT003;NUCLEAR WASTE ROOM
NT004;NUCLEAR TANK ROOM
NT005;NUCLEAR WASTE LIFT
OA001;TRANSFORMER ROOM
OGP001;O2 HIGH PRESSURE
OGP002;O2 LOW PRESSURE
OGP003;AIR COMPRESSOR
R001;GSM ROOM
R002;ELEC. ROOM
R003;LIFT MOTOR ROOM
R004;STAIR-05
R005;LIFT MOTOR ROOM
R006;ELEC. ROOM
R007;PLANT ROOM
R008;PLANT ROOM
R009;PLANT ROOM
R010;PLANT ROOM
R011;STAIR-08
R012;TELECOM ROOM
R013;OPEN TERRACE
S001;STAIR-01
S002;PREMIUM SUITE-BED
S003;PANTRY / KITCHENETTE
S004;PREMIUM SUITE-LIVING
S005;TOILET
S006;PREMIUM SUITE-BED
S007;PREMIUM SUITE-BED
S008;TOILET
S009;PREMIUM SUITE-LIVING
S010;PANTRY / KITCHENETTE
S011;PREMIUM SUITE-BED
S012;PANTRY / KITCHENETTE
S013;PREMIUM SUITE-BED
S014;PREMIUM SUITE-LIVING
S015;TOILET
S016;PREMIUM SUITE-BED
S017;PREMIUM SUITE-BED
S018;TOILET
S019;PANTRY / KITCHENETTE
S020;PREMIUM SUITE-LIVING
S021;PREMIUM SUITE-BED
S022;NURSES OFFICE
S023;SECRETARY
S024;MED CARE
S025;WC
S026;NURSES REST
S027;RECEPTION ROOM
S028;PHARMACY
S029;DU
S030;PANTRY
S031;EXAM ROOM
S032;NURSE STATION
S033;VIP LOBBY
S034;CORRIDOR
S035;SENIOR SUITE ROOM
S036;PANTRY / KITCHENETTE
S037;SENIOR SUITE ROOM
S038;WC
S039;SENIOR SUITE ROOM
S040;WC
S041;PANTRY / KITCHENETTE
S042;SENIOR SUITE ROOM
S043;SENIOR SUITE ROOM
S044;PANTRY / KITCHENETTE
S045;SENIOR SUITE
S046;WC
S047;SENIOR SUITE ROOM
S048;WC
S049;SENIOR SUITE LIVING
S050;PANTRY / KITCHENETTE
S051;CORRIDOR
S052;HK
S053;LINEN
S054;D/U
S055;C/U
S056;PANTRY
S057;OFFICE
S058;SECURITY
S059;SECRETARY
S060;MEDICAL DIRECTOR
S061;WC& WASH
S062;SECRETARY
S063;OFFICE  EN SUITE
S064;WC & WASH
S065;OFFICE
S066;OFFICE
S067;OFFICE
S068;BOARD ROOM
S069;WC& WASH
S070;OFFICE  EN SUITE
S071;SECRETARY
S072;DIR. OFFICE
S073;COMMUNICATION OFFICE
S074;INTERNATIONAL OFFICE
S075;AUDIT OFFICE
S076;SECRETARY
S077;FINANCE OFFICE WITH ENSUITE
S078;WC & WASH
S079;FINANCE OFFICE
S080;FINANCE OFFICE
S081;SECRETARY
S082;ADMIN. OFFICE WITH ENSUITE
S083;WC & WASH
S084;DIR. OFFICE
S085;ADMIN. OFFICE 
S086;PRINT ROOM
S087;FEMALE WC
S088;WAITING AREA / CORRIDOR
S089;WASH
S090;ARCHIVES
S091;ARCHIVES
S092;PRINT ROOM
S093;MALE WC
S094;WAITING /CORRIDOR
S095;MEETING ROOM
S096;WAITING ROOM 
S097;MEETING ROOM
S098;OFFICE
S099;OFFICE
S100;WAITING AREA / CORRIDOR
S101;FEMALE WC
S102;MALE WC
S103;CORRIDOR
S104;LIFT LOBBY
S105;STAIR-06
S106;ELEC. ROOM
S107;IDF ROOM
S108;WC
S109;PANTRY
S110;WC
S111;MEDICAL CLUB (DINING)
S112;CORRIDOR
S113;CORRIDOR
S114;LIFT LOBBY
S115;STAIR-05
S116;WC
S117;WC
S118;VISITOR'S & PATIENTS WAITING LOUNGE (MAJLIS)
S119;GENERAL OFFICE
S120;WC
S121;STORE
S122;WC
S123;WAITING
S124;DRESSING
S125;WC
S126;RECEPTION & SECRETARY
S127;PRESIDENT OFFICE
S128;CORRIDOR
S129;STAFF WC
S130;STAFF WC
S131;OFFICE
S132;OFFICE
S133;OFFICE
S134;CORRIDOR
S135;STAFF REST & MEETING ROOM
S136;WC 1 & 2
S137;LOCKERS 1 & 2
S138;MEDICAL CLUB LOUNGE
S139;CORRIDOR
S140;EMERGENCY ESCAPE
S141;DIS. WC
S142;DIS. WC
S143;ELEC. ROOM
S144;IDF ROOM
S145 ;VISITOR'S WAITING LOUNGE
S146 ;BABY CHANGE
S147;DIS. WC
S148;PRAYER ROOM
S149;WASH + BOOT
S150;WASH
S151;WC
S152;WC
S153;STAIR-04
S154;CORRIDOR
S155;STORE
S156;E/S
S157;SINGLE BED ROOM
S158;SINGLE BED ROOM
S159;E/S
S160;E/S
S161;SINGLE BED ROOM
S162;SINGLE BED ROOM
S163;E/S
S164;E/S
S165;SINGLE BED ROOM
S166;SINGLE BED ROOM
S167;E/S
S168;E/S
S169;SINGLE BED ROOM
S170;STORE
S171;TREATMENT PREP.
S172;CLEAN UTILITY
S173;LINEN
S174;NURSE OFFICE
S175;SINGLE BED ROOM
S176;E/S
S177;E/S
S178;SINGLE BED ROOM
S179;DR OFFICE
S180;E/S
S181;SINGLE BED ROOM
S182;SINGLE BED ROOM
S183;E/S
S184;E/S
S185;SINGLE BED ROOM
S186;SINGLE BED ROOM
S187;E/S
S188;E/S
S188;SINGLE BED ROOM
S190;SINGLE BED ROOM
S191;E/S
S192;E/S
S193;SINGLE BED ROOM
S194;SINGLE BED ROOM
S195;E/S
S196;STAIR-07
S197;D/U
S198;PANTRY
S199;E/S
S200;SINGLE BED ROOM
S201;SINGLE BED ROOM
S202;E/S
S203;E/S
S204;SINGLE BED ROOM
S205;SINGLE BED ROOM
S206;E/S
S207;E/S
S208;SINGLE BED ROOM
S209;SINGLE BED ROOM
S210;E/S
S211;STORE
S212;CORRIDOR
S213;STORE
S214;E/S
S215;SINGLE BED ROOM
S216;SINGLE BED ROOM
S217;E/S
S218;E/S
S219;SINGLE BED ROOM
S220;MICU SINGLE BED ROOM
S221;E/S
S222;E/S
S223;MICU SINGLE BED ROOM
S224;MICU SINGLE BED ROOM
S225;E/S
S226;LINEN
S227;TREATMENT
S228;STORE
S229;D/U
S230;C/U
S231;E/S
S232;SINGLE BED ROOM
S233;SINGLE BED ROOM
S234;E/S
S235;E/S
S236;SINGLE BED ROOM
S237;SINGLE BED ROOM
S238;E/S
S239;NURSE OFFICE
S240;SINGLE BED ROOM
S241;E/S
S242;E/S
S243;SINGLE BED ROOM
S244;DR OFFICE
S245;E/S
S246;SINGLE BED ROOM
S247;SINGLE BED ROOM
S248;E/S
S249;E/S
S250;SINGLE BED ROOM
S251;SINGLE BED ROOM
S252;E/S
S253;STAIR-08
S255;E/S
S256;MICU SINGLE BED ROOM
S257;MICU SINGLE BED ROOM
S258;E/S
S259;E/S
S260;MICU SINGLE BED ROOM
S261;SINGLE BED ROOM
S262;E/S
S263;E/S
S264;SINGLE BED ROOM
S265;SINGLE BED ROOM
S266;E/S
S267;HK
S268;CORRIDOR
S269;E/S
S270;SINGLE BED ROOM
S271;SINGLE BED ROOM
S272;E/S
S273;E/S
S274;SINGLE BED ROOM
S275;SINGLE BED ROOM
S276;E/S
S277;STAIR-09
S278;D/U
S279;PANTRY
S280;E/S
S281;SINGLE BED ROOM
S282;SINGLE BED ROOM
S283;E/S
S284;E/S
S285;SINGLE BED ROOM
S286;SINGLE BED ROOM
S287;E/S
S288;E/S
S289;SINGLE BED ROOM
S290;SINGLE BED ROOM
S291;E/S
S292;STORE
S293;STORE
S294;E/S
S295;SINGLE BED ROOM
S296;SINGLE BED ROOM
S297;E/S
S298;E/S
S299;SINGLE BED ROOM
S300;SINGLE BED ROOM
S301;E/S
S302;E/S
S303;SINGLE BED ROOM
S304;SINGLE BED ROOM
S305;E/S
S306;E/S
S307;SINGLE BED ROOM
S308;STORE
S309;TREATMENT PREP.
S310;C/U
S311;LINEN
S312;NURSE OFFICE
S313;SINGLE BED ROOM
S314;E/S
S315;E/S
S316;SINGLE BED ROOM
S317;DR OFFICE
S318;E/S
S319;SINGLE BED ROOM
S320;SINGLE BED ROOM
S321;E/S
S322;E/S
S323;SINGLE BED ROOM
S324;SINGLE BED ROOM
S325;E/S
S326;CORRIDOR
S327;CORRIDOR
S328;CORRIDOR
S329;PLANT ROOM 2
S330;PLANT ROOM 1
S331;STAIR-02
S332;STAIR-03
S333;TERRACE
S334;VIP LIFT-01
S335;LIFT-02
S336;LIFT-03
S337;LIFT-04(GOOD'S LIFT)
S338;LIFT-05
S339;LIFT-06
S340;LIFT-07
S341;LIFT-08
S342;LIFT-09
S343;LIFT-10
S344;LIFT-11
S345;LIFT-12
S346;ELEC.
S347;ELEC.
S348;IDF ROOM
S349;ELEC.
S350;ELEC.
S351;ELEC.
S352;ELEC.
S353;ELEC.
S354;FEMALE WC
S355;MALE WC
S356;NURSE STATION
S357;NURSE STATION
S358;NURSE STATION
S359;SERVER ROOM
S360;CORRIDOR
S361;STAIR LOBBY
UB001;PUMP ROOM 
UB002;TREATED WATER TANK
UB003;RAW WATER TANK
UB004;FIRE WATER TANK
UB005;FIRE WATER TANK
UB006;SERVICES TUNNEL
UG001;HV SWITCH ROOM
UG001A;LYDEC ROOM
UG002;BATTERY ROOM
UG003;IPTV HEADEND 
UG004;IDF ROOM
UG005;LV SWITCH ROOM
UG006;TRANSFORMER ROOM
UG007;BOILERS ROOM
UG013;SWITCH GEAR"""
		
		for (String s:locloc.split("\n")) {
			def st = s.split(";")
			//println "########"+st.length
			//println "#####"+(i)+""+st[0]+"-"+st[1]+"-"+st[7]
			i++
			Localisation loc = Localisation.findByLibelleCourt(st[0])
			
			if (loc && (loc.libelle.equals(".") ||  equals("?"))) {
				println "########" + i + " : " + loc.libelleCourt + "--->" + loc.libelle+ "|||||" + st[1]
				loc.libelle = st[1]
				loc.save(flush:true)
			}
		}
		
		render ("done !")
	}
}