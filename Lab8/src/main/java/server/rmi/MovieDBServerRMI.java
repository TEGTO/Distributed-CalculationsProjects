package server.rmi;
import movies.Movie;
import movies.MovieGenre;
import movieDB.MovieDataBaseControlling;
import server.ServerConstants;

import java.rmi.RemoteException;

public class MovieDBServerRMI implements DBControllingServerRMI
{
    MovieDataBaseControlling movieDataBaseControlling;
    public MovieDBServerRMI()
    {
        movieDataBaseControlling = new MovieDataBaseControlling(ServerConstants.DB_URL, ServerConstants.DB_USER, ServerConstants.DB_PASSWORD);
    }

    @Override
    public Boolean updateMovie(Movie movie) throws RemoteException
    {
        return movieDataBaseControlling.updateMovie(movie);
    }
    @Override
    public Boolean updateMovieGenre(MovieGenre movieGenre) throws RemoteException
    {
        return movieDataBaseControlling.updateMovieGenre(movieGenre);
    }
    @Override
    public Movie getMovieById(int index) throws RemoteException
    {
        return movieDataBaseControlling.getMovieById(index);
    }
    @Override
    public MovieGenre getMovieGenreById(int index) throws RemoteException
    {
        return movieDataBaseControlling.getMovieGenreById(index);
    }
}
