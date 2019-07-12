package workbench1.lwm2m.leshanWrapper;


import java.sql.Timestamp;
import org.eclipse.leshan.core.request.AbstractDownlinkRequest;
import org.eclipse.leshan.core.request.ContentFormat;
import org.eclipse.leshan.core.request.ExecuteRequest;
import org.eclipse.leshan.core.request.ObserveRequest;
import org.eclipse.leshan.core.response.LwM2mResponse;
import org.eclipse.leshan.server.californium.impl.LeshanServer;
import org.eclipse.leshan.server.registration.Registration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LeshanPort {
  private static final Logger LOG = LoggerFactory.getLogger(LeshanPort.class);

  protected static LeshanServer server;
  protected static String endpoint;


  public LeshanPort(LeshanServer server, String endpoint) {
    LeshanPort.server = server;
    LeshanPort.endpoint = endpoint;
  }

  public void execute(LeshanExecuteIf e) throws InterruptedException {
    execute(e, String.format("{ \"sender\": \"SERVER\", \"receiver\": \"ROBOT\", \"timestamp\": \"%s\"}", new Timestamp(System.currentTimeMillis())));
  }

  public void execute(LeshanExecuteIf e, String arguments) throws InterruptedException {
    LOG.debug("Sending execute to " + endpoint + " " + e.getPath());
    ExecuteRequest request = new ExecuteRequest(e.getPath(), arguments);
    if (sendRequest(request) == null){
      LOG.error("Error sending execute to " + endpoint + " " + e.getPath());
    }

  }

  public void observe(LeshanAttributeIf resource) {
    ObserveRequest request = new ObserveRequest(ContentFormat.JSON, resource.getPath());
    try {
      sendRequest(request);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }


  protected <T extends LwM2mResponse> T sendRequest(AbstractDownlinkRequest<T> request) throws InterruptedException {
    Registration registration = server.getRegistrationService().getByEndpoint(endpoint);
    T cResponse = server.send(registration, request);
    if (cResponse == null) {
      LOG.warn("Request timed out.");
    } else {
      return cResponse;
    }
    return null;
  }
}
