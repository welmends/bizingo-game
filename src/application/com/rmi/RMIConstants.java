package application.com.rmi;

public class RMIConstants {
	
	//example: 'rmi://127.0.0.1:9999/Bizingo'
	
	public static String BIZINGO_RMI_NAME = "Bizingo";
	
	public static String BIZINGO_RMI_SERVER_NAME = BIZINGO_RMI_NAME + "-Server";
	public static String BIZINGO_RMI_CLIENT_NAME = BIZINGO_RMI_NAME + "-Client";
	
	public static String SYS_RESTART_REQUEST = "restart";
	public static String SYS_RESTART_RESPONSE_OK = "restart_ok";
	public static String SYS_RESTART_RESPONSE_FAIL = "restart_fail";
}
