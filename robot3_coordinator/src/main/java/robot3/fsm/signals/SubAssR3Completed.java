package robot3.fsm.signals;

import uml4iot.GenericStateMachine.core.BaseSignal;

public class SubAssR3Completed extends BaseSignal {

	  public SubAssR3Completed(){}

	  public SubAssR3Completed(String from, String to) {
	    super(from, to);
	  }

	  public SubAssR3Completed(String json) {
	    super(json);
	  }
	}