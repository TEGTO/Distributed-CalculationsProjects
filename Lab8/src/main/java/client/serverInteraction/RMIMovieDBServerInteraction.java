package client.serverInteraction;
import movies.Movie;
import movies.MovieGenre;
import myLogger.MyLogger;
import server.ServerConstants;
import server.rmi.DBControllingServerRMI;

import java.awt.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

public class RMIMovieDBServerInteraction extends BaseMovieDBServerInteraction
{
    DBControllingServerRMI dBControllingServerRMI;
    public RMIMovieDBServerInteraction()
    {
        try
        {
            MyLogger.printInfoMessage("Client is running...");
            Registry registry = LocateRegistry.getRegistry(ServerConstants.SERVER_PORT);
            dBControllingServerRMI = (DBControllingServerRMI) registry.lookup(ServerConstants.UNIQUE_BINDING_NAME);
        }
        catch (RemoteException | NotBoundException ex)
        {
            MyLogger.printErrorMessage(ex.getMessage());
        }
    }
    @Override
    public void updateMovie(Movie movie)
    {
        MyLogger.printInfoMessage("Client sends request to update Movie...");
        Boolean result = false;
        try
        {
            result = dBControllingServerRMI.updateMovie(movie);
        }
        catch (RemoteException ex)
        {
            MyLogger.printErrorMessage(ex.getMessage());
        }
        if (result)
            MyLogger.printInfoMessage("Operation successful");
        else
            MyLogger.printInfoMessage("Operation failed");
    }
    @Override
    public void updateMovieGenre(MovieGenre movieGenre)
    {
        MyLogger.printInfoMessage("Client sends request to update Movie Genre...");
        Boolean result = false;
        try
        {
            result = dBControllingServerRMI.updateMovieGenre(movieGenre);
        }
        catch (RemoteException ex)
        {
            MyLogger.printErrorMessage(ex.getMessage());
        }
        if (result)
            MyLogger.printInfoMessage("Operation successful");
        else
            MyLogger.printInfoMessage("Operation failed");
    }
    @Override
    public Movie getMovieById(int index)
    {
        MyLogger.printInfoMessage("Client sends request to get Movie by Id...");
        Boolean result = false;
        Movie movie = null;
        try
        {
            movie = dBControllingServerRMI.getMovieById(index);
            result = true;
        }
        catch (RemoteException ex)
        {
            MyLogger.printErrorMessage(ex.getMessage());
            result = false;
        }
        if (result)
            MyLogger.printInfoMessage("Operation successful");
        else
            MyLogger.printInfoMessage("Operation failed");
        return movie;
    }
    @Override
    public MovieGenre getGenreById(int index)
    {
        MyLogger.printInfoMessage("Client sends request to get Movie Genre by Id...");
        Boolean result = false;
        MovieGenre movieGenre = null;
        try
        {
            movieGenre = dBControllingServerRMI.getMovieGenreById(index);
            result = true;
        }
        catch (RemoteException ex)
        {
            MyLogger.printErrorMessage(ex.getMessage());
            result = false;
        }
        if (result)
            MyLogger.printInfoMessage("Operation successful");
        else
            MyLogger.printInfoMessage("Operation failed");
        return movieGenre;
    }
}
