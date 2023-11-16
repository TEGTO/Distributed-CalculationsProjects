package client;
import client.serverInteraction.BaseMovieDBServerInteraction;
import client.serverInteraction.JMSMovieDBServerInteraction;
import client.serverInteraction.RMIMovieDBServerInteraction;
import client.ui.ClientUI;
import client.serverInteraction.SocketMovieDBServerInteraction;

public class ClientStart
{
    public static void main(String[] args)
    {
        //BaseMovieDBServerInteraction socketMovieDBServerInteraction = new SocketMovieDBServerInteraction();
        //BaseMovieDBServerInteraction socketMovieDBServerInteraction = new RMIMovieDBServerInteraction();
        BaseMovieDBServerInteraction socketMovieDBServerInteraction = new JMSMovieDBServerInteraction();
        ClientUI clientUI = new ClientUI(socketMovieDBServerInteraction);
    }
}
