package workbench2.lwm2m;

import org.eclipse.leshan.core.model.ResourceModel;
import org.eclipse.leshan.core.node.LwM2mSingleResource;
import org.eclipse.leshan.server.registration.Registration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ObservationData {

  private static final Logger LOG = LoggerFactory.getLogger(ObservationData.class);

  private Registration registration;
  private LwM2mSingleResource resource;
  private String path;

  public ObservationData(Registration registration, LwM2mSingleResource resource, String path) {
    this.registration = registration;
    this.resource = resource;
    this.path = path;
  }

  public Registration getRegistration() {
    return registration;
  }

  public String getEndpoint() {
    return registration.getEndpoint();
  }

  public Object getValue() {
    return resource.getValue();
  }

  public String getPath() {
    return path;
  }

  public LwM2mSingleResource getResource() {
    return resource;
  }

  public <T> T getValue(Class<T> cls) {
    Object value = resource.getValue();
    ResourceModel.Type type = resource.getType();

    if (cls == Boolean.class && type == ResourceModel.Type.BOOLEAN) {
      return cls.cast(Boolean.valueOf(value.toString()));
    } else if (cls == String.class && type == ResourceModel.Type.STRING) {
      return cls.cast(value.toString());
    } else if (cls == Double.class && type == ResourceModel.Type.FLOAT) {
      return cls.cast(Double.valueOf(value.toString()));
    } else {
      LOG.error("Error in resource data type");
    }

    return null;
  }

}

