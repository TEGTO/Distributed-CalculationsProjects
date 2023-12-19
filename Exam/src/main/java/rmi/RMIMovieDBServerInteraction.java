package rmi;
import constants.Constants;
import house.Concert;
import myLogger.MyLogger;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

public class RMIMovieDBServerInteraction
{
    DBControllingServerRMI dBControllingServerRMI;
    public RMIMovieDBServerInteraction()
    {
        try
        {
            MyLogger.logger.info("Client is running...");
            Registry registry = LocateRegistry.getRegistry(Constants.SERVER_PORT);
            dBControllingServerRMI = (DBControllingServerRMI) registry.lookup(Constants.UNIQUE_BINDING_NAME);
        }
        catch (RemoteException | NotBoundException ex)
        {
            MyLogger.logger.error(ex.getMessage());
        }
    }
    public Concert getConcertById(int id)
    {
        MyLogger.logger.info("Client sends request to get concert with id: " + id + "...");
        Concert result = null;
        try
        {
            result = dBControllingServerRMI.getConcertById(id);
        }
        catch (RemoteException ex)
        {
            MyLogger.logger.error(ex.getMessage());
        }
        return result;
    }
    public void addNewConcert(Concert concert)
    {
        MyLogger.logger.info("Client sends request to add new concert...");
        try
        {
            if (dBControllingServerRMI.addNewConcert(concert))
                MyLogger.logger.info("Concert successfully added.");
            else
                MyLogger.logger.info("Error while adding...");
        }
        catch (RemoteException ex)
        {
            MyLogger.logger.error(ex.getMessage());
        }
    }
    public void removeConcertById(int id)
    {
        MyLogger.logger.info("Client sends request to remove a concert by id...");
        try
        {
            if (dBControllingServerRMI.removeConcertById(id))
                MyLogger.logger.info("Concert successfully removed.");
            else
                MyLogger.logger.info("Error while removing...");
        }
        catch (RemoteException ex)
        {
            MyLogger.logger.error(ex.getMessage());
        }
    }
    public void updateConcert(Concert concert)
    {
        MyLogger.logger.info("Client sends request to update a concert...");
        try
        {
            if (dBControllingServerRMI.updateConcert(concert))
                MyLogger.logger.info("Concert successfully updated.");
            else
                MyLogger.logger.info("Error while updating...");
        }
        catch (RemoteException ex)
        {
            MyLogger.logger.error(ex.getMessage());
        }
    }
}
