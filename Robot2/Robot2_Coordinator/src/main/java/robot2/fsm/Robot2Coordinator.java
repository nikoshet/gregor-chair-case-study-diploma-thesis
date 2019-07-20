package robot2.fsm;
import robot2.ConfigurationUtils;
import robot2.Robot2CoordinatorApplication;
import robot2.unixsocket.UnixClient;
import robot2.unixsocket.UnixServer;
import uml4iot.GenericStateMachine.core.*;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import robot2.fsm.signals.Pos1Reached;
import robot2.fsm.signals.Pos2Reached;
import robot2.fsm.signals.SubAss2_1Completed;
import robot2.fsm.signals.SubAss2_2Completed;
import robot2.fsm.signals.SubAssW2Completed;
import robot2.fsm.signals.W1Pos2Available;
import robot2.fsm.signals.W2Available;
import robot2.lwm2m.RobotInstance;


public class Robot2Coordinator extends StateMachine{
    Robot2CoordinatorState robot2State;
    public static Logger LOGGER;
    public static boolean pos2avail=false;
    public static BlockingQueue<SMReception> notificationQueue;
    State waiting4w1pos2,subass2,moving2pos2,waiting4w2,subassw2,moving2pos1,completingSubAss2;
    private static UnixClient unixClient = new UnixClient();
    UnixServer server = new UnixServer();
    public Robot2Coordinator(){
        super(null);
        notificationQueue = new ArrayBlockingQueue<SMReception>(10);
        waiting4w1pos2 = new Waiting4W1Pos2();
        subass2 = new SubAss2();
        moving2pos2 = new Moving2Pos2();
        waiting4w2 = new Waiting4W2();
        subassw2 = new SubAssW2();
        moving2pos1 = new Moving2Pos1();
        completingSubAss2 = new CompletingSubAss2();

        new Waiting4W1POS2_2_SubAss2(waiting4w1pos2,subass2);
        new SubAss2_2_Moving2P2(subass2,moving2pos2);
        new Moving2P2_2_Wating4W2(moving2pos2,waiting4w2);
        new Wating4W2_2_SubAssW2(waiting4w2 ,subassw2);
        new SubAssW2_2_Moving2P1(subassw2,moving2pos1);
        new Moving2P1_2_completingSubAss2(moving2pos1,completingSubAss2);
        new CompletingSubAss2_2_Waiting4W1POS2(completingSubAss2 , waiting4w1pos2);

        LOGGER = Logger.getLogger(Robot2Coordinator.class.getName() + " LOGGER");
        LOGGER.info("\n");
        LOGGER.setLevel(Level.WARNING); // Request that every detail gets logged.
        LOGGER.info("Robot2 starting");
        server.start();
        setInitState(waiting4w1pos2);
        Robot2Coordinator.LOGGER.severe("R2: Controller State = " + robot2State +"\n");
    }

    //-----------------States----------------

    public class Waiting4W1Pos2 extends State {

        @Override
        protected void entry() {
            SendAcquire2W1Pos2();
            robot2State = Robot2CoordinatorState.WAITING_4_W1POS2;
            Robot2Coordinator.LOGGER.severe("R2: Controller State = " + robot2State +"\n");
        }

        @Override
        protected void doActivity() { }

        @Override
        protected void exit() { }
    }

    public class SubAss2 extends State {

        @Override
        protected void entry() {
            robot2State = Robot2CoordinatorState.SUBASS2;
            Robot2Coordinator.LOGGER.severe("R2: Controller State = " + robot2State +"\n");
            doSubAss2OnW1();
        }

        @Override
        protected void doActivity() { }

        @Override
        protected void exit() { }
    }

    public class Moving2Pos2 extends State {

        @Override
        protected void entry() {
            robot2State = Robot2CoordinatorState.MOVING_2_POS2;
            Robot2Coordinator.LOGGER.severe("R2: Controller State = " + robot2State +"\n");
            callMoveMs("left");
            //SignalDetector.msgQ.add(new Pos2Reached());
        }

        @Override
        protected void doActivity() { }

