package robot2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import robot2.fsm.Robot2Coordinator;
import robot2.lwm2m.Robot;
import robot2.lwm2m.RobotInstance;

@SpringBootApplication
public class Robot2CoordinatorApplication {

	public static Robot robot2;
	public static void main(String[] args) {
		if(args.length==2){
			ConfigurationUtils.W1_COAP_SERVER = args[0];
			ConfigurationUtils.W2_COAP_SERVER = args[1];
			SpringApplication.run(Robot2CoordinatorApplication.class, args);
			Robot2Coordinator robot2controller = new Robot2Coordinator();


			robot2 = new Robot("Robot2", robot2controller, args);
			robot2.init();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			new Thread(RobotInstance.robot2Coordinator).start();
			//robot2controller.callAT3();
		}
		else{
			System.out.println("Please insert 2 parameters for w1server,w2server");
		}
	}
}

