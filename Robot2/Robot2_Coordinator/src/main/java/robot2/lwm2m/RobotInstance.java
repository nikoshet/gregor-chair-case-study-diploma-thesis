package robot2.lwm2m;

import org.eclipse.leshan.client.resource.BaseInstanceEnabler;
import org.eclipse.leshan.core.node.LwM2mResource;
import org.eclipse.leshan.core.response.ExecuteResponse;
import org.eclipse.leshan.core.response.ReadResponse;
import org.eclipse.leshan.core.response.WriteResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import robot2.fsm.Robot2Coordinator;
import robot2.fsm.SignalDetector;
import robot2.fsm.signals.Pos1Reached;
import robot2.fsm.signals.Pos2Reached;
import robot2.fsm.signals.W1Pos2Available;
import robot2.fsm.signals.W2Available;
import uml4iot.GenericStateMachine.core.BaseSignal;

public class RobotInstance extends BaseInstanceEnabler {

    private static final Logger LOG = LoggerFactory.getLogger(RobotInstance.class);
    public static int modelId = 20001;
    public static String event;
    public static String event2sim;
    public static Robot2Coordinator robot2Coordinator;
    public static SignalDetector signaldetect;
    public RobotInstance(final Robot2Coordinator robot2Coordinator, String endpoint) {
    	this.robot2Coordinator = robot2Coordinator;
    	signaldetect = new SignalDetector(robot2Coordinator.itsMsgQ);
    	event="";
    	event2sim="";
    	/*new Thread(()-> {
			
				/while (true) {
					if(event2sim!="") {
						fireResourcesChange(20);
						event2sim="";
					}
					if(event!="") {
						fireResourcesChange(16);
						System.out.println("event \n \n \n \n \n \n \n \n " + event + "\n \n \n \n \n \n \n \n \n");
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
        case 1:    //Pos1Reached
            return addSignal(params, Pos1Reached.class);
        case 2:    //Pos2Reached
            return addSignal(params, Pos2Reached.class);
        case 3:    //setW1Pos2Available
            return addSignal(params, W1Pos2Available.class);
        case 4:    //setw2Available
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
        return "test";
    }
    
    private <T extends BaseSignal> ExecuteResponse addSignal(String args, Class<T> clazz){
        if (args == null){
            return ExecuteResponse.badRequest("Arguments not correct");
        }
        System.out.println(clazz.getSimpleName());

        switch(clazz.getSimpleName()){
            case "Pos1Reached":    //Pos1Reached
                signaldetect.msgQ.add( new Pos1Reached());
                return ExecuteResponse.success();

            case "Pos2Reached":    //Pos2Reached
                signaldetect.msgQ.add( new Pos2Reached());
                return ExecuteResponse.success();

            case "W1Pos2Available":    //setPos1Available
                signaldetect.msgQ.add( new W1Pos2Available());
                return ExecuteResponse.success();

            case "W2Available":    //setW2Available
                signaldetect.msgQ.add(new W2Available());
                return ExecuteResponse.success();

            default:
                return ExecuteResponse.badRequest(args) ;
        }

    }
}
