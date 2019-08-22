package workbench1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import workbench1.lwm2m.W1_LwM2mServer;


@SpringBootApplication(scanBasePackages={"workbench1.lwm2m"})
@EnableAutoConfiguration
public class Workbench1CoordinatorApplication{

	//@Autowired
	//W1_LwM2mServer w1_LwM2mServer;
	
	public static void main(String[] args) {


		if(args.length==1){
			W1_LwM2mServer w1_LwM2mServer = new W1_LwM2mServer();
			ConfigurationUtils.W1_HOSTNAME = args[0];
			SpringApplication.run(Workbench1CoordinatorApplication.class, args);
			w1_LwM2mServer.overallInit();
		}
		else{
			System.out.println("Please insert 1 parameter for w1server");
		}


	}
/*
	@Override
	public void run(String... args) throws Exception {
	}*/
}
