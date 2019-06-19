package robot3.lwm2m;

import org.eclipse.leshan.client.resource.BaseInstanceEnabler;
import org.eclipse.leshan.core.node.LwM2mResource;
import org.eclipse.leshan.core.response.ExecuteResponse;
import org.eclipse.leshan.core.response.ReadResponse;
import org.eclipse.leshan.core.response.WriteResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import robot3.fsm.Robot3Ctrl;
import robot3.fsm.SignalDetector;
import robot3.fsm.signals.W1Pos3Available;
import uml4iot.GenericStateMachine.core.BaseSignal;

public class RobotInstance extends BaseInstanceEnabler {

    private static final Logger LOG = LoggerFactory.getLogger(RobotInstance.class);
    public static int modelId = 20002;
    public static String event;
    public static String event2sim;
    public static Robot3Ctrl robot3Ctrl;
    public static SignalDetector signaldetect;
    public RobotInstance(final Robot3Ctrl robot3Ctrl, String endpoint) {
    	this.robot3Ctrl = robot3Ctrl;
    	signaldetect = new SignalDetector(robot3Ctrl.itsMsgQ);
    	 //new Thread(robot3Ctrl).start();
    	/*event = new JSONObject()
                .put("event", "")
                .put("timestamp", "")
                .put("value", "").toString();*/
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
        case 1:
            return ReadResponse.success(resourceid, getpos3available());
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
        case 3:    //setPos3Available
        	 return addSignal(params, W1Pos3Available.class);
        default:
            return execute(resourceid, params);
        }
		
      
    }

    @Override
    public WriteResponse write(int resourceid, LwM2mResource value) {
		return null;
       
    }

    private String getStatus() {
        return " test";
    }

    private Boolean getpos3available() {
        return Robot3Ctrl.pos3avail;
    }

    
    
    private <T extends BaseSignal> ExecuteResponse addSignal(String args, Class<T> clazz){
       /* if (args == null){
            return ExecuteResponse.badRequest("Arguments not correct");
        }
        try {
            Constructor<T> ctor = clazz.getConstructor(String.class);
            BaseSignal sign = ctor.newInstance(args);
            robot3Ctrl.getEventQueue().put(sign);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return ExecuteResponse.internalServerError("Operation stopped:" + e.getMessage());
        } catch (Exception e){
            return ExecuteResponse.badRequest("Arguments not correct:" + e.getMessage());
        }*/
    	signaldetect.msgQ.add(new W1Pos3Available());
        return ExecuteResponse.success();
    }
}
