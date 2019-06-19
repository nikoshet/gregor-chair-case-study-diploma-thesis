package robot2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import robot2.fsm.Robot2Ctrl;
import robot2.fsm.driver.R2Driver;
import robot2.lwm2m.Robot;
import robot2.lwm2m.RobotInstance;

@SpringBootApplication
public class Robot2CpmsApplication {

	public static Robot robot2;
	public static void main(String[] args) {
		SpringApplication.run(Robot2CpmsApplication.class, args);
		R2Driver robot2driver = new R2Driver();
    	Robot2Ctrl robot2controller = new Robot2Ctrl(robot2driver);
    		
    		
    	robot2 = new Robot("Robot2", robot2controller, args);
    	robot2.init();
    	try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	new Thread(RobotInstance.robot2Ctrl).start();
	}
}
