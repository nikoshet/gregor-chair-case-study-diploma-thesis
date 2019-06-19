package robot2.fsm.driver;

import java.util.concurrent.ArrayBlockingQueue;

import org.json.JSONException;
import org.json.JSONObject;

import robot2.fsm.Robot2Ctrl;
import robot2.fsm.SignalDetector;
import robot2.fsm.signals.Pos1Reached;
import robot2.fsm.signals.Pos2Reached;
import robot2.fsm.signals.SubAssW2Completed;
import robot2.lwm2m.RobotInstance;


public class R2Driver {

    protected ArrayBlockingQueue<Robot2Ctrl> itsCtrlEq;

    public void move2pos2() {
		try {
			RobotInstance.event2sim = new JSONObject()
					.put("event2sim", "R2_MOVE_2_POS2")
					.put("value", "").toString();
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		System.out.println("robot2 moving to pos2");
    	try {
			Thread.sleep(1000);
			SignalDetector.msgQ.add(new Pos2Reached());
			//Robot2Ctrl.notificationQueue.add(new Pos2Reached());

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }

    public void move2pos1() {
		try {
			RobotInstance.event2sim = new JSONObject()
					.put("event2sim", "R2_MOVE_2_POS1")
					.put("value", "").toString();
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

    	System.out.println("robot2 moving to pos1");
    	try {
			Thread.sleep(1000);
			//Robot2Ctrl.notificationQueue.add(new Pos1Reached());
			SignalDetector.msgQ.add(new Pos1Reached());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    public void wait4w1pos2() {
		try {
			RobotInstance.event2sim = new JSONObject()
					.put("event2sim", "R2_WAIT_4_POS2")
					.put("value", "").toString();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
    public void wait4W2() {
		try {
			RobotInstance.event2sim = new JSONObject()
					.put("event2sim", "R2_WAIT_4_W2")
					.put("value", "").toString();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
    }
    public void doSubAss2OnW1(){
		try {
			RobotInstance.event2sim = new JSONObject()
					.put("event2sim", "R2_WORK_ON_W1")
					.put("value", "").toString();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
    public void doSubAss2OnW2() {
		try {
			RobotInstance.event2sim = new JSONObject()
					.put("event2sim", "R2_WORK_ON_W2")
					.put("value", "").toString();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}
	public void completeSubAss2OnW1() {
		try {
			RobotInstance.event2sim = new JSONObject()
					.put("event2sim", "R2_WORK_ON_W1")
					.put("value", "").toString();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}