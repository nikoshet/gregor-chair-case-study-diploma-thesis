/**
 * @author Achilleas Triantafyllou
 */
package robot1.unixsocket;

import org.newsclub.net.unix.AFUNIXServerSocket;
import org.newsclub.net.unix.AFUNIXSocketAddress;
import robot1.ConfigurationUtils;

import java.io.*;
import java.net.Socket;

public class UnixServer extends Thread{

    public void startServer(){

            final File socketFile = ConfigurationUtils.Robot1CoordinatorSocketFile;
        try (AFUNIXServerSocket server = AFUNIXServerSocket.newInstance()) {
            server.bind(new AFUNIXSocketAddress(socketFile));
            System.out.println("server: " + server);

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
                                    break;
                                case "AT2_FINISHED":
                                    System.out.println("AT2 finished.");
                                    break;
                                case "POS1_REACHED":
                                    System.out.println("POS1_REACHED");
                                    break;
                                case "POS2_REACHED":
                                    System.out.println("POS2_REACHED.");
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
