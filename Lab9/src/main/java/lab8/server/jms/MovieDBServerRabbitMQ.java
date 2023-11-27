package lab8.server.jms;
import com.google.gson.Gson;
import com.rabbitmq.client.*;
import lab8.server.ServerConstants;
import dbController.MovieDataBaseControlling;
import movies.Movie;
import movies.MovieGenre;
import myLogger.MyLogger;

public class MovieDBServerRabbitMQ
{
    static Gson gson;
    public static void main(String[] argv) throws Exception
    {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(ServerConstants.SERVER_IP);

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(ServerConstants.QUEUE_NAME, false, false, false, null);
        channel.queuePurge(ServerConstants.QUEUE_NAME);
        channel.basicQos(1);
        MyLogger.printInfoMessage("Server is running and waiting for requests...");
        gson = new Gson();
        MovieDataBaseControlling movieDataBaseControlling = new MovieDataBaseControlling(ServerConstants.DB_URL, ServerConstants.DB_USER, ServerConstants.DB_PASSWORD);
        DeliverCallback deliverCallback = (consumerTag, delivery) ->
        {
            AMQP.BasicProperties replyProps = new AMQP.BasicProperties.Builder().correlationId(delivery.getProperties().getCorrelationId()).build();

            String response = "";
            try
            {
                String message = new String(delivery.getBody(), "UTF-8");
                String[] parts = message.split("%");
                Integer index;
                switch (parts[0])
                {
                    case "getMovieById":
                        // Receive the MovieGenre object
                        index = Integer.parseInt(parts[1]);
                        response = getMovieById(index, movieDataBaseControlling);
                        break;
                    case "getMovieGenreById":
                        // Receive the MovieGenre object
                        index = Integer.parseInt(parts[1]);
                        response = getMovieGenreById(index, movieDataBaseControlling);
                        break;
                    case "removeMovieGenre":
                        // Receive the MovieGenre object
                        MovieGenre movieGenre = gson.fromJson((String) parts[1], MovieGenre.class);
                        response = removeMovieGenre(movieGenre, movieDataBaseControlling);
                        break;
                    case "removeMovie":
                        // Receive the MovieGenre object
                        Movie movie = gson.fromJson((String) parts[1], Movie.class);
                        response = removeMovie(movie, movieDataBaseControlling);
                        break;
                    default:
                        MyLogger.logger.error("Unknown request: " + parts[0]);
                        break;
                }
            }
            catch (RuntimeException e)
            {
                MyLogger.logger.error(e.getMessage());
            }
            finally
            {
                channel.basicPublish("", delivery.getProperties().getReplyTo(), replyProps, response.getBytes("UTF-8"));
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            }
        };
        channel.basicConsume(ServerConstants.QUEUE_NAME, false, deliverCallback, (consumerTag ->
        {
        }));
    }
    static String getMovieById(Integer index, MovieDataBaseControlling movieDataBaseControlling)
    {
        Movie movie = null;
        boolean result = false;
        try
        {
            movie = movieDataBaseControlling.getMovieById(index);
            result = true;
        }
        catch (Exception ex)
        {
            MyLogger.logger.error(ex.getMessage());
            result = false;
        }
        return result + "%" + gson.toJson(movie);
    }
    static String getMovieGenreById(Integer index, MovieDataBaseControlling movieDataBaseControlling)
    {
        MovieGenre movieGenre = null;
        boolean result = false;
        try
        {
            movieGenre = movieDataBaseControlling.getMovieGenreById(index);
            result = true;
        }
        catch (Exception ex)
        {
            MyLogger.logger.error(ex.getMessage());
            result = false;
        }
        return result + "%" + gson.toJson(movieGenre);
    }
    static String removeMovieGenre(MovieGenre movieGenre, MovieDataBaseControlling movieDataBaseControlling)
    {
        boolean result = false;
        try
        {
            result = movieDataBaseControlling.removeMovieGenre(movieGenre);
        }
        catch (Exception ex)
        {
            MyLogger.logger.error(ex.getMessage());
            result = false;
        }
        return String.valueOf(result);
    }
    static String removeMovie(Movie movie, MovieDataBaseControlling movieDataBaseControlling)
    {
        boolean result = false;
        try
        {
            result = movieDataBaseControlling.removeMovie(movie);
        }
        catch (Exception ex)
        {
            MyLogger.logger.error(ex.getMessage());
            result = false;
        }
        return String.valueOf(result);
    }
}
