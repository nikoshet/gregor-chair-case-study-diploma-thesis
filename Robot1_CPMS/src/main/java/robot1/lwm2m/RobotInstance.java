package robot1.lwm2m;


import org.eclipse.leshan.client.resource.BaseInstanceEnabler;
import org.eclipse.leshan.core.node.LwM2mResource;
import org.eclipse.leshan.core.response.ExecuteResponse;
import org.eclipse.leshan.core.response.ReadResponse;
import org.eclipse.leshan.core.response.WriteResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import robot1.fsm.Robot1Ctrl;
import robot1.fsm.SignalDetector;
import robot1.fsm.signals.Pos1AvailSignal;
import robot1.fsm.signals.Pos1Reached;
import robot1.fsm.signals.Pos2Reached;
import robot1.fsm.signals.W2Available;
import uml4iot.GenericStateMachine.core.BaseSignal;

public class RobotInstance extends BaseInstanceEnabler {

    private static final Logger LOG = LoggerFactory.getLogger(RobotInstance.class);
    public static int modelId = 20000;
    public static String event;
    public static String event2sim;
    public static Robot1Ctrl robot1ctrl;
    public static SignalDetector signaldetect;
    public RobotInstance(final Robot1Ctrl robot1ctrl , String endpoint) {
    	RobotInstance.robot1ctrl=robot1ctrl;
    	signaldetect = new SignalDetector(robot1ctrl.itsMsgQ);
    	
    	event="";
    	event2sim="";
    	/*new Thread(()-> {
			
				while (true) {
					if(event2sim!="") {
						fireResourcesChange(20);
						event2sim="";
					}
					if(event!="") {
						fireResourcesChange(16);
						event="";
					}
				}
			
			}
		).start();*/
    	
    }

    @Override
    public ReadResponse read(int resourceid) {
        LOG.info("Read on Device Resource " + resourceid);
        switch (resourceid) {
        case 0:
            return ReadResponse.success(resourceid, getStatus());
        case 16:
            return ReadResponse.success(resourceid,event);
        case 20:
            return ReadResponse.success(resourceid,event2sim);

        default:
            return super.read(resourceid);
        }
    }

	@Override
    public ExecuteResponse execute(int resourceid, String params) {
		LOG.info("Execute on Device Resource " + resourceid + params);
        switch (resourceid) {
        case 1:    //setPos1Available
       	 return addSignal(params, Pos1Reached.class);
        case 2:    //setW2Available
      	 return addSignal(params, Pos2Reached.class);
        case 3:    //setPos1Available
        	System.out.println("\n \n \n \n \n \n pos1available reached \n \n \n \n \n \n \n ");
        	 return addSignal(params, Pos1AvailSignal.class);
        case 4:    //setW2Available
       	 return addSignal(params, W2Available.class);
        default:
            return execute(resourceid, params);
        }
		
      
    }

    @Override
    public WriteResponse write(int resourceid, LwM2mResource value) {
		return null;
       
    }

    private String getStatus() {
        return " waiting4pos1";
    }
    
    private Boolean getpos1available() {
        return Robot1Ctrl.pos1avail;
    }
    
    private Boolean getW2available() {
    	return Robot1Ctrl.w2avail;
    }
    
    
    
    
    private <T extends BaseSignal> ExecuteResponse addSignal(String args, Class<T> clazz){
        if (args == null){
            return ExecuteResponse.badRequest("Arguments not correct");
        }
        
            System.out.println("here is clazz name" +clazz.getSimpleName());
			
            switch(clazz.getSimpleName()){
	            case "Pos1AvailSignal":    //setPos1Available
	            	signaldetect.msgQ.add( new Pos1AvailSignal());
	            	 return ExecuteResponse.success();
				  
	            case "W2Available":    //setW2Available
	            	signaldetect.msgQ.add(new W2Available());
	            	return ExecuteResponse.success();
	            
	            case "Pos1Reached":    //setPos1Available
	            	System.out.println("\n \n \n \n \n \n pos1available reached \n \n \n \n \n \n \n ");
	            	signaldetect.msgQ.add( new Pos1Reached());
	            	 return ExecuteResponse.success();
				  
	            case "Pos2Reached":    //setW2Available
	            	signaldetect.msgQ.add(new Pos2Reached());
	            	return ExecuteResponse.success();
				 
	            default:
	               return ExecuteResponse.badRequest(args) ;
	        }
           
    }
}
