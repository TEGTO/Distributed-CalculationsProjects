package lab8.client;
import lab8.client.serverInteraction.BaseMovieDBServerInteraction;
import lab8.client.serverInteraction.JMSMovieDBServerInteraction;
import lab8.client.serverInteraction.SocketMovieDBServerInteraction;
import lab8.client.ui.ClientUI;

public class ClientStart
{
    public static void main(String[] args)
    {
        BaseMovieDBServerInteraction socketMovieDBServerInteraction = new SocketMovieDBServerInteraction();
        //BaseMovieDBServerInteraction socketMovieDBServerInteraction = new RMIMovieDBServerInteraction();
        //BaseMovieDBServerInteraction socketMovieDBServerInteraction = new JMSMovieDBServerInteraction();
        ClientUI clientUI = new ClientUI(socketMovieDBServerInteraction);
    }
}
