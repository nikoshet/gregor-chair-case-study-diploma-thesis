package robot3.fsm;

import robot3.ConfigurationUtils;
import robot3.Robot3CoordinatorApplication;
import robot3.lwm2m.RobotInstance;
import robot3.unixsocket.UnixClient;
import robot3.unixsocket.UnixServer;
import uml4iot.GenericStateMachine.core.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONException;
import org.json.JSONObject;
import robot3.fsm.signals.SubAssR3Completed;
import robot3.fsm.signals.W1Pos3Available;


public class Robot3Coordinator extends StateMachine{

    Robot3CoordinatorState robot3State;
    public static Logger LOGGER;
    public static boolean pos3avail =false;
    public static BlockingQueue<SMReception> notificationQueue;
    State waiting4w1pos3,subAss3;
    private UnixClient unixClient = new UnixClient();

    public Robot3Coordinator(){
        super(null);
        notificationQueue = new ArrayBlockingQueue<SMReception>(10);
        waiting4w1pos3 = new Waiting4W1Pos3();
        subAss3 = new SubAss3();

        new waiting4W1Pos3_2_subAss3 (waiting4w1pos3,subAss3);
        new subAss3_2_waiting4W1Pos3 (subAss3,waiting4w1pos3);

        LOGGER = Logger.getLogger(Robot3Coordinator.class.getName() + " LOGGER");
        LOGGER.info("\n ");
        LOGGER.setLevel(Level.ALL); // Request that every detail gets logged.
        LOGGER.info("Robot3 starting");
        setInitState(waiting4w1pos3);
        LOGGER.info("R3: Controller State = " + robot3State +"\n");
    }

    public BlockingQueue<SMReception> getEventQueue() {
        return itsMsgQ;
    }

    //-----------------States----------------

    public class Waiting4W1Pos3 extends State {

        @Override
        protected void entry() {
            SendAcquire2W1Pos3();
            robot3State = Robot3CoordinatorState.WAITING4W1POS3;
            LOGGER.warning("R3: Controller State = " + robot3State +"\n");
        }

        @Override
        protected void doActivity() { }

        @Override
        protected void exit() { }
    }

    public class SubAss3 extends State {

        @Override
        protected void entry() {
            robot3State = Robot3CoordinatorState.SUBASS3;
            LOGGER.warning("R3: Controller State = " + robot3State +"\n");
            workOnW1();
        }

        @Override
        protected void doActivity() { }

        @Override
        protected void exit() {
            SendRelease2W1Pos3();
            try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }

    //---------------------Transitions-----------------------------------------

    class waiting4W1Pos3_2_subAss3 extends Transition {

        public waiting4W1Pos3_2_subAss3(State fromState, State toState) {
            super(fromState, toState);
        }

        @Override
        protected boolean trigger(SMReception smReception) {
            Robot3Coordinator.LOGGER.warning("R3: Event Reception = " + smReception +"\n");
            return (smReception instanceof W1Pos3Available);
        }

        @Override
        protected void effect() { }
    }

    class subAss3_2_waiting4W1Pos3 extends Transition {

        public subAss3_2_waiting4W1Pos3(State fromState, State toState) {
            super(fromState, toState);
        }

        @Override
        protected boolean trigger(SMReception smReception) {
            Robot3Coordinator.LOGGER.warning("R3: Event Reception = " + smReception +"\n");
            return (smReception instanceof SubAssR3Completed);
        }

        @Override
        protected void effect() { }
    }

    //---------------------Functions----------------

    private void SendAcquire2W1Pos3(){
        LOGGER.severe("Now Sending Acquire message to W1 for Pos3.."+"\n");
        try {
			RobotInstance.event = new JSONObject()
			        .put("event", "R3acquireW1")
			        .put("value", "").toString();
            Robot3CoordinatorApplication.robot3.robot2instance.fireResourcesChange(16);
		} catch (JSONException e) {
			e.printStackTrace();
		}
    }

    private void SendRelease2W1Pos3(){
        LOGGER.severe("Now Sending Release message to W1 for Pos3.."+"\n");
        try {
			RobotInstance.event = new JSONObject()
			        .put("event", "R3releaseW1")
			        .put("value", "").toString();
            Robot3CoordinatorApplication.robot3.robot2instance.fireResourcesChange(16);
		} catch (JSONException e) {
			e.printStackTrace();
		}
    }

    private void workOnW1(){
        LOGGER.severe("SubAss3 started.."+"\n");
        pos3avail =true;

        callAT6();

        LOGGER.severe("SubAss3 completed.."+"\n");
        SignalDetector.msgQ.add(new SubAssR3Completed());
    }

    public void callAT7(){
        UnixServer server = new UnixServer();
        server.start();
        String sendText = new JSONObject()
                .put("AT7", "START")
                .put("sender","coordinator")
                .toString();
        unixClient.communicateWithAT(ConfigurationUtils.AT5SocketFile,sendText);
    }

    private void callAT6(){
        UnixServer server = new UnixServer();
        server.start();
        String sendText = new JSONObject()
                .put("AT6", "START")
                .put("sender","coordinator")
                .toString();
        unixClient.communicateWithAT(ConfigurationUtils.AT6SocketFile,sendText);
    }

}