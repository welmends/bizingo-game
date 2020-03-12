package application.rmi;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RMIP2P extends UnicastRemoteObject implements RMIP2PInterface {

	private static final long serialVersionUID = 0L;
	public static RMIP2PInterface rmi_client;
	
    private String server_link, client_link;
    
    private String peer_type;
    private String ip;
    private int port;
    
	public RMIP2P(String ip, int port) throws RemoteException{
		super();
		
		this.peer_type = "";
		this.ip = ip;
		this.port = port;
		
		setup();
	}
	
	public Boolean setup() {
		server_link = "rmi://"+this.ip+":"+String.valueOf(this.port)+"/"+RMIConstants.BIZINGO_RMI_SERVER_NAME;
		client_link = "rmi://"+this.ip+":"+String.valueOf(this.port)+"/"+RMIConstants.BIZINGO_RMI_CLIENT_NAME;
		
        try {
        	if(Naming.list(server_link).length==0) {
    			this.peer_type = "server";
    		}
        } catch(Exception e){
        	System.out.println(e);
        }
        
        try {
        	if(Naming.list(client_link).length==0) {
    			this.peer_type = "client";
    			server_link = "rmi://"+this.ip+":"+String.valueOf(this.port)+"/"+RMIConstants.BIZINGO_RMI_CLIENT_NAME;
    			client_link = "rmi://"+this.ip+":"+String.valueOf(this.port)+"/"+RMIConstants.BIZINGO_RMI_SERVER_NAME;
    		}
        } catch(Exception e){
        	System.out.println(e);
        }
		
        if(this.peer_type.compareTo("")==0) {
        	return false;
        }
        return true;
	}
	
	public Boolean rebind() {
		try {
			Naming.rebind(server_link, this);
			
			return true;
		} catch(Exception e){
			System.out.println(e);
			
			return false;
		}
	}
	
	public Boolean unbind() {
		try {
			Naming.unbind(server_link);
			
			return true;
		} catch(Exception e){
			System.out.println(e);
			
			return false;
		}
	}
	
	public Boolean lookup() {
		try {
			rmi_client = (RMIP2PInterface)Naming.lookup(client_link);
			
			return true;
		} catch(Exception e){
			System.out.println(e);
			
			return false;
		}
	}
	
	public Boolean has_connection() {
		try {
			if(Naming.list(server_link).length>1) {
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
		return msg;
    }
}