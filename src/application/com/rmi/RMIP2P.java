package application.com.rmi;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.Semaphore;

public class RMIP2P extends UnicastRemoteObject implements RMIP2PInterface {

	private static final long serialVersionUID = 0L;
	public static RMIP2PInterface rmi_client;
	
	private Semaphore mutex;
	
    private String server_link, client_link;
    
    private String chat_msg, game_mov, sys_cmd;
    private Boolean chat_stack_full, game_stack_full, sys_stack_full;
    
    private Boolean is_connected;
    private String peer_type;
    private String ip;
    private int port;
    
	public RMIP2P() throws RemoteException{
		super();
		
		this.mutex = new Semaphore(1);
		
		this.server_link = "";
		this.client_link = "";
		
		this.chat_stack_full = false;
		this.game_stack_full = false;
		this.sys_stack_full = false;
		
		this.is_connected = false;
		this.peer_type = "";
		this.ip = "";
		this.port = -1;
	}
	
	public void setup(String ip, int port) {
		this.ip = ip;
		this.port = port;
	}
	
	public Boolean connect() {
		
		try {
			LocateRegistry.createRegistry(port);
		} catch (Exception e) {
			System.out.println(e);
		} 
		  
		server_link = "rmi://"+this.ip+":"+String.valueOf(port)+"/"+RMIConstants.BIZINGO_RMI_SERVER_NAME;
		client_link = "rmi://"+this.ip+":"+String.valueOf(port)+"/"+RMIConstants.BIZINGO_RMI_CLIENT_NAME;
		
        try {
        	int length = Naming.list(server_link).length;
        	if(length==0) {
    			peer_type = "server";
    			this.rebind();
    			return true;
    		}
        	else if(length==1) {
        		peer_type = "client";
    			server_link = "rmi://"+this.ip+":"+String.valueOf(port)+"/"+RMIConstants.BIZINGO_RMI_CLIENT_NAME;
    			client_link = "rmi://"+this.ip+":"+String.valueOf(port)+"/"+RMIConstants.BIZINGO_RMI_SERVER_NAME;
    			this.rebind();
    			this.lookup();
    			RMIP2P.rmi_client.server_lookup();
    			return true;
    		}else {
    			return false;
    		}
        } catch(Exception e){
        	System.out.println(e);
        	return false;
        }
	}
	
	public Boolean disconnect() {
		this.is_connected = false;
		this.unbind();
		server_disconnect_call();
		return false;
	}
	
	// Getters
    public String getPeerType() {
    	return this.peer_type;
    }
    
    public Boolean isServer() {
    	if(this.peer_type.equals("server")) {
    		return true;
    	}
    	return false;
    }
    
    public Boolean isClient() {
    	if(this.peer_type.equals("client")) {
    		return true;
    	}
    	return false;
    }
    
    public Boolean isConnected() {
    	return is_connected;
    }
    
    // Bizingo Stack Full Verifiers
    public Boolean chatMessageStackFull() {
    	Boolean stack_full = false;
		try {
			mutex.acquire();
			stack_full = chat_stack_full;
			if(stack_full == true) { chat_stack_full = false; }
	    	mutex.release();
		} catch (Exception e) {
			System.out.println(e);
		}
    	return stack_full;
    }
    
    public Boolean gameMessageStackFull() {
    	Boolean stack_full = false;
		try {
			mutex.acquire();
			stack_full = game_stack_full;
			if(stack_full == true) { game_stack_full = false; }
	    	mutex.release();
		} catch (Exception e) {
			System.out.println(e);
		}
    	return stack_full;
    }
    
    public Boolean sysMessageStackFull() {
    	Boolean stack_full = false;
		try {
			mutex.acquire();
			stack_full = sys_stack_full;
			if(stack_full == true) { sys_stack_full = false; }
	    	mutex.release();
		} catch (Exception e) {
			System.out.println(e);
		}
    	return stack_full;
    }
    
	// Bizingo Getters
	public String get_chat_msg() {
		return chat_msg;
	}
	
	public String get_game_mov() {
		return game_mov;
	}
	
	public String get_sys_cmd() {
		return sys_cmd;
	}
	
	// RMI Interface Implementations calls
	public void server_lookup_call() {
		try {
			RMIP2P.rmi_client.server_lookup();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public void server_disconnect_call() {
		try {
			RMIP2P.rmi_client.server_disconnect();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public void send_chat_msg_call(String msg) {
		try {
			RMIP2P.rmi_client.send_chat_msg(msg);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
    }
	
	public void move_game_piece_call(String mov) {
		try {
			RMIP2P.rmi_client.move_game_piece(mov);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public void sys_restart_request_call() {
		try {
			RMIP2P.rmi_client.sys_restart_request();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public void sys_restart_response_ok_call() {
		try {
			RMIP2P.rmi_client.sys_restart_response_ok();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void sys_restart_response_fail_call() {
		try {
			RMIP2P.rmi_client.sys_restart_response_fail();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	// RMI Interface Implementations
	@Override
	public void server_lookup() {
		this.lookup();
	}
	
	@Override
	public void server_disconnect() {
		this.is_connected = false;
		this.unbind();
	}
	
	@Override
	public void send_chat_msg(String msg) {
		chat_msg = msg;
		try {
			mutex.acquire();
			chat_stack_full = true;
	    	mutex.release();
		} catch (Exception e) {
			System.out.println(e);
		}
    }
	
	@Override
	public void move_game_piece(String mov) {
		game_mov = mov;
		try {
			mutex.acquire();
			game_stack_full = true;
	    	mutex.release();
		} catch (Exception e) {
			System.out.println(e);
		}
    }
	
	@Override
	public void sys_restart_request() {
		sys_cmd = RMIConstants.SYS_RESTART_REQUEST;
		try {
			mutex.acquire();
			sys_stack_full = true;
	    	mutex.release();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	@Override
	public void sys_restart_response_ok() {
		sys_cmd = RMIConstants.SYS_RESTART_RESPONSE_OK;
		try {
			mutex.acquire();
			sys_stack_full = true;
	    	mutex.release();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	@Override
	public void sys_restart_response_fail() {
		sys_cmd = RMIConstants.SYS_RESTART_RESPONSE_FAIL;
		try {
			mutex.acquire();
			sys_stack_full = true;
	    	mutex.release();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	// RMI Basics
	private Boolean rebind() {
		try {
			Naming.rebind(server_link, this);
			
			return true;
		} catch(Exception e){
			System.out.println(e);
			
			return false;
		}
	}
	
	private Boolean unbind() {
		try {
			Naming.unbind(server_link);
			
			return true;
		} catch(Exception e){
			System.out.println(e);
			
			return false;
		}
	}
	
	private Boolean lookup() {
		try {
			rmi_client = (RMIP2PInterface)Naming.lookup(client_link);
			is_connected = true;
			return true;
		} catch(Exception e){
			System.out.println(e);
			
			return false;
		}
	}
	
}