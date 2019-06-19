package robot2.lwm2m;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.leshan.client.resource.LwM2mInstanceEnabler;
import org.eclipse.leshan.client.resource.LwM2mInstanceEnablerFactory;
import org.eclipse.leshan.client.resource.LwM2mObjectEnabler;
import org.eclipse.leshan.client.resource.ObjectsInitializer;
import org.eclipse.leshan.client.resource.SimpleInstanceEnabler;
import org.eclipse.leshan.core.model.ObjectModel;

import robot2.fsm.Robot2Ctrl;

public class Robot extends AbstractDevice{

	public RobotInstance robot2instance;
	Robot2Ctrl controller;
	
	 protected LwM2mInstanceEnablerFactory defaultFactory = new LwM2mInstanceEnablerFactory() {
	        @Override
	        public LwM2mInstanceEnabler create(ObjectModel model) {
	            SimpleInstanceEnabler simpleInstanceEnabler = new SimpleInstanceEnabler();
	            simpleInstanceEnabler.setObjectModel(model);
	            return simpleInstanceEnabler;
	        }
	    };
	    
	public Robot(String endpoint, Robot2Ctrl controller, String[] args) {
        super(endpoint, args);
        this.controller = controller;
        robot2instance = new RobotInstance(controller, endpoint);
    }
	
	
	 @Override
	    public void init() {
	        super.init();
	        //new Thread(silo).start();
	    }

	    @Override
	    protected List<LwM2mObjectEnabler> getEnablers(ObjectsInitializer initializer) {
	        List<LwM2mObjectEnabler> superEnablers = super.getEnablers(initializer );


	        Map<Integer, LwM2mInstanceEnabler> instances = new HashMap<>();
	        instances.put(0, robot2instance);
	        ObjectModel robot1Model = getLwM2mModel().getObjectModel(RobotInstance.modelId);
	        RobotObject robot2Object = new RobotObject(RobotInstance.modelId, robot1Model, instances, defaultFactory);

	        superEnablers.add(robot2Object);
	        return superEnablers;
	    }

	   

	

}
