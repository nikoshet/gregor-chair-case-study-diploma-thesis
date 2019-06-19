package robot1.fsm.driver;

import java.util.concurrent.ArrayBlockingQueue;

import org.json.JSONException;
import org.json.JSONObject;

import robot1.fsm.Robot1Ctrl;
import robot1.fsm.SignalDetector;
import robot1.fsm.signals.Pos1Reached;
import robot1.fsm.signals.Pos2Reached;
import robot1.lwm2m.RobotInstance;



public class R1Driver {

    protected ArrayBlockingQueue<Robot1Ctrl> itsCtrlEq;

    public void move2pos2() {
    	
    	
    	System.out.println("robot1 moving to pos2");
    	try {
			RobotInstance.event2sim =new JSONObject()
			        .put("event2sim", "R1_MOVE_2_POS2")
			        .put("value", "").toString();
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
    	  SignalDetector.msgQ.add(new Pos2Reached());
       // RobotInstance.fireResourcesChange(20);
    }

    public void move2pos1() {
    	
    	System.out.println("robot1 moving to pos1");
    	try {
			RobotInstance.event2sim =new JSONObject()
			        .put("event2sim", "R1_MOVE_2_POS1")
			        .put("value", "").toString();
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
    	  SignalDetector.msgQ.add(new Pos1Reached());
       // RobotInstance.fireResourcesChange(20);
    }

    public void wait4w1pos1() {
        
    	try {
			RobotInstance.event2sim =new JSONObject()
			        .put("event2sim", "R1_WAIT_4_POS1")
			        .put("value", "").toString();
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
       // RobotInstance.fireResourcesChange(20);
    }
    
    public void wait4W2() {
    	 
    	try {
			RobotInstance.event2sim =new JSONObject()
			        .put("event2sim", "R1_WAIT_4_POS1")
			        .put("value", "").toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
       // RobotInstance.fireResourcesChange(20); */
    }
    
    public void doSubAss1(){
    	try {
			RobotInstance.event2sim =new JSONObject()
			        .put("event2sim", "R1_WORK_ON_W1")
			        .put("value", "").toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
      // RobotInstance.fireResourcesChange(20); */
    }
    
    public void doSubAssW2() {
    	try {
			RobotInstance.event2sim =new JSONObject()
			        .put("event2sim", "R1_WORK_ON_W2")
			        .put("value", "").toString();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     //   RobotInstance.fireResourcesChange(20); */
    }


}