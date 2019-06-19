package robot3.fsm;

import robot3.Robot3CpmsApplication;
import robot3.lwm2m.RobotInstance;
import uml4iot.GenericStateMachine.core.*;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import robot3.fsm.driver.R3Driver;
import robot3.fsm.signals.SubAssR3Completed;
import robot3.fsm.signals.W1Pos3Available;


public class Robot3Ctrl extends StateMachine{
    Robot3CtrlState robot3State;
    public static Logger LOGGER;
    //public static Robot1Simulator robot1Simulator;
  //  public static Robot1CtrlGui robot1ctrlgui;
    public static boolean pos3avail =false;
    public static R3Driver itsDriver;
   // public BlockingQueue<R1SMEvent> notificationQueue;
    public static BlockingQueue<SMReception> notificationQueue;
    State waiting4w1pos3,subAss3;
    public static Robot3Ctrl ctrl;
    public static SignalDetector signalDetector;

    public Robot3Ctrl(R3Driver r1Driver){
        super(null);
        notificationQueue = new ArrayBlockingQueue<SMReception>(10);
        itsDriver = r1Driver;
        waiting4w1pos3 = new Waiting4W1Pos3();
        subAss3 = new SubAss3();

        new waiting4W1Pos3_2_subAss3 (waiting4w1pos3,subAss3);
        new subAss3_2_waiting4W1Pos3 (subAss3,waiting4w1pos3);
        
        LOGGER = Logger.getLogger(Robot3Ctrl.class.getName() + " LOGGER");
        
        FileHandler fileHandler = null;
		try {
			fileHandler = new FileHandler("robot3.log", true);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}        
        LOGGER.addHandler(fileHandler);
        fileHandler.setFormatter(new Logger_Formatter());
        LOGGER.info("\n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n ");
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
            itsDriver.wait4pos3();
            SendAcquire2W1Pos3();
            robot3State = Robot3CtrlState.WAITING4W1POS3;
            LOGGER.warning("R3: Controller State = " + robot3State +"\n");
        }

        @Override
        protected void doActivity() {

        }

        @Override
        protected void exit() {

        }
    }

    public class SubAss3 extends State {

        @Override
        protected void entry() {
            robot3State = Robot3CtrlState.SUBASS3;
            LOGGER.warning("R3: Controller State = " + robot3State +"\n");
            itsDriver.doSubAss3();
            workOnW1();
        }

        @Override
        protected void doActivity() {

        }

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


    //---------------------Transitions----------------

    class waiting4W1Pos3_2_subAss3 extends Transition {


        public waiting4W1Pos3_2_subAss3(State fromState, State toState) {
            super(fromState, toState);
        }

        @Override
        protected boolean trigger(SMReception smReception) {
            Robot3Ctrl.LOGGER.warning("R3: Event Reception = " + smReception +"\n");
            return (smReception instanceof W1Pos3Available);
        }

        @Override
        protected void effect() {

        }
    }

    class subAss3_2_waiting4W1Pos3 extends Transition {


        public subAss3_2_waiting4W1Pos3(State fromState, State toState) {
            super(fromState, toState);
        }

        @Override
        protected boolean trigger(SMReception smReception) {
            Robot3Ctrl.LOGGER.warning("R3: Event Reception = " + smReception +"\n");
            return (smReception instanceof SubAssR3Completed);
        }

        @Override
        protected void effect() {

        }
    }


    private void SendAcquire2W1Pos3(){
        LOGGER.severe("Now Sending Acquire message to W1 for Pos3.."+"\n");
        try {
			RobotInstance.event = new JSONObject()
			        .put("event", "R3acquireW1")
			        .put("value", "").toString();

            Robot3CpmsApplication.robot3.robot2instance.fireResourcesChange(16);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    private void SendRelease2W1Pos3(){
        LOGGER.severe("Now Sending Release message to W1 for Pos3.."+"\n");
        try {
			RobotInstance.event = new JSONObject()
			        .put("event", "R3releaseW1")
			        .put("value", "").toString();

            Robot3CpmsApplication.robot3.robot2instance.fireResourcesChange(16);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
    }

    private void workOnW1(){
        LOGGER.severe("SubAss3 started.."+"\n");
        pos3avail =true;
     
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LOGGER.severe("SubAss3 completed.."+"\n");
        SignalDetector.msgQ.add(new SubAssR3Completed());
    }

}