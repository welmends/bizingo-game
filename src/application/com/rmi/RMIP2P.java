package application.com.rmi;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.Semaphore;

import application.com.P2PInterface;
import application.com.P2PConstants;

public class RMIP2P extends UnicastRemoteObject implements P2PInterface, RMIP2PInterface {
	
	private static final long serialVersionUID = 0L;
	private static RMIP2PInterface rmi_client;
	
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

	// P2P Interface Implementation - Technology
	@Override
	public void set_technology(P2PInterface technology) {
		return;
	}
	
	@Override
	public String get_technology_name() {
		return "RMI";
	}
	
	// P2P Interface Implementation - Thread
	@Override
	public void thread_call() {
		return;
	}
	
	// P2P Interface Implementation - Connection
	@Override
	public void setup(String ip, int port) {
		this.ip = ip;
		this.port = port;
	}
	
	@Override
	public Boolean connect() {
		
		try {
			LocateRegistry.createRegistry(port);
		} catch (Exception e) {
			System.out.println(e);
		} 
		  
		server_link = "rmi://"+this.ip+":"+String.valueOf(port)+"/"+P2PConstants.BIZINGO_RMI_SERVER_NAME;
		client_link = "rmi://"+this.ip+":"+String.valueOf(port)+"/"+P2PConstants.BIZINGO_RMI_CLIENT_NAME;
		
        try {
        	int length = Naming.list(server_link).length;
        	if(length==0) {
    			peer_type = "server";
    			this.rebind();
    			return true;
    		}
        	else if(length==1) {
        		peer_type = "client";
    			server_link = "rmi://"+this.ip+":"+String.valueOf(port)+"/"+P2PConstants.BIZINGO_RMI_CLIENT_NAME;
    			client_link = "rmi://"+this.ip+":"+String.valueOf(port)+"/"+P2PConstants.BIZINGO_RMI_SERVER_NAME;
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
	
	@Override
	public Boolean disconnect() {
		this.is_connected = false;
		this.unbind();
		server_disconnect_call();
		return false;
	}
	
	// P2P Interface Implementation - Getters
	@Override
    public String get_peer_type() {
    	return this.peer_type;
    }
    
	@Override
    public Boolean is_server() {
    	if(this.peer_type.equals("server")) {
    		return true;
    	}
    	return false;
    }
    
	@Override
    public Boolean is_client() {
    	if(this.peer_type.equals("client")) {
    		return true;
    	}
    	return false;
    }
    
	@Override
    public Boolean has_connection() {
    	return is_connected;
    }
    
    // P2P Interface Implementation - Bizingo Stack Full
	@Override
    public Boolean chat_stack_full() {
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
    
	@Override
    public Boolean game_stack_full() {
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
    
	@Override
    public Boolean sys_stack_full() {
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
    
	// P2P Interface Implementation - Bizingo Getters
	@Override
	public String get_chat_msg() {
		return chat_msg;
	}
	
	@Override
	public String get_game_mov() {
		return game_mov;
	}
	
	@Override
	public String get_sys_cmd() {
		return sys_cmd;
	}
	
	// P2P Interface Implementation - Calls
	@Override
	public void server_lookup_call() {
		try {
			RMIP2P.rmi_client.server_lookup();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void server_disconnect_call() {
		try {
			RMIP2P.rmi_client.server_disconnect();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void send_chat_msg_call(String msg) {
		try {
			RMIP2P.rmi_client.send_chat_msg(msg);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
    }
	
	@Override
	public void move_game_piece_call(String mov) {
		try {
			RMIP2P.rmi_client.move_game_piece(mov);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void sys_restart_request_call() {
		try {
			RMIP2P.rmi_client.sys_restart_request();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void sys_restart_response_ok_call() {
		try {
			RMIP2P.rmi_client.sys_restart_response_ok();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void sys_restart_response_fail_call() {
		try {
			RMIP2P.rmi_client.sys_restart_response_fail();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	// RMI Interface Implementation
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
		sys_cmd = P2PConstants.SYS_RESTART_REQUEST;
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
		sys_cmd = P2PConstants.SYS_RESTART_RESPONSE_OK;
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
		sys_cmd = P2PConstants.SYS_RESTART_RESPONSE_FAIL;
		try {
			mutex.acquire();
			sys_stack_full = true;
	    	mutex.release();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	// RMI Connection Methods
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