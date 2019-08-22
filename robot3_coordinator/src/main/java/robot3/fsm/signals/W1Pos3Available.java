package robot3.fsm.signals;

import uml4iot.GenericStateMachine.core.BaseSignal;

public class W1Pos3Available extends BaseSignal {

	  public W1Pos3Available(){}

	  public W1Pos3Available(String from, String to) {
	    super(from, to);
	  }

	  public W1Pos3Available(String json) {
	    super(json);
	  }
	}