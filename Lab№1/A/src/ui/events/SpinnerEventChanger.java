package ui.events;
import threads.MyThread;
import ui.UI;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
public class SpinnerEventChanger implements ChangeListener
{
    MyThread threadFirst;
    MyThread threadSecond;
    UI ui;
    public SpinnerEventChanger(MyThread threadFirst, MyThread threadSecond, UI ui)
    {
        this.threadFirst = threadFirst;
        this.threadSecond = threadSecond;
        this.ui = ui;
    }
    public void stateChanged(ChangeEvent e)
    {
        int spinnerValue1 = (int)ui.SpinnerFirstThread.getValue()<0?1:(int)ui.SpinnerFirstThread.getValue();
        int spinnerValue2 = (int)ui.SpinnerSecondThread.getValue()<0?1:(int)ui.SpinnerSecondThread.getValue();
        threadFirst.SetThreadPriority(spinnerValue1);
        threadSecond.SetThreadPriority(spinnerValue2);
    }
}
