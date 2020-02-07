package application.socket;

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class SocketP2P extends Thread {
    private ServerSocket serverSocket = null;
    private Socket socket = null;
    
    private String peer_type;
    private String ip;
    private int port;

    private DataInputStream input_stream  = null;
    private DataOutputStream output_stream = null;

    private String message_input= "";
    private String message_output= "";
	private Scanner console;

    public SocketP2P(String ip, int port){
    	this.peer_type = "";
        this.ip   = ip;
        this.port = port;

		try {
	        if(ip=="localhost"){
	        	InetAddress server = InetAddress.getByName("localhost");
	            ip = server.getHostName();
	        }
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

    }
    
    public void run(){
        try {
        	System.out.println("Peer Type: "+ this.peer_type);
            while(true){
                message_input = input_stream.readUTF();
                System.out.println("Received: "+message_input);
            }
        } catch(Exception e) {
            System.out.println(e);
        }
    }
    
    public void connect(){
        try {
        	this.peer_type = "server";
        	
            serverSocket = new ServerSocket(port);
            System.out.println("Server Online..");

            socket = serverSocket.accept();
            System.out.println("Connection Estabilished..");
            
            input_stream = new DataInputStream(socket.getInputStream());
            output_stream = new DataOutputStream(socket.getOutputStream());

            this.start();//Start thread

            console = new Scanner(System.in);
            while(true){
                message_output = console.nextLine(); 
                output_stream.writeUTF(message_output);
                output_stream.flush();
            }
        } catch(Exception e_server){
        	try {
        		this.peer_type = "client";
        		
        		socket = new Socket(ip, port);
        		System.out.println("Client Connected..");
        		
        		input_stream = new DataInputStream(socket.getInputStream());
        		output_stream = new DataOutputStream(socket.getOutputStream());
        		
        		this.start();//Start thread
        		
        		console = new Scanner(System.in);
        		while(true){
        			message_output = console.nextLine();
        			output_stream.writeUTF(message_output);
        			output_stream.flush();
        		}
        	} catch(Exception e_client) {
        		System.out.println("both server and client with problem..");
        	}
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

