package workbench1.fsm;

import workbench1.events.Rbt_2_W1_Event;

public interface P1StateMachineIf {
    //methods
    void performActions(P1StateMachine targSt, Rbt_2_W1_Event ev);
    P1StateMachine definePosStates(Rbt_2_W1_Event ev, boolean tmt, P2StateMachine p2CurSt, P3StateMachine p3CurSt);
    boolean getTMTStatus(P2StateMachine p2CurSt, P3StateMachine p3CurSt);
}
