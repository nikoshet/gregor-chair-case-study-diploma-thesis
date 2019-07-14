package robot1.fsm;

import robot1.ConfigurationUtils;
import robot1.Robot1CoordinatorApplication;
import robot1.lwm2m.RobotInstance;
import robot1.unixsocket.UnixClient;
import uml4iot.GenericStateMachine.core.*;
import robot1.unixsocket.UnixServer;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import robot1.fsm.signals.*;


public class Robot1Coordinator extends StateMachine{
    public static Robot1CoordinatorState robot1State;
    public static Logger LOGGER;
    public static boolean pos1avail=false;
    public static boolean w2avail=false;
	public static BlockingQueue<SMReception> notificationQueue = new ArrayBlockingQueue<SMReception>(10);
    private State waiting4w1pos1,subass1,moving2pos2,waiting4w2,subassw2,moving2pos1;
    private static UnixClient unixClient = new UnixClient();
    UnixServer server = new UnixServer();

    public Robot1Coordinator(){
        super(null);
        waiting4w1pos1 = new Waiting4W1Pos1();
        subass1 = new SubAss1();
        moving2pos2 = new Moving2Pos2();
        waiting4w2 = new Waiting4W2();
        subassw2 = new SubAssW2();
        moving2pos1 = new Moving2Pos1();

        new Waiting4W1POS1_2_SubAss1(waiting4w1pos1,subass1);
        new SubAss1_2_Moving2P2(subass1,moving2pos2);
        new Moving2P2_2_Wating4W2(moving2pos2,waiting4w2);
        new Wating4W2_2_SubAssW2(waiting4w2,subassw2);
        new SubAssW2_2_Moving2P1(subassw2,moving2pos1);
        new Moving2P1_2_Waiting4W1POS1(moving2pos1,waiting4w1pos1);
        
        LOGGER = Logger.getLogger(Robot1Coordinator.class.getName() + " LOGGER");
        LOGGER.setLevel(Level.WARNING);

        LOGGER.info("\n ");
        LOGGER.setLevel(Level.ALL); // Request that every detail gets logged.
        LOGGER.info("Robot1 starting");
        server.start();
        setInitState(waiting4w1pos1);
    }

    //-----------------States----------------

    private class Waiting4W1Pos1 extends State {

        @Override
        protected void entry() {
            SendAcquire2W1Pos1();
            robot1State = Robot1CoordinatorState.WAITING4W1POS1;
            Robot1Coordinator.LOGGER.severe("R1: Controller State = " + robot1State +"\n");
            wait4w1pos1();
        }
        @Override
        protected void doActivity() { }

        @Override
        protected void exit() { }
    }

    private class SubAss1 extends State {

        @Override
        protected void entry() {
            robot1State = Robot1CoordinatorState.SUBASS1;
            Robot1Coordinator.LOGGER.severe("R1: Controller State = " + robot1State +"\n");
            doSubAss1();
        }

        @Override
        protected void doActivity() { }

        @Override
        protected void exit() {
            SendRelease2W1Pos1();
        }
    }

    private class Moving2Pos2 extends State {

        @Override
        protected void entry() {
            robot1State = Robot1CoordinatorState.MOVING2POS2;
            Robot1Coordinator.LOGGER.severe("R1: Controller State = " + robot1State +"\n");
           // callMoveMs("pos2");
            SignalDetector.msgQ.add(new Pos2Reached());
        }

        @Override
        protected void doActivity() { }

        @Override
        protected void exit() { }
    }

    private class Waiting4W2 extends State {

        @Override
        protected void entry() {
            SendAcquire2W2();
            robot1State = Robot1CoordinatorState.WAITING4W2;
            Robot1Coordinator.LOGGER.severe("R1: Controller State = " + robot1State +"\n");
            wait4W2();
        }

        @Override
        protected void doActivity() { }

        @Override
        protected void exit() { }
    }

    private class SubAssW2 extends State {

        @Override
        protected void entry() {
            robot1State = Robot1CoordinatorState.SUBASSW2;
            Robot1Coordinator.LOGGER.severe("R1: Controller State = " + robot1State +"\n");
            doSubAssW2();
        }

        @Override
        protected void doActivity() { }

        @Override
        protected void exit() {
            SendRelease2W2();
        }
    }

    private class Moving2Pos1 extends State {

        @Override
        protected void entry() {
            robot1State = Robot1CoordinatorState.MOVING2POS1;
            Robot1Coordinator.LOGGER.severe("R1: Controller State = " + robot1State +"\n");
           // callMoveMs("pos1");
            SignalDetector.msgQ.add(new Pos1Reached());
        }

        @Override
        protected void doActivity() { }

        @Override
        protected void exit() { }
    }


    //---------------------Transitions----------------

    private class Waiting4W1POS1_2_SubAss1 extends Transition {


        private Waiting4W1POS1_2_SubAss1(State fromState, State toState) {
            super(fromState, toState);
        }

        @Override
        protected boolean trigger(SMReception smReception) {
            return (smReception instanceof Pos1AvailSignal);
        }

        @Override
        protected void effect() { }
    }

    private class SubAss1_2_Moving2P2 extends Transition {

        private SubAss1_2_Moving2P2(State fromState, State toState) {
            super(fromState, toState);
        }

        @Override
        protected boolean trigger(SMReception smReception) {
            return (smReception instanceof SubAss1Completed);
        }

