import Bee.BeeSearching;
import threads.MyThread;

import java.util.ArrayList;
import java.util.List;

public class BeeManager
{
    public BeeManager(int amountOfBees, int minChanceToFind, int maxChanceToFind)
    {
        List<MyThread> myThreadsList = new ArrayList<>();
        BeeSearching beeSearching =  new BeeSearching();
        for (int i = 0; i < amountOfBees; i++)
        {
            MyThread myThread = new MyThread(beeSearching,getRandomNumberInRange(minChanceToFind, maxChanceToFind), getRandomNumberInRange(2, 10));
            myThreadsList.add(myThread);
        }
        for (MyThread thread : myThreadsList)
            thread.start();
        try
        {
            for (MyThread thread : myThreadsList)
                thread.join();
        }
        catch (Exception e)
        {
            System.out.println("Interrupted");
        }
    }
    public int getRandomNumberInRange(int min, int max)
    {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