        @Override
        protected void exit() { }
    }

    public class Waiting4W2 extends State {

        @Override
        protected void entry() {
            SendAcquire2W2();
            robot2State = Robot2CoordinatorState.WAITING_4_W2;
            Robot2Coordinator.LOGGER.severe("R2: Controller State = " + robot2State +"\n");
        }

        @Override
        protected void doActivity() { }

        @Override
        protected void exit() { }
    }

    public class SubAssW2 extends State {

        @Override
        protected void entry() {
            robot2State = Robot2CoordinatorState.SUBASSW2;
            Robot2Coordinator.LOGGER.severe("R2: Controller State = " + robot2State +"\n");
            doSubAss2OnW2();
        }

        @Override
        protected void doActivity() { }

        @Override
        protected void exit() {
            SendRelease2W2();
            try {
                                Thread.sleep(200);
                        } catch (InterruptedException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                        }

        }
    }

    public class Moving2Pos1 extends State {

        @Override
        protected void entry() {
            robot2State = Robot2CoordinatorState.MOVING_2_POS1;
            Robot2Coordinator.LOGGER.severe("R2: Controller State = " + robot2State +"\n");
            callMoveMs("right");
            //SignalDetector.msgQ.add(new Pos1Reached());
        }

        @Override
        protected void doActivity() { }

        @Override
        protected void exit() { }
    }

    private class CompletingSubAss2 extends State {

        @Override
        protected void entry() {
            robot2State = Robot2CoordinatorState.COMPLETINGSUBASS2;
            Robot2Coordinator.LOGGER.severe("R2: Controller State = " + robot2State +"\n");
            completeSubAss2OnW1();
        }

        @Override
        protected void doActivity() { }

        @Override
        protected void exit() {
            SendRelease2W1Pos2();
            try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }


    //---------------------Transitions----------------

    class Waiting4W1POS2_2_SubAss2 extends Transition {


        public Waiting4W1POS2_2_SubAss2(State fromState, State toState) {
            super(fromState, toState);
        }

        @Override
        protected boolean trigger(SMReception smReception) {
            Robot2Coordinator.LOGGER.warning("R2: Event Reception = " + smReception +"\n");
            return (smReception instanceof W1Pos2Available);
        }

        @Override
        protected void effect() { }
    }

    class SubAss2_2_Moving2P2  extends Transition {


        public SubAss2_2_Moving2P2(State fromState, State toState) {
            super(fromState, toState);
        }

        @Override
        protected boolean trigger(SMReception smReception) {
            Robot2Coordinator.LOGGER.warning("R2: Event Reception = " + smReception +"\n");
            return (smReception instanceof SubAss2_1Completed);
        }

        @Override
        protected void effect() { }
    }

    class Moving2P2_2_Wating4W2 extends Transition {


        public Moving2P2_2_Wating4W2(State fromState, State toState) {
            super(fromState, toState);
        }

        @Override
        protected boolean trigger(SMReception smReception) {
            Robot2Coordinator.LOGGER.warning("R2: Event Reception = " + smReception +"\n");
            return (smReception instanceof Pos2Reached);
        }

        @Override
        protected void effect() { }
    }

    class Wating4W2_2_SubAssW2 extends Transition {


        public Wating4W2_2_SubAssW2(State fromState, State toState) {
            super(fromState, toState);
        }

        @Override
        protected boolean trigger(SMReception smReception) {
            Robot2Coordinator.LOGGER.warning("R2: Event Reception = " + smReception +"\n");
            return (smReception instanceof W2Available);
        }

        @Override
        protected void effect() { }
    }

    class SubAssW2_2_Moving2P1 extends Transition {


        public SubAssW2_2_Moving2P1(State fromState, State toState) {
            super(fromState, toState);
        }

        @Override
        protected boolean trigger(SMReception smReception) {
            Robot2Coordinator.LOGGER.warning("R2: Event Reception = " + smReception +"\n");
        	return (smReception instanceof SubAssW2Completed);
        }

        @Override
        protected void effect() { }
    }

    class Moving2P1_2_completingSubAss2 extends Transition {


