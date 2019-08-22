package robot2.fsm.signals;

import uml4iot.GenericStateMachine.core.BaseSignal;

public class SubAss2_2Completed extends BaseSignal {

    public SubAss2_2Completed(){}

    public SubAss2_2Completed(String from, String to) {
        super(from, to);
    }

    public SubAss2_2Completed(String json) {
        super(json);
    }
}