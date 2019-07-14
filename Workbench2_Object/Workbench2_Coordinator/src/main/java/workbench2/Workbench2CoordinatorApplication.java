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

		if(args.length==1){
			ConfigurationUtils.W1_HOSTNAME = args[0];
			SpringApplication.run(Workbench2CoordinatorApplication.class, args);
		}
		else{
			System.out.println("Please insert 1 parameter for w1server");
		}

	}

	@Override
	public void run(String... args) throws Exception {
		
		w2_LwM2mServer.overallInit();
		
	}
}
