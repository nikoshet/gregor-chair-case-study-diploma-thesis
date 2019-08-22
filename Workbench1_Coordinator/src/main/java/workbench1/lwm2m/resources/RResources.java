package workbench1.lwm2m.resources;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;


public enum RResources implements ResourceIf {
	 
	  EVENTofR1("/20000/0/16"),
	  EVENTofR2("/20001/0/16"), 
	  EVENTofR3("/20002/0/16");
	  private static final Map<String, RResources> lookup = new HashMap<>();

	  static {
	    for (RResources s : EnumSet.allOf(RResources.class)) {
	      lookup.put(s.getPath(), s);
	    }
	  }

	  private String path;

	  RResources(String path) {
	    this.path = path;
	  }

	  public String getPath() {
	    return path;
	  }

	  public static RResources get(String path) {
	    return lookup.get(path);
	  }

	}
