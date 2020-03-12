package application.rmi;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RMIClient extends UnicastRemoteObject implements RMIClientInterface{
	
	private static final long serialVersionUID = 1L;

	public RMIClient() throws RemoteException{
		super();
		System.out.println("> Cliente criado");
	}
	
	@Override
	public String inverter(String msg) {
		StringBuffer strbuf = new StringBuffer(msg);
		System.out.println("> Recebido: "+msg);
		String retorno = (strbuf.reverse()).toString();
		return retorno;
    }
	
	public static void main(String args[])  { 
		try {   
			RMIServerInterface rmi_server = (RMIServerInterface)Naming.lookup("rmi://127.0.0.1:9999/Inverter");
			System.out.println("> Objeto Localizado");
			
			String feedback = rmi_server.inverter("wellington");
			System.out.println("> Frase Invertida = " + feedback);  

		} catch(Exception e){
			System.err.println("> Error");
		}
		System.exit(0);
	}

}