package robot1;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import robot1.fsm.Robot1Ctrl;
import robot1.fsm.driver.R1Driver;
import robot1.lwm2m.Robot;
import robot1.lwm2m.RobotInstance;

@SpringBootApplication
public class Robot1CpmsApplication {

	
	public static Robot robot1;
	public static void main(String[] args) {
		SpringApplication.run(Robot1CpmsApplication.class, args);
		R1Driver robot1driver = new R1Driver();
    	Robot1Ctrl robot1controller = new Robot1Ctrl();
    		
    		
    	robot1 = new Robot("Robot1", robot1controller, args);
    	robot1.init();
    	try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	new Thread(RobotInstance.robot1ctrl).start();
	}
}
