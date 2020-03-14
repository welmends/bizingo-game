package application.com;

public interface P2PInterface {
	
	// Technology
	public void set_technology(P2PInterface technology);
	public String get_technology_name();
	
	// Thread
	public void thread_call();
	
	// Connection
	public void setup(String ip, int port);
	public String findLocalIpAddressFromNetworkInterfaces();
	public Boolean connect();
	public Boolean disconnect();
	
	// Getters
	public String get_peer_type();
	public String get_ip_address();
	public Integer get_port_number();
	public Boolean is_server();
	public Boolean is_client();
	public Boolean has_connection();
	
	// Bizingo Stack Full
	public Boolean chat_stack_full();
	public Boolean game_stack_full();
	public Boolean sys_stack_full();
	
	// Bizingo Getters
	public String get_chat_msg();
	public String get_game_mov();
	public String get_sys_cmd();
	
	// Calls
	public void send_chat_msg_call(String msg);
	public void move_game_piece_call(String mov);
	public void sys_restart_request_call();
	public void sys_restart_response_ok_call();
	public void sys_restart_response_fail_call();
}
