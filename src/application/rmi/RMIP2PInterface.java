package application.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIP2PInterface extends Remote {
	
	String hello(String msg) throws RemoteException;
  
}