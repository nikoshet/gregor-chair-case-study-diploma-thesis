package workbench2.fsm;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import uml4iot.GenericStateMachine.core.MessageQueue;
import uml4iot.GenericStateMachine.core.SMReception;


public class SignalDetector implements ActionListener{

    public static MessageQueue<SMReception> msgQ;

    public SignalDetector(MessageQueue<SMReception> itsMsgQ) {
        SignalDetector.msgQ = itsMsgQ;
    }

    public void actionPerformed(ActionEvent e) {

    /*     if(e.getSource() == W2ctrlgui.R1acquire)
        {
            //Custom Deferred Event Handling -- Probably not working
            if (W2Ctrl.w2State == W2CtrlState.SUBASSR2_W2){
                W2Ctrl.subassR2_W2.addDeferredEvent(W2SMEvent.R1ACQUIRE);
                System.out.println("Deferred Event: " + W2Ctrl.subassR2_W2.getDeferredEvents());
            }
            //Custom Deferred Event Handling -- Probably not working
            else{
                SignalDetector.msgQ.add(W2SMEvent.R1ACQUIRE);
            }
        }
        if(e.getSource() ==  W2ctrlgui.R2acquire)
        {
            //Custom Deferred Event Handling -- Probably not working
            if (W2Ctrl.w2State == W2CtrlState.FREE){
                //ERROR Report
                W2Ctrl.LOGGER.warning("ERROR Report: R2acquire Message was received before R1acquire Message.\n");
                //
            }
            else if (W2Ctrl.w2State == W2CtrlState.SUBASSR1_W2){
                W2Ctrl.subassR1_W2.addDeferredEvent(W2SMEvent.R2ACQUIREW2);
                System.out.println("Deferred Event: " + W2Ctrl.subassR1_W2.getDeferredEvents());
            }
            //Custom Deferred Event Handling -- Probably not working
            else{
                SignalDetector.msgQ.add(W2SMEvent.R2ACQUIREW2);
            }
        }
        if(e.getSource() ==  W2ctrlgui.R1release)
        {
            SignalDetector.msgQ.add(W2SMEvent.R1RELEASE);
        }
        if(e.getSource() ==  W2ctrlgui.R2release)
        {
            SignalDetector.msgQ.add(W2SMEvent.R2RELEASE);
        }
        */
    }

}
