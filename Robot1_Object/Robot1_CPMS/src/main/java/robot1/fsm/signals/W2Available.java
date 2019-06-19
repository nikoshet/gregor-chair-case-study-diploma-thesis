package robot1.fsm.signals;

import uml4iot.GenericStateMachine.core.BaseSignal;

public class W2Available extends BaseSignal {

	  public W2Available(){}

	  public W2Available(String from, String to) {
	    super(from, to);
	  }

	  public W2Available(String json) {
	    super(json);
	  }
	}