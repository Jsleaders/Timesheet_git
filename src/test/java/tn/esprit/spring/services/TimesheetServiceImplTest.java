package tn.esprit.spring.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Mission;
import tn.esprit.spring.entities.Timesheet;
import tn.esprit.spring.entities.TimesheetPK;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.MissionRepository;
import tn.esprit.spring.repository.TimesheetRepository;
import static org.assertj.core.api.Assertions.assertThat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase
public class TimesheetServiceImplTest {
	 @Autowired
	  private TestEntityManager entityManager;

	  @Autowired
	  TimesheetRepository timesheetrepository;
	  @Autowired
	  MissionRepository missionrepository;
	  @Autowired
	  DepartementRepository deprepository;
	  
	  
	  
	  @Test
	  public void should_ajouterMission() {
	    Mission mamission = missionrepository.save(new Mission("mamission", "c ma mission"));

	    assertThat(mamission).hasFieldOrPropertyWithValue("name", "mamission");
	    assertThat(mamission).hasFieldOrPropertyWithValue("description", "c ma mission");
	  }
	  
	  @Test
	  public void should_affecterMissionADepartement() {
	    Mission mission1 = new Mission("mission#1", "Desc#1");
	    entityManager.persist(mission1);
	    Departement dep = new Departement("namedep#2");
	    Mission updatedmission = new Mission("updated mission#1", "updated Desc#1",dep);

	    Mission mission = missionrepository.findById(mission1.getId()).get();
	    mission.setName(updatedmission.getName());
	    mission.setDescription(updatedmission.getDescription());
	    mission.setDepartement(updatedmission.getDepartement());
	    missionrepository.save(mission);

	    Mission checkMission = missionrepository.findById(mission1.getId()).get();
	    
	    assertThat(checkMission.getId()).isEqualTo(mission1.getId());
	    assertThat(checkMission.getName()).isEqualTo(updatedmission.getName());
	    assertThat(checkMission.getDescription()).isEqualTo(updatedmission.getDescription());
	    assertThat(checkMission.getDepartement()).isEqualTo(updatedmission.getDepartement());
	  }
	  @Test
	  public void should_ajouterTimesheet() throws ParseException {
		  
		  SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date dd = dateFormat.parse("2022-03-22");
			Date df = dateFormat.parse("2023-03-22");
			TimesheetPK timepk = new TimesheetPK(1,1,dd,df);
			Timesheet timesheet = new Timesheet();
			timesheet.setTimesheetPK(timepk);
			timesheet.setValide(false);
	    timesheetrepository.save(timesheet);
	    

	    assertThat(timesheet).hasFieldOrPropertyWithValue("timesheetPK", timepk);
	    assertThat(timesheet).hasFieldOrPropertyWithValue("isValide", false);
	  }
	  /*@Test
	  public void should_findAllMissionByEmployeJPQL() throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date dd1 = dateFormat.parse("2022-03-22");
			Date df1 = dateFormat.parse("2023-03-22");
			TimesheetPK timepk1 = new TimesheetPK(1,1,dd1,df1);
	    Timesheet timesheet1 = new Timesheet(timepk1, false);
	    entityManager.persist(timesheet1);

			Date dd2 = dateFormat.parse("2022-03-22");
			Date df2 = dateFormat.parse("2023-03-22");
			TimesheetPK timepk2 = new TimesheetPK(1,1,dd2,df2);
		Timesheet timesheet2 = new Timesheet(timepk2, false);
	    entityManager.persist(timesheet2);
	    
	    	Date dd3 = dateFormat.parse("2022-03-22");
	    	Date df3 = dateFormat.parse("2023-03-22");
			TimesheetPK timepk3 = new TimesheetPK(1,1,dd3,df3);
		Timesheet timesheet3 = new Timesheet(timepk3, false);
		entityManager.persist(timesheet3);

    	Date dd4 = dateFormat.parse("2022-03-22");
    	Date df4 = dateFormat.parse("2023-03-22");
		TimesheetPK timepk4 = new TimesheetPK(1,1,dd4,df4);
			Timesheet timesheet4 = new Timesheet(timepk4, false);
			entityManager.persist(timesheet4);

	    

	    List<Mission> tutorials = timesheetrepository.findAllMissionByEmployeJPQL(2);

	    //assertThat(tutorials).hasSize(2).contains(timesheet4);
	  }*/
}
