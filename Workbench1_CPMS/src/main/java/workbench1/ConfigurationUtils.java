package workbench1;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties
public class ConfigurationUtils {
	
	//public static String W1_HOSTNAME="172.16.239.10";
	public static String W1_HOSTNAME="localhost";
	public static int W1_COAP_PORT=8563;
	public static int W1_COAPS_PORT=8564;
	


}
