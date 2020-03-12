package application.rmi;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RMIServer extends UnicastRemoteObject implements RMIServerInterface {

	private static final long serialVersionUID = 1L;

	public RMIServer() throws RemoteException{
		super();
		System.out.println("> Servidor criado");
	}
	
	@Override
	public String inverter(String msg) {
		StringBuffer strbuf = new StringBuffer(msg);
		System.out.println("> Recebido: "+msg);
		String retorno = (strbuf.reverse()).toString();
		return retorno;
    }
	
	public static void main(String [] args) {
		try{
			Naming.rebind("rmi://127.0.0.1:9999/Inverter",new RMIServer());
			System.out.println("> Servidor Registrado!");
		} catch (Exception e){
			System.out.println("> Error: " + e);
			System.exit(0);
		}
	}
	
}