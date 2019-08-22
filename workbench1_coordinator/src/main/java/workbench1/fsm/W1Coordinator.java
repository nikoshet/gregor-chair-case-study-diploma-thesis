package workbench1.fsm;
import org.json.JSONObject;
import workbench1.ConfigurationUtils;
import workbench1.events.Rbt_2_W1_Event;
import workbench1.unixsocket.UnixClient;
import workbench1.unixsocket.UnixServer;

import java.lang.Thread;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.logging.Level;
import java.util.logging.Logger;


public class W1Coordinator extends Thread {

    public static ArrayBlockingQueue<Rbt_2_W1_Event> eventQueue = new ArrayBlockingQueue<Rbt_2_W1_Event>(20);
    private Queue<Rbt_2_W1_Event> defEvQueue = new LinkedList<Rbt_2_W1_Event>();
    int[][] pos = new int[][]{ // shows the posLfc that needs to be updated
            {1, 2, 3}, {2, 1, 3}, {3, 1, 2}
    };
    private Rbt_2_W1_Event nextEvent;
    private P1StateMachine p1CurSt, p1TargSt;
    private P2StateMachine p2CurSt, p2TargSt;
    private P3StateMachine p3CurSt, p3TargSt;
    private boolean tmt = false;
    static Logger LOGGER;

    public static BlockingQueue<String> ctrlQueue = new LinkedBlockingDeque<>();
    UnixServer unixServer = new UnixServer();
    UnixClient unixclient = new UnixClient();
    W1Coordinator(P1StateMachine p1St, P2StateMachine p2St, P3StateMachine p3St){
        /*Create Logger file and make configurations */
    	System.out.println("controller started");
        LOGGER = Logger.getLogger(W1Coordinator.class.getName()+ "LOGGER");

        LOGGER.setLevel(Level.ALL); // Request that every detail gets logged.
        unixServer.start();
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
                System.out.println(" \n \n Table Must Turn \n \n ");
                String sendText = new JSONObject()
                        .put("ROTATE", "START")
                        .toString();
                unixclient.communicate(ConfigurationUtils.W1CCtrlrSocketFile,sendText);
                outerloop:
                while(true){
                    String value = null;
                    try {
                        value = ctrlQueue.take();
                        switch (value.toString()){
                            case "ROTATE_FINISHED":
                                System.out.println("\n \n ROTATE FINISHED \n \n ");
                                break outerloop;
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }

                /*updatePosLfcs*/
                updatePosLfcs();
                /*turn tmt 2 false*/
                tmt = false;
                /*check if there are defEvents in defEvQueue and serve them*/
                if(!defEvQueue.isEmpty()){
                    processDefEvents();
                }
            }
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
            }
            else{
                //edw na ginei to rotate
            }
        }
        else if(pos[nextEvent.eventSource][0]== 2){
            p2TargSt = p2CurSt.definePosStates(nextEvent, tmt, p1CurSt, p3CurSt);
            System.out.println("------------------- " +p2TargSt + p2CurSt);
            tmt = p2CurSt.getTMTStatus(p1CurSt, p3CurSt); //edw tou allazw to tmt value!!!

            if((nextEvent == Rbt_2_W1_Event.R2AcquireP2) && ((p2CurSt == P2StateMachine.SubAss2Completed) || (p2CurSt == P2StateMachine.Free))){
                defEvQueue.add(Rbt_2_W1_Event.R2AcquireP2);//edw prosthetw defEv an uparxei
                System.out.println("DeferredEvent4P2: R2AcquireP2");
            }

            if(!tmt) {
                p2CurSt = p2TargSt;
            }
            else{
                //edw na ginei to rotate
            }
        }
        else if(pos[nextEvent.eventSource][0]== 3){
            p3TargSt =	p3CurSt.definePosStates(nextEvent, tmt, p1CurSt, p2CurSt);
            tmt = p3CurSt.getTMTStatus(p1CurSt, p2CurSt); //edw tou allazw to tmt value!!!

            if((nextEvent == Rbt_2_W1_Event.R3AcquireP3) && (p3CurSt == P3StateMachine.Free)){
                defEvQueue.add(Rbt_2_W1_Event.R3AcquireP3);//edw prosthetw defEv an uparxei
                System.out.println("DeferredEvent4P1: R3AcquireP3");
            }

            if(!tmt){
                p3CurSt = p3TargSt;
            }
            else{
                //edw na ginei to rotate
            }
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