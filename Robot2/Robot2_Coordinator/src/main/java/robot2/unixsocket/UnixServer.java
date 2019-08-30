package robot2.unixsocket;

import org.newsclub.net.unix.AFUNIXServerSocket;
import org.newsclub.net.unix.AFUNIXSocketAddress;
import robot2.ConfigurationUtils;
import robot2.fsm.Robot2Coordinator;
import robot2.fsm.SignalDetector;
import robot2.fsm.signals.SubAss2_1Completed;
import robot2.fsm.signals.SubAss2_2Completed;
import robot2.fsm.signals.SubAssW2Completed;
import robot2.fsm.signals.Pos1Reached;
import robot2.fsm.signals.Pos2Reached;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class UnixServer extends Thread{

    public void startServer(){

        final File socketFile = ConfigurationUtils.Robot2CoordinatorSocketFile;
        try (AFUNIXServerSocket server = AFUNIXServerSocket.newInstance()) {
            server.bind(new AFUNIXSocketAddress(socketFile));
            System.out.println("server: " + server);

            //outerloop:
            while (true) {
                System.out.println("Waiting for connection...");
                try (Socket sock = server.accept()) {
                    System.out.println("Connected: " + sock);

                    try (InputStream is = sock.getInputStream(); //
                         OutputStream os = sock.getOutputStream()) {
                        byte[] buf = new byte[128];
                        int read = is.read(buf);
                        String response = new String(buf, 0, read, "UTF-8");
                        if(!response.equals("")){
                            System.out.println("Client's response: " + response);
                            switch (response.toString()){
                                case "AT2_FINISHED":
                                    Robot2Coordinator.LOGGER.info("AT3 finished.");
                                    Robot2Coordinator.LOGGER.warning("SubAss2OnW1 completed.."+"\n");
                                    SignalDetector.msgQ.add(new SubAss2_1Completed());
                                    break;
                                case "AT3_FINISHED":
                                    Robot2Coordinator.LOGGER.info("AT4 finished.");
                                    Robot2Coordinator.LOGGER.warning("SubAss2OnW2 completed.."+"\n");
                                    SignalDetector.msgQ.add(new SubAssW2Completed());
                                    break;
                                case "AT5_FINISHED":
                                    Robot2Coordinator.LOGGER.info("AT5 finished.");
                                    Robot2Coordinator.LOGGER.warning("CompletingSubAss2OnW1 completed.."+"\n");
                                    SignalDetector.msgQ.add(new SubAss2_2Completed());
                                    break;
                                case "POS1_REACHED":
                                    Robot2Coordinator.LOGGER.info("POS1_REACHED");
                                    SignalDetector.msgQ.add(new Pos1Reached());
                                    break;
                                case "POS2_REACHED":
                                    Robot2Coordinator.LOGGER.info("POS2_REACHED.");
                                    SignalDetector.msgQ.add(new Pos2Reached());
                                    break;
                            }
                        }
                    }
                } catch (IOException e) {
                    if (server.isClosed()) {
                        throw e;
                    } else {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        startServer();
    }
}
