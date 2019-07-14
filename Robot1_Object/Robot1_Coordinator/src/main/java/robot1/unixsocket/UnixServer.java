package robot1.unixsocket;

import org.newsclub.net.unix.AFUNIXServerSocket;
import org.newsclub.net.unix.AFUNIXSocketAddress;
import robot1.ConfigurationUtils;
import robot1.fsm.Robot1Coordinator;
import robot1.fsm.SignalDetector;
import robot1.fsm.signals.SubAss1Completed;
import robot1.fsm.signals.SubAssW2Completed;

import java.io.*;
import java.net.Socket;

public class UnixServer extends Thread{

    public void startServer(){

            final File socketFile = ConfigurationUtils.Robot1CoordinatorSocketFile;
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
                            switch (response){
                                case "AT1_FINISHED":
                                    System.out.println("AT1 finished.");
                                    Robot1Coordinator.LOGGER.warning("SubAss1 completed.."+"\n");
                                    SignalDetector.msgQ.add(new SubAss1Completed());
                                    //break outerloop;
                                case "AT2_FINISHED":
                                    System.out.println("AT2 finished.");
                                    Robot1Coordinator.LOGGER.warning("SubAssW2 completed.."+"\n");
                                    SignalDetector.msgQ.add(new SubAssW2Completed());
                                    //break outerloop;
                                case "POS1_REACHED":
                                    System.out.println("POS1_REACHED");
                                    //break outerloop;
                                case "POS2_REACHED":
                                    System.out.println("POS2_REACHED.");
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
