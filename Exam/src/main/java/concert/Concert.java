package concert;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class Concert implements Serializable
{
    public String genre;
    public String date;
    public Float price;
    public ArrayList<String> participantsNames;
    public String place;
    public transient ArrayList<Concert> exampleData = new ArrayList<>();
    protected Integer id;
    public Concert()
    {
        exampleData.add(new Concert(1,"rock", "05.02.2022", 5f, new ArrayList<>(Arrays.asList("First", "Second")), "street1"));
        exampleData.add(new Concert(2,"rap", "13.11.2020", 15f, new ArrayList<>(Arrays.asList("Third", "Second")), "street2"));
    }
    public Concert(Integer id, String genre, String date, Float price, ArrayList<String> participantsNames, String place)
    {
        this.id = id;
        this.genre = genre;
        this.date = date;
        this.price = price;
        this.participantsNames = participantsNames;
        this.place = place;
    }
    public static void PrintConcert(Concert concert)
    {
        System.out.println("====Result==== ");
        if (concert == null)
        {
            System.out.println("---Concert is not found by the criteria.---");
            System.out.println("==============");
            return;
        }
        System.out.println(concert);
        System.out.println("==============");
    }
    public int getId()
    {return id;}

    @Override
    public String toString()
    {
        String s = "Concert ID: " + id + " Genre: " + genre + " Data: " + date + " Price: " + price + " Participants: " + participantsNames + " Place: " + place;
        return s;
    }
}
