package rmi;
import constants.Constants;
import myLogger.MyLogger;
import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ServerRmiTask6
{
    public static void main(String[] args) throws RemoteException, AlreadyBoundException, InterruptedException
    {
        MyLogger.logger.info("Server is running and waiting for connections...");
        final MovieDBServerRMI server = new MovieDBServerRMI();
        final Registry registry = LocateRegistry.createRegistry(Constants.SERVER_PORT);
        Remote stub = UnicastRemoteObject.exportObject(server, 0);
        registry.bind(Constants.UNIQUE_BINDING_NAME, stub);
        Thread.sleep(Integer.MAX_VALUE);
    }
}
