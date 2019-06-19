package workbench2.fsm;

import uml4iot.GenericStateMachine.core.SMReception;

public enum W2SMEvent implements SMReception {
    START,
    STOP,
    R1ACQUIREW2,
    R1RELEASEW2,
    R2ACQUIREW2,
    R2RELEASEW2

}