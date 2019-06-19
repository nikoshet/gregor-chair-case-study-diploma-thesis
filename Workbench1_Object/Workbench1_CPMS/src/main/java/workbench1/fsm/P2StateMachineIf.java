package workbench1.fsm;

import workbench1.events.Rbt_2_W1_Event;

public interface P2StateMachineIf {
    void performActions(P2StateMachine targSt, Rbt_2_W1_Event ev);
    P2StateMachine definePosStates(Rbt_2_W1_Event ev, boolean tmt, P1StateMachine p1CurSt, P3StateMachine p3CurSt);
    boolean getTMTStatus(P1StateMachine p1CurSt, P3StateMachine p3CurSt);
}