        @Override
        protected void effect() { }
    }

    private class Moving2P2_2_Wating4W2 extends Transition {

        private Moving2P2_2_Wating4W2(State fromState, State toState) {
            super(fromState, toState);
        }

        @Override
        protected boolean trigger(SMReception smReception) {
        	  return (smReception instanceof Pos2Reached);
        }

        @Override
        protected void effect() { }
    }

    private class Wating4W2_2_SubAssW2 extends Transition {

        private Wating4W2_2_SubAssW2(State fromState, State toState) {
            super(fromState, toState);
        }

        @Override
        protected boolean trigger(SMReception smReception) {
        	  return (smReception instanceof W2Available);
        }

        @Override
        protected void effect() { }
    }

    private class SubAssW2_2_Moving2P1 extends Transition {

        private SubAssW2_2_Moving2P1(State fromState, State toState) {
            super(fromState, toState);
        }

        @Override
        protected boolean trigger(SMReception smReception) {
        	return (smReception instanceof SubAssW2Completed);
        }

        @Override
        protected void effect() { }
    }

    private class Moving2P1_2_Waiting4W1POS1 extends Transition {

        private Moving2P1_2_Waiting4W1POS1(State fromState, State toState) {
            super(fromState, toState);
        }

        @Override
        protected boolean trigger(SMReception smReception) {
        	 return (smReception instanceof Pos1Reached);
        }

        @Override
        protected void effect() { }
    }

    // ---------------------- functions -----------------------------------------------------

    private void SendAcquire2W1Pos1(){
        Robot1Coordinator.LOGGER.warning("Now Sending Acquire message to W1 for Pos1.."+"\n");
        try {
			RobotInstance.event = new JSONObject()
			        .put("event", "R1acquireW1")
			        .put("value", "").toString();

            Robot1CoordinatorApplication.robot1.robot1instance.fireResourcesChange(16);

		} catch (JSONException e) {
			e.printStackTrace();
		}
      
    }

    private void SendRelease2W1Pos1(){
        Robot1Coordinator.LOGGER.warning("Now Sending Release message to W1 for Pos1.."+"\n");
       try {
		RobotInstance.event = new JSONObject()
		            .put("event", "R1releaseW1")
		            .put("value", "").toString();

           Robot1CoordinatorApplication.robot1.robot1instance.fireResourcesChange(16);

	    } catch (JSONException e) {
		    e.printStackTrace();
	    }
    }

    private void SendAcquire2W2(){
        Robot1Coordinator.LOGGER.warning("Now Sending Acquire message to W2.."+"\n");
        try {
		RobotInstance.event = new JSONObject()
		            .put("event", "R1acquireW2")
		            .put("value", "").toString();

           Robot1CoordinatorApplication.robot1.robot1instance.fireResourcesChange(16);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void SendRelease2W2(){
        Robot1Coordinator.LOGGER.warning("Now Sending Acquire message to W2.."+"\n");
        try {
			RobotInstance.event = new JSONObject()
			        .put("event", "R1releaseW2")
			        .put("value", "").toString();

            Robot1CoordinatorApplication.robot1.robot1instance.fireResourcesChange(16);

		} catch (JSONException e) {
			e.printStackTrace();
		}
    }

    private void doSubAss1(){
        //call AT1
        Robot1Coordinator.LOGGER.warning("SubAss1 started.."+"\n");
        pos1avail=true;

        try {
            RobotInstance.event2sim =new JSONObject()
                    .put("event2sim", "R1_WORK_ON_W1")
                    .put("value", "").toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        callAT1();


    }

    private void doSubAssW2(){
        //call AT2
        Robot1Coordinator.LOGGER.warning("SubAssW2 started.."+"\n");

        try {
            RobotInstance.event2sim =new JSONObject()
                    .put("event2sim", "R1_WORK_ON_W2")
                    .put("value", "").toString();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        callAT2();

    }

    private void wait4w1pos1() {

        try {
            RobotInstance.event2sim =new JSONObject()
                    .put("event2sim", "R1_WAIT_4_POS1")
                    .put("value", "").toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        // RobotInstance.fireResourcesChange(20);
    }

    private void wait4W2() {

        try {
            RobotInstance.event2sim =new JSONObject()
                    .put("event2sim", "R1_WAIT_4_POS1")
                    .put("value", "").toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // RobotInstance.fireResourcesChange(20); */
    }

    public void callAT1(){
        /*UnixServer server = new UnixServer();
        server.start();*/
        String sendText = new JSONObject()
                .put("AT1", "START")
                .put("sender","coordinator")
                .toString();

       unixClient.communicateWithAT(ConfigurationUtils.AT1SocketFile,sendText);

    }

    private void callAT2(){
       /* UnixServer server = new UnixServer();
        server.start();*/
        String sendText = new JSONObject()
                .put("AT2", "START")
                .put("sender","coordinator")
                .toString();

        unixClient.communicateWithAT(ConfigurationUtils.AT2SocketFile,sendText);

    }

    private void callMoveMs(String toPosition){
       /* UnixServer server = new UnixServer();
        server.start();*/
        String sendText = new JSONObject()
                .put("MOVE_MS", "GOTO : " +toPosition)
                .put("sender","coordinator")
                .toString();

        unixClient.communicateWithAT(ConfigurationUtils.Robot1CtrlrSocketFile,sendText);
    }

}