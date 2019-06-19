package workbench2.lwm2m;

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

import workbench2.ConfigurationUtils;
import workbench2.fsm.W2Application;
import workbench2.lwm2m.ports.Robot1Port;
import workbench2.lwm2m.ports.Robot2Port;


@Component
public class W2_LwM2mServer {
	//--------------- leshan server variables --------------------------------------------------//
	
		private static LeshanServerBuilder serverBuilder;
		public static LeshanServer lwServer;
		private static String localAddress;
		private static String securelocalAddress;
		private static int securelocalPort;
		private static int localPort;
		private static LwM2mModelProvider modelProvider;
		public static Robot1Port robot1port;
		public static Robot2Port robot2port;
		public static W2Application w2Application;
		//-------------- constructor ---------------------------------------------------//
		

		/*public W1_LwM2mServer() 
		{

			//initServer(localAddress, localPort, securelocalAddress, securelocalPort);
			//addRegistationService();
			
		}
		*/
		//--------------------- functions --------------------------------------------//
		public void overallInit() {
			setLocalAddress(ConfigurationUtils.W2_HOSTNAME);
			setLocalPort(ConfigurationUtils.W2_COAP_PORT);
			setSecurelocalAddress(ConfigurationUtils.W2_HOSTNAME);
			setSecurelocalPort(ConfigurationUtils.W2_COAPS_PORT);
			initServer();
			addRegistationService();
			setW2Application(new W2Application(W2_LwM2mServer.lwServer, null));
			getW2Application().start();
		}
		
		
		public void initServer() {
			  	serverBuilder = new LeshanServerBuilder();
			  	serverBuilder.setLocalAddress(localAddress, localPort);
			  	serverBuilder.setLocalSecureAddress(securelocalAddress, securelocalPort);
			  	serverBuilder.setEncoder(new DefaultLwM2mNodeEncoder());
		        LwM2mNodeDecoder decoder = new DefaultLwM2mNodeDecoder();
		        serverBuilder.setDecoder(decoder);
		        
		        modelProvider = new LwM2mModelProvider() {
		            @Override
		            public LwM2mModel getObjectModel(Registration client) {
		            	  InputStream defaultSpec = W2_LwM2mServer.class.getResourceAsStream("/models/oma-objects-spec.json");
			  	          InputStream gregorSpec = W2_LwM2mServer.class.getResourceAsStream("/models/W2_Objects.json");
			  	          List<ObjectModel> models = ObjectLoader.loadJsonStream(defaultSpec);
			  	          models.addAll(ObjectLoader.loadJsonStream(gregorSpec));
		                  return new LwM2mModel(models);
		            }
		        };

		        serverBuilder.setObjectModelProvider(modelProvider);
		       
		        lwServer = serverBuilder.build();
		        
		        lwServer.start();
		        
		        robot1port= new Robot1Port(lwServer, "Robot1");
				robot2port = new Robot2Port(lwServer, "Robot2");
				
				
		}
		
		public RegistrationManager addRegistationService() {
			return new RegistrationManager(W2_LwM2mServer.lwServer);
			
		}
		
		
		//-----------------setters getters -----------------------------------------
		

		public static W2Application getW2Application() {
			return w2Application;
		}


		public static void setW2Application(W2Application w2Application) {
			W2_LwM2mServer.w2Application = w2Application;
		}

		
		public static String getLocalAddress() {
			return localAddress;
		}

		public void setLocalAddress(String localAddress) {
			W2_LwM2mServer.localAddress = localAddress;
		}

		public static String getSecurelocalAddress() {
			return securelocalAddress;
		}

		public void setSecurelocalAddress(String securelocalAddress) {
			W2_LwM2mServer.securelocalAddress = securelocalAddress;
		}

		public static int getSecurelocalPort() {
			return securelocalPort;
		}

		public void setSecurelocalPort(int securelocalPort) {
			W2_LwM2mServer.securelocalPort = securelocalPort;
		}

		public static int getLocalPort() {
			return localPort;
		}

		public void setLocalPort(int localPort) {
			W2_LwM2mServer.localPort = localPort;
		}

	}
