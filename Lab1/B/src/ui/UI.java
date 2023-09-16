package ui;

import javax.swing.*;

public class UI
{
    public JFrame Frame;
    public JButton StartButtonFirst;
    public JButton StartButtonSecond;
    public JButton StopButtonFirst;
    public JButton StopButtonSecond;
    public JSlider SliderThread;

    public UI()
    {
        Frame = new JFrame();//creating  of JFrame
        //Buttons

        //START
        StartButtonFirst = new JButton("StartFirst");//creating instance of JButton
        StartButtonFirst.setBounds(80, 150, 75, 40);//x axis, y axis, width, height
        StartButtonSecond = new JButton("StartSecond");//creating instance of JButton
        StartButtonSecond.setBounds(165, 150, 75, 40);//x axis, y axis, width, height
        //STOP
        StartButtonFirst = new JButton("Start1");//creating instance of JButton
        StartButtonFirst.setBounds(80, 100, 75, 40);//x axis, y axis, width, height
        StartButtonSecond = new JButton("Start2");//creating instance of JButton
        StartButtonSecond.setBounds(165, 100, 75, 40);//x axis, y axis, width, height

        StopButtonFirst = new JButton("Stop1");//creating instance of JButton
        StopButtonFirst.setBounds(80, 150, 75, 40);//x axis, y axis, width, height
        StopButtonSecond = new JButton("Stop2");//creating instance of JButton
        StopButtonSecond.setBounds(165, 150, 75, 40);//x axis, y axis, width, height

        Frame.add(StartButtonFirst);//adding button in JFrame
        Frame.add(StartButtonSecond);//adding button in JFrame
        Frame.add(StopButtonFirst);//adding button in JFrame
        Frame.add(StopButtonSecond);//adding button in JFrame
        //Slider
        SliderThread = new JSlider(0, 100, 50);
        SliderThread.setBounds(10, 50, 300, 40);
        SliderThread.setPaintTrack(true);
        SliderThread.setPaintTicks(true);
        SliderThread.setPaintLabels(true);
        SliderThread.setMajorTickSpacing(10);
        Frame.add(SliderThread);//adding slider in JFrame
        //Layout
        Frame.setSize(350, 300);//400 width and 500 height
        Frame.setLayout(null);//using no layout managers
        Frame.setVisible(true);//making the frame visible
    }
}
