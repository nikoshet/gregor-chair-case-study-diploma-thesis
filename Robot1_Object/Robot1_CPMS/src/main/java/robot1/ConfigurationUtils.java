package robot1;

import java.io.File;

public class ConfigurationUtils {

	//public static String W1_COAP_SERVER="coap://172.16.239.10:8563";
	//public static String W2_COAP_SERVER="coap://172.16.239.20:8565";
	public static String W1_COAP_SERVER="coap://localhost:8563";
	public static String W2_COAP_SERVER="coap://localhost:8565";
	//UNIX Socket for Robot1
	public static final File socketFile =
			//new File(new File(System.getProperty("java.io.tmpdir")), "robot.sock");
			new File("/tmp/robot1.sock");
}
