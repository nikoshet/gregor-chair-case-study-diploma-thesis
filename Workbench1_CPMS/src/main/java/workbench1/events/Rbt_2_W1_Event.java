package workbench1.events;

public enum Rbt_2_W1_Event implements W1Event{
    R1AcquireP1(0),
    R1ReleaseP1(0),
    R2AcquireP2(1),
    R2ReleaseP2(1),
    R3AcquireP3(2),
    R3ReleaseP3(2);

    public int eventSource;

    Rbt_2_W1_Event(int source){
        eventSource = source;
    }

}
