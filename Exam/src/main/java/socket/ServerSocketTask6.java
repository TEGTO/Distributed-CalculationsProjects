package socket;
import com.google.gson.Gson;
import constants.Constants;
import concert.Concert;
import myLogger.MyLogger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerSocketTask6
{
    public static void main(String[] args)
    {
        Gson gson = new Gson();
        Concert dataSource = new Concert();
        try (ServerSocket serverSocket = new ServerSocket(Constants.SERVER_PORT))
        {
            MyLogger.logger.info("Server is running and waiting for connections...");
            Socket clientSocket = serverSocket.accept();
            ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream());
            ArrayList<Concert> receivedData;
            String request, result;
            while (true)
            {
                // Wait for a client to connect
                // Create input and output streams for the client
                try
                {
                    // Receive the request string
                    request = (String) inputStream.readObject();
                    // Process the request
                    switch (request)
                    {
                        case "getConcertById":
                            try
                            {
                                result = null;
                                int id = (int) inputStream.readObject();
                                Concert concertResult = null;
                                for (Concert concert : dataSource.exampleData)
                                {
                                    if (concert.getId() == id)
                                    {
                                        concertResult = concert;
                                        break;
                                    }
                                }
                                outputStream.writeObject(true);
                                if (concertResult != null)
                                    result = gson.toJson(concertResult);
                                outputStream.writeObject(result);
                            }
                            catch (Exception ex)
                            {
                                outputStream.writeObject(false);
                            }
                            break;
                        case "addNewConcert":
                            try
                            {
                                Concert currentConcert = gson.fromJson(inputStream.readObject().toString(), Concert.class);
                                boolean boolResult = true;
                                for (Concert concert : dataSource.exampleData)
                                {
                                    if (concert.getId() == currentConcert.getId())
                                    {
                                        MyLogger.logger.info("Id is occupied!");
                                        boolResult = false;
                                        break;
                                    }
                                }
                                if (boolResult)
                                    dataSource.exampleData.add(currentConcert);
                                outputStream.writeObject(boolResult);
                            }
                            catch (Exception ex)
                            {
                                outputStream.writeObject(false);
                            }
                            break;
                        case "removeConcertById":
                            try
                            {
                                int id = (int) inputStream.readObject();
                                boolean boolResult = false;
                                for (Concert concert : dataSource.exampleData)
                                {
                                    if (concert.getId() == id)
                                    {
                                        int index = dataSource.exampleData.indexOf(concert);
                                        dataSource.exampleData.remove(index);
                                        boolResult = true;
                                        break;
                                    }
                                }
                                outputStream.writeObject(boolResult);
                            }
                            catch (Exception ex)
                            {
                                outputStream.writeObject(false);
                            }
                            break;
                        case "updateConcert":
                            try
                            {
                                Concert newConcert = gson.fromJson(inputStream.readObject().toString(), Concert.class);
                                boolean boolResult = false;
                                for (Concert concert : dataSource.exampleData)
                                {
                                    if (concert.getId() == newConcert.getId())
                                    {
                                        int index = dataSource.exampleData.indexOf(concert);
                                        dataSource.exampleData.set(index, newConcert);
                                        boolResult = true;
                                        break;
                                    }
                                }
                                outputStream.writeObject(boolResult);
                            }
                            catch (Exception ex)
                            {
                                outputStream.writeObject(false);
                            }
                            break;
                        default:
                            MyLogger.logger.error("Unknown request: " + request);
                            break;
                    }
                }
                catch (ClassNotFoundException e)
                {
                    MyLogger.logger.error(e.getMessage());
                }
            }
        }
        catch (IOException e)
        {
            MyLogger.logger.error(e.getMessage());
        }
    }
}
