package workbench1.fsm;
import workbench1.events.Rbt_2_W1_Event;

import java.io.IOException;
import java.lang.Thread;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;


public class W1Controller extends Thread {
    //fields
    public static ArrayBlockingQueue<Rbt_2_W1_Event> eventQueue = new ArrayBlockingQueue<Rbt_2_W1_Event>(20);
    //the size of the ArrayBlockingQueue is the number of events in the queue --> by default it is 0
    private Queue<Rbt_2_W1_Event> defEvQueue = new LinkedList<Rbt_2_W1_Event>();// oxi ArrayBlockingQueue den thelw na ginetai block otan
    // den uparxoun events h otan exw full to queue
    int[][] pos = new int[][]{ // shows the posLfc that needs to be updated
            {1, 2, 3}, {2, 1, 3}, {3, 1, 2}
    };
    public Rbt_2_W1_Event nextEvent;
    P1StateMachine p1CurSt, p1TargSt;
    P2StateMachine p2CurSt, p2TargSt;
    P3StateMachine p3CurSt, p3TargSt;
    //tableMustTurn --> tmt
    boolean tmt = false;
    public static Logger LOGGER;



    public W1Controller(P1StateMachine p1St,P2StateMachine p2St,P3StateMachine p3St){
        /*Create Logger file and make configurations */
    	System.out.println("controller started");
        LOGGER = Logger.getLogger(W1Controller.class.getName()+ "LOGGER");
        FileHandler fh = null;
        try {
            fh = new FileHandler("W1Events.log", true);


        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        LOGGER.addHandler(fh);
        fh.setFormatter(new Logger_Formatter());
        LOGGER.setLevel(Level.ALL); // Request that every detail gets logged.
        //LOGGER.finest("W1 starting");
        LOGGER.info("W1 starting");


        p1CurSt = p1St;
        p2CurSt = p2St;
        p3CurSt = p3St;
    }


    /*** run method ***/
    public void run(){
        while(true){
            /*retrieve event from eventQueue*/
            nextEvent = getNextEvent();

            /*updatePosLfc*/
            updatePosLfc();

            /*check if table must turn and make the appropriate actions*/
            if(tmt){
                System.out.println("Table Must Turn");

                /*updatePosLfcs*/
                updatePosLfcs();
                /*turn tmt 2 false*/
                tmt = false;
                /*check if there are defEvents in defEvQueue and serve(eksuphrethse) them*/
                if(!defEvQueue.isEmpty()){
                    processDefEvents();
                }
            }


            //System.out.println("Hello JoKo");
        }
    }

    /********* methods **********/

    private Rbt_2_W1_Event getNextEvent(){
        Rbt_2_W1_Event nextEvent = null;
        try {
            nextEvent = eventQueue.take();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return nextEvent;
    }

    private void updatePosLfc(){
        //check which posLfc to update
        if(pos[nextEvent.eventSource][0] == 1){

            p1TargSt = p1CurSt.definePosStates(nextEvent, tmt, p2CurSt, p3CurSt);
            tmt = p1CurSt.getTMTStatus(p2CurSt, p3CurSt); //edw tou allazw to tmt value!!!

            if((nextEvent == Rbt_2_W1_Event.R1AcquireP1) && (p1CurSt == P1StateMachine.SubAss1Completed)){
                defEvQueue.add(Rbt_2_W1_Event.R1AcquireP1);//edw prosthetw defEv an uparxei
                System.out.println("DeferredEvent4P1: R1AcquireP1");
            }

            if(!tmt){
                p1CurSt = p1TargSt;

                //System.out.println("After transition : p1CurSt == SubAss1Completed");
            }
        }
        else if(pos[nextEvent.eventSource][0]== 2){
            p2TargSt = p2CurSt.definePosStates(nextEvent, tmt, p1CurSt, p3CurSt);
            System.out.println("//////////////////////" +p2TargSt + p2CurSt);
            tmt = p2CurSt.getTMTStatus(p1CurSt, p3CurSt); //edw tou allazw to tmt value!!!

            if((nextEvent == Rbt_2_W1_Event.R2AcquireP2) && ((p2CurSt == P2StateMachine.SubAss2Completed) || (p2CurSt == P2StateMachine.Free))){
                defEvQueue.add(Rbt_2_W1_Event.R2AcquireP2);//edw prosthetw defEv an uparxei
                System.out.println("DeferredEvent4P2: R2AcquireP2");
            }

            if(!tmt) {
                p2CurSt = p2TargSt;
               /* System.out.println(".................."+ p2CurSt);
                if(p2CurSt==P2StateMachine.SubAss2) {           
                	p2TargSt = p2CurSt.definePosStates(nextEvent, tmt, p1CurSt, p3CurSt);*/
               // }
            }
        }
        else if(pos[nextEvent.eventSource][0]== 3){
            p3TargSt =	p3CurSt.definePosStates(nextEvent, tmt, p1CurSt, p2CurSt);
            tmt = p3CurSt.getTMTStatus(p1CurSt, p2CurSt); //edw tou allazw to tmt value!!!

            if((nextEvent == Rbt_2_W1_Event.R3AcquireP3) && (p3CurSt == P3StateMachine.Free)){
                defEvQueue.add(Rbt_2_W1_Event.R3AcquireP3);//edw prosthetw defEv an uparxei
                System.out.println("DeferredEvent4P1: R3AcquireP3");
            }

            if(!tmt)
                p3CurSt = p3TargSt;
        }


    }

    private void updatePosLfcs(){
        if(pos[nextEvent.eventSource][0] == 1){
            p2TargSt = p2CurSt.definePosStates(nextEvent, tmt, p1CurSt, p3CurSt);
            p3TargSt = p3CurSt.definePosStates(nextEvent, tmt, p1CurSt, p2CurSt);
        }
        else if(pos[nextEvent.eventSource][0] == 2){
            p1TargSt = p1CurSt.definePosStates(nextEvent, tmt, p2CurSt, p3CurSt);
            p3TargSt = p3CurSt.definePosStates(nextEvent, tmt, p1CurSt, p2CurSt);
        }
        else{
            p1TargSt = p1CurSt.definePosStates(nextEvent, tmt, p2CurSt, p3CurSt);
            p2TargSt = p2CurSt.definePosStates(nextEvent, tmt, p1CurSt, p3CurSt);
        }
        /*make the currentStates equal to the target states*/
        p1CurSt = p1TargSt;
        p2CurSt = p2TargSt;
        p3CurSt = p3TargSt;
    }

    void processDefEvents(){
        if(!defEvQueue.isEmpty()){
            int size = defEvQueue.size();
            for(int i = 0; i < size; i++){
                //Retrieve event from defEvQueue
                nextEvent = defEvQueue.poll();

                /*updatePosLfc*/
                updatePosLfc();
            }
        }
    }

   

}
