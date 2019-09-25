package workbench1.fsm;

import org.eclipse.leshan.server.californium.impl.LeshanServer;


import workbench1.lwm2m.ObservationManager;
import workbench1.lwm2m.RegistrationManager;
import workbench1.lwm2m.W1_LwM2mServer;

public class W1_Initializer extends Thread{
	
	public static LeshanServer server;
	public static W1Coordinator w1cooordinator;
	public static RegistrationManager registrationManager;
	public static String[] args;
	 
	public W1_Initializer(LeshanServer server, String[] args) {
		W1_Initializer.server =server;
		W1_Initializer.args =args;
		registrationManager = new RegistrationManager(W1_LwM2mServer.lwServer);

	}
	 @Override
	    public void run() {


		 w1cooordinator = new W1Coordinator(P1StateMachine.Free, P2StateMachine.Free, P3StateMachine.Free);
		 new ObservationManager(W1_LwM2mServer.lwServer, W1Coordinator.eventQueue);

		 try {
			 RegistrationManager.waitDevices("Robot1","Robot2","Robot3");
		 } catch (InterruptedException e) {
			 e.printStackTrace();
		 }


		try { Thread.sleep(2000); }
		catch (InterruptedException e) { e.printStackTrace(); }

		w1cooordinator.start();
	    }
}
