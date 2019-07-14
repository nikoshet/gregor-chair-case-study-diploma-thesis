package workbench2.fsm;


import org.eclipse.leshan.server.californium.impl.LeshanServer;

import workbench2.lwm2m.ObservationManager;
import workbench2.lwm2m.RegistrationManager;
import workbench2.lwm2m.W2_LwM2mServer;


public class W2Application extends Thread {


	public static LeshanServer server;
	public RegistrationManager registrations;
	public static W2Coordinator w2controller;
	public static String[] args;
	 
	public W2Application(LeshanServer server,String[] args) {
		this.server=server;
		this.args=args;
		this.registrations = new RegistrationManager(server);
	}
	
	
	 @Override
	    public void run() {

			 // Wait for clients to connect
			 try {
				registrations.waitDevices("Robot1" , "Robot2");
			 } catch (InterruptedException e) {
				 e.printStackTrace();
			 }

		 	System.out.println("All robots have been connected");

	        w2controller = new W2Coordinator();
	        new Thread (w2controller).start();
	        new ObservationManager(W2_LwM2mServer.lwServer, w2controller.itsMsgQ);
	        
	        try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
}
