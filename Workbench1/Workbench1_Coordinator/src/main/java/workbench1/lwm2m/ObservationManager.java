package workbench1.lwm2m;

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import org.eclipse.leshan.core.node.LwM2mNode;
import org.eclipse.leshan.core.node.LwM2mObjectInstance;
import org.eclipse.leshan.core.node.LwM2mResource;
import org.eclipse.leshan.core.node.LwM2mSingleResource;
import org.eclipse.leshan.core.observation.Observation;
import org.eclipse.leshan.core.response.ObserveResponse;
import org.eclipse.leshan.server.californium.impl.LeshanServer;
import org.eclipse.leshan.server.observation.ObservationListener;
import org.eclipse.leshan.server.registration.Registration;
import org.json.JSONException;
import org.json.JSONObject;
import workbench1.events.Rbt_2_W1_Event;
import workbench1.fsm.W1Coordinator;
import workbench1.lwm2m.resources.RResources;

public class ObservationManager implements ObservationListener{

	public ObservationManager(LeshanServer server , ArrayBlockingQueue<Rbt_2_W1_Event> eventQueue) {
		  server.getObservationService().addListener(this);
	}

	@Override
	public void newObservation(Observation observation, Registration registration) {
		System.out.println("Observing resource " + observation.getPath() + " from client " + registration.getEndpoint());
	}

	@Override
	public void cancelled(Observation observation) { }

	@Override
	public void onResponse(Observation observation, Registration registration, ObserveResponse response) {
		System.out.println("New notification from client " + observation.getRegistrationId()+ ": "
	                + response.getContent() + response.getObservation().getPath().toString());
		 
		 if (registration != null) {
	            String path = response.getObservation().getPath().toString();

	            LwM2mNode node = response.getContent();
	            if (node instanceof LwM2mObjectInstance) {
	                LwM2mObjectInstance instance = (LwM2mObjectInstance) node;
	                Map<Integer, LwM2mResource> resources = instance.getResources();
	                for (Map.Entry<Integer, LwM2mResource> entry : resources.entrySet()) {
	                    LwM2mSingleResource value = (LwM2mSingleResource) entry.getValue();
	                    String fullPath = path + '/' + value.getId();
	                    try { mapEvent2Queue(new ObservationData(registration, value, fullPath)); }
	                    catch (JSONException e) { e.printStackTrace(); }
	                }
	            }
	            else if (node instanceof LwM2mSingleResource) {
	                LwM2mSingleResource value = (LwM2mSingleResource) node;
	                try { mapEvent2Queue(new ObservationData(registration, value, path)); }
	                catch (JSONException e) { e.printStackTrace(); }
	            }
	        }
	}

	@Override
	public void onError(Observation observation, Registration registration, Exception error) { }
	

    private Rbt_2_W1_Event mapEvent2Queue(ObservationData data) throws JSONException {
        String endpointName = data.getEndpoint();
        String path = data.getPath();

        //Robot 1 events
        if (endpointName.equals("Robot1")) {
            if (RResources.EVENTofR1.getPath().equals(path)) {
            	 System.out.println("event from robot1 \n \n \n \n \n"+data.getValue()+" \n \n \n \n \n \n \n \n ");
                JSONObject json = new JSONObject(data.getValue(String.class));
                String event = json.getString("event");
                if(event.equals("R1acquireW1")) {
                	W1Coordinator.eventQueue.add(Rbt_2_W1_Event.R1AcquireP1);
                }
                else if(event.equals("R1releaseW1")) {
                	W1Coordinator.eventQueue.add(Rbt_2_W1_Event.R1ReleaseP1);
                }
            }
        }

       //Robot 2 events
        if (endpointName.equals("Robot2")) {
        	 if (RResources.EVENTofR2.getPath().equals(path)) {
        		 System.out.println("event from robot2 \n"+data.getValue()+" \n ");
                 JSONObject json = new JSONObject(data.getValue(String.class));
                 String event = json.getString("event");
                 System.out.println("event from robot2 \n " +event + "\n ");

                 if(event.equals("R2acquireW1")) {
                 	W1Coordinator.eventQueue.add(Rbt_2_W1_Event.R2AcquireP2);
                 }
                 else if(event.equals("R2releaseW1")) {
                 	W1Coordinator.eventQueue.add(Rbt_2_W1_Event.R2ReleaseP2);
                 }
             }
        }

      //Robot 3 events
        if (endpointName.equals("Robot3")) {
        	 if (RResources.EVENTofR3.getPath().equals(path)) {
                 JSONObject json = new JSONObject(data.getValue(String.class));
                 String event = json.getString("event");
               
                 if(event.equals("R3acquireW1")) {
                 	W1Coordinator.eventQueue.add(Rbt_2_W1_Event.R3AcquireP3);
                 }
                 else if(event.equals("R3releaseW1")) {
                 	W1Coordinator.eventQueue.add(Rbt_2_W1_Event.R3ReleaseP3);
                 }
             }
        }
        return null;
    }
}