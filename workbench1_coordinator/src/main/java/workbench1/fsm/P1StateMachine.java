package workbench1.fsm;


import java.sql.Timestamp;

import org.eclipse.leshan.core.request.ExecuteRequest;
import org.eclipse.leshan.core.response.ExecuteResponse;
import org.eclipse.leshan.server.registration.Registration;

import workbench1.events.Rbt_2_W1_Event;
import workbench1.lwm2m.W1_LwM2mServer;

//import static workbench1.fsm.W1Coordinator.gpioconfig;

public enum P1StateMachine implements P1StateMachineIf {
    Free{

        public P1StateMachine definePosStates(Rbt_2_W1_Event ev, boolean tmt, P2StateMachine p2CurSt, P3StateMachine p3CurSt){
            W1Coordinator.LOGGER.info("Before transision : p1CurSt == Free");

            P1StateMachine targSt  = Free;

            if( ev == Rbt_2_W1_Event.R1AcquireP1){
                W1Coordinator.LOGGER.info("Event : R1AcquireP1");
                targSt = SubAss1;
                W1Coordinator.LOGGER.info("After transition : p1CurSt == SubAss1");
                performActions(targSt, ev);
                return targSt;
            }

            System.out.println("No transition : p1CurSt == Free");
            return targSt;
        }

        public void performActions(P1StateMachine targSt, Rbt_2_W1_Event event){
            if(event == Rbt_2_W1_Event.R1AcquireP1) {
                /****************** send event P1Available to R1 *********************/
                W1Coordinator.LOGGER.info("P1Available");

                	//W1_LwM2mServer.robot1port.execute(Robot1Port.Execute.POS1AVAIL);
                	ExecuteRequest request=new ExecuteRequest(20000,0,3,String.format("{ \"sender\": \"SERVER\", \"receiver\": \"ROBOT\", \"timestamp\": \"%s\"}", new Timestamp(System.currentTimeMillis())));//(Robot1Port.Execute.POS1AVAIL.getPath());
                	
                	try {
                		Registration registration = W1_LwM2mServer.lwServer.getRegistrationService().getByEndpoint("Robot1");
						ExecuteResponse response = W1_LwM2mServer.lwServer.send(registration, request);
						if(!response.isSuccess()) {
							System.out.println(response.getErrorMessage());
							System.out.println(response.getCode().toString());
						}
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    				System.out.println("P1Available sended");

                /****************** show P1 state via LEDs *********************/
            /*    if(targSt == P1StateMachine.Free)
                {
                    gpioconfig.W1working1_gpio.turnOffPin(gpioconfig.W1working1pin);
                    gpioconfig.W1completed1_gpio.turnOffPin(gpioconfig.W1completed1pin);
                    gpioconfig.W1free1_gpio.turnOnPin(gpioconfig.W1free1pin);
                }
                else if(targSt == P1StateMachine.SubAss1)
                {
                    gpioconfig.W1free1_gpio.turnOffPin(gpioconfig.W1free1pin);
                    gpioconfig.W1completed1_gpio.turnOffPin(gpioconfig.W1completed1pin);
                    gpioconfig.W1working1_gpio.turnOnPin(gpioconfig.W1working1pin);
                }
                else{}*/
    	    }
        }

        public boolean getTMTStatus(P2StateMachine p2CurSt, P3StateMachine p3CurSt){
            return false;
        }
    },
    SubAss1{
        public P1StateMachine definePosStates(Rbt_2_W1_Event ev, boolean tmt, P2StateMachine p2CurSt, P3StateMachine p3CurSt){
            W1Coordinator.LOGGER.info("Before transition : p1CurSt == SubAss1");

            P1StateMachine targSt  = SubAss1;

            if(ev == Rbt_2_W1_Event.R1ReleaseP1){
                W1Coordinator.LOGGER.info("Event : R1ReleaseP1");
                if((p2CurSt == (P2StateMachine.Free) || p2CurSt == P2StateMachine.SubAss2_Part2Completed) && p3CurSt == P3StateMachine.Free){
                    targSt = Free;
                    W1Coordinator.LOGGER.info("After transition : p1CurSt == Free");
                    performActions(targSt, ev);
                    return targSt;
                }
                else
                {
                    targSt = SubAss1Completed;
                    W1Coordinator.LOGGER.info("After transition : p1CurSt == SubAss1Completed");
                    performActions(targSt, ev);
                    return targSt;
                }
            }else{
                W1Coordinator.LOGGER.info("Error :  p1CurSt == subAss1 kai m erthei event ap to R1");
                performActions(targSt, ev);
                return targSt;
            }
        }

        public void performActions(P1StateMachine targSt, Rbt_2_W1_Event event){
            /****************** show P1 state via LEDs *********************/
/*            gpioconfig.W1free1_gpio.turnOffPin(gpioconfig.W1free1pin);
            gpioconfig.W1completed1_gpio.turnOffPin(gpioconfig.W1completed1pin);
            gpioconfig.W1working1_gpio.turnOnPin(gpioconfig.W1working1pin);
            if(targSt == P1StateMachine.Free) {
                gpioconfig.W1working1_gpio.turnOffPin(gpioconfig.W1working1pin);
                gpioconfig.W1completed1_gpio.turnOffPin(gpioconfig.W1completed1pin);
                gpioconfig.W1free1_gpio.turnOnPin(gpioconfig.W1free1pin);
            }
            else if(targSt == P1StateMachine.SubAss1Completed) {
                gpioconfig.W1working1_gpio.turnOffPin(gpioconfig.W1working1pin);
                gpioconfig.W1free1_gpio.turnOffPin(gpioconfig.W1free1pin);
                gpioconfig.W1completed1_gpio.turnOnPin(gpioconfig.W1completed1pin);
            }
            else{}*/
        }

        public boolean getTMTStatus(P2StateMachine p2CurSt, P3StateMachine p3CurSt){
            if((p2CurSt == (P2StateMachine.Free) || p2CurSt == P2StateMachine.SubAss2_Part2Completed) && p3CurSt == P3StateMachine.Free)
                return true;
            else
                return false;
        }

    },
    SubAss1Completed{

        public P1StateMachine definePosStates(Rbt_2_W1_Event ev, boolean tmt, P2StateMachine p2CurSt, P3StateMachine p3CurSt){
            W1Coordinator.LOGGER.info("Before transition : p1CurSt == SubAss1Completed");

            P1StateMachine targSt = SubAss1Completed;
            if(tmt){
                targSt = Free;
                W1Coordinator.LOGGER.info("After transition : p1CurSt == Free");
                performActions(targSt, ev);
                return targSt;
            }else if(ev == Rbt_2_W1_Event.R1AcquireP1){
                W1Coordinator.LOGGER.info("Event : R1AcquireP1");
                W1Coordinator.LOGGER.info("No transition : p1CurSt == SubAss1Completed");
                performActions(targSt, ev);
                return targSt;
            }else{
                W1Coordinator.LOGGER.info("error: p1CurSt = SubAss1Completed and received a non R1acquireP1 event.");
                performActions(targSt, ev);
                return targSt;
            }
        }

        public void performActions(P1StateMachine targSt, Rbt_2_W1_Event event) {
            /****************** show P1 state via LEDs *********************/
      /*      gpioconfig.W1working1_gpio.turnOffPin(gpioconfig.W1working1pin);
            gpioconfig.W1free1_gpio.turnOffPin(gpioconfig.W1free1pin);
            gpioconfig.W1completed1_gpio.turnOnPin(gpioconfig.W1completed1pin);
            if (targSt == P1StateMachine.Free) {
                gpioconfig.W1working1_gpio.turnOffPin(gpioconfig.W1working1pin);
                gpioconfig.W1completed1_gpio.turnOffPin(gpioconfig.W1completed1pin);
                gpioconfig.W1free1_gpio.turnOnPin(gpioconfig.W1free1pin);
            }
            else {}*/
        }

        public boolean getTMTStatus(P2StateMachine p2CurSt, P3StateMachine p3CurSt){
            return false;
        }
    }

}
