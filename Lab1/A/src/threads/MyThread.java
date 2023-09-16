package threads;
import javax.swing.*;

public class MyThread extends Thread
{
    JSlider SliderThread;
    int numberToSlider;
    boolean IsContinue = true;

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
                    newSliderNumber -= 1;
                else if (newSliderNumber < numberToSlider)
                    newSliderNumber += 1;
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
