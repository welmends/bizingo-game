package application.rmi;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RMIClient extends UnicastRemoteObject implements RMIClientInterface{
	
	private static final long serialVersionUID = 1L;
	public static RMIServerInterface rmi_server;
	
	private String server_link, client_link;
    private String ip;
    private int port;
    
	public RMIClient(String ip, int port) throws RemoteException{
		super();
		this.ip = ip;
		this.port = port;
		server_link = "rmi://"+this.ip+":"+String.valueOf(this.port)+"/"+RMIConstants.BIZINGO_RMI_SERVER_NAME;
		client_link = "rmi://"+this.ip+":"+String.valueOf(this.port)+"/"+RMIConstants.BIZINGO_RMI_CLIENT_NAME;
	}
	
	public Boolean rebind() {
		try {
			Naming.rebind(client_link, this);
			
			return true;
		} catch(Exception e){
			System.out.println(e);
			
			return false;
		}
	}
	
	public Boolean unbind() {
		try {
			Naming.unbind(client_link);
			
			return true;
		} catch(Exception e){
			System.out.println(e);
			
			return false;
		}
	}
	
	public Boolean lookup() {
		try {
			rmi_server = (RMIServerInterface)Naming.lookup(server_link);

			return true;
		} catch(Exception e){
			System.out.println(e);
			
			return false;
		}
	}
	
	public Boolean has_connection() {
		try {
			if(Naming.list(client_link).length>0) {
				return true;
			}else {
				return false;
			}
		} catch(Exception e){
			System.out.println(e);
			
			return false;
		}
	}
	
	@Override
	public String hello(String msg) {
		return "im client - " + msg;
    }
}