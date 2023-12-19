package socket;
import com.google.gson.Gson;
import constants.Constants;
import house.Concert;
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
                        case "getApartmentsByRoomNumber":
                            try
                            {
                                int roomNumber = (int) inputStream.readObject();
                                receivedData = new ArrayList<>();
                                for (Concert concert : dataSource.exampleData)
                                {
                                    if (concert.roomNumber.equals(roomNumber))
                                        receivedData.add(concert);
                                }
                                outputStream.writeObject(true);
                                result = new String();
                                for (Concert concert : receivedData)
                                {
                                    result += gson.toJson(concert);
                                    result += Constants.SPLIT_SYMBOL;
                                }
                                outputStream.writeObject(result);
                            }
                            catch (Exception ex)
                            {
                                outputStream.writeObject(false);
                            }
                            break;
                        case "getApartmentsByRoomNumberAndInFloorRange":
                            try
                            {
                                int roomNumber = (int) inputStream.readObject();
                                int floorFrom = (int) inputStream.readObject();
                                int floorTo = (int) inputStream.readObject();
                                receivedData = new ArrayList<>();
                                for (Concert concert : dataSource.exampleData)
                                {
                                    if (concert.roomNumber.equals(roomNumber) && concert.floor >= floorFrom && concert.floor <= floorTo)
                                        receivedData.add(concert);
                                }
                                outputStream.writeObject(true);
                                result = new String();
                                for (Concert concert : receivedData)
                                {
                                    result += gson.toJson(concert);
                                    result += Constants.SPLIT_SYMBOL;
                                }
                                outputStream.writeObject(result);
                            }
                            catch (Exception ex)
                            {
                                outputStream.writeObject(false);
                            }
                            break;
                        case "getApartmentsThatHaveSquareMoreThan":
                            try
                            {
                                float moreThanSquare = (float) inputStream.readObject();
                                receivedData = new ArrayList<>();
                                for (Concert concert : dataSource.exampleData)
                                {
                                    if (concert.price > moreThanSquare)
                                        receivedData.add(concert);
                                }
                                outputStream.writeObject(true);
                                result = new String();
                                for (Concert concert : receivedData)
                                {
                                    result += gson.toJson(concert);
                                    result += Constants.SPLIT_SYMBOL;
                                }
                                outputStream.writeObject(result);
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
