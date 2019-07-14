package robot2.fsm.signals;

import uml4iot.GenericStateMachine.core.BaseSignal;

public class Pos2Reached extends BaseSignal {

	  public Pos2Reached(){}

	  public Pos2Reached(String from, String to) {
	    super(from, to);
	  }

	  public Pos2Reached(String json) {
	    super(json);
	  }
	
}
