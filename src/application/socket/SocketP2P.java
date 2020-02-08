package application.socket;

import java.net.*;
import java.io.*;

public class SocketP2P extends Thread {
    private ServerSocket serverSocket = null;
    private Socket socket = null;
    
    private String peer_type;
    private int thread_action;
    private Boolean is_connected;
    
    private String ip;
    private int port;

    private DataInputStream input_stream  = null;
    private DataOutputStream output_stream = null;

    private String message_input= "";
    private String message_output= "";
    
    public SocketP2P(){
    	this.peer_type = "";
    	this.thread_action = 1;
    	this.is_connected = false;
    }
    
    public SocketP2P(String ip, int port){
    	this.peer_type = "";
    	this.thread_action = 1;
    	this.is_connected = false;
    	
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
                    message_input = message_received; //SEMAPHORE
                }
            } catch(Exception e) {
                System.out.println(e);
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
        		this.start();
        		
        		return true;
        	} catch(Exception e_client) {
        		return false;
        	}
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
    
    public Boolean messageStackFull() {
    	if(message_input.length()>0) { //SEMAPHORE
    		return true;
    	}else {
    		return false;
    	}
    }
    
    public void send_message(String msg) {
		try {
			message_output = msg;
			output_stream.writeUTF(message_output);
			output_stream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public String get_message() {
    	String message_received = message_input; //SEMAPHORE
    	message_input = ""; //SEMAPHORE
    	return message_received; 
    }
    
    public String getPeerType() {
    	return this.peer_type;
    }
    
    public Boolean isServer() {
    	if(this.peer_type=="server") {
    		return true;
    	}
    	return false;
    }
    
    public Boolean isClient() {
    	if(this.peer_type=="client") {
    		return true;
    	}
    	return false;
    }
    
    public Boolean isConnected() {
    	return is_connected;
    }
}

