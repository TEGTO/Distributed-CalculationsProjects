package Bee;
import java.util.*;

public class BeeSearching
{
    private static int WhoFoundWinny = 0;
    private static int amountOfBees = 0;
    private static boolean IsWinnyFoundGlobal = false;
    public void Search(int _chanceToFindWinnieOnField, int _amountOfTriesToFind)
    {
        int chanceToFindWinnieOnField = _chanceToFindWinnieOnField > 100 ? 100 : _chanceToFindWinnieOnField < 0 ? 0 : _chanceToFindWinnieOnField;
        int amountOfTriesToFind = _amountOfTriesToFind < 0 ? 0 : _amountOfTriesToFind;
        amountOfBees += 1;
        int id = amountOfBees;
        try
        {
            int times = 0;
            while (times < amountOfTriesToFind && WhoFoundWinny != id)
            {
                synchronized (this)
                {
                    if (!IsWinnyFoundGlobal && chanceToFindWinnieOnField > (new Random()).nextInt(100))
                    {
                        WhoFoundWinny = id;
                        IsWinnyFoundGlobal = true;
                        System.out.println(String.format("!!Winnie is found by Bee%1$s on Field%1$s on the %2$s try!!", id, times + 1));

                    }
                }
                if (WhoFoundWinny != id)
                {
                    times += 1;
                    System.out.println(String.format("Bee%1$s is searching", id));
                    Thread.sleep(200);
                }
            }
        }
        catch (Exception e)
        {
            System.out.println("Thread interrupted.");
        }
        if (WhoFoundWinny == id)
        {
            try
            {
                System.out.println(String.format("Bee%1$s is hitting Winnie", id));
            }
            catch (Exception e)
            {
                System.out.println("Thread interrupted.");
            }
        }
        System.out.println(String.format("Bee%1$s is coming to a hive", id));
    }
}


