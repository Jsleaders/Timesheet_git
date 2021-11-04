package tn.esprit.spring;

import org.apache.log4j.PropertyConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.nio.file.Paths;
@SpringBootApplication
public class TimesheetSpringBootCoreDataJpaMvcRest1Application {
	
	public static void main(String[] args) {
		SpringApplication.run(TimesheetSpringBootCoreDataJpaMvcRest1Application.class, args);
		PropertyConfigurator.configure(Paths.get(".").toAbsolutePath().normalize().toString()+"\\src\\main\\resources\\application.properties");

	}

}
