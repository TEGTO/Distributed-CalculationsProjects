package main.java;
import main.java.a.database.Database;
import main.java.b.garden.GardenApp;

import java.io.IOException;

public class Main
{
    public static void main(String[] args) throws IOException
    {
//        Database db = new Database("tmp.txt");
//
//        db.addRecord("Ivanov1", "Ivan1", "Ivanovich1", "113456789");
//        System.out.println(db.getName("113456789"));
//        System.out.println(db.getPhoneNumber("Ivanov1"));
//        db.addRecord("Ivanov2", "Ivan2", "Ivanovich2", "123456789");
//        db.addRecord("Ivanov3", "Ivan3", "Ivanovich3", "133456789");
//        System.out.println(db.removeRecord("Ivanov3", "Ivan3", "Ivanovich3", "133456789"));

        GardenApp app = new GardenApp(15, 15, "garden.txt");
    }
}