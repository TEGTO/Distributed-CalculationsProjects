package server.rmi;
import movies.Movie;
import movies.MovieGenre;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface DBControllingServerRMI extends Remote
{
    public Boolean updateMovie(Movie movie) throws RemoteException;
    public Boolean updateMovieGenre(MovieGenre movieGenre) throws RemoteException;
    public Movie getMovieById(int index) throws RemoteException;
    public MovieGenre getMovieGenreById(int index) throws RemoteException;
}

