package robot2.fsm.signals;

import uml4iot.GenericStateMachine.core.BaseSignal;

public class W1Pos2Available extends BaseSignal {

	  public W1Pos2Available(){}

	  public W1Pos2Available(String from, String to) {
	    super(from, to);
	  }

	  public W1Pos2Available(String json) {
	    super(json);
	  }
	}