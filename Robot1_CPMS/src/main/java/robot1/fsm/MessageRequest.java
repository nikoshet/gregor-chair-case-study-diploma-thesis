package robot1.fsm;


public class MessageRequest {

    public enum Source{
        R1, R2, R3, W1_POS1, W1_POS2, W1_POS3, W2 , SIMULATOR
    }

    public enum Target{
        R1, R2, R3, W1_POS1, W1_POS2, W1_POS3, W2 , SIMULATOR
    }
    public enum Command{
        ACQUIRE,RELEASE,  //general
        W1_POS1_AVAILABLE,W1_POS2_AVAILABLE,W1_POS3_AVAILABLE,W1_POS1_FREE,W1_POS2_FREE,W1_POS3_FREE, //w1
        // W1_POS1_FREE,W1_POS1_FREE,W1_POS1_FREE,W1_POS1_FREE,W1_POS1_FREE
        W2_AVAILABLE,W2_FREE,W2_DOING_SUBASS_R1,W2_SUBASS_R1_COMPLETED,W2_DOING_SUBASS_R2, //w2
        R2_MOVE_2_POS2,R2_MOVE_2_POS1,R2_WAIT_4_W2,R2_WAIT_4_POS2,R2_WORK_ON_W1 ,R2_WORK_ON_W2, //r2
        R2_POS2_REACHED,R2_POS1_REACHED,
        R1_MOVE_2_POS2,R1_MOVE_2_POS1,R1_WAIT_4_W2,R1_WAIT_4_POS1,R1_WORK_ON_W1 ,R1_WORK_ON_W2, //r1
        R1_POS2_REACHED,R1_POS1_REACHED,
        R3_WAIT_4_W1,R3_WORK_ON_W1  //r3
    }

    public Source source;
    public Target  target;
    public Command  command;

    public MessageRequest(MessageRequest.Source source,MessageRequest.Target target,MessageRequest.Command command) {
        this.source = source;
        this.target = target;
        this.command = command;

    }

}
