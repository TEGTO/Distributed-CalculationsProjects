package client.serverInteraction;
import com.google.gson.Gson;
import movies.Movie;
import movies.MovieGenre;
import myLogger.MyLogger;
import server.ServerConstants;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class SocketMovieDBServerInteraction extends BaseMovieDBServerInteraction implements AutoCloseable
{
    Socket socket;
    ObjectOutputStream outputStream;
    ObjectInputStream inputStream;
    public SocketMovieDBServerInteraction()
    {
        try
        {
            MyLogger.printInfoMessage("Client is running...");
            socket = new Socket(ServerConstants.SERVER_IP, ServerConstants.SERVER_PORT);
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void addMovieGenre(MovieGenre movieGenre)
    {
        try
        {
            MyLogger.printInfoMessage("Client sends request to add new movie genre...");
            Gson gson = new Gson();
            outputStream.writeObject("addMovieGenre");
            outputStream.writeObject(gson.toJson(movieGenre));
            boolean result = (boolean) inputStream.readObject();
            if (result)
            {
                System.out.println("Operation successful");
            }
            else
            {
                System.out.println("Operation failed");
            }
            return;
        }
        catch (IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        System.out.println("Operation failed");
    }
    @Override
    public void addMovie(Movie movie)
    {
        try
        {
            MyLogger.printInfoMessage("Client sends request to add new movie...");
            Gson gson = new Gson();
            outputStream.writeObject("addMovie");
            outputStream.writeObject(gson.toJson(movie));
            boolean result = (boolean) inputStream.readObject();
            if (result)
            {
                System.out.println("Operation successful");
            }
            else
            {
                System.out.println("Operation failed");
            }
            return;
        }
        catch (IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        System.out.println("Operation failed");
    }
    @Override
    public ArrayList<MovieGenre> getMovieGenres()
    {
        try
        {
            MyLogger.printInfoMessage("Client sends request to get all Movie Genres...");
            Gson gson = new Gson();
            outputStream.writeObject("getMovieGenres");
            boolean result = (boolean) inputStream.readObject();
            if (result)
            {
                MyLogger.printInfoMessage("Operation successful");
                String[] parts = ((String) inputStream.readObject()).split("%");
                ArrayList<MovieGenre> movieGenres = new ArrayList<MovieGenre>();
                for (String movieGenreStr : parts)
                {
                    MovieGenre movieGenre = gson.fromJson(movieGenreStr, MovieGenre.class);
                    movieGenres.add(movieGenre);
                }
                return movieGenres;
            }
            else
            {
                MyLogger.printInfoMessage("Operation failed");
                return null;
            }
        }
        catch (IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
            MyLogger.printInfoMessage("Operation failed");
            return null;
        }
    }
    @Override
    public ArrayList<Movie> getMovies()
    {
        try
        {
            MyLogger.printInfoMessage("Client sends request to get all Movies...");
            Gson gson = new Gson();
            outputStream.writeObject("getMovies");
            boolean result = (boolean) inputStream.readObject();
            if (result)
            {
                MyLogger.printInfoMessage("Operation successful");
                String[] parts = ((String) inputStream.readObject()).split("%");
                ArrayList<Movie> movies = new ArrayList<Movie>();
                for (String movieStr : parts)
                {
                    Movie movie = gson.fromJson(movieStr, Movie.class);
                    movies.add(movie);
                }
                return movies;
            }
            else
            {
                MyLogger.printInfoMessage("Operation failed");
                return null;
            }
        }
        catch (IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
            MyLogger.printInfoMessage("Operation failed");
            return null;
        }
    }
    @Override
    public Movie getMovieById(int index)
    {
        try
        {
            MyLogger.printInfoMessage("Client sends request to get Movie by Id...");
            Gson gson = new Gson();
            outputStream.writeObject("getMovieById");
            outputStream.writeObject(index);
            boolean result = (boolean) inputStream.readObject();
            if (result)
            {
                MyLogger.printInfoMessage("Operation successful");
                return gson.fromJson((String) inputStream.readObject(), Movie.class);
            }
            else
            {
                MyLogger.printInfoMessage("Operation failed");
                return null;
            }
        }
        catch (IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
            MyLogger.printInfoMessage("Operation failed");
            return null;
        }
    }
    @Override
    public MovieGenre getGenreById(int index)
    {
        try
        {
            MyLogger.printInfoMessage("Client sends request to get Movie Genre by Id...");
            Gson gson = new Gson();
            outputStream.writeObject("getMovieGenreById");
            outputStream.writeObject(index);
            boolean result = (boolean) inputStream.readObject();
            if (result)
            {
                MyLogger.printInfoMessage("Operation successful");
                return gson.fromJson((String) inputStream.readObject(), MovieGenre.class);
            }
            else
            {
                MyLogger.printInfoMessage("Operation failed");
                return null;
            }
        }
        catch (IOException | ClassNotFoundException e)
        {
            MyLogger.printInfoMessage("Operation failed");
            MyLogger.printErrorMessage((e.getMessage()));
            return null;
        }
    }
    @Override
    public void close() throws Exception
    {
        try
        {
            socket.close();
            outputStream.close();
            inputStream.close();
        }
        catch (Exception ex)
        {
            MyLogger.printInfoMessage("Closing exception");
            MyLogger.printErrorMessage((ex.getMessage()));
        }
    }
}
