package dbController;
import movies.Movie;
import movies.MovieFields;
import movies.MovieGenre;
import myLogger.MyLogger;

import java.sql.*;
import java.util.ArrayList;

public class MovieDataBaseControlling extends AbstractController
{
    public MovieDataBaseControlling(String url, String user, String password)
    {
        super(url, user, password);
    }
    public Boolean addMovieGenre(MovieGenre movieGenre)
    {
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection con = DriverManager.getConnection(url, user, password); PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO " + MovieFields.MOVIE_GENRE + " (" + MovieFields.MOVIE_GENRE_ID + ", " + MovieFields.MOVIE_GENRE_NAME + ") VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS))
            {
                preparedStatement.setInt(1, movieGenre.getMovieGenreId());
                preparedStatement.setString(2, movieGenre.getMovieGenreName());
                int affectedRows = preparedStatement.executeUpdate();
                if (affectedRows == 1)
                {
                    try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys())
                    {
                        if (generatedKeys.next())
                        {
                            int generatedMovieGenreId = generatedKeys.getInt(1);
                            movieGenre.setMovieGenreId(generatedMovieGenreId);
                        }
                    }
                }
                return true;
            }
        }
        catch (ClassNotFoundException | SQLException sqlEx)
        {
            MyLogger.logger.error(sqlEx.getMessage());
            return false;
        }
    }
    public Boolean updateMovieGenre(MovieGenre movieGenre)
    {
        try (Connection con = DriverManager.getConnection(url, user, password); PreparedStatement preparedStatement = con.prepareStatement("UPDATE moviegenre SET movieGenreName=? WHERE movieGenreId=?"))
        {
            preparedStatement.setString(1, movieGenre.getMovieGenreName());
            preparedStatement.setInt(2, movieGenre.getMovieGenreId());
            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        }
        catch (SQLException sqlEx)
        {
            MyLogger.logger.error(sqlEx.getMessage());
            return false;
        }
    }
    public Boolean removeMovieGenre(MovieGenre movieGenre)
    {
        try (Connection con = DriverManager.getConnection(url, user, password))
        {
            // First, delete all movies that belong to this genre
            String deleteMoviesQuery = "DELETE FROM " + MovieFields.MOVIE + " WHERE " + MovieFields.MOVIE_GENRE_ID + "=?";
            try (PreparedStatement deleteMoviesStatement = con.prepareStatement(deleteMoviesQuery))
            {
                deleteMoviesStatement.setInt(1, movieGenre.getMovieGenreId());
                deleteMoviesStatement.executeUpdate();
            }

            // Then, delete the MovieGenre record
            String deleteGenreQuery = "DELETE FROM " + MovieFields.MOVIE_GENRE + " WHERE " + MovieFields.MOVIE_GENRE_ID + "=?";
            try (PreparedStatement deleteGenreStatement = con.prepareStatement(deleteGenreQuery))
            {
                deleteGenreStatement.setInt(1, movieGenre.getMovieGenreId());
                int affectedRows = deleteGenreStatement.executeUpdate();
                return affectedRows > 0;
            }
        }
        catch (SQLException sqlEx)
        {
            MyLogger.logger.error(sqlEx.getMessage());
            return false;
        }
    }
    public Boolean addMovie(Movie movie)
    {
        try (Connection con = DriverManager.getConnection(url, user, password); PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO " + MovieFields.MOVIE + " (" + MovieFields.MOVIE_NAME + ", " + MovieFields.CREATION_YEAR + ", " + MovieFields.DURATION + ", " + MovieFields.MOVIE_GENRE_ID + ") VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS))
        {
            preparedStatement.setString(1, movie.getMovieName());
            preparedStatement.setInt(2, movie.getCreationYear());
            preparedStatement.setFloat(3, movie.getDuration());
            preparedStatement.setInt(4, movie.getGenre().getMovieGenreId());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 1)
            {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys())
                {
                    if (generatedKeys.next())
                    {
                        int generatedMovieId = generatedKeys.getInt(1);
                        movie.setMovieId(generatedMovieId);
                    }
                }
            }
            return true;
        }
        catch (SQLException sqlEx)
        {
            MyLogger.logger.error(sqlEx.getMessage());
            return false;
        }
    }
    public Boolean updateMovie(Movie movie)
    {
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection con = DriverManager.getConnection(url, user, password); PreparedStatement preparedStatement = con.prepareStatement("UPDATE movie SET movieName=?, creationYear=?, duration=? WHERE movieId=?"))
            {
                preparedStatement.setString(1, movie.getMovieName());
                preparedStatement.setInt(2, movie.getCreationYear());
                preparedStatement.setFloat(3, movie.getDuration());
                preparedStatement.setInt(4, movie.getMovieId());
                int affectedRows = preparedStatement.executeUpdate();
                return affectedRows > 0;
            }
        }
        catch (ClassNotFoundException | SQLException sqlEx)
        {
            MyLogger.logger.error(sqlEx.getMessage());
            return false;
        }
    }
    public Boolean removeMovie(Movie movie)
    {
        try (Connection con = DriverManager.getConnection(url, user, password); PreparedStatement preparedStatement = con.prepareStatement("DELETE FROM movie WHERE movieId=?"))
        {
            preparedStatement.setInt(1, movie.getMovieId());
            int affectedRows = preparedStatement.executeUpdate();
            return true;
        }
        catch (SQLException sqlEx)
        {
            MyLogger.logger.error(sqlEx.getMessage());
            return false;
        }
    }

    public ArrayList<MovieGenre> getMovieGenres()
    {
        String query = "SELECT * FROM moviegenre";
        ArrayList<MovieGenre> movieGenres = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(url, user, password); Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(query))
        {
            while (rs.next())
            {
                int movieGenreId = rs.getInt("movieGenreId");
                String movieGenreName = rs.getString("movieGenreName");
                MovieGenre movieGenre = new MovieGenre();
                movieGenre.setMovieGenreId(movieGenreId);
                movieGenre.setMovieGenreName(movieGenreName);
                movieGenres.add(movieGenre);
            }
        }
        catch (SQLException sqlEx)
        {
            sqlEx.printStackTrace();
        }
        return movieGenres;
    }
    public ArrayList<Movie> getMovies()
    {
        String query = "SELECT * FROM movie";
        ArrayList<Movie> movies = new ArrayList<>();
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection con = DriverManager.getConnection(url, user, password); Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(query))
            {
                while (rs.next())
                {
                    int movieId = rs.getInt("movieId");
                    String movieName = rs.getString("movieName");
                    int creationYear = rs.getInt("creationYear");
                    float duration = rs.getFloat("duration");
                    int movieGenreId = rs.getInt("movieGenreId");
                    Movie movie = new Movie();
                    movie.setMovieId(movieId);
                    movie.setMovieName(movieName);
                    movie.setCreationYear(creationYear);
                    movie.setDuration(duration);
                    movie.setGenre(getMovieGenreById(movieGenreId));
                    movies.add(movie);
                }
            }
        }
        catch (ClassNotFoundException | SQLException sqlEx)
        {
            MyLogger.logger.error(sqlEx.getMessage());
        }
        return movies;
    }
    public Movie getMovieById(int index)
    {
        String query = "SELECT * FROM movie WHERE movieId = ?";
        Movie movie = null;
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection con = DriverManager.getConnection(url, user, password); PreparedStatement preparedStatement = con.prepareStatement(query))
            {
                preparedStatement.setInt(1, index);
                try (ResultSet rs = preparedStatement.executeQuery())
                {
                    if (rs.next())
                    {
                        movie = new Movie();
                        movie.setMovieId(rs.getInt("movieId"));
                        movie.setMovieName(rs.getString("movieName"));
                        movie.setCreationYear(rs.getInt("creationYear"));
                        movie.setDuration(rs.getFloat("duration"));
                        movie.setGenre(getMovieGenreById(rs.getInt("movieGenreId")));
                    }
                }
            }
        }
        catch (ClassNotFoundException | SQLException sqlEx)
        {
            MyLogger.logger.error(sqlEx.getMessage());
        }
        return movie;
    }
    public MovieGenre getMovieGenreById(int index)
    {
        String query = "SELECT * FROM moviegenre WHERE movieGenreId = ?";
        MovieGenre movieGenre = null;
        try (Connection con = DriverManager.getConnection(url, user, password); PreparedStatement preparedStatement = con.prepareStatement(query))
        {
            preparedStatement.setInt(1, index);
            try (ResultSet rs = preparedStatement.executeQuery())
            {
                if (rs.next())
                {
                    movieGenre = new MovieGenre();
                    movieGenre.setMovieGenreId(rs.getInt("movieGenreId"));
                    movieGenre.setMovieGenreName(rs.getString("movieGenreName"));
                }
            }
        }
        catch (SQLException sqlEx)
        {
            MyLogger.logger.error(sqlEx.getMessage());
        }
        return movieGenre;
    }
    public ArrayList<Movie> getMoviesByGenreId(int index)
    {
        String query = "SELECT * FROM " + MovieFields.MOVIE + " WHERE " + MovieFields.MOVIE_GENRE_ID + " = ?";
        ArrayList<Movie> movies = new ArrayList<>();
        MovieGenre movieGenre = getMovieGenreById(index);
        try (Connection con = DriverManager.getConnection(url, user, password); PreparedStatement preparedStatement = con.prepareStatement(query))
        {
            preparedStatement.setInt(1, index);

            try (ResultSet rs = preparedStatement.executeQuery())
            {
                while (rs.next())
                {
                    int movieId = rs.getInt("movieId");
                    String movieName = rs.getString("movieName");
                    int creationYear = rs.getInt("creationYear");
                    float duration = rs.getFloat("duration");

                    // Create a Movie object and add it to the list
                    Movie movie = new Movie(movieId, movieName, creationYear, duration, movieGenre); // Assuming you pass the genre ID as well
                    movies.add(movie);
                }
            }
        }
        catch (SQLException sqlEx)
        {
            MyLogger.logger.error(sqlEx.getMessage());
        }

        return movies;
    }
    public Boolean saveMovies(ArrayList<Movie> movies, boolean rewriteOld)
    {
        String insertQuery = "INSERT INTO movie (movieName, creationYear, duration) VALUES (?, ?, ?)";
        boolean success = false;
        try (Connection con = DriverManager.getConnection(url, user, password); PreparedStatement preparedStatement = con.prepareStatement(insertQuery))
        {
            con.setAutoCommit(false);
            if (rewriteOld)
            {
                try (PreparedStatement deleteStatement = con.prepareStatement("DELETE FROM movie"))
                {
                    deleteStatement.executeUpdate();
                }
            }
            for (Movie movie : movies)
            {
                preparedStatement.setString(1, movie.getMovieName());
                preparedStatement.setInt(2, movie.getCreationYear());
                preparedStatement.setFloat(3, movie.getDuration());
                preparedStatement.addBatch();
            }
            int[] batchResults = preparedStatement.executeBatch();
            con.commit();
            success = true;
        }
        catch (SQLException sqlEx)
        {
            sqlEx.printStackTrace();
            try (Connection con = DriverManager.getConnection(url, user, password))
            {
                con.rollback();
            }
            catch (SQLException ex)
            {
                MyLogger.logger.error(ex.getMessage());
            }
        }
        return success;
    }
    public Boolean saveMovieGenres(ArrayList<MovieGenre> movieGenres, boolean rewriteOld)
    {
        String insertQuery = "INSERT INTO moviegenre (movieGenreName) VALUES (?)";
        boolean success = false;
        try (Connection con = DriverManager.getConnection(url, user, password); PreparedStatement preparedStatement = con.prepareStatement(insertQuery))
        {
            con.setAutoCommit(false);
            if (rewriteOld)
            {
                try (PreparedStatement deleteStatement = con.prepareStatement("DELETE FROM moviegenre"))
                {
                    deleteStatement.executeUpdate();
                }
            }
            for (MovieGenre movieGenre : movieGenres)
            {
                preparedStatement.setString(1, movieGenre.getMovieGenreName());
                preparedStatement.addBatch();
            }
            int[] batchResults = preparedStatement.executeBatch();
            con.commit();
            success = true;
        }
        catch (SQLException sqlEx)
        {
            sqlEx.printStackTrace();
            try (Connection con = DriverManager.getConnection(url, user, password))
            {
                con.rollback();
            }
            catch (SQLException ex)
            {
                MyLogger.logger.error(ex.getMessage());
            }
        }
        return success;
    }
}
