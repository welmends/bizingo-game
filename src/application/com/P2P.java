package application.com;

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
	public Boolean connect() {
		return this.technology.connect();
	}

	@Override
	public Boolean disconnect() {
		return this.technology.disconnect();
	}

	@Override
	public String getPeerType() {
		return this.technology.getPeerType();
	}

	@Override
	public Boolean isServer() {
		return this.technology.isServer();
	}

	@Override
	public Boolean isClient() {
		return this.technology.isClient();
	}

	@Override
	public Boolean isConnected() {
		return this.technology.isConnected();
	}

	@Override
	public Boolean chatMessageStackFull() {
		return this.technology.chatMessageStackFull();
	}

	@Override
	public Boolean gameMessageStackFull() {
		return this.technology.gameMessageStackFull();
	}

	@Override
	public Boolean sysMessageStackFull() {
		return this.technology.sysMessageStackFull();
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
	public void server_lookup_call() {
		this.technology.server_lookup_call();
	}

	@Override
	public void server_disconnect_call() {
		this.technology.server_disconnect_call();
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
