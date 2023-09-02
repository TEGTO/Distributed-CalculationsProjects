package threads;
import javax.swing.*;

public class MyThread extends Thread
{
    JSlider SliderThread;
    int numberToSlider;
    int threadPriority =1;
    boolean IsContinue = true;
    public  void SetThreadPriority(int newPriority)
    {
        threadPriority = newPriority;
    }
    public  int GetThreadPriority()
    {
        return threadPriority;
    }
    public  void CloseThreadCycle()
    {
        IsContinue = false;
    }
    public void run()
    {
        while (IsContinue)
        {
            try {
                int newSliderNumber =  (int)SliderThread.getValue();
                if(newSliderNumber > numberToSlider)
                    newSliderNumber -= threadPriority;
                else if (newSliderNumber < numberToSlider)
                    newSliderNumber += threadPriority;
                SliderThread.setValue(newSliderNumber);
                Thread.sleep(10);
            }
            catch (Exception e) {
                System.out.println("Exception is caught");
            }
        }
    }
    public MyThread(JSlider SliderThread, int numberToSlider)
    {
        this.SliderThread = SliderThread;
        this.numberToSlider = numberToSlider;
    }
}
