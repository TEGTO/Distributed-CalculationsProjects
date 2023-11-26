package client.serverInteraction;
import com.google.gson.Gson;
import com.rabbitmq.client.AMQP;
import movies.Movie;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import movies.MovieGenre;
import myLogger.MyLogger;
import server.ServerConstants;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class JMSMovieDBServerInteraction extends BaseMovieDBServerInteraction implements AutoCloseable
{
    private Connection connection;
    private Channel channel;
    private Gson gson;
    public JMSMovieDBServerInteraction()
    {
        try
        {
            gson = new Gson();
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(ServerConstants.SERVER_IP);
            connection = factory.newConnection();
            channel = connection.createChannel();
        }
        catch (TimeoutException | IOException ex)
        {
            MyLogger.printErrorMessage(ex.getMessage());
        }
    }

    @Override
    public Movie getMovieById(int index)
    {
        try
        {
            String message = "getMovieById%" + index;
            String[] parts = call(message).split("%");
            boolean result = Boolean.parseBoolean(parts[0]);
            if (result)
            {
                MyLogger.printInfoMessage("Operation successful");
                return gson.fromJson((String) parts[1], Movie.class);
            }
            else
            {
                MyLogger.printInfoMessage("Operation failed");
                return null;
            }
        }
        catch (IOException | InterruptedException | ExecutionException ex)
        {
            MyLogger.printErrorMessage(ex.getMessage());
            return null;
        }
    }
    @Override
    public MovieGenre getGenreById(int index)
    {
        try
        {
            String message = "getMovieGenreById%" + index;
            String[] parts = call(message).split("%");
            boolean result = Boolean.parseBoolean(parts[0]);
            if (result)
            {
                MyLogger.printInfoMessage("Operation successful");
                return gson.fromJson((String) parts[1], MovieGenre.class);
            }
            else
            {
                MyLogger.printInfoMessage("Operation failed");
                return null;
            }
        }
        catch (IOException | InterruptedException | ExecutionException ex)
        {
            MyLogger.printErrorMessage(ex.getMessage());
            return null;
        }
    }
    @Override
    public void removeMovieGenre(MovieGenre movieGenre)
    {
        try
        {
            String message = "removeMovieGenre%" + gson.toJson(movieGenre);
            String[] parts = call(message).split("%");
            boolean result = Boolean.parseBoolean(parts[0]);
            if (result)
                MyLogger.printInfoMessage("Operation successful");
            else
                MyLogger.printInfoMessage("Operation failed");
        }
        catch (IOException | InterruptedException | ExecutionException ex)
        {
            MyLogger.printErrorMessage(ex.getMessage());
        }
    }
    public void removeMovie(Movie movie)
    {
        try
        {
            String message = "removeMovie%" + gson.toJson(movie);
            String[] parts = call(message).split("%");
            boolean result = Boolean.parseBoolean(parts[0]);
            if (result)
                MyLogger.printInfoMessage("Operation successful");
            else
                MyLogger.printInfoMessage("Operation failed");
        }
        catch (IOException | InterruptedException | ExecutionException ex)
        {
            MyLogger.printErrorMessage(ex.getMessage());
        }
    }
    public String call(String message) throws IOException, InterruptedException, ExecutionException
    {
        final String corrId = UUID.randomUUID().toString();
        String replyQueueName = channel.queueDeclare().getQueue();
        AMQP.BasicProperties props = new AMQP.BasicProperties.Builder().correlationId(corrId).replyTo(replyQueueName).build();
        channel.basicPublish("", ServerConstants.QUEUE_NAME, props, message.getBytes("UTF-8"));
        final CompletableFuture<String> response = new CompletableFuture<>();
        String ctag = channel.basicConsume(replyQueueName, true, (consumerTag, delivery) ->
        {
            if (delivery.getProperties().getCorrelationId().equals(corrId))
                response.complete(new String(delivery.getBody(), "UTF-8"));
        }, consumerTag ->
        {
        });

        String result = response.get();
        channel.basicCancel(ctag);
        return result;
    }
    @Override
    public void close() throws Exception
    {
        connection.close();
    }
}
