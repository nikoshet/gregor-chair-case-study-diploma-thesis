package robot2.fsm.signals;

import uml4iot.GenericStateMachine.core.BaseSignal;

public class SubAss2_1Completed extends BaseSignal {

	  public SubAss2_1Completed(){}

	  public SubAss2_1Completed(String from, String to) {
	    super(from, to);
	  }

	  public SubAss2_1Completed(String json) {
	    super(json);
	  }
	}