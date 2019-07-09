package robot1;

import java.io.File;

public class ConfigurationUtils {

	//public static String W1_COAP_SERVER="coap://10.42.77.129:8563";
	//public static String W2_COAP_SERVER="coap://10.42.77.129:8565";
	//public static String W1_COAP_SERVER="coap://localhost:8563";
	//public static String W2_COAP_SERVER="coap://localhost:8565";
	public static String W1_COAP_SERVER;
	public static String W2_COAP_SERVER;

	//UNIX Sockets
	public static final File Robot1CtrlrSocketFile = new File("/tmp/robot1ctrl.sock");
	public static final File AT1SocketFile = new File("/tmp/at1.sock");
	public static final File AT2SocketFile = new File("/tmp/at2.sock");
	public static final File Robot1CoordinatorSocketFile = new File("/tmp/robot1coordinator.sock");

}
