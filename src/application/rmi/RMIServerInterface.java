package application.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIServerInterface extends Remote {
	
	String hello(String msg) throws RemoteException;
  
}