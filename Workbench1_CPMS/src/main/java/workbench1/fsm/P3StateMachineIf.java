package workbench1.fsm;

import workbench1.events.Rbt_2_W1_Event;

public interface P3StateMachineIf {
    //methods
    void performActions(P3StateMachine targSt, Rbt_2_W1_Event ev);
    P3StateMachine definePosStates(Rbt_2_W1_Event ev, boolean tmt, P1StateMachine p1CurSt, P2StateMachine p2CurSt);
    boolean getTMTStatus(P1StateMachine p1CurSt, P2StateMachine p2CurSt);
}
