package workbench1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import workbench1.lwm2m.W1_LwM2mServer;


@SpringBootApplication(scanBasePackages={"workbench1.lwm2m"})
@EnableAutoConfiguration
public class Workbench1CpmsApplication implements CommandLineRunner{

	@Autowired
	W1_LwM2mServer w1_LwM2mServer;
	
	public static void main(String[] args) {
		SpringApplication.run(Workbench1CpmsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		w1_LwM2mServer.overallInit();
	}
}
