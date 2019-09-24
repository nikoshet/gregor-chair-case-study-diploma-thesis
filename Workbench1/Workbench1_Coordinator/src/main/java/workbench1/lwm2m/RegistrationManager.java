package workbench1.lwm2m;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.leshan.core.observation.Observation;
import org.eclipse.leshan.core.request.ObserveRequest;
import org.eclipse.leshan.core.response.LwM2mResponse;
import org.eclipse.leshan.server.californium.impl.LeshanServer;
import org.eclipse.leshan.server.registration.Registration;
import org.eclipse.leshan.server.registration.RegistrationListener;
import org.eclipse.leshan.server.registration.RegistrationUpdate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import workbench1.fsm.W1_Initializer;

public class RegistrationManager implements RegistrationListener{

	private static final Logger LOG = LoggerFactory.getLogger(RegistrationManager.class);

	  private static Map<String, Registration> registrations = new HashMap<>();
	  //public static ArrayList<String > registrations = new ArrayList();
	  public LeshanServer server;
	  
	  public RegistrationManager(LeshanServer server) {
		  this.server=server;
	      server.getRegistrationService().addListener(this);
	      System.out.println(" \n RegistrationListener Added \n ");
	  }

	  public static void waitDevices(String... devices) throws InterruptedException {
	    LOG.info("Waiting for devices {}", devices.toString());
		  for (String device : devices) {
			  while (true) {
				  synchronized (registrations) {
					  if (registrations.containsKey(device)) {
						  break;
					  }
					  registrations.wait();
				  }
			  }
		  }
	  }

	@Override
	public  void registered(Registration reg, Registration previousReg, Collection<Observation> previousObsersations) {
		System.out.println("new registration : " + reg.getEndpoint());
		synchronized (registrations) {
			registrations.put(reg.getEndpoint(), reg);
			registrations.notifyAll();
			if(reg.getEndpoint().equals("Robot1")) {
				ObserveRequest observe1 = new ObserveRequest("/20000/0/16");
				try {
					LwM2mResponse response1= W1_Initializer.server.send(reg, observe1);
					System.out.println("device response: " + response1);
				}
				catch (InterruptedException e) { e.printStackTrace(); }
			}
			else if(reg.getEndpoint().equals("Robot2")) {
				ObserveRequest observe0 = new ObserveRequest("/20001/0/16");
				System.out.println(observe0.getPath());
				try {
					LwM2mResponse response0= W1_Initializer.server.send(reg, observe0);
					System.out.println("device response: " + response0);
				}
				catch (InterruptedException e) { e.printStackTrace(); }
			}
			else if(reg.getEndpoint().equals("Robot3")) {
				ObserveRequest observe0 = new ObserveRequest("/20002/0/16");
				System.out.println(observe0.getPath());
				try {
					LwM2mResponse response0= W1_Initializer.server.send(reg, observe0);
					System.out.println("device response: " + response0);
				}
				catch (InterruptedException e) {e.printStackTrace(); }
			}
		}

	}

	@Override
	public void unregistered(Registration reg, Collection<Observation> observations, boolean expired, Registration newReg) {
	  	LOG.warn("\n \n SOS \n \n " +
					"\n \n "+ reg.getEndpoint() +" unregistered \n \n"+
					"\n \n SOS \n \n");
		registrations.remove(reg.getEndpoint());
	}

	@Override
	public void updated(RegistrationUpdate update, Registration updatedReg, Registration previousReg) { }

}