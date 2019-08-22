package robot3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import robot3.fsm.Robot3Coordinator;
import robot3.lwm2m.Robot;
import robot3.lwm2m.RobotInstance;

@SpringBootApplication
public class Robot3CoordinatorApplication {

	public static Robot robot3;
	public static void main(String[] args) {
		if(args.length==1){
			ConfigurationUtils.W1_COAP_SERVER = args[0];
			SpringApplication.run(Robot3CoordinatorApplication.class, args);
			Robot3Coordinator robot3controller = new Robot3Coordinator();


			robot3 = new Robot("Robot3", robot3controller, args);
			robot3.init();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			new Thread(RobotInstance.robot3Ctrl).start();
			/*try {
				robot3controller.workOnW1();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}*/
		}
		else{
			System.out.println("Please insert 1 parameter for w1server");
		}
	}
}
