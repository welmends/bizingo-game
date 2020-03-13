package application.com;

public interface P2P {
	
	// Connection
	void setup(String ip, int port);
	Boolean connect();
	Boolean disconnect();
	
	// Getters
	String getPeerType();
	Boolean isServer();
	Boolean isClient();
	Boolean isConnected();
	
	// Bizingo Stack Full
	Boolean chatMessageStackFull();
	Boolean gameMessageStackFull();
	Boolean sysMessageStackFull();
	
	// Bizingo Getters
	String get_chat_msg();
	String get_game_mov();
	String get_sys_cmd();
	
	// Calls
	void server_lookup_call();
	void server_disconnect_call();
	void send_chat_msg_call(String msg);
	void move_game_piece_call(String mov);
	void sys_restart_request_call();
	void sys_restart_response_ok_call();
	void sys_restart_response_fail_call();
}
