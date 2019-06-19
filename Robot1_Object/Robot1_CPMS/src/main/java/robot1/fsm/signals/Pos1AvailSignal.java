package robot1.fsm.signals;

import uml4iot.GenericStateMachine.core.BaseSignal;

public class Pos1AvailSignal extends BaseSignal {

	  public Pos1AvailSignal(){}

	  public Pos1AvailSignal(String from, String to) {
	    super(from, to);
	  }

	  public Pos1AvailSignal(String json) {
	    super(json);
	  }
	}