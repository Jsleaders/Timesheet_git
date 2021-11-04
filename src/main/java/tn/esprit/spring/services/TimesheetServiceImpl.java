package tn.esprit.spring.services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Mission;
import tn.esprit.spring.entities.Role;
import tn.esprit.spring.entities.Timesheet;
import tn.esprit.spring.entities.TimesheetPK;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EmployeRepository;
import tn.esprit.spring.repository.MissionRepository;
import tn.esprit.spring.repository.TimesheetRepository;

@Service
public class TimesheetServiceImpl implements ITimesheetService {
	


	private static final Logger logger = Logger.getLogger(TimesheetServiceImpl.class);

	@Autowired
	MissionRepository missionRepository;
	@Autowired
	DepartementRepository deptRepoistory;
	@Autowired
	TimesheetRepository timesheetRepository;
	@Autowired
	EmployeRepository employeRepository;

	public int ajouterMission(Mission mission) {
		
		

		missionRepository.save(mission);
		logger.info("Mission added (Mission ID "+ mission.getId() +")");

		return mission.getId();
	}
    
	public void affecterMissionADepartement(int missionId, int depId) {
		
		Mission mission = missionRepository.findById(missionId).get();
		Departement dep = deptRepoistory.findById(depId).get();
		mission.setDepartement(dep);
		
		missionRepository.save(mission);
		logger.info("Mission got affected successfully to the "+dep.getName()+" departement | (departement ID "+ dep.getId() +")");
		
	}

	public Timesheet ajouterTimesheet(int missionId, int employeId, Date dateDebut, Date dateFin) {
		TimesheetPK timesheetPK = new TimesheetPK();
		timesheetPK.setDateDebut(dateDebut);
		timesheetPK.setDateFin(dateFin);
		timesheetPK.setIdEmploye(employeId);
		timesheetPK.setIdMission(missionId);
		
		Timesheet timesheet = new Timesheet();
		timesheet.setTimesheetPK(timesheetPK);
		timesheet.setValide(false); //par defaut non valide
		timesheetRepository.save(timesheet);
		logger.info("Timesheet waiting for validation and added to employe ID "+ employeId +" | Mission ID "+ missionId +" | Due date : "+dateFin);
		return timesheet;
	
	}

	
	public void validerTimesheet(int missionId, int employeId, Date dateDebut, Date dateFin, int validateurId) {
		TimesheetPK timesheetPK = new TimesheetPK(missionId, employeId, dateDebut, dateFin);
		Timesheet timesheet =timesheetRepository.findBytimesheetPK(timesheetPK);
		logger.info("trying to validating timesheet |  ID" +timesheetPK);
		Employe validateur = employeRepository.findById(validateurId).get();
		Mission mission = missionRepository.findById(missionId).get();
		//verifier s'il est un chef de departement (interet des enum)
		if(!validateur.getRole().equals(Role.CHEF_DEPARTEMENT)){
			logger.warn("l'employe doit etre chef de departement pour valider une feuille de temps !");
			return;
		}
		logger.info("verification du role de validateur terminée avec succées ");
		logger.info("verifions s'il est le chef de departement de la mission en question ");
		
		boolean chefDeLaMission = false;
		for(Departement dep : validateur.getDepartements()){
			if(dep.getId() == mission.getDepartement().getId()){
				chefDeLaMission = true;
				logger.info("verification terminée avec succées ");
				
				break;
			}
		}
		if(!chefDeLaMission){
			logger.error("l'employe doit etre chef de departement de la mission en question");
			return;
		}
//
		timesheet.setValide(true);
		logger.info("updated timesheet valid status to true. ");
		//Comment Lire une date de la base de données
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		logger.info("dateDebut : " + dateFormat.format(timesheet.getTimesheetPK().getDateDebut()));
		
	}
	public List<Mission> findAllMissionByEmployeJPQL(int employeId) {		
		/*List<Mission> AllMissionByEmploye =timesheetRepository.findAllMissionByEmployeJPQL(employeId);*/
		logger.info("fetched all mission by the given employee ID | "+ employeId +"successfully" );
		return timesheetRepository.findAllMissionByEmployeJPQL(employeId);
	}
	public List<Employe> getAllEmployeByMission(int missionId) {
		logger.info("fetched all employees by the given Mission ID | "+ missionId +"successfully" );
		return timesheetRepository.getAllEmployeByMission(missionId);
	}

}
