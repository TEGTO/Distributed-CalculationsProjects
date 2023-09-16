package ui;
import javax.swing.*;
public class UI {
    public JFrame Frame;
    public  JButton StartButton;
    public  JSlider SliderThread;
    public   JSpinner SpinnerFirstThread;
    public   JSpinner SpinnerSecondThread;
    public UI()
    {
        Frame = new JFrame();//creating instance of JFrame
        //Buttons
        StartButton=new JButton("click");//creating instance of JButton
        StartButton.setBounds(80,150,150, 40);//x axis, y axis, width, height
        Frame.add(StartButton);//adding button in JFrame
        //Slider
        SliderThread =  new JSlider(0, 100, 50);
        SliderThread.setBounds(10,50,300, 40);
        SliderThread.setPaintTrack(true);
        SliderThread.setPaintTicks(true);
        SliderThread.setPaintLabels(true);
        SliderThread.setMajorTickSpacing(10);
        Frame.add(SliderThread);//adding slider in JFrame
        //Spinners
        SpinnerFirstThread = new JSpinner();
        SpinnerSecondThread = new JSpinner();
        SpinnerFirstThread.setBounds(50, 100, 75, 40);
        SpinnerSecondThread.setBounds(200, 100, 75, 40);
        SpinnerFirstThread.setValue(1);
        SpinnerSecondThread.setValue(1);
        Frame.add(SpinnerFirstThread);
        Frame.add(SpinnerSecondThread);
        //Layout
        Frame.setSize(350,300);//400 width and 500 height
        Frame.setLayout(null);//using no layout managers
        Frame.setVisible(true);//making the frame visible
    }
}
