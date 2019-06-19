package robot1.fsm.signals;

import uml4iot.GenericStateMachine.core.BaseSignal;

public class SubAss1Completed extends BaseSignal {

	  public SubAss1Completed(){}

	  public SubAss1Completed(String from, String to) {
	    super(from, to);
	  }

	  public SubAss1Completed(String json) {
	    super(json);
	  }
	}