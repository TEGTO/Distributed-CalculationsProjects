package rmi;
import concert.Concert;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface DBControllingServerRMI extends Remote
{
    public Concert getConcertById(int id) throws RemoteException;
    public Boolean addNewConcert(Concert concert) throws RemoteException;
    public Boolean updateConcert(Concert concert) throws RemoteException;
    public Boolean removeConcertById(int id) throws RemoteException;
}
