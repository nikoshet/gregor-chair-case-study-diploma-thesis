package robot1.fsm;



import org.newsclub.net.unix.AFUNIXServerSocket;
import org.newsclub.net.unix.AFUNIXSocketAddress;
import robot1.ConfigurationUtils;
import robot1.Robot1CpmsApplication;
import robot1.lwm2m.RobotInstance;
import uml4iot.GenericStateMachine.core.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import robot1.fsm.driver.R1Driver;
import robot1.fsm.signals.*;


public class Robot1Ctrl extends StateMachine{
    Robot1CtrlState robot1State;
    public static Logger LOGGER;
    public static boolean pos1avail=false;
    public static boolean w2avail=false;
    public static R1Driver itsDriver = new R1Driver();
	public static BlockingQueue<SMReception> notificationQueue;
    State waiting4w1pos1,subass1,moving2pos2,waiting4w2,subassw2,moving2pos1;
    public static Robot1Ctrl ctrl;
    public static SignalDetector signalDetector;

    public Robot1Ctrl(){
        super(null);
        notificationQueue = new ArrayBlockingQueue<SMReception>(10);
        setItsDriver(itsDriver);
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
        
        LOGGER = Logger.getLogger(Robot1Ctrl.class.getName() + " LOGGER");
        LOGGER.setLevel(Level.WARNING);
        FileHandler fileHandler = null;
		try {
			fileHandler = new FileHandler("robot1.log", true);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}        
        LOGGER.addHandler(fileHandler);
        fileHandler.setFormatter(new Logger_Formatter());  
        LOGGER.info("\n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n ");
        LOGGER.setLevel(Level.ALL); // Request that every detail gets logged.
        LOGGER.info("Robot1 starting");

        setInitState(waiting4w1pos1);
        Robot1Ctrl.LOGGER.info("R1: Controller State = " + robot1State +"\n");
    }
    
    
    public BlockingQueue<SMReception> getEventQueue() {
        return itsMsgQ;
    }
    
    public static R1Driver getItsDriver() {
		return itsDriver;
	}


	public static void setItsDriver(R1Driver itsDriver) {
		Robot1Ctrl.itsDriver = itsDriver;
	}

    //-----------------States----------------

    public class Waiting4W1Pos1 extends State {

        @Override
        protected void entry() {
            SendAcquire2W1Pos1();
            robot1State = Robot1CtrlState.WAITING4W1POS1;
            Robot1Ctrl.LOGGER.severe("R1: Controller State = " + robot1State +"\n");
            itsDriver.wait4w1pos1();
           
        }

        @Override
        protected void doActivity() {

        }

        @Override
        protected void exit() {

        }
    }

    public class SubAss1 extends State {

        @Override
        protected void entry() {
            robot1State = Robot1CtrlState.SUBASS1;
            Robot1Ctrl.LOGGER.severe("R1: Controller State = " + robot1State +"\n");
            itsDriver.doSubAss1();
            doSubAss1();
        }

        @Override
        protected void doActivity() {

        }

        @Override
        protected void exit() {
            SendRelease2W1Pos1();
        }
    }

    public class Moving2Pos2 extends State {

        @Override
        protected void entry() {
            robot1State = Robot1CtrlState.MOVING2POS2;
            Robot1Ctrl.LOGGER.severe("R1: Controller State = " + robot1State +"\n");
            itsDriver.move2pos2();
        }

        @Override
        protected void doActivity() {

        }

        @Override
        protected void exit() {

        }
    }

    public class Waiting4W2 extends State {

        @Override
        protected void entry() {
            SendAcquire2W2();
            robot1State = Robot1CtrlState.WAITING4W2;
            Robot1Ctrl.LOGGER.severe("R1: Controller State = " + robot1State +"\n");
            itsDriver.wait4W2();
        }

        @Override
        protected void doActivity() {

        }

        @Override
        protected void exit() {

        }
    }

    public class SubAssW2 extends State {

        @Override
        protected void entry() {
            robot1State = Robot1CtrlState.SUBASSW2;
            Robot1Ctrl.LOGGER.severe("R1: Controller State = " + robot1State +"\n");
            itsDriver.doSubAssW2();
            doSubAssW2();
        }

        @Override
        protected void doActivity() {

        }

        @Override
        protected void exit() {
            SendRelease2W2();
        }
    }

    public class Moving2Pos1 extends State {

        @Override
        protected void entry() {
            robot1State = Robot1CtrlState.MOVING2POS1;
            Robot1Ctrl.LOGGER.severe("R1: Controller State = " + robot1State +"\n");
            itsDriver.move2pos1();
        }

        @Override
        protected void doActivity() {

        }

        @Override
        protected void exit() {

        }
    }


    //---------------------Transitions----------------

    class Waiting4W1POS1_2_SubAss1 extends Transition {


        public Waiting4W1POS1_2_SubAss1(State fromState, State toState) {
            super(fromState, toState);
        }

        @Override
        protected boolean trigger(SMReception smReception) {
            return (smReception instanceof Pos1AvailSignal);
        }

        @Override
        protected void effect() {

        }
    }

    class SubAss1_2_Moving2P2 extends Transition {


        public SubAss1_2_Moving2P2(State fromState, State toState) {
            super(fromState, toState);
        }

        @Override
        protected boolean trigger(SMReception smReception) {
            return (smReception instanceof SubAss1Completed);
        }

