package workbench1.fsm;


import java.sql.Timestamp;

import org.eclipse.leshan.core.request.ExecuteRequest;
import org.eclipse.leshan.core.response.ExecuteResponse;
import org.eclipse.leshan.server.registration.Registration;

import workbench1.events.Rbt_2_W1_Event;
import workbench1.lwm2m.W1_LwM2mServer;

import static workbench1.fsm.W1Coordinator.gpioconfig;


public enum P3StateMachine implements P3StateMachineIf {
    Free{
        public P3StateMachine definePosStates(Rbt_2_W1_Event ev, boolean tmt, P1StateMachine p1CurSt, P2StateMachine p2CurSt){
            W1Coordinator.LOGGER.info("Before transition : p3CurSt == Free \n ");
            P3StateMachine p3TargSt = Free;
            if(tmt && ((p2CurSt == P2StateMachine.SubAss2)||(p2CurSt == P2StateMachine.SubAss2Completed))){
                p3TargSt = Sub2Completed;
                W1Coordinator.LOGGER.info("After transition : p3CurSt == Sub2Completed \n ");
                performActions(p3TargSt, ev);
                return p3TargSt;
            }
            else if(!tmt && ev != Rbt_2_W1_Event.R3AcquireP3){
                W1Coordinator.LOGGER.info("Error : (p3CurSt == Free) && (tmt == false) && (ev != R3AcquireP3) \n  ");
                performActions(p3TargSt, ev);
                return p3TargSt;
            }else if(!tmt && ev == Rbt_2_W1_Event.R3AcquireP3){
                W1Coordinator.LOGGER.info("Event : R3AcquireP3 \n ");
                W1Coordinator.LOGGER.info("No transition : p3CurSt == Free \n ");
                performActions(p3TargSt, ev);
                return p3TargSt;
            }else{
                W1Coordinator.LOGGER.info("No transition : p3CurSt == Free \n ");
                performActions(p3TargSt, ev);
                return p3TargSt;
            }
        }

        public void performActions(P3StateMachine p3TargSt, Rbt_2_W1_Event ev){
            /****************** show P3 state via LEDs *********************/
            gpioconfig.W1working3_gpio.turnOffPin(gpioconfig.W1working3pin);
            gpioconfig.W1pending3_gpio.turnOffPin(gpioconfig.W1pending3pin);
            gpioconfig.W1free3_gpio.turnOnPin(gpioconfig.W1free3pin);
            if(p3TargSt == P3StateMachine.Sub2Completed) {
                gpioconfig.W1working3_gpio.turnOffPin(gpioconfig.W1working3pin);
                gpioconfig.W1free3_gpio.turnOffPin(gpioconfig.W1free3pin);
                gpioconfig.W1pending3_gpio.turnOnPin(gpioconfig.W1pending3pin);
            }
            else{}
        }

        public boolean getTMTStatus(P1StateMachine p1CurSt, P2StateMachine p2CurSt){
            return false;
        }
    },
    Sub2Completed{
        public P3StateMachine definePosStates(Rbt_2_W1_Event ev, boolean tmt, P1StateMachine p1CurSt, P2StateMachine p2CurSt){
            W1Coordinator.LOGGER.info("Before transition : p3CurSt == Sub2Completed \n ");
            P3StateMachine p3TargSt = Sub2Completed;
            if(ev == Rbt_2_W1_Event.R3AcquireP3)
            {
                W1Coordinator.LOGGER.info("Event : R3AcquireP3 \n ");
                p3TargSt = SubAss3;
                performActions(p3TargSt, ev);
                W1Coordinator.LOGGER.info("After transition : p3CurSt == SubAss3 \n ");
                return p3TargSt;
            }else {
                W1Coordinator.LOGGER.info("Error : (p3CurSt == Sub2Completed) && ev != R3AcquireP3 \n ");
                performActions(p3TargSt, ev);
                return p3TargSt;
            }
        }

        public void performActions(P3StateMachine p3TargSt, Rbt_2_W1_Event ev){

        	ExecuteRequest request=new ExecuteRequest(20002,0,3,String.format("{ \"sender\": \"SERVER\", \"receiver\": \"ROBOT\", \"timestamp\": \"%s\"}", new Timestamp(System.currentTimeMillis())));//(Robot1Port.Execute.POS1AVAIL.getPath());
        	
        	try {
        		Registration registration = W1_LwM2mServer.lwServer.getRegistrationService().getByEndpoint("Robot3");
				ExecuteResponse response = W1_LwM2mServer.lwServer.send(registration, request);
				if(!response.isSuccess()) {
					System.out.println(response.getErrorMessage());
					System.out.println(response.getCode().toString());

				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            /****************** show P3 state via LEDs *********************/
            gpioconfig.W1working3_gpio.turnOffPin(gpioconfig.W1working3pin);
            gpioconfig.W1free3_gpio.turnOffPin(gpioconfig.W1free3pin);
            gpioconfig.W1pending3_gpio.turnOnPin(gpioconfig.W1pending3pin);
            if(p3TargSt == P3StateMachine.SubAss3) {
                gpioconfig.W1free3_gpio.turnOffPin(gpioconfig.W1free3pin);
                gpioconfig.W1pending3_gpio.turnOffPin(gpioconfig.W1pending3pin);
                gpioconfig.W1working3_gpio.turnOnPin(gpioconfig.W1working3pin);
                }
            else{}
        }

        public boolean getTMTStatus(P1StateMachine p1CurSt, P2StateMachine p2CurSt){
            return false;
        }
    },
    SubAss3{
        public P3StateMachine definePosStates(Rbt_2_W1_Event ev, boolean tmt, P1StateMachine p1CurSt, P2StateMachine p2CurSt){
            W1Coordinator.LOGGER.info("Before transition : p3CurSt == SubAss3 \n");
            P3StateMachine p3TargSt = SubAss3;
            if(ev == Rbt_2_W1_Event.R3ReleaseP3){
                W1Coordinator.LOGGER.info("Event : R3ReleaseP3 \n");
                if(((p1CurSt == P1StateMachine.Free) || (p1CurSt == P1StateMachine.SubAss1Completed))&& (p2CurSt == P2StateMachine.SubAss2Completed)){
                    p3TargSt = Sub2Completed;
                    W1Coordinator.LOGGER.info("After transition : p3CurSt == Sub2Completed \n");
                    performActions(p3TargSt, ev);
                    return p3TargSt;
                }else {
                    p3TargSt = Free;
                    W1Coordinator.LOGGER.info("After transition : p3CurSt == Free \n");
                    performActions(p3TargSt, ev);
                    return p3TargSt;
                }
            }else {
                W1Coordinator.LOGGER.info("Error : (p3CurSt == SubAss3) && (ev != R3ReleaseP3) \n");
                performActions(p3TargSt, ev);
                return p3TargSt;
            }
        }

        public void performActions(P3StateMachine p3TargSt, Rbt_2_W1_Event ev){
            /****************** show P3 state via LEDs *********************/
            gpioconfig.W1free3_gpio.turnOffPin(gpioconfig.W1free3pin);
            gpioconfig.W1pending3_gpio.turnOffPin(gpioconfig.W1pending3pin);
            gpioconfig.W1working3_gpio.turnOnPin(gpioconfig.W1working3pin);
            if(p3TargSt == P3StateMachine.Sub2Completed) {
                gpioconfig.W1working3_gpio.turnOffPin(gpioconfig.W1working3pin);
                gpioconfig.W1free3_gpio.turnOffPin(gpioconfig.W1free3pin);
                gpioconfig.W1pending3_gpio.turnOnPin(gpioconfig.W1pending3pin);
                }
            if(p3TargSt == P3StateMachine.Free) {
                gpioconfig.W1working3_gpio.turnOffPin(gpioconfig.W1working3pin);
                gpioconfig.W1pending3_gpio.turnOffPin(gpioconfig.W1pending3pin);
                gpioconfig.W1free3_gpio.turnOnPin(gpioconfig.W1free3pin);
                }
            else{}
        }

        public boolean getTMTStatus(P1StateMachine p1CurSt, P2StateMachine p2CurSt){
            if(((p1CurSt == P1StateMachine.Free) || (p1CurSt == P1StateMachine.SubAss1Completed))&& (p2CurSt == P2StateMachine.SubAss2Completed))
                return true;
            else
                return false;
        }
    }
}