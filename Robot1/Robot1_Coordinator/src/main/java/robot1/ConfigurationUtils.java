package robot1;

import java.io.File;

public class ConfigurationUtils {

	//public static String W1_COAP_SERVER="coap://172.16.239.10:8563";
	//public static String W2_COAP_SERVER="coap://172.16.239.20:8565";
	public static String W1_COAP_SERVER;//="coap://localhost:8563";
	public static String W2_COAP_SERVER;//="coap://localhost:8565";

	//UNIX Sockets
	public static final File Robot1CtrlrSocketFile = new File("/tmp/robotctrl.sock");
	public static final File AT1SocketFile = new File("/tmp/at1.sock");
	public static final File AT4SocketFile = new File("/tmp/at4.sock");
	public static final File Robot1CoordinatorSocketFile = new File("/tmp/robot1coordinator.sock");

}
