package house;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

public class Concert implements Serializable
{
    protected static Integer instanceCounter = 0;
    public String genre;
    public LocalDateTime data;
    public Float price;
    public ArrayList<String> participantsNames;
    public String place;
    public ArrayList<Concert> exampleData = new ArrayList<>();
    protected Integer id;
    public Concert()
    {
        exampleData.add(new Concert("rock", LocalDateTime.of(2021, Month.APRIL, 24, 14, 33, 48, 123456789), 5f, new ArrayList<>(Arrays.asList("First", "Second")), "street1"));
        exampleData.add(new Concert("rap", LocalDateTime.of(2023, Month.APRIL, 24, 14, 33, 48, 123456789), 15f, new ArrayList<>(Arrays.asList("Third", "Second")), "street2"));
    }
    public Concert(String genre, LocalDateTime data, Float price, ArrayList<String> participantsNames, String place)
    {
        instanceCounter++;
        this.id = instanceCounter;
        this.genre = genre;
        this.data = data;
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
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String s = "Concert ID: " + id + " Genre: " + genre + " Data: " + data.format(myFormatObj) + " Price: " + price + " Participants: " + participantsNames + " Place: " + place;
        return s;
    }
}
