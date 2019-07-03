package workbench2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import workbench2.lwm2m.W2_LwM2mServer;


@SpringBootApplication(scanBasePackages={"workbench2.lwm2m" , "workbench2.fsm"})
public class Workbench2CoordinatorApplication implements CommandLineRunner {

	@Autowired
	W2_LwM2mServer w2_LwM2mServer;
	
	
	public static void main(String[] args) {
		SpringApplication.run(Workbench2CoordinatorApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		w2_LwM2mServer.overallInit();
		
	}
}
