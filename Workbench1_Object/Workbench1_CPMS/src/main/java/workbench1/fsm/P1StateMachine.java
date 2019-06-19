package workbench1.fsm;


import java.sql.Timestamp;

import org.eclipse.leshan.core.request.ExecuteRequest;
import org.eclipse.leshan.core.response.ExecuteResponse;
import org.eclipse.leshan.server.registration.Registration;

import workbench1.events.Rbt_2_W1_Event;
import workbench1.lwm2m.W1_LwM2mServer;

public enum P1StateMachine implements P1StateMachineIf {
    Free{

        public P1StateMachine definePosStates(Rbt_2_W1_Event ev, boolean tmt, P2StateMachine p2CurSt, P3StateMachine p3CurSt){
            W1Controller.LOGGER.info("Before transision : p1CurSt == Free");
            //System.out.println("Before transision : p1CurSt == Free");

            //W1Simulator.p1.setBackground(Color.GREEN);

            P1StateMachine targSt  = Free;

            if( ev == Rbt_2_W1_Event.R1AcquireP1){
                W1Controller.LOGGER.info("Event : R1AcquireP1");
                //System.out.println("Event : R1AcquireP1");
                targSt = SubAss1;
                W1Controller.LOGGER.info("After transition : p1CurSt == SubAss1");
                //System.out.println("After transition : p1CurSt == SubAss1");
                performActions(targSt, ev);
                //Driver : change pos 1 state
               // W1Simulator.p1.setBackground(Color.red);
                return targSt;
            }
            //den stelnw oti pali eimai se free ---> den kserw an exei nohma
            System.out.println("No transition : p1CurSt == Free");
            return targSt;

            //edw paizei na uparksei lathos an den erthei ev == R1AcquireP1
        }

        public void performActions(P1StateMachine targSt, Rbt_2_W1_Event event){
           /* MessageRequest.Source source = MessageRequest.Source.W1_POS1 ;
            MessageRequest.Command command = null;
            MessageRequest mes = null;
            Gson gson = null;
            String json = null;
            MessageRequest.Target  target = null;*/

            if(event == Rbt_2_W1_Event.R1AcquireP1) {
                /****************** send event P1Available to R1 *********************/
                W1Controller.LOGGER.info("P1Available");
                //System.out.println("P1Available");

                //createAndSendMessageToR1
            /*    //System.out.println("AvailablePos1");
                source = MessageRequest.Source.W1_POS1;
                target = MessageRequest.Target.R1;
               */ 
               
                	//W1_LwM2mServer.robot1port.execute(Robot1Port.Execute.POS1AVAIL);
                	ExecuteRequest request=new ExecuteRequest(20000,0,3,String.format("{ \"sender\": \"SERVER\", \"receiver\": \"ROBOT\", \"timestamp\": \"%s\"}", new Timestamp(System.currentTimeMillis())));//(Robot1Port.Execute.POS1AVAIL.getPath());
                	//new ExecuteRequest(5, 0, 2)
                	
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
    			
                
              /*  command = MessageRequest.Command.W1_POS1_AVAILABLE;
                mes = new MessageRequest(source, target, command);
                gson = new Gson();
                json = gson.toJson(mes);
             //   new Client(ConfigPorts.HET, ConfigPorts.PORT_R1, json);
              * */
            }

            /*//************************** send targState to Simulator  ************************************//*
            //W2Ctrl.LOGGER.info("Now Sending Available message to R1..");
            //System.out.println("AvailablePos1");
            source = MessageRequest.Source.W1_POS1 ;
            target = MessageRequest.Target.SIMULATOR ;
           
    
            if(targSt == P1StateMachine.Free) 
            {
                command = MessageRequest.Command.W1_POS1_FREE;
	            W1Device.event2sim = new JSONObject()
	                    .put("event2sim", "W1_POS1_FREE")
	                    .put("value", "").toString();
            }
            else if(targSt == P1StateMachine.SubAss1) 
            {
            	 W1Device.event2sim = new JSONObject()
                         .put("event2sim", "SUBASS1")
                         .put("value", "").toString();
                command = MessageRequest.Command.SUBASS1;
            }
            else
                W1Controller.LOGGER.info("Error while sendind targetState 2 Simulator (p1CurSt == Free) ");
                //System.out.println("Error while sendind targetState 2 Simulator (p1CurSt == Free) ");
            mes = new MessageRequest(source,target,command);
            gson = new Gson();
            json = gson.toJson(mes);
            
            W1Device.fireResourcesChange(20);*/
           // new Client(ConfigPorts.ACHILEAS,ConfigPorts.PORT_SIMULATOR,json);
        }

        public boolean getTMTStatus(P2StateMachine p2CurSt, P3StateMachine p3CurSt){
            return false;
        }
    },
    SubAss1{
        public P1StateMachine definePosStates(Rbt_2_W1_Event ev, boolean tmt, P2StateMachine p2CurSt, P3StateMachine p3CurSt){
            W1Controller.LOGGER.info("Before transition : p1CurSt == SubAss1");
            //System.out.println("Before transition : p1CurSt == SubAss1");

            P1StateMachine targSt  = SubAss1;

            if(ev == Rbt_2_W1_Event.R1ReleaseP1){
                W1Controller.LOGGER.info("Event : R1ReleaseP1");
                //System.out.println("Event : R1ReleaseP1");
                //1) check if table must turn
                if((p2CurSt == (P2StateMachine.Free) || p2CurSt == P2StateMachine.SubAss2Completed) && p3CurSt == P3StateMachine.Free){
                    targSt = Free;
                    W1Controller.LOGGER.info("After transition : p1CurSt == Free");
                    //System.out.println("After transition : p1CurSt == Free");
                    //Driver :  change pos1 state
                   // W1Simulator.p1.setBackground(Color.GREEN);
                    performActions(targSt, ev);
                    return targSt;
                }
                else
                {
                    targSt = SubAss1Completed;
                    W1Controller.LOGGER.info("After transition : p1CurSt == SubAss1Completed");
                    //System.out.println("After transition : p1CurSt == SubAss1Completed");
                    //Driver : change pos 1 state
                  //  W1Simulator.p1.setBackground(Color.pink);
                    performActions(targSt, ev);
                    return targSt;
                }
            }else{
                W1Controller.LOGGER.info("Error :  p1CurSt == subAss1 kai m erthei event ap to R1");
                //System.out.println("Error :  p1CurSt == subAss1 kai m erthei event ap to R1");
                performActions(targSt, ev);
                return targSt;
            }
        }

        public void performActions(P1StateMachine targSt, Rbt_2_W1_Event event){
            /************************** send targState to Simulator  ************************************/
            //W2Ctrl.LOGGER.info("Now Sending Available message to R1..");
            //System.out.println("AvailablePos1");
            /*MessageRequest.Source source = MessageRequest.Source.W1_POS1;
            MessageRequest.Target target = MessageRequest.Target.SIMULATOR ;
            MessageRequest.Command command = MessageRequest.Command.SUBASS1;
            W1Device.event2sim = new JSONObject()
                    .put("event2sim", "SUBASS1")
                    .put("value", "").toString();
            if(targSt == P1StateMachine.Free) {
            	W1Device.event2sim = new JSONObject()
	                    .put("event2sim", "W1_POS1_FREE")
	                    .put("value", "").toString();
            	command = MessageRequest.Command.W1_POS1_FREE;
            }
                
            else if(targSt == P1StateMachine.SubAss1Completed) {
            	W1Device.event2sim = new JSONObject()
	                    .put("event2sim", "SUBASS1_COMPLETED")
	                    .put("value", "").toString();
                command = MessageRequest.Command.SUBASS1_COMPLETED;}
            else
                System.out.println("Error while sendind targetState 2 Simulator (p1CurSt == SubAss1) ");
            MessageRequest mes = new MessageRequest(source,target,command);
            Gson gson = new Gson();
            String json = gson.toJson(mes);
          //  new Client(ConfigPorts.ACHILEAS,ConfigPorts.PORT_SIMULATOR,json);
            W1Device.fireResourcesChange(20);*/
        }

        public boolean getTMTStatus(P2StateMachine p2CurSt, P3StateMachine p3CurSt){
            if((p2CurSt == (P2StateMachine.Free) || p2CurSt == P2StateMachine.SubAss2Completed) && p3CurSt == P3StateMachine.Free)
                return true;
            else
                return false;
        }

    },
    SubAss1Completed{

        public P1StateMachine definePosStates(Rbt_2_W1_Event ev, boolean tmt, P2StateMachine p2CurSt, P3StateMachine p3CurSt){
            W1Controller.LOGGER.info("Before transition : p1CurSt == SubAss1Completed");
            //System.out.println("Before transition : p1CurSt == SubAss1Completed");

            P1StateMachine targSt = SubAss1Completed;
            if(tmt){
                targSt = Free;
                W1Controller.LOGGER.info("After transition : p1CurSt == Free");
                //System.out.println("After transition : p1CurSt == Free");
                //Driver : change pos 1 state
            //    W1Simulator.p1.setBackground(Color.green);
                performActions(targSt, ev);
                return targSt;
            }else if(ev == Rbt_2_W1_Event.R1AcquireP1){
                W1Controller.LOGGER.info("Event : R1AcquireP1");
                //System.out.println("Event : R1AcquireP1");
                //System.out.println("deferredEvent4R1");
                ////////////////////////////////////////////////////SOS na ftiaksw ta deferredEvents!!!!!//////////////////////////////////
                W1Controller.LOGGER.info("No transition : p1CurSt == SubAss1Completed");
                //System.out.println("No transition : p1CurSt == SubAss1Completed");
                performActions(targSt, ev);
                return targSt;
            }else{
                W1Controller.LOGGER.info("error: p1CurSt = SubAss1Completed and received a non R1acquireP1 event.");
                //System.out.println("error: p1CurSt = SubAss1Completed and received a non R1acquireP1 event. ");
                performActions(targSt, ev);
                return targSt;
            }
        }

        public void performActions(P1StateMachine targSt, Rbt_2_W1_Event event){
            /************************** send targState to Simulator  ************************************/
           /* MessageRequest.Source source = MessageRequest.Source.W1_POS1;
            MessageRequest.Target target = MessageRequest.Target.SIMULATOR ;
            MessageRequest.Command command = MessageRequest.Command.SUBASS1_COMPLETED;
            W1Device.event2sim = new JSONObject()
                    .put("event2sim", "SUBASS1_COMPLETED")
                    .put("value", "").toString();
            if(targSt == P1StateMachine.Free) {
            	W1Device.event2sim = new JSONObject()
	                    .put("event2sim", "W1_POS1_FREE")
	                    .put("value", "").toString();
                command = MessageRequest.Command.W1_POS1_FREE;}
            else
                W1Controller.LOGGER.info("Error while sendind targetState 2 Simulator (p1CurSt == SubAss1Completed) ");
                //System.out.println("Error while sendind targetState 2 Simulator (p1CurSt == SubAss1Completed) ");
            MessageRequest mes = new MessageRequest(source,target,command);
            Gson gson = new Gson();
            String json = gson.toJson(mes);
            //new Client(ConfigPorts.ACHILEAS,ConfigPorts.PORT_SIMULATOR,json);
            W1Device.fireResourcesChange(20);*/
        }

        public boolean getTMTStatus(P2StateMachine p2CurSt, P3StateMachine p3CurSt){
            return false;
        }
    }

}
