package robot2.lwm2m;

import java.util.Map;

import org.eclipse.leshan.client.request.ServerIdentity;
import org.eclipse.leshan.client.resource.LwM2mInstanceEnabler;
import org.eclipse.leshan.client.resource.LwM2mInstanceEnablerFactory;
import org.eclipse.leshan.client.resource.ObjectEnabler;
import org.eclipse.leshan.core.model.ObjectModel;
import org.eclipse.leshan.core.request.ExecuteRequest;
import org.eclipse.leshan.core.response.ExecuteResponse;

public class RobotObject extends ObjectEnabler{
	
    public RobotObject(int id, ObjectModel objectModel, Map<Integer, LwM2mInstanceEnabler> instances, LwM2mInstanceEnablerFactory instanceFactory) {
        super(id, objectModel, instances, instanceFactory);
    }

    @Override
    public synchronized ExecuteResponse execute(ServerIdentity identity, ExecuteRequest request) {
        //ExecuteRequest newReq = new ExecuteRequest(request.getPath().toString(), identity.getPeerAddress().toString() + request.getParameters());
        return super.execute(identity, request);
    }
}
