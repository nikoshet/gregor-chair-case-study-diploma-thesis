package robot3.unixsocket;

import org.newsclub.net.unix.AFUNIXServerSocket;
import org.newsclub.net.unix.AFUNIXSocketAddress;
import robot3.ConfigurationUtils;
import robot3.fsm.Robot3Coordinator;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class UnixServer extends Thread{

    public void startServer(){

        final File socketFile = ConfigurationUtils.Robot3CoordinatorSocketFile;
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
                                case "AT6_FINISHED":
                                    Robot3Coordinator.LOGGER.info("AT6 finished.");
                                    Robot3Coordinator.atQueue.put("AT6_FINISHED");
				    break;
                                    //break outerloop;
                                case "AT7_FINISHED":
                                    Robot3Coordinator.LOGGER.info("AT7 finished.");
                                    Robot3Coordinator.atQueue.put("AT7_FINISHED");
				    break;
                                    //break outerloop;
                                case "AT8_FINISHED":
                                    Robot3Coordinator.LOGGER.info("AT8 finished.");
                                    Robot3Coordinator.atQueue.put("AT8_FINISHED");
				    break;
                                    //break outerloop;
                            }
                        }
                    }
                }catch (IOException | InterruptedException e) {
                    if (server.isClosed()) {
                        throw e;
                    } else {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        startServer();
    }
}
