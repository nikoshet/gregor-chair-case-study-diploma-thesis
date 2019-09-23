package workbench1.fsm;

import java.sql.Timestamp;

import org.eclipse.leshan.core.request.ExecuteRequest;
import org.eclipse.leshan.core.response.ExecuteResponse;
import org.eclipse.leshan.server.registration.Registration;

import workbench1.events.Rbt_2_W1_Event;
import workbench1.lwm2m.W1_LwM2mServer;

import static workbench1.fsm.W1Coordinator.gpioconfig;


public enum P2StateMachine implements P2StateMachineIf {
    Free{
        public P2StateMachine definePosStates(Rbt_2_W1_Event ev, boolean tmt, P1StateMachine p1CurSt, P3StateMachine p3CurSt){

            W1Coordinator.LOGGER.info("Current State = Free \n");
            P2StateMachine p2TargSt = Free;
            if(tmt && (p1CurSt == P1StateMachine.SubAss1))
            {
                p2TargSt = Sub1Completed;
                W1Coordinator.LOGGER.info("After transition : p2CurSt == Sub1Completed \n");
                performActions(p2TargSt, ev);
                return p2TargSt;
            }
            else if( !tmt && ev != Rbt_2_W1_Event.R2AcquireP2){
                W1Coordinator.LOGGER.info("Error : p2CurSt == Free && ev != R2AcquireP2 \n");
                performActions(p2TargSt, ev);
                return p2TargSt;
            }else if(!tmt && ev == Rbt_2_W1_Event.R2AcquireP2){
                W1Coordinator.LOGGER.info("Event : R2AcuireP2 \n");
                W1Coordinator.LOGGER.info("No transition : p2CurSt = Free \n");

                performActions(p2TargSt, ev);
                return p2TargSt;
            }else{
                W1Coordinator.LOGGER.info("No transition : p2CurSt = Free \n");
                performActions(p2TargSt, ev);
                return p2TargSt;//????
            }
        }

        public void performActions(P2StateMachine targSt, Rbt_2_W1_Event ev){
            /****************** show P2 state via LEDs *********************/
            gpioconfig.W1working2_gpio.turnOffPin(gpioconfig.W1working2pin);
            gpioconfig.W1completed2_gpio.turnOffPin(gpioconfig.W1completed2pin);
            gpioconfig.W1pending2_gpio.turnOffPin(gpioconfig.W1pending2pin);
            gpioconfig.W1free2_gpio.turnOnPin(gpioconfig.W1free2pin);
            if(targSt == P2StateMachine.Free) {
                gpioconfig.W1working2_gpio.turnOffPin(gpioconfig.W1working2pin);
                gpioconfig.W1completed2_gpio.turnOffPin(gpioconfig.W1completed2pin);
                gpioconfig.W1pending2_gpio.turnOffPin(gpioconfig.W1pending2pin);
                gpioconfig.W1free2_gpio.turnOnPin(gpioconfig.W1free2pin);
            }
            else if(targSt == P2StateMachine.Sub1Completed) {
                gpioconfig.W1working2_gpio.turnOffPin(gpioconfig.W1working2pin);
                gpioconfig.W1completed2_gpio.turnOffPin(gpioconfig.W1completed2pin);
                gpioconfig.W1free2_gpio.turnOffPin(gpioconfig.W1free2pin);
                gpioconfig.W1pending2_gpio.turnOnPin(gpioconfig.W1pending2pin);
            }
            else{}
        }

        public boolean getTMTStatus(P1StateMachine p1CurSt, P3StateMachine p3CurSt){
            return false;
        }
    },
    Sub1Completed{

        public P2StateMachine definePosStates(Rbt_2_W1_Event ev, boolean tmt, P1StateMachine p1CurSt, P3StateMachine p3CurSt){
            W1Coordinator.LOGGER.info("Before transition : p2CurSt == Sub1Completed \n");
            P2StateMachine p2TargSt = Sub1Completed;
            if(ev == Rbt_2_W1_Event.R2AcquireP2)
            {
            	 p2TargSt = SubAss2_Part1;
                W1Coordinator.LOGGER.info("Event : R2AcquireP2 \n");
                W1Coordinator.LOGGER.info("After transition : p2CurSt == SubAss2_Part1 \n");
                performActions(p2TargSt, ev);
                System.out.println("fddfdf "+p2TargSt);
                return p2TargSt;
            }
            else{
                W1Coordinator.LOGGER.info("Error: p2CurSt == Sub1Completed && event != R2AcquireP2 \n");
                //System.out.println("Error: p2CurSt == Sub1Completed && event != R2AcquireP2");
                performActions(p2TargSt, ev);
                return p2TargSt;
            }
        }

        public void performActions(P2StateMachine targSt, Rbt_2_W1_Event ev){

            if(ev == Rbt_2_W1_Event.R2AcquireP2) {
                /********* send P2Available to R2 **********/
                W1Coordinator.LOGGER.info("P2Available\n");
                ExecuteRequest request=new ExecuteRequest(20001,0,3,String.format("{ \"sender\": \"SERVER\", \"receiver\": \"ROBOT\", \"timestamp\": \"%s\"}", new Timestamp(System.currentTimeMillis())));//(Robot1Port.Execute.POS1AVAIL.getPath());
            	try {
            		Registration registration = W1_LwM2mServer.lwServer.getRegistrationService().getByEndpoint("Robot2");
					ExecuteResponse response = W1_LwM2mServer.lwServer.send(registration, request);
					if(!response.isSuccess()) {
						System.out.println(response.getErrorMessage());
						System.out.println(response.getCode().toString());

					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
            /****************** show P2 state via LEDs *********************/
            gpioconfig.W1working2_gpio.turnOffPin(gpioconfig.W1working2pin);
            gpioconfig.W1completed2_gpio.turnOffPin(gpioconfig.W1completed2pin);
            gpioconfig.W1free2_gpio.turnOffPin(gpioconfig.W1free2pin);
            gpioconfig.W1pending2_gpio.turnOnPin(gpioconfig.W1pending2pin);
            if(targSt == P2StateMachine.SubAss2_Part1) {
                gpioconfig.W1free2_gpio.turnOffPin(gpioconfig.W1free2pin);
                gpioconfig.W1completed2_gpio.turnOffPin(gpioconfig.W1completed2pin);
                gpioconfig.W1pending2_gpio.turnOffPin(gpioconfig.W1pending2pin);
                gpioconfig.W1working2_gpio.turnOnPin(gpioconfig.W1working2pin);
            }
            else{}
        }

        public boolean getTMTStatus(P1StateMachine p1CurSt, P3StateMachine p3CurSt){
            return false;
        }
    },
    SubAss2_Part1{

        public P2StateMachine definePosStates(Rbt_2_W1_Event ev, boolean tmt, P1StateMachine p1CurSt, P3StateMachine p3CurSt){
            W1Coordinator.LOGGER.info("Before transition : p2CurSt == SubAss2_Part1 \n");
            System.out.println("into subass2_Part1.........................");

            P2StateMachine p2TargSt = SubAss2_Part1;
            if(ev == Rbt_2_W1_Event.R2ReleaseP2)
            {
                p2TargSt = SubAss2_Part1Completed;
                W1Coordinator.LOGGER.info("Event : R2ReleaseP2 \n");
                W1Coordinator.LOGGER.info("After transition : p2CurSt == SubAss2_Part1 \n");
                performActions(p2TargSt, ev);
                System.out.println("fddfdf "+p2TargSt);
                return p2TargSt;
            }
            else{
                W1Coordinator.LOGGER.info("Error: p2CurSt == SubAss2_Part1 && event != R2ReleaseP2 \n");
                performActions(p2TargSt, ev);
                return p2TargSt;
            }



        }

        public void performActions(P2StateMachine p2TargSt, Rbt_2_W1_Event event){
            /****************** show P2 state via LEDs *********************/
            gpioconfig.W1free2_gpio.turnOffPin(gpioconfig.W1free2pin);
            gpioconfig.W1completed2_gpio.turnOffPin(gpioconfig.W1completed2pin);
            gpioconfig.W1pending2_gpio.turnOffPin(gpioconfig.W1pending2pin);
            gpioconfig.W1working2_gpio.turnOnPin(gpioconfig.W1working2pin);
            if(p2TargSt == P2StateMachine.SubAss2_Part1Completed) {
                gpioconfig.W1working2_gpio.turnOffPin(gpioconfig.W1working2pin);
                gpioconfig.W1completed2_gpio.turnOffPin(gpioconfig.W1completed2pin);
                gpioconfig.W1free2_gpio.turnOffPin(gpioconfig.W1free2pin);
                gpioconfig.W1pending2_gpio.turnOnPin(gpioconfig.W1pending2pin);
            }
            else{}
        }

        public boolean getTMTStatus(P1StateMachine p1CurSt, P3StateMachine p3CurSt){return false;}
    },
    SubAss2_Part1Completed{
        public P2StateMachine definePosStates(Rbt_2_W1_Event ev, boolean tmt, P1StateMachine p1CurSt, P3StateMachine p3CurSt){
            W1Coordinator.LOGGER.info("Before transition : p2CurSt == SubAss2_Part1Completed \n");
            P2StateMachine p2TargSt = SubAss2_Part1Completed;
            if(ev == Rbt_2_W1_Event.R2AcquireP2)
            {
                p2TargSt = SubAss2_Part1Completed;
                W1Coordinator.LOGGER.info("Event : R2AcquireP2 \n");
                W1Coordinator.LOGGER.info("After transition : p2CurSt == SubAss2_Part2 \n");
                performActions(p2TargSt, ev);
                System.out.println("fddfdf "+p2TargSt);
                return p2TargSt;
            }
            else{
                W1Coordinator.LOGGER.info("Error: p2CurSt == SubAss2_Part1_Completed && event != R2AcquireP2 \n");
                //System.out.println("Error: p2CurSt == Sub1Completed && event != R2AcquireP2");
                performActions(p2TargSt, ev);
                return p2TargSt;
            }
        }

        public void performActions(P2StateMachine p2TargSt, Rbt_2_W1_Event event){
            if(event == Rbt_2_W1_Event.R2AcquireP2) {
                /********* send P2Available to R2 **********/
                W1Coordinator.LOGGER.info("P2Available\n");
                ExecuteRequest request=new ExecuteRequest(20001,0,3,String.format("{ \"sender\": \"SERVER\", \"receiver\": \"ROBOT\", \"timestamp\": \"%s\"}", new Timestamp(System.currentTimeMillis())));//(Robot1Port.Execute.POS1AVAIL.getPath());
                try {
                    Registration registration = W1_LwM2mServer.lwServer.getRegistrationService().getByEndpoint("Robot2");
                    ExecuteResponse response = W1_LwM2mServer.lwServer.send(registration, request);
                    if(!response.isSuccess()) {
                        System.out.println(response.getErrorMessage());
                        System.out.println(response.getCode().toString());

                    }
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            /****************** show P2 state via LEDs *********************/
            gpioconfig.W1working2_gpio.turnOffPin(gpioconfig.W1working2pin);
            gpioconfig.W1completed2_gpio.turnOffPin(gpioconfig.W1completed2pin);
            gpioconfig.W1free2_gpio.turnOffPin(gpioconfig.W1free2pin);
            gpioconfig.W1pending2_gpio.turnOnPin(gpioconfig.W1pending2pin);
            if(p2TargSt == P2StateMachine.SubAss2_Part2) {
                gpioconfig.W1free2_gpio.turnOffPin(gpioconfig.W1free2pin);
                gpioconfig.W1completed2_gpio.turnOffPin(gpioconfig.W1completed2pin);
                gpioconfig.W1pending2_gpio.turnOffPin(gpioconfig.W1pending2pin);
                gpioconfig.W1working2_gpio.turnOnPin(gpioconfig.W1working2pin);
            }
            else{}
        }

        public boolean getTMTStatus(P1StateMachine p1CurSt, P3StateMachine p3CurSt){
            return false;
        }
    },
    SubAss2_Part2{

        public P2StateMachine definePosStates(Rbt_2_W1_Event ev, boolean tmt, P1StateMachine p1CurSt, P3StateMachine p3CurSt){
            W1Coordinator.LOGGER.info("Before transition : p2CurSt == SubAss2_Part2 \n");
            System.out.println("into subass2_Part1.........................");

            P2StateMachine p2TargSt = SubAss2_Part2;
            if(ev == Rbt_2_W1_Event.R2ReleaseP2){
                W1Coordinator.LOGGER.info("Event : R2ReleaseP2 \n");
                //System.out.println("Event : R2ReleaseP2");
                if(((p1CurSt == P1StateMachine.Free) || (p1CurSt == P1StateMachine.SubAss1Completed)) && (p3CurSt == P3StateMachine.Free)){
                    //tmt = true;
                    if(p1CurSt == P1StateMachine.Free){
                        p2TargSt = Free;
                        W1Coordinator.LOGGER.info("After transition : p2CurSt == Free \n");
                        performActions(p2TargSt, ev);
                        return p2TargSt;
                    }
                    else{
                        p2TargSt = Sub1Completed;
                        W1Coordinator.LOGGER.info("After transition : p2CurSt == Sub1Completed \n");
                        performActions(p2TargSt, ev);
                        return p2TargSt;
                    }
                }else{
                    p2TargSt = SubAss2_Part2Completed;
                    W1Coordinator.LOGGER.info("After transition : p2CurSt == SubAss2_Part2Completed \n");
                    performActions(p2TargSt, ev);
                    return p2TargSt;
                }
            } else{
                W1Coordinator.LOGGER.info("Event :-------------------------------------------" + ev.toString());
                W1Coordinator.LOGGER.info("Error : p2CurSt == SubAss2 && ev != R2ReleaseP2 \n");
                performActions(p2TargSt, ev);
                return p2TargSt;
            }
        }

        public void performActions(P2StateMachine p2TargSt, Rbt_2_W1_Event event){
            /****************** show P2 state via LEDs *********************/
            gpioconfig.W1free2_gpio.turnOffPin(gpioconfig.W1free2pin);
            gpioconfig.W1completed2_gpio.turnOffPin(gpioconfig.W1completed2pin);
            gpioconfig.W1pending2_gpio.turnOffPin(gpioconfig.W1pending2pin);
            gpioconfig.W1working2_gpio.turnOnPin(gpioconfig.W1working2pin);
            if(p2TargSt == P2StateMachine.Free) {
                gpioconfig.W1working2_gpio.turnOffPin(gpioconfig.W1working2pin);
                gpioconfig.W1completed2_gpio.turnOffPin(gpioconfig.W1completed2pin);
                gpioconfig.W1pending2_gpio.turnOffPin(gpioconfig.W1pending2pin);
                gpioconfig.W1free2_gpio.turnOnPin(gpioconfig.W1free2pin);
            }
            else if(p2TargSt == P2StateMachine.Sub1Completed) {
                gpioconfig.W1working2_gpio.turnOffPin(gpioconfig.W1working2pin);
                gpioconfig.W1completed2_gpio.turnOffPin(gpioconfig.W1completed2pin);
                gpioconfig.W1free2_gpio.turnOffPin(gpioconfig.W1free2pin);
                gpioconfig.W1pending2_gpio.turnOnPin(gpioconfig.W1pending2pin);
            }
            else if(p2TargSt == P2StateMachine.SubAss2_Part2Completed) {
                gpioconfig.W1working2_gpio.turnOffPin(gpioconfig.W1working2pin);
                gpioconfig.W1free2_gpio.turnOffPin(gpioconfig.W1free2pin);
                gpioconfig.W1pending2_gpio.turnOffPin(gpioconfig.W1pending2pin);
                gpioconfig.W1completed2_gpio.turnOnPin(gpioconfig.W1completed2pin);
            }
            else{}
        }

        public boolean getTMTStatus(P1StateMachine p1CurSt, P3StateMachine p3CurSt){
            if(((p1CurSt == P1StateMachine.Free) || (p1CurSt == P1StateMachine.SubAss1Completed)) && (p3CurSt == P3StateMachine.Free))
                return true;
            else
                return false;
        }
    },
    SubAss2_Part2Completed{


        public P2StateMachine definePosStates(Rbt_2_W1_Event ev, boolean tmt, P1StateMachine p1CurSt, P3StateMachine p3CurStr){
            W1Coordinator.LOGGER.info("Before transition : p2CurSt == SubAss2_Part2Completed");

            P2StateMachine p2TargSt = SubAss2_Part2Completed;
            if(tmt){
                if(p1CurSt == P1StateMachine.Free){
                    p2TargSt = Free;
                    W1Coordinator.LOGGER.info("After transition : p2CurSt == Free \n");
                    performActions(p2TargSt, ev);
                    return p2TargSt;
                }
                else{
                    p2TargSt = Sub1Completed;
                    W1Coordinator.LOGGER.info("After transition : p2CurSt == Sub1Completed \n");
                    performActions(p2TargSt, ev);
                    return p2TargSt;
                }
            }else if(ev != Rbt_2_W1_Event.R2AcquireP2){
                W1Coordinator.LOGGER.info("Error : p2CurSt == SubAss2_Part2Completed && ev != R2AcquireP2\n");
                performActions(p2TargSt, ev);
                return p2TargSt;
            }else {
                W1Coordinator.LOGGER.info("Event : R2AcquireP2 \n");
                W1Coordinator.LOGGER.info("No transition : p2CurSt == SubAss2_Part2Completed\n");
                performActions(p2TargSt, ev);
                return p2TargSt;
            }
        }

        public void performActions(P2StateMachine p2TargSt, Rbt_2_W1_Event ev){
            if(ev == Rbt_2_W1_Event.R2AcquireP2) {
                /********* send P2Available to R2 **********/
                W1Coordinator.LOGGER.info("P2Available\n");
                ExecuteRequest request=new ExecuteRequest(20001,0,3,String.format("{ \"sender\": \"SERVER\", \"receiver\": \"ROBOT\", \"timestamp\": \"%s\"}", new Timestamp(System.currentTimeMillis())));//(Robot1Port.Execute.POS1AVAIL.getPath());
                try {
                    Registration registration = W1_LwM2mServer.lwServer.getRegistrationService().getByEndpoint("Robot2");
                    ExecuteResponse response = W1_LwM2mServer.lwServer.send(registration, request);
                    if(!response.isSuccess()) {
                        System.out.println(response.getErrorMessage());
                        System.out.println(response.getCode().toString());

                    }
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            /****************** show P2 state via LEDs *********************/
            gpioconfig.W1working2_gpio.turnOffPin(gpioconfig.W1working2pin);
            gpioconfig.W1free2_gpio.turnOffPin(gpioconfig.W1free2pin);
            gpioconfig.W1pending2_gpio.turnOffPin(gpioconfig.W1pending2pin);
            gpioconfig.W1completed2_gpio.turnOnPin(gpioconfig.W1completed2pin);
            if(p2TargSt == P2StateMachine.Free) {
                gpioconfig.W1working2_gpio.turnOffPin(gpioconfig.W1working2pin);
                gpioconfig.W1completed2_gpio.turnOffPin(gpioconfig.W1completed2pin);
                gpioconfig.W1pending2_gpio.turnOffPin(gpioconfig.W1pending2pin);
                gpioconfig.W1free2_gpio.turnOnPin(gpioconfig.W1free2pin);
                }
            else if(p2TargSt == P2StateMachine.Sub1Completed) {
                gpioconfig.W1working2_gpio.turnOffPin(gpioconfig.W1working2pin);
                gpioconfig.W1completed2_gpio.turnOffPin(gpioconfig.W1completed2pin);
                gpioconfig.W1free2_gpio.turnOffPin(gpioconfig.W1free2pin);
                gpioconfig.W1pending2_gpio.turnOnPin(gpioconfig.W1pending2pin);
                }
            else{}
         }

        public boolean getTMTStatus(P1StateMachine p1CurSt, P3StateMachine p3CurSt){
            return false;
        }
    }


}