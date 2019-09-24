package workbench1.lwm2m;

import java.io.InputStream;
import java.util.List;
import org.eclipse.leshan.core.model.LwM2mModel;
import org.eclipse.leshan.core.model.ObjectLoader;
import org.eclipse.leshan.core.model.ObjectModel;
import org.eclipse.leshan.core.node.codec.DefaultLwM2mNodeDecoder;
import org.eclipse.leshan.core.node.codec.DefaultLwM2mNodeEncoder;
import org.eclipse.leshan.core.node.codec.LwM2mNodeDecoder;
import org.eclipse.leshan.server.californium.LeshanServerBuilder;
import org.eclipse.leshan.server.californium.impl.LeshanServer;
import org.eclipse.leshan.server.model.LwM2mModelProvider;
import org.eclipse.leshan.server.registration.Registration;
import org.springframework.stereotype.Component;
import workbench1.ConfigurationUtils;
import workbench1.fsm.W1_Initializer;
import workbench1.lwm2m.ports.Robot1Port;
import workbench1.lwm2m.ports.Robot2Port;
import workbench1.lwm2m.ports.Robot3Port;

@Component
public class W1_LwM2mServer {
	
	//--------------- leshan server variables --------------------------------------------------//
	
	private static LeshanServerBuilder serverBuilder;
	public static LeshanServer lwServer;
	private static String localAddress;
	private static String securelocalAddress;
	private static int securelocalPort;
	private static int localPort;
	private static LwM2mModelProvider modelProvider;
	private static W1_Initializer w1_Initializer;
	public static Robot1Port robot1port;
	public static Robot2Port robot2port;
	public static Robot3Port robot3port;
	
	//-------------- constructor ---------------------------------------------------//
	
	/*public W1_LwM2mServer() 
	{

		//initServer(localAddress, localPort, securelocalAddress, securelocalPort);
		//addRegistationService();
		
	}
	*/
	//--------------------- functions --------------------------------------------//
	public void overallInit() {
		setLocalAddress(ConfigurationUtils.W1_HOSTNAME);
		setLocalPort(ConfigurationUtils.W1_COAP_PORT);
		setSecurelocalAddress(ConfigurationUtils.W1_HOSTNAME);
		setSecurelocalPort(ConfigurationUtils.W1_COAPS_PORT);
		initServer();
		setW1_Initializer(new W1_Initializer(W1_LwM2mServer.lwServer, null));
		getW1_Initializer().start();
		//addRegistationService();
	}
	
	public static W1_Initializer getW1_Initializer() {
		return w1_Initializer;
	}

	public static void setW1_Initializer(W1_Initializer w1_Initializer) {
		W1_LwM2mServer.w1_Initializer = w1_Initializer;
	}

	private void initServer() {
		  	serverBuilder = new LeshanServerBuilder();
		  	serverBuilder.setLocalAddress(localAddress, localPort);
		  	serverBuilder.setLocalSecureAddress(securelocalAddress, securelocalPort);
		  	serverBuilder.setEncoder(new DefaultLwM2mNodeEncoder());
	        LwM2mNodeDecoder decoder = new DefaultLwM2mNodeDecoder();
	        serverBuilder.setDecoder(decoder);
	        modelProvider = new LwM2mModelProvider() {
	            @Override
	            public LwM2mModel getObjectModel(Registration client) {
	            	  InputStream defaultSpec = W1_LwM2mServer.class.getResourceAsStream("/models/oma-objects-spec.json");
		  	          InputStream gregorSpec = W1_LwM2mServer.class.getResourceAsStream("/models/W1_Objects.json");
		  	          List<ObjectModel> models = ObjectLoader.loadJsonStream(defaultSpec);
		  	          models.addAll(ObjectLoader.loadJsonStream(gregorSpec));
	                  return new LwM2mModel(models);
	            }
	        };

	        serverBuilder.setObjectModelProvider(modelProvider);

			// Create CoAP Config
		//	NetworkConfig coapConfig;
		//	coapConfig = LeshanServerBuilder.createDefaultNetworkConfig();
		//	coapConfig.set(NetworkConfig.Keys.NOTIFICATION_CHECK_INTERVAL_COUNT,1000);
		//	serverBuilder.setCoapConfig(coapConfig);

	        lwServer = serverBuilder.build();
	        lwServer.start();
	        robot1port= new Robot1Port(lwServer, "Robot1");
			robot2port = new Robot2Port(lwServer, "Robot2");
			robot3port= new Robot3Port(lwServer, "Robot3");
	}
	
	public RegistrationManager addRegistationService() {
		return new RegistrationManager(W1_LwM2mServer.lwServer);
		
	}

	//-----------------setters getters -----------------------------------------
	
	public static String getLocalAddress() {
		return localAddress;
	}

	public void setLocalAddress(String localAddress) {
		W1_LwM2mServer.localAddress = localAddress;
	}

	public static String getSecurelocalAddress() {
		return securelocalAddress;
	}

	public void setSecurelocalAddress(String securelocalAddress) {
		W1_LwM2mServer.securelocalAddress = securelocalAddress;
	}

	public static int getSecurelocalPort() {
		return securelocalPort;
	}

	public void setSecurelocalPort(int securelocalPort) {
		W1_LwM2mServer.securelocalPort = securelocalPort;
	}

	public static int getLocalPort() {
		return localPort;
	}

	public void setLocalPort(int localPort) {
		W1_LwM2mServer.localPort = localPort;
	}

}