package workbench2.lwm2m;

import java.util.Map;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uml4iot.GenericStateMachine.core.MessageQueue;
import uml4iot.GenericStateMachine.core.SMReception;
import workbench2.fsm.SignalDetector;
import workbench2.fsm.W2Coordinator;
import workbench2.fsm.W2SMEvent;
import workbench2.fsm.events.W2Event;
import workbench2.lwm2m.resources.RResources;


public class ObservationManager implements ObservationListener{

	private static final Logger LOG = LoggerFactory.getLogger(ObservationManager.class);

	public static SignalDetector signaldetect;

	private MessageQueue<SMReception> queue;
	public ObservationManager(LeshanServer server , MessageQueue<SMReception> eventQueue) {
		server.getObservationService().addListener(this);
		queue = eventQueue;
	}

	
	
	@Override
	public void newObservation(Observation observation, Registration registration) {
		// TODO Auto-generated method stub
		System.out.println("Observing resource " + observation.getPath() + " from client "
	            + registration.getEndpoint());
	}

	@Override
	public void cancelled(Observation observation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public synchronized void onResponse(Observation observation, Registration registration, ObserveResponse response) {
		// TODO Auto-generated method stub
		 //System.out.println("New notification from client " + observation.getRegistrationId()+ ": "
	     //           + response.getContent() + response.getObservation().getPath().toString());
		// System.out.println( observation.getContext());
		LOG.info("\n \n \n \n New notification from client " + observation.getRegistrationId()+ ": "
				+ response.getContent() + response.getObservation().getPath().toString()+"\n \n \n \n ");


		if (registration != null) {
	            String path = response.getObservation().getPath().toString();

	            LwM2mNode node = response.getContent();
	            //If there are multiple values
	            if (node instanceof LwM2mObjectInstance) {
	                LwM2mObjectInstance instance = (LwM2mObjectInstance) node;
	                Map<Integer, LwM2mResource> resources = instance.getResources();

	                for (Map.Entry<Integer, LwM2mResource> entry : resources.entrySet()) {
	                    LwM2mSingleResource value = (LwM2mSingleResource) entry.getValue();
	                    String fullPath = path + '/' + value.getId();
	                    try {
							mapEvent(new ObservationData(registration, value, fullPath));
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	                    /*ObservationData data= new ObservationData(registration, value, fullPath);
	                    String endpointName = data.getEndpoint();
	                    String path1 = data.getPath();
	                    System.out.println(R1Resources.EVENT.getPath());
	                    
	                        if (R1Resources.EVENT.getPath().equals(path)) {
	                            JSONObject json = new JSONObject(data.getValue(String.class));
	                            String event = json.getString("event");
	                            System.out.println(event);
	                            W1Controller.eventQueue.add(W2Event.R1AcquireP1);
	                        }*/
	                   
	                }
	            }
	            //If it's only one value
	            else if (node instanceof LwM2mSingleResource) {
	                LwM2mSingleResource value = (LwM2mSingleResource) node;
	                try {
						mapEvent(new ObservationData(registration, value, path));
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	                /*ObservationData data= new ObservationData(registration, value, path);
                    String endpointName = data.getEndpoint();
                    String path1 = data.getPath();
                    System.out.println(R1Resources.EVENT.getPath());
                    
                        if (R1Resources.EVENT.getPath().equals(path)) {
                            JSONObject json = new JSONObject(data.getValue(String.class));
                            String event = json.getString("event");
                            System.out.println(event);
                            W1Controller.eventQueue.add(W2Event.R1AcquireP1);
                        }*/
	            }
	        }
		 notifyAll();
	}

	@Override
	public void onError(Observation observation, Registration registration, Exception error) {
		// TODO Auto-generated method stub
		
	}
	
/*	private void addToQueue(ObservationData data) {
		W2Event event = mapEvent(data);
        if (event != null) {
            queue.add(event);
        }

    }*/

    private W2Event mapEvent(ObservationData data) throws JSONException {
        String endpointName = data.getEndpoint();
        String path = data.getPath();

			//W2 R1 events
			if (endpointName.equals("Robot1")) {
				if (RResources.EVENTofR1.getPath().equals(path)) {
					JSONObject json = new JSONObject(data.getValue(String.class));
					String event = json.getString("event");
					System.out.println(event);
					W2Coordinator.LOGGER.severe(json.toString());
					if(event.equals("R1acquireW2")) {
						//W2Coordinator.eventQueue.add(W2Event.R1AcquireW2);

						System.out.println("\n \n \n \n \n \n robot1 send acquire to w2 \n \n \n \n \n \n ");
						this.queue.add(W2SMEvent.R1ACQUIREW2 );
						//SignalDetector.msgQ.add(new R1ACQUIREW2());
					}
					else if(event.equals("R1releaseW2")) {
						this.queue.add(W2SMEvent.R1RELEASEW2 );
						//SignalDetector.msgQ.add(new R1RELEASEW2());
					}

				}
			}

			//W2 R2 events
			if (endpointName.equals("Robot2")) {
				if (RResources.EVENTofR2.getPath().equals(path)) {
					JSONObject json = new JSONObject(data.getValue(String.class));
					String event = json.getString("event");
					System.out.println(event);
					W2Coordinator.LOGGER.severe(json.toString());
					if(event.equals("R2acquireW2")) {
						this.queue.add(W2SMEvent.R2ACQUIREW2 );
						//SignalDetector.msgQ.add(new R2ACQUIREW2());
					}
					else if(event.equals("R2releaseW2")) {
						this.queue.add(W2SMEvent.R2RELEASEW2 );
						//SignalDetector.msgQ.add(new R2RELEASEW2());
					}

				}
			}

        return null;
    }
    

}

