package application.com;

public interface P2PInterface {
	
	// Technology
	public void set_technology(P2PInterface technology);
	public String get_technology_name();
	
	// Thread
	public void thread_call();
	
	// Connection
	void setup(String ip, int port);
	Boolean connect();
	Boolean disconnect();
	
	// Getters
	String get_peer_type();
	Boolean is_server();
	Boolean is_client();
	Boolean has_connection();
	
	// Bizingo Stack Full
	Boolean chat_stack_full();
	Boolean game_stack_full();
	Boolean sys_stack_full();
	
	// Bizingo Getters
	String get_chat_msg();
	String get_game_mov();
	String get_sys_cmd();
	
	// Calls
	void send_chat_msg_call(String msg);
	void move_game_piece_call(String mov);
	void sys_restart_request_call();
	void sys_restart_response_ok_call();
	void sys_restart_response_fail_call();
}
