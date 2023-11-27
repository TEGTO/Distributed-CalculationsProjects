package lab8.server.rmi;
import lab8.server.ServerConstants;
import myLogger.MyLogger;

import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ServerRMIMain
{
    public static void main(String[] args) throws RemoteException, AlreadyBoundException, InterruptedException
    {

        MyLogger.printInfoMessage("Server is running and waiting for connections...");
        final MovieDBServerRMI server = new MovieDBServerRMI();
        final Registry registry = LocateRegistry.createRegistry(ServerConstants.SERVER_PORT);
        Remote stub = UnicastRemoteObject.exportObject(server, 0);
        registry.bind(ServerConstants.UNIQUE_BINDING_NAME, stub);
        Thread.sleep(Integer.MAX_VALUE);

    }
}
