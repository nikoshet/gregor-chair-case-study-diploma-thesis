package workbench1.fsm;

import java.sql.Timestamp;

import org.eclipse.leshan.core.request.ExecuteRequest;
import org.eclipse.leshan.core.response.ExecuteResponse;
import org.eclipse.leshan.server.registration.Registration;

import workbench1.events.Rbt_2_W1_Event;
import workbench1.lwm2m.W1_LwM2mServer;


public enum P2StateMachine implements P2StateMachineIf {
    Free{
        public P2StateMachine definePosStates(Rbt_2_W1_Event ev, boolean tmt, P1StateMachine p1CurSt, P3StateMachine p3CurSt){

            W1Controller.LOGGER.info("Current State = Free \n");
            //System.out.println("Before transition : p2CurSt == Free");

            P2StateMachine p2TargSt = Free;
            if(tmt && (p1CurSt == P1StateMachine.SubAss1))
            {
                p2TargSt = Sub1Completed;
                W1Controller.LOGGER.info("After transition : p2CurSt == Sub1Completed \n");
                //System.out.println("After transition : p2CurSt == Sub1Completed");
                //Driver : change pos2 state
               // W1Simulator.p2.setBackground(Color.YELLOW);
                performActions(p2TargSt, ev);
                return p2TargSt;
            }//edw uparxei periptwsh kai na exw deferred event
            else if( !tmt && ev != Rbt_2_W1_Event.R2AcquireP2){
                W1Controller.LOGGER.info("Error : p2CurSt == Free && ev != R2AcquireP2 \n");
                //System.out.println("Error : p2CurSt == Free && ev != R2AcquireP2");
                performActions(p2TargSt, ev);
                return p2TargSt;
            }else if(!tmt && ev == Rbt_2_W1_Event.R2AcquireP2){
                W1Controller.LOGGER.info("Event : R2AcuireP2 \n");
                //System.out.println("Event : R2AcquireP2");
                W1Controller.LOGGER.info("No transition : p2CurSt = Free \n");

                //System.out.println("No transition : p2CurSt == Free");
                performActions(p2TargSt, ev);
                return p2TargSt;
            }else{
                W1Controller.LOGGER.info("No transition : p2CurSt = Free \n");
                //System.out.println("No transition : p2CurSt == Free");
                performActions(p2TargSt, ev);
                return p2TargSt;//????
            }
        }

        public void performActions(P2StateMachine targSt, Rbt_2_W1_Event ev){
            /************************** send targState to Simulator  ************************************/
         /*   MessageRequest.Source source = MessageRequest.Source.W1_POS2;
            MessageRequest.Target target = MessageRequest.Target.SIMULATOR ;
            MessageRequest.Command command = MessageRequest.Command.W1_POS2_FREE;
            W1Device.event2sim = new JSONObject()
                    .put("event2sim", "W1_POS2_FREE")
                    .put("value", "").toString();
            if(targSt == P2StateMachine.Free) {
            	W1Device.event2sim = new JSONObject()
	                    .put("event2sim", "W1_POS2_FREE")
	                    .put("value", "").toString();
                command = MessageRequest.Command.W1_POS2_FREE;
            }
            else if(targSt == P2StateMachine.Sub1Completed) {
            	W1Device.event2sim = new JSONObject()
	                    .put("event2sim", "SUB1_COMPLETED")
	                    .put("value", "").toString();
                command = MessageRequest.Command.SUB1_COMPLETED;}
            else
                System.out.println("Error while sendind targetState 2 Simulator (p1CurSt == SubAss1) ");
            MessageRequest mes = new MessageRequest(source,target,command);
            Gson gson = new Gson();
            String json = gson.toJson(mes);
         //   new Client(ConfigPorts.ACHILEAS,ConfigPorts.PORT_SIMULATOR,json);
            W1Device.fireResourcesChange(20);*/
        }

        public boolean getTMTStatus(P1StateMachine p1CurSt, P3StateMachine p3CurSt){
            return false;
        }
    },
    Sub1Completed{

        public P2StateMachine definePosStates(Rbt_2_W1_Event ev, boolean tmt, P1StateMachine p1CurSt, P3StateMachine p3CurSt){
            W1Controller.LOGGER.info("Before transition : p2CurSt == Sub1Completed \n");
            //System.out.println("Before transition : p2CurSt == Sub1Completed");

            P2StateMachine p2TargSt = Sub1Completed;
            if(ev == Rbt_2_W1_Event.R2AcquireP2)
            {
            	 p2TargSt = SubAss2;
                W1Controller.LOGGER.info("Event : R2AcquireP2 \n");
                //System.out.println("Event : R2AcquireP2");
               
                W1Controller.LOGGER.info("After transition : p2CurSt == SubAss2 \n");
                //System.out.println("After transition : p2CurSt == SubAss2");
                //Driver : change pos2 state
               // W1Simulator.p2.setBackground(Color.red);
                performActions(p2TargSt, ev);
                System.out.println("fddfdfdfdfddddddd    "+p2TargSt);
                return p2TargSt;
            }
            else{
                W1Controller.LOGGER.info("Error: p2CurSt == Sub1Completed && event != R2AcquireP2 \n");
                //System.out.println("Error: p2CurSt == Sub1Completed && event != R2AcquireP2");
                performActions(p2TargSt, ev);
                return p2TargSt;
            }
        }

        public void performActions(P2StateMachine targSt, Rbt_2_W1_Event ev){

          /*  MessageRequest.Source source = MessageRequest.Source.W1_POS2 ;
            MessageRequest.Command command = null;
            MessageRequest mes = null;
            Gson gson = null;
            String json = null;
            MessageRequest.Target target = null;*/

            if(ev == Rbt_2_W1_Event.R2AcquireP2) {
                /********* send P2Available to R2 **********/
                W1Controller.LOGGER.info("P2Available\n");
                //createAndSendMessageToR2
               /* source = MessageRequest.Source.W1_POS2;
                target = MessageRequest.Target.R2;
                command = MessageRequest.Command.W1_POS2_AVAILABLE;
                mes = new MessageRequest(source, target, command);
                gson = new Gson();
                json = gson.toJson(mes);*/
               /* try {
                	W1_LwM2mServer.robot2port.execute(Robot2Port.Execute.POS2AVAIL);
    				System.out.println("sended pos2 available");
    			} catch (InterruptedException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}*/
                
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
             //   new Client(ConfigPorts.ACHILEAS, ConfigPorts.PORT_R2, json);
            }
            /************************** send targState to Simulator  ************************************/
            /*source = MessageRequest.Source.W1_POS2 ;
            target = MessageRequest.Target.SIMULATOR ;
            command = MessageRequest.Command.SUB1_COMPLETED;
            W1Device.event2sim = new JSONObject()
                    .put("event2sim", "SUB1_COMPLETED")
                    .put("value", "").toString();
            if(targSt == P2StateMachine.SubAss2) {
            	W1Device.event2sim = new JSONObject()
	                    .put("event2sim", "SUBASS2")
	                    .put("value", "").toString();
            	command = MessageRequest.Command.SUBASS2;
            }
            else{
                W1Controller.LOGGER.info("Error while sendind targetState 2 Simulator (p2CurSt == Sub1Completed) \n ");
                //System.out.println("Error while sendind targetState 2 Simulator (p2CurSt == Sub1Completed) ");
            }
            mes = new MessageRequest(source,target,command);
            gson = new Gson();
            json = gson.toJson(mes);
           // new Client(ConfigPorts.ACHILEAS,ConfigPorts.PORT_SIMULATOR,json);
            W1Device.fireResourcesChange(20);*/
        }

        public boolean getTMTStatus(P1StateMachine p1CurSt, P3StateMachine p3CurSt){
            return false;
        }
    },

