package robot2.fsm;

import uml4iot.GenericStateMachine.core.SMReception;

public enum R2SMEvent implements SMReception {
    START,
    STOP,
    W1POS2AVAILABLE,
    SUBASS2_1_COMPLETED,
    POS2REACHED,
    W2AVAILABLE,
    SUBASSW2COMPLETED,
    POS1REACHED,
    SUBASS2_2_COMPLETED

}