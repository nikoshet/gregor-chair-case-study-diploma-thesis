package robot3.fsm;

import uml4iot.GenericStateMachine.core.SMReception;

public enum R1SMEvent implements SMReception {
    START,
    STOP,
    W1POS1AVAILABLE,
    SUBASS1COMPLETED,
    POS2REACHED,
    W2AVAILABLE,
    SUBASSW2COMPLETED,
    POS1REACHED

}