package robot1.lwm2m;

import robot1.fsm.Robot1Ctrl;

public class Robot1App {
	private static Robot robot1;

    public Robot1App(){
    	
    	Robot1Ctrl robot1controller = new Robot1Ctrl();
    		
    		
    	robot1 = new Robot("Robot1", robot1controller, null);
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
