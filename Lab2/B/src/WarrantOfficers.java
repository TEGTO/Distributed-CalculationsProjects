import java.util.*;

public class WarrantOfficers
{
    int maxAmountThatCanBeStolen = 5;
    List<String> stolenItemsOutside = new ArrayList<String>(0);
    List<String> stolenItemsInCar = new ArrayList<String>(0);
    Float priceOFStolenItems = (float) 0;
    final Map<String, Float> itemsListPrice = new HashMap<String, Float>()
    {{
        put("table", 15f);
        put("sofa", 35f);
        put("rocket", 350f);
        put("closet", 40F);
        put("rifle", 80F);
    }};
    public WarrantOfficers()
    {
        Thread IvanovThread = new Thread(() ->
        {
            try
            {
                while (stolenItemsInCar.size() < maxAmountThatCanBeStolen)
                {
                    if (stolenItemsOutside.isEmpty())
                    {
                        int randomInex = (new Random()).nextInt(itemsListPrice.size());
                        String newItemName = (String) itemsListPrice.keySet().toArray()[randomInex];
                        stolenItemsOutside.add(newItemName);
                        synchronized (System.out)
                        {
                            System.out.println(String.format("Ivanov steals a new %1$s.", newItemName));
                        }
                    }
                    Thread.sleep(1500);
                }
            }
            catch (InterruptedException e)
            {
                throw new RuntimeException(e);
            }
        });

        Thread PetrovThread = new Thread(() ->
        {
            try
            {
                while (stolenItemsInCar.size() < maxAmountThatCanBeStolen)
                {
                    if (!stolenItemsOutside.isEmpty())
                    {
                        String newItemName = new String(stolenItemsOutside.get(0));
                        synchronized (System.out)
                        {
                            System.out.println(String.format("Petrov loads %1$s from outside into the car.", newItemName));
                        }
                        stolenItemsInCar.add(newItemName);
                        stolenItemsOutside.remove(0);
                    }
                    Thread.sleep(500);
                }
            }
            catch (InterruptedException e)
            {
                throw new RuntimeException(e);
            }
        });
        Thread NechyporchukThread = new Thread(() ->
        {
            int itIndex = 0;
            try
            {
                while (stolenItemsInCar.size() <= maxAmountThatCanBeStolen)
                {
                    if (stolenItemsInCar.size() > itIndex)
                    {
                        String newItemName = stolenItemsInCar.get(itIndex);
                        priceOFStolenItems += itemsListPrice.get(newItemName);
                        itIndex++;
                        synchronized (System.out)
                        {
                            System.out.println(String.format("Nechyporchuk counts the price of %1$s in the car. It's " + itemsListPrice.get(newItemName), newItemName));
                        }
                    }
                    Thread.sleep(500);
                }
                synchronized (System.out)
                {
                    System.out.println(String.format("All stolen items cost: " + priceOFStolenItems));
                }
            }
            catch (InterruptedException e)
            {
                throw new RuntimeException(e);
            }
        });
        IvanovThread.start();
        PetrovThread.start();
        NechyporchukThread.start();
    }
}
