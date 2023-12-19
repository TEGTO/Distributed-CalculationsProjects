package socket;
import house.Concert;
import java.util.ArrayList;
import java.util.Scanner;

public class ClientSocketTask6
{
    public static void main(String[] args)
    {
        Scanner myObj = new Scanner(System.in);
        SocketMovieDBServerInteraction socketMovieDBServerInteraction = new SocketMovieDBServerInteraction();
        int roomNumber, floorFrom, floorTo;
        float square;
        ArrayList<Concert> apartments;
        while (true)
        {
            System.out.println("++++++++Socket Client++++++++");
            System.out.println("1. Get Apartments By Room Number");
            System.out.println("2. Get Apartments By Room Number And In Floor Range");
            System.out.println("3. Get Apartments That Have Square More Than");
            System.out.print("Enter an action: ");
            int action = myObj.nextInt();
            switch (action)
            {
                case 1:
                    System.out.print("Enter roomNumber: ");
                    roomNumber = myObj.nextInt();
                    apartments = socketMovieDBServerInteraction.getConcertById(roomNumber);
                    Concert.PrintConcert(apartments);
                    break;
                case 2:
                    System.out.print("Enter roomNumber: ");
                    roomNumber = myObj.nextInt();
                    System.out.print("Enter floor from: ");
                    floorFrom = myObj.nextInt();
                    System.out.print("Enter floor to: ");
                    floorTo = myObj.nextInt();
                    apartments = socketMovieDBServerInteraction.addNewConcert(roomNumber, floorFrom, floorTo);
                    Concert.PrintConcert(apartments);
                    break;
                case 3:
                    System.out.print("Enter square: ");
                    square = myObj.nextFloat();
                    apartments = socketMovieDBServerInteraction.getApartmentsThatHaveSquareMoreThan(square);
                    Concert.PrintConcert(apartments);
                    break;
                default:
                    System.out.println("Try again.");
                    break;
            }
        }
    }
}
