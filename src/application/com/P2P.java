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
	public String get_peer_type() {
		return this.technology.get_peer_type();
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
