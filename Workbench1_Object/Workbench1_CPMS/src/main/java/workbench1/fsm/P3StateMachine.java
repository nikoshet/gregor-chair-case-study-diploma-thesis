package workbench1.fsm;


import java.sql.Timestamp;

import org.eclipse.leshan.core.request.ExecuteRequest;
import org.eclipse.leshan.core.response.ExecuteResponse;
import org.eclipse.leshan.server.registration.Registration;

import workbench1.events.Rbt_2_W1_Event;
import workbench1.lwm2m.W1_LwM2mServer;


public enum P3StateMachine implements P3StateMachineIf {
    Free{
        public P3StateMachine definePosStates(Rbt_2_W1_Event ev, boolean tmt, P1StateMachine p1CurSt, P2StateMachine p2CurSt){
            W1Controller.LOGGER.info("Before transition : p3CurSt == Free \n ");
            //System.out.println("Before transition : p3CurSt == Free");

            P3StateMachine p3TargSt = Free;
            if(tmt && ((p2CurSt == P2StateMachine.SubAss2)||(p2CurSt == P2StateMachine.SubAss2Completed))){
                p3TargSt = Sub2Completed;
                W1Controller.LOGGER.info("After transition : p3CurSt == Sub2Completed \n ");
                //System.out.println("After transition : p3CurSt == Sub2Completed");
                //Driver : change pos3 state
               // W1Simulator.p3.setBackground(Color.yellow);
                performActions(p3TargSt, ev);
                return p3TargSt;
            }
            else if(!tmt && ev != Rbt_2_W1_Event.R3AcquireP3){
                W1Controller.LOGGER.info("Error : (p3CurSt == Free) && (tmt == false) && (ev != R3AcquireP3) \n  ");
                //System.out.println("Error : (p3CurSt == Free) && (tmt == false) && (ev != R3AcquireP3)  ");
                performActions(p3TargSt, ev);
                return p3TargSt;
            }else if(!tmt && ev == Rbt_2_W1_Event.R3AcquireP3){
                W1Controller.LOGGER.info("Event : R3AcquireP3 \n ");
                //System.out.println("Event : R3AcquireP3");
                W1Controller.LOGGER.info("No transition : p3CurSt == Free \n ");
                //System.out.println("No transition : p3CurSt == Free");
                performActions(p3TargSt, ev);
                return p3TargSt;
            }else{
                W1Controller.LOGGER.info("No transition : p3CurSt == Free \n ");
                //System.out.println("No transition : p3CurSt == Free");
                performActions(p3TargSt, ev);
                return p3TargSt;//???????????
            }
        }

        public void performActions(P3StateMachine p3TargSt, Rbt_2_W1_Event ev){
            /************************** send targState to Simulator  ************************************/
            //W2Ctrl.LOGGER.info("Now Sending Available message to R1..");
            //System.out.println("AvailablePos1");
            /*MessageRequest.Source source = MessageRequest.Source.W1_POS3;
            MessageRequest.Target target = MessageRequest.Target.SIMULATOR ;
            MessageRequest.Command command = MessageRequest.Command.W1_POS3_FREE;
            W1Device.event2sim = new JSONObject()
                    .put("event2sim", "W1_POS3_FREE")
                    .put("value", "").toString();
            
            if(p3TargSt == P3StateMachine.Sub2Completed) {
            	W1Device.event2sim = new JSONObject()
	                    .put("event2sim", "SUB2_COMPLETED")
	                    .put("value", "").toString();
                command = MessageRequest.Command.SUB2_COMPLETED;
            }
            else
                W1Controller.LOGGER.info("Error while sendind targetState 2 Simulator (p3CurSt == Free) or first time of the run\n ");
                //System.out.println("Error while sendind targetState 2 Simulator (p3CurSt == Free) ");
            MessageRequest mes = new MessageRequest(source,target,command);
            Gson gson = new Gson();
            String json = gson.toJson(mes);
          //  new Client(ConfigPorts.ACHILEAS,ConfigPorts.PORT_SIMULATOR,json);
            W1Device.fireResourcesChange(20);*/
        }

        public boolean getTMTStatus(P1StateMachine p1CurSt, P2StateMachine p2CurSt){
            return false;
        }
    },
    Sub2Completed{
        public P3StateMachine definePosStates(Rbt_2_W1_Event ev, boolean tmt, P1StateMachine p1CurSt, P2StateMachine p2CurSt){
            W1Controller.LOGGER.info("Before transition : p3CurSt == Sub2Completed \n ");
            //System.out.println("Before transition : p3CurSt == Sub2Completed");

            P3StateMachine p3TargSt = Sub2Completed;
            if(ev == Rbt_2_W1_Event.R3AcquireP3)
            {
                W1Controller.LOGGER.info("Event : R3AcquireP3 \n ");
                //System.out.println("Event : R3AcquireP3");
                p3TargSt = SubAss3;
                performActions(p3TargSt, ev);
                W1Controller.LOGGER.info("After transition : p3CurSt == SubAss3 \n ");
                //System.out.println("After transition : p3CurSt == SubAss3");
                //Driver : change pos3 state
            //    W1Simulator.p3.setBackground(Color.red);
               // performActions(p3TargSt, ev);
                return p3TargSt;
            }else {
                W1Controller.LOGGER.info("Error : (p3CurSt == Sub2Completed) && ev != R3AcquireP3 \n ");
                //System.out.println("Error : (p3CurSt == Sub2Completed) && ev != R3AcquireP3");
                performActions(p3TargSt, ev);
                return p3TargSt;
            }
        }

        public void performActions(P3StateMachine p3TargSt, Rbt_2_W1_Event ev){
           /* MessageRequest.Source source = MessageRequest.Source.W1_POS3 ;
            MessageRequest.Command command = null;
            MessageRequest mes = null;
            Gson gson = null;
            String json = null;
            MessageRequest.Target  target = null;

            if(ev == Rbt2W1Event.R3AcquireP3){
                *//******** send event P3Available to R3 ********//*
                W1Controller.LOGGER.info("P3Available \n ");
                target = MessageRequest.Target.R3 ;

                command = MessageRequest.Command.W1_POS3_AVAILABLE;
                mes = new MessageRequest(source,target,command);
                gson = new Gson();
                json = gson.toJson(mes);

                System.out.println("Send ");
                *//*try {
					W1_LwM2mServer.robot3port.execute(Robot3Port.Execute.POS3AVAIL);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
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
                /*
              //  new Client(ConfigPorts.ACHILEAS,ConfigPorts.PORT_R3,json);
                System.out.println("Sent ");
            }
            *//************************** send targState to Simulator  ************************************//*
            source = MessageRequest.Source.W1_POS3 ;
            target = MessageRequest.Target.SIMULATOR ;
            command = MessageRequest.Command.SUB2_COMPLETED;
            W1Device.event2sim = new JSONObject()
                    .put("event2sim", "SUB2_COMPLETED")
                    .put("value", "").toString();
            if(p3TargSt == P3StateMachine.SubAss3) {
            	 W1Device.event2sim = new JSONObject()
                         .put("event2sim", "SUBASS3")
                         .put("value", "").toString();
                command = MessageRequest.Command.SUBASS3;}
            else
                W1Controller.LOGGER.info("Error while sendind targetState 2 Simulator (p3CurSt == Sub2Completed) \n ");
                //System.out.println("Error while sendind targetState 2 Simulator (p3CurSt == Sub2Completed) ");
            mes = new MessageRequest(source,target,command);
            gson = new Gson();
            json = gson.toJson(mes);
          //  new Client(ConfigPorts.ACHILEAS,ConfigPorts.PORT_SIMULATOR,json);
            W1Device.fireResourcesChange(20);*/

        }

        public boolean getTMTStatus(P1StateMachine p1CurSt, P2StateMachine p2CurSt){
            return false;
        }
    },
    SubAss3{
        public P3StateMachine definePosStates(Rbt_2_W1_Event ev, boolean tmt, P1StateMachine p1CurSt, P2StateMachine p2CurSt){
            W1Controller.LOGGER.info("Before transition : p3CurSt == SubAss3 \n");
            //System.out.println("Before transition : p3CurSt == SubAss3");

            P3StateMachine p3TargSt = SubAss3;
            if(ev == Rbt_2_W1_Event.R3ReleaseP3){
                W1Controller.LOGGER.info("Event : R3ReleaseP3 \n");
                //System.out.println("Event : R3ReleaseP3");
                if(((p1CurSt == P1StateMachine.Free) || (p1CurSt == P1StateMachine.SubAss1Completed))&& (p2CurSt == P2StateMachine.SubAss2Completed)){
                    //tmt = true;
                    p3TargSt = Sub2Completed;
                    W1Controller.LOGGER.info("After transition : p3CurSt == Sub2Completed \n");
                    //System.out.println("After transition : p3CurSt == Sub2Completed");
                    //Driver : change pos3 state
                   // W1Simulator.p3.setBackground(Color.yellow);
                    performActions(p3TargSt, ev);
                    return p3TargSt;
                }else {
                    p3TargSt = Free;

                    W1Controller.LOGGER.info("After transition : p3CurSt == Free \n");
                    //System.out.println("After transition : p3CurSt == Free");
                    //Driver : change pos3 state
                //    W1Simulator.p3.setBackground(Color.green);
                    performActions(p3TargSt, ev);
                    return p3TargSt;
                }
            }else {
                W1Controller.LOGGER.info("Error : (p3CurSt == SubAss3) && (ev != R3ReleaseP3) \n");
                //System.out.println("Error : (p3CurSt == SubAss3) && (ev != R3ReleaseP3)");
                performActions(p3TargSt, ev);
                return p3TargSt;
            }
        }

        public void performActions(P3StateMachine p3TargSt, Rbt_2_W1_Event ev){
            /************************** send targState to Simulator  ************************************/
           /* MessageRequest.Source source = MessageRequest.Source.W1_POS3;
            MessageRequest.Target target = MessageRequest.Target.SIMULATOR ;
            MessageRequest.Command command = MessageRequest.Command.SUBASS3;
            W1Device.event2sim = new JSONObject()
                    .put("event2sim", "SUBASS3")
                    .put("value", "").toString();
            if(p3TargSt == P3StateMachine.Sub2Completed) {
            	 W1Device.event2sim = new JSONObject()
                         .put("event2sim", "SUB2_COMPLETED")
                         .put("value", "").toString();
                command = MessageRequest.Command.SUB2_COMPLETED;}
            if(p3TargSt == P3StateMachine.Free) {
            	 W1Device.event2sim = new JSONObject()
                         .put("event2sim", "W1_POS3_FREE")
                         .put("value", "").toString();
                command = MessageRequest.Command.W1_POS3_FREE;}
            else
                W1Controller.LOGGER.info("Error while sendind targetState 2 Simulator (p3CurSt == SubAss3) \n ");
                //System.out.println("Error while sendind targetState 2 Simulator (p3CurSt == SubAss3) ");
            MessageRequest mes = new MessageRequest(source,target,command);
            Gson gson = new Gson();
            String json = gson.toJson(mes);
           /// new Client(ConfigPorts.ACHILEAS,ConfigPorts.PORT_SIMULATOR,json);
            W1Device.fireResourcesChange(20);*/
        }

        public boolean getTMTStatus(P1StateMachine p1CurSt, P2StateMachine p2CurSt){
            if(((p1CurSt == P1StateMachine.Free) || (p1CurSt == P1StateMachine.SubAss1Completed))&& (p2CurSt == P2StateMachine.SubAss2Completed))
                return true;
            else
                return false;
        }
    }
}