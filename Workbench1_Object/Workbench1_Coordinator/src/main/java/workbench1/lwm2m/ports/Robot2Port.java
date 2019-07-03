package workbench1.lwm2m.ports;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.leshan.server.californium.impl.LeshanServer;

import workbench1.lwm2m.leshanWrapper.LeshanAttributeIf;
import workbench1.lwm2m.leshanWrapper.LeshanExecuteIf;
import workbench1.lwm2m.leshanWrapper.LeshanPort;
import workbench1.lwm2m.leshanWrapper.LeshanUriIf;


public class Robot2Port  extends LeshanPort {

	  public Robot2Port(LeshanServer server, String endpoint) {
	    super(server, endpoint);

	  }

	  public enum Execute implements LeshanExecuteIf {
	    POS2AVAIL("/20001/0/3"),
	    W2AVAIL("/20001/0/4");

	    private static final Map<String, Execute> lookup = new HashMap<>();

	    static {
	      for (Execute s : EnumSet.allOf(Execute.class)) {
	        lookup.put(s.getPath(), s);
	      }
	    }

	    private String path;

	    Execute(String path) {
	      this.path = path;
	    }

	    public LeshanUriIf get(String path) {
	      return lookup.get(path);
	    }

	    public String getPath() {
	      return path;
	    }
	  }

	  public enum Attribute implements LeshanAttributeIf {
	    EVENT("/20000/0/16");
	    private static final Map<String, Attribute> lookup = new HashMap<>();

	    static {
	      for (Attribute s : EnumSet.allOf(Attribute.class)) {
	        lookup.put(s.getPath(), s);
	      }
	    }

	    private String path;

	    Attribute(String path) {
	      this.path = path;
	    }

	    public Attribute get(String path) {
	      return lookup.get(path);
	    }

	    public String getPath() {
	      return path;
	    }
	  }

	  public enum Events {

	  }


	}