    SubAss2{
        public P2StateMachine definePosStates(Rbt_2_W1_Event ev, boolean tmt, P1StateMachine p1CurSt, P3StateMachine p3CurSt){
            W1Controller.LOGGER.info("Before transition : p2CurSt == SubAss2 \n");
            System.out.println("into subass2.........................");

            P2StateMachine p2TargSt = SubAss2;
            if(ev == Rbt_2_W1_Event.R2ReleaseP2){
                W1Controller.LOGGER.info("Event : R2ReleaseP2 \n");
                //System.out.println("Event : R2ReleaseP2");
                if(((p1CurSt == P1StateMachine.Free) || (p1CurSt == P1StateMachine.SubAss1Completed)) && (p3CurSt == P3StateMachine.Free)){
                    //tmt = true;
                    if(p1CurSt == P1StateMachine.Free){
                        p2TargSt = Free;
                        W1Controller.LOGGER.info("After transition : p2CurSt == Free \n");
                        //System.out.println("After transition : p2CurSt == Free");
                        //Driver : change pos2 state
                   //     W1Simulator.p2.setBackground(Color.green);
                        performActions(p2TargSt, ev);
                        return p2TargSt;
                    }
                    else{
                        p2TargSt = Sub1Completed;
                        W1Controller.LOGGER.info("After transition : p2CurSt == Sub1Completed \n");
                        //System.out.println("After transition : p2CurSt == Sub1Completed");
                        //Driver : change pos2 state
                 //       W1Simulator.p2.setBackground(Color.yellow);
                        performActions(p2TargSt, ev);
                        return p2TargSt;
                    }
                }else{
                    p2TargSt = SubAss2Completed;
                    W1Controller.LOGGER.info("After transition : p2CurSt == SubAss2Completed \n");
                    //System.out.println("After transition : p2CurSt == SubAss2Completed");
                    //Driver : change pos2 state
                   // W1Simulator.p2.setBackground(Color.pink);
                    performActions(p2TargSt, ev);
                    return p2TargSt;
                }
            } else{
            	W1Controller.LOGGER.info("Event :-------------------------------------------" + ev.toString());
                W1Controller.LOGGER.info("Error : p2CurSt == SubAss2 && ev != R2ReleaseP2 \n");
                //System.out.println("Error : p2CurSt == SubAss2 && ev != R2ReleaseP2");
                performActions(p2TargSt, ev);
                return p2TargSt;
            }
        }

        public void performActions(P2StateMachine p2TargSt, Rbt_2_W1_Event event){
            /************************** send targState to Simulator  ************************************/
            //W2Ctrl.LOGGER.info("Now Sending Available message to R1..");
            //System.out.println("AvailablePos1");
           /* MessageRequest.Source source = MessageRequest.Source.W1_POS2;
            MessageRequest.Target target = MessageRequest.Target.SIMULATOR ;
            MessageRequest.Command command = MessageRequest.Command.SUBASS2;
            W1Device.event2sim = new JSONObject()
                    .put("event2sim", "SUBASS2")
                    .put("value", "").toString();
            if(p2TargSt == P2StateMachine.Free) {
            	W1Device.event2sim = new JSONObject()
	                    .put("event2sim", "W1_POS2_FREE")
	                    .put("value", "").toString();
                command = MessageRequest.Command.W1_POS2_FREE;
                }
            else if(p2TargSt == P2StateMachine.Sub1Completed) {
            	W1Device.event2sim = new JSONObject()
	                    .put("event2sim", "SUB1_COMPLETED")
	                    .put("value", "").toString();
                command = MessageRequest.Command.SUB1_COMPLETED;}
            else if(p2TargSt == P2StateMachine.SubAss2Completed) {
            	W1Device.event2sim = new JSONObject()
	                    .put("event2sim", "SUBASS2_COMPLETED")
	                    .put("value", "").toString();
                command = MessageRequest.Command.SUBASS2_COMPLETED;}
            else
                W1Controller.LOGGER.info("Error while sendind targetState 2 Simulator (p2CurSt == SubAss2Completed)+\n ");
                //System.out.println("Error while sendind targetState 2 Simulator (p2CurSt == SubAss2Completed) ");
            MessageRequest mes = new MessageRequest(source,target,command);
            Gson gson = new Gson();
            String json = gson.toJson(mes);
           // new Client(ConfigPorts.ACHILEAS,ConfigPorts.PORT_SIMULATOR,json);
            W1Device.fireResourcesChange(20);*/
        }

        public boolean getTMTStatus(P1StateMachine p1CurSt, P3StateMachine p3CurSt){
            if(((p1CurSt == P1StateMachine.Free) || (p1CurSt == P1StateMachine.SubAss1Completed)) && (p3CurSt == P3StateMachine.Free))
                return true;
            else
                return false;
        }
    },
    SubAss2Completed{


        public P2StateMachine definePosStates(Rbt_2_W1_Event ev, boolean tmt, P1StateMachine p1CurSt, P3StateMachine p3CurStr){
            W1Controller.LOGGER.info("Before transition : p2CurSt == SubAss2Completed");
            //System.out.println("Before transition : p2CurSt == SubAss2Completed");

            P2StateMachine p2TargSt = SubAss2Completed;
            if(tmt){
                if(p1CurSt == P1StateMachine.Free){
                    p2TargSt = Free;
                    W1Controller.LOGGER.info("After transition : p2CurSt == Free \n");
                    //System.out.println("After transition : p2CurSt == Free");
                    //Driver : change pos2 state
               //     W1Simulator.p2.setBackground(Color.green);
                    performActions(p2TargSt, ev);
                    return p2TargSt;
                }
                else{
                    p2TargSt = Sub1Completed;
                    W1Controller.LOGGER.info("After transition : p2CurSt == Sub1Completed \n");
                    //System.out.println("After transition : p2CurSt == Sub1Completed");
                    //Driver : change pos2 state
                 //   W1Simulator.p2.setBackground(Color.yellow);
                    performActions(p2TargSt, ev);
                    return p2TargSt;
                }
            }else if(ev != Rbt_2_W1_Event.R2AcquireP2){
                W1Controller.LOGGER.info("Error : p2CurSt == SubAss2Completed && ev != R2AcquireP2\n");
                //System.out.println("Error : p2CurSt == SubAss2Completed && ev != R2AcquireP2");
                performActions(p2TargSt, ev);
                return p2TargSt;
            }else {
                W1Controller.LOGGER.info("Event : R2AcquireP2 \n");
                W1Controller.LOGGER.info("No transition : p2CurSt == SubAss2Completed\n");
                //System.out.println("Event : R2AcquireP2");
                //System.out.println("No transition : p2CurSt == SubAss2Completed");
                performActions(p2TargSt, ev);
                return p2TargSt;
            }
        }

        public void performActions(P2StateMachine p2TargSt, Rbt_2_W1_Event ev){
            /************************** send targState to Simulator  ************************************/
            //W2Ctrl.LOGGER.info("Now Sending Available message to R1..");
            //System.out.println("AvailablePos1");
           /* MessageRequest.Source source = MessageRequest.Source.W1_POS2;
            MessageRequest.Target target = MessageRequest.Target.SIMULATOR ;
            MessageRequest.Command command = MessageRequest.Command.SUBASS2_COMPLETED;
            W1Device.event2sim = new JSONObject()
                    .put("event2sim", "SUBASS2_COMPLETED")
                    .put("value", "").toString();
            if(p2TargSt == P2StateMachine.Free) {
            	W1Device.event2sim = new JSONObject()
	                    .put("event2sim", "W1_POS2_FREE")
	                    .put("value", "").toString();
                command = MessageRequest.Command.W1_POS2_FREE;}
            else if(p2TargSt == P2StateMachine.Sub1Completed) {
            	W1Device.event2sim = new JSONObject()
	                    .put("event2sim", "SUB1_COMPLETED")
	                    .put("value", "").toString();
                command = MessageRequest.Command.SUB1_COMPLETED;}
            else
                W1Controller.LOGGER.info("Error while sendind targetState 2 Simulator (p2CurSt == SubAss2Completed) \n");
                //System.out.println("Error while sendind targetState 2 Simulator (p2CurSt == SubAss2Completed) ");
            MessageRequest mes = new MessageRequest(source,target,command);
            Gson gson = new Gson();
            String json = gson.toJson(mes);
            //new Client(ConfigPorts.ACHILEAS,ConfigPorts.PORT_SIMULATOR,json);
            W1Device.fireResourcesChange(20);*/
        }

        public boolean getTMTStatus(P1StateMachine p1CurSt, P3StateMachine p3CurSt){
            return false;
        }
    }


}