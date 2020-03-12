package application.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIClientInterface extends Remote {
	
	String inverter(String msg) throws RemoteException;
  
}