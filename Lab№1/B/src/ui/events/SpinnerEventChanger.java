package ui.events;
import threads.MyThread;
import ui.UI;

import javax.swing.*;
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
        JSpinner source = (JSpinner) e.getSource();
        if ((int) source.getValue() > 10)
            source.setValue(10);
        else if ((int) source.getValue() <= 0)
            source.setValue(1);
        int spinnerValue = (int) source.getValue();
    }
}
