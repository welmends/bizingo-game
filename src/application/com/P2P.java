package application.com;

import java.rmi.registry.LocateRegistry;

import application.com.P2PConstants;

public class P2P implements P2PInterface {
	
	private P2PInterface technology;
	
	@Override
	public void set_technology(P2PInterface technology) {
		this.technology = technology;
	}
	
	@Override
	public String get_technology_name() {
		return this.technology.get_technology_name();
	}

	@Override
	public void thread_call() {
		this.technology.thread_call();
	}

	@Override
	public void setup(String ip, int port) {
		this.technology.setup(ip, port);
	}

	@Override
	public String findLocalIpAddressFromNetworkInterfaces() {
		return this.technology.findLocalIpAddressFromNetworkInterfaces();
	}
	
	@Override
	public Boolean connect() {
		switch (this.get_technology_name()) {
		case P2PConstants.SOCKET:
			return this.technology.connect();
		case P2PConstants.RMI:
			// Create RMI Registry
			String ip_address = this.findLocalIpAddressFromNetworkInterfaces();
			if(ip_address.equals("")) { return false; }
			try {
				System.out.println(this.get_peer_type());
				System.out.println(ip_address+":"+this.get_port_number());
				System.setProperty("java.rmi.server.hostname",ip_address);
				LocateRegistry.createRegistry(this.get_port_number());
			} catch (Exception e) {
				System.out.println(e);
			}
			// Call connect
			return this.technology.connect();
		default:
			return false;
		}
	}

	@Override
	public Boolean disconnect() {
		return this.technology.disconnect();
	}

	@Override
	public String get_peer_type() {
		return this.technology.get_peer_type();
	}
	
	@Override
	public String get_ip_address() {
		return this.technology.get_ip_address();
	}
	
	@Override
	public Integer get_port_number() {
		return this.technology.get_port_number();
	}

	@Override
	public Boolean is_server() {
		return this.technology.is_server();
	}

	@Override
	public Boolean is_client() {
		return this.technology.is_client();
	}

	@Override
	public Boolean has_connection() {
		return this.technology.has_connection();
	}

	@Override
	public Boolean chat_stack_full() {
		return this.technology.chat_stack_full();
	}

	@Override
	public Boolean game_stack_full() {
		return this.technology.game_stack_full();
	}

	@Override
	public Boolean sys_stack_full() {
		return this.technology.sys_stack_full();
	}

	@Override
	public String get_chat_msg() {
		return this.technology.get_chat_msg();
	}

	@Override
	public String get_game_mov() {
		return this.technology.get_game_mov();
	}

	@Override
	public String get_sys_cmd() {
		return this.technology.get_sys_cmd();
	}
	
	@Override
	public void send_chat_msg_call(String msg) {
		this.technology.send_chat_msg_call(msg);
	}

	@Override
	public void move_game_piece_call(String mov) {
		this.technology.move_game_piece_call(mov);
	}

	@Override
	public void sys_restart_request_call() {
		this.technology.sys_restart_request_call();
	}

	@Override
	public void sys_restart_response_ok_call() {
		this.technology.sys_restart_response_ok_call();
	}

	@Override
	public void sys_restart_response_fail_call() {
		this.technology.sys_restart_response_fail_call();
	}

}
