package robot3.fsm;


import uml4iot.GenericStateMachine.core.MessageQueue;
import uml4iot.GenericStateMachine.core.SMReception;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class SignalDetector implements ActionListener{

    public static MessageQueue<SMReception> msgQ;

    public SignalDetector(MessageQueue<SMReception> itsMsgQ) {
        SignalDetector.msgQ = itsMsgQ;
    }

    public void actionPerformed(ActionEvent e) {
/*        if(e.getSource() == robot1ctrlgui.w1pos1avail)
        {
            SignalDetector.msgQ.add(R1SMEvent.W1POS1AVAILABLE);
        }
        if(e.getSource() ==  robot1ctrlgui.w2avail)
        {
            SignalDetector.msgQ.add(R1SMEvent.W2AVAILABLE);
        }*/
    }
}