        @Override
        protected void effect() {

        }
    }

    class Moving2P2_2_Wating4W2 extends Transition {


        public Moving2P2_2_Wating4W2(State fromState, State toState) {
            super(fromState, toState);
        }

        @Override
        protected boolean trigger(SMReception smReception) {
        	  return (smReception instanceof Pos2Reached);
        }

        @Override
        protected void effect() {

        }
    }

    class Wating4W2_2_SubAssW2 extends Transition {


        public Wating4W2_2_SubAssW2(State fromState, State toState) {
            super(fromState, toState);
        }

        @Override
        protected boolean trigger(SMReception smReception) {
        	  return (smReception instanceof W2Available);
        }

        @Override
        protected void effect() {

        }
    }

    class SubAssW2_2_Moving2P1 extends Transition {


        public SubAssW2_2_Moving2P1(State fromState, State toState) {
            super(fromState, toState);
        }

        @Override
        protected boolean trigger(SMReception smReception) {
        	return (smReception instanceof SubAssW2Completed);
        }

        @Override
        protected void effect() {

        }
    }

    class Moving2P1_2_Waiting4W1POS1 extends Transition {


        public Moving2P1_2_Waiting4W1POS1(State fromState, State toState) {
            super(fromState, toState);
        }

        @Override
        protected boolean trigger(SMReception smReception) {
        	 return (smReception instanceof Pos1Reached);
        }

        @Override
        protected void effect() {

        }
    }

    private void SendAcquire2W1Pos1(){
        Robot1Ctrl.LOGGER.warning("Now Sending Acquire message to W1 for Pos1.."+"\n");
        //RobotInstance.event="R1acquire";
        try {
			RobotInstance.event = new JSONObject()
			        .put("event", "R1acquireW1")
			        .put("value", "").toString();

            Robot1CpmsApplication.robot1.robot1instance.fireResourcesChange(16);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      
    }

    private void SendRelease2W1Pos1(){
        Robot1Ctrl.LOGGER.warning("Now Sending Release message to W1 for Pos1.."+"\n");
       try {
		RobotInstance.event = new JSONObject()
		            .put("event", "R1releaseW1")
		            .put("value", "").toString();

           Robot1CpmsApplication.robot1.robot1instance.fireResourcesChange(16);

	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    }

    private void SendAcquire2W2(){
        Robot1Ctrl.LOGGER.warning("Now Sending Acquire message to W2.."+"\n");
       try {
		RobotInstance.event = new JSONObject()
		            .put("event", "R1acquireW2")
		            .put("value", "").toString();

           Robot1CpmsApplication.robot1.robot1instance.fireResourcesChange(16);

	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    }

    private void SendRelease2W2(){
        Robot1Ctrl.LOGGER.warning("Now Sending Acquire message to W2.."+"\n");
        try {
			RobotInstance.event = new JSONObject()
			        .put("event", "R1releaseW2")
			        .put("value", "").toString();

            Robot1CpmsApplication.robot1.robot1instance.fireResourcesChange(16);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    private void doSubAss1(){
        Robot1Ctrl.LOGGER.warning("SubAss1 started.."+"\n");
        pos1avail=true;
     //   RobotInstance.fireResourcesChange(1);
        /*try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        try (AFUNIXServerSocket server = AFUNIXServerSocket.newInstance()) {
            server.bind(new AFUNIXSocketAddress(ConfigurationUtils.socketFile));
            System.out.println("Robot1: " + server);

            //startWorkerTask1(server);

            while (!Thread.interrupted()) {
                Thread.sleep(1000);
                System.out.println("Waiting for connection...");
                String sendText = new JSONObject()
                        .put("WorkerTask1", "START")
                        .toString();
                try (Socket sock = server.accept()) {
                    System.out.println("Connected: " + sock);

                    try (InputStream is = sock.getInputStream(); //
                         OutputStream os = sock.getOutputStream()) {
                        System.out.println("Starting WorkerTask1: " + os);
                        os.write(sendText.getBytes("UTF-8"));
                        os.flush();

                        byte[] buf = new byte[128];
                        int read = is.read(buf);
                        System.out.println("WorkerTask1's response: " + new String(buf, 0, read, "UTF-8"));
                        while (!new String(buf,0,read).equals("")){}
                        if (new String(buf,0,read).equals(new JSONObject()
                                .put("WorkerTask1", "FINISHED")
                                .toString())){
                            System.out.println("WorkerTask1 finished.");
                            is.close();
                            os.close();
                            break;
                        }
                    }
                } catch (Exception e){
                    if (server.isClosed()) {
                        //throw e;
                        e.printStackTrace();
                    } else {
                        e.printStackTrace();
                    }
                }
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        /*try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/


        Robot1Ctrl.LOGGER.warning("SubAss1 completed.."+"\n");
        //notificationQueue.add(new SubAss1Completed());
        SignalDetector.msgQ.add(new SubAss1Completed());
    }

    private void doSubAssW2(){
        Robot1Ctrl.LOGGER.warning("SubAssW2 started.."+"\n");
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Robot1Ctrl.LOGGER.warning("SubAssW2 completed.."+"\n");
        SignalDetector.msgQ.add(new SubAssW2Completed());
        //	SignalDetector.msgQ.add(R1SMEvent.SUBASSW2COMPLETED);
    }

}