package threads;
import Bee.BeeSearching;

import javax.swing.*;

public class MyThread extends Thread
{
    private BeeSearching beeSearching;
    private int chanceToFindWinnieOnField;
    private int amountOfTriesToFind;
    public MyThread(BeeSearching beeSearching, int chanceToFindWinnieOnField, int amountOfTriesToFind)
    {
        this.beeSearching = beeSearching;
        this.chanceToFindWinnieOnField = chanceToFindWinnieOnField;
        this.amountOfTriesToFind = amountOfTriesToFind;
    }
    public void run()
    {
        beeSearching.Search(chanceToFindWinnieOnField, amountOfTriesToFind);
    }
}
