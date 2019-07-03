package robot2.fsm.signals;

import uml4iot.GenericStateMachine.core.BaseSignal;

public class SubAssW2Completed extends BaseSignal {

	  public SubAssW2Completed(){}

	  public SubAssW2Completed(String from, String to) {
	    super(from, to);
	  }

	  public SubAssW2Completed(String json) {
	    super(json);
	  }
	}