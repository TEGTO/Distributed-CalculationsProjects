package rmi;
import house.Concert;
import myLogger.MyLogger;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class ConcertDBServerRMI implements DBControllingServerRMI
{
    ArrayList<Concert> data;
    ConcertDBServerRMI()
    {
        Concert dataSource = new Concert();
        data = dataSource.exampleData;
    }
    @Override
    public Concert getConcertById(int id) throws RemoteException
    {
        for (Concert concert : data)
        {
            if (concert.getId() == id)
                return concert;
        }
        return null;
    }
    @Override
    public Boolean addNewConcert(Concert newConcert) throws RemoteException
    {
        try
        {
            for (Concert concert : data)
            {
                if (concert.getId() == newConcert.getId())
                {
                    MyLogger.logger.info("Id is occupied!");
                    return false;
                }
            }
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
    public Boolean updateConcert(Concert newConcert) throws RemoteException
    {
        try
        {
            for (Concert concert : data)
            {
                if (concert.getId() == newConcert.getId())
                {
                    int index = data.indexOf(concert);
                    data.set(index, newConcert);
                    return true;
                }
            }
            return false;
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
            for (Concert concert : data)
            {
                if (concert.getId() == id)
                {
                    data.remove(concert);
                    return true;
                }
            }
            return false;
        }
        catch (Exception e)
        {
            MyLogger.logger.error(e.getMessage());
            return false;
        }
    }
}