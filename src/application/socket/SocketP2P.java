package application.socket;

import java.net.*;
import java.io.*;

public class SocketP2P extends Thread {
    private ServerSocket serverSocket = null;
    private Socket socket = null;
    
    private String peer_type;
    private int thread_action;
    
    private String ip;
    private int port;

    private DataInputStream input_stream  = null;
    private DataOutputStream output_stream = null;

    private String message_input= "";
    private String message_output= "";
    
    @Override
    public void run(){
    	if(thread_action==1) {
    		wait_connection();
    	}else {
    		try {
                while(true){
                    message_input = input_stream.readUTF();
                    System.out.println("Received: "+message_input);
                }
            } catch(Exception e) {
                System.out.println(e);
            }
    	}
    }
    
    public SocketP2P(){
    	this.peer_type = "";
    	this.thread_action = 1;
    }
    
    public SocketP2P(String ip, int port){
    	this.peer_type = "";
    	this.thread_action = 1;
    	
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
        		
        		thread_action=2;
        		this.start();// Start receive thread
        		
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
            
            thread_action = 2;
            this.start();// Start receive thread
            
            return true;
    	} catch(Exception e_client) {
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
}

