package socket;
import com.google.gson.Gson;
import constants.Constants;
import house.Concert;
import myLogger.MyLogger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class SocketMovieDBServerInteraction implements AutoCloseable
{
    Socket socket;
    ObjectOutputStream outputStream;
    ObjectInputStream inputStream;
    public SocketMovieDBServerInteraction()
    {
        try
        {
            MyLogger.logger.info("Client is running...");
            socket = new Socket(Constants.SERVER_IP, Constants.SERVER_PORT);
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    public Concert getConcertById(int id)
    {
        try
        {
            MyLogger.logger.info("Client sends request to get concert with id: " + id + "...");
            Gson gson = new Gson();
            outputStream.writeObject("getConcertById");
            outputStream.writeObject(id);
            boolean result = (boolean) inputStream.readObject();
            if (result)
            {
                String conecertStr = ((String) inputStream.readObject());
                Concert resultConcert = gson.fromJson(conecertStr, Concert.class);
                return resultConcert;
            }
            else
            {
                MyLogger.logger.info("Operation failed");
                return null;
            }
        }
        catch (IOException | ClassNotFoundException e)
        {
            MyLogger.logger.error(e.getMessage());
            return null;
        }
    }
    public void addNewConcert(Concert concert)
    {
        try
        {
            MyLogger.logger.info("Client sends request to add new concert...");
            Gson gson = new Gson();
            outputStream.writeObject("addNewConcert");
            outputStream.writeObject(gson.toJson(concert));
            boolean result = (boolean) inputStream.readObject();
            if (result)
            {
                MyLogger.logger.info("Concert successfully added.");
            }
            else
            {
                MyLogger.logger.info("Error while adding...");
            }
        }
        catch (IOException | ClassNotFoundException e)
        {
            MyLogger.logger.error(e.getMessage());
        }
    }
    public void removeConcertById(int id)
    {
        try
        {
            MyLogger.logger.info("Client sends request to remove a concert by id...");
            Gson gson = new Gson();
            outputStream.writeObject("removeConcertById");
            outputStream.writeObject(id);
            boolean result = (boolean) inputStream.readObject();
            if (result)
            {
                MyLogger.logger.info("Concert successfully removed.");
            }
            else
            {
                MyLogger.logger.info("Error while removing...");
            }
        }
        catch (IOException | ClassNotFoundException e)
        {
            MyLogger.logger.error(e.getMessage());
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
            MyLogger.logger.info("Closing exception");
            MyLogger.logger.error((ex.getMessage()));
        }
    }
}
