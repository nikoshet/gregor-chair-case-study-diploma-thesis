package workbench2.fsm;

import uml4iot.GenericStateMachine.core.*;
import workbench2.fsm.driver.W2Driver;
import workbench2.lwm2m.W2_LwM2mServer;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.concurrent.BlockingQueue;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.leshan.core.request.ExecuteRequest;
import org.eclipse.leshan.core.response.ExecuteResponse;
import org.eclipse.leshan.server.registration.Registration;
import org.springframework.stereotype.Controller;


@Controller
public class W2Ctrl extends StateMachine{
    public static W2CtrlState w2State;
    public static Logger LOGGER;
   // public static W2Simulator w2Simulator;
 //   public static W2CtrlGui W2ctrlgui;
    public static W2Driver itsDriver;
	static State free,subassR1_W2,subassR1_W2_compl, subassR2_W2;
    public static W2Ctrl ctrl;
    public String source,target,command;
    public static SignalDetector signalDetector;

    public W2Ctrl(){
        super(null);
        free = new Free();
        subassR1_W2 = new SubAssR1_W2();
        subassR1_W2_compl = new SubAssR1_W2_completed();
        subassR2_W2 = new SubAss_R2_W2();

        //Add deferred events
      //  free.addDeferredEvent(new R2ACQUIREW2());
        //free.addDeferredEvent(new R1ACQUIREW2());
        free.addDeferredEvent(W2SMEvent.R2ACQUIREW2);
        subassR1_W2.addDeferredEvent(W2SMEvent.R2ACQUIREW2);
        //subassR1_W2.addDeferredEvent(new R1ACQUIREW2());
        //subassR2_W2.addDeferredEvent(new R1ACQUIREW2());
        subassR1_W2_compl.addDeferredEvent(W2SMEvent.R1ACQUIREW2);
        //subassR1_W2_compl.addDeferredEvent(new R2ACQUIREW2());
        subassR2_W2.addDeferredEvent(W2SMEvent.R1ACQUIREW2);
        //

        new Free_2_SubAssR1W2(free,subassR1_W2);
        new SubAssR1W2_2_SubAssR1W2Compl(subassR1_W2,subassR1_W2_compl);
        new SubAssR1W2Compl_2_SubAssR2W2(subassR1_W2_compl, subassR2_W2);
        new SubAssR2W2_2_Free(subassR2_W2,free);

        LOGGER = Logger.getLogger(W2Ctrl.class.getName() + " LOGGER");

        FileHandler fileHandler = null;
		try {
			fileHandler = new FileHandler("workbench2.log", true);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        LOGGER.addHandler(fileHandler);
        fileHandler.setFormatter(new Logger_Formatter());

        LOGGER.setLevel(Level.ALL); // Request that every detail gets logged.
        LOGGER.info("\n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n ");
        LOGGER.info("WorkBench2 starting");

        initialize();
    }

    private void initialize(){
        setInitState(free);
        LOGGER.severe("W2: Controller State = " + w2State +"\n");

        // Initialize observations
        //W2Application.robot1port.observe(Robot1Port.Attribute.EVENT);
        //W2Application.robot2port.observe(Robot2Port.Attribute.EVENT);
    }

    public static W2Driver getItsDriver() {
		return itsDriver;
	}

	public static void setItsDriver(W2Driver itsDriver) {
		W2Ctrl.itsDriver = itsDriver;
	}
    
    public BlockingQueue<SMReception> getEventQueue() {
        return itsMsgQ;
    }

    //-----------------States----------------

    public class Free extends State {

        @Override
        protected void entry() {
            w2State = W2CtrlState.FREE;
            W2Ctrl.LOGGER.severe("W2: Controller State = " + w2State +"\n");
            //itsDriver.free(); //NullPointerException??????
            W2Driver.free();
        }

        @Override
        protected void doActivity() {

        }

        @Override
        protected void exit() {

        }
    }

    public class SubAssR1_W2 extends State {

        @Override
        protected void entry() {
            w2State = W2CtrlState.SUBASSR1_W2;
            W2Ctrl.LOGGER.severe("W2: Controller State = " + w2State +"\n");
            sendAvailableToR1();
            W2Driver.doingSubAssR1W2();
        }

        @Override
        protected void doActivity() {

        }

        @Override
        protected void exit(){

        }
    }

    public class SubAssR1_W2_completed extends State {

        @Override
        protected void entry() {
            w2State = W2CtrlState.SUBASSR1_W2_COMPLETED;
            W2Ctrl.LOGGER.severe("W2: Controller State = " + w2State +"\n");
            W2Driver.SubAssR1W2Completed();
        }

        @Override
        protected void doActivity() {

        }

        @Override
        protected void exit() {

        }
    }

    public class SubAss_R2_W2 extends State {

        @Override
        protected void entry() {
            w2State = W2CtrlState.SUBASSR2_W2;
            W2Ctrl.LOGGER.severe("W2: Controller State = " + w2State +"\n");
            sendAvailableToR2();
            W2Driver.doingSubAssR2W2();
        }

        @Override
        protected void doActivity() {

        }

        @Override
        protected void exit() {

        }
    }


    //---------------------Transitions----------------

    class Free_2_SubAssR1W2 extends Transition {


        public Free_2_SubAssR1W2(State fromState, State toState) {
            super(fromState, toState);
        }

        @Override
        protected boolean trigger(SMReception smReception) {
            W2Ctrl.LOGGER.warning("W2: Event Reception = " + smReception +"\n");
            return (smReception == W2SMEvent.R1ACQUIREW2 );
        }

        @Override
        protected void effect() {
            //sendAvailableToR1();
        }
    }

    class SubAssR1W2_2_SubAssR1W2Compl extends Transition {


        public SubAssR1W2_2_SubAssR1W2Compl(State fromState, State toState) {
            super(fromState, toState);
        }

        @Override
        protected boolean trigger(SMReception smReception) {
            W2Ctrl.LOGGER.warning("W2: Event Reception = " + smReception+"\n");
            return (smReception == W2SMEvent.R1RELEASEW2 );
        }

        @Override
        protected void effect() {

        }
    }

    class SubAssR1W2Compl_2_SubAssR2W2 extends Transition {


        public SubAssR1W2Compl_2_SubAssR2W2(State fromState, State toState) {
            super(fromState, toState);
        }

        @Override
        protected boolean trigger(SMReception smReception) {
            W2Ctrl.LOGGER.warning("W2: Event Reception = " + smReception +"\n");
            return (smReception == W2SMEvent.R2ACQUIREW2 );
        }

        @Override
        protected void effect() {
          //  sendAvailableToR2();
        }
    }

    class SubAssR2W2_2_Free extends Transition {


        public SubAssR2W2_2_Free(State fromState, State toState) {
            super(fromState, toState);
        }

        @Override
        protected boolean trigger(SMReception smReception) {
            W2Ctrl.LOGGER.warning("W2: Event Reception = " + smReception +"\n");
            return (smReception == W2SMEvent.R2RELEASEW2 );
        }

        @Override
        protected void effect() {

        }
    }


    private void sendAvailableToR1(){
        W2Ctrl.LOGGER.warning("Now Sending Available message to R1..");
       /* try {
            Robot1Port.execute(Robot1Port.Execute.W2AVAIL);
            W2Ctrl.LOGGER.warning("W2Available sended");
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }*/
        
    	ExecuteRequest request=new ExecuteRequest(20000,0,4,String.format("{ \"sender\": \"SERVER\", \"receiver\": \"ROBOT\", \"timestamp\": \"%s\"}", new Timestamp(System.currentTimeMillis())));//(Robot1Port.Execute.POS1AVAIL.getPath());
    	//new ExecuteRequest(5, 0, 2)
    	
    	try {
    		Registration registration = W2_LwM2mServer.lwServer.getRegistrationService().getByEndpoint("Robot1");
			ExecuteResponse response = W2_LwM2mServer.lwServer.send(registration, request);
			if(!response.isSuccess()) {
				System.out.println(response.getErrorMessage());
				System.out.println(response.getCode().toString());

			}
            W2Ctrl.LOGGER.warning("W2Available sended");

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    private void sendAvailableToR2(){
        W2Ctrl.LOGGER.warning("Now Sending Available message to R2..");
       /* try {
        	Robot2Port.execute(Robot2Port.Execute.W2AVAIL);
            W2Ctrl.LOGGER.warning("W2Available sended");
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }*/
        
        ExecuteRequest request=new ExecuteRequest(20001,0,4,String.format("{ \"sender\": \"SERVER\", \"receiver\": \"ROBOT\", \"timestamp\": \"%s\"}", new Timestamp(System.currentTimeMillis())));//(Robot1Port.Execute.POS1AVAIL.getPath());
    	//new ExecuteRequest(5, 0, 2)
    	
    	try {
    		Registration registration = W2_LwM2mServer.lwServer.getRegistrationService().getByEndpoint("Robot2");
			ExecuteResponse response = W2_LwM2mServer.lwServer.send(registration, request);
			if(!response.isSuccess()) {
				System.out.println(response.getErrorMessage());
				System.out.println(response.getCode().toString());

			}
            W2Ctrl.LOGGER.warning("W2Available sended");

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }



}