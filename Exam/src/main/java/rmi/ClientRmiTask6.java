package rmi;
import house.Concert;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class ClientRmiTask6
{
    public static void main(String[] args)
    {
        Scanner myObj = new Scanner(System.in);
        RMIMovieDBServerInteraction rmiMovieDBServerInteraction = new RMIMovieDBServerInteraction();
        int id;
        float price;
        String genre, place;
        Concert concert;
        while (true)
        {
            System.out.println("++++++++RMI Client++++++++");
            System.out.println("1. Get Concert by ID");
            System.out.println("2. Add New Concert");
            System.out.println("3. Remove Concert By Id");
            System.out.println("4. Update Concert");
            System.out.print("Enter an action: ");
            int action = myObj.nextInt();
            switch (action)
            {
                case 1:
                    System.out.print("Enter concert id: ");
                    id = myObj.nextInt();
                    concert = rmiMovieDBServerInteraction.getConcertById(id);
                    Concert.PrintConcert(concert);
                    break;
                case 2:
                    System.out.print("Enter price: ");
                    price = myObj.nextFloat();
                    System.out.print("Enter place: ");
                    place = myObj.nextLine();
                    System.out.print("Enter genre: ");
                    genre = myObj.nextLine();
                    Concert newConcert = new Concert(genre, LocalDateTime.now(), price, new ArrayList<>(Arrays.asList("New1", "New2")), place);
                    rmiMovieDBServerInteraction.addNewConcert(newConcert);
                    break;
                case 3:
                    System.out.print("Enter concert id: ");
                    id = myObj.nextInt();
                    rmiMovieDBServerInteraction.removeConcertById(id);
                    break;
                case 4:
                    System.out.print("Enter concert id: ");
                    id = myObj.nextInt();
                    concert = rmiMovieDBServerInteraction.getConcertById(id);
                    System.out.print("Enter price: ");
                    price = myObj.nextFloat();
                    concert.price = price;
                    System.out.print("Enter place: ");
                    place = myObj.nextLine();
                    concert.place = place;
                    System.out.print("Enter genre: ");
                    genre = myObj.nextLine();
                    concert.genre = genre;
                    concert.data = LocalDateTime.now();
                    rmiMovieDBServerInteraction.addNewConcert(concert);
                    break;
                default:
                    System.out.println("Try again.");
                    break;
            }
        }
    }
}