        public Moving2P1_2_completingSubAss2(State fromState, State toState) {
            super(fromState, toState);
        }

        @Override
        protected boolean trigger(SMReception smReception) {
            Robot2Coordinator.LOGGER.warning("R2: Event Reception = " + smReception +"\n");
        	 return (smReception instanceof Pos1Reached);
        }

        @Override
        protected void effect() { }
    }

    private class CompletingSubAss2_2_Waiting4W1POS2 extends Transition {

        public CompletingSubAss2_2_Waiting4W1POS2(State fromState, State toState) {
            super(fromState, toState);
        }

        @Override
        protected boolean trigger(SMReception smReception) {
            Robot2Coordinator.LOGGER.warning("R2: Event Reception = " + smReception +"\n");
            return (smReception instanceof SubAss2_2Completed);
        }

        @Override
        protected void effect() { }
    }

    //--------------------- functions -------------------------------------------

    private void SendAcquire2W1Pos2(){
        Robot2Coordinator.LOGGER.warning("Now Sending Acquire message to W1 for Pos2.."+"\n");
        try {
			RobotInstance.event = new JSONObject()
			        .put("event", "R2acquireW1")
			        .put("value", "").toString();

            Robot2CoordinatorApplication.robot2.robot2instance.fireResourcesChange(16);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    private void SendRelease2W1Pos2(){
        Robot2Coordinator.LOGGER.warning("Now Sending Release message to W1 for Pos2.."+"\n");
        try {
			RobotInstance.event = new JSONObject()
			        .put("event", "R2releaseW1")
			        .put("value", "").toString();

            Robot2CoordinatorApplication.robot2.robot2instance.fireResourcesChange(16);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    private void SendAcquire2W2(){
        Robot2Coordinator.LOGGER.warning("Now Sending Acquire message to W2.."+"\n");
        try {
			RobotInstance.event = new JSONObject()
			        .put("event", "R2acquireW2")
			        .put("value", "").toString();

            Robot2CoordinatorApplication.robot2.robot2instance.fireResourcesChange(16);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     
    }

    private void SendRelease2W2(){
        Robot2Coordinator.LOGGER.warning("Now Sending Acquire message to W2.."+"\n");
        try {
			RobotInstance.event = new JSONObject()
			        .put("event", "R2releaseW2")
			        .put("value", "").toString();

            Robot2CoordinatorApplication.robot2.robot2instance.fireResourcesChange(16);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    
    }

    private void doSubAss2OnW1(){
        Robot2Coordinator.LOGGER.warning("SubAss2OnW1 started.."+"\n");
        pos2avail=true;
        //RobotInstance.fireResourcesChange(0);

        callAT3();


    }

    private void doSubAss2OnW2(){
        Robot2Coordinator.LOGGER.warning("SubAss2OnW2 started.."+"\n");

        callAT4();


    }

    private void completeSubAss2OnW1(){
        Robot2Coordinator.LOGGER.warning("CompletingSubAss2OnW1 started.."+"\n");

        callAT5();

    }

    public void callAT3(){
        String sendText = new JSONObject()
                .put("AT3", "START")
                .put("sender","coordinator")
                .toString();
        unixClient.communicateWithAT(ConfigurationUtils.AT3SocketFile,sendText);
    }

    private void callAT4(){
        String sendText = new JSONObject()
                .put("AT4", "START")
                .put("sender","coordinator")
                .toString();
        unixClient.communicateWithAT(ConfigurationUtils.AT4SocketFile,sendText);
    }

    private void callAT5(){
        String sendText = new JSONObject()
                .put("AT5", "START")
                .put("sender","coordinator")
                .toString();
        unixClient.communicateWithAT(ConfigurationUtils.AT5SocketFile,sendText);
    }


    private void callMoveMs(String toPosition){
        String sendText = new JSONObject()
                .put("Move", toPosition)
                .put("sender","/tmp/robot2coordinator.sock")
                .toString();

        unixClient.communicateWithAT(ConfigurationUtils.Robot1CtrlrSocketFile,sendText);
    }

}


