package robot1.lwm2m;

import java.io.InputStream;
import java.util.List;
import java.util.Random;

import org.eclipse.leshan.client.californium.LeshanClient;
import org.eclipse.leshan.client.californium.LeshanClientBuilder;
import org.eclipse.leshan.client.object.Server;
import org.eclipse.leshan.client.resource.LwM2mObjectEnabler;
import org.eclipse.leshan.client.resource.ObjectsInitializer;
import org.eclipse.leshan.core.model.LwM2mModel;
import org.eclipse.leshan.core.model.ObjectLoader;
import org.eclipse.leshan.core.model.ObjectModel;
import org.eclipse.leshan.core.request.BindingMode;

import static org.eclipse.leshan.LwM2mId.*;
import static org.eclipse.leshan.client.object.Security.*;

import robot1.ConfigurationUtils;

public abstract class AbstractDevice {
    String endpoint, localAddress, secureLocalAddress, serverURI_W1,serverURI_W2,serverURIofSIM;
    int localPort, secureLocalPort;
    boolean needBootstrap;
    byte[] pskIdentity, pskKey;


    public AbstractDevice(String endpoint, String[] args) {

        this.endpoint = endpoint;
        this.localPort = (new Random()).nextInt(60000 - 20000) + 20000;
        this.serverURI_W1 = ConfigurationUtils.W1_COAP_SERVER;
        this.serverURI_W2 = ConfigurationUtils.W2_COAP_SERVER;

    }

    public void init() {
        List<LwM2mObjectEnabler> enablersW1 = this.createObjectsW1();
        List<LwM2mObjectEnabler> enablersW2 = this.createObjectsW2();

        //NetworkConfig coapConfig;
        //coapConfig = LeshanClientBuilder.createDefaultNetworkConfig();
        //coapConfig.set(NetworkConfig.Keys.NOTIFICATION_CHECK_INTERVAL_COUNT,1000);

        // Create client
        LeshanClientBuilder builderW1 = new LeshanClientBuilder(endpoint);
        builderW1.setObjects(enablersW1);
        // builderW1.setCoapConfig(coapConfig);

        final LeshanClient client2W1 = builderW1.build();

        // Start the client
          client2W1.start();
          
          // Create client
          LeshanClientBuilder builderW2 = new LeshanClientBuilder(endpoint);
          builderW2.setObjects(enablersW2);
         // builderW2.setCoapConfig(coapConfig);
          final LeshanClient client2W2 = builderW2.build();
          // Start the client
          client2W2.start();

          // De-register on shutdown and stop client.
          Runtime.getRuntime().addShutdownHook(new Thread() {
              @Override
              public void run() {
                  client2W1.destroy(true); // send de-registration request before destroy
                  client2W2.destroy(true);
                  
              }
          });
    }

    protected List<LwM2mObjectEnabler>  createObjectsW1() {

        ObjectsInitializer initializer = getObjectInitializerW1();
        return getEnablers(initializer);
    }
      
    protected List<LwM2mObjectEnabler>  createObjectsW2() {

        ObjectsInitializer initializer = getObjectInitializerW2();
        return getEnablers(initializer);
    }
      

    protected List<LwM2mObjectEnabler> getEnablers(ObjectsInitializer initializer) {
        List<LwM2mObjectEnabler> enablers = initializer.create(SECURITY, SERVER);
        return enablers;
    }

    protected ObjectsInitializer getObjectInitializerW1(){
        // Initialize object list
        ObjectsInitializer initializer;
        LwM2mModel model = getLwM2mModel();
        if (model==null){
            initializer = new ObjectsInitializer();
        }
        else {
            initializer = new ObjectsInitializer(model);
        }

        if (needBootstrap) {
            if (pskIdentity == null)
                initializer.setInstancesForObject(SECURITY, noSecBootstap(serverURI_W1));
            else
                initializer.setInstancesForObject(SECURITY, pskBootstrap(serverURI_W1, pskIdentity, pskKey));
        } else {
            if (pskIdentity == null) {
                initializer.setInstancesForObject(SECURITY, noSec(serverURI_W1, 123));
                initializer.setInstancesForObject(SERVER, new Server(123, 30, BindingMode.U, false));
            } else {
                initializer.setInstancesForObject(SECURITY, psk(serverURI_W1, 123, pskIdentity, pskKey));
                initializer.setInstancesForObject(SERVER, new Server(123, 30, BindingMode.U, false));
            }
        }
        return initializer;
    }
      
    protected ObjectsInitializer getObjectInitializerW2(){
        // Initialize object list
        ObjectsInitializer initializer;
        LwM2mModel model = getLwM2mModel();
        if (model==null){
            initializer = new ObjectsInitializer();
        }
        else {
            initializer = new ObjectsInitializer(model);
        }

        if (needBootstrap) {
            if (pskIdentity == null)
                initializer.setInstancesForObject(SECURITY, noSecBootstap(serverURI_W2));
            else
                initializer.setInstancesForObject(SECURITY, pskBootstrap(serverURI_W2, pskIdentity, pskKey));
        } else {
            if (pskIdentity == null) {
                initializer.setInstancesForObject(SECURITY, noSec(serverURI_W2, 123));
                initializer.setInstancesForObject(SERVER, new Server(123, 30, BindingMode.U, false));
            } else {
                initializer.setInstancesForObject(SECURITY, psk(serverURI_W2, 123, pskIdentity, pskKey));
                initializer.setInstancesForObject(SERVER, new Server(123, 30, BindingMode.U, false));
            }
        }
        return initializer;
    }

    protected LwM2mModel getLwM2mModel() {
        InputStream defaultSpec = this.getClass().getResourceAsStream("/models/oma-objects-spec.json");
        InputStream r1Spec = this.getClass().getResourceAsStream("/models/R1_Objects.json");
        List<ObjectModel> models = ObjectLoader.loadJsonStream(defaultSpec);
        models.addAll(ObjectLoader.loadJsonStream(r1Spec));
        return new LwM2mModel(models);
    }
}