package robot1.fsm.signals;

import uml4iot.GenericStateMachine.core.BaseSignal;

public class Pos1Reached extends BaseSignal {

	  public Pos1Reached(){}

	  public Pos1Reached(String from, String to) {
	    super(from, to);
	  }

	  public Pos1Reached(String json) {
	    super(json);
	  }
	}