package application.socket;

import java.net.*;
import java.util.concurrent.Semaphore;
import java.io.*;

public class SocketP2P extends Thread {
    private ServerSocket serverSocket = null;
    private Socket socket = null;
    
    private DataInputStream input_stream  = null;
    private DataOutputStream output_stream = null;

    private String message_input= "";
    private String message_output= "";
    
    private String peer_type;
    private int thread_action;
    private Boolean is_connected;
    
    private Semaphore mutex;
    
    private String CHAT_CODEC;
    private String GAME_CODEC;
    private String SYS_CODEC;
    
    private String ip;
    private int port;
    
    public SocketP2P(){
    	this.peer_type = "";
    	this.thread_action = 1;
    	this.is_connected = false;
    	
    	mutex = new Semaphore(1);
    	
    	CHAT_CODEC = "#C$";
    	GAME_CODEC = "#G$";
    	SYS_CODEC = "#S$";
    }
    
    public SocketP2P(String ip, int port){
    	this.peer_type = "";
    	this.thread_action = 1;
    	this.is_connected = false;
    	
    	mutex = new Semaphore(1);
    	
    	CHAT_CODEC = "#C$";
    	GAME_CODEC = "#G$";
    	SYS_CODEC = "#S$";
    	
        this.ip   = ip;
        this.port = port;

		try {
	        if(this.ip=="localhost"){
	        	InetAddress server = InetAddress.getByName("localhost");
	            this.ip = server.getHostName();
	        }
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
    }
    
    public void setup(String ip, int port) {
    	this.peer_type = "";
        this.ip   = ip;
        this.port = port;

		try {
	        if(this.ip=="localhost"){
	        	InetAddress server = InetAddress.getByName("localhost");
	            this.ip = server.getHostName();
	        }
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
    }
    
    @Override
    public void run(){
    	if(thread_action==1) {
    		wait_connection();
    	}
    	if(thread_action==2) {
    		try {
                while(true){
                    String message_received = input_stream.readUTF();
                    mutex.acquire();
                    message_input = message_received;
                    mutex.release();
                }
            } catch(Exception e) {
                System.out.println(e);
                is_connected = false;
            }
    	}
    }
    
    public Boolean connect(){
        try {
        	this.peer_type = "server";
        	
            serverSocket = new ServerSocket(port);
            
            return true;
        } catch(Exception e_server){
        	try {
        		this.peer_type = "client";
        		
        		socket = new Socket(ip, port);
        		
        		input_stream = new DataInputStream(socket.getInputStream());
        		output_stream = new DataOutputStream(socket.getOutputStream());
        		
        		thread_action=2;// Flag receive behavior
        		
        		is_connected = true;
        		
        		this.start();
        		
        		return true;
        	} catch(Exception e_client) {
        		return false;
        	}
        }
    }
    
    public Boolean disconnect(){
    	try {
			socket.close();
			
			if(isServer()) {
				serverSocket.close();
			}
			
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
    }
    
    public Boolean wait_connection() {
    	try {
            socket = serverSocket.accept();
            
            input_stream = new DataInputStream(socket.getInputStream());
            output_stream = new DataOutputStream(socket.getOutputStream());
            
            thread_action = 2;// Flag receive behavior
            
            is_connected = true;
            
            return true;
    	} catch(Exception e_client) {
    		return false;
    	}

    }
    
    public Boolean chatMessageStackFull() {
    	String message_received = "";
		try {
			mutex.acquire();
			message_received = message_input;
	    	mutex.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

    	if(message_received.length()>0) {
    		if(CHAT_CODEC.equals(message_received.substring(0,CHAT_CODEC.length()))) {
    			return true;
    		}else {
    			return false;
    		}
    	}else {
    		return false;
    	}
    }
    
    public Boolean gameMessageStackFull() {
    	String message_received = "";
		try {
			mutex.acquire();
			message_received = message_input;
	    	mutex.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

    	if(message_received.length()>0) {
    		if(GAME_CODEC.equals(message_received.substring(0,GAME_CODEC.length()))) {
    			return true;
    		}else {
    			return false;
    		}
    	}else {
    		return false;
    	}
    }
    
    public Boolean sysMessageStackFull() {
    	String message_received = "";
		try {
			mutex.acquire();
			message_received = message_input;
	    	mutex.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

    	if(message_received.length()>0) {
    		if(SYS_CODEC.equals(message_received.substring(0,SYS_CODEC.length()))) {
    			return true;
    		}else {
    			return false;
    		}
    	}else {
    		return false;
    	}
    }
    
    public void sendChatMessage(String msg) {
		try {
			message_output = CHAT_CODEC + msg;
			output_stream.writeUTF(message_output);
			output_stream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public void sendGameMessage(String msg) {
		try {
			message_output = GAME_CODEC + msg;
			output_stream.writeUTF(message_output);
			output_stream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public void sendSysMessage(String msg) {
		try {
			message_output = SYS_CODEC + msg;
			output_stream.writeUTF(message_output);
			output_stream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public String getMessage() {
    	String message_received = "";
		try {
			mutex.acquire();
			message_received = message_input;
			message_input = "";
	    	mutex.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	return message_received.substring(CHAT_CODEC.length(),message_received.length()); 
    }
    
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
}

