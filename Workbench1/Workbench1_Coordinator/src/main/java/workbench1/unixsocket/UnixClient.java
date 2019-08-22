package workbench1.unixsocket;

import org.newsclub.net.unix.AFUNIXSocket;
import org.newsclub.net.unix.AFUNIXSocketAddress;

import java.io.File;
import java.io.OutputStream;

public class UnixClient {

    public static void communicate(File socketFile, String message){

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
