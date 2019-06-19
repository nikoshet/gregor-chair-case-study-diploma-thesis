package workbench1.fsm;

import org.eclipse.leshan.server.californium.impl.LeshanServer;

import workbench1.lwm2m.ObservationManager;
import workbench1.lwm2m.RegistrationManager;
import workbench1.lwm2m.W1_LwM2mServer;
import workbench1.lwm2m.ports.Robot1Port;
import workbench1.lwm2m.ports.Robot2Port;
import workbench1.lwm2m.ports.Robot3Port;

public class W1_App extends Thread{
	
	
 
	public static LeshanServer server;/*
	public static Robot1Port robot1port;
	public static Robot2Port robot2port;
	public static Robot3Port robot3port;*/
	public RegistrationManager registrations;
	public static W1Controller w1controller;
	public static String[] args;
	 
	public W1_App(LeshanServer server,String[] args) {
		this.server=server;
		this.args=args;
	}
	
	
	 @Override
	    public void run() {
	        w1controller = new W1Controller(P1StateMachine.Free, P2StateMachine.Free, P3StateMachine.Free);
	        new ObservationManager(W1_LwM2mServer.lwServer, W1Controller.eventQueue);
	        
	        try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        w1controller.start();
	    }

}
