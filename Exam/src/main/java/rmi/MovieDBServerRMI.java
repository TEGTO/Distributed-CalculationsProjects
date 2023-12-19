package rmi;
import house.Concert;
import myLogger.MyLogger;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class MovieDBServerRMI implements DBControllingServerRMI
{
    ArrayList<Concert> data;
    MovieDBServerRMI()
    {
        Concert dataSource = new Concert();
        data = dataSource.exampleData;
    }
    @Override
    public Concert getConcertById(int id) throws RemoteException
    {
        Concert result = null;
        for (Concert concert : data)
        {
            if (concert.getId() == id)
                result = concert;
        }
        return result;
    }
    @Override
    public Boolean addNewConcert(Concert newConcert) throws RemoteException
    {
        try
        {
            data.add(newConcert);
            return true;
        }
        catch (Exception e)
        {
            MyLogger.logger.error(e.getMessage());
            return false;
        }
    }
    @Override
    public Boolean updateConcert(Concert concert) throws RemoteException
    {
        try
        {
           int index = data.indexOf(concert);
           data.set(index,concert);
            return true;
        }
        catch (Exception e)
        {
            MyLogger.logger.error(e.getMessage());
            return false;
        }
    }
    @Override
    public Boolean removeConcertById(int id) throws RemoteException
    {
        try
        {
            data.remove(id);
            return true;
        }
        catch (Exception e)
        {
            MyLogger.logger.error(e.getMessage());
            return false;
        }
    }
}