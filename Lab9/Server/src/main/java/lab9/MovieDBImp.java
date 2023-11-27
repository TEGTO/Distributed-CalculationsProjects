package lab9;
import dbController.MovieDataBaseControlling;
import jakarta.jws.WebService;
import lab8.server.ServerConstants;
import movies.Movie;
import movies.MovieGenre;

@WebService(endpointInterface = "lab9.IMovieDBWS")
public class MovieDBImp implements IMovieDBWS
{
    @Override
    public String getMovieById(Integer id)
    {
        MovieDataBaseControlling movieDataBaseControlling = new MovieDataBaseControlling(ServerConstants.DB_URL, ServerConstants.DB_USER, ServerConstants.DB_PASSWORD);
        return movieDataBaseControlling.getMovieById(id).toString();
    }
    @Override
    public String addNewMovie(String movieName, Integer creationYear, Float duration, Integer movieGenreId, String movieGenreName)
    {
        MovieDataBaseControlling movieDataBaseControlling = new MovieDataBaseControlling(ServerConstants.DB_URL, ServerConstants.DB_USER, ServerConstants.DB_PASSWORD);
        MovieGenre movieGenre = movieDataBaseControlling.getMovieGenreById(movieGenreId);
        if (movieGenre == null)
        {
            movieGenre = new MovieGenre(movieGenreId, movieGenreName);
            movieDataBaseControlling.addMovieGenre(movieGenre);
        }
        Movie movie = new Movie(0, movieName, creationYear, duration, movieGenre);
        movieDataBaseControlling.addMovie(movie);
        return "Movie successfully added";
    }
}
