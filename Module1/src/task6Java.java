import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Train implements Runnable
{
    private String name;
    private Tunnel targetTunnel;
    private RailwaySystem railwaySystem;
    public Train(String name, Tunnel targetTunnel, RailwaySystem system)
    {
        this.name = name;
        this.targetTunnel = targetTunnel;
        this.railwaySystem = system;
    }
    @Override
    public void run()
    {
        try
        {
            railwaySystem.requestTunnelEntry(this, targetTunnel);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
    public String getName()
    {
        return name;
    }
}

class Tunnel
{
    private final Lock lock = new ReentrantLock();
    private String name;
    private Queue<Train> queue;
    public Tunnel(String name)
    {
        this.name = name;
        this.queue = new LinkedList<>();
    }
    public boolean tryEnter(Train train)
    {
        if (lock.tryLock())
        {
            System.out.println(train.getName() + " has entered " + name);
            queue.offer(train);
            return true;
        }
        return false;
    }
    public void exit(Train train)
    {
        System.out.println(train.getName() + " is exiting " + name);
        queue.poll();
        lock.unlock();
    }
    public String getName()
    {
        return name;
    }
    public boolean hasWaitingTrains()
    {
        return !queue.isEmpty();
    }
}
class RailwaySystem
{
    public static final int WAITING_TIME = 2000;
    public static final int IN_TUNNEL_TIME = 5000;
    private Tunnel tunnel1;
    private Tunnel tunnel2;
    public RailwaySystem(Tunnel tunnel1, Tunnel tunnel2)
    {
        this.tunnel1 = tunnel1;
        this.tunnel2 = tunnel2;
    }
    public void requestTunnelEntry(Train train, Tunnel preferredTunnel) throws InterruptedException
    {
        boolean hasEntered = preferredTunnel.tryEnter(train);
        int trainWaitingTime = 0;
        if (!hasEntered)
        {
            System.out.println(train.getName() + " is waiting...");
            while (!hasEntered && trainWaitingTime < WAITING_TIME)
            {
                hasEntered = preferredTunnel.tryEnter(train);
                trainWaitingTime++;
                Thread.sleep(1);
            }
            if (!hasEntered)
            {
                System.out.println(train.getName() + " is changing tunnel...");
                Tunnel alternateTunnel = (preferredTunnel == tunnel1) ? tunnel2 : tunnel1;
                requestTunnelEntry(train, alternateTunnel);
                return;
            }
        }
        Thread.sleep(IN_TUNNEL_TIME); // Simulate the time train spends in the tunnel
        preferredTunnel.exit(train);
    }
}
public class task6Java
{
    public static void main(String[] args)
    {
        Tunnel tunnel1 = new Tunnel("Tunnel 1");
        Tunnel tunnel2 = new Tunnel("Tunnel 2");
        RailwaySystem system = new RailwaySystem(tunnel1, tunnel2);

        Train train1 = new Train("Train 1", tunnel1, system);
        Train train2 = new Train("Train 2", tunnel2, system);
        Train train3 = new Train("Train 3", tunnel1, system);

        new Thread(train1).start();
        new Thread(train2).start();
        new Thread(train3).start();
    }
}