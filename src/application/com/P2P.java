package application.com;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Enumeration;

import application.com.P2PConstants;
import application.com.rmi.RMIP2P;
import application.com.socket.SocketP2P;

public class P2P implements P2PInterface {
	
	private P2PInterface technology;
	private String technology_name;
	private String ip, local_ip;
	private int port;
	
	public P2P() {
		technology = null;
		this.technology_name = "";
		this.ip = "";
		this.local_ip = "";
		this.port = -1;
	}
	
    // P2P Interface Implementation - Technology
	@Override
	public void set_technology(final String technology_name) {
		this.technology_name = technology_name;
	}
	
	@Override
	public String get_technology_name() {
		if(technology!=null) {
			return this.technology.get_technology_name();
			
		}else {
			return this.technology_name;
		}
	}

 	// P2P Interface Implementation - Connection
	@Override
	public void setup(String ip, int port) {
		if(technology!=null) {
			this.technology.setup(ip, port);
		}else {
			this.ip = ip;
			this.port = port;
		}
	}
	
	@Override
	public void setup(String ip, String local_ip, int port) {
		if(technology!=null) {
			this.technology.setup(ip, local_ip, port);
		}else {
			this.ip = ip;
			this.local_ip = local_ip;
			this.port = port;
		}
	}
	
	@Override
	public Boolean connect() {
		switch (this.get_technology_name()) {
		case P2PConstants.SOCKET:
			this.technology = new SocketP2P();
			this.technology.setup(ip, port);
			return this.technology.connect();
		case P2PConstants.RMI:
			//Create RMI Registry
			String local_ip_address = this.findLocalIpAddressFromNetworkInterfaces();
			if(local_ip_address.equals("")) { return false; }
			try {
				System.setProperty("java.rmi.server.hostname", local_ip_address);
				LocateRegistry.createRegistry(port);
			} catch (RemoteException e) {
				System.out.println(e);
			}
			// Call connect
			try {
				this.technology = new RMIP2P();
			} catch (RemoteException e) {
				System.out.println(e);
			}
			this.technology.setup(ip, local_ip, port);
			return this.technology.connect();
		default:
			return false;
		}
	}

	public String findLocalIpAddressFromNetworkInterfaces() {
		if(ip.equals("") || port==-1) {
			return "";
		}
		if(!ip.equals("localhost")) {
			Boolean is_local = false;
			try {
				Enumeration<?> enum_1 = NetworkInterface.getNetworkInterfaces();
				while(enum_1.hasMoreElements()){
					NetworkInterface net_int =(NetworkInterface) enum_1.nextElement();
					Enumeration<?> enum_2 = net_int.getInetAddresses();
					while(enum_2.hasMoreElements()) {
						InetAddress inet_addr = (InetAddress) enum_2.nextElement();
						
						if(inet_addr.isLoopbackAddress()==true || inet_addr.isSiteLocalAddress()==false) {
							continue;
						}else {
							local_ip = inet_addr.getHostAddress();
						}
				        
						if(ip.equals(inet_addr.getHostAddress())) {
							local_ip = ip;
				        	is_local = true;
				        	break;
				        }
					}
					if(is_local==true) {
						break;
				    }
				}
				if(local_ip.equals("")) {
					return "";
				}
			} catch (Exception e) {
				System.out.println(e);
				return "";
			}
		}else {
			local_ip = ip;
		}
		return local_ip;
	}
	
	@Override
	public Boolean disconnect() {
		if(technology!=null) {
			return this.technology.disconnect();
		}else {
			return false;
		}
	}
	
 	// P2P Interface Implementation - Thread
	@Override
	public void thread_call() {
		if(technology!=null) {
			this.technology.thread_call();
		}
	}

 	// P2P Interface Implementation - Getters
	@Override
	public String get_peer_type() {
		if(technology!=null) {
			return this.technology.get_peer_type();
		}else {
			return "";
		}
	}
	
	@Override
	public String get_ip_address() {
		if(technology!=null) {
			return this.technology.get_ip_address();
		}else {
			return "";
		}
	}
	
	@Override
	public Integer get_port_number() {
		if(technology!=null) {
			return this.technology.get_port_number();
		}else {
			return -1;
		}
	}

	@Override
	public Boolean is_server() {
		if(technology!=null) {
			return this.technology.is_server();
		}else {
			return false;
		}
	}

	@Override
	public Boolean is_client() {
		if(technology!=null) {
			return this.technology.is_client();
		}else {
			return false;
		}
	}

	@Override
	public Boolean has_connection() {
		if(technology!=null) {
			return this.technology.has_connection();
		}else {
			return false;
		}
	}

    // P2P Interface Implementation - Bizingo Stack Full
	@Override
	public Boolean chat_stack_full() {
		if(technology!=null) {
			return this.technology.chat_stack_full();
		}else {
			return false;
		}
	}

	@Override
	public Boolean game_stack_full() {
		if(technology!=null) {
			return this.technology.game_stack_full();
		}else {
			return false;
		}
	}

	@Override
	public Boolean sys_stack_full() {
		if(technology!=null) {
			return this.technology.sys_stack_full();
		}else {
			return false;
		}
	}

	// P2P Interface Implementation - Bizingo Getters
	@Override
	public String get_chat_msg() {
		if(technology!=null) {
			return this.technology.get_chat_msg();
		}else {
			return "";
		}
	}

	@Override
	public String get_game_mov() {
		if(technology!=null) {
			return this.technology.get_game_mov();
		}else {
			return "";
		}
	}

	@Override
	public String get_sys_cmd() {
		if(technology!=null) {
			return this.technology.get_sys_cmd();
		}else {
			return "";
		}
	}
	
	// P2P Interface Implementation - Calls
	@Override
	public void send_chat_msg_call(String msg) {
		if(technology!=null) {
			this.technology.send_chat_msg_call(msg);
		}
	}

	@Override
	public void move_game_piece_call(String mov) {
		if(technology!=null) {
			this.technology.move_game_piece_call(mov);
		}
	}

	@Override
	public void sys_restart_request_call() {
		if(technology!=null) {
			this.technology.sys_restart_request_call();
		}
	}

	@Override
	public void sys_restart_response_ok_call() {
		if(technology!=null) {
			this.technology.sys_restart_response_ok_call();
		}
	}

	@Override
	public void sys_restart_response_fail_call() {
		if(technology!=null) {
			this.technology.sys_restart_response_fail_call();
		}
	}
	
}
