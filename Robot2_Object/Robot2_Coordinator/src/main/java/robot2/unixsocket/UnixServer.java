package robot2.unixsocket;

import org.newsclub.net.unix.AFUNIXServerSocket;
import org.newsclub.net.unix.AFUNIXSocketAddress;
import robot2.ConfigurationUtils;
import robot2.fsm.Robot2Coordinator;
import robot2.fsm.SignalDetector;
import robot2.fsm.signals.*;

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
                                case "AT3_FINISHED":
                                    System.out.println("AT3 finished.");
                                    Robot2Coordinator.LOGGER.warning("SubAss2OnW1 completed.."+"\n");
                                    SignalDetector.msgQ.add(new SubAss2_1Completed());
                                    //break outerloop;
                                case "AT4_FINISHED":
                                    System.out.println("AT4 finished.");
                                    Robot2Coordinator.LOGGER.warning("SubAss2OnW2 completed.."+"\n");
                                    SignalDetector.msgQ.add(new SubAssW2Completed());
                                    //break outerloop;
                                case "AT5_FINISHED":
                                    System.out.println("AT5 finished.");
                                    Robot2Coordinator.LOGGER.warning("CompletingSubAss2OnW1 completed.."+"\n");
                                    SignalDetector.msgQ.add(new SubAss2_2Completed());
                                    //break outerloop;
                                case "POS1_REACHED":
                                    System.out.println("POS1_REACHED");
                                    SignalDetector.msgQ.add(new Pos1Reached());
                                case "POS2_REACHED":
                                    System.out.println("POS2_REACHED.");
                                    SignalDetector.msgQ.add(new Pos2Reached());
                                    //break outerloop;
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
