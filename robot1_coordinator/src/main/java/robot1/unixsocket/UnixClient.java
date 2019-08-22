package robot1.unixsocket;

import java.io.*;
import org.newsclub.net.unix.AFUNIXSocket;
import org.newsclub.net.unix.AFUNIXSocketAddress;

public class UnixClient {

    public static void communicateWithAT(File socketFile, String message){

        try {
            AFUNIXSocket sock = AFUNIXSocket.newInstance();
            sock.connect(new AFUNIXSocketAddress(socketFile));
            System.out.println("Connected");

            try(OutputStream os = sock.getOutputStream()) {
                os.write(message.getBytes("UTF-8"));
                os.flush();
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }

    }

}
