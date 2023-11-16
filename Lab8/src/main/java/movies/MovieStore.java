package movies;
import movieDB.MovieDataBaseControlling;
import other.XMLControlling;

import java.util.ArrayList;

public class MovieStore
{
    private ArrayList<MovieGenre> movieGenres = new ArrayList<MovieGenre>();
    private ArrayList<Movie> movies = new ArrayList<Movie>();
    private XMLControlling xmlControlling;
    private MovieDataBaseControlling dbControlling;
    public MovieStore() {}
    public MovieStore(String xmlPath, String url, String user, String password)
    {
        xmlControlling = new XMLControlling(xmlPath);
        dbControlling = new MovieDataBaseControlling(url, user, password);
    }
    public void setXMLPath(String xmlPath)
    {
        xmlControlling = new XMLControlling(xmlPath);
    }
    public void setDBParams(String url, String user, String password)
    {
        dbControlling = new MovieDataBaseControlling(url, user, password);
    }
    public MovieGenre getMovieGenre(int code)
    {
        for (MovieGenre element : movieGenres)
        {
            if (element.getMovieGenreId() == code)
                return element;
        }
        return null;
    }
    public Movie getMovie(int code)
    {
        for (Movie element : movies)
        {
            if (element.getMovieId() == code)
                return element;
        }
        return null;
    }
    public ArrayList<Movie>getMoviesByGenreXML(int genreId)
    {
        return xmlControlling.getMoviesByGenreId(String.valueOf(genreId));
    }
    public ArrayList<Movie>getMoviesByGenreDB(int genreId)
    {
        return dbControlling.getMoviesByGenreId(genreId);
    }
    public MovieGenre getMovieGenreFromXML(int code)
    {
        return xmlControlling.getMovieGenreById(code);
    }
    public Movie getMovieFromXML(int code)
    {
        return xmlControlling.getMovieById(code);
    }
    public Movie getMovieFromDB(int code)
    {
        return dbControlling.getMovieById(code);
    }
    public MovieGenre getMovieGenreFromDB(int code)
    {
        return dbControlling.getMovieGenreById(code);
    }
    public ArrayList<Movie> getMovieFromXML()
    {
        return xmlControlling.loadMovieStore().getMovies();
    }
    public ArrayList<MovieGenre> getMovieGenreFromXML()
    {
        return xmlControlling.loadMovieStore().getMovieGenres();
    }
    public ArrayList<MovieGenre> getMovieGenreFromDB()
    {
        return dbControlling.getMovieGenres();
    }
    public ArrayList<Movie> getMoviesFromDB()
    {
        return dbControlling.getMovies();
    }

    public Boolean addNewMovieToXML(Movie newMovie)
    {
        return xmlControlling.addNewMovie(newMovie);
    }
    public Boolean addNewMovieToDB(Movie newMovie)
    {
        return dbControlling.addMovie(newMovie);
    }
    public Boolean addNewMovieGenreToXML(MovieGenre newMovieGenre)
    {
        return xmlControlling.addNewMovieGenre(newMovieGenre);
    }
    public Boolean addNewMovieGenreToDB(MovieGenre newMovieGenre)
    {
        return dbControlling.addMovieGenre(newMovieGenre);
    }
    public Boolean updateMovieXML(int id, Movie newMovie)
    {
        return xmlControlling.updateMovieById(id, newMovie);
    }
    public Boolean updateMovieDB(Movie newMovie)
    {
        return dbControlling.updateMovie(newMovie);
    }
    public Boolean updateMovieGenreXML(int id, MovieGenre newMovieGenre)
    {
        return xmlControlling.updateMovieGenreById(id, newMovieGenre);
    }
    public Boolean updateMovieGenreDB(MovieGenre movieGenre)
    {
        return dbControlling.updateMovieGenre(movieGenre);
    }
    public Boolean removeMovieXML(int id)
    {
        return xmlControlling.removeMovie(String.valueOf(id));
    }
    public Boolean removeMovieDB(Movie movie)
    {
        return dbControlling.removeMovie(movie);
    }
    public Boolean removeMovieGenreXML(int id)
    {
        return xmlControlling.removeMovieGenre(String.valueOf(id));
    }
    public Boolean removeMovieGenreDB(MovieGenre movieGenre)
    {
        return dbControlling.removeMovieGenre(movieGenre);
    }

    public void saveToXml(ArrayList<Movie> movies, ArrayList<MovieGenre> movieGenres)
    {
        xmlControlling.saveToFile(movieGenres, movies);
    }
    public int countMovieGenres()
    {
        return movieGenres.size();
    }
    public void addMovieGenre(int code, String name)
    {
        movieGenres.add(new MovieGenre(code, name));
    }
    public void addMovieGenre(MovieGenre movieGenre)
    {
        movieGenres.add(movieGenre);
    }
    public void deleteMovieGenre(int code)
    {
        movieGenres.remove(getMovieGenre(code));
    }
    public void addMovie(Movie movie)
    {
        movies.add(movie);
    }
    public void deleteMovie(int code)
    {
        movies.remove(getMovie(code));
    }
    public int countMovies()
    {
        return movies.size();
    }
    public ArrayList<Movie> getMovies()
    {
        return movies;
    }
    public ArrayList<MovieGenre> getMovieGenres()
    {
        return movieGenres;
    }
}
