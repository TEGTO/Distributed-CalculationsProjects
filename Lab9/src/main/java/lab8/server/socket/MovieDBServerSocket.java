package lab8.server.socket;
import com.google.gson.Gson;
import lab8.server.ServerConstants;
import movies.Movie;
import movies.MovieGenre;
import myLogger.MyLogger;
import dbController.MovieDataBaseControlling;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.ArrayList;

public class MovieDBServerSocket
{
    public static void main(String[] args)
    {
        Gson gson = new Gson();
        MovieDataBaseControlling movieDataBaseControlling = new MovieDataBaseControlling(ServerConstants.DB_URL, ServerConstants.DB_USER, ServerConstants.DB_PASSWORD);
        try (ServerSocket serverSocket = new ServerSocket(ServerConstants.SERVER_PORT))
        {
            MyLogger.printInfoMessage("Server is running and waiting for connections...");
            Socket clientSocket = serverSocket.accept();
            ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream());
            while (true)
            {
                // Wait for a client to connect
                // Create input and output streams for the client
                try
                {
                    // Receive the request string
                    String request = (String) inputStream.readObject();
                    // Process the request
                    switch (request)
                    {
                        case "addMovieGenre":
                            // Receive the MovieGenre object
                            MovieGenre movieGenre = gson.fromJson((String) inputStream.readObject(), MovieGenre.class);
                            outputStream.writeObject(movieDataBaseControlling.addMovieGenre(movieGenre));
                            break;
                        case "addMovie":
                            // Receive the Movie object
                            Movie movie = gson.fromJson((String) inputStream.readObject(), Movie.class);
                            // Process the addMovie operation and send the result back
                            outputStream.writeObject(movieDataBaseControlling.addMovie(movie));
                            break;
                        case "getMovieGenres":
                            ArrayList<MovieGenre> movieGenreArrayList = movieDataBaseControlling.getMovieGenres();
                            if (movieGenreArrayList == null)
                            {
                                outputStream.writeObject(false);
                            }
                            else
                            {
                                outputStream.writeObject(true);
                                String result = new String();
                                for (MovieGenre genre : movieGenreArrayList)
                                {
                                    result += gson.toJson(genre);
                                    result += "%";
                                }
                                outputStream.writeObject(result);
                            }
                            break;
                        case "getMovies":
                            ArrayList<Movie> movies = movieDataBaseControlling.getMovies();
                            if (movies == null)
                            {
                                outputStream.writeObject(false);
                            }
                            else
                            {
                                outputStream.writeObject(true);
                                String result = new String();
                                for (Movie movie1 : movies)
                                {
                                    result += gson.toJson(movie1);
                                    result += "%";
                                }
                                outputStream.writeObject(result);
                            }
                            break;
                        case "getMovieById":
                            int id = (int) inputStream.readObject();
                            Movie movie1 = movieDataBaseControlling.getMovieById(id);
                            if (movie1 == null)
                            {
                                outputStream.writeObject(false);
                            }
                            else
                            {
                                outputStream.writeObject(true);
                                outputStream.writeObject(gson.toJson(movie1));
                            }
                            break;
                        case "getMovieGenreById":
                            int movieGenreId = (int) inputStream.readObject();
                            MovieGenre movieGenre1 = movieDataBaseControlling.getMovieGenreById(movieGenreId);
                            if (movieGenre1 == null)
                            {
                                outputStream.writeObject(false);
                            }
                            else
                            {
                                outputStream.writeObject(true);
                                outputStream.writeObject(gson.toJson(movieGenre1));
                            }
                            break;
                        default:
                            MyLogger. logger.error("Unknown request: " + request);
                            break;
                    }
                }
                catch (ClassNotFoundException e)
                {
                    e.printStackTrace();
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
