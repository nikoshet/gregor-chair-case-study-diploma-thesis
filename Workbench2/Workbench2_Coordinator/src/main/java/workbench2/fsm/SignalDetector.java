package workbench2.fsm;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import uml4iot.GenericStateMachine.core.MessageQueue;
import uml4iot.GenericStateMachine.core.SMReception;


public class SignalDetector implements ActionListener{

    private static MessageQueue<SMReception> msgQ;

    public SignalDetector(MessageQueue<SMReception> itsMsgQ) {
        SignalDetector.msgQ = itsMsgQ;
    }

    public void actionPerformed(ActionEvent e) {   }

}
