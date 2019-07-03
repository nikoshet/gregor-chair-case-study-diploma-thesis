package workbench2;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.File;

@ConfigurationProperties
public class ConfigurationUtils {
	
	//public static String W2_HOSTNAME="172.16.239.20";
	public static String W2_HOSTNAME="localhost";
	public static int W2_COAP_PORT=8565;
	public static int W2_COAPS_PORT=8566;

	//UNIX Sockets
	public static final File HoldSocketFile = new File("/tmp/hold.sock");
	public static final File W2CoordinatorSocketFile = new File("/tmp/w2coordinator.sock");



}
