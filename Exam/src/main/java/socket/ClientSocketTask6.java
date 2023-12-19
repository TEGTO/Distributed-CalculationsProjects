package socket;
import house.Concert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class ClientSocketTask6
{
    public static void main(String[] args)
    {
        Scanner myObj = new Scanner(System.in);
        SocketConcertDBServerInteraction socketConcertDBServerInteraction = new SocketConcertDBServerInteraction();
        int id;
        float price;
        String genre, place;
        Concert concert;
        while (true)
        {
            System.out.println("++++++++Socket Client++++++++");
            System.out.println("1. Get Concert by ID");
            System.out.println("2. Add New Concert");
            System.out.println("3. Remove Concert By Id");
            System.out.println("4. Update Concert");
            System.out.print("Enter an action: ");
            int action = Integer.parseInt(myObj.nextLine());
            switch (action)
            {
                case 1:
                    System.out.print("Enter concert id: ");
                    id = Integer.parseInt(myObj.nextLine());
                    concert = socketConcertDBServerInteraction.getConcertById(id);
                    Concert.PrintConcert(concert);
                    break;
                case 2:
                    System.out.print("Enter id: ");
                    id =  Integer.parseInt(myObj.nextLine());
                    System.out.print("Enter price: ");
                    price = Float.parseFloat(myObj.nextLine());
                    System.out.print("Enter place: ");
                    place = myObj.nextLine();
                    System.out.print("Enter genre: ");
                    genre = myObj.nextLine();
                    Concert newConcert = new Concert(id,genre, "10.12.2019", price, new ArrayList<>(Arrays.asList("New1", "New2")), place);
                    socketConcertDBServerInteraction.addNewConcert(newConcert);
                    break;
                case 3:
                    System.out.print("Enter concert id: ");
                    id = Integer.parseInt(myObj.nextLine());
                    socketConcertDBServerInteraction.removeConcertById(id);
                    break;
                case 4:
                    System.out.print("Enter concert id: ");
                    id = Integer.parseInt(myObj.nextLine());
                    concert = socketConcertDBServerInteraction.getConcertById(id);
                    System.out.print("Enter price: ");
                    price = Float.parseFloat(myObj.nextLine());
                    concert.price = price;
                    System.out.print("Enter place: ");
                    place = myObj.nextLine();
                    concert.place = place;
                    System.out.print("Enter genre: ");
                    genre = myObj.nextLine();
                    concert.genre = genre;
                    concert.date = "10.10.2010";
                    socketConcertDBServerInteraction.updateConcert(concert);
                    break;
                default:
                    System.out.println("Try again.");
                    break;
            }
        }
    }
}
