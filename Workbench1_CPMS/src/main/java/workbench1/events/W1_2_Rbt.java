package workbench1.events;


public enum W1_2_Rbt {
    P1Available(0),//W1POS1AVAILABLE
    P2Available(1),//
    P3Available(2);

    int eventDestination;

    W1_2_Rbt(int dest){
        eventDestination = dest;
    }
}
