package workbench1;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.File;

@ConfigurationProperties
public class ConfigurationUtils {

	public static String W1_HOSTNAME;//="localhost";
	public static int W1_COAP_PORT=8563;
	public static int W1_COAPS_PORT=8564;
	public static final File W1CCtrlrSocketFile = new File("/tmp/w1ctrl.sock");
	public static final File W1CCoordinatorsocketFile = new File("/tmp/w1coordinator.sock");


}
