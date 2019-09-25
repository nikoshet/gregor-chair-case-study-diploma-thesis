package robot2;

import java.io.File;

public class ConfigurationUtils {

	//public static String W1_COAP_SERVER="coap://172.16.239.10:8563";
	//public static String W2_COAP_SERVER="coap://172.16.239.20:8565";
	public static String W1_COAP_SERVER; //="coap://localhost:8563";
	public static String W2_COAP_SERVER; //="coap://localhost:8565";

	//UNIX Sockets
	public static final File Robot2CtrlrSocketFile = new File("/tmp/robot2ctrl.sock");
	public static final File AT2SocketFile = new File("/tmp/at2.sock");
	public static final File AT3SocketFile = new File("/tmp/at3.sock");
	public static final File AT5SocketFile = new File("/tmp/at5.sock");

	public static final File Robot2CoordinatorSocketFile = new File("/tmp/robot2coordinator.sock");

}



