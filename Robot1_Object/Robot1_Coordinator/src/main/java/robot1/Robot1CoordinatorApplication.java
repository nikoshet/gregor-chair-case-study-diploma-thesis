package robot1;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import robot1.fsm.Robot1Coordinator;
import robot1.lwm2m.Robot;
import robot1.lwm2m.RobotInstance;

@SpringBootApplication
public class Robot1CoordinatorApplication {

	
	public static Robot robot1;
	public static void main(String[] args) {
		SpringApplication.run(Robot1CoordinatorApplication.class, args);
    	Robot1Coordinator robot1coordinator = new Robot1Coordinator();
    		

    	robot1 = new Robot("Robot1", robot1coordinator, args);
    	robot1.init();
    	try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	new Thread(RobotInstance.robot1Coordinator).start();

		//robot1coordinator.callAT1();
	}
}
