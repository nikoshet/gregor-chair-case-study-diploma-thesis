package workbench2.fsm;

import uml4iot.GenericStateMachine.core.*;
import workbench2.lwm2m.W2_LwM2mServer;
import java.sql.Timestamp;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.leshan.core.request.ExecuteRequest;
import org.eclipse.leshan.core.response.ExecuteResponse;
import org.eclipse.leshan.server.registration.Registration;
import org.springframework.stereotype.Controller;


@Controller
public class W2Coordinator extends StateMachine{
    public static W2CoordinatorState w2State;
    public static Logger LOGGER;
	static State free,subassR1_W2,subassR1_W2_compl, subassR2_W2;

    public W2Coordinator(){
        super(null);
        free = new Free();
        subassR1_W2 = new SubAssR1_W2();
        subassR1_W2_compl = new SubAssR1_W2_completed();
        subassR2_W2 = new SubAss_R2_W2();

        //Add deferred events
        free.addDeferredEvent(W2SMEvent.R2ACQUIREW2);
        subassR1_W2.addDeferredEvent(W2SMEvent.R2ACQUIREW2);
        subassR1_W2_compl.addDeferredEvent(W2SMEvent.R1ACQUIREW2);
        subassR2_W2.addDeferredEvent(W2SMEvent.R1ACQUIREW2);
        //

        new Free_2_SubAssR1W2(free,subassR1_W2);
        new SubAssR1W2_2_SubAssR1W2Compl(subassR1_W2,subassR1_W2_compl);
        new SubAssR1W2Compl_2_SubAssR2W2(subassR1_W2_compl, subassR2_W2);
        new SubAssR2W2_2_Free(subassR2_W2,free);

        LOGGER = Logger.getLogger(W2Coordinator.class.getName() + " LOGGER");

        LOGGER.setLevel(Level.ALL); // Request that every detail gets logged.
        LOGGER.info("\n  ");
        LOGGER.info("WorkBench2 starting");

        initialize();
    }

    private void initialize(){
        setInitState(free);
        LOGGER.severe("W2: Controller State = " + w2State +"\n");
    }

    public BlockingQueue<SMReception> getEventQueue() {
        return itsMsgQ;
    }

    //-----------------States----------------

    public class Free extends State {

        @Override
        protected void entry() {
            w2State = W2CoordinatorState.FREE;
            W2Coordinator.LOGGER.severe("W2: Controller State = " + w2State +"\n");
        }

        @Override
        protected void doActivity() { }

        @Override
        protected void exit() { }
    }

    public class SubAssR1_W2 extends State {

        @Override
        protected void entry() {
            w2State = W2CoordinatorState.SUBASSR1_W2;
            W2Coordinator.LOGGER.severe("W2: Controller State = " + w2State +"\n");
            sendAvailableToR1();
        }

        @Override
        protected void doActivity() { }

        @Override
        protected void exit(){ }
    }

    public class SubAssR1_W2_completed extends State {

        @Override
        protected void entry() {
            w2State = W2CoordinatorState.SUBASSR1_W2_COMPLETED;
            W2Coordinator.LOGGER.severe("W2: Controller State = " + w2State +"\n");
        }

        @Override
        protected void doActivity() { }

        @Override
        protected void exit() { }
    }

    public class SubAss_R2_W2 extends State {

        @Override
        protected void entry() {
            w2State = W2CoordinatorState.SUBASSR2_W2;
            W2Coordinator.LOGGER.severe("W2: Controller State = " + w2State +"\n");
            sendAvailableToR2();
        }

        @Override
        protected void doActivity() { }

        @Override
        protected void exit() { }
    }


    //---------------------Transitions----------------

    class Free_2_SubAssR1W2 extends Transition {


        public Free_2_SubAssR1W2(State fromState, State toState) {
            super(fromState, toState);
        }

        @Override
        protected boolean trigger(SMReception smReception) {
            W2Coordinator.LOGGER.warning("W2: Event Reception = " + smReception +"\n");
            return (smReception == W2SMEvent.R1ACQUIREW2 );
        }

        @Override
        protected void effect() { }
    }

    class SubAssR1W2_2_SubAssR1W2Compl extends Transition {


        public SubAssR1W2_2_SubAssR1W2Compl(State fromState, State toState) {
            super(fromState, toState);
        }

        @Override
        protected boolean trigger(SMReception smReception) {
            W2Coordinator.LOGGER.warning("W2: Event Reception = " + smReception+"\n");
            return (smReception == W2SMEvent.R1RELEASEW2 );
        }

        @Override
        protected void effect() {  }
    }

    class SubAssR1W2Compl_2_SubAssR2W2 extends Transition {


        public SubAssR1W2Compl_2_SubAssR2W2(State fromState, State toState) {
            super(fromState, toState);
        }

        @Override
        protected boolean trigger(SMReception smReception) {
            W2Coordinator.LOGGER.warning("W2: Event Reception = " + smReception + "\n");
            return (smReception == W2SMEvent.R2ACQUIREW2);
        }

        @Override
        protected void effect() { }
    }
    class SubAssR2W2_2_Free extends Transition {


        public SubAssR2W2_2_Free(State fromState, State toState) {
            super(fromState, toState);
        }

        @Override
        protected boolean trigger(SMReception smReception) {
            W2Coordinator.LOGGER.warning("W2: Event Reception = " + smReception +"\n");
            return (smReception == W2SMEvent.R2RELEASEW2 );
        }

        @Override
        protected void effect() { }
    }


    private void sendAvailableToR1(){
        W2Coordinator.LOGGER.warning("Now Sending Available message to R1..");
       /* try {
            Robot1Port.execute(Robot1Port.Execute.W2AVAIL);
            W2Coordinator.LOGGER.warning("W2Available sended");
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
            W2Coordinator.LOGGER.warning("W2Available sended");

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    private void sendAvailableToR2(){
        W2Coordinator.LOGGER.warning("Now Sending Available message to R2..");
       /* try {
        	Robot2Port.execute(Robot2Port.Execute.W2AVAIL);
            W2Coordinator.LOGGER.warning("W2Available sended");
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
            W2Coordinator.LOGGER.warning("W2Available sended");

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }



}