package workbench2.fsm.events;

import uml4iot.GenericStateMachine.core.SMReception;

public enum W2Event implements SMReception {
    R1AcquireW2,
    R1ReleaseW2,
    R2AcquireW2,
    R2ReleaseW2;

    public int eventSource;

    W2Event(int source){
        eventSource = source;
    }

    W2Event() {

    }
}
