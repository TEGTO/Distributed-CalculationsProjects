package threads;

import javax.swing.*;

public class MyThread extends Thread
{
    JSlider SliderThread;
    int numberToSlider;
    boolean IsRunning = false;
    static boolean IsFree = true;

    public static boolean IsThreadFree()
    {
        return IsFree;
    }

    public void CloseThreadCycle()
    {
        if (IsRunning)
        {
            IsRunning = false;
            IsFree = true;
        }
    }

    public void run()
    {
        if (IsFree)
        {
            IsRunning = true;
            IsFree = false;
            while (IsRunning)
            {
                try
                {
                    int newSliderNumber = (int) SliderThread.getValue();
                    if (newSliderNumber > numberToSlider)
                        newSliderNumber -= 1;
                    else if (newSliderNumber < numberToSlider)
                        newSliderNumber += 1;
                    SliderThread.setValue(newSliderNumber);
                    Thread.sleep(10);
                } catch (Exception e)
                {
                    System.out.println("Exception is caught");
                }
            }
        }
    }

    public MyThread(JSlider SliderThread, int numberToSlider)
    {
        this.SliderThread = SliderThread;
        this.numberToSlider = numberToSlider;
    }
}
